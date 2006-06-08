package apperk.winmgr;

/**
 * A window manager used by an application to manage windows. The
 * WindowManager allows the application to worry only about
 * providing something to display (a {@link apperk.winmgr.View})
 * and lets the window manager worry about how to display it.
 */
public interface WindowManager {
	/**
	 * Display a given view. Client code would normally pass an instance
	 * of a View that it creates. eg.
	 * <pre>
	 * UserEditView view = new UserEditView(someUser);
	 * view.someOtherMethods();
	 * // Called from a view itself, so access to getWindow() is provided
	 * getWindow().getWindowManager().display(view);
	 * </pre>
	 * 
	 * @param view The view to display.
	 * @return The window the view is displayed in.
	 */
	Window display(View view);
}
