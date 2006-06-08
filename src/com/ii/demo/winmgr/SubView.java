package com.ii.demo.winmgr;

import java.awt.BorderLayout;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import apperk.winmgr.View;
import apperk.winmgr.Window;

class SubViewPanel extends JPanel
{
	public SubViewPanel()
	{
		super(new BorderLayout());
		add(new JScrollPane(new JTree()));
	}
}

public class SubView implements View {
	protected SubViewPanel panel = new SubViewPanel();
	protected Window window;
	
	public JPanel getPanel() {
		return panel;
	}

	public boolean close() {
		return true;
	}

	public String getTitle() {
		return "Sub panel";
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		// unused
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		// unused
	}
}
