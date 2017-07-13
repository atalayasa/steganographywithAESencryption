package encryption_decryption;

import java.security.InvalidAlgorithmParameterException;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Derive an AES Encryption Key and Authentication Key from Given Password and
 * Salt, using PBKDF2 key stretching.The Authentication key is 64 bits long.
 * keyLength length of the AES key in bits (128, 192, or 256)
 * 
 * @param password
 *            the password from which to derive the keys
 * @param salt
 *            the salt from which to derive the keys
 * @return a Keys object containing the two generated keys
 */

public class AesEncrypt {

	private static final String CIPHER_SPEC = "AES/CBC/PKCS5Padding";// AES Specification(Cipher Block Chaining) with PKCS5PAdding
	private static final String KEYGEN_SPEC = "PBKDF2WithHmacSHA256";
	private static final int SALT_LENGTH = 16; // in bytes
	private static final int AUTH_KEY_LENGTH = 16;// in bytes
	private static final int ITERATIONS = 65352;
	
	public static String textForGUI;
	
	private static class Keys {
		public final SecretKey encryption, authentication;

		public Keys(SecretKey encryption, SecretKey authentication) {
			this.encryption = encryption;
			this.authentication = authentication;

		}
	}

	private static Keys keygen(char[] password, int keyLength, byte[] salt) {
		SecretKeyFactory factory;

		try {

			factory = SecretKeyFactory.getInstance(KEYGEN_SPEC);// PBKDF2WithHmacSHA256

		} catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			return null;
		}

