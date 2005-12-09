package apperk.gencriteria;

public abstract class Operator
{
	public static final Operator GREATER_THAN = new GreaterThan();

	protected final String stringView;
	protected final int minParams;
	protected final int maxParams;

	private Operator(String stringView, int minParams, int maxParams)
	{
		this.stringView = stringView;
		this.minParams = minParams;
		this.maxParams = maxParams;
	}

	protected abstract Object doGetResult(Object value, Object[] params);
	protected abstract String doGetDescription(Object[] params);

	private void checkParams(Object[] params)
	{
		int size = params.length;

		if(size < minParams || size > maxParams)
		{
			if(minParams == maxParams)
			{
				throw new IllegalArgumentException(String.format(
							"Must have %d params, but %d were given",
							minParams, size));
			}
			else
			{
				throw new IllegalArgumentException(String.format(
							"Must have between %d and %d params, " +
							"but %d were given",
							minParams, maxParams, size));
			}
		}

		doCheckParams(params);
	}

	protected void doCheckParams(Object[] params) { }

	protected void doCheckValue(Object value) { }

	public Object getResult(Object value, Object[] params)
	{
		checkParams(params);
		doCheckValue(value);
		return doGetResult(value, params);
	}

	public String getDescription(Object[] params)
	{
		checkParams(params);
		return doGetDescription(params);
	}

	private static class GreaterThan extends Operator
	{
		private GreaterThan()
		{
			super(">", 1, 1);
		}

		protected Object doGetResult(Object value, Object[] params)
		{
			return ((Number)value).doubleValue() >
				((Number)params[0]).doubleValue();
		}

		protected String doGetDescription(Object[] params)
		{
			return String.format("is greater than %d", params[0]);
		}

		protected void doCheckParams(Object[] params)
		{
			for(int i = 0; i < params.length; ++i)
			{
				if(!Number.class.isAssignableFrom(params[i].getClass()))
				{
					throw new IllegalArgumentException(String.format(
								"Argument %d is not a descendant of " +
								"java.lang.Number: %s",
								i, params[i].getClass()));
				}
			}
		}
	}

	private static class Between extends Operator
	{
		private Between()
		{
			super("between", 2, 2);
		}

		protected Object doGetResult(Object value, Object[] params)
		{
			return (((Number)value).doubleValue() >=
				((Number)params[0]).doubleValue()) &&
				(((Number)value).doubleValue() <=
				((Number)params[1]).doubleValue()) &&;
		}

		protected String doGetDescription(Object[] params)
		{
			return String.format("is between %d and %d", params[0], params[1]);
		}

		protected void doCheckParams(Object[] params)
		{
			for(int i = 0; i < params.length; ++i)
			{
				if(!Number.class.isAssignableFrom(params[i].getClass()))
				{
					throw new IllegalArgumentException(String.format(
								"Argument %d is not a descendant of " +
								"java.lang.Number: %s",
								i, params[i].getClass()));
				}
			}
		}
	}

	public String getStringView()
	{
	    return stringView;
	}

	public int getMinParams()
	{
	    return minParams;
	}

	public int getMaxParams()
	{
	    return maxParams;
	}
}

