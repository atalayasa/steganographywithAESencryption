package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class Finish extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Finish frame = new Finish();
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
	public Finish() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1650,1080);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBounds(0, 0, 1650,1080);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblYourTransactionHas = new JLabel("Your transaction has been successfully !");
		lblYourTransactionHas.setForeground(Color.WHITE);
		lblYourTransactionHas.setFont(new Font("Tahoma", Font.BOLD, 50));
		lblYourTransactionHas.setBounds(228, 188, 1011, 99);
		panel.add(lblYourTransactionHas);
		
		JButton btnFnsh = new JButton("FINISH");
		btnFnsh.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnFnsh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnFnsh.setBounds(543, 435, 269, 53);
		panel.add(btnFnsh);
		
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
		label.setBounds(0, 0, 1650,1024);
		panel.add(label);
		
		setBounds(0,0,Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);

	}

}

