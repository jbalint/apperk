package apperk.menu;

import java.util.*;
import javax.swing.*;
import org.springframework.beans.factory.*;

/**
 * A factory bean to allow creating menus via Spring configuration. Normally
 * used in conjunction with {@link apperk.menu.MenuBarConfigurer}.<p/>
 *
 * Example:<br/>
 * <pre>
 * &lt;bean id=&quot;editMenu&quot; class=&quot;apperk.menu.Menu&quot;>
 *     &lt;property name=&quot;text&quot; value=&quot;Edit&quot;/&gt;
 *     &lt;property name=&quot;menuItems&quot;&gt;
 *         &lt;list&gt;
 *             ... menu items
 *         &lt;/list&gt;
 *     &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 */
public class Menu implements FactoryBean
{
    JMenu menu = new JMenu();

    /**
     * Set the text of the menu. This is what will appear in the menu bar.
     *
     * @param text The menu text.
     */
    public void setText(String text)
    {
        menu.setText(text);
    }

    /**
     * Set the list of menu items. This can accept the following elements:<p/>
     *
     * <ul>
     * <li>String value &quot;separator&quot;, for a separator item.</li>
     * <li>{@link javax.swing.JMenuItem}, directly added to menu.</li>
     * <li>{@link javax.swing.Action}, added to menu. Either directly or via
     *     {@link apperk.ActionConfigurer}.</li>
     * </ul>
     *
     * @param menuItems The list of menu items.
     */
     public void setMenuItems(List menuItems)
    {
        for (Iterator i = menuItems.iterator(); i.hasNext();)
        {
            Object o = i.next();
            if (JMenuItem.class.isAssignableFrom(o.getClass()))
            {
                menu.add((JMenuItem)o);
            }
            else if (Action.class.isAssignableFrom(o.getClass()))
            {
                menu.add((Action)o);
            }
            else if (String.class.isAssignableFrom(o.getClass()))
            {
                if (o.equals("separator"))
                {
                    menu.addSeparator();
                }
                else
                {
                    throw new IllegalArgumentException(
                        "Invalid menu item entry: " + o);
                }
            }
            else
            {
                throw new IllegalArgumentException("Invalid menu item: " + o);
            }
        }
    }

    public Object getObject() throws Exception
    {
        return menu;
    }

    public Class getObjectType()
    {
        return JMenu.class;
    }

    public boolean isSingleton()
    {
        return true;
    }
}

