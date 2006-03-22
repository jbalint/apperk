package apperk.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import apperk.util.ClassMapResourceLoader;
import org.apache.log4j.Logger;

/**
 * Factory used to obtain InputValidator instances. This parses a given string
 * and creates an InputValidator from the given validation rules.
 * <p/>
 * The default configuration is loaded from a classpath resource
 * <span style="font-style: italic">
 * /apperk/validator/apperk.validators.properties
 * </span>. This loads the following validators:
 * <ul>
 * <li><span style="font-weight: bold">required
 * </span> - {@link InputValidator.RequiredValidator}</li>
 * <li><span style="font-weight: bold">min
 * </span> - {@link InputValidator.MinValidator}</li>
 * <li><span style="font-weight: bold">max
 * </span> - {@link InputValidator.MaxValidator}</li>
 * <li><span style="font-weight: bold">range
 * </span> - {@link InputValidator.RangeValidator}</li>
 * </ul>
 * <p/>
 * <font color="red">TODO: allow user to add another properties file
 * with their validators</font>
 *
 * @see #createInputValidator
 */
public class InputValidatorFactory
{
	private static final Logger log =
		Logger.getLogger(InputValidatorFactory.class);

	private static final String CLASS_MAP_RESOURCE =
		"/apperk/validator/apperk.validators.properties";

	private static Map<String, Class> validatorClasses;

	static
	{
		// Load the validator class map
		validatorClasses = ClassMapResourceLoader.load(CLASS_MAP_RESOURCE);
	}

	public InputValidatorFactory()
	{
	}

	/**
	 * Create a new {@link InputValidator} based on the given validation
	 * string. The string is of the format
	 * <span style="font-style: italic">
	 * validator-name(param1, param2, ...),...
	 * </span>
	 * <p/>
	 * Parens can be omitted with validators that don't accept any parameters.
	 * <p/>
	 * Example:
	 * <span style="font-style: italic">
	 * required(),range(2.4, 7)
	 * </span>
	 * or
	 * <span style="font-style: italic">
	 * required,min(2.4),max(7)
	 * </span>
	 * <p/>
	 * <font color="red">TODO: handle parsing problems</font>
	 *
	 * @param validationString Describe the needed validator
	 * @return A validator to handle the requested validations
	 */
	public static InputValidator createInputValidator(String validationString)
	{
		List<Validator> validators = new ArrayList<Validator>();

		String[] rulesary = validationString.split(",");

		for(int i = 0; i < rulesary.length; ++i)
		{
			String s = rulesary[i];
			while(s.contains("(") && !s.contains(")"))
			{
				s += "," + rulesary[++i];
			}

			String[] p = s.split("\\(|\\)");
			String validatorName = p[0];
			String[] params = new String[0];

			if(p.length > 1)
				params = p[1].split(",");

			Class c = validatorClasses.get(validatorName);
			Validator validator;

			// Make sure this is an existing validator
			if(c == null)
				throw new IllegalArgumentException(
					"Validator " + validatorName + " not found");

			// create a new instance of the validator
			try
			{
				validator = (Validator)c.newInstance();
				validator.setParams(params);
			}
			catch(ClassCastException ex)
			{
				throw new IllegalArgumentException(
					String.format("Class '%s' for validator '%s' " +
						"does not implement apperk.validator.Validator",
						c.getName(), validatorName), ex);
			}
			catch(Exception ex)
			{
				throw new IllegalArgumentException(String.format(
						"Error using class '%s' for validator '%s'",
						c.getName(), validatorName), ex);
			}

			validators.add(validator);
		}

		return new InputValidator(validators);
	}
}

