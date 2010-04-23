package org.lazydog.utilities.ejbmonitor.mbean;

import java.util.Date;


/**
 * Method statistics MXBean interface.
 * 
 * @author  Ron Rickard
 */
public interface MethodStatisticsMXBean {

    /**
     * Add a failure.
     */
    public void addFailure();

    /**
     * Add a success.
     *
     * @param  duration  the duration for the success.
     */
    public void addSuccess(long duration);

    /**
     * Get the average success duration.
     *
     * @return  the average success duration.
     */
    public long getAverageSuccessDuration();

    /**
     * Get the number of failures.
     *
     * @return  the number of failures.
     */
    public int getFailures();

    /**
     * Get the last failure time.
     *
     * @return  the last failure time.
     */
    public Date getLastFailureTime();

    /**
     * Get the last success duration.
     *
     * @return  the last success duration.
     */
    public long getLastSuccessDuration();

    /**
     * Get the last success time.
     *
     * @return  the last success time.
     */
    public Date getLastSuccessTime();

    /**
     * Get the maximum success duration.
     *
     * @return  the maximum success duration.
     */
    public long getMaximumSuccessDuration();

    /**
     * Get the minimum success duration.
     *
     * @return  the minimum success duration.
     */
    public long getMinimumSuccessDuration();

    /**
     * Get the number of requests.
     *
     * @return  the number of requests.
     */
    public int getRequests();

    /**
     * Get the number of successes.
     *
     * @return  the number of successes.
     */
    public int getSuccesses();

    /**
     * Get the total success duration.
     *
     * @return  the total success duration.
     */
    public long getTotalSuccessDuration();

    /**
     * Reset the method statistics.
     */
    public void reset();
}
