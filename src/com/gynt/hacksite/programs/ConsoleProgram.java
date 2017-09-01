package com.gynt.hacksite.programs;

import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

import com.gynt.hacksite.events.InputEvent;
import com.gynt.hacksite.events.InputType;
import com.gynt.hacksite.ui.console.OsMode;
import com.gynt.hacksite.ui.console.Terminal;

public class ConsoleProgram extends Program {

	private OsMode MODE = OsMode.WINDOWS;
	private String header;
	private String priortext;
	private String newtext;

	public ConsoleProgram(Terminal terminal, boolean newscreen) {
		super(terminal, newscreen);

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

		commit(header);
	}

	private final Pattern typables = Pattern.compile("[ -~]");

	@Override
	public void receive(InputEvent k) {
		if (hasLower()) {
			toLower(k);
			return;
		}
		if (isEscape(k)) {
			loseFocus();
		}
		KeyEvent e = k.getKeyEvent();
		if (k.getType() == InputType.TYPED) {
			if (typables.matcher(e.getKeyChar() + "").matches()) {
				newtext += e.getKeyChar();
				System.out.println("typed: " + e.getKeyChar());
				display(priortext + newtext);
			}
		} else if (k.getType() == InputType.PRESSED) {
			if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				if (getPanel().getEditor().getCaretPosition() < priortext.length() + 1) {
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
				if (newtext.length() > 0) {
					System.out.println("backspaced: " + newtext.substring(0, newtext.length() - 1));
					newtext = newtext.substring(0, newtext.length() - 1);
					display(priortext + newtext);
				}
				e.consume();
			}
		}
		
	}

	private void onCommand(String substring) {
		commit(substring);
		
		if(substring.equals("tunneler")) {
			setLower(new Tunneler(getTerminal(), false));
		}
		
		loseFocus();
	}
	
	@Override
	public void regainFocus() {
		commit("\n" + header);
	}

	public void commit(String s) {
		priortext += s;
		display(priortext);
	}
	
	public void display(String s) {
		getPanel().getEditor().setText(s);
		getPanel().getEditor().setCaretPosition(getPanel().getEditor().getText().length());
	}

}
