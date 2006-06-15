package apperk.sqlreport;

import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import apperk.jgoodies.MapBackedValueModel;
import apperk.util.ClassMapResourceLoader;

import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.value.BufferedValueModel;
import com.jgoodies.binding.value.ConverterFactory;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.toedter.calendar.JDateChooser;

/**
 * Factory used to create the input UI for a report. This uses an internal
 * properties file called
 * <span style="font-style: italic">
 * /apperk/sqlreport/apperk.sqlreport.inputtypes.properties
 * </span>. This file lists the allowed input parameter types and what class
 * is used to handle it.
 * <p/>
 * All type implementation classes are inner classes of this class. Currently
 * supported values are:
 * <ul>
 * <li>STRING</li>
 * <li>DATE</li>
 * <li>BOOLEAN</li>
 * <li>INTEGER</li>
 * </ul>
 * <p/>
 * The base method used to create the input UI is {@link #createInputPanel}.
 * It's not the most flexible to the user (yet).
 * <p/>
 * <font color="red">TODO: allow new input parameter types to be loaded</font>.
 * <font color="red">TODO: write the list selection code and figure out how to integrate with Spring to call service methods</font>.
 * <font color="red">TODO: allow setting the default component width</font>.
 */
public class ReportInputFactory
{
	/** Logger. */
	private static final Logger log =
		Logger.getLogger(ReportInputFactory.class);

	private static final String CLASS_MAP_RESOURCE =
		"/apperk/sqlreport/apperk.sqlreport.inputtypes.properties";

	private static Map<String, InputType> inputTypes =
		new HashMap<String, InputType>();

	static
	{
		// Load the input type class map
		Map<String, Class> inputTypeClasses =
			ClassMapResourceLoader.load(CLASS_MAP_RESOURCE);

		// Create an instance of all the input types
		for(Map.Entry<String, Class> entry : inputTypeClasses.entrySet())
		{
			InputType it;

			try
			{
				it = (InputType)entry.getValue().newInstance();
			}
			catch(Exception ex)
			{
				log.error(String.format("Failed to instantiate class %s for " +
						"input type %s", entry.getValue().getName(),
						entry.getKey()), ex);
				continue;
			}

			inputTypes.put(entry.getKey(), it);
		}
	}

	/**
	 * Default minimum component width. If components are
	 * less wide than this, their preferred size will be
	 * expanded.
	 */
	protected static final int DEFAULT_COMPONENT_WIDTH = 150;

	/**
	 * Default column specifications. Just so we have the
	 * labels on the left and the edit components on the
	 * right with a thin gap in between.
	 */
	protected static final ColumnSpec[] DEFAULT_COLSPECS = new ColumnSpec[] {
		FormFactory.DEFAULT_COLSPEC,
		FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
		FormFactory.DEFAULT_COLSPEC
	};

	public ReportInputFactory()
	{
	}

	/**
	 * Creates a new ReportInputPanel that can be used to get the input
	 * from the user.
	 * <p/>
	 * All ValueModels created in this method will be buffered using the
	 * trigger channel from the returned report input panel.
	 *
	 * @see ReportInputPanel
	 * @param def The report definition.
	 * @return The new report input panel.
	 */
	public static ReportInputPanel createInputPanel(ReportDefinition def)
	{
		// create the panel
		Map<String, Object> model = buildModel(def);
		ReportInputPanel panel = new ReportInputPanel(model);
		int paramCount = def.getInputParameters().size();
		RowSpec[] rows = createRowSpecs(paramCount);
		FormLayout layout = new FormLayout(DEFAULT_COLSPECS, rows);
		panel.setLayout(layout);
		panel.setBorder(Borders.TABBED_DIALOG_BORDER);

		CellConstraints cc = new CellConstraints();

		// add each row (label+edit component) to the panel
		for(int i = 0; i < paramCount; ++i)
		{
			InputParameterDefinition pdef = def.getInputParameters().get(i);
			JLabel name = new JLabel(pdef.getDisplay());
			ValueModel vm1 =
				new MapBackedValueModel(model, pdef.getName());
			ValueModel valueModel =
				new BufferedValueModel(vm1, panel.getTriggerChannel());
			// TODO: handle list/ComboBox stuff here or somewhere
			JComponent editComponent = inputTypes.get(pdef.getType())
				.getBoundEditComponent(valueModel);

			/////////////////////////////
			// TODO: allow editing this?
			Dimension size = editComponent.getPreferredSize();
			if(size.width < DEFAULT_COMPONENT_WIDTH)
				size.width = DEFAULT_COMPONENT_WIDTH;
			editComponent.setPreferredSize(size);
			/////////////////////////////

			int componentRow = 2 * i + 1; // (+1)=FormLayout starts rows at 1
			panel.add(name, cc.xy(1, componentRow));
			panel.add(editComponent, cc.xy(3, componentRow));
		}

		// Create & add the button bar to the last row
		JPanel buttons = ButtonBarFactory
			.buildOKCancelBar(panel.getBtnOk(), panel.getBtnCancel());
		panel.add(buttons, cc.xyw(1, paramCount * 2 + 1, 3, "center, fill"));

		return panel;
	}

