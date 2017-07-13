package steganography;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;
import javax.imageio.*;

import encryption_decryption.AesEncrypt;
import encryption_decryption.AesEncrypt.InvalidAESStreamException;
import encryption_decryption.AesEncrypt.InvalidPasswordException;
import encryption_decryption.AesEncrypt.StrongEncryptionNotAvailableException;

import java.util.*;

public class Main {

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidPasswordException, InvalidAESStreamException, StrongEncryptionNotAvailableException {

		int processors = Runtime.getRuntime().availableProcessors();
		System.out.println("Available Processors: " + processors);

		BufferedImage coverImageText = null;

		try {

			// AesEncrypt test=new AesEncrypt();
			coverImageText = ImageIO.read(new File("test1.jpg"));
			
			String string, password;
			Scanner scan = new Scanner(System.in);
			
			System.out.println("Please enter the sentence that you would like to hide: ");
			string = scan.nextLine();
			
			System.out.println("Please enter your Password in order to encrypt your data: ");
			password = scan.nextLine();

			System.out.println("------------------Embedding Please Wait------------------");
			
			
			//serial Embed Text
			//EmbedTextClass embed = new EmbedTextClass(coverImageText, AesEncrypt.encrypt(string, password.toCharArray()));
			
			
			//parallel Embed Text
			ParallelEmbedding parallelEmbed = new ParallelEmbedding(coverImageText, AesEncrypt.encrypt(string, password.toCharArray()));

			
			System.out.println("Please enter Your password in order to see your secret message");
			password = scan.nextLine();

			
			//serial Extract Text
			ExtractTextClass extract = new ExtractTextClass();
			AesEncrypt.decrypt(password.toCharArray(), extract.extractText(ImageIO.read(new File("textEmbedded.png"))));// extract the secret information
																									

			//parallel Extract Text
			//ParallelExtracting parallelExtract = new ParallelExtracting();
			//AesEncrypt.decrypt(password.toCharArray(), parallelExtract.extractText(ImageIO.read(new File("textEmbedded.png"))));
		} catch (IOException e) {
			System.out.println("Resim Bulunamad�");
		}
	}
}