package apperk.winmgr;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * A convenient implementation of most of the {@link Window} interface. The
 * concrete implementation is responsible for managing the UI window
 * component. This also includes implementing {@link #close()}.
 * <p/>
 * This abstract class stores the {@link View} and {@link WindowManager}. It
 * adds a keyboard action to attempt to close the window when the 'Esc' key
 * is pressed.
 */
public abstract class AbstractWindow implements Window {
	/** The view. */
	protected View view;
	/** The window manager. */
	protected WindowManager windowManager;
	
	/**
	 * Create a new base window and setup the view.
	 * 
	 * @param view The view being displayed.
	 */
	public AbstractWindow(View view) {
		this.view = view;
		view.setWindow(this);
		registerEscToCloseWindow(view.getPanel());
	}
	
	/**
	 * Add a keyboard action to the view's panel to try closing the
	 * window when 'Esc' is pressed.
	 * 
	 * @param panel The view's panel.
	 */
	protected void registerEscToCloseWindow(JPanel panel) {
		panel.registerKeyboardAction(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tryClose();
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
			JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	/**
	 * Try closing the view and close the window if it was
	 * successful.
	 * 
	 * @return Whether or not the view is closed.
	 */
	public boolean tryClose() {
		if(!view.close())
			return false;
		close();
		return true;
	}

	/**
	 * @see Window#getWindowManager()
	 */
	public WindowManager getWindowManager() {
		return windowManager;
	}

	/**
	 * Set the window manager.
	 * 
	 * @param windowManager The window manager managing this window.
	 */
	public void setWindowManager(WindowManager windowManager) {
		this.windowManager = windowManager;
	}
}
