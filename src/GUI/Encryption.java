package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import encryption_decryption.AesEncrypt;
import steganography.EmbedTextClass;

@SuppressWarnings("serial")
public class Encryption extends JFrame {

	protected static final BufferedImage BufferedImage = null;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;

	public static BufferedImage newimage;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Encryption frame = new Encryption();
					frame.setSize(1650,1080);

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
	public Encryption() {
		
		
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
		
		JLabel lblPleaseAttachAn = new JLabel("Please attach an image.");
		lblPleaseAttachAn.setForeground(Color.WHITE);
		lblPleaseAttachAn.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPleaseAttachAn.setBounds(404, 177, 431, 34);
		panel.add(lblPleaseAttachAn);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBackground(Color.LIGHT_GRAY);
		textField.setBounds(404, 243, 548, 34);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("ATTACH");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooser = new JFileChooser(System.getProperty("user.home")+"/workspace/SteganographyNew");
				FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("Only Image Files",
						ImageIO.getReaderFileSuffixes());
				chooser.setFileFilter(fileNameExtensionFilter);
				File f = chooser.getSelectedFile();
				String filename ;
				try {
					int fileChooseInt = chooser.showOpenDialog(null);
					if (fileChooseInt == JFileChooser.APPROVE_OPTION) {
						f = chooser.getSelectedFile();
						filename = f.getAbsolutePath();
						textField.setText(filename);
					}
					newimage = ImageIO.read(new File(textField.getText()));
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Please Select an image", "Warning", JOptionPane.ERROR_MESSAGE);
					 e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(863, 181, 89, 34);
		panel.add(btnNewButton);
		
		JLabel lblPleaseAttachA = new JLabel("Please write your message that you want to encrypt.");
		lblPleaseAttachA.setForeground(Color.WHITE);
		lblPleaseAttachA.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPleaseAttachA.setBounds(404, 308, 566, 34);
		panel.add(lblPleaseAttachA);
		
		
		JButton btnEncrypt = new JButton("ENCRYPT");
		btnEncrypt.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				
				//Encryption i�leminin yap�ld��� fonksiyon �a��r�lacak
			
				try {
					EmbedTextClass embed = new EmbedTextClass(newimage, AesEncrypt.encrypt(textField_1.getText(), passwordField.getText().toCharArray()));
				} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//Encrption yap�ld�ktan sonra
				new EncryptedImage().show();
				dispose();
				
			}
		});
		btnEncrypt.setBounds(641, 634, 108, 34);
		panel.add(btnEncrypt);
		
		textField_1 = new JTextField();
		textField_1.setBounds(404, 383, 548, 34);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblPleaseWriteYour = new JLabel("Please write your password");
		lblPleaseWriteYour.setForeground(Color.WHITE);
		lblPleaseWriteYour.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPleaseWriteYour.setBounds(404, 442, 548, 34);
		panel.add(lblPleaseWriteYour);
		
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		
		passwordField.setBounds(404, 503, 548, 34);
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
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("/Users/atalayasa/Documents/workspace/SteganographyEnd/steg.jpeg"));
		label.setBounds(0, -33, 1650,1080);
		panel.add(label);
		
	
		
		
		setBounds(0,0,Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);

		
	}
}

