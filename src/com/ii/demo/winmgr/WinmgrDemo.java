package com.ii.demo.winmgr;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import apperk.winmgr.TopLevelWindowManager;
import apperk.winmgr.JFrameWindow;
import apperk.winmgr.Window;
import apperk.winmgr.WindowManager;

/**
 * A simple window manager demo. Uses the top-level window manager with
 * a simple 'main' view that provides access to a second view. The main
 * view is setup to recursively recreate itself until the 'finalize'
 * button is pressed.
 */
public class WinmgrDemo {
	/** Marker for when to actually close the main window. */
	public static boolean closeForReal = false;

	/**
	 * Setup the frame to recreate itself when it's closed.
	 * 
	 * @param frame
	 */
	private static void makeRecreatable(JFrame frame) {
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				if(closeForReal)
					return;
				Window w = windowManager.display(new MainView());
				makeRecreatable(((JFrameWindow)w).getFrame());
			}
		});
	}
	
	private static WindowManager windowManager = new TopLevelWindowManager();
	public static void main(String[] args) {
		// Probably would be Spring managed
		Window mainWindow = windowManager.display(new MainView());
		// demo/white-box code to recreate the window on close
		makeRecreatable(((JFrameWindow)mainWindow).getFrame());
	}
}
