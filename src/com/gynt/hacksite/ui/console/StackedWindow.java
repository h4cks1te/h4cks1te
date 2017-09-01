package com.gynt.hacksite.ui.console;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class StackedWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StackedWindow frame = new StackedWindow();
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
	public StackedWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(null);
		contentPane.add(panel, "name_472446315648969");
		panel.setLayout(new BorderLayout(0, 0));

		JButton btnHelloworld = new JButton("helloworld");
		btnHelloworld.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((CardLayout) contentPane.getLayout()).next(contentPane);
			}
		});
		panel.add(btnHelloworld, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		contentPane.add(panel_1, "name_472502144383029");
		panel_1.setLayout(new BorderLayout(0, 0));

		JButton btnHelloworld_1 = new JButton("hello_world2");
		panel_1.add(btnHelloworld_1, BorderLayout.CENTER);
	}
}
