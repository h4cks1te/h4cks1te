package com.gynt.hacksite.programs;

import java.awt.event.KeyEvent;

import com.gynt.hacksite.events.InputEvent;
import com.gynt.hacksite.events.InputType;
import com.gynt.hacksite.ui.console.Terminal;
import com.gynt.hacksite.ui.console.TerminalPanel;

import lombok.Getter;
import lombok.Setter;

public class Program {

	@Getter
	private final Terminal terminal;

	@Getter
	@Setter
	private Program lower;

	@Getter
	@Setter
	private Program upper;

	@Getter
	private final TerminalPanel panel;

	public Program(Terminal t, boolean newscreen) {
		terminal = t;
		if (newscreen) {
			panel = t.createSubScreen(this);
		} else {
			panel = (TerminalPanel) terminal.getSubscreenLayout().getCurrent();
		}

	}

	public boolean hasLower() {
		return lower != null;
	}

	public boolean hasUpper() {
		return upper != null;
	}

	public void toLower(InputEvent k) {
		if (lower != null) {
			lower.receive(k);
		}
	}

	public void toUpper(String output) {
		if (upper != null) {
			upper.send(output);
		}
	}

	public void receive(InputEvent k) {
		toLower(k);
	}

	public void send(String output) {
		toUpper(output);
	}

	public boolean isEscape(InputEvent k) {
		if (k.getType() == InputType.PRESSED) {
			if (k.getKeyEvent().getKeyCode() == KeyEvent.VK_ESCAPE) {
				return true;
			}
		}
		return false;
	}

	public void loseFocus() {
		terminal.getSubscreenLayout().previous(terminal);
	}
	
	public void regainFocus() {
		
	}

}
