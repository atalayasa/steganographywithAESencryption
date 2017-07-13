package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import encryption_decryption.AesEncrypt;
import encryption_decryption.AesEncrypt.InvalidAESStreamException;
import encryption_decryption.AesEncrypt.InvalidPasswordException;
import encryption_decryption.AesEncrypt.StrongEncryptionNotAvailableException;
import steganography.ExtractTextClass;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class Decryption extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	// private JTextField textField_1;
	private JTextField textField_2;
	private JPasswordField passwordField;
	private static String filename;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Decryption frame = new Decryption();
					frame.setSize(1650, 1080);
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Decryption() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1650, 1080);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBounds(0, 0, 1650, 1080);
		contentPane.add(panel);
		panel.setLayout(null);

		passwordField = new JPasswordField();
		JLabel lblPleaseAttachAn = new JLabel("Please attach an encrypted image!");
		lblPleaseAttachAn.setForeground(Color.WHITE);
		lblPleaseAttachAn.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPleaseAttachAn.setBounds(359, 112, 489, 24);
		panel.add(lblPleaseAttachAn);

		textField = new JTextField();
		textField.setBounds(359, 162, 610, 36);
		panel.add(textField);
		textField.setColumns(10);
		textField.setEditable(false);
		textField.setBackground(Color.LIGHT_GRAY);
		JButton btnAttach = new JButton("ATTACH");
		btnAttach.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnAttach.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// JFileChooser chooser = new
				// JFileChooser(System.getProperty("user.home")+"/workspace/SteganographyNew");
				// chooser.showOpenDialog(null);
				// File f = chooser.getSelectedFile();
				// String filename = f.getAbsolutePath();
				// textField.setText(filename);

				// yeni

				JFileChooser chooser = new JFileChooser(
						System.getProperty("user.home") + "/workspace/SteganographyNew");
				FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("Only Image Files",
						ImageIO.getReaderFileSuffixes());
				chooser.setFileFilter(fileNameExtensionFilter);
				File f = chooser.getSelectedFile();

				int fileChooseInt = chooser.showOpenDialog(null);
				if (fileChooseInt == JFileChooser.APPROVE_OPTION) {
					f = chooser.getSelectedFile();
					filename = f.getAbsolutePath();
					textField.setText(filename);
				} else {
					JOptionPane.showMessageDialog(null, "Please Select an image", "Warning", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAttach.setBounds(858, 114, 99, 26);
		panel.add(btnAttach);

		JButton btnDecrypt = new JButton("DECRYPT");
		btnDecrypt.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDecrypt.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {

				// Decryption i�leminin yap�ld��� method �a�r�lacak

				ExtractTextClass extract = new ExtractTextClass();
				try {
					if (passwordField.getPassword().length != 0) {
						AesEncrypt.decrypt(passwordField.getText().toCharArray(),
							extract.extractText(ImageIO.read(new File(filename))));
						if (AesEncrypt.textForGUI != null) {
							textField_2.setText(AesEncrypt.textForGUI);	
							UIManager.put("OptionPane.minimumSize", new Dimension(300, 300));
							// JOptionPane.showMessageDialog(null, "Your Decrypted
							// Message is\n"+ " \""+ AesEncrypt.textForGUI +"\"",
							// "DECRYPTION", 2);
							JOptionPane.showMessageDialog(null, "<html><center>"
									+ ("Your Decrypted Message is\n\"" + AesEncrypt.textForGUI + "\"").replaceAll("\\n", "<br>")
									+ "</center></html>", "DECRYPTION", 2);
						}
						else{
							JOptionPane.showMessageDialog(null, "Invalid Password", "Warning", JOptionPane.ERROR_MESSAGE);
						} 
					
					}/* else {
						JOptionPane.showMessageDialog(null, "Invalid Password", "Warning", JOptionPane.ERROR_MESSAGE);
					}
					*/
				} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidPasswordException
						| InvalidAESStreamException | StrongEncryptionNotAvailableException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnDecrypt.setBounds(614, 359, 117, 36);
		panel.add(btnDecrypt);

		JLabel lblPleaseWriteYour = new JLabel("Please write your password");
		lblPleaseWriteYour.setForeground(Color.WHITE);
		lblPleaseWriteYour.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPleaseWriteYour.setBounds(359, 235, 389, 26);
		panel.add(lblPleaseWriteYour);

		// textField_1 = new JTextField();
		// textField_1.setBounds(359, 290, 610, 36);
		// panel.add(textField_1);
		// textField_1.setColumns(10);

		passwordField.setBounds(359, 290, 610, 36);
		panel.add(passwordField);
		passwordField.setColumns(10);

		JButton btnBack = new JButton("");
		btnBack.setBackground(Color.BLACK);
		btnBack.setForeground(Color.black);
		btnBack.setIcon(new ImageIcon("/Users/atalayasa/Documents/workspace/SteganographyEnd/button/back.png"));
		btnBack.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				
				new ChooseYourStep().show();
				dispose();
				
			}
		});
		btnBack.setBounds(27, 16, 36, 41);
		panel.add(btnBack);
		

		JButton btnExt = new JButton("");
		btnExt.setBackground(Color.BLACK);
		btnExt.setIcon(new ImageIcon("/Users/atalayasa/Documents/workspace/SteganographyEnd/button/cancel.png"));
		btnExt.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				
			}
		});
		btnExt.setBounds(1184, 16, 30, 30);
		panel.add(btnExt);

		JLabel lblHereIsYour = new JLabel("Here is your secret message :");
		lblHereIsYour.setForeground(Color.WHITE);
		lblHereIsYour.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblHereIsYour.setBounds(359, 492, 498, 23);
		panel.add(lblHereIsYour);

		textField_2 = new JTextField();
		textField_2.setBounds(359, 537, 474, 108);
		panel.add(textField_2);
		textField_2.setColumns(10);
		textField_2.setEditable(false);

		JButton btnFnsh = new JButton("FINISH");
		btnFnsh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Finish().show();
				dispose();

			}
		});
		btnFnsh.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnFnsh.setBounds(858, 572, 102, 36);
		panel.add(btnFnsh);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("/Users/atalayasa/Documents/workspace/SteganographyEnd/steg.jpeg"));
		label.setBounds(0, -33, 1650, 1080);
		panel.add(label);

		setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height);

	}
}
