import com.mitrais.atm.util.RandomNumberGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class RandomNumberGeneratorTest {

    String result;
    @Before
    public void setUp() {
        result = null;
    }

    @Test
    public void getRandomDigitNumberHas6Digit() {
        RandomNumberGenerator rng = new RandomNumberGenerator();
        result = rng.getRandom6DigitNumber();
        int size = result.length();
        assertEquals(size, 6);
    }

    @Test
    public void getRandomDigitNumberReturnNumberOnly() {
        RandomNumberGenerator rng = new RandomNumberGenerator();
        result = rng.getRandom6DigitNumber();
        boolean isNumberOnly = result.matches("[0-9]+");
        assertEquals(isNumberOnly, true);
    }
}
