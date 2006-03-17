package apperk.sqlreport;

import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.jgoodies.binding.value.Trigger;
import com.jgoodies.validation.ValidationResult;

/**
 * The form used to input parameter values from the user for a report. Will
 * normally be created from {@link ReportInputFactory}.
 * <p/>
 * <font color="red">Implement Validation</font>
 */
public class ReportInputPanel extends JPanel
{
	protected JButton btnOk = new JButton("OK");
	protected JButton btnCancel = new JButton("Cancel");

	protected Map<String, Object> inputParams;
	protected Trigger triggerChannel = new Trigger();

	/**
	 * Create a new report input panel. The given map will be used directly
	 * for the fields. The value model will be buffered and the parameters
	 * will not be committed until {@link #getInputParameters} is called.
	 * <p/>
	 * Optionally, you can retrieve the trigger channel and commit or flush
	 * the value buffer manually.
	 *
	 * @param inputParams A map with the default values for the fields.
	 */
	public ReportInputPanel(Map<String, Object> inputParams)
	{
		this.inputParams = inputParams;
	}

	/**
	 * <font color="red">IMPLEMENT THIS</font>
	 */
	public ValidationResult validateInput()
	{
		throw new IllegalArgumentException("NO VALiDATION YET");
	}

	/**
	 * Commit the edit buffer and return the input parameter map.
	 *
	 * @return The input parameter map.
	 */
	public Map<String, Object> getInputParameters()
	{
		triggerChannel.triggerCommit();
		return inputParams;
	}

	/**
	 * The trigger channel used for the input buffering.
	 *
	 * @return The trigger channel value model.
	 */
	public Trigger getTriggerChannel()
	{
		return triggerChannel;
	}

	/**
	 * Provide access to the &quot;OK&quot; button used on the input
	 * panel. This returns the actual object. This means it will send
	 * events to an added ActionListeners.
	 * <p/>
	 * If this ReportInputPanel is created by {@link ReportInputFactory},
	 * this button will appear with the Cancel button in a button bar at
	 * the bottom of the panel.
	 *
	 * @return The button.
	 */
	public JButton getBtnOk()
	{
		return btnOk;
	}

	/**
	 * Provide access to the &quot;Cancel&quot; button. Same notes apply from
	 * &quot;OK&quot; button.
	 *
	 * @see #getBtnOk
	 * @return The button.
	 */
	public JButton getBtnCancel()
	{
		return btnCancel;
	}
}

