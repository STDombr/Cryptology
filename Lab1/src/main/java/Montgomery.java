import java.math.BigInteger;

public class Montgomery {
    private static final BigInteger TWO = BigInteger.valueOf(2);

    private static BigInteger MR(BigInteger T, BigInteger N, BigInteger R, int pow) {
        Euclid e = new Euclid();
        BigInteger[] array = e.extendedAlgorithm(N, R);
        BigInteger d = array[0];

        if (!d.equals(BigInteger.ONE))
            throw new IllegalArgumentException("GCD(N,R) != 1");
        if (T.compareTo(N.shiftLeft(pow)) >= 0)
            throw new IllegalArgumentException("T >= NR");

        BigInteger invN = array[1];
        BigInteger minusInvN = invN.negate();
        BigInteger m = (T.multiply(minusInvN)).mod(R);
        BigInteger t = (T.add(m.multiply(N))).shiftRight(pow);

        while (N.compareTo(t) < 0) {
            t = t.subtract(N);
        }
        return t;
    }

    public static BigInteger multiply(BigInteger a, BigInteger b, BigInteger R, BigInteger N, int pow) {
        BigInteger a1 = a.shiftLeft(pow).mod(N);
        BigInteger b1 = b.shiftLeft(pow).mod(N);
        BigInteger c1 = MR(a1.multiply(b1), N, R, pow);
        return MR(c1, N, R, pow);
    }

    public static BigInteger pow(BigInteger a, BigInteger e, BigInteger R, BigInteger N, int pow) {
        BigInteger a1 = a.shiftLeft(pow).mod(N);
        BigInteger x1 = BigInteger.ONE;
        while (e.compareTo(BigInteger.ZERO) > 0) {
            x1 = MR(x1.multiply(a1), N, R, pow);
            e = e.subtract(BigInteger.ONE);
        }
        return x1;
    }

}