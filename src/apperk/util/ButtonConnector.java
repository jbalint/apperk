package apperk.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.AbstractButton;

import foxtrot.Job;
import foxtrot.Worker;
import info.javelot.functionalj.Function;
import info.javelot.functionalj.InstanceFunction;
import org.apache.log4j.Logger;

/**
 * A class to allow easier creation of action listeners that simply delegate
 * to another method. To use this class, simply call the static "connect()"
 * method and everything else will be taken care of.
 */
public class ButtonConnector implements ActionListener
{
	private static final Logger log = Logger.getLogger(ButtonConnector.class);

	/** Function reference to invoke. */
	protected Function function;
	/** Whether or not to pass the AWT event to the method. */
	protected boolean passEvent;
	/** Whether or not to invoke the method on the event dispatch thread. */
	protected boolean onSwingThread;
	/** Whether or not to create a new thread to invoke the method. */
	protected boolean onNewThread;

	/**
	 * Create a new ButtonConnector. This will act as an ActionListener
	 * that will execute the given function when an event is received.
	 * <p/>
	 * The function will be called on either a new thread or the EDT. If
	 * neither is specified, it will be called on the FoxTrot worker thread.
	 *
	 * @param function A function reference to call.
	 * @param passEvent Whether or not to pass the ActionEvent.
	 * @param onSwingThread Call function on EDT.
	 * @param onNewThread Spawn a new thread for the function call.
	 */
	public ButtonConnector(Function function, boolean passEvent,
			boolean onSwingThread, boolean onNewThread)
	{
		this.function = function;
		this.passEvent = passEvent;
		this.onSwingThread = onSwingThread;
		this.onNewThread = onNewThread;
	}

	/**
	 * Handle the running of the function.
	 *
	 * @param ae The action event that triggered this invocation.
	 */
	protected void invoke(ActionEvent ae)
	{
		if(passEvent)
			function.addParameter(ae);
		function.call();
	}

	/**
	 * Delegate to the action target method. Saves the ActionEvent as a thread
	 * local on the current thread before calling run().
	 *
	 * @param ae The action event.
	 */
	public void actionPerformed(final ActionEvent ae)
	{
		if(onSwingThread)
		{
			invoke(ae);
		}
		else if(onNewThread)
		{
			Runnable r = new Runnable()
			{
				public void run()
				{
					invoke(ae);
				}
			};
			new Thread(r).start();
		}
		else
		{
			Job j = new Job()
			{
				public Object run()
				{
					invoke(ae);
					return null;
				}
			};
			Worker.post(j);
		}
	}

	/**
	 * Create a new ButtonConnector and add it as an ActionListener
	 * on the given button.
	 * <p/>
	 * @see ButtonConnector#ButtonConnector(Function,
	 * 			boolean, boolean, boolean)
	 */
	public static void connect(AbstractButton button, Function function,
			boolean passEvent, boolean onSwingThread, boolean onNewThread)
	{
		ButtonConnector connector = new ButtonConnector(function,
				passEvent, onSwingThread, onNewThread);
		button.addActionListener(connector);
	}

	/**
	 * Connect the given button to an ActionListener that will call
	 * &quot;method&quot; on &quot;target&quot;.
	 */
	public static void connect(AbstractButton button, Object target,
			String method, boolean passEvent,
			boolean onSwingThread, boolean onNewThread)
	{
		Function f = new InstanceFunction(target.getClass(), method, target);
		connect(button, f, passEvent, onSwingThread, onNewThread);
	}

	/**
	 * Convenience method with defaults. Defaults to calling
	 * the handler without passing the event and running it on the Worker
	 * thread.
	 */
	public static void connect(AbstractButton button,
			Object target, String method)
	{
		// defaults
		boolean passEvent = false;
		boolean onSwingThread = false;
		boolean onNewThread = false;

		ButtonConnector.connect(button, target, method,
				passEvent, onSwingThread, onNewThread);
	}
}

