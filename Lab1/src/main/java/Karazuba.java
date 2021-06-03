import java.math.BigInteger;

public class Karazuba {
    public static BigInteger multiply(BigInteger x, BigInteger y) {
        int N = Math.max(x.bitLength(), y.bitLength());

        if (N <= 500) {
            return x.multiply(y);
        }

        N = (N / 2) + (N % 2);

        BigInteger x1 = x.shiftRight(N);
        BigInteger x2 = x.subtract(x1.shiftLeft(N));
        BigInteger y1 = y.shiftRight(N);
        BigInteger y2 = y.subtract(y1.shiftLeft(N));

        BigInteger x1y1 = multiply(x1, y1);
        BigInteger x2y2 = multiply(x2, y2);
        BigInteger x1x2y1y2 = multiply(x2.add(x1), y2.add(y1));

        return x2y2.add(x1x2y1y2.subtract(x2y2).subtract(x1y1).shiftLeft(N)).add(x1y1.shiftLeft(2 * N));
    }
}