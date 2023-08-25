import org.junit.jupiter.api.Test;

import static org.example.Validator.*;
import static org.junit.jupiter.api.Assertions.*;

public class ParenthesesValidationTest {

    @Test
    public void testValidParentheses() {
        assertTrue(isParenthesesValid("(2 + 3) * (4 - 1)"));
        assertTrue(isParenthesesValid("((x + 2) / 3) * (4 - x)"));
        assertTrue(isParenthesesValid("2 * ((3 + 4) / 2)"));
    }

    @Test
    public void testInvalidParentheses() {
        assertFalse(isParenthesesValid("(2 + 3 * (4 - 1)"));
        assertFalse(isParenthesesValid("((x + 2) / 3) * (4 - x = 3"));
        assertFalse(isParenthesesValid("2 * (3 + 4) / 2) = 3"));
        assertFalse(isParenthesesValid(")2 + 3( = 3"));
        assertFalse(isParenthesesValid(")(x + 2) / 3) * (4 - x)"));
        assertFalse(isParenthesesValid("(x = 2) / 3 * (4 - x)"));
        assertFalse(isParenthesesValid("(+ 2) / 3 * (4 - x)"));
    }


}
