import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    @Test
    void binaryPowerTest() {
        assertEquals(0, BinaryPower.pow(new BigInteger("2"), new BigInteger("10")).compareTo(new BigInteger("1024")));
        assertEquals(0, BinaryPower.pow(new BigInteger("3"), new BigInteger("4")).compareTo(new BigInteger("81")));
    }

    @Test
    void euclidTest() {
        BigInteger[] res = Euclid.extendedAlgorithm(new BigInteger("10"), new BigInteger("15"));
        assertEquals(0, res[0].compareTo(new BigInteger("5")));
        assertEquals(0, res[1].compareTo(new BigInteger("-1")));
        assertEquals(0, res[2].compareTo(new BigInteger("1")));
    }

    @Test
    void fermaTest() {
        assertTrue(Ferma.isPrime(new BigInteger("7"), 20));
        assertFalse(Ferma.isPrime(new BigDecimal("1.34078e161").toBigInteger(), 20));
    }

    @Test
    void karazubaTest() {
        assertEquals(0, Karazuba.multiply(new BigInteger("100"), new BigInteger("244")).compareTo(new BigInteger("24400")));
        assertEquals(0, Karazuba.multiply(new BigInteger("5534535"), new BigInteger("2423454353")).compareTo(new BigInteger("13412692937580855")));
    }
}
