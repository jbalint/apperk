package apperk.winmgr;

import javax.swing.JButton;
import javax.swing.JRootPane;

/**
 * A window represents something that would normally be referred to by the
 * user as a &quot;window&quot;. In Swing parlance, it could be anything
 * from a {@link javax.swing.JDialog} or {@link javax.swing.JFrame} to a
 * {@link javax.swing.JInternalFrame}.
 * <p/>
 * This interface provides a unified and abstract interface to these
 * components.
 */
public interface Window {
	/**
	 * Close the represented window. This doesn't allow the application
	 * to verify with the user or otherwise prevent closing.
	 */
	void close();
	
	/**
	 * Attempt to close the window. If all the internals allow the window
	 * to be close, it will be closed.
	 * 
	 * @return Whether or not the window was actually closed.
	 */
	boolean tryClose();
	
	/**
	 * Provide access to the {@link WindowManager}. This is used for things
	 * like displaying other {@link View}s from this window.
	 * 
	 * @return The WindowManager managing this Window.
	 */
	WindowManager getWindowManager();

	/**
	 * Provide access to the {@link JRootPane}. This is common
	 * to the underlying frames, but not exposed in a uniform way.
	 */
	JRootPane getRootPane();

	/**
	 * Allow setting the default button conveniently.
	 */
	void setDefaultButton(JButton button);
}
