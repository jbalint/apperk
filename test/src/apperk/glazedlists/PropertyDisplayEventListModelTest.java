package apperk.glazedlists;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import junit.framework.TestCase;

public class PropertyDisplayEventListModelTest extends TestCase
{
	/**
	 * A simple JavaBeans style class with a property to test with.
	 */
	private static final class SomeBean
	{
		private String name;

		public SomeBean(String name)
		{
			this.name = name;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public String getSomethingBad()
		{
			// throw NPE on purpose for testing
			Object o = null;
			return ((String)o).toLowerCase();
		}

		public void setSomethingBad(String s)
		{
			throw new IllegalArgumentException("xxx");
		}

		public String getReadOnlyProp()
		{
			return null;
		}

		public boolean equals(Object o)
		{
			SomeBean sb = (SomeBean)o;
			return sb.getName().equals(name);
		}
	}

	private List<SomeBean> someBeans = new ArrayList<SomeBean>();
	private EventList<SomeBean> beansEventList;

	protected void setUp()
	{
		SomeBean bean;
		bean = new SomeBean("Adrienne");
		someBeans.add(bean);
		bean = new SomeBean("Brandon");
		someBeans.add(bean);
		bean = new SomeBean("Carlos");
		someBeans.add(bean);

		beansEventList = GlazedLists.eventList(someBeans);
	}

	public void testNormalUse()
	{
		PropertyDisplayEventListModel model =
			new PropertyDisplayEventListModel(beansEventList, "name");

		// test basic access
		assertEquals("Adrienne", model.getElementAt(0));
		assertEquals("Carlos", model.getElementAt(2));
		try
		{
			model.getElementAt(3);
			fail("Should not be able to retrieve value out of index range");
		}
		catch(IndexOutOfBoundsException ex)
		{
		}

		// test get/set entity+item
		assertNull(model.getSelectedEntity());
		model.setSelectedItem("Brandon");
		assertEquals(new SomeBean("Brandon"), model.getSelectedEntity());
		assertEquals("Brandon", model.getSelectedItem());

		// test get/set item w/missing entity
		String missingName = "Name not in list";
		model.setSelectedItem(missingName);
		assertNull(model.getSelectedEntity());
		assertEquals(missingName, model.getSelectedItem());
	}

	/* complete the impl of this test when exception handling in the
	 * class is fixed */
	public void testProblemSituations()
	{
		PropertyDisplayEventListModel model =
			new PropertyDisplayEventListModel(beansEventList, "somethingBad");
		try
		{
			model.setSelectedItem("x");
			//fail("Should throw InvocationTargetException accessing somethingBad");
		}
		//catch(InvocationTargetException ex)
		catch(Exception ex)
		{
		}

		model =
			new PropertyDisplayEventListModel(beansEventList, "readOnlyProp");
		model.setSelectedItem("x");
		// TODO: need to figure out how to get an IllegalAccessException
		// throw in the model class
		/*
		model =
			new PropertyDisplayEventListModel(beansEventList, "privateProp");
		model.setSelectedItem("x");
		*/
	}
}

