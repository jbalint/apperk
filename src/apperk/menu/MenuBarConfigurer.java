package apperk.menu;

import java.util.Iterator;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.springframework.beans.factory.FactoryBean;

/**
 * A factory bean to allow creating menu bars via Spring configurations.
 * <p/>
 * Works in conjunction with the {@link apperk.menu.Menu} factory bean.
 * <p/>
 * An example of setting up the menu bar:<br/>
 * <pre>
 * &lt;bean id=&quot;menuBar&quot;
 *     class=&quot;apperk.menu.MenuBarConfigurer&quot;&gt;
 *     &lt;property name=&quot;menus&quot;&gt;
 *         &lt;list&gt;
 *             ... menus ..
 *         &lt;/list&gt;
 *     &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 */
public class MenuBarConfigurer implements FactoryBean
{
    private JMenuBar menuBar = new JMenuBar();

    public void setMenus(List menus)
    {
        for (Iterator iter = menus.iterator(); iter.hasNext();)
        {
            Object o = iter.next();
            if (JMenu.class.isAssignableFrom(o.getClass()))
            {
                menuBar.add((JMenu)o);
            }
            else
            {
                throw new IllegalArgumentException("Invalid menu: " + o);
            }
        }
    }

    public Object getObject() throws Exception
    {
        return menuBar;
    }

    public Class getObjectType()
    {
        return JMenuBar.class;
    }

    public boolean isSingleton()
    {
        return true;
    }
}
