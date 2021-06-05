import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

public class ElGamalTest {

    @Test
    public void test() {
        ElGamal elgamal = new ElGamal();
        Random rand = new Random();

        for (int i = 0; i < 10000; i++) {
            BigInteger number = BigInteger.ONE;
            for (int j = 0; j < rand.nextInt(10000); j++) {
                number.add(BigInteger.ONE);
            }

            List<BigInteger> encrypt = elgamal.Encrypt(number);
            BigInteger decrypt = elgamal.Decrypt(encrypt);
            Assert.assertEquals(number, decrypt);
        }
    }
}
