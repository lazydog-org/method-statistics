package org.lazydog.utilities.ejbmonitor.interceptor;

import java.lang.management.ManagementFactory;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import org.lazydog.utilities.ejbmonitor.mbean.MethodStatistics;


/**
 * EJB monitor interceptor.
 * 
 * @author  Ron Rickard
 */
public class EJBMonitor {
    
    /**
     * Invoke the MBean addFailure() method.
     * 
     * @param  className   the EJB class name.
     * @param  methodName  the EJB method name.
     */
    private static void addFailure(String className, String methodName) {

        try {

            // Declare.
            MBeanServer mBeanServer;
            ObjectName name;

            // Get the MBean name.
            name = getMBeanName(className, methodName);

            // Get the MBean server with the registered MBean.
            mBeanServer = getMBeanServer(name);

            // Invoke the MBean addFailure() method.
            mBeanServer.invoke(name, "addFailure", null, null);
        }
        catch(Exception e) {
            // Ignore.
        }
    }
    
    /**
     * Invoke the MBean addSuccess() method.
     *
     * @param  className   the EJB class name.
     * @param  methodName  the EJB method name.
     * @param  duration    the method duration.
     */
    private static void addSuccess(
            String className, String methodName, long duration) {

        try {

            // Declare.
            MBeanServer mBeanServer;
            ObjectName name;

            // Get the MBean name.
            name = getMBeanName(className, methodName);

            // Get the MBean server with the registered MBean.
            mBeanServer = getMBeanServer(name);

            // Invoke the MBean addSuccess().
            mBeanServer.invoke(name, "addSuccess",
                    new Object[]{duration},
                    new String[]{"long"});
        }
        catch(Exception e) {
            // Ignore.
        }
    }

    /**
     * Get the MBean name.
     *
     * @param  className    the class name.
     * @param  methodName   the method name.
     *
     * @return  the MBean name.
     *
     * @throws  Exception  if unable to get the MBean name.
     */
    private static ObjectName getMBeanName(String className, String methodName)
            throws Exception {
        return new ObjectName(className + ":method=" + methodName);
    }

    /**
     * Get the MBean server with the registered MBean.
     *
     * @param  name  the MBean name.
     *
     * @throws  Exception  if unable to get the MBean server with the
     *                     registered MBean.
     */
    private static MBeanServer getMBeanServer(ObjectName name)
            throws Exception {

        // Declare.
        MBeanServer mBeanServer;

        // Get the MBean server.
        mBeanServer = ManagementFactory.getPlatformMBeanServer();

        // Check if the MBean is not registered with the MBean server.
        if (!mBeanServer.isRegistered(name)) {

            // Register the MBean with the MBean server.
            mBeanServer.registerMBean(new MethodStatistics(), name);
        }

        return mBeanServer;
    }

    /**
     * Monitor the EJB method call.
     * 
     * @param  invocationContext  the generic representation of the EJB method.
     * 
     * @return  the result of the EJB method call.
     * 
     * @throws  Exception  if the EJB method call throws an exception.
     */
    @AroundInvoke
    public Object monitor(InvocationContext invocationContext)
           throws Exception {
        
        // Declare.
        String className;
        String methodName;
        Object result;

        // Initialize.
        result = null;

        // Get the EJB class and method names.
        className = invocationContext.getMethod().getDeclaringClass().getName();
        methodName = invocationContext.getMethod().getName();

        try {

            // Declare.
            long duration;
            long startTime;

            // Get the time the EJB method started.
            startTime = System.currentTimeMillis();

            // Invoke the EJB method.
            result = invocationContext.proceed();

            // Calculate the duration of the EJB method.
            duration = System.currentTimeMillis() - startTime;

            // Add the success to the statistics.
            addSuccess(className, methodName, duration);
        }
        catch(Exception e) {

            // Add the failure to the statistics.
            addFailure(className, methodName);

            // Throw the original EJB method exception.
            throw e;
        }
        
        return result;
    }
}