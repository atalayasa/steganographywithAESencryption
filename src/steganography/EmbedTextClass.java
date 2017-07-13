package steganography;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.Normalizer;

import javax.imageio.ImageIO;

public class EmbedTextClass {

	BufferedImage coverImage;
	String sentenceToEmbed;

	public EmbedTextClass(BufferedImage coverImage, String text) {

		this.coverImage = coverImage;
		this.sentenceToEmbed = text;
		replaceTurkishCharacters(text);

	}

	//türkçe karakterleri ingilizceye çevirerek gizliyoruz
	private void replaceTurkishCharacters(String sentenceToEmbed) { 
		sentenceToEmbed = Normalizer.normalize(sentenceToEmbed, Normalizer.Form.NFD);
		sentenceToEmbed = sentenceToEmbed.replaceAll("[\\p{InCombiningDiacriticalMarks}]|", "");
		sentenceToEmbed = sentenceToEmbed.replaceAll("ý", "i");
		embedText(coverImage, sentenceToEmbed);
	}

	// embed secret information/TEXT into a "cover image"
	public static BufferedImage embedText(BufferedImage image, String text) {
		int bitMask = 0x00000001; // define the mask bit used to get the digit
		int bit; // define a integer number to represent the ASCII number of a character
		int x = 0; // define the starting pixel x
		int y = 0; // define the starting pixel y

		for (int i = 0; i < text.length(); i++) {
			bit = (int) text.charAt(i); // get the ASCII number of a character
			for (int j = 0; j < 8; j++) {
				int flag = bit & bitMask; // gömmemiz gereken bit get 1 digit
											// from the character a=65 10000001
											// 00000001 00000011
				if (flag == 1) {
					if (x < image.getWidth()) {
						image.setRGB(x, y, image.getRGB(x, y) | 0x00000001); // store the bit which is 1 into a pixel's last digit
						x++;
					} else {
						x = 0;
						y++;
						image.setRGB(x, y, image.getRGB(x, y) | 0x00000001); // store the bit which is 1 into a pixel's last digit
						x++;
					}
				} else { // mask ile mesela x=120 ASCII binary = 01111000 120 &
							// 1 = 0
					if (x < image.getWidth()) {
						image.setRGB(x, y, image.getRGB(x, y) & 0xFFFFFFFE); // store the bit which is 0 into a pixel's last digit
						x++;
					} else {
						x = 0;
						y++;
						image.setRGB(x, y, image.getRGB(x, y) & 0xFFFFFFFE); // store the bit which is 0 into a pixel's last digit
						x++;
					}
				}
				bit = bit >> 1; // get the next digit from the character
			}
		}

		// save the image which contains the secret information to another image file
		try {
			File outputfile = new File("textEmbedded.png");
			ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {

		}
		return image;
	}
	
	//multithreading
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
