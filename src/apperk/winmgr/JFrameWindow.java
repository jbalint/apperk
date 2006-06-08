package apperk.winmgr;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;

/**
 * A {@link Window} implementation based on a {@link javax.swing.JFrame}.
 */
public class JFrameWindow extends AbstractWindow {
	/** The frame backing this window. */
	protected JFrame frame;
	
	/**
	 * Create a new frame-backed window.
	 * 
	 * @param view The view to be displayed.
	 */
	public JFrameWindow(View view) {
		super(view);
		frame = new JFrame(view.getTitle());
		frame.setContentPane(view.getPanel());
		frame.pack();
		view.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("title"))
					frame.setTitle(evt.getNewValue().toString());
			}
		});
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				tryClose();
			}
		});
	}

	/**
	 * Access the underlying frame.
	 * 
	 * @return The underlying frame.
	 */
	public JFrame getFrame() {
		return frame;
	}
	
	/**
	 * Hide and dispose the backing frame.
	 */
	public void close() {
		frame.setVisible(false);
		frame.dispose();
	}
}
