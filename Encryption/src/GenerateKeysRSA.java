import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.*;

/**
 * Created by Stijn on 18-6-2017.
 */
public class GenerateKeysRSA {
    /**
     * Generate two keys one public and one private.
     * Both keys are generated using the RSA Algorithm and eventually will be saved to disk.
     * @param args Arguments given by the user.
     */
    public static void main(String[] args) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024, new SecureRandom());
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey rsaPublicKey = keyPair.getPublic();
            PrivateKey rsaPrivateKey = keyPair.getPrivate();

            FileOutputStream fileOutputStreamPublicKey = new FileOutputStream("PublicRSAKey");
            ObjectOutputStream objectOutputStreamPublicKey = new ObjectOutputStream(fileOutputStreamPublicKey);
            objectOutputStreamPublicKey.writeObject(rsaPublicKey);

            FileOutputStream fileOutputStreamPrivateKey = new FileOutputStream("PrivateRSAKey");
            ObjectOutputStream objectOutputStreamPrivateKey = new ObjectOutputStream(fileOutputStreamPrivateKey);
            objectOutputStreamPrivateKey.writeObject(rsaPrivateKey);

            fileOutputStreamPublicKey.close();
            objectOutputStreamPublicKey.close();
            fileOutputStreamPrivateKey.close();
            objectOutputStreamPrivateKey.close();

            System.out.println("Public Key :" + rsaPublicKey);
            System.out.println("Private Key :" + rsaPrivateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}