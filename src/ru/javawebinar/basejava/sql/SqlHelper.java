package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.util.JsonParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String sql, AddCodeBlock<T> addCodeBlock) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return addCodeBlock.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException("Update: Resume already exists");
            } else {
                throw new StorageException(e);
            }
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                 throw new ExistStorageException("Update: Resume already exists");
            } else {
                throw new StorageException(e);
            }
        }
    }

    public Resume newResume(String uuid, ResultSet rs){
        try {
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException("", "Some error in SqlStorage.newResume");
        }
    }

    public void resumeAddContact(Resume resume, ResultSet rs) {
        try {
            Object object = rs.getObject("contact_type");
            if (object != null) {
                resume.addContact(ContactType.valueOf((String) object), rs.getString("contact_value"));
            }
        } catch (SQLException e) {
            throw new StorageException("", "Some error in SqlStorage.resumeAddContact");
        }
    }

    public void resumeAddSection(Resume resume, ResultSet rs) {
        try {
            Object object = rs.getObject("section_type");
            if (object != null) {
                resume.addSection(SectionType.valueOf((String) object),JsonParser.read(rs.getString("section_value"), Section.class));
            }
        } catch (SQLException e) {
            throw new StorageException("", "Some error in SqlStorage.resumeAddContact");
        }
    }
}
