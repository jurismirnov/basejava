package ru.javawebinar.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface AddCodeBlock<T> {
    T execute(PreparedStatement prStatement) throws SQLException;
}
