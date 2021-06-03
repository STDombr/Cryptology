import java.math.BigInteger;

public class BinaryPower {

    public static BigInteger pow(BigInteger a, BigInteger n) {
        if (n.equals(BigInteger.ZERO)) {
            return BigInteger.ONE;
        }
        if (n.equals(BigInteger.ONE)) {
            return a;
        }
        if (n.mod(BigInteger.TWO).equals(BigInteger.ONE)) {
            return a.multiply(pow(a, n.subtract(BigInteger.ONE)));
        }

        else {
            BigInteger b = pow(a, n.divide(BigInteger.TWO));
            return b.multiply(b);
        }
    }
}