package apperk;

import java.awt.Dimension;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.Sizes;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * Prototype edit panel for a generic entity. This builds a form on-the-fly
 * for editing. Alot of it right now is hardcoded, but can be parameterzied.
 */
public class EntityEditPanel extends JPanel
{
    protected static final Border DEFAULT_BORDER = Borders.TABBED_DIALOG_BORDER;
    protected FormLayout formLayout;

    /**
     * Default constructor.
     */
    public EntityEditPanel()
    {
        setBorder(DEFAULT_BORDER);
    }

    /**
     * Set the entity. This will cause the panel to be populated.
     *
     * @param o The entity to create the form for.
     */
    public void setEntity(Object o)
    {
        initFromEntity(o);
    }

    /**
     * Populate the form from the given entity. A JGoodies FormLayout is
     * created. This consists of the following
     * steps:<br/>
     * <ul>
     * <li>Get the propertys from the entity.</li>
     * <li>Create the rowspecs from the property list.</li>
     * <li>Create the actual layout and apply it to the panel.</li>
     * <li>For each property, add a label with the display name and an
     *     editor component. The editor components are saved in a map.</li>
     * <li>Create a subform with OK and Cancel buttons and add this to the
     *     main form.</li>
     * </ul>
     * There is a String[] declared at the top that specificies which
     * properties to use and what order to display them in. This should
     * ideally be sent by the user.<p/>
     * The static parameters are:<br/>
     * <ul>
     * <li>A TABBED_DIALOG_BORDER is set by default on the panel.</li>
     * <li>Editor components are all JTextFields with a 150px preferred
     *     width.</li>
     * <li>OK and Cancel buttons are in a seperate panel that spans the whole
     *     bottom row. They both have a 75px preferred width. This panel is
     *     centered horizontally in the bottom row.</li>
     * <li>Line and column gaps are inserted.</li>
     * <li>There are two columns in the layout, one for labels and one for
     *     editor components.</li>
     * </ul>
     */
    protected void initFromEntity(Object o)
    {
        // represents order of properties and which properties to use
        // we should probably also use a second array of display names
        String[] displayProps = new String[] {
            "name", "nickname", "age", "address", "telephone" };

        // a map (that will be saved for later) of display components for
        // each property of the entity
        Map<String, Object> editComponents = new HashMap<String, Object>();

        // the row specs are built up then applied to the form list at the end
        List<RowSpec> rowSpecs = new ArrayList<RowSpec>();

        // properties of the entity
        Map<String, Object> props = null;

        try
        {
            // obtain the properties from beanutils
            props = PropertyUtils.describe(o);
        }
        catch (NoSuchMethodException e)
        {
            // TODO
        }
        catch (InvocationTargetException e)
        {
            // TODO
        }
        catch (IllegalAccessException e)
        {
            // TODO
        }

        // create the row specs for the property editors
        for(int i = 0; i < displayProps.length; ++i)
        {
            if(i > 0)
                rowSpecs.add(FormFactory.LINE_GAP_ROWSPEC);
            rowSpecs.add(FormFactory.DEFAULT_ROWSPEC);
        }

        // add an additional row for the button bar
        rowSpecs.add(FormFactory.LINE_GAP_ROWSPEC);
        rowSpecs.add(FormFactory.DEFAULT_ROWSPEC);

        // create the layout using a hard-coded column spec array
        formLayout = new FormLayout(new ColumnSpec[] {
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT,
                    FormSpec.DEFAULT_GROW) },
            rowSpecs.toArray(new RowSpec[rowSpecs.size()]));

        // apply the layout to the form
        setLayout(formLayout);

        // shared constraints used to add components to the panel
        CellConstraints cc = new CellConstraints();

        // row increments for each property added
        int row = 1;

        // add all the properties to the panel
        for(String propName : displayProps)
        {
            // TODO: get display name
            String displayName = propName;

            // setup the editor component
            JTextField editComponent = new JTextField();
            editComponent.setText(String.valueOf(props.get(propName)));
            editComponents.put(propName, editComponent);

            // add the editor component and it's label
            cc.gridY = row;
            cc.gridX = 1;
            add(new JLabel(displayName), cc);
            Dimension psize = editComponent.getPreferredSize();
            psize.width = 150;
            editComponent.setPreferredSize(psize);
            cc.gridX = 3;
            add(editComponent, cc);

            // increment an extra time for the gap row
            row += 2;
        }

        // setup the panel for the button bar, again hardcoded column specs
        FormLayout buttonLayout = new FormLayout(new ColumnSpec[] {
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC },
            new RowSpec[] { FormFactory.DEFAULT_ROWSPEC });
        JPanel buttons = new JPanel(buttonLayout);

        // add the button bar to the main panel
        cc.gridY = row;
        cc.gridX = 1;
        cc.gridWidth = 3;
        cc.hAlign = CellConstraints.CENTER;
        add(buttons, cc);

        // reset the cell constraints
        cc = new CellConstraints();

        // setup the ok button
        cc.gridY = 1;
        cc.gridX = 1;
        JButton btnOk = new JButton("OK");
        Dimension psize;
        psize = btnOk.getPreferredSize();
        psize.width = 75;
        btnOk.setPreferredSize(psize);
        buttons.add(btnOk, cc);

        // setup the cancel button
        cc.gridX = 3;
        JButton btnCancel = new JButton("Cancel");
        psize = btnCancel.getPreferredSize();
        psize.width = 75;
        btnCancel.setPreferredSize(psize);
        buttons.add(btnCancel, cc);
    }
}
