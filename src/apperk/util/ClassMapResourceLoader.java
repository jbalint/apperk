package apperk.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Utility class to load a Map&lt;String, Class&gt; from a
 * classpath resource.
 */
public class ClassMapResourceLoader
{
	private static final Logger log =
		Logger.getLogger(ClassMapResourceLoader.class);

	/**
	 * Load the map from the given classpath resource. All classnames will
	 * be resolved and a Class object will be placed as the value in the map.
	 * <p/>
	 * The classpath resource should be a properties field of the format:
	 * <br/>
	 * key=com.package.Classname
	 * <p/>
	 * If there is an error loading the resource, it will be logged and an
	 * empty Map will be returned.
	 *
	 * @param resourceName The classpath resource to load.
	 * @return The newly created map from the given resource.
	 */
	public static Map<String, Class> load(String resourceName)
	{
		Map<String, Class> result = new HashMap<String, Class>();
		Properties p = new Properties();

		try
		{
			p.load(ClassMapResourceLoader.class.getResourceAsStream(
					   resourceName));
		}
		catch(IOException ex)
		{
			log.error("Cannot load resource: " + resourceName, ex);
		}

		for(Enumeration e = p.propertyNames(); e.hasMoreElements(); )
		{
			String prop = (String)e.nextElement();
			Class c;

			try
			{
				c = Class.forName(p.getProperty(prop));
			}
			catch(ClassNotFoundException ex)
			{
				log.error(String.format(
							  "Class not found %s for key %s (resource=%s)",
							  p.getProperty(prop), prop, resourceName));
				continue;
			}

			result.put(prop, c);
		}

		return result;
	}
}

