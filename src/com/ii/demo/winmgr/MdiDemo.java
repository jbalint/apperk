package com.ii.demo.winmgr;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;


import apperk.winmgr.JDesktopPaneWindowManager;
import apperk.winmgr.WindowManager;

// TODO: make the main MDI window check all the internal frames when closing
/**
 * Sample of using the MDI window manager. We have a little more work with
 * this one to setup the frame with the desktop pane.
 */
public class MdiDemo {
	public static void main(String[] args) {
		JFrame frame = new JFrame("MDI Demo");
		frame.setSize(800, 600);
		JDesktopPane desktopPane = new JDesktopPane();
		frame.setContentPane(desktopPane);
		// TODO: check all windows before closing
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		WindowManager windowManager = new JDesktopPaneWindowManager(desktopPane);
		windowManager.display(new MainView());
	}
}
