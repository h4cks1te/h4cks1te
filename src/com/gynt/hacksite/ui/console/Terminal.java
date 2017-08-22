package com.gynt.hacksite.ui.console;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gynt.hacksite.programs.ConsoleProgram;
import com.gynt.hacksite.programs.Program;

public class Terminal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Terminal frame = new Terminal();

					ConsoleProgram base = new ConsoleProgram(frame, true);

					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private List<Integer> acceptable_keys;
	{
		acceptable_keys = new ArrayList<>();
	}

	Pattern pattern = Pattern.compile("[a-zA-Z0-9]");

	private SubscreenLayout subscreenlayout;

	/**
	 * Create the frame.
	 */
	public Terminal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 675, 342);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		subscreenlayout = new SubscreenLayout();
		contentPane.setLayout(subscreenlayout);
		setContentPane(contentPane);
	}

	public TerminalPanel createSubScreen(Program program) {
		TerminalPanel result = new TerminalPanel(program);
		contentPane.add(result, new UUID(new Random().nextLong(), new Random().nextLong()).toString());
		getSubscreenLayout().next(contentPane);
		return result;
	}
	
	public void addSubscreen(Component c, Object constraints) {
		contentPane.add(c, constraints);
	}

	public SubscreenLayout getSubscreenLayout() {
		return subscreenlayout;
	}

}
