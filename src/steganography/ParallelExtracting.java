package steganography;

import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelExtracting {

	BufferedImage textEmbedded;
	private static char[] cs;
	
	// extract secret information/Text from a "cover image"
	public static String extractText(BufferedImage image) {
		System.out.print("Extracting: ");
		int buffer = (image.getHeight() * image.getWidth()) / 8;
		int bitMask = 0x00000001; // define the mask bit used to get the digit
		int x = 0; // define the starting pixel x
		int y = 0; // define the starting pixel y
		int flag;
		int i = 0;
		String pad = "!"; //nerede duracaðýný gösteriyor
		String text = "";
		char a = pad.charAt(0);
		cs = new char[buffer];
		
		int[] bits = new int[text.length() * 8];
		int[] index_x = new int[text.length() * 8];
		int[] index_y = new int[text.length() * 8];
		int bit = 0;
		while (cs[i] != a) {
			
			i++;
			for(int j = 0; j <8; j++){
				if(x < image.getWidth()){
					index_x[i * 8 + j] = x;//
					index_y[i * 8 + j] = y;
					x++;
				} else{
					x = 0;
					y++;
					index_x[i * 8 + j] = x;
					index_y[i * 8 + j] = y;
					x++;

				}
				
				bit = bit >> 1;

			}//end of for
		}//end of while
		
		AtomicInteger done = new AtomicInteger(0);
		for (AtomicInteger ai = new AtomicInteger(0); ai.get() < text.length(); ) {
			int i_t =  ai.getAndIncrement();
			
			(new Thread() {
				public void run() {
					for (int j = 0; j < 8; j++) {
						int ordered_index = i_t * 8 + j;
						//System.out.println(ordered_index + " için x " + index_x[ordered_index] + " ve y "+ index_y[ordered_index] +" olur");
						
						int bit = bits[ordered_index];
						int flag = bit | bitMask; // gömmemiz gereken bit get 1
													// digit
													// from the character a=65
													// 10000001
													// 00000001 00000011
						if (flag == 1) {
								image.getRGB(index_x[ordered_index], index_y[ordered_index] & 0x00000001);	
							
						} else { // mask ile mesela x=120 ASCII binary =
									// 01111000 120
					
										image.getRGB(index_x[ordered_index], index_y[ordered_index] | 0xFFFFFFFE); // store	
							
						}
					}
					done.getAndIncrement();
				}
			}).start();
		}

		cs[i] = (char) bit;

		if (cs[i] != a) {
			// represent the ASCII number by characters
			text = text + (char) bit;
			//System.out.print(cs[i]);
		}
	
		return text;
	}

}