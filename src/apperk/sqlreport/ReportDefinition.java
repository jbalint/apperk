package apperk.sqlreport;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Encapsulate a generic report definition. This contains the list of input
 * parameters which are used to obtain inputs for the report. It also contains
 * a number of things for output representation. It is expected that the
 * client will handle view-specific portions, eg. column-width, row coloring
 * constraints, etc.
 */
public class ReportDefinition
{
	private String name;
	private Map<String, String> fieldDisplayNames;
	private Set<String> availableFields;
	private List<String> defaultViewFields;
	private Map<String, String> entityRefFields;
	private List<InputParameterDefinition> inputParameters;

	/**
	 * The formal name of the report.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * The display names for the fields. This maps the internal query names of
	 * the fields to something more understandable to the user.
	 *
	 * @return the map
	 */
	public Map<String, String> getFieldDisplayNames()
	{
		return fieldDisplayNames;
	}

	public void setFieldDisplayNames(Map<String, String> fieldDisplayNames)
	{
		this.fieldDisplayNames = fieldDisplayNames;
	}

	/**
	 * Provides a list of available fields (internal names) so that a client
	 * application can present the user with a list and let them select which
	 * fields they want to see.
	 * <p/>
	 * <font color="red">TODO: figure out how this plays into the situation
	 * with available fields on entities</font>
	 *
	 * @return the available fields.
	 */
	public Set<String> getAvailableFields()
	{
		return availableFields;
	}

	public void setAvailableFields(Set<String> availableFields)
	{
		this.availableFields = availableFields;
	}

	/**
	 * A list (order intended) of view fields (internal names) that should
	 * as a default display. This can be overridden by user (or other)
	 * preference.
	 *
	 * @return the default fields.
	 */
	public List<String> getDefaultViewFields()
	{
		return defaultViewFields;
	}

	public void setDefaultViewFields(List<String> defaultViewFields)
	{
		this.defaultViewFields = defaultViewFields;
	}

	/**
	 * Allow entity references to be embedded in the report. This maps
	 * a internal field name to a class name. The field in the report row
	 * referenced by the internal name will contain a unique id that will
	 * allow loading the entity for editing or additional display in the
	 * report.
	 *
	 * @return the map.
	 */
	public Map<String, String> getEntityRefFields()
	{
		return entityRefFields;
	}

	public void setEntityRefFields(Map<String, String> entityRefFields)
	{
		this.entityRefFields = entityRefFields;
	}

	/**
	 * Provide access to the input parameter definitions. These are used to
	 * know the fields and their corresponding types when prompting the user
	 * for input. It is intended that the input form will be built dynamically
	 * based on this.
	 *
	 * @return the input param defs.
	 */
	public List<InputParameterDefinition> getInputParameters()
	{
		return inputParameters;
	}

	public void setInputParameters(
			List<InputParameterDefinition> inputParameters)
	{
		this.inputParameters = inputParameters;
	}
}

