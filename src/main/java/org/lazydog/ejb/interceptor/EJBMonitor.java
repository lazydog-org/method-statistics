/**
 * Copyright 2010-2013 lazydog.org.
 *
 * This file is part of repository.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.lazydog.ejb.interceptor;

import java.util.Hashtable;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.management.MBeanException;
import javax.management.ObjectName;
import org.lazydog.mbean.MethodStatistics;
import org.lazydog.mbean.utility.MBeanUtility;

/**
 * EJB monitor interceptor.
 * 
 * @author  Ron Rickard
 */
public class EJBMonitor {
    
    /**
     * Get the method statistics MBean.
     * 
     * @param  className   the EJB class name.
     * @param  methodName  the EJB method name.
     * 
     * @return  the method statistics MBean.
     * 
     * @throws  MBeanException  if unable to get the method statistics MBean.
     */
    private static MethodStatistics getMethodStatistics(String className, String methodName) throws MBeanException {

        // Set the properties table.
        Hashtable<String,String> table = new Hashtable<String,String>();
        table.put("ejb", className);
        table.put("method", methodName);

        // Register the method statistics MBean.
        ObjectName objectName = MBeanUtility.register(MethodStatistics.class, table);

        // Get the method statistics MBean.
        return MBeanUtility.getMBean(MethodStatistics.class, objectName);
    }

    /**
     * Add the failure to the method statistics MBean.
     * 
     * @param  className   the EJB class name.
     * @param  methodName  the EJB method name.
     */
    private static void addFailure(String className, String methodName) {

        try {

            // Add the failure to the method statistics MBean.
            getMethodStatistics(className, methodName).addFailure();
        } catch (Exception e) {
            // Ignore.
        }
    }
    
    /**
     * Add the success to the method statistics MBean.
     *
     * @param  className   the EJB class name.
     * @param  methodName  the EJB method name.
     * @param  duration    the method duration.
     */
    private static void addSuccess(String className, String methodName, long duration) {

        try {

            // Add the success to the method statistics MBean.
            getMethodStatistics(className, methodName).addSuccess(duration);
        } catch (Exception e) {
            // Ignore.
        }
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
    public Object monitor(InvocationContext invocationContext) throws Exception {

        // Initialize.
        Object result = null;

        // Get the EJB class and method names.
        String className = invocationContext.getMethod().getDeclaringClass().getName();
        String methodName = invocationContext.getMethod().getName();

        try {

            // Get the time the EJB method started.
            long startTime = System.currentTimeMillis();

            // Invoke the EJB method.
            result = invocationContext.proceed();

            // Calculate the duration of the EJB method.
            long duration = System.currentTimeMillis() - startTime;

            // Add the success to the statistics.
            addSuccess(className, methodName, duration);
        } catch (Exception e) {

            // Add the failure to the statistics.
            addFailure(className, methodName);

            // Throw the original EJB method exception.
            throw e;
        }
        
        return result;
    }
}