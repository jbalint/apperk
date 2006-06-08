package apperk.winmgr;

import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

/**
 * An abstract representation of a view that provides operations to
 * display and control it.
 */
public interface View {
	/**
	 * Accessor method for the panel that is the view content.
	 * 
	 * @return The panel of view content.
	 */
	JPanel getPanel();
	
	/**
	 * Attempt to close the view. If the view wants to do something
	 * like confirm with the user, this is the place to do it. If this
	 * methods returns true, the view must be closed.
	 * 
	 * @return Whether or not the view can actually be closed.
	 */
	boolean close();
	
	/**
	 * Title of the view. This property is bound so a {@link Window} or
	 * other object can listen for changes and appropriately change
	 * the window title.
	 * 
	 * @return The title of the view.
	 */
	String getTitle();
	
	/**
	 * Window containing this view.
	 * 
	 * @return The window containing this view.
	 */
	Window getWindow();
	
	/**
	 * Provide the window that is going to contain this view.
	 * 
	 * @param window The window.
	 */
	void setWindow(Window window);
	
	/**
	 * Listen for property changes.
	 * 
	 * @param listener The listener.
	 */
	void addPropertyChangeListener(PropertyChangeListener listener);
	
	/**
	 * Don't listen for property changes.
	 * 
	 * @param listener The listener.
	 */
	void removePropertyChangeListener(PropertyChangeListener listener);
}
