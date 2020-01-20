package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
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
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE contact SET value=? WHERE resume_uuid=? AND type=?  ")) {
                        for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                            ps.setString(1, e.getValue());
                            ps.setString(2, r.getUuid());
                            ps.setString(3, e.getKey().name());
                            ps.addBatch();
                        }
                        ps.executeBatch();
                    }
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
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                        for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                            ps.setString(1, r.getUuid());
                            ps.setString(2, e.getKey().name());
                            ps.setString(3, e.getValue());
                            ps.addBatch();
                        }
                        ps.executeBatch();
                    }
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r " +
                " LEFT JOIN contact c " +
                "ON r.uuid = c.resume_uuid " +
                "WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException("Resume " + uuid + " is not exists");
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                String value = rs.getString("value");
                ContactType type = ContactType.valueOf(rs.getString("type"));
                resume.addContact(type, value);
            } while (rs.next());
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            boolean rs = ps.execute();
            if (!rs) {
                throw new NotExistStorageException("Delete: Resume not exists");
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT * FROM resume ORDER BY full_name,uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumeList = new ArrayList<>();
            while (rs.next()) {
                Resume resume = new Resume(rs.getString("uuid").trim(), rs.getString("full_name"));
                resumeList.add(resumeGetContacts(resume));
            }
            return resumeList;
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

    private Resume resumeGetContacts(Resume resume) {
        return sqlHelper.execute("SELECT * FROM contact WHERE resume_uuid=?", ps -> {
            ps.setString(1, resume.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String value = rs.getString("value");
                ContactType type = ContactType.valueOf(rs.getString("type"));
                resume.addContact(type, value);
            }
            return resume;
        });
    }
}
