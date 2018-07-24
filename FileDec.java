import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
public class FileDec {

    public static byte[] decryptFile(SecretKey key, byte[] textCryp) {
        Cipher cipher;
        byte[] decrypted = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypted = cipher.doFinal(textCryp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decrypted;
    }

    public static void saveFile(byte[] bytes,String name) throws IOException {

        FileOutputStream fos = new FileOutputStream("/home/rajdeep/Desktop/Coding/Cryptography/"+name);
        fos.write(bytes);
        fos.close();

    }

    public static byte[] readFile(String name) throws IOException {

        File file = new File("/home/rajdeep/Desktop/Coding/Cryptography/"+name);
        FileInputStream fos = new FileInputStream(file);

        byte fileContent[] = new byte[(int)file.length()];
        fos.read(fileContent);
        fos.close();
        return fileContent;

    }

    public static void main(String args[])
            throws NoSuchAlgorithmException, InstantiationException, IllegalAccessException, IOException {

        //for decryption encrypted pic name and after decryption file name        

        SecretKeySpec secretKey;
        byte[] key;
        String myKey = "ThisIsAStrongPasswordForEncryptionAndDecryption";

        MessageDigest sha = null;
        key = myKey.getBytes("UTF-8");
        System.out.println(key.length);
        sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); // use only first 128 bit
        System.out.println(key.length);
        System.out.println(new String(key, "UTF-8"));
        secretKey = new SecretKeySpec(key, "AES");

        byte[] encrypted=readFile(args[0]);
        byte[] decrypted = decryptFile(secretKey, encrypted);
        System.out.println(decrypted);

        saveFile(decrypted,args[1]);
        System.out.println("Done");

    }

}