package apperk.jgoodies;

import java.util.Map;

import com.jgoodies.binding.value.AbstractValueModel;

/**
 * A ValueModel implementation that uses a map entry
 * instead of a JavaBean property.
 */
public class MapBackedValueModel extends AbstractValueModel
{
	protected Map<Object, Object> map;
	protected Object key;

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		Object oldKey = this.key;
		this.key = key;
		firePropertyChange("key", oldKey, key);
	}

	/**
	 * Create a new instance.
	 *
	 * @param map The map to use to access the entry.
	 * @param key The key loopup the entry.
	 */
	public MapBackedValueModel(Map<Object, Object> map, Object key)
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
		Object oldValue = map.get(key);
		map.put(key, newValue);
		fireValueChange(oldValue, newValue);
	}
	
	public Map<Object, Object> getMap()
	{
		return map;
	}
	
	public void setMap(Map<Object, Object> map)
	{
		Map<Object, Object> oldMap = this.map;
		Object oldValue = oldMap.get(key);
		this.map = map;
		firePropertyChange("map", oldMap, map);
		fireValueChange(oldValue, getValue());
	}
}

