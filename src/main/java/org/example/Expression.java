package org.example;

public class Expression {
    private Long id;
    private String expression;

    public Expression(Long id, String expression) {
        this.id = id;
        this.expression = expression;
    }

    public Expression(String expression) {
        this.expression = expression;
    }


    public Expression() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

}
