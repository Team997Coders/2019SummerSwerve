package frc;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SwerveTest {

    @Test
    public void mixerTest() {
        double c = -1;

        double d = c * c;

        assertTrue("Hinkey", d == 1);
    }

}