package apperk.gencriteria;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.springframework.beans.factory.InitializingBean;

import apperk.ApplicationWindow;
import apperk.util.ButtonConnector;

class ExpressionEditor
{
	protected ExpressionEditPanel holder = new ExpressionEditPanel();
}

public class CriteriaListEdit implements InitializingBean
{
	protected CriteriaListEditPanel holder = new CriteriaListEditPanel();
	protected ApplicationWindow appWindow;

	public CriteriaListEdit()
	{
		ButtonConnector.connect(holder.btnAddExpression, this,
				"onAdd", false, true, false);
	}

	public void onAdd()
	{
		ExpressionEditor ee = new ExpressionEditor();
		final JInternalFrame jif = appWindow.addInternalFrame(ee.holder);
		//Runnable r = new Runnable()
		//{
		//};
		//new Thread(r).start();
	}

	public JPanel getPanel()
	{
		return holder;
	}

	public void afterPropertiesSet() throws Exception
	{
		// TODO: do we even need this method?
	}

	// TODO: this is currently managed via IOC, but I think would be
	// better as a static method on some Services or ApplicationWindow
	// that gets the "default", eg. bean called "applicationWindow" in
	// bean factory or some other designator
	public void setApplicationWindow(ApplicationWindow appWindow)
	{
		this.appWindow = appWindow;
	}
}

