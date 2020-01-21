package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?;\n" +
                            "DELETE FROM contact WHERE resume_uuid=?;")) {
                        ps.setString(1, r.getFullName());
                        ps.setString(2, r.getUuid());
                        ps.setString(3, r.getUuid());
                        ps.execute();
                        if (ps.executeUpdate() < 1) {
                            throw new NotExistStorageException("Update: Resume not exists");
                        }
                    }
                    dbInsertContact(r, conn);
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
                resumeAddContact(resume, rs);
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
        return sqlHelper.execute("SELECT * FROM resume r JOIN contact c ON r.uuid=c.resume_uuid ORDER BY r.full_name,r.uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumeList = new ArrayList<>();
            String compareTo = "";
            Resume resume = null;
            String uuidTmp;
            while (rs.next()) {
                uuidTmp = rs.getString("uuid").trim();
                if (!uuidTmp.equals(compareTo)) {
                    if (resume != null) {
                        resumeList.add(resume);
                    }
                    resume = new Resume(uuidTmp, rs.getString("full_name"));
                    compareTo = uuidTmp;
                }
                resumeAddContact(resume, rs);
            }
            resumeList.add(resume);
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

    private void dbInsertContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void resumeAddContact(Resume resume, ResultSet rs) throws SQLException {
        ContactType type = ContactType.valueOf(rs.getString("type"));
        if (type != null) {
            String value = rs.getString("value");
            resume.addContact(type, value);
        }
    }
}
