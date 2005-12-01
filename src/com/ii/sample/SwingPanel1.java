package com.ii.sample;

import java.awt.*;
import javax.swing.*;

public class SwingPanel1 extends JPanel
{
    public SwingPanel1()
    {
        // Initialize components
        initialize();

        // Add components
        setLayout(new BorderLayout());
        add(panel1, BorderLayout.CENTER);

        // TODO: add your own initialization code & event handlers here
    }

    private String getLocalizedText(String text)
    {
        return text;
    }

    // Components (X-develop Swing designer code)

    private JTextField textfield1; // Textbox,,1,,1
    private JTextField textfield2; // Textbox,,1,,1,2
    private JButton okButton; // Button,OK,10,,1
    private JButton cancelButton; // Button,Cancel,10,,2

    // Panels (X-develop Swing designer code)

    private JPanel panel1; // Panel,Title
    private JLabel label1; // Label,,1
    private JPanel panel3; // Panel,,1,,,1
    private JPanel panel2; // Vertical Space,,1,,1,1
    private JLabel label2; // Label,,1,,,2
    private JPanel panel7; // Panel,,1,,,3
    private JPanel panel6; // Vertical Space,,1,,1,3
    private JPanel panel5; // Panel,,1,,,4
    private JPanel panel4; // Grouppanel,,1,,1,4
    private JPanel panel8; // Panel,,9
    private JPanel panel9; // Horizontal Space,,10
    private JPanel panel10; // Horizontal Space,,10,,3

    // Implementation (X-develop Swing designer code)

    private static final double ASPECT_RATIO = 1.3333333333333333; // Aspect Ratio
    private static final double SCREEN_AREA_RATIO = 0.1; // Screen Area Ratio

    private String titleText;

    private int screenSize;
    private double pixelAspectRatio;
    private Dimension windowSize;

    private void initialize()
    {
        titleText = getLocalizedText("Title");
        calculateScreenSize();
        calculateWindowSize();
        createComponents();
        initComponents();
        layoutComponents();
    }

    private void createComponents()
    {
        panel1 = new JPanel();
        label1 = new JLabel();
        textfield1 = new JTextField("");
        panel3 = new JPanel();
        panel2 = new JPanel();
        label2 = new JLabel();
        textfield2 = new JTextField("");
        panel7 = new JPanel();
        panel6 = new JPanel();
        panel5 = new JPanel();
        panel4 = new JPanel();
        panel8 = new JPanel();
        panel9 = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();
        panel10 = new JPanel();
    }

    private void initComponents()
    {
        panel1.setLayout(new GridBagLayout());
        label1.setText(getLocalizedText("Label"));
        textfield1.setColumns(7);
        panel3.setPreferredSize(new Dimension(0, 0));
        panel2.setPreferredSize(new Dimension(0, 0));
        label2.setText(getLocalizedText("Label"));
        textfield2.setColumns(7);
        panel7.setPreferredSize(new Dimension(0, 0));
        panel6.setPreferredSize(new Dimension(0, 0));
        panel5.setPreferredSize(new Dimension(0, 0));
        panel4.setBorder(BorderFactory.createTitledBorder(getLocalizedText("Grouppanel")));
        panel4.setLayout(new GridBagLayout());
        panel8.setLayout(new GridBagLayout());
        panel9.setPreferredSize(new Dimension(0, 0));
        okButton.setText(getLocalizedText("OK"));
        cancelButton.setText(getLocalizedText("Cancel"));
        panel10.setPreferredSize(new Dimension(0, 0));
    }

    private void layoutComponents()
    {
        panel1.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, translateMargins(2, 2, 2, 2), 0, 0));
        panel1.add(textfield1, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, translateMargins(2, 2, 2, 2), 0, 0));
        panel1.add(panel3, new GridBagConstraints(0, 1, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, translateMargins(0, 0, 0, 0), 0, 0));
        panel1.add(panel2, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, translateMargins(0, 0, 0, 0), 0, 0));
        panel1.add(label2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, translateMargins(2, 2, 2, 2), 0, 0));
        panel1.add(textfield2, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, translateMargins(2, 2, 2, 2), 0, 0));
        panel1.add(panel7, new GridBagConstraints(0, 3, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, translateMargins(0, 0, 0, 0), 0, 0));
        panel1.add(panel6, new GridBagConstraints(1, 3, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, translateMargins(0, 0, 0, 0), 0, 0));
        panel1.add(panel5, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, translateMargins(0, 0, 0, 0), 0, 0));
        panel1.add(panel4, new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, translateMargins(2, 2, 2, 2), 0, 0));
        panel4.add(panel8, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, translateMargins(0, 0, 0, 0), 0, 0));
        panel8.add(panel9, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, translateMargins(0, 0, 0, 0), 0, 0));
        panel8.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, translateMargins(2, 2, 2, 2), 0, 0));
        panel8.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, translateMargins(2, 2, 2, 2), 0, 0));
        panel8.add(panel10, new GridBagConstraints(3, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, translateMargins(0, 0, 0, 0), 0, 0));
    }

    private void calculateScreenSize()
    {
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize = Math.min(screenDim.height * 4 / 3, screenDim.width);
        pixelAspectRatio = 1.0;
        if (screenDim.height == 1024 && screenDim.width == 1280) pixelAspectRatio = (double) 1024 * 4 / 3 / 1280;
    }

    private Insets translateMargins(int top, int left, int bottom, int right)
    {
        int topPixels = (int) ((screenSize * top + 500) / 1000 * pixelAspectRatio);
        int bottomPixels = (int) ((screenSize * bottom + 500) / 1000 * pixelAspectRatio);
        int leftPixels = (int) ((screenSize * left + 500) / 1000 / pixelAspectRatio);
        int rightPixels = (int) ((screenSize * right + 500) / 1000 / pixelAspectRatio);
        return new Insets(topPixels, leftPixels, bottomPixels, rightPixels);
    }

    private void calculateWindowSize()
    {
        int width = (int) (screenSize * Math.sqrt(SCREEN_AREA_RATIO * ASPECT_RATIO) / pixelAspectRatio);
        int height = (int) (screenSize * Math.sqrt(SCREEN_AREA_RATIO / ASPECT_RATIO) * pixelAspectRatio);
        windowSize = new Dimension(width, height);
    }

    private static class CustomSeparator extends JComponent
    {
        public CustomSeparator(int orientation)
        {
            setLayout(new GridBagLayout());
            int o = orientation == JSeparator.HORIZONTAL ? GridBagConstraints.HORIZONTAL : GridBagConstraints.VERTICAL;
            add(new JSeparator(orientation), new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, o, new Insets(0, 0, 0, 0), 0, 0));
        }
    }

    // End of Implementation (X-develop Swing designer code)
}

