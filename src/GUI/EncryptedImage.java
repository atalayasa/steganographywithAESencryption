package GUI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class EncryptedImage extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EncryptedImage frame = new EncryptedImage();
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
	public EncryptedImage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1650, 1080);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1650, 1080);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblHereIsYour = new JLabel("Here is your orjinal image :");
		lblHereIsYour.setForeground(Color.WHITE);
		lblHereIsYour.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblHereIsYour.setBounds(20, 45, 405, 27);
		panel.add(lblHereIsYour);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 83, 600, 500);
		panel.add(scrollPane);

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(Encryption.newimage));
		scrollPane.setViewportView(label_1);

		JLabel lblHereIsYour_1 = new JLabel("Here is your encrypted image:");
		lblHereIsYour_1.setForeground(Color.WHITE);
		lblHereIsYour_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblHereIsYour_1.setBounds(701, 45, 415, 27);
		panel.add(lblHereIsYour_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(650, 83, 600, 500);
		panel.add(scrollPane_1);

		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon("/Users/atalayasa/Documents/workspace/SteganographyEnd/textEmbedded.png"));
		scrollPane_1.setViewportView(label_2);

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
		
		JButton btnOkey = new JButton("OK");
		btnOkey.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnOkey.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				new Finish().show();
				dispose();
			}
		});
		btnOkey.setBounds(631, 673, 96, 35);
		panel.add(btnOkey);

		JLabel label = new JLabel("");
		label.setForeground(Color.BLACK);
		label.setIcon(new ImageIcon("/Users/atalayasa/Documents/workspace/SteganographyEnd/steg.jpeg"));
		label.setBounds(0, -33, 1650, 1080);
		panel.add(label);

		setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height);

	}
}
