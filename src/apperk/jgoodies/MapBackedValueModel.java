package apperk.jgoodies;

import java.util.Map;

import com.jgoodies.binding.value.AbstractValueModel;

/**
 * A ValueModel implementation that uses a map entry
 * instead of a JavaBean property. This version does
 * NOT provide property change notification.
 */
public class MapBackedValueModel extends AbstractValueModel
{
	protected Map<String, Object> map;
	protected String key;

	/**
	 * Create a new instance.
	 *
	 * @param map The map to use to access the entry.
	 * @param key The key loopup the entry.
	 */
	public MapBackedValueModel(Map<String, Object> map, String key)
	{
		this.map = map;
		this.key = key;
	}

	/**
	 * Retrieve the value.
	 *
	 * @return the value.
	 */
	public Object getValue()
	{
		return map.get(key);
	}

	/**
	 * Set the value.
	 *
	 * @param newValue the new value.
	 */
	public void setValue(Object newValue)
	{
		map.put(key, newValue);
	}
}

