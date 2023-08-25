package org.example;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;

public class Main {
    public static ExpressionService expressionService = ExpressionService.getInstance();
    public static RootService rootService = RootService.getInstance();
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args){
        while (true) {
            System.out.println("Введіть вираз: ");
            String expression = scanner.nextLine();
            if (Validator.isParenthesesValid(expression) && Validator.isExpressionValid(expression)) {

                Expression expressionEntity = new Expression(expression);
                try {
                    expressionService.saveExpression(expressionEntity);
                } catch (RuntimeException e) {
                    System.out.println("Такий вираз вже записаний в бд");
                    continue;
                }

                System.out.println("Чи бажаєте ввести корені?");
                System.out.println("1 - Бажаю ввести");
                System.out.println("2 - Не бажаю");
                if (scanner.nextInt() == 1) {
                    while (true) {
                        double root;
                        System.out.print("x = ");
                        root = scanner.nextDouble();
                        if (RootService.isRootValid(expression, root)) {
                            Root rootEntity = new Root();
                            rootEntity.setRoot(root);
                            System.out.println("Корінь " + root + " є валідним");
                            rootService.saveRoot(rootEntity, expressionEntity);
                        } else {
                            System.out.println("Корінь " + root + " не є валідним.");
                        }
                        System.out.println("Бажаєте ввести ще корінь, чи вважаєте що досить?");
                        System.out.println("1 - ввести ще корінь");
                        System.out.println("2 - досить");
                        if (scanner.nextInt() == 2)
                            break;
                    }
                }
                System.out.println("Бажаєте побачити всі вирази з одинаковим коренем?");
                System.out.println("1 - так");
                System.out.println("2 - ні");
                if (scanner.nextInt() == 1){
                    System.out.println("Введіть корінь:");
                    for (Expression e : rootService.getAllExpressionsByRoot(scanner.nextDouble())){
                        System.out.println(e.getExpression());
                    }
                }

                System.out.println("Бажаєте продовжити чи вийти?");
                System.out.println("1 - продовжити");
                System.out.println("2 - зупинити роботу");
                if (scanner.nextInt() == 1)
                    continue;
                break;

            } else {
                System.out.println("Вираз " + expression + " не є валідним");
            }
        }
    }
}
