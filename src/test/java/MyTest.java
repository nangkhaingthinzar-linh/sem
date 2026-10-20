
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyTest {

    @Test
    public void unitTest() {
        assertEquals(5, 5);
    }

    @Test
    public void unitTest2() {
        assertEquals(5, 5);
    }

    @Test
    public void unitTest3() {
        assertEquals(5, 5, "Messages are equal");
    }

    @Test
    public void unitTest4() {
        assertEquals(5.0, 5.01, 0.02);
    }

    @Test
    public void unitTest5() {
        int[] a = {1, 2, 3};
        int[] b = {1, 2, 3};
        assertArrayEquals(a, b);
    }

    @Test
    public void unitTest6() {
        assertTrue(5 == 5);
    }

    @Test
    public void unitTest7() {
        assertFalse(5 == 4);
    }

    @Test
    public void unitTest8() {
        assertNull(null);
    }

    @Test
    public void unitTest9() {
        assertNotNull("Hello");
    }

    @Test
    public void unitTest10() {
        assertThrows(
                NullPointerException.class,
                this::throwsException
        );
    }

    public void throwsException() {
        throw new NullPointerException();
    }
}
