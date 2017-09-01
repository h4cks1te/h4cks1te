package com.gynt.hacksite.ui.console;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.util.HashMap;

import lombok.Getter;

public class SubscreenLayout extends CardLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	private final HashMap<String, Component> mapping = new HashMap<>();

	private String current;

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		super.addLayoutComponent(comp, constraints);
		mapping.put((String) constraints, comp);
	}

	public Component get(String name) {
		return mapping.get(name);
	}

	public void remove(String name) {
		removeLayoutComponent(mapping.get(name));
	}

	@Override
	public void show(Container parent, String name) {
		super.show(parent, name);
		current = name;
	}

	public Component getCurrent() {
		return mapping.get(current);
	}

	public String getCurrentName() {
		return current;
	}

}
