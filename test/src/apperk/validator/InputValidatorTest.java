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
	/**
	 * Test a couple different combinations.
	 */
	public void testFew()
	{
		InputValidator iv = InputValidatorFactory.createInputValidator(
			"required(),min(0),range(2.2,4)");
		List<String> results;

		results = iv.validate("age", "");

		assertEquals(3, results.size());
		assertEquals("age is required", results.get(0));
	}

	/**
	 * Test the range validator, specifically around boundary conditions.
	 */
	public void testRange()
	{
		InputValidator iv = InputValidatorFactory
			.createInputValidator("range(2.2,4)");
		List<String> results;

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
	}
}

