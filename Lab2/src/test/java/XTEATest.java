import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class XTEATest {

    @Test
    public void test1() {
        XTEA tea = new XTEA("special key");
        byte[] encryptString = tea.encryptString("Test text");
        String decrypt = tea.decryptString(encryptString);
        Assert.assertEquals("Test text", decrypt);
    }

    @Test
    public void test2() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        XTEA tea = new XTEA("special key");

        for(int i = 0; i < 100; i++) {
            StringBuilder sb = new StringBuilder();

            int length = random.nextInt(100000);
            int index = 0;
            char randomChar;
            for (int j = 0; j < length; j++) {
                index = random.nextInt(alphabet.length());
                randomChar = alphabet.charAt(index);
                sb.append(randomChar);
            }

            byte[] encryptString = tea.encryptString(sb.toString());
            String decrypt = tea.decryptString(encryptString);
            Assert.assertEquals(sb.toString(), decrypt);
        }
    }
}