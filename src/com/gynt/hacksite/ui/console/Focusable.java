package com.gynt.hacksite.ui.console;

import javax.swing.JComponent;

import lombok.Data;

@Data
public abstract class Focusable {
	
	private JComponent parent;
	private JComponent current;
	
	public abstract void onFocusFromChild();
	
	public abstract void onFocusFromParent();

}
