package apperk.winmgr;

import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 * A {@link WindowManager} implementation that uses a
 * {@link javax.swing.JDesktopPane} and places windows inside of it. The
 * default positioning cascades the internal frames.
 * <p/>
 * <font color="red">TODO</font>: somehow allow for centering a window, eg login window, console, etc
 * <br/>
 * <font color="red">TODO</font>: allow modal internal frames (maybe via JDialogWindow?)
 * <br/>
 * 			ref: http://java.sun.com/developer/JDCTechTips/2001/tt1220.html
 * <br/>
 * <font color="red">TODO</font>: allowing scrollable desktop would be nice
 * <br/>
 * 			refs for scrollable desktop:
 * <br/>
 *          http://www.javaworld.com/javaworld/jw-11-2001/jw-1130-jscroll-p2.html
 * <br/>
 * 			http://jscroll.sourceforge.net/
 * <br/>
 * 			maybe manually w/ one of these: http://forum.java.sun.com/thread.jspa?forumID=57&threadID=617623
 * <br/>
 * <font color="red">TODO</font>: allow enumerating internal frames so the outer window can try to
 * <br/>
 * 			close them when it closes
 */
public class JDesktopPaneWindowManager implements WindowManager {
	/** Horizontal increment pixels when cascading windows. */
	protected static final int CASCADE_HORIZ_INCREMENT = 20;
	/** Vertical increment pixels when cascading windows. */
	protected static final int CASCADE_VERT_INCREMENT = 10;
	
	/** Next window x position. */
	protected int windowPosX = 0;
	/** Next window y position. */
	protected int windowPosY = 0;
	/** The desktop pane to put the internal frames into. */
	protected JDesktopPane desktopPane;

	/**
	 * Create a new window manager.
	 * 
	 * @param desktopPane The JDesktopPane to use for adding frames to.
	 */
	public JDesktopPaneWindowManager(JDesktopPane desktopPane) {
		this.desktopPane = desktopPane;
	}

	/**
	 * Display a given view. This involves creating a new internal frame,
	 * positioning it and making it visible. The act of making it visible
	 * automatically selects it in the desktop pane.
	 * 
	 * @param view The view to create a window for.
	 * @return The window created.
	 */
	public Window display(View view) {
		JInternalFrameWindow window = new JInternalFrameWindow(view);
		window.setWindowManager(this);
		cascadeWindow(window.getInternalFrame());
		desktopPane.add(window.getInternalFrame());
		// don't do this because then every internal frame is selected
		// somehow and they overlap in wierd ways
		//desktopPane.setSelectedFrame(window.getInternalFrame());
		window.getInternalFrame().setVisible(true);
		return window;
	}

	/**
	 * Cascade the provided internal frame.
	 * 
	 * @param internalFrame The internal frame to position.
	 */
	protected void cascadeWindow(JInternalFrame internalFrame) {
		Dimension d = internalFrame.getSize();
		if(windowPosX + d.width > desktopPane.getWidth())
			windowPosX = 0;
		if(windowPosY + d.height > desktopPane.getHeight())
			windowPosY = 0;
		internalFrame.setLocation(windowPosX, windowPosY);
		windowPosX += CASCADE_HORIZ_INCREMENT;
		windowPosY += CASCADE_VERT_INCREMENT;
	}
	
	/**
	 * Access the desktop pane.
	 * 
	 * @return The desktop pane.
	 */
	public JDesktopPane getDesktopPane() {
		return desktopPane;
	}
}
