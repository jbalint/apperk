package apperk;

import javax.swing.Action;

import org.springframework.beans.factory.FactoryBean;

/**
 * A configuration object for an {@link javax.swing.Action}. This allows
 * creating an instance of a given action and assigning it's properties like
 * name and (NOT YET) accelerator key. It also (NOT YET) allows assigning security
 * parameters for enable/disable of the action.
 */
public class ActionConfigurer implements FactoryBean
{
    private Action action;
    private String name;

    /**
     * Provide an action to configure.
     *
     * @param action An action that must only implement
     *     {@link java.awt.event.ActionListener#actionPerformed}.
     */
    public void setAction(Action action)
    {
        this.action = action;
    }

    /**
     * Provide a name for the action.
     *
     * @param name Name.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Return the configured {@link javax.swing.Action}.
     *
     * @return The action.
     */
    public Object getObject() throws Exception
    {
        if (action == null)
            throw new IllegalArgumentException(
                "action property cannot be null");
        if (name != null)
            action.putValue(Action.NAME, name);
        return action;
    }

    /**
     * Give the return type.
     *
     * @return Action.class.
     */
    public Class getObjectType()
    {
        return Action.class;
    }

    /**
     * Whether or not a singleton = true.
     *
     * @return true
     */
    public boolean isSingleton()
    {
        return true;
    }

}
