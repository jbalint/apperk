package com.ii.demo.winmgr;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import apperk.winmgr.View;
import apperk.winmgr.Window;

/**
 * Simple view with a few buttons to do stuff.
 */
class MainViewPanel extends JPanel
{
	public JButton s1 = new JButton("Show sub view");
	public JButton s2 = new JButton("Finalize Window (JFrame only)");
	public JButton s3 = new JButton("Do nothing");
	public MainViewPanel()
	{
		super(new BorderLayout());
		add(new JLabel("Welcome to the baseball hall of fame"), BorderLayout.NORTH);
		add(s1, BorderLayout.WEST);
		add(s2, BorderLayout.CENTER);
		add(s3, BorderLayout.EAST);
		add(new JLabel("Bottom of the barrel here"), BorderLayout.SOUTH);
	}
}

/**
 * A sample view class. Uses a simple JPanel-based view.
 */
public class MainView implements View {
	protected PropertyChangeSupport propChange = new PropertyChangeSupport(this);
	private Window window;
	private String title = "Baseball Arena " + (num++);
	protected MainViewPanel panel = new MainViewPanel();
	
	private static int num = 1;
	
	public MainView() {
		panel.s1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// change the title to show the frame title changing
				setTitle(getTitle() + ".");
				// display a sub view
				getWindow().getWindowManager().display(new SubView());
			}
		});
		panel.s2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// alternate the close flag
				WinmgrDemo.closeForReal = !WinmgrDemo.closeForReal;
			}
		});
	}

	public JPanel getPanel() {
		return panel;
	}

	public boolean close() {
		// TODO: find how to really know when to close
		return true;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String newTitle) {
		String old = title;
		title = newTitle;
		propChange.firePropertyChange("title", old, title);
	}

	public Window getWindow() {
		return window;
	}
	
	public void setWindow(Window window) {
		this.window = window;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propChange.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propChange.removePropertyChangeListener(listener);
	}
}
