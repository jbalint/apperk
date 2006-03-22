package apperk.sqlreport;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a single input parameter for a report. Contains all the
 * information needed to present an interface to the user to enter a
 * value.
 */
public class InputParameterDefinition implements Serializable
{
	private String name;
	private String display;
	private String type;
	private String validationRules;
	private List displayValues;
	private List paramValues;

	/**
	 * Get field name. This is the name of the parameter that will be bound
	 * on the report query.
	 *
	 * @return name as String.
	 */
	public String getName()
	{
	    return name;
	}

	/**
	 * Set field name.
	 *
	 * @param name the value to set.
	 */
	public void setName(String name)
	{
	    this.name = name;
	}

	/**
	 * Get display string. This is a string that will be displayed to the user
	 * when asking for input.
	 *
	 * @return display as String.
	 */
	public String getDisplay()
	{
	    return display;
	}

	/**
	 * Set display string.
	 *
	 * @param display the value to set.
	 */
	public void setDisplay(String display)
	{
	    this.display = display;
	}

	/**
	 * Get parameter type.
	 *
	 * @see ReportInputFactory
	 * @return type as String.
	 */
	public String getType()
	{
	    return type;
	}

	/**
	 * Set parameter type.
	 *
	 * @param type the value to set.
	 */
	public void setType(String type)
	{
	    this.type = type;
	}

	/**
	 * Get validation rules.
	 *
	 * @see apperk.validator.InputValidatorFactory
	 * @return validation rules
	 */
	public String getValidationRules()
	{
		return validationRules;
	}

	/**
	 * Set validation rules.
	 *
	 * @param validationRules the validation rules.
	 */
	public void setValidationRules(String validationRules)
	{
		this.validationRules = validationRules;
	}

	/**
	 * Get display values. A list of display values. If this list is present,
	 * but paramValues is null, the choices here will be presented in a
	 * combo box and the result will be bound to the report parameter.
	 * <p/>
	 * If paramValues is also given, these lists will be assumed equal size
	 * and same order. The matching object will be taken from paramValues
	 * based on the selected index in this list. This matching object will
	 * be the one that is bound to the report query.
	 * <p/>
	 * The reason this was created was to use an object attribute,
	 * eg. &quot;name&quot; for the user to select from. However the
	 * query will be run with eg. the &quot;id&quot; attribute of the
	 * object.
	 * <p/>
	 * <font color="red">TODO: util class to pull attribute lists from
	 * lists of objects</font> (and mention it here)
	 *
	 * @return displayValues as List.
	 */
	public List getDisplayValues()
	{
	    return displayValues;
	}

	/**
	 * Set display values.
	 *
	 * @param displayValues the value to set.
	 */
	public void setDisplayValues(List displayValues)
	{
	    this.displayValues = displayValues;
	}

	/**
	 * Get param values. The actual values that will be bound to query
	 * parameters. This is only valid when displayValues is given.
	 *
	 * @return paramValues as List.
	 */
	public List getParamValues()
	{
	    return paramValues;
	}

	/**
	 * Set param values.
	 *
	 * @param paramValues the value to set.
	 */
	public void setParamValues(List paramValues)
	{
	    this.paramValues = paramValues;
	}
}

