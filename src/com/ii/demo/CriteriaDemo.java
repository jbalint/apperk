package com.ii.demo;

import java.util.*;
import javax.swing.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import apperk.gencriteria.EntityDisplayDescriptor;

public class CriteriaDemo
{
	protected static ApplicationContext appctx;

	protected CriteriaPanel1 holder = new CriteriaPanel1();

	JFrame frame = new JFrame("Criteria Demo");

	public CriteriaDemo()
	{
		frame.setContentPane(holder);
		//holder.
		Map entities = appctx.getBeansOfType(EntityDisplayDescriptor.class);
		Set<EntityDisplayDescriptor> entities2 =
			(Set<EntityDisplayDescriptor>)entities.entrySet();
		Vector entityList = new Vector();
		for(EntityDisplayDescriptor edd : entities2)
		{
			//entityList.add(edd.getEntityName());
		}


		holder.comboEntity.setModel(new DefaultComboBoxModel(entityList));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String args[])
	{
        appctx = new FileSystemXmlApplicationContext(
				new String[] { "etc/spring.xml", "etc/gencriteria.xml" });
		new CriteriaDemo();
	}
}

