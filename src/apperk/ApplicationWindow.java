package apperk;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

/**
 * An umbrella window for an MDI application.
 */
public class ApplicationWindow implements InitializingBean, WindowListener
{
    private static final Logger log = Logger.getLogger(ApplicationWindow.class);

    public static final int CASCADE_X_SHIFT = 20;
    public static final int CASCADE_Y_SHIFT = 20;

    protected Point nextCascadeLocation = new Point(0, 0);
    protected boolean confirmApplicationClose = true;
    protected boolean centered = false;
    protected JFrame frame = new JFrame();
    protected JDesktopPane desktop = new JDesktopPane();
    protected List defaultInternalFrames = new ArrayList();

    /**
     * Default constructor.
     */
    public ApplicationWindow()
    {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setContentPane(desktop);
        frame.addWindowListener(this);
    }

    /**
     * Setup the window after it has been configured.
     */
    public void afterPropertiesSet() throws Exception {
        if(centered)
            frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Internal method to position internal frames in a cascading style.
     *
     * @param jif The internal frame to position.
     */
    protected void cascadeInternalFrame(JInternalFrame jif)
    {
        if(nextCascadeLocation.x + jif.getWidth() >= desktop.getWidth())
        {
            nextCascadeLocation.x = 0;
        }

        if(nextCascadeLocation.y + jif.getHeight() >= desktop.getHeight())
        {
            nextCascadeLocation.y = 0;
        }

        jif.setLocation(nextCascadeLocation);

        nextCascadeLocation.x += CASCADE_X_SHIFT;
        nextCascadeLocation.y += CASCADE_Y_SHIFT;
    }

    /**
     * Allows adding a panel to the window by wrapping it in a default
     * internal frame.
     * <p/>
     * TODO: Allow setting default properties for the created frame.
	 * This can be accomplished with some map that is applied or a
	 * &quot;prototype&quot; frame (will it be clone()d?)
     * <p/>
     * @param panel The panel to wrap and insert.
     */
    public JInternalFrame addInternalFrame(JPanel panel)
    {
        JInternalFrame jif = new JInternalFrame();
        jif.setContentPane(panel);
        jif.pack();
        addInternalFrame(jif, true);
		return jif;
    }

    /**
     * Allow adding an arbitrary frame to the desktop.
     *
     * @param jif The internal frame to add.
     * @param center Whether or not to center the frame. It can be positioned
     *         manually before entering this method.
     */
    public void addInternalFrame(JInternalFrame jif, boolean center)
    {
        if(center)
            jif.setLocation((desktop.getWidth() - jif.getWidth()) / 2,
                            (desktop.getHeight() - jif.getHeight()) / 2);

        desktop.add(jif);
        desktop.setSelectedFrame(jif);
        jif.setVisible(true);
    }

    /**
     * {@link java.awt.event.WindowListener} method that notifies when the
     * window is opened. We use this opportunity to open an default internal
     * frames provided in the configuration.
     *
     * @param e Unused.
     */
    public void windowOpened(WindowEvent e)
    {
        for(Iterator i  = defaultInternalFrames.iterator(); i.hasNext(); )
        {
            Object o = i.next();
            if(JPanel.class.isAssignableFrom(o.getClass()))
            {
                addInternalFrame((JPanel)o);
            }
            else if(JInternalFrame.class.isAssignableFrom(o.getClass()))
            {
                addInternalFrame((JInternalFrame)o, true);
            }
            else if(InternalWindow.class.isAssignableFrom(o.getClass()))
            {
                InternalWindow win = (InternalWindow)o;
                if(win.getPositionCenter())
                {
                    addInternalFrame(win.getInternalFrame(), true);
                }
                else
                {
                    cascadeInternalFrame(win.getInternalFrame());
                    addInternalFrame(win.getInternalFrame(), false);
                }
            }
            else
            {
                throw new IllegalArgumentException(
                    "Cannot open internal frame: " + o);
            }
        }
    }

    /**
     * {@link javax.swing.event.WindowListener} method that notifies when the
     * window is closing. Since we specify DO_NOTHING_ON_CLOSE in the
     * constructor, we are responsible for closing the frame here. Checks the
     * configuration whether or not we should confirm, then closes the frame.
     *
     * @param e unused.
     */
    public void windowClosing(WindowEvent e)
    {
        if(confirmApplicationClose)
        {
            // TODO: DO NOT USE JOptionPane directly
            if(JOptionPane.showConfirmDialog(frame, "Do you wish to exit?",
                "Confirm", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
                return;
        }
        frame.setVisible(false);
        frame.dispose();
    }

    /**
     * Unused.
     * @param e unused.
     */
    public void windowClosed(WindowEvent e)
    {
    }

    /**
     * Unused.
     * @param e unused.
     */
    public void windowIconified(WindowEvent e)
    {
    }

    /**
     * Unused.
     * @param e unused.
     */
    public void windowDeiconified(WindowEvent e)
    {
    }

    /**
     * Unused.
     * @param e unused.
     */
    public void windowActivated(WindowEvent e)
    {
    }

    /**
     * Unused.
     * @param e unused.
     */
    public void windowDeactivated(WindowEvent e)
    {
    }

    /**
     * Set the title for the window.
     *
     * @param title title
     */
    public void setTitle(String title)
    {
        frame.setTitle(title);
    }

    /**
     * Set the menu bar for the window.
     *
     * @param menuBar
     */
    public void setMenuBar(JMenuBar menuBar)
    {
        frame.setJMenuBar(menuBar);
    }

    /**
     * Provide a list of default internal frames to open when the
     * window is created.
     *
     * @param defaultInternalFrames
     */
    public void setDefaultInternalFrames(List defaultInternalFrames)
    {
        this.defaultInternalFrames.addAll(defaultInternalFrames);
    }

    /**
     * Set the size of the window.
     *
     * @param size
     */
    public void setSize(Dimension size)
    {
        frame.setSize(size);
    }

    /**
     * Convenience method to allow setting the width.
     *
     * @param width
     */
    public void setWidth(int width)
    {
        frame.setSize(width, frame.getHeight());
    }

    /**
     * Convenience method to allow setting the height.
     *
     * @param height
     */
    public void setHeight(int height)
    {
        frame.setSize(frame.getWidth(), height);
    }

    /**
     * Set whether or not the window should be centered when it is
     * created. false by default.
     *
     * @param centered
     */
    public void setCentered(boolean centered)
    {
        this.centered = centered;
    }

    /**
     * Set whether or not the application should confirm to the user if it
     * should close. true by default.
     *
     * @param confirm
     */
    public void setConfirmApplicationClose(boolean confirm)
    {
        this.confirmApplicationClose = confirm;
    }
}

