package apperk.glazedlists;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.swing.EventComboBoxModel;

import java.beans.IntrospectionException;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.event.ListDataEvent;

/**
 * An extension of the Glazed Lists' EventComboBoxModel that displays
 * a property of the entity in the list. An additional method is added
 * to the interface called {@link #getSelectedEntity} in order to retrieve
 * the selected entity.
 * <br/>
 * If there was no entity which matched the property value when
 * {@link #setSelectedItem} was called, the selected entity will
 * effectively be null.
 * <br/>
 * This might be an abomination of the generics.
 * <br/>
 * <font color="red">TODO:</font> There is a problem using the JavaBeans
 * introspection classes because they seem to throw an exception if there
 * is a read-only property.
 */
public class PropertyDisplayEventListModel<E> extends EventComboBoxModel<E>
{
	protected String displayProperty;
	protected Object selectedItem;
	/** The selected entity. */
	protected E selectedEntity;
	/** A raw EventList. Only other copy is proxied to execute on EDT. */
	protected EventList<E> rawSource;
	
	protected PropertyChangeSupport propSupport = 
		new PropertyChangeSupport(this);

	/**
	 * Construct a new model using the displayProperty as the name
	 * of a JavaBeans compatible property to be displayed.
	 *
	 * @param source Source list of entities.
	 * @param displayProperty Name of property to display.
	 */
	public PropertyDisplayEventListModel(EventList<E> source,
			String displayProperty)
	{
		super(source);
		this.rawSource = source;
		this.displayProperty = displayProperty;
	}

	public Object getElementAt(int i)
	{
		E o = (E)super.getElementAt(i);
		if(o == null)
			System.err.println("apperk.glazedlists.PropertyDisplayEventListModel.getElementAt is Null at: " + i);
		return o == null ? null : getProperty(o);
	}

	public Object getSelectedItem()
	{
		if(selectedEntity == null)
			return selectedItem;
		return getProperty(selectedEntity);
	}

	/**
	 * Search through the entity list to find one which has a property
	 * that matches the &quot;selected value&quot;. A linear search is
	 * performed so if multiple entities would match, the
	 * first is selected based on list order.
	 * <br/>
	 * If none are found that match the criteria, the selected entity is
	 * set to null.
	 *
	 * @param o the &quot;selected value&quot;
	 */
	public void setSelectedItem(Object o)
	{
		E old = selectedEntity;
		
		selectedItem = o;
		// linear search for now...
		selectedEntity = null;
		for(E curr : rawSource)
		{
			Object prop = getProperty(curr);
			if(prop != null && prop.equals(o))
			{
				selectedEntity = curr;
				break;
			}
		}
		// see super.setSelectedItem(o); for details
		fireListDataEvent(new ListDataEvent(
				this, ListDataEvent.CONTENTS_CHANGED, -1, -1));
		
		propSupport.firePropertyChange("selectedEntity", old, selectedEntity);
	}

	/**
	 * The selected entity (if any).
	 *
	 * @return the selected entity
	 */
	public E getSelectedEntity()
	{
		return selectedEntity;
	}

	public void setSelectedEntity(E entity)
	{
		if(entity == null)
			setSelectedItem(null);
		else
			setSelectedItem(getProperty(entity));
	}

	/**
	 * Internal method to get the value of the JavaBeans property.
	 * <br/>
	 * <font color="red">TODO:</font> fix the exception handling
	 *
	 * @param o The entity from the list.
	 * @return The value of the display property for the given entity.
	 */
	protected Object getProperty(E o)
	{
		try
		{
			PropertyDescriptor pd =
					new PropertyDescriptor(displayProperty, o.getClass());
			Method rd = pd.getReadMethod();
			return rd.invoke(o);
		}
		catch(IntrospectionException e)
		{
			e.printStackTrace();
		}
		catch(IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch(InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propSupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propSupport.removePropertyChangeListener(listener);
	}
}
