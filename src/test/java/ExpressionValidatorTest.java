import org.example.Validator;
import org.junit.jupiter.api.Test;

import static org.example.Validator.*;
import static org.junit.jupiter.api.Assertions.*;

public class ExpressionValidatorTest {



    @Test
    public void testValidEquations() {
        assertTrue(isExpressionValid("x + 2 = 5"));
        assertTrue(isExpressionValid("3 * (x - 4) / 2 = 7"));
        assertTrue(isExpressionValid("2 * x + 1 = 10"));
        assertTrue(isExpressionValid("(x + 1) * (x - 1) = 0"));
        assertTrue(isExpressionValid("21 - -5 + x * -10 = 21"));
        assertTrue(isExpressionValid("x = 5"));
        assertTrue(isExpressionValid("(x + x - 1 + (x - -3) / 4) = 0"));
    }

    @Test
    public void testInvalidEquations() {
        assertFalse(isExpressionValid("x + = 5"));
        assertFalse(isExpressionValid("x + 2"));
        assertFalse(isExpressionValid("+ 2 * x = 10"));
        assertFalse(isExpressionValid("4*+7 = x"));
        assertFalse(isExpressionValid("4-7 = 0"));
        assertFalse(isExpressionValid("2 * x + = 8"));
        assertFalse(isExpressionValid("(x + 1) * (x - -1) += 0"));
        assertFalse(isExpressionValid("xx = 5 = 7"));
        assertFalse(isExpressionValid("x * * x = 25"));
        assertFalse(isExpressionValid("3*xqwq-4/2=7"));
        assertFalse(isExpressionValid("3*xxx-4/2=7"));
        assertFalse(isExpressionValid("3*x12131-4/2=7"));
        assertFalse(isExpressionValid("///3*x-4/2=7"));
        assertFalse(isExpressionValid("-3*x-4/2=7/"));
        assertFalse(isExpressionValid("*3*x-4/2=7"));
    }
}
