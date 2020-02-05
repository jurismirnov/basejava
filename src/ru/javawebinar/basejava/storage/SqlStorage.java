package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;
import ru.javawebinar.basejava.util.JsonParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
                resumeAddContact(resume, rs);
                resumeAddSection(resume, rs);
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

    //
    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> mapResume = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name,uuid")) {
                ResultSet rsResume = ps.executeQuery();
                while (rsResume.next()) {
                    String uuid = rsResume.getString("uuid");
                    String fullName = (String) rsResume.getObject("full_name");
                    mapResume.computeIfAbsent(uuid, k -> new Resume(uuid, fullName));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
                ResultSet rsContact = ps.executeQuery();
                while (rsContact.next()) {
                    resumeAddContact(mapResume.get(rsContact.getString("resume_uuid")), rsContact);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section")) {
                ResultSet rsSection = ps.executeQuery();
                while (rsSection.next()) {
                    resumeAddSection(mapResume.get(rsSection.getString("resume_uuid")), rsSection);
                }
            }
            return new ArrayList<>(mapResume.values());
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
                String type = e.getKey().name();
                ps.setString(2, type);
                ps.setString(3, sectionToString(SectionType.valueOf(type), e.getValue()));
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

    private void resumeAddContact(Resume resume, ResultSet rs) throws SQLException {
        String str = rs.getString("contact_type");
        if (str != null) {
            resume.addContact(ContactType.valueOf(str), rs.getString("contact_value"));
        }
    }

    private void resumeAddSection(Resume resume, ResultSet rs) throws SQLException {
        String sectionType =rs.getString("section_type");
        if (sectionType != null) {
        SectionType type = SectionType.valueOf(sectionType);
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    resume.addSection(type, new TextSection(rs.getString("section_value")));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    resume.addSection(type, new TextListSection(Arrays.asList(rs.getString("section_value").split("\n"))));
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    resume.addSection(type,JsonParser.read(rs.getString("section_value"), Section.class));
            }
        }
    }

    private String sectionToString(SectionType type, Section section) {
        String str = null;
        switch (type) {
            case OBJECTIVE:
            case PERSONAL:
                str = ((TextSection) section).getText();
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                str = String.join("\n", ((TextListSection) section).getRecords());
                break;
            case EDUCATION:
            case EXPERIENCE:
                str = JsonParser.write(section, Section.class);
        }
        return str;
    }
}
