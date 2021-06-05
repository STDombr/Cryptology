import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class ElGamal {

    private BigInteger p;
    private BigInteger order;
    private BigInteger g;
    private BigInteger x;
    private BigInteger y;

    public ElGamal() {
        genkey(10);
    }

    public static void main(String[] args) {
        ElGamal elgamal = new ElGamal();
        List<BigInteger> list = elgamal.Encrypt(new BigInteger("11100"));
        List<BigInteger> list2 = elgamal.Encrypt(new BigInteger("2"));

        BigInteger m = elgamal.Decrypt(list);
        System.out.println("Decrypt:" + m);
        m = elgamal.Decrypt(list2);
        System.out.println("Decrypt:" + m);
    }

    private void genkey(int nb_bits) {
        order = new BigInteger(nb_bits, 10, new SecureRandom());
        p = order.multiply(BigInteger.TWO).add(BigInteger.ONE);

        while (!p.isProbablePrime(10)) {
            order = new BigInteger(nb_bits, 10, new SecureRandom());
            p = order.multiply(BigInteger.TWO).add(BigInteger.ONE);
        }

        g = random_number(p);
        while (!g.modPow(order, p).equals(BigInteger.ONE)) {
            if (g.modPow(order.multiply(BigInteger.TWO), p).equals(BigInteger.ONE)) {
                g = g.modPow(BigInteger.TWO, p);
            } else {
                g = random_number(p);
            }
        }
        x = random_number(p);
        y = g.modPow(x, p);
    }

    public List<BigInteger> Encrypt(BigInteger m) {
        List<BigInteger> list = new ArrayList<>();
        BigInteger k = random_number(order);
        BigInteger a = g.modPow(k, p);
        BigInteger temp = y.modPow(k, p);
        BigInteger b = g.modPow(m, p);
        b = temp.multiply(b);
        list.add(a);
        list.add(b);
        return list;
    }

    public BigInteger Decrypt(List<BigInteger> list) {
        BigInteger gr = list.get(0);
        BigInteger hrgm = list.get(1);
        BigInteger hr = gr.modPow(x, p);
        BigInteger gm = hrgm.multiply(hr.modInverse(p)).mod(p);

        BigInteger m = BigInteger.ONE;
        BigInteger gm_prime = g.modPow(m, p);

        while (!gm_prime.equals(gm)) {
            m = m.add(BigInteger.ONE);
            gm_prime = g.modPow(m, p);
        }

        return m;
    }

    private BigInteger random_number(BigInteger n) {
        return new BigInteger(n.bitLength(), new SecureRandom()).mod(n);
    }

}