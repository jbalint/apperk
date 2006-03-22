package apperk.sqlreport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

/**
 * Tests for {@link apperk.sqlreport.ReportInputFactory} and
 * {@link apperk.sqlreport.ReportInputPanel}.
 * <p/>
 * <font color="red">TODO: this test is not good enough/need to change design of classes</font>
 */
public class ReportInputTest extends TestCase
{
	public void testXXX()
	{
		SqlReportDefinition def = new SqlReportDefinition();
		List<InputParameterDefinition> pdefs =
				new ArrayList<InputParameterDefinition>();
		def.setInputParameters(pdefs);
		InputParameterDefinition pdef;

		pdef = new InputParameterDefinition();
		pdef.setDisplay("Username");
		pdef.setName("user_name");
		pdef.setType("STRING");
		pdefs.add(pdef);

		pdef = new InputParameterDefinition();
		pdef.setDisplay("Birthdate");
		pdef.setName("birth_date");
		pdef.setType("DATE");
		pdefs.add(pdef);

		pdef = new InputParameterDefinition();
		pdef.setDisplay("New user");
		pdef.setName("add_user");
		pdef.setType("BOOLEAN");
		pdefs.add(pdef);

		pdef = new InputParameterDefinition();
		pdef.setDisplay("Age");
		pdef.setName("age_yrs");
		pdef.setType("INTEGER");
		pdefs.add(pdef);

		ReportInputPanel p = ReportInputFactory.createInputPanel(def);

		Map<String, Object> params = p.getInputParameters();

		// we are only checking default values here....
		assertEquals("", params.get("user_name"));
		//assertEquals(new Date(), params.get("birth_date"));
		assertEquals(false, params.get("add_user"));
		assertEquals(0, params.get("age_yrs"));

		assertNull(params.get("NON_EXIST_FIELD"));
	}
}
