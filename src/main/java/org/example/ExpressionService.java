package org.example;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class ExpressionService {
    private static ExpressionRepository repository;
    private static ExpressionService instance;
    public static ExpressionService getInstance() {
        if (instance == null) {
            instance = new ExpressionService();
            try {
                repository = new ExpressionRepository(DatabaseUtil.getConnection());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }
    public void saveExpression(Expression expression) {
        try {
            expression.setId(repository.saveAndGetId(expression));
            System.out.println("Вираз: " + expression.getExpression() + " валідний та збережений в БД.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
