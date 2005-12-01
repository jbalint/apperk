package apperk;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.springframework.beans.factory.InitializingBean;

/**
 * A wrapper for an internal frame that contains functionality that is
 * of use internally to the framework.
 */
public class InternalWindow implements InitializingBean {
    protected boolean positionCenter = false;
    protected boolean positionCascade = true;
    protected boolean shouldPack = true;
    protected JInternalFrame internalFrame = new JInternalFrame();

    public void afterPropertiesSet() throws Exception {
        if(shouldPack)
            internalFrame.pack();
    }

    /**
     * Provide access to the configured frame.
     *
     * @return The frame.
     */
    public JInternalFrame getInternalFrame()
    {
        return internalFrame;
    }

    public boolean getPositionCenter()
    {
        return positionCenter;
    }

    public void setContentPane(JPanel pane)
    {
        internalFrame.setContentPane(pane);
    }

    public void setPositionCenter(boolean center)
    {
        positionCenter = center;
        positionCascade = !center;
    }

    public boolean getPositionCascade()
    {
        return positionCascade;
    }

    public void setPositionCascade(boolean cascade)
    {
        setPositionCenter(!cascade);
    }

    public void setShouldPack(boolean shouldPack)
    {
        this.shouldPack = shouldPack;
    }

    public void setTitle(String title)
    {
        internalFrame.setTitle(title);
    }

    public void setResizable(boolean r)
    {
        internalFrame.setResizable(r);
    }

    public void setClosable(boolean c)
    {
        internalFrame.setClosable(c);
    }

    public void setMaximizable(boolean m)
    {
        internalFrame.setMaximizable(m);
    }

    public void setIconifiable(boolean i)
    {
        internalFrame.setIconifiable(i);
    }
}
