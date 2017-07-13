package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class ChooseYourStep extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChooseYourStep frame = new ChooseYourStep();
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
	public ChooseYourStep() {
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
		
		JLabel lblChooseWhatYou = new JLabel("PLEASE SELECT YOUR OPERATION");
		lblChooseWhatYou.setForeground(Color.WHITE);
		lblChooseWhatYou.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblChooseWhatYou.setBounds(371, 144, 843, 85);
		panel.add(lblChooseWhatYou);
		
		JButton btnEncrypt = new JButton("ENCRYPTION");
		btnEncrypt.setFont(new Font("Dialog", Font.BOLD, 15));
		btnEncrypt.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				
				new Encryption().show();
				dispose();

				
			}
		});
		btnEncrypt.setBounds(302, 523, 155, 47);
		panel.add(btnEncrypt);
		
		JButton btnDecrypton = new JButton("DECRYPTION");
		btnDecrypton.setFont(new Font("Dialog", Font.BOLD, 15));
		btnDecrypton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				
				new Decryption().show();
				dispose();
				
			}
		});
		btnDecrypton.setBounds(868, 523, 164, 47);
		panel.add(btnDecrypton);
		
		
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
		label.setBounds(6, -34,1650,1080);
		panel.add(label);
		
		setBounds(0,0,Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);

	}
}

