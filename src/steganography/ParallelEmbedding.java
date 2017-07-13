package steganography;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;

public class ParallelEmbedding {

	BufferedImage coverImage;
	String sentenceToEmbed;

	public ParallelEmbedding(BufferedImage coverImage, String text) {

		this.coverImage = coverImage;
		this.sentenceToEmbed = text;
		replaceTurkishCharacters(text);

	}

	// türkçe karakterleri ingilizceye çevirerek gizliyoruz
	private void replaceTurkishCharacters(String sentenceToEmbed) {
		sentenceToEmbed = Normalizer.normalize(sentenceToEmbed, Normalizer.Form.NFD);
		sentenceToEmbed = sentenceToEmbed.replaceAll("[\\p{InCombiningDiacriticalMarks}]|", "");
		sentenceToEmbed = sentenceToEmbed.replaceAll("ý", "i");
		embedText(coverImage, sentenceToEmbed);
	}

	// embed secret information/TEXT into a "cover image"
	public BufferedImage embedText(BufferedImage image, String text) {
		int bitMask = 0x00000001; // define the mask bit used to get the digit
		int bit; // define a integer number to represent the ASCII number of a character
		int x = 0; // define the starting pixel x
		int y = 0; // define the starting pixel y

		// bir array tanýmlayacaðýz önce. Bu array elinde bütün yazý bitlerini
		// barýndýracak.
		// bütün bitleri bir array e bindireceðiz, o arrayin indexinden iki
		// index üreteceðiz ve
		// o iki index üzerinden resime yazdýracaðýz

		int[] bits = new int[text.length() * 8];
		int[] index_x = new int[text.length() * 8];
		int[] index_y = new int[text.length() * 8];

		for (int i = 0; i < text.length(); i++) {
			bit = (int) text.charAt(i); // get the ASCII number of a character
			for (int j = 0; j < 8; j++) {
				bits[i * 8 + j] = bit;

				if (x < image.getWidth()) {
					index_x[i * 8 + j] = x;
					index_y[i * 8 + j] = y;
					x++;
				} else {
					x = 0;
					y++;
					index_x[i * 8 + j] = x;
					index_y[i * 8 + j] = y;
					x++;
				}

				bit = bit >> 1;
			}//end of for j
		}//end of for i
		
		AtomicInteger done = new AtomicInteger(0);
		for (AtomicInteger i = new AtomicInteger(0); i.get() < text.length(); ) {
			int i_t =  i.getAndIncrement();
			
			(new Thread() {
				public void run() {
					for (int j = 0; j < 8; j++) {
						int ordered_index = i_t * 8 + j;
						//System.out.println(ordered_index + " iÃ§in x " + index_x[ordered_index] + " ve y "+ index_y[ordered_index] +" olur");
						
						int bit = bits[ordered_index];
						int flag = bit & bitMask; // gÃ¶mmemiz gereken bit get 1
													// digit
													// from the character a=65
													// 10000001
													// 00000001 00000011
						if (flag == 1) {
								image.setRGB(index_x[ordered_index], index_y[ordered_index],
										image.getRGB(index_x[ordered_index], index_y[ordered_index]) | 0x00000001);	
							
						} else { // mask ile mesela x=120 ASCII binary =
									// 01111000 120
								image.setRGB(index_x[ordered_index], index_y[ordered_index],
										image.getRGB(index_x[ordered_index], index_y[ordered_index]) & 0xFFFFFFFE); // store	
							
						}
					}
					done.getAndIncrement();
				}
			}).start();
		}

		// save the stego image as...
		try {
			File outputfile = new File("textEmbedded.png");
			ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {

		}
		return image;
	}

}
