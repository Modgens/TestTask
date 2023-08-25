package org.example;

import java.sql.*;

public class ExpressionRepository {
    private final Connection connection;

    public ExpressionRepository(Connection connection) {
        this.connection = connection;
    }

    public Long saveAndGetId(Expression expression) throws SQLException {
        String sql = "INSERT INTO expressions (expression) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, expression.getExpression());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating expression failed, no ID obtained.");
                }
            }
        }
    }


}
