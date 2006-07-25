package apperk.winmgr;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;

/**
 * A {@link Window} implementation based on a {@link javax.swing.JDialog}.
 */
public class JDialogWindow extends AbstractWindow {
	/** The dialog backing this window. */
	protected JDialog dialog;

	/**
	 * Create a new dialog-backed window.
	 * 
	 * @param parent The JFrame parent of the window.
	 * @param view The view to be displayed in the dialog.
	 */
	public JDialogWindow(JFrame parent, View view) {
		super(view);
		dialog = new JDialog(parent);
		dialog.setContentPane(view.getPanel());
		dialog.setTitle(view.getTitle());
		dialog.pack();
		// keep the view title in sync with the dialog title
		view.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("title"))
					dialog.setTitle(evt.getNewValue().toString());
			}
		});
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				tryClose();
			}
		});
	}

	/**
	 * Access the underlying dialog.
	 * 
	 * @return The underlying dialog.
	 */
	public JDialog getDialog() {
		return dialog;
	}

	/**
	 * Hide and dispose the backing dialog.
	 */
	public void close() {
		dialog.setVisible(false);
		dialog.dispose();
	}

	/**
	 * Get the JRootPane from the JDialog.
	 */
	public JRootPane getRootPane() {
		return dialog.getRootPane();
	}
}

