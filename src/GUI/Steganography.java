package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Steganography extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Steganography frame = new Steganography();
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
	public Steganography() {
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1650, 1080);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBounds(0, 0, 1650,1080);
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(null);

		
		JLabel lblWelcomeToSteganograpy = new JLabel("WELCOME TO STEGANOGRAPHY");
		lblWelcomeToSteganograpy.setForeground(Color.WHITE);
		lblWelcomeToSteganograpy.setFont(new Font("Tahoma", Font.BOLD, 50));
		lblWelcomeToSteganograpy.setBackground(Color.BLACK);
		lblWelcomeToSteganograpy.setBounds(240, 114, 881, 171);
		panel.add(lblWelcomeToSteganograpy);
		
		JButton btnStart = new JButton("PRESS HERE FOR STARTING");
		btnStart.setOpaque(false);
		btnStart.setForeground(Color.BLACK);
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnStart.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				
				new ChooseYourStep().show();
				dispose();
			}
		});
		btnStart.setBounds(514, 493, 256, 62);
		panel.add(btnStart);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("/Users/atalayasa/Documents/workspace/SteganographyEnd/steg.jpeg"));
		label.setBounds(0, -40, 1650, 1080);
		panel.add(label);
		
		
		setBounds(0,0,Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);

		
	}
}
