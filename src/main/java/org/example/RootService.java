package org.example;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RootService {

    private static RootRepository repository;
    private static RootService instance;
    public static RootService getInstance() {
        if (instance == null) {
            instance = new RootService();
            try {
                repository = new RootRepository(DatabaseUtil.getConnection());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    public static boolean isRootValid(String expression, double root) {
        expression = expression.replaceAll("x", ""+root);
        expression = expression.replaceAll(" ", "");
        expression = expression.replaceAll("([0-9])-([0-9])", "$1+-$2");
        return evaluateExpression(expression);
    }

    private static boolean evaluateExpression(String expression) {
        String[] parts = expression.split("=");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid expression format");
        }

        double leftResult = Double.parseDouble(evaluateArithmeticExpression(parts[0]));
        double rightResult = Double.parseDouble(evaluateArithmeticExpression(parts[1]));

        return leftResult == rightResult;
    }

    private static String evaluateArithmeticExpression(String part) {

        String regex = "(-?[0-9.]+)([/*])(-?[0-9.]+)"; // Регулярний вираз для пошуку

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(part);

        if (matcher.find()) {
            return getGroups(part, matcher);
        } else {
            regex = "(-?[0-9.]+)([-+])(-?[0-9.]+)";
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(part);
            if(matcher.find()) {
                return getGroups(part, matcher);
            } else if(part.contains("(")){
                return evaluateArithmeticExpression(removeBrackets(part));
            } else {
                return part;
            }
        }
    }

    private static String getGroups(String part, Matcher matcher) {
        String full = matcher.group();
        String left = matcher.group(1);
        String operator = matcher.group(2);
        String right = matcher.group(3);
        String result = eval(left, operator, right);
        return evaluateArithmeticExpression(part.replace(full, result));
    }

    private static String removeBrackets (String expression) {
        String regex = "\\((-?[0-9.]+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(expression);

        if (matcher.find()){
            String full = matcher.group(0);
            String g1 = matcher.group(1);
            return expression.replace(full, g1);
        }
        return expression;
    }

    private static String eval(String a, String operator, String b) {
        double r = 0;

        switch (operator) {
            case "/" -> r += Double.parseDouble(a) / Double.parseDouble(b);
            case "*" -> r += Double.parseDouble(a) * Double.parseDouble(b);
            case "-" -> r += Double.parseDouble(a) - Double.parseDouble(b);
            case "+" -> r += Double.parseDouble(a) + Double.parseDouble(b);
        }

        return Double.toString(r);
    }

    public void saveRoot(Root rootEntity, Expression expressionEntity) {

        long id;
        try {
            id = repository.getRootIdByRootValue(rootEntity.getRoot());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (id == 0) {
            try {
                rootEntity.setId(repository.saveAndGetId(rootEntity));
                repository.linkRootToExpression(rootEntity.getId(), expressionEntity.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            rootEntity.setId(id);
            try {
                if(!repository.checkIfRelationshipExists(id, expressionEntity.getId()))
                    repository.linkRootToExpression(rootEntity.getId(), expressionEntity.getId()); // Нам не нада 2 зв'язка одинакових
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public List<Expression> getAllExpressionsByRoot(double root) {
        try {
            return repository.getExpressionsWithSameRoot(root);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
