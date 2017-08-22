package com.gynt.hacksite.events;

import java.awt.event.KeyEvent;

import lombok.Data;

@Data
public class InputEvent {

	private final KeyEvent keyEvent;

	private boolean handled;

	private final InputType type;
}
