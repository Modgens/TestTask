import org.example.ExpressionService;
import org.example.RootService;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RootServiceTest {
    @Test
    public void testIsRootValid() {
        assertTrue(RootService.isRootValid("x * (x - 3) = 0", 3.0));
        assertTrue(RootService.isRootValid("x + 4*x + 4 = -6", -2.0));
        assertTrue(RootService.isRootValid("x / x + 4 = 5", 1.0));
        assertTrue(RootService.isRootValid("x - 2*x / 4  = -1", -2.0));
        assertTrue(RootService.isRootValid("(x + (x * (2 + 4))) = -7", -1.0));
    }
}
