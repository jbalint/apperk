package com.ii.sample;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Simple application launcher that gives a Spring configuration setup
 * for an apperk application.
 */
public class MyApplication
{
    /**
     * Main entry point for application. Set the look and feel then
     * launch the app.
     */
    public static void main(String args[])
        throws InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException, ClassNotFoundException
    {
        javax.swing.UIManager.setLookAndFeel(
            "com.jgoodies.looks.windows.WindowsLookAndFeel");
        new FileSystemXmlApplicationContext(
				new String[] { "etc/spring.xml", "etc/gencriteria.xml" });
    }
}

