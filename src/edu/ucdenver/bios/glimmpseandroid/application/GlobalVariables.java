/*
 * Mobile - Android, User Interface for the GLIMMPSE Software System.  Allows
 * users to perform power, sample size calculations. 
 * 
 * Copyright (C) 2010 Regents of the University of Colorado.  
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package edu.ucdenver.bios.glimmpseandroid.application;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class GlobalVariables.
 */
public class GlobalVariables {
	/*--------------------
	 * Singleton Instance Variable
	 *--------------------*/
	/** The instance. */
	private static GlobalVariables instance = null;

	/*--------------------
	 * Member Variables
	 *--------------------*/
	/** The groups. */
	private int groups = 2;

	/** The type i error. */
	private double typeIError = 0.01;

	/** The smallest group size. */
	private Integer smallestGroupSize;

	/** The variance. */
	private Double variance;
	
	// Enum/String - Solving for
	/** The solving for enum. */
	private String solvingForEnum = null;
	
	private List<Double> means = null;

	/** The progress. */
	private Integer[] progress = {0,0,0,0,0,0};

	// Enum/String - power method

	/*--------------------
	 * Constructors
	 *--------------------*/
	/**
	 * Instantiates a new global variables.
	 */
	GlobalVariables() {		
	}

	/*--------------------
	 * Getter/Setter Methods
	 *--------------------*/
	/**
	 * Gets the solving for enum.
	 * 
	 * @return the solving for enum
	 */
	public String getSolvingFor() {
		return solvingForEnum;
	}

	/**
	 * Sets the solving for enum.
	 * 
	 * @param solvingForEnum
	 *            the new solving for enum
	 */
	public void setSolvingFor(String solvingFor) {
		this.solvingForEnum = solvingFor;
	}

	/**
	 * Gets the groups.
	 * 
	 * @return the groups
	 */
	public int getGroups() {
		return groups;
	}

	/**
	 * Sets the groups.
	 * 
	 * @param groups
	 *            the new groups
	 */
	public void setGroups(int groups) {
		this.groups = groups;
	}

	/**
	 * Gets the type i error.
	 * 
	 * @return the type i error
	 */
	public double getTypeIError() {
		return typeIError;
	}

	/**
	 * Sets the type i error.
	 * 
	 * @param typeIError
	 *            the new type i error
	 */
	public void setTypeIError(double typeIError) {
		this.typeIError = typeIError;
	}

	/**
	 * Gets the smallest group size.
	 * 
	 * @return the smallest group size
	 */
	public Integer getSmallestGroupSize() {
		return smallestGroupSize;
	}

	/**
	 * Sets the smallest group size.
	 * 
	 * @param smallestGroupSize
	 *            the new smallest group size
	 */
	public void setSmallestGroupSize(Integer smallestGroupSize) {
		this.smallestGroupSize = smallestGroupSize;
	}

	/**
	 * Gets the variance.
	 * 
	 * @return the variance
	 */
	public Double getVariance() {
		return variance;
	}

	/**
	 * Sets the variance.
	 * 
	 * @param variance
	 *            the new variance
	 */
	public void setVariance(Double variance) {
		this.variance = variance;
	}
	
	public List<Double> getMeans() {
		return means;
	}

	public void setMeans(List<Double> means) {
		this.means = means;
	}

	/**
	 * Gets the single instance of GlobalVariables.
	 * 
	 * @return single instance of GlobalVariables
	 */
	public static synchronized GlobalVariables getInstance() {
		if (null == instance) {
			instance = new GlobalVariables();
		}
		return instance;
	}

	/**
	 * Reset instance.
	 * 
	 * @return the global variables
	 */
	public static synchronized GlobalVariables resetInstance() {		
		instance = null;
		System.gc();
		return instance;
	}
	
	public Integer[] getProgress(){
		return this.progress;
	}

	public int getTotalProgress() {
		int sum = 0;		
		int index = 0;
		while(index < this.progress.length){
			sum = sum + this.progress[index++];		
		}		    
		return sum;
	}
	
	public int getIndividualProgress(int position){
		return this.progress[position];
	}

	public void setProgress(Integer[] progress) {
		this.progress = progress;
	}
	
	public void setProgress(int position) {
		this.progress[position] = 1;
	}
	
	public void resetProgress(int position) {
		this.progress[position] = 0;
	}
	
	public void resetProgress() {
		int index = 0;
		while(index<6) {
			this.progress[index++] = 0;
		}
	}

	
}
