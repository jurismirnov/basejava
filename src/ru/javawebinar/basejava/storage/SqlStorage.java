package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.sql.SqlHelper;

import javax.print.DocFlavor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?;")) {
                        ps.setString(1, r.getFullName());
                        ps.setString(2, r.getUuid());
                        ps.execute();
                        if (ps.executeUpdate() < 1) {
                            throw new NotExistStorageException("Update: Resume not exists");
                        }
                    }
                    dbDeleteContact(r, conn);
                    dbInsertContact(r, conn);
                    dbDeleteSection(r, conn);
                    dbInsertSection(r, conn);
                    return null;
                }
        );
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }
                    dbInsertContact(r, conn);
                    dbInsertSection(r, conn);
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                "SELECT * FROM " +
                "(SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid WHERE r.uuid=?) res " +
                "LEFT JOIN section s ON res.uuid = s.resume_uuid WHERE res.uuid=? ", ps -> {
            ps.setString(1, uuid);
            ps.setString(2, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException("Resume " + uuid + " is not exists");
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                sqlHelper.resumeAddContact(resume, rs);
                sqlHelper.resumeAddSection(resume, rs);
            } while (rs.next());
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            int rs = ps.executeUpdate();
            if (rs == 0) {
                throw new NotExistStorageException("Delete: Resume not exists");
            }
            return null;
        });
    }

    //@Override
    public List<Resume> getAllSortedOld() {
        return sqlHelper.execute("SELECT * FROM resume r LEFT JOIN contact c ON r.uuid=c.resume_uuid ORDER BY r.full_name,r.uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            Map<String, Resume> map = new LinkedHashMap<>();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                Resume resume = map.computeIfAbsent(uuid, k -> sqlHelper.newResume(uuid, rs));
                sqlHelper.resumeAddContact(resume, rs);
                sqlHelper.resumeAddSection(resume, rs);
            }
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> map = sqlHelper.execute("SELECT * FROM resume ORDER BY full_name,uuid", ps -> {
            ResultSet rsResume = ps.executeQuery();
            Map<String, Resume> mapResume = new LinkedHashMap<>();
            while (rsResume.next()) {
                String uuid = rsResume.getString("uuid");
                mapResume.computeIfAbsent(uuid, k -> sqlHelper.newResume(uuid, rsResume));
            }
            return mapResume;
        });
        sqlHelper.execute("SELECT * FROM contact", ps -> {
            ResultSet rsContact = ps.executeQuery();
            while (rsContact.next()) {
                String uuid = rsContact.getString("resume_uuid");
                map.computeIfPresent(uuid, (key, value) -> {
                    sqlHelper.resumeAddContact(value, rsContact);
                    return value;
                });
            }
            return map;
        });
        return sqlHelper.execute("SELECT * FROM section", ps -> {
            ResultSet rsSection = ps.executeQuery();
            while (rsSection.next()) {
                String uuid = rsSection.getString("resume_uuid");
                map.computeIfPresent(uuid, (key, value) -> {
                    sqlHelper.resumeAddSection(value, rsSection);
                    return value;
                });
            }
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT (*) as rows_amount FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new StorageException("", "Resume is not set");
            }
            return rs.getInt("rows_amount");
        });
    }

    private void dbInsertContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, contact_type, contact_value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void dbDeleteContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void dbInsertSection(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, section_type, section_value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, String.valueOf(e.getValue()));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void dbDeleteSection(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid=?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }
}
