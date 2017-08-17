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

public class Tunneler implements EventListener {

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

	private Pattern displayable = Pattern.compile("[A-Za-z0-9 ~!@#$%^&*()_+{}:\"|<>?`-=[];\'\\,./]");

	@Override
	public void onUserInput(KeyEvent k) {
		if (displayable.matcher(k.getKeyChar() + "").matches()) {
			tracker += k.getKeyChar();
		}
		if (k.getKeyCode() == KeyEvent.VK_ENTER) {
			onUserCommand(tracker);
		}
	}

	public void onUserCommand(String s) {
		if (s.equals("ENTER")) {
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
	}

	private void processList(ListArgs list) {
		// TODO Auto-generated method stub

	}

	private void processAdd(AddArgs add) {
		// TODO Auto-generated method stub

	}

}
