package apperk.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.AbstractButton;

import foxtrot.Job;
import foxtrot.Worker;
import org.apache.log4j.Logger;

/**
 * A class to allow easier creation of action listeners that simply delegate
 * to another method. To use this class, simply call the static "connect()"
 * method and everything else will be taken care of.
 */
public class ButtonConnector implements ActionListener
{
	private static final Logger log = Logger.getLogger(ButtonConnector.class);

	/** Target object the method will be invoked on. */
	protected Object target;
	/** Method reference to invoke. */
	protected Method method;
	/** Whether or not to pass the AWT event to the method. */
	protected boolean passEvent;
	/** Whether or not to invoke the method on the event dispatch thread. */
	protected boolean onSwingThread;
	/** Whether or not to create a new thread to invoke the method. */
	protected boolean onNewThread;

	/**
	 * Create a new ButtonConnector.
	 *
	 * @param target The target object.
	 * @param method A method reference to call.
	 * @param passEvent Whether or not to pass the ActionEvent.
	 */
	public ButtonConnector(Object target, Method method, boolean passEvent,
			boolean onSwingThread, boolean onNewThread)
	{
		this.target = target;
		this.method = method;
		this.passEvent = passEvent;
		this.onSwingThread = onSwingThread;
		this.onNewThread = onNewThread;
	}

	/**
	 * Handle the running of the final target method.
	 *
	 * @param ae The action event that triggered this invocation.
	 */
	protected void invoke(ActionEvent ae)
	{
		try
		{
			if(passEvent)
				method.invoke(target, ae);
			else
				method.invoke(target);
		}
		catch(IllegalAccessException ex)
		{
			log.error(String.format("Failed to call '%s'", method), ex);
			throw new IllegalArgumentException(ex);
		}
		catch(IllegalArgumentException ex)
		{
			log.error(String.format("Failed to call '%s'", method), ex);
			throw new IllegalArgumentException(ex);
		}
		catch(InvocationTargetException ex)
		{
			log.error(String.format("Failed to call '%s'", method), ex);
			throw new IllegalArgumentException(ex);
		}
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
	 * Connect a button to the given target object. The method on the
	 * target object must be publicly accessible to be called. The
	 * arguments on the method must be either none, or an ActionEvent
	 * based on the value of passEvent.
	 * <p/>
	 *
	 * @param button The button to list for actions on.
	 * @param target The target object to call a method on
	 * 		when the action occurs.
	 * @param method The method name to call.
	 * @param passEvent Whether or not to pass the ActionEvent to
	 * 		the given method.
	 */
	public static void connect(AbstractButton button, Object target,
			String method, boolean passEvent,
			boolean onSwingThread, boolean onNewThread)
	{
		Class params[];

		if(passEvent)
			params = new Class[] { ActionEvent.class };
		else
			params = new Class[] { };

		Method m = null;

		try
		{
			// get the method
			m = target.getClass().getMethod(method, params);
			// add a new button connector to the button
			button.addActionListener(
					new ButtonConnector(target, m, passEvent,
						onSwingThread, onNewThread));

			if(log.isDebugEnabled())
				log.debug(String.format("Connected button '%s' to " +
							"method '%s'", button, m));
		}
		catch(NoSuchMethodException ex)
		{
			String emsg = String.format("Failed to connect button, method " +
						"'%s' doesn't exist or isn't public in class '%s'",
						method, target.getClass().getName());
			log.error(emsg, ex);
			throw new IllegalArgumentException(emsg, ex);
		}
		catch(SecurityException ex)
		{
			log.error("Failed to connect button, security error", ex);
			throw new IllegalArgumentException(ex);
		}
	}

	/**
	 * Convenience method with defaults. Defaults to calling
	 * the handler without passing the event and running it on the Worker
	 * thread.
	 *
	 * @param button Button to connect.
	 * @param target Target object exposing handler.
	 * @param method Name of handler method.
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

