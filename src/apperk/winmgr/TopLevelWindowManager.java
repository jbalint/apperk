package apperk.winmgr;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * A {@link WindowManager} implementation based on top-level windows
 * like {@link javax.swing.JFrame} and {@link javax.swing.JDialog}.
 * A JFrame is used for the first window and JDialogs (with the JFrame
 * as the parent) for all further windows. This is done because JFrame
 * is visible on the OS taskbar, but a JDialog is not. For detailed
 * discussion of top-level containers, see
 * <a href="http://java.sun.com/docs/books/tutorial/uiswing/components/toplevel.html">
 * here</a>.
 */
public class TopLevelWindowManager implements WindowManager {
	/** The root frame used as the first window and parent of all other windows. */
	protected JFrameWindow rootFrame;

	/**
	 * Create a new window.
	 * 
	 * @param view The view to be displayed.
	 */
	public Window display(View view) {
		if(rootFrame == null) {
			rootFrame = new JFrameWindow(view);
			rootFrame.setWindowManager(this);
			rootFrame.getFrame().setLocationRelativeTo(null);
			rootFrame.getFrame().setVisible(true);
			// allow the window manager to re-create a root window
			rootFrame.getFrame().addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent e) {
					rootFrame = null;
				}
			});
			return rootFrame;
		} else {
			JDialogWindow window =
				new JDialogWindow(rootFrame.getFrame(), view);
			window.setWindowManager(this);
			window.getDialog().setModal(true);
			window.getDialog().setLocationRelativeTo(null);
			window.getDialog().setVisible(true);
			return window;
		}
	}
}
