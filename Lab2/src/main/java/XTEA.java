public class XTEA {

    private final int[] key;
    public XTEA(String key) {
        this(toByteArray(key));
    }

    private static byte[] toByteArray(String key) {
        byte[] bkey = new byte[key.length()];
        for (int i = 0; i < key.length(); ++i)
            bkey[i] = (byte) key.charAt(i);
        return bkey;
    }

    public XTEA(byte[] key) {
        if (key.length < 1)
            throw new IllegalArgumentException();

        this.key = new int[alignInt(alignX(key.length, 16))];
        for (int i = 0; i < this.key.length; i += 1)
            this.key[i] = makeInt(key, i << 2);
    }

    public byte[] encrypt(byte[] data) {
        int[] buffer = toIntArray(data);
        new Engine(key).encrypt(buffer);
        return toByteArray(buffer);
    }

    public byte[] decrypt(byte[] data) {
        int[] buffer = toIntArray2(data);
        new Engine(key).decrypt(buffer);
        return toByteArray2(buffer);
    }

    public byte[] encryptString(String text) {
        return encrypt(toByteArray(text));
    }

    public String decryptString(byte[] data) {
        return new String(toCharArray(decrypt(data)));
    }

    private char[] toCharArray(byte[] data) {
        char[] chars = new char[data.length];
        for (int i = 0; i < data.length; ++i)
            chars[i] = (char) data[i];
        return chars;
    }

    public static class Engine {
        private static final int delta = 0x9E3779B9;

        private final int rounds;
        private final int[] key;
        private final int keybits;

        public Engine(int[] key) {
            this(key, 32);
        }

        public Engine(int[] key, int rounds) {
            if (rounds <= 0)
                throw new IllegalArgumentException();
            if (key.length == 0 || (key.length & 3) != 0)
                throw new IllegalArgumentException();
            this.key = key;
            this.rounds = rounds;
            this.keybits = key.length - 1;
        }

        public void encrypt(int[] data) {
            if (data.length == 0 || (data.length & 1) != 0)
                throw new IllegalArgumentException();

            for (int index = 0; index < data.length; index += 2)
                encrypt(data, index);
        }

        public void encrypt(int[] data, int index) {
            if (data.length < index + 1)
                throw new IllegalArgumentException();

            int sum = 0;
            int v0 = data[index];
            int v1 = data[index + 1];

            for (int round = 0; round < rounds; ++round) {
                v0 += (((v1 << 4) ^ (v1 >>> 5)) + v1) ^ (sum + key[(index + sum) & keybits]);
                sum += delta;
                v1 += (((v0 << 4) ^ (v0 >>> 5)) + v0) ^ (sum + key[(index + (sum >>> 11)) & keybits]);
            }

            data[index] = v0;
            data[index + 1] = v1;
        }

        public void decrypt(int[] data) {
            if (data.length == 0 || (data.length & 1) != 0)
                throw new IllegalArgumentException();

            for (int index = 0; index < data.length; index += 2)
                decrypt(data, index);
        }

        public void decrypt(int[] data, int index) {
            if (data.length < index + 1)
                throw new IllegalArgumentException();

            int sum = delta * rounds;
            int v0 = data[index];
            int v1 = data[index + 1];

            for (int round = 0; round < rounds; ++round) {
                v1 -= (((v0 << 4) ^ (v0 >>> 5)) + v0) ^ (sum + key[(index + (sum >>> 11)) & keybits]);
                sum -= delta;
                v0 -= (((v1 << 4) ^ (v1 >>> 5)) + v1) ^ (sum + key[(index + sum) & keybits]);
            }

            data[index] = v0;
            data[index + 1] = v1;
        }
    }

    private static int alignX(int length, int i) {
        int r = length / i * i;
        if (r != length)
            r += i;
        return r;
    }

    private static int alignInt(int length) {
        int r = length >> 2;
        if ((r << 2) != length)
            r += 1;
        return r;
    }

    private static int[] toIntArray(byte[] data) {
        int[] buffer = new int[alignInt(alignX(data.length + 4, 8))];
        buffer[0] = data.length;
        for (int i = 1; i < buffer.length; i++)
            buffer[i] = makeInt(data, (i - 1) << 2);
        return buffer;
    }

    private static int[] toIntArray2(byte[] data) {
        int[] buffer = new int[alignInt(alignX(data.length, 8))];
        for (int i = 0; i < buffer.length; i++)
            buffer[i] = makeInt(data, i << 2);
        return buffer;
    }

    private static int makeInt(byte[] bytes, int offset) {
        int r = 0;
        for (int n = 0; n < 4; ++n)
            r = (r << 8) | (bytes[offset++ % bytes.length] & 0xff);
        return r;
    }

    private static byte[] toByteArray(int[] buffer) {
        byte[] encrypted = new byte[buffer.length << 2];
        for (int i = 0; i < buffer.length; ++i)
            toByteArray(encrypted, i << 2, buffer[i]);
        return encrypted;
    }

    private static byte[] toByteArray2(int[] buffer) {
        byte[] encrypted = new byte[buffer[0]];
        for (int i = 0; i < encrypted.length; i += 4)
            toByteArray(encrypted, i, buffer[i / 4 + 1]);
        return encrypted;
    }

    private static void toByteArray(byte[] encrypted, int index, int val) {
        encrypted[index++] = (byte) (((val >>> 24)) & 0xff);
        if (index < encrypted.length) {
            encrypted[index++] = (byte) (((val >>> 16)) & 0xff);
            if (index < encrypted.length) {
                encrypted[index++] = (byte) (((val >>> 8)) & 0xff);
                if (index < encrypted.length) {
                    encrypted[index] = (byte) (((val)) & 0xff);
                }
            }
        }
    }
}