package com.gynt.hacksite.ui.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Terminal extends JFrame {

	private enum OsMode {
		WINDOWS, LINUX, OSX;
	}

	private final OsMode MODE = OsMode.WINDOWS;

	private JPanel contentPane;
	private JEditorPane dtrpnfranktriangle;
	private String priortext;
	private String header;
	private String newtext;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Terminal frame = new Terminal();
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

	/**
	 * Create the frame.
	 */
	public Terminal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 675, 342);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		switch (MODE) {
		case LINUX:
			header = "[frank@triangle ~]$ ";
			break;
		case OSX:
			break;
		case WINDOWS:
			header = "s1234567@WORKSPACE.COM> ";
			break;
		default:
			break;

		}

		priortext = header;
		newtext = "";

		dtrpnfranktriangle = new JEditorPane();
		// dtrpnfranktriangle.setKeymap(null);
		dtrpnfranktriangle.setEditable(true);
		dtrpnfranktriangle.setBackground(Color.BLACK);
		dtrpnfranktriangle.setForeground(Color.WHITE);
		dtrpnfranktriangle.setText(header);
		switch (MODE) {
		case LINUX:

			dtrpnfranktriangle.setFont(new Font("Monospaced", Font.PLAIN, 12));
			break;
		case OSX:
			break;
		case WINDOWS:
			dtrpnfranktriangle.setFont(new Font("Lucida Console", Font.BOLD, 12));
			break;
		default:
			break;

		}

		dtrpnfranktriangle.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// keyPressed(e);
				if (Pattern.matches("[A-Za-z0-9 !?@#$%^&*()_+{}|\":?><]", e.getKeyChar() + "")) {
					newtext += e.getKeyChar();
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// keyPressed(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					if (dtrpnfranktriangle.getCaretPosition() < priortext.length() + 1) {
						e.consume();
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					e.consume();
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					e.consume();
					onCommand(newtext);
					newtext = "";
				}
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					newtext = newtext.substring(0, newtext.length() - 1);
				}
			}
		});
		panel.add(dtrpnfranktriangle, BorderLayout.CENTER);
	}

	private void onCommand(String substring) {
		print(substring);
		print("\n" + header);
	}

	public void print(String s) {
		priortext += s;
		dtrpnfranktriangle.setText(priortext);
		dtrpnfranktriangle.setCaretPosition(dtrpnfranktriangle.getText().length());
	}

}
