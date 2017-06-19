import java.io.*;
import java.security.*;

/**
 * Created by Stijn on 19-6-2017.
 */
public class VerifySignature {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        boolean signatureIsValid = false;

        try {
            String fileName = "";

            while (fileName.isEmpty()) {
                System.out.println("What is your name?");
                fileName = new BufferedReader(new InputStreamReader(System.in)).readLine();
            }

            File publicRSAKeyFile = new File("C:/Users/Stijn/Documents/GitHub/FHICT-SE42/Encryption/PublicRSAKey");
            FileInputStream fileInputStreamKey = new FileInputStream(publicRSAKeyFile);
            ObjectInputStream objectInputStreamPublicKey = new ObjectInputStream(fileInputStreamKey);
            PublicKey publicKey = (PublicKey) objectInputStreamPublicKey.readObject();

            FileInputStream fileInputStream = new FileInputStream("C:/Users/Stijn/Documents/GitHub/FHICT-SE42/EncryptionClient/" + String.format("LYRICS(SignedBy%s)", fileName.replace(" ", "")));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            int signatureSize = objectInputStream.readInt();
            byte[] signatureBytes = (byte[]) objectInputStream.readObject();
            String inputText = (String) objectInputStream.readObject();

            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(publicKey);
            signatureIsValid = signature.verify(signatureBytes);

            if(signatureIsValid) {
                System.out.println("Signature is valid!");
                System.out.print(inputText);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
    }
}
