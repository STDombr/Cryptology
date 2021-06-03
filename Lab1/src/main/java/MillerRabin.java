import java.math.BigInteger;
import java.util.Random;

public class MillerRabin {
    private static final BigInteger THREE = new BigInteger("3");

    public static boolean isPrime(BigInteger n, int maxIterations) {
        if (n.compareTo(THREE) < 0)
            return true;

        int s = 0;
        // n - 1 = 2^s * d, where d % 2 = 1
        BigInteger d = n.subtract(BigInteger.ONE);

        while (d.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            s++;
            d = d.divide(BigInteger.TWO);
        }

        for (int i = 0; i < maxIterations; i++) {
            BigInteger a = uniformRandom(BigInteger.TWO, n.subtract(BigInteger.ONE));
            // x = a^d mod n
            BigInteger x = a.modPow(d, n);

            if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE)))
                continue;

            int r = 0;
            for (; r < s; r++) {
                // x = x^2 mod n
                x = x.modPow(BigInteger.TWO, n);

                if (x.equals(BigInteger.ONE))
                    return false;

                if (x.equals(n.subtract(BigInteger.ONE)))
                    break;
            }

            if (r == s)
                return false;
        }
        return true;
    }

    private static BigInteger uniformRandom(BigInteger bottom, BigInteger top) {
        Random rnd = new Random();

        BigInteger res;
        do {
            res = new BigInteger(top.bitLength(), rnd);
        } while (res.compareTo(bottom) < 0 || res.compareTo(top) > 0);
        return res;
    }
}