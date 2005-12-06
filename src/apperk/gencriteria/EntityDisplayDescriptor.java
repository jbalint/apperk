package apperk.gencriteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.WrapDynaClass;
import org.springframework.beans.factory.InitializingBean;

/**
 * A descriptor for displaying an entity. This defines:<br/>
 * <ul>
 * <li>The class being described</li>
 * <li>A display name for the entity class</li>
 * <li>The properties to use for display</li>
 * <li>Display names for each property that will be displayed</li>
 * </li>
 */
public class EntityDisplayDescriptor implements InitializingBean
{
	private Class entityClass;
	private String entityName;
	private List<DynaProperty> displayProperties =
		new ArrayList<DynaProperty>();
	private List<String> displayPropertyNames;
	private Map<String, String> displayNames = new HashMap<String, String>();
	private List<String> excludeProperties = null;

	public EntityDisplayDescriptor()
	{
	}

	/**
	 * TODO: javadoc
	 */
	public void afterPropertiesSet() throws Exception
	{
		if(entityClass == null)
		{
			throw new IllegalArgumentException("entityClass cannot be null");
		}

		WrapDynaClass wdc = WrapDynaClass.createDynaClass(entityClass);

		// setup an empty List for convenience if not provided
		if(excludeProperties == null)
		{
			excludeProperties = new ArrayList<String>();
		}

		// Setup initial properties
		if(displayPropertyNames == null)
		{
			for(DynaProperty dp : wdc.getDynaProperties())
			{
				if(!excludeProperties.contains(dp.getName()))
					displayProperties = Arrays.asList(wdc.getDynaProperties());
			}
		}
		else
		{
			for(String name : displayPropertyNames)
			{
				DynaProperty dp = wdc.getDynaProperty(name);
				if(dp == null)
				{
					throw new IllegalArgumentException(String.format(
								"Cannot access property %s of class %s",
								name, entityClass.getName()));
				}

				if(!excludeProperties.contains(name))
					displayProperties.add(dp);
			}
		}

		// Setup display names
		for(DynaProperty dp : displayProperties)
		{
			// Allow base settings from configuration
			if(displayNames.get(dp.getName()) == null)
			{
				displayNames.put(dp.getName(),
						generateProperName(dp.getName()));
			}
		}
	}

	/**
	 * Generate a display name for a given property name. This is meant
	 * to be used to create defaults for something like:<br/>
	 * <code>accountBalance =&gt; Account Balance</code>
	 * The following rules are applied:<br/>
	 * <ol>
	 * <li>Capitalize the first letter</li>
	 * <li>Add a space before each capital letter</li>
	 * <li>Add a space before a sequence of digits</li>
	 * </ol>
	 *
	 * @param propertyName The property name to use.
	 * @return The generated &quot;proper&quot; name.
	 */
	protected String generateProperName(String propertyName)
	{
		char chars[] = propertyName.toCharArray();
		StringBuffer result = new StringBuffer();

		for(int i = 0; i < chars.length; ++i)
		{
			if(i == 0)
			{
				result.append(Character.toUpperCase(chars[i]));
			}
			else if(Character.isUpperCase(chars[i]))
			{
				result.append(' ');
				result.append(chars[i]);
			}
			else if(Character.isDigit(chars[i]))
			{
				result.append(' ');
				for(; i < chars.length && Character.isDigit(chars[i]); ++i)
				{
					result.append(chars[i]);
				}
				i--;
			}
			else
			{
				result.append(chars[i]);
			}
		}

		return result.toString();
	}

	/**
	 * Set entityClass.
	 *
	 * @param entityClass the value to set.
	 */
	public void setEntityClass(Class entityClass)
	{
		this.entityClass = entityClass;
	}

	/**
	 * Set entityName.
	 *
	 * @param entityName the value to set.
	 */
	public void setEntityName(String entityName)
	{
		this.entityName = entityName;
	}

	/**
	 * Set displayPropertyNames.
	 *
	 * @param displayPropertyNames the value to set.
	 */
	public void setDisplayPropertyNames(List<String> displayPropertyNames)
	{
		this.displayPropertyNames = displayPropertyNames;
	}

	/**
	 * Set displayNames.
	 *
	 * @param displayNames the value to set.
	 */
	public void setDisplayNames(Map<String, String> displayNames)
	{
		this.displayNames = displayNames;
	}

	/**
	 * Set excludeProperties.
	 *
	 * @param excludeProperties the value to set.
	 */
	public void setExcludeProperties(List<String> excludeProperties)
	{
		this.excludeProperties = excludeProperties;
	}
}

