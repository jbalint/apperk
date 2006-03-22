package apperk.validator;

import java.util.List;

import apperk.validator.InputValidator;
import apperk.validator.InputValidatorFactory;
import junit.framework.TestCase;

/**
 * Tests for the validator library.
 */
public class InputValidatorTest extends TestCase
{
	List<String> results;
	InputValidator iv;

	/**
	 * Test some exceptional cases.
	 */
	public void testException()
	{
		try
		{
			// no exists
			InputValidatorFactory.createInputValidator("junkie");
			fail("Invalid validator");
		}
		catch(IllegalArgumentException ex)
		{}
	}

	/**
	 * Test a couple different combinations.
	 */
	public void testFew()
	{
		iv = InputValidatorFactory
				.createInputValidator("required(),min(0),range(2.2,4)");

		results = iv.validate("age", "");

		assertEquals(3, results.size());
		assertEquals("age is required", results.get(0));
	}

	/**
	 * Test the required() validator.
	 */
	public void testRequired()
	{
		iv = InputValidatorFactory.createInputValidator("required");

		results = iv.validate("age", "");
		assertEquals(1, results.size());

		results = iv.validate("age", "x");
		assertEquals(0, results.size());

		results = iv.validate("age", null);
		assertEquals(1, results.size());

		// non-null value of unknown class validates true
		results = iv.validate("age", this);
		assertEquals(0, results.size());

		try
		{
			InputValidatorFactory.createInputValidator("required(x)");
			fail("required() takes no parameter");
		}
		catch(IllegalArgumentException ex)
		{}
	}

	/**
	 * Test all aspects of the max() validator.
	 */
	public void testMax()
	{
		iv = InputValidatorFactory.createInputValidator("max(7)");

		results = iv.validate("age", "xx");
		assertEquals(1, results.size());
		assertEquals("age must be less than or equal to 7", results.get(0));

		results = iv.validate("age", "12");
		assertEquals(1, results.size());

		results = iv.validate("age", "4");
		assertEquals(0, results.size());

		results = iv.validate("age", 7);
		assertEquals(0, results.size());

		try
		{
			InputValidatorFactory.createInputValidator("max");
			fail("max() must be given parameter");
		}
		catch(IllegalArgumentException ex)
		{}

		try
		{
			InputValidatorFactory.createInputValidator("max(1,1)");
			fail("max() must be given only one parameter");
		}
		catch(IllegalArgumentException ex)
		{}

		iv = InputValidatorFactory.createInputValidator("max(7.2)");
		results = iv.validate("age", this);
		assertEquals(1, results.size());
	}

	/**
	 * Test all aspects of the min() validator.
	 */
	public void testMin()
	{
		iv = InputValidatorFactory.createInputValidator("min(7)");

		results = iv.validate("age", "xx");
		assertEquals(1, results.size());
		assertEquals("age must be greater than or equal to 7",
				results.get(0));

		results = iv.validate("age", "2");
		assertEquals(1, results.size());

		results = iv.validate("age", "8");
		assertEquals(0, results.size());

		results = iv.validate("age", 7);
		assertEquals(0, results.size());

		try
		{
			InputValidatorFactory.createInputValidator("min");
			fail("min() must be given parameter");
		}
		catch(IllegalArgumentException ex)
		{}

		try
		{
			InputValidatorFactory.createInputValidator("min(1,1)");
			fail("min() must be given only one parameter");
		}
		catch(IllegalArgumentException ex)
		{}

		iv = InputValidatorFactory.createInputValidator("min(7.2)");
		results = iv.validate("age", this);
		assertEquals(1, results.size());
	}

	/**
	 * Test the range validator, specifically around boundary conditions.
	 */
	public void testRange()
	{
		////////////////////////////////////////
		iv = InputValidatorFactory.createInputValidator("range(2.2,4)");

		// test a blank string is not valid
		results = iv.validate("age", "");
		assertEquals(1, results.size());

		// test the lower floating point boundary
		results = iv.validate("age", 2.2);
		assertEquals(0, results.size());

		// test a normal valid case
		results = iv.validate("age", 3);
		assertEquals(0, results.size());

		// test the upper integer boundary
		results = iv.validate("age", 4);
		assertEquals(0, results.size());

		// test close out of bounds
		results = iv.validate("age", 2.19);
		assertEquals(1, results.size());
		assertEquals("age must be between 2.2 and 4", results.get(0));
		results = iv.validate("age", 4.01);
		assertEquals(1, results.size());
		assertEquals("age must be between 2.2 and 4", results.get(0));

		// test integer out of bounds
		results = iv.validate("age", 5);
		assertEquals(1, results.size());
		assertEquals("age must be between 2.2 and 4", results.get(0));

		////////////////////////////////////////
		iv = InputValidatorFactory.createInputValidator("range(2,4.1)");

		results = iv.validate("age", "2");
		assertEquals(0, results.size());

		////////////////////////////////////////
		// exception tests
		try
		{
			InputValidatorFactory.createInputValidator("range");
			fail("Shouldnt accept no parameters");
		}
		catch(IllegalArgumentException ex)
		{}

		try
		{
			InputValidatorFactory.createInputValidator("range(2)");
			fail("Shouldnt accept only one parameter");
		}
		catch(IllegalArgumentException ex)
		{}

		try
		{
			InputValidatorFactory.createInputValidator("range(2,2,2)");
			fail("Shouldnt accept three parameters");
		}
		catch(IllegalArgumentException ex)
		{}

		// test non-related class is invalid
		results = iv.validate("age", this);
		assertEquals(1, results.size());
	}
}