	/**
	 * Build the Map that we will use as the model for the input parameters.
	 *
	 * @param def The report definition.
	 * @return A newly created map keyed with the input parameters names, and
	 * 			defaults for the values.
	 */
	protected static Map<String, Object> buildModel(ReportDefinition def)
	{
		Map<String, Object> model = new HashMap<String, Object>();

		for(InputParameterDefinition pdef : def.getInputParameters())
			model.put(pdef.getName(),
				inputTypes.get(pdef.getType()).getDefaultValue());

		return model;
	}

	/**
	 * Create a RowSpec array suitable for the input form. Creates a row
	 * for every input field, with line gaps inbetween and a final row
	 * for a button bar.
	 * <p/>
	 * Based on the RowSpecs from the JGoodies {@link FormFactory}.
	 *
	 * @param rowCount The number of input field rows needed.
	 * @return The new RowSpec array.
	 */
	protected static RowSpec[] createRowSpecs(int rowCount)
	{
		RowSpec[] rows = new RowSpec[rowCount * 2 + 1];

		for(int i = 0; i < rowCount; ++i)
		{
			rows[2 * i] = FormFactory.DEFAULT_ROWSPEC;
			rows[2 * i + 1] = FormFactory.LINE_GAP_ROWSPEC;
		}
		rows[2 * rowCount] = FormFactory.DEFAULT_ROWSPEC;

		return rows;
	}

	/**
	 * An interface to handle an input parameter type.
	 */
	public static interface InputType
	{
		/**
		 * Obtain the default value that will be bound to the value model.
		 *
		 * @return default value
		 */
		Object getDefaultValue();

		/**
		 * Create a JComponent used to edit the given value model. This method
		 * must bind the given value model to the returned component.
		 *
		 * @param valueModel The value model to bind to.
		 * @return A JComponent suitable for editing the value model's value.
		 */
		JComponent getBoundEditComponent(ValueModel valueModel);
	}

	/**
	 * Implementation to handle the input of integers. This implementation
	 * uses a JTextField for input and a default value of zero.
	 */
	public static class IntegerType implements InputType
	{
		public Integer getDefaultValue()
		{
			return 0;
		}

		/**
		 * Create a new JTextField and bind to the ValueModel through a
		 * String converter using {@link DecimalFormat}.
		 */
		public JComponent getBoundEditComponent(ValueModel valueModel)
		{
			JTextField textField = new JTextField();
			ValueModel converted = ConverterFactory
				.createStringConverter(valueModel, new DecimalFormat("#"));
			Bindings.bind(textField, converted);
			return textField;
		}
	}

	/**
	 * Implementation to handle the input of booleans. This implementation
	 * uses a JCheckBox for input and a default value of false.
	 */
	public static class BooleanType implements InputType
	{
		public Boolean getDefaultValue()
		{
			return false;
		}

		public JComponent getBoundEditComponent(ValueModel valueModel)
		{
			JCheckBox checkBox = new JCheckBox();
			Bindings.bind(checkBox, valueModel);
			return checkBox;
		}
	}

	/**
	 * Implementation to handle the input of strings. This implementation
	 * uses a JTextField for input and a default value of the empty string.
	 */
	public static class StringType implements InputType
	{
		public String getDefaultValue()
		{
			return "";
		}

		public JComponent getBoundEditComponent(ValueModel valueModel)
		{
			JTextField textField = new JTextField();
			Bindings.bind(textField, valueModel);
			return textField;
		}
	}

	/**
	 * Implementation to handle the input of Dates. This implementation
	 * uses a JDateChooser (from JCalendar library) for input and a
	 * default value of today's date.
	 */
	public static class DateType implements InputType
	{
		public Date getDefaultValue()
		{
			return new Date();
		}

		public JComponent getBoundEditComponent(ValueModel valueModel)
		{
			// TODO: fix the way this is bound
			JDateChooser dateChooser = new JDateChooser();
			/*
			SpinnerDateModel mod = SpinnerAdapterFactory
				.createDateAdapter(valueModel, getDefaultValue());
			dateChooser.setModel(mod);
			*/
			return dateChooser;
		}
	}
}

