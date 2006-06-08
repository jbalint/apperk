package apperk.winmgr;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 * A {@link Window} implementation based on
 * {@link javax.swing.JInternalFrame}. This is intended to be used with a
 * {@link javax.swing.JDesktopPane}-based window manager like
 * {@link JDesktopPaneWindowManager}.
 */
public class JInternalFrameWindow extends AbstractWindow {
	/** The internal frame backing this window. */
	protected JInternalFrame internalFrame;

	/**
	 * Create a new internal frame-backed window.
	 * 
	 * @param view The view to be displayed.
	 */
	public JInternalFrameWindow(View view) {
		super(view);
		internalFrame = new JInternalFrame(view.getTitle(),
				true, true, false, false);
		internalFrame.setContentPane(view.getPanel());
		internalFrame.pack();
		view.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("title"))
					internalFrame.setTitle(evt.getNewValue().toString());
			}
		});
		internalFrame.setDefaultCloseOperation(
				JInternalFrame.DO_NOTHING_ON_CLOSE);
		internalFrame.addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameClosing(InternalFrameEvent e) {
				tryClose();
			}
		});
	}

	/**
	 * Access the underlying internal frame.
	 * 
	 * @return The underlying internal frame.
	 */
	public JInternalFrame getInternalFrame() {
		return internalFrame;
	}

	/**
	 * Hide and dispose the backing internal frame.
	 */
	public void close() {
		internalFrame.setVisible(false);
		internalFrame.dispose();
	}
}
