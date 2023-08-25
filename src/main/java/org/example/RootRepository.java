package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RootRepository {
    private final Connection connection;

    public RootRepository(Connection connection) {
        this.connection = connection;
    }

    public Long saveAndGetId(Root root) throws SQLException {
        String sql = "INSERT INTO roots (root) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1, root.getRoot());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating root failed, no ID obtained.");
                }
            }
        }
    }
    public void linkRootToExpression(Long rootId, Long expressionId) throws SQLException {
        String sql = "INSERT INTO root_expression (root_id, expression_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, rootId);
            preparedStatement.setLong(2, expressionId);
            preparedStatement.executeUpdate();
        }
    }
    public long getRootIdByRootValue(double rootValue) throws SQLException {
        String sql = "SELECT id FROM roots WHERE root = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, rootValue);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("id");
                }
            }
        }

        return 0;
    }
    public Root getRootById(long id) throws SQLException {
        String sql = "SELECT root FROM roots WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double rootValue = resultSet.getDouble("root");
                    return new Root(id, rootValue);
                }
            }
        }

        return null; // Якщо запис не знайдено
    }
    public boolean checkIfRelationshipExists(long rootId, long expressionId) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM root_expression WHERE root_id = ? AND expression_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, rootId);
            preparedStatement.setLong(2, expressionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        }

        return false; // Помилка або зв'язок не знайдений
    }
    public List<Expression> getExpressionsWithSameRoot(double rootValue) throws SQLException {
        String sql = "SELECT e.id, e.expression " +
                "FROM roots r " +
                "JOIN root_expression re ON r.id = re.root_id " +
                "JOIN expressions e ON re.expression_id = e.id " +
                "WHERE r.root = ?";

        List<Expression> expressionsWithSameRoot = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, rootValue);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long expressionId = resultSet.getLong("id");
                    String expressionText = resultSet.getString("expression");
                    Expression expression = new Expression(expressionId, expressionText);
                    expressionsWithSameRoot.add(expression);
                }
            }
        }

        return expressionsWithSameRoot;
    }
}

