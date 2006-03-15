package apperk.validator;

/**
 * Interface for a single value-validation.
 */
public interface Validator
{
	/**
	 * Should be called to determine if the given object is valid according
	 * to this Validator object.
	 *
	 * @param o The object to check validity.
	 * @return Whether or not the object is valid.
	 */
	boolean isValid(Object o);

	/**
	 * Called to retrieve the message this validator would give to describe
	 * the constraint on the given field name.
	 *
	 * @param fieldName The field name.
	 * @return A message describing the constraint, suitable for user feedback.
	 */
	String getMessage(String fieldName);

	/**
	 * Called internally to provide user-specified parameters to
	 * this validator.
	 *
	 * @param p The user-given parameters.
	 */
	void setParams(String[] p);
}

