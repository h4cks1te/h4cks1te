package com.gynt.hacksite.ui.console;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.Caret;

import java.awt.Color;
import javax.swing.JList;
import javax.swing.JEditorPane;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Terminal extends JFrame {

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
		acceptable_keys  = new ArrayList<>();
	}
	
	Pattern pattern = Pattern.compile("[a-zA-Z0-9]");

	/**
	 * Create the frame.
	 */
	public Terminal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		header = "[frank@triangle ~]$ ";
		priortext = header;
		newtext="";
		
		dtrpnfranktriangle = new JEditorPane();
		
		
		
		//dtrpnfranktriangle.setKeymap(null);
		dtrpnfranktriangle.setEditable(true);
		dtrpnfranktriangle.setText("[frank@triangle ~]$ ");
		dtrpnfranktriangle.setBackground(Color.BLACK);
		dtrpnfranktriangle.setForeground(Color.WHITE);
		dtrpnfranktriangle.setFont(new Font("Monospaced", Font.PLAIN, 12));
		dtrpnfranktriangle.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				//keyPressed(e);
				if(Pattern.matches("[A-Za-z0-9 !?@#$%^&*()_+{}|\":?><]", e.getKeyChar()+"")) {
					newtext+=e.getKeyChar();
				}
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				//keyPressed(e);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE) {
					if(dtrpnfranktriangle.getCaretPosition()<priortext.length()+1) {
						e.consume();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_DELETE) {
					e.consume();
				}
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					e.consume();
					onCommand(newtext);
					newtext="";
				}
				if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE) {
					newtext=newtext.substring(0, newtext.length()-1);
				}
			}
		});
		panel.add(dtrpnfranktriangle, BorderLayout.CENTER);
	}

	private void onCommand(String substring) {
		print(substring);
		print("\n"+header);
	}
	
	public void print(String s) {
		priortext+=s;
		dtrpnfranktriangle.setText(priortext);
		dtrpnfranktriangle.setCaretPosition(dtrpnfranktriangle.getText().length());
	}
	
	

}
