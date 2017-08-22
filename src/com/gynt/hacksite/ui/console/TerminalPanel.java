package com.gynt.hacksite.ui.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.text.DefaultEditorKit;

import com.gynt.hacksite.events.InputEvent;
import com.gynt.hacksite.events.InputType;
import com.gynt.hacksite.programs.Program;

import lombok.Getter;
import lombok.Setter;

public class TerminalPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OsMode MODE = OsMode.WINDOWS;
	private JEditorPane editor;

	@Getter
	@Setter
	private Program program;

	public TerminalPanel(Program program) {
		this();
		this.program=program;
	}
	
	private TerminalPanel() {
		setForeground(Color.WHITE);
		setBackground(Color.BLACK);
		setLayout(new BorderLayout(0, 0));

		editor = new JEditorPane();
		// dtrpnfranktriangle.setKeymap(null);
		editor.setEditable(false);
		editor.getCaret().setVisible(true);
		editor.setBackground(Color.BLACK);
		editor.setForeground(Color.WHITE);

		switch (MODE) {
		case LINUX:
			editor.setFont(new Font("Monospaced", Font.PLAIN, 12));
			break;
		case OSX:
			break;
		case WINDOWS:
			editor.setFont(new Font("Lucida Console", Font.PLAIN, 12));
			break;
		default:
			break;

		}
		
		editor.getActionMap().get(DefaultEditorKit.beepAction).setEnabled(false);

		editor.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				getProgram().receive(new InputEvent(e, InputType.TYPED));
			}

			@Override
			public void keyReleased(KeyEvent e) {
				getProgram().receive(new InputEvent(e, InputType.RELEASED));
			}

			@Override
			public void keyPressed(KeyEvent e) {
				getProgram().receive(new InputEvent(e, InputType.PRESSED));
			}

		});
		add(editor, BorderLayout.CENTER);
	}

	public JEditorPane getEditor() {
		return editor;
	}

}
