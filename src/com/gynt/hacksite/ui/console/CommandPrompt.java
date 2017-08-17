package com.gynt.hacksite.ui.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JEditorPane;
import javax.swing.JPanel;

public class CommandPrompt extends JPanel {
	public CommandPrompt() {
		setLayout(new BorderLayout(0, 0));

		JEditorPane dtrpnSworkspacerugnl = new JEditorPane();
		dtrpnSworkspacerugnl.setText("s2304929@WORKSPACE.RUG.NL>");
		dtrpnSworkspacerugnl.setFont(new Font("Lucida Console", Font.BOLD, 12));
		dtrpnSworkspacerugnl.setForeground(SystemColor.control);
		dtrpnSworkspacerugnl.setBackground(Color.BLACK);
		add(dtrpnSworkspacerugnl, BorderLayout.CENTER);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
