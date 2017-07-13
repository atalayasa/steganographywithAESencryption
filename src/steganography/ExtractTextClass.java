package steganography;

import java.awt.image.BufferedImage;

public class ExtractTextClass {

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
		while (cs[i] != a) {
			int bit = 0;
			i++;
			// 8 digits form a character
			for (int j = 0; j < 8; j++) {
				if (x < image.getWidth()) {
					flag = image.getRGB(x, y) & bitMask; // get the last digit of the pixel
					x++;
				} else {
					x = 0;
					y++;
					flag = image.getRGB(x, y) & bitMask; // get the last digit of the pixel
					x++;
				}

				// store the extracted digits into an integer as a ASCII number
				if (flag == 1) {
					bit = bit >> 1;
					bit = bit | 0x80;
				} else {
					bit = bit >> 1;
				}
			}

			cs[i] = (char) bit;

			if (cs[i] != a) {
				// represent the ASCII number by characters
				text = text + (char) bit;
				//System.out.print(cs[i]);
			}
		}
		return text;
	}

}