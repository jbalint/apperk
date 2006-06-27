package apperk.jgoodies;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 * Test the {@link apperk.jgoodies.MapBackedValueModel}.
 */
public class MapBackedValueModelTest extends TestCase
{
	public void test() throws ParseException
	{
		// constant values for tests
		final String fName = "name";
		final String fAge = "age";

		final String name1 = "Johnny";
		final String name2 = "Sandy";

		DateFormat df = new SimpleDateFormat("MM-dd-yy");
		final Date date1 = df.parse("2005-01-17");
		final Date date2 = df.parse("2005-04-22");

		// setup the model
		Map<Object, Object> model = new HashMap<Object, Object>();
		model.put(fName, name1);
		model.put(fAge, date1);

		// create the value models
		MapBackedValueModel vmName = new MapBackedValueModel(model, fName);
		MapBackedValueModel vmAge = new MapBackedValueModel(model, fAge);

		// make sure we get the same values that are already there
		assertEquals(name1, vmName.getValue());
		assertEquals(date1, vmAge.getValue());

		// change the values
		vmName.setValue(name2);
		vmAge.setValue(date2);

		// make sure vm propagated the changes
		assertEquals(name2, model.get(fName));
		assertEquals(date2, model.get(fAge));
	}
}
