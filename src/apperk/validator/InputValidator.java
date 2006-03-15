package apperk.validator;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface to using validators. An instance of this should be obtained
 * from the {@link InputValidatorFactory}.
 */
public class InputValidator
{
	/**
	 * A validator which constrains a value to being defined. This checks
	 * if an object is null or an empty string and fails validation,
	 * otherwise passes.
	 * <p/>
	 * Accepts zero parameters.
	 */
	public static class RequiredValidator implements Validator
	{
		public boolean isValid(Object o)
		{
			if (o == null)
				return false;
			if (o instanceof String && o.equals(""))
				return false;
			return true;
		}

		public String getMessage(String fieldName)
		{
			return String.format("%s is required", fieldName);
		}

		public void setParams(String[] params)
		{
			if(params.length > 0)
				throw new IllegalArgumentException(
					"Required validation does not take params");
		}
	}

	/**
	 * A validator which constrains a value to being a number greater than
	 * or equal to a user-specified minimum. Fails validation if the input
	 * is not a number.
	 * <p/>
	 * Accepts one number parameter as &quot;minimum&quot;.
	 */
	public static class MinValidator implements Validator
	{
		/** Minimum value. */
		protected Number min;

		public boolean isValid(Object o)
		{
			if (o instanceof String)
			{
				try
				{
					o = new Double((String)o);
				}
				catch (NumberFormatException ex)
				{
					return false;
				}
			}
			else if (!(o instanceof Number))
				return false;
			return ((Number)o).doubleValue() >= min.doubleValue();
		}

		public String getMessage(String fieldName)
		{
			return String.format("%s must be greater than or equal to %s",
				fieldName, min);
		}

		public void setParams(String[] p)
		{
			if (p.length != 1)
				throw new IllegalArgumentException(
					"Min validator takes one number parameter");
			if (p[0].contains("."))
				min = new Double(p[0]);
			else
				min = new Long(p[0]);
		}
	}

	/**
	 * A validator which constrains a value to being a number less than
	 * or equal to a user-specified maximum. Fails validation if the input
	 * is not a number.
	 * <p/>
	 * Accepts one number parameter as &quot;maximum&quot;.
	 */
	public static class MaxValidator implements Validator
	{
		/** Maximum value. */
		protected Number max;

		public boolean isValid(Object o)
		{
			if (o instanceof String)
			{
				try
				{
					o = new Double((String)o);
				}
				catch (NumberFormatException ex)
				{
					return false;
				}
			}
			else if (!(o instanceof Number))
				return false;
			return ((Number)o).doubleValue() <= max.doubleValue();
		}

		public String getMessage(String fieldName)
		{
			return String.format("%s must be less than or equal to %s",
				fieldName, max);
		}

		public void setParams(String[] p)
		{
			if (p.length != 1)
				throw new IllegalArgumentException(
					"Max validator takes one number parameter");
			if (p[0].contains("."))
				max = new Double(p[0]);
			else
				max = new Long(p[0]);
		}
	}

	/**
	 * A validator which constrains a value to being a number within a
	 * user-specified range. Fails validation if the input is not a number.
	 * <p/>
	 * Accepts two number parameters as range bounds
	 * (&quot;minimum&quot; and &quot;maximum&quot;).
	 */
	public static class RangeValidator implements Validator
	{
		/** Range lower bound. */
		protected Number min;
		/** Range upper bound. */
		protected Number max;

		public boolean isValid(Object o)
		{
			if (o instanceof String)
			{
				try
				{
					o = new Double((String)o);
				}
				catch (NumberFormatException ex)
				{
					return false;
				}
			}
			else if (!(o instanceof Number))
				return false;

			double v = ((Number)o).doubleValue();
			if (v > max.doubleValue())
				return false;
			return v >= min.doubleValue();
		}

		public String getMessage(String fieldName)
		{
			return String.format("%s must be between %s and %s",
				fieldName, min, max);
		}

		public void setParams(String[] p)
		{
			if (p.length != 2)
				throw new IllegalArgumentException(
					"Range validator takes two number parameters");

			if (p[0].contains("."))
				min = new Double(p[0]);
			else
				min = new Long(p[0]);

			if (p[1].contains("."))
				max = new Double(p[1]);
			else
				max = new Long(p[1]);
		}
	}

	/** Internal validators. */
	protected List<Validator> validators = new ArrayList<Validator>();

	/**
	 * Create a new input validator instance with the given
	 * field validators.
	 *
	 * @param validators The field validators for this input validator.
	 */
	InputValidator(List<Validator> validators)
	{
		this.validators = validators;
	}

	/**
	 * Validate a given value using this input validator. Check the object
	 * against all internal validators and returns a list of message from
	 * failed validations.
	 *
	 * @param fieldName Field name used to construct validation messages.
	 * @param val The value to validate.
	 * @return A list of messages regarding the field. They will be in the same
	 * 			order as the list of internal validators.
	 */
	public List<String> validate(String fieldName, Object val)
	{
		List<String> result = new ArrayList<String>();

		for (Validator v : validators)
		{
			if (!v.isValid(val))
				result.add(v.getMessage(fieldName));
		}

		return result;
	}
}

