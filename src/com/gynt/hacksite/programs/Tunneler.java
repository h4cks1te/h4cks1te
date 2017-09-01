package com.gynt.hacksite.programs;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.gynt.hacksite.events.EventListener;
import com.gynt.hacksite.events.InputEvent;
import com.gynt.hacksite.events.InputType;
import com.gynt.hacksite.ui.console.Terminal;

public class Tunneler extends Program {

	public Tunneler(Terminal t, boolean newscreen) {
		super(t, newscreen);
	}

	@Parameters(separators = "=", commandDescription = "Record changes to the repository")
	public class AddArgs {

		@Parameter(names = "--from", description = "Tunnel from host", converter = HostConverter.class, validateWith = HostConverter.class)
		private Host from;

		@Parameter(names = "--to", description = "Tunnel to host", converter = HostConverter.class, validateWith = HostConverter.class)
		private Host to;

	}

	@Parameters(separators = "=", commandDescription = "Record changes to the repository")
	public class ListArgs {

	}

	public class MainArgs {

	}

	public static class HostConverter implements IStringConverter<Host>, IParameterValidator {

		@Override
		public Host convert(String arg0) {
			return new Host(arg0);
		}

		private static final Pattern p1 = Pattern.compile("([0-9]+\\.)+[0-9]+:[0-9]+");
		private static final Pattern p2 = Pattern.compile("[a-zA-Z0-9]+\\.[a-z]+:[0-9]+");

		@Override
		public void validate(String name, String value) throws ParameterException {
			if (!value.contains(":")) {
				throw new ParameterException("No port specified");
			}
			if (!p1.matcher(value).matches() && !p2.matcher(value).matches()) {
				throw new ParameterException("Invalid format.");
			}
		}

	}

	private String tracker = "";

	public String[] tokenize(String s) {
		int depth1 = 0;
		int depth2 = 0;

		List<Integer> indexes = new ArrayList<>();

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '\'') {
				depth1++;
			}
			if (s.charAt(i) == '\"') {
				depth2++;
			}
			if (Character.isWhitespace(s.charAt(i))) {
				if (depth1 == 0 && depth2 == 0) {
					indexes.add(i);
				}
			}
		}
		int previous = 0;
		String[] result = new String[indexes.size()];
		for (int i = 0; i < indexes.size(); i++) {
			result[i] = s.substring(previous, indexes.get(i));
			previous = indexes.get(i) + 1;
		}
		return result;
	}

	private Pattern typables = Pattern.compile("[ -~]");
	private String newtext;
	private String priortext;

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
				onUserCommand();
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

	public void onUserCommand() {

			MainArgs main = new MainArgs();
			AddArgs add = new AddArgs();
			ListArgs list = new ListArgs();

			JCommander j = JCommander.newBuilder().addObject(main).build();
			j.addCommand("add", add);
			j.addCommand("list", list);
			j.parse(tokenize(tracker));
			tracker = "";
			switch (j.getParsedCommand()) {
			case "add":
				processAdd(add);
				break;
			case "list":
				processList(list);
			default: {
				throw new RuntimeException();
			}
			}
		
	}

	public void commit(String s) {
		priortext += s;
		display(priortext);
	}
	
	public void display(String s) {
		getPanel().getEditor().setText(s);
		getPanel().getEditor().setCaretPosition(getPanel().getEditor().getText().length());
	}

	private void processList(ListArgs list) {
		// TODO Auto-generated method stub

	}

	private void processAdd(AddArgs add) {
		// TODO Auto-generated method stub

	}

}