		// Deriving longer key, then split into AES key and authentication key
		KeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, keyLength + AUTH_KEY_LENGTH * 8);// "String"
		SecretKey tmp = null;

		try {
			tmp = factory.generateSecret(spec);
		} catch (InvalidKeySpecException impossible) {
			
		}

		byte[] fullKey = tmp.getEncoded();
		
		// This is the key for authentication(also ensuring Message Integrity)
		SecretKey authKey = new SecretKeySpec(Arrays.copyOfRange(fullKey, 0, AUTH_KEY_LENGTH), "AES");

		SecretKey encKey = new SecretKeySpec(Arrays.copyOfRange(fullKey, AUTH_KEY_LENGTH, fullKey.length), "AES");

		System.out.print("Your Authentication key is: ");

		for (int i = 0; i < authKey.getEncoded().length; i++) {

			System.out.print(authKey.getEncoded()[i] + "  ");
		}
		System.out.println();
		System.out.print("Your Encryption Key is: ");

		for (int i = 0; i < encKey.getEncoded().length; i++) {

			System.out.print(encKey.getEncoded()[i] + "   ");
		}
		System.out.println();
		return new Keys(encKey, authKey);
	}

	public static String encrypt(String stringToEncrypt, char[] password)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {

		// generate salt and derive keys for authentication and encryption
		byte[] salt = generateSecureRandomValue(SALT_LENGTH);// 16
		Keys keys = keygen(password, 128, salt);// "String,128,16 bitlik SecureRandom bits."

		// Get instance of Aes/CBC/PKCS5Padding Cipher
		Cipher aesCipher = null;
		aesCipher = Cipher.getInstance(CIPHER_SPEC);// "AES/CBC/PKCS5PAdding"

		// get SecureRandom initialization vector
		byte[] iv = new byte[aesCipher.getBlockSize()];
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.nextBytes(iv);

		try {
			aesCipher.init(Cipher.ENCRYPT_MODE, keys.encryption, new IvParameterSpec(iv));// Initializing the Aes Encryption
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] byteCipherText = null;
		
		try {

			byteCipherText = aesCipher.doFinal(stringToEncrypt.getBytes()); // Given string will be encrypted
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Mac m = Mac.getInstance("HMACSHA256");
		m.init(keys.authentication);
		byte[] hmacEncrypted = null;

		hmacEncrypted = m.doFinal(byteCipherText);

		byte[] ultimateByteArray = new byte[hmacEncrypted.length + iv.length + salt.length + byteCipherText.length];
		System.arraycopy(salt, 0, ultimateByteArray, 0, 16);
		System.arraycopy(iv, 0, ultimateByteArray, 16, 16);
		System.arraycopy(byteCipherText, 0, ultimateByteArray, 32, byteCipherText.length);
		System.arraycopy(hmacEncrypted, 0, ultimateByteArray, 32 + byteCipherText.length, hmacEncrypted.length);

		String encryptedPackage = new String(Base64.getEncoder().encodeToString(ultimateByteArray)) + "!";
		System.out.println("Size of last version of String is  " + encryptedPackage.length()
				+ "   And the size of given String is " + stringToEncrypt.length());

		System.out.println("Last version of data is " + encryptedPackage);
		System.out.println();

		System.out.print("IV is equal to ");
		for (int i = 0; i < iv.length; i++) {

			System.out.print(iv[i] + "  ");

		}
		System.out.println();

		System.out.println();
		System.out.print("Salt is equal to ");
		for (int i = 0; i < salt.length; i++) {

			System.out.print(salt[i] + "  ");

		}
		System.out.println();
		System.out.println();
		System.out.print("Our String's byte array components are  ");
		for (int i = 0; i < stringToEncrypt.getBytes().length; i++) {

			System.out.print(stringToEncrypt.getBytes()[i] + "  ");

		}
		System.out.println();
		System.out.println();
		System.out.print("Cipher text Array is equal to ");

		for (int i = 0; i < byteCipherText.length; i++) {

			System.out.print(byteCipherText[i] + "  ");

		}
		System.out.println();

		return encryptedPackage;

	}

	public static void decrypt(char[] password, String encryptedText)
			throws InvalidPasswordException, InvalidAESStreamException, StrongEncryptionNotAvailableException,
			NoSuchAlgorithmException, InvalidKeyException {

		byte[] wholeString = Base64.getDecoder().decode(encryptedText);
		byte[] salt = Arrays.copyOfRange(wholeString, 0, 16);
		byte[] iv = Arrays.copyOfRange(wholeString, 16, 32);
		byte[] cipherText = Arrays.copyOfRange(wholeString, 32, wholeString.length - 32);
		byte[] firsthmac = Arrays.copyOfRange(wholeString, wholeString.length - 32, wholeString.length);

		Keys keys = keygen(password, 128, salt);

		SecretKeySpec secretKeySpec = new SecretKeySpec(keys.authentication.getEncoded(), "HmacSHA256");
		Mac hMac = Mac.getInstance("HmacSHA256");

		hMac.init(secretKeySpec);
		byte[] chmac = hMac.doFinal(cipherText);
		if (MessageDigest.isEqual(firsthmac, chmac)) {

			Cipher decrypt = null;

			// If needed we can give Encryption Key by Hand.
			try {

				decrypt = Cipher.getInstance(CIPHER_SPEC);
				decrypt.init(Cipher.DECRYPT_MODE, keys.encryption, new IvParameterSpec(iv));

			} catch (NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException impossible) {
			} catch (InvalidKeyException e) { // 192 or 256-bit AES not available
				throw new StrongEncryptionNotAvailableException(128);
			}

			// read data from input decrypt and write to output
			byte[] decrypted;
			try {
				decrypted = decrypt.doFinal(cipherText);
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				throw new InvalidAESStreamException(e);
			}
			if (decrypted != null) {
				String ultimateString = new String(decrypted);
				System.out.println("Your Decrypted Data is: " + ultimateString);
				
				textForGUI = ultimateString;
			}

		}
	}

	// Generating new Pseudo Random Salt of Specified Length.
	private static byte[] generateSecureRandomValue(int length) {

		SecureRandom randomSalt = new SecureRandom();
		byte[] secureRandom = new byte[length];
		randomSalt.nextBytes(secureRandom);
		return secureRandom;
	}

	// ******** EXCEPTIONS thrown by encrypt and decrypt ********

	
	//Thrown if an attempt is made to decrypt a stream with an incorrect password.
	 

	public static class InvalidPasswordException extends Exception {

		private static final long serialVersionUID = 1L;
	}

	
	// Thrown if an attempt is made to encrypt a stream with an invalid AES key length.
	 
	public static class InvalidKeyLengthException extends Exception {
		
		private static final long serialVersionUID = 1L;

		InvalidKeyLengthException(int length) {
			super("Invalid AES key length: " + length);
		}
	}

	// Thrown if 192- or 256-bit AES encryption or decryption is attempted, but not available on the particular Java platform.
	 
	public static class StrongEncryptionNotAvailableException extends Exception {
		
		private static final long serialVersionUID = 1L;

		public StrongEncryptionNotAvailableException(int keySize) {
			super(keySize + "-bit AES encryption is not available on this Java platform.");
		}
	}

	//Thrown if an attempt is made to decrypt an invalid AES stream.
	 
	public static class InvalidAESStreamException extends Exception {
		
		private static final long serialVersionUID = 1L;

		public InvalidAESStreamException() {
			super();
		};

		public InvalidAESStreamException(Exception e) {
			super(e);
		}
	}

}
