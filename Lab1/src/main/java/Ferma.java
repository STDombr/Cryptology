import java.math.BigInteger;
import java.util.Random;

public class Ferma {

    private static BigInteger getRandomFermatBase(BigInteger n) {
        Random rand = new Random();
        while (true) {
            final BigInteger a = new BigInteger(n.bitLength(), rand);

            if (BigInteger.ONE.compareTo(a) <= 0 && a.compareTo(n) < 0)
                return a;
        }
    }

    public static boolean isPrime(BigInteger n, int iterations) {
        if (n.equals(BigInteger.ONE))
            return false;

        for (int i = 0; i < iterations; i++) {
            BigInteger a = getRandomFermatBase(n);
            a = a.modPow(n.subtract(BigInteger.ONE), n);

            if (!a.equals(BigInteger.ONE))
                return false;
        }

        return true;
    }
}