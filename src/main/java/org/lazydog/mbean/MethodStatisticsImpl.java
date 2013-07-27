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
package org.lazydog.mbean;

import java.util.Date;

/**
 * Method statistics.
 * 
 * @author  Ron Rickard
 */
public class MethodStatisticsImpl implements MethodStatistics {

    private int failures;
    private Date lastFailureTime;
    private long lastSuccessDuration;
    private Date lastSuccessTime;
    private long maximumSuccessDuration;
    private long minimumSuccessDuration;
    private int successes;
    private long totalSuccessDuration;
        
    /**
     * Add a failure.
     */
    @Override
    public void addFailure() {

        // Increment the failures.
        this.failures++;

        // Set the last failure time.
        this.lastFailureTime = new Date();
    }
    
    /**
     * Add a success.
     * 
     * @param  duration  the duration for the success.
     */
    @Override
    public void addSuccess(long duration) {
        
        // Check if the maximum success duration is less than the duration.
        if (this.successes == 0 || this.maximumSuccessDuration < duration) {
            
            // Set the maximum success duration to the duration.
            this.maximumSuccessDuration = duration;
        }
        
        // Check if the minimum success duration is more than the duration.
        if (this.successes == 0 || this.minimumSuccessDuration > duration) {
            
            // Set the minimum success duration to the duration.
            this.minimumSuccessDuration = duration;
        }

        // Increment the successes.
        this.successes++;

        // Set the total success duration.
        this.totalSuccessDuration += duration;

        // Set the last success duration.
        this.lastSuccessDuration = duration;

        // Set the last success time.
        this.lastSuccessTime = new Date();
    }

    /**
     * Get the average success duration.
     * 
     * @return  the average success duration.
     */
    @Override
    public long getAverageSuccessDuration() {
        return (this.successes != 0) ? this.totalSuccessDuration / this.successes : 0;
    }

    /**
     * Get the number of failures.
     *
     * @return  the number of failures.
     */
    @Override
    public int getFailures() {
        return this.failures;
    }

    /**
     * Get the last failure time.
     *
     * @return  the last failure time.
     */
    @Override
    public Date getLastFailureTime() {
        return this.lastFailureTime;
    }

    /**
     * Get the last success duration.
     * 
     * @return  the last success duration.
     */
    @Override
    public long getLastSuccessDuration() {
        return this.lastSuccessDuration;
    }

    /**
     * Get the last success time.
     *
     * @return  the last success time.
     */
    @Override
    public Date getLastSuccessTime() {
        return this.lastSuccessTime;
    }

    /**
     * Get the maximum success duration.
     * 
     * @return  the maximum success duration.
     */
    @Override
    public long getMaximumSuccessDuration() {
        return this.maximumSuccessDuration;
    }

    /**
     * Get the minimum success duration.
     * 
     * @return  the minimum success duration.
     */
    @Override
    public long getMinimumSuccessDuration() {
        return this.minimumSuccessDuration;
    }

    /**
     * Get the number of requests.
     * 
     * @return  the number of requests.
     */
    @Override
    public int getRequests() {
        return this.failures + this.successes;
    }

    /**
     * Get the number of successes.
     *
     * @return  the number of successes.
     */
    @Override
    public int getSuccesses() {
        return this.successes;
    }

    /**
     * Get the total success duration.
     * 
     * @return  the total success duration.
     */
    @Override
    public long getTotalSuccessDuration() {
        return this.totalSuccessDuration;
    }
    
    /**
     * Reset the method statistics.
     */
    @Override
    public void reset() {
        this.failures = 0;
        this.lastFailureTime = null;
        this.lastSuccessDuration = 0L;
        this.lastSuccessTime = null;
        this.maximumSuccessDuration = 0L;
        this.minimumSuccessDuration = 0L;
        this.successes = 0;
        this.totalSuccessDuration = 0L;
    }
}
