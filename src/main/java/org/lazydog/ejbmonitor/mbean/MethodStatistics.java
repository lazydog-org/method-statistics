package org.lazydog.ejbmonitor.mbean;

import java.util.Date;
import javax.management.MXBean;


/**
 * Method statistics MXBean.
 * 
 * @author  Ron Rickard
 */
@MXBean
public interface MethodStatistics {

    /**
     * Add a failure.
     */
    void addFailure();

    /**
     * Add a success.
     *
     * @param  duration  the duration for the success.
     */
    void addSuccess(long duration);

    /**
     * Get the average success duration.
     *
     * @return  the average success duration.
     */
    long getAverageSuccessDuration();

    /**
     * Get the number of failures.
     *
     * @return  the number of failures.
     */
    int getFailures();

    /**
     * Get the last failure time.
     *
     * @return  the last failure time.
     */
    Date getLastFailureTime();

    /**
     * Get the last success duration.
     *
     * @return  the last success duration.
     */
    long getLastSuccessDuration();

    /**
     * Get the last success time.
     *
     * @return  the last success time.
     */
    Date getLastSuccessTime();

    /**
     * Get the maximum success duration.
     *
     * @return  the maximum success duration.
     */
    long getMaximumSuccessDuration();

    /**
     * Get the minimum success duration.
     *
     * @return  the minimum success duration.
     */
    long getMinimumSuccessDuration();

    /**
     * Get the number of requests.
     *
     * @return  the number of requests.
     */
    int getRequests();

    /**
     * Get the number of successes.
     *
     * @return  the number of successes.
     */
    int getSuccesses();

    /**
     * Get the total success duration.
     *
     * @return  the total success duration.
     */
    long getTotalSuccessDuration();

    /**
     * Reset the method statistics.
     */
    void reset();
}
