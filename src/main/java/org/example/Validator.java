package org.example;

import java.util.regex.Pattern;

public class Validator {
    public static boolean isParenthesesValid(String expression) {

        if (expression == null || expression.equals(""))
            return false;

        expression = expression.replaceAll(" ", "");

        if (Pattern.compile("\\([+/*]").matcher(expression).find() || Pattern.compile("[+/*]\\)").matcher(expression).find()){
            return false;
        }

        int count = 0;
        for (char ch : expression.toCharArray()) {
            if (ch == '(') {
                count++;
            } else if (ch == ')') {
                count--;
                if (count < 0) {
                    return false;
                }
            }
            if (ch == '=' && count != 0)
                return false;
        }
        return count == 0;
    }
    public static boolean isExpressionValid(String expression) {

        if (expression == null || expression.equals(""))
            return false;

        // Дужки нам не потрібні, щоб перевірити вираз на коректність
        // Вони лише заважатимуть, як і пробіли
        expression = expression.replaceAll("[ ()]", "");

        // Перевірка кількості x та єдиного =
        if (Pattern.compile("([0-9x]+x)|(x[0-9x]+)").matcher(expression).find()
                || expression.length() - expression.replace("x", "").length() == 0
                || expression.length() - expression.replace("=", "").length() != 1

        ) {
            return false;
        }
        // Рішення казусу з плутаниною операції мінус та від'ємного числа
        expression = expression.replaceAll("[x0-9]+-[0-9x]+", " - ");
        // Заміна чисел на " "
        expression = expression.replaceAll("-?\\d+(\\.\\d+)?", " ");
        // Заміна букви x та -x
        expression = expression.replaceAll("-?x", " ");
        // Прибираємо = важливо прибирати пробіли зліва та справа, інакше у нас некоректне розміщення =
        expression = expression.replaceAll(" = ", " ");
        // Тепер прибираємо всі символи з пробілом справа і в нас має лишитись 1 пробіл
        expression = expression.replaceAll(" [-+/*]", "");
        return expression.length() == 1;
    }
}
