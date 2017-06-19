import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * Created by Stijn on 18-6-2017.
 */
public class GenerateSignedOutputFile {

    /**
     * Private RSA key is read from disk.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            String textFromFile = readInputFile();

            File privateRSAKeyFile = new File("C:/Users/Stijn/Documents/GitHub/FHICT-SE42/Encryption/PrivateRSAKey");
            FileInputStream fileInputStream = new FileInputStream(privateRSAKeyFile);
            ObjectInputStream objectInputStreamPrivateKey = new ObjectInputStream(fileInputStream);
            PrivateKey privateKey = (PrivateKey) objectInputStreamPrivateKey.readObject();

            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(privateKey);
            byte[] signatureBytes = signature.sign();

            String fileName = "";
            while(fileName.isEmpty()) {
                System.out.println("What is your name?");
                fileName = new BufferedReader(new InputStreamReader(System.in)).readLine();
            }

            File signedFile = new File(String.format("LYRICS(SignedBy%s)", fileName.replace(" ", "")));
            signedFile.createNewFile();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(signedFile));
            objectOutputStream.writeInt(signatureBytes.length);
            objectOutputStream.writeObject(signatureBytes);
            objectOutputStream.writeObject(textFromFile);
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
    }

    private static String readInputFile() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("LYRICS.txt"));
        String inputText = "";
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
                line =  bufferedReader.readLine();
            }
            inputText = stringBuilder.toString();
        } finally {
            bufferedReader.close();
        }

        return inputText;
    }
}
