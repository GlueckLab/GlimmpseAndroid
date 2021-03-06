/*
 * Mobile - Android, User Interface for the GLIMMPSE Software System.  Allows
 * users to perform power and sample size calculations. 
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.ucdenver.bios.webservice.common.domain.BetaScale;
import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.domain.Blob2DArray;
import edu.ucdenver.bios.webservice.common.domain.Category;
import edu.ucdenver.bios.webservice.common.domain.Covariance;
import edu.ucdenver.bios.webservice.common.domain.Hypothesis;
import edu.ucdenver.bios.webservice.common.domain.HypothesisBetweenParticipantMapping;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.NominalPower;
import edu.ucdenver.bios.webservice.common.domain.RelativeGroupSize;
import edu.ucdenver.bios.webservice.common.domain.ResponseNode;
import edu.ucdenver.bios.webservice.common.domain.SampleSize;
import edu.ucdenver.bios.webservice.common.domain.SigmaScale;
import edu.ucdenver.bios.webservice.common.domain.StandardDeviation;
import edu.ucdenver.bios.webservice.common.domain.StatisticalTest;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.domain.TypeIError;
import edu.ucdenver.bios.webservice.common.enums.CovarianceTypeEnum;
import edu.ucdenver.bios.webservice.common.enums.HypothesisTrendTypeEnum;
import edu.ucdenver.bios.webservice.common.enums.HypothesisTypeEnum;
import edu.ucdenver.bios.webservice.common.enums.SolutionTypeEnum;
import edu.ucdenver.bios.webservice.common.enums.StatisticalTestTypeEnum;
import edu.ucdenver.bios.webservice.common.enums.StudyDesignViewTypeEnum;

// TODO: Auto-generated Javadoc
/**
 * Wizard context for study design.
 * 
 * @author Uttara Sakhadeo
 * @version 1.0.0
 */
public class StuyDesignContext {
    /*--------------------
     * Singleton Instance Variable
     *--------------------*/
    /** The instance. */
    private static StuyDesignContext instance = null;

    /** The study design. */
    private static StudyDesign studyDesign;

    /** The progress. */
    private static Integer[] progress = { 0, 0, 0, 0, 0, 0 };

    /** The Constant SOLVING_FOR_ROW. */
    private static final int SOLVING_FOR_ROW = 0;

    /** The Constant POWER_OR_SAMPLE_SIZE_ROW. */
    private static final int POWER_OR_SAMPLE_SIZE_ROW = 1;

    /** The Constant TYPE_I_ERROR_ROW. */
    private static final int TYPE_I_ERROR_ROW = 2;

    /** The Constant NUMBER_OF_GROUPS_ROW. */
    private static final int NUMBER_OF_GROUPS_ROW = 3;

    /** The Constant RELATIVE_GROUP_SIZE_ROW. */
    private static final int RELATIVE_GROUP_SIZE_ROW = 4;

    /** The Constant MEANS_VARIANCE_ROW. */
    private static final int MEANS_VARIANCE_ROW = 5;

    /** The Constant DEFAULT_GROUP_NAME. */
    private static final String DEFAULT_GROUP_NAME = "Group";

    /** The Constant DEFAULT_GROUPS. */
    private static final int DEFAULT_GROUPS = 2;

    /** The Constant DEFAULT_ALPHA. */
    private static final double DEFAULT_ALPHA = 0.05;

    /** The Constant DEFAULT_RESPONSE. */
    private static final String DEFAULT_RESPONSE = "Response";

    /** The Constant DEFAULT_RELATIVE_GROUP_SIZE. */
    private static final int DEFAULT_RELATIVE_GROUP_SIZE = 1;

    /** The Constant DEFAULT_COVARIANCE_TYPE. */
    private static final CovarianceTypeEnum DEFAULT_COVARIANCE_TYPE = CovarianceTypeEnum.UNSTRUCTURED_CORRELATION;

    /** The Constant DEFAULT_COVARIANCE_NAME. */
    private static final String DEFAULT_COVARIANCE_NAME = "__RESPONSE_COVARIANCE__";

    /** The Constant DEFAULT_VARIANCE. */
    private static final double DEFAULT_STANDARD_DEVIATION = 1;

    /** The Constant DEFAULT_MEAN. */
    private static final double DEFAULT_MEAN = 0;

    /** The Constant DEFAULT_MEAN_COLUMN. */
    private static final int DEFAULT_MEAN_COLUMN = 0;

    /** The Constant ENUM_POWER_VALUE. */
    private static final String ENUM_POWER_VALUE = "Power";

    /** The Constant ENUM_SAMPLE_SIZE_VALUE. */
    private static final String ENUM_SAMPLE_SIZE_VALUE = "Sample Size";

    /** The Constant DEFAULT_CORELATION_MATRIX. */

    /** The Constant MATRIX_BETA. */
    private static final String MATRIX_BETA = "beta";

    /** The standard deviation data. */
    private static StandardDeviation standardDeviationData = new StandardDeviation();

    /*--------------------
     * Constructors
     *--------------------*/
    /**
     * Create a new context with an empty study design object.
     */
    StuyDesignContext() {
        studyDesign = new StudyDesign();
    }

    /*--------------------
     * Getter/Setter Methods
     *--------------------*/

    /**
     * Get the study design object associated with this context.
     * 
     * @return the study design
     */
    public StudyDesign getStudyDesign() {
        return studyDesign;
    }

    /**
     * Sets the study design.
     * 
     * @param study
     *            the new study design
     */
    public void setStudyDesign(StudyDesign study) {
        studyDesign = study;
    }

    /**
     * Gets the single instance of StudyDesignContext.
     * 
     * @return single instance of GlobalVariables
     */
    public static synchronized StuyDesignContext getInstance() {
        if (null == instance) {
            instance = new StuyDesignContext();
        }
        return instance;
    }

    /**
     * Reset instance.
     * 
     * @return the global variables
     */
    public static synchronized StuyDesignContext resetInstance() {
        studyDesign = null;
        instance = null;
        System.gc();
        return instance;
    }

    /**
     * Measures the completeness of each input in the GUI.
     * 
     * @return the progress
     */
    public Integer[] getProgress() {
        return progress;
    }

    /**
     * Measures the completeness of study design.
     * 
     * @return the total progress
     */
    public int getTotalProgress() {
        int sum = 0;
        int index = 0;
        while (index < progress.length) {
            sum = sum + progress[index++];
        }
        return sum;
    }

    /**
     * Gets the individual progress.
     * 
     * @param position
     *            the position
     * @return the individual progress
     */
    public int getIndividualProgress(int position) {
        return progress[position];
    }

    /**
     * Sets the progress.
     * 
     * @param data
     *            the new progress
     */
    public void setProgress(Integer[] data) {
        progress = data;
    }

    /**
     * Sets the progress.
     * 
     * @param position
     *            the new progress
     */
    public void setProgress(int position) {
        progress[position] = 1;
    }

    /**
     * Reset progress.
     * 
     * @param position
     *            the position
     */
    public void resetProgress(int position) {
        progress[position] = 0;
    }

    /**
     * Reset progress.
     */
    public void resetProgress() {
        int index = 0;
        while (index < progress.length) {
            progress[index++] = 0;
        }
    }

    /*--------------------
     * # Groups
     *--------------------*/
    /**
     * convenience Method which sets default Number of Groups. Stores the
     * between participant factor information to the StudyDesign.
     */
    public void setDefaultGroups() {
        setGroups(DEFAULT_GROUPS);
    }

    /**
     * convenience Method which sets given Number of Groups. Stores the between
     * participant factor information to the StudyDesign.
     * 
     * @param groups
     *            the new groups
     */
    public void setGroups(int groups) {
        List<BetweenParticipantFactor> list = studyDesign
                .getBetweenParticipantFactorList();
        List<Category> categoryList;
        if (list == null || list.isEmpty()) {
            list = new ArrayList<BetweenParticipantFactor>();
        } else {
            if (list.size() > 0) {
                categoryList = (List<Category>) list.get(0).getCategoryList();
                if (categoryList != null && !categoryList.isEmpty()) {
                    list.clear();
                }
            }
        }
        list.add(createCategory(groups));
        studyDesign.setBetweenParticipantFactorList(list);
        setProgress(NUMBER_OF_GROUPS_ROW);
        setProgress(RELATIVE_GROUP_SIZE_ROW);
        setProgress(MEANS_VARIANCE_ROW);
    }

    /**
     * Convenience Method which returns Number of Groups.
     * 
     * @return the groups
     */

    public int getGroups() {
        List<BetweenParticipantFactor> list = studyDesign
                .getBetweenParticipantFactorList();
        List<Category> categoryList;
        if (list == null || list.isEmpty()) {
            return 0;
        } else {
            if (list.size() > 0) {
                categoryList = list.get(0).getCategoryList();
                if (categoryList == null || categoryList.isEmpty()) {
                    return 0;
                }
            } else {
                return 0;
            }
        }
        return categoryList.size();
    }

    /**
     * Convenience Method which returns Number of Groups.
     * 
     * @param groups
     *            the groups
     * @return the between participant factor
     */
    private BetweenParticipantFactor createCategory(int groups) {
        List<Category> categoryList = new ArrayList<Category>();
        int index = 0;
        while (index < groups) {
            categoryList.add(new Category(Integer.toString(index + 1)));
            index++;
        }
        return new BetweenParticipantFactor(DEFAULT_GROUP_NAME, categoryList);
    }

    /*--------------------
     * Type I Error
     *--------------------*/
    /**
     * Convenience Method which sets default type i error.
     */
    public void setDefaultTypeIError() {
        setTypeIError(DEFAULT_ALPHA);
    }

    /**
     * Sets the type i error.
     * 
     * @param alpha
     *            the new type i error
     */
    public void setTypeIError(Double alpha) {
        List<TypeIError> list = new ArrayList<TypeIError>(1);
        list.add(0, new TypeIError(alpha));
        studyDesign.setAlphaList(list);
        setProgress(TYPE_I_ERROR_ROW);
    }

    /**
     * Gets the type i error.
     * 
     * @return the type i error
     */
    public Double getTypeIError() {
        List<TypeIError> list = studyDesign.getAlphaList();
        if (list == null || list.isEmpty()) {
            return 0.0;
        } else {
            if (list.size() < 1) {
                return 0.0;
            }
        }
        return list.get(0).getAlphaValue();
    }

    /*--------------------
     * Relative Group Size
     *--------------------*/
    /**
     * Sets the default relative group size.
     */
    public void setDefaultRelativeGroupSize() {
        int groups = getGroups();
        for (int index = 0; index < groups; index++)
            setRelativeGroupSize(DEFAULT_RELATIVE_GROUP_SIZE, index);
    }

    /**
     * Sets the default relative group size.
     * 
     * @param position
     *            the new default relative group size
     */
    public void setDefaultRelativeGroupSize(int position) {
        setRelativeGroupSize(DEFAULT_RELATIVE_GROUP_SIZE, position);
    }

    /**
     * Sets the relative group size.
     * 
     * @param relativeGroupSize
     *            the relative group size
     * @param position
     *            the position
     */
    public void setRelativeGroupSize(int relativeGroupSize, int position) {
        List<RelativeGroupSize> list = studyDesign.getRelativeGroupSizeList();
        if (list == null || list.isEmpty()) {
            list = new ArrayList<RelativeGroupSize>(getGroups());
            list.add(position, new RelativeGroupSize(relativeGroupSize));
        } else {
            if (list.size() > position) {
                if (list.get(position) != null) {
                    list.set(position, new RelativeGroupSize(relativeGroupSize));
                } else {
                    list.add(position, new RelativeGroupSize(relativeGroupSize));
                }
            } else {
                list.add(position, new RelativeGroupSize(relativeGroupSize));
            }
        }
        studyDesign.setRelativeGroupSizeList(list);
        setProgress(RELATIVE_GROUP_SIZE_ROW);
    }

    /**
     * Gets the relative group size.
     * 
     * @param position
     *            the position
     * @return the relative group size
     */
    public int getRelativeGroupSize(int position) {
        List<RelativeGroupSize> list = studyDesign.getRelativeGroupSizeList();
        if (list == null || list.isEmpty()) {
            return 0;
        } else {
            if (list.size() <= position) {
                return 0;
            }
        }
        return list.get(position).getValue();
    }

    /**
     * Synch for removed groups.
     * 
     * @param numberOfGroups
     *            the number of groups
     */
    private void synchForRemovedGroups(int numberOfGroups) {
        /* Relative Group Size */
        List<RelativeGroupSize> list = studyDesign.getRelativeGroupSizeList();
        int index;
        if (list != null && !list.isEmpty()) {
            index = list.size() - numberOfGroups;
            for (int inc = (numberOfGroups - 1); inc >= 0; inc--) {
                studyDesign.getRelativeGroupSizeList().remove(index + inc);
            }
            System.gc();
        }
        /* Means */
        NamedMatrix beta = studyDesign.getNamedMatrix(MATRIX_BETA);
        if (beta != null) {
            int originalRows = beta.getRows();
            double[][] originalData = beta.getData().getData();
            index = originalRows - numberOfGroups;
            double[][] changedData = new double[index][1];
            for (int inc = 0; inc < index; inc++) {
                changedData[inc][0] = originalData[inc][0];
            }
            beta.setRows(index);
            beta.setDataFromArray(changedData);
        }
        studyDesign.setNamedMatrix(beta);
        System.gc();
    }

    /**
     * Synch for added groups.
     * 
     * @param numberOfGroups
     *            the number of groups
     */
    private void synchForAddedGroups(int numberOfGroups) {
        /* Relative Group Size */
        List<RelativeGroupSize> list = studyDesign.getRelativeGroupSizeList();
        int index = 0;
        if (list != null && !list.isEmpty())
            index = list.size();
        for (int inc = index; inc < index + numberOfGroups; inc++) {
            setDefaultRelativeGroupSize(inc);
        }
        /* Standard Deviation */
        setStandardDeviation(getStandardDeviation());
        /* Means */
        setOriginalMeans(numberOfGroups);
    }

    /**
     * Synch for group changes.
     * 
     * @param numberOfGroupsChanged
     *            the number of groups changed
     */
    public void synchForGroupChanges(int numberOfGroupsChanged) {
        if (numberOfGroupsChanged > 0) {
            synchForRemovedGroups(numberOfGroupsChanged);
        } else if (numberOfGroupsChanged < 0) {
            int groups = Math.abs(numberOfGroupsChanged);
            synchForAddedGroups(groups);
        }
    }

    /**
     * Relative group equality.
     * 
     * @return true, if successful
     */
    public boolean relativeGroupEquality() {
        int index = 0;
        boolean equalityFlagRelativeGp = true;
        int groups = getGroups() - 1;
        while (index < groups) {
            equalityFlagRelativeGp = ((getRelativeGroupSize(index) == getRelativeGroupSize(index + 1)) ? true
                    : false);
            if (!equalityFlagRelativeGp)
                return false;
            index++;
        }
        return equalityFlagRelativeGp;
    }

    /*--------------------
     * Solving For
     *--------------------*/
    /**
     * Sets the solving for.
     * 
     * @param solutionType
     *            the new solving for
     */
    public void setSolvingFor(String solutionType) {
        if (solutionType != null) {
            if (ENUM_SAMPLE_SIZE_VALUE.equals(solutionType))
                studyDesign.setSolutionTypeEnum(SolutionTypeEnum.SAMPLE_SIZE);
            else
                studyDesign.setSolutionTypeEnum(SolutionTypeEnum.POWER);
        }
        setProgress(SOLVING_FOR_ROW);
        resetProgress(POWER_OR_SAMPLE_SIZE_ROW);
    }

    /**
     * Gets the solving for.
     * 
     * @return the solving for
     */
    public String getSolvingFor() {
        SolutionTypeEnum solvingFor = studyDesign.getSolutionTypeEnum();
        if (solvingFor == null || solvingFor.equals("")) {
            return null;
        } else if (solvingFor.equals(SolutionTypeEnum.POWER))
            return ENUM_POWER_VALUE;
        else
            return ENUM_SAMPLE_SIZE_VALUE;
    }

    /*--------------------
     * Variance
     *--------------------*/
    /**
     * Checks for covariance.
     * 
     * @return true, if successful
     */
    public boolean hasCovariance() {
        Set<Covariance> covarianceSet = studyDesign.getCovariance();
        if (covarianceSet == null || covarianceSet.isEmpty()){            
            return false;
        }
        Iterator<Covariance> itr = covarianceSet.iterator();
        if (!itr.hasNext()){           
            return false;
        }
        Covariance covariance = (Covariance) itr.next();
        /*
         * Blob2DArray blob = covariance.getBlob(); if (blob == null) return
         * false; return true;
         */
        List<StandardDeviation> sdList = covariance.getStandardDeviationList();
        if (sdList != null && sdList.isEmpty()) {            
            return false;
        } else
            return true;
    }

    /**
     * Gets the standard deviation.
     * 
     * @return the standard deviation
     */
    public double getStandardDeviation() {
        if (hasCovariance()) {
            Covariance cov = (Covariance) studyDesign.getCovariance()
                    .iterator().next();
            if (cov != null) {                
                List<StandardDeviation> sdList = cov.getStandardDeviationList();
                if (sdList != null && !sdList.isEmpty()) {
                    standardDeviationData = sdList.get(0);                    
                    return standardDeviationData.getValue();
                }                
            }            
        }
        return DEFAULT_STANDARD_DEVIATION;
    }

    /**
     * Sets the default standard deviation.
     */
    public void setDefaultStandardDeviation() {
        setStandardDeviation(DEFAULT_STANDARD_DEVIATION);
    }

    /**
     * Sets the standard deviation.
     * 
     * @param standardDeviation
     *            the new standard deviation
     */
    public void setStandardDeviation(double standardDeviation) {
        Set<Covariance> covarianceSet = studyDesign.getCovariance();
        Covariance covariance;
        Iterator<Covariance> itr;
        if (!hasCovariance()) {
            if (covarianceSet == null) {
                covarianceSet = new HashSet<Covariance>(1);
            } else {
                itr = covarianceSet.iterator();
                if (itr.hasNext()) {
                    covariance = ((Covariance) itr.next());
                    covarianceSet.clear();
                }
            }
        } else {
            itr = covarianceSet.iterator();
            if (itr.hasNext()) {
                covariance = ((Covariance) itr.next());
                covarianceSet.clear();
            }
        }
        covariance = createCovariance(standardDeviation);        
        studyDesign.addCovariance(covariance);
    }

    /**
     * Creates the covariance.
     * 
     * @param standardDeviation
     *            the standard deviation
     * @return the covariance
     */
    public Covariance createCovariance(double standardDeviation) {
        Covariance covariance = new Covariance();
        covariance.setType(DEFAULT_COVARIANCE_TYPE);
        covariance.setName(DEFAULT_COVARIANCE_NAME);
        double[][] covarianceMatrixData = new double[1][1];
        covarianceMatrixData[0][0] = 1;
        covariance.setBlobFromArray(covarianceMatrixData);
        covariance.setRows(1);
        covariance.setColumns(1);
        standardDeviationData.setValue(standardDeviation);
        List<StandardDeviation> list = new ArrayList<StandardDeviation>(1);
        list.add(standardDeviationData);
        covariance.setStandardDeviationList(list);
        return covariance;
    }

    /*--------------------
     * Mean
     *--------------------*/

    /**
     * Sets the default relative group size.
     */
    public void setDefaultMeansAndVariance() {
        int groups = getGroups();
        setDefaultMeans(groups);
        setDefaultStandardDeviation();
    }

    /**
     * Sets the default means.
     * 
     * @param numberOfGroups
     *            the new default means
     */
    private void setDefaultMeans(int numberOfGroups) {
        // int index = 0;
        NamedMatrix beta = studyDesign.getNamedMatrix(MATRIX_BETA);
        if (beta == null) {
            beta = buildBetaMatrix(numberOfGroups);
        }
        double[][] data = new double[numberOfGroups][1];
        for (int inc = 0; inc < numberOfGroups; inc++) {
            data[inc][DEFAULT_MEAN_COLUMN] = DEFAULT_MEAN;
        }
        beta.setDataFromArray(data);
        beta.setRows(numberOfGroups);
        studyDesign.setNamedMatrix(beta);
        System.gc();
    }

    /**
     * Sets the default means.
     * 
     * @param numberOfGroups
     *            the new default means
     */
    private void setOriginalMeans(int numberOfGroups) {
        int index = 0;
        NamedMatrix beta = studyDesign.getNamedMatrix(MATRIX_BETA);
        double[][] originalData = null;
        if (beta != null) {
            index = beta.getRows();
            originalData = beta.getData().getData();
        } else {
            beta = buildBetaMatrix(numberOfGroups);
        }
        int totalRows = index + numberOfGroups;
        double[][] changedData = new double[totalRows][1];
        for (int inc = 0; inc < index; inc++) {
            changedData[inc][DEFAULT_MEAN_COLUMN] = originalData[inc][DEFAULT_MEAN_COLUMN];
        }
        for (int inc = index; inc < totalRows; inc++) {
            changedData[inc][DEFAULT_MEAN_COLUMN] = DEFAULT_MEAN;
        }
        beta.setDataFromArray(changedData);
        beta.setRows(totalRows);
        studyDesign.setNamedMatrix(beta);
    }

    /**
     * Sets the mean.
     * 
     * @param mean
     *            the mean
     * @param position
     *            the position
     */
    public void setMean(double mean, int position) {
        NamedMatrix beta = studyDesign.getNamedMatrix(MATRIX_BETA);
        int groups = getGroups();
        if (beta == null) {
            beta = buildBetaMatrix(groups);
            double[][] data = new double[groups][1];
            for (int index = 0; index < groups; index++) {
                if (index != position)
                    data[index][DEFAULT_MEAN_COLUMN] = DEFAULT_MEAN;
                else
                    data[index][DEFAULT_MEAN_COLUMN] = mean;
            }
            beta.setDataFromArray(data);
        } else {
            double[][] originalData = beta.getData().getData();
            int originalRows = beta.getRows();
            if (originalRows > position) {
                originalData[position][DEFAULT_MEAN_COLUMN] = mean;
                beta.setDataFromArray(originalData);
            } else {
                double[][] changedData = new double[position + 1][DEFAULT_MEAN_COLUMN];
                for (int index = 0; index < originalRows; index++) {
                    changedData[index][DEFAULT_MEAN_COLUMN] = originalData[index][DEFAULT_MEAN_COLUMN];
                }
                changedData[position][DEFAULT_MEAN_COLUMN] = mean;
                beta.setDataFromArray(changedData);
            }
        }
        studyDesign.setNamedMatrix(beta);
    }

    /**
     * Gets the mean.
     * 
     * @param position
     *            the position
     * @return the mean
     */
    public double getMean(int position) {
        NamedMatrix beta = studyDesign.getNamedMatrix(MATRIX_BETA);
        double[][] data = beta.getData().getData();
        return data[position][DEFAULT_MEAN_COLUMN];
    }

    /*--------------------
     * Power
     *--------------------*/
    /**
     * Sets the power.
     * 
     * @param power
     *            the power
     * @param position
     *            the position
     */
    public void setPower(double power, int position) {
        List<NominalPower> powerList = studyDesign.getNominalPowerList();
        int size;
        if (powerList == null || powerList.isEmpty()) {
            powerList = new ArrayList<NominalPower>(5);
            powerList.add(new NominalPower(power));
        } else if (powerList.size() > position) {
            powerList.set(position, new NominalPower(power));
            System.gc();
        } else if (powerList.size() == position) {
            powerList.add(new NominalPower(power));
        } else {
            size = powerList.size();
            int additionalIndices = position - size;
            for (int inc = 0; inc < additionalIndices; inc++) {
                if (position == size + inc) {
                    powerList.add(new NominalPower(power));
                } else {
                    powerList.add(new NominalPower());
                }
            }
        }
        studyDesign.setNominalPowerList(powerList);
        setProgress(POWER_OR_SAMPLE_SIZE_ROW);
    }

    /**
     * Gets the power.
     * 
     * @param position
     *            the position
     * @return the power
     */
    public double getPower(int position) {
        List<NominalPower> powerList = studyDesign.getNominalPowerList();
        if (powerList != null && !powerList.isEmpty()) {
            if (powerList.size() > position && powerList.get(position) != null)
                return powerList.get(position).getValue();
        }
        return -1;
    }

    /**
     * Clear power list.
     */
    public void clearPowerList() {
        studyDesign.setNominalPowerList(null);
        resetProgress(POWER_OR_SAMPLE_SIZE_ROW);
        System.gc();
    }

    /**
     * Clear power.
     * 
     * @param position
     *            the position
     */
    public void clearPower(int position) {
        List<NominalPower> originalPowerList = studyDesign
                .getNominalPowerList();
        if (originalPowerList != null && !originalPowerList.isEmpty()) {
            List<NominalPower> newPowerList = null;
            int size = originalPowerList.size();
            if (size > 1) {
                newPowerList = new ArrayList<NominalPower>(size - 1);
                int count = 0;
                int index = 0;
                while (count < size) {
                    if (count != position) {
                        NominalPower np = originalPowerList.get(count);
                        newPowerList.add(index++, np);
                    }
                    count++;
                }
            } else {
                newPowerList = null;
                resetProgress(POWER_OR_SAMPLE_SIZE_ROW);
            }
            studyDesign.setNominalPowerList(newPowerList);
            originalPowerList.clear();
            System.gc();
        }
    }

    /**
     * Gets the power list size.
     * 
     * @return the power list size
     */
    public int getPowerListSize() {
        List<NominalPower> powerList = studyDesign.getNominalPowerList();
        if (powerList != null && !powerList.isEmpty()) {
            return powerList.size();
        }
        return 0;
    }

    /*--------------------
     * Sample Size
     *--------------------*/
    /**
     * Sets the sample size.
     * 
     * @param sampleSize
     *            the sample size
     * @param position
     *            the position
     */
    public void setSampleSize(int sampleSize, int position) {
        List<SampleSize> sampleSizeList = studyDesign.getSampleSizeList();
        int size;
        if (sampleSizeList == null || sampleSizeList.isEmpty()) {
            sampleSizeList = new ArrayList<SampleSize>(5);
            sampleSizeList.add(new SampleSize(sampleSize));
        } else if (sampleSizeList.size() - 1 > position) {
            sampleSizeList.set(position, new SampleSize(sampleSize));
            System.gc();
        } else if (sampleSizeList.size() == position) {
            sampleSizeList.add(new SampleSize(sampleSize));
        } else {
            size = sampleSizeList.size();
            int additionalIndices = position - size;
            for (int inc = 0; inc < additionalIndices; inc++) {
                if (position == size + inc) {
                    sampleSizeList.add(new SampleSize(sampleSize));
                } else {
                    sampleSizeList.add(new SampleSize());
                }
            }
        }
        studyDesign.setSampleSizeList(sampleSizeList);
        setProgress(POWER_OR_SAMPLE_SIZE_ROW);
    }

    /**
     * Gets the sample size.
     * 
     * @param position
     *            the position
     * @return the sample size
     */
    public int getSampleSize(int position) {
        List<SampleSize> sampleSizeList = studyDesign.getSampleSizeList();
        if (sampleSizeList != null && !sampleSizeList.isEmpty()) {
            if (sampleSizeList.size() > position
                    && sampleSizeList.get(position) != null)
                return sampleSizeList.get(position).getValue();
        }
        return -1;
    }

    /**
     * Clear sample size list.
     */
    public void clearSampleSizeList() {
        studyDesign.setSampleSizeList(null);
        resetProgress(POWER_OR_SAMPLE_SIZE_ROW);
        System.gc();
    }

    /**
     * Clear sample size.
     * 
     * @param position
     *            the position
     */
    public void clearSampleSize(int position) {
        List<SampleSize> originalSampleSizeList = studyDesign
                .getSampleSizeList();
        if (originalSampleSizeList != null && !originalSampleSizeList.isEmpty()) {
            List<SampleSize> newSampleSizeList = null;
            int size = originalSampleSizeList.size();
            if (size > 1) {
                newSampleSizeList = new ArrayList<SampleSize>(size - 1);
                int count = 0;
                int index = 0;
                while (count < size) {
                    if (count != position) {
                        SampleSize np = originalSampleSizeList.get(count);
                        newSampleSizeList.add(index++, np);
                    }
                    count++;
                }
                originalSampleSizeList.clear();
            } else {
                newSampleSizeList = null;
                resetProgress(POWER_OR_SAMPLE_SIZE_ROW);
            }
            System.gc();
            studyDesign.setSampleSizeList(newSampleSizeList);
        }
    }

    /**
     * Gets the sample size list size.
     * 
     * @return the sample size list size
     */
    public Integer getSampleSizeListSize() {
        List<SampleSize> sampleSizeList = studyDesign.getSampleSizeList();
        if (sampleSizeList != null && !sampleSizeList.isEmpty())
            return sampleSizeList.size();
        else
            return 0;
    }

    /*--------------------
     * Called when calculate button is clicked
     *--------------------*/
    /**
     * Sets the defaults.
     */
    public void setDefaults() {
        // Set view type to Guided Mode
        studyDesign.setViewTypeEnum(StudyDesignViewTypeEnum.GUIDED_MODE);
        /* Set One response */
        List<ResponseNode> responseList = new ArrayList<ResponseNode>();
        responseList.add(new ResponseNode(DEFAULT_RESPONSE));
        studyDesign.setResponseList(responseList);
        /* Set Hypothesis */
        List<HypothesisBetweenParticipantMapping> mapList = new ArrayList<HypothesisBetweenParticipantMapping>();
        for (BetweenParticipantFactor factor : studyDesign
                .getBetweenParticipantFactorList()) {
            mapList.add(new HypothesisBetweenParticipantMapping(
                    HypothesisTrendTypeEnum.NONE, factor));
        }
        Hypothesis hypothesis = new Hypothesis(HypothesisTypeEnum.MAIN_EFFECT,
                mapList, null);
        studyDesign.setHypothesisToSet(hypothesis);
        /* Set statistical test */
        List<StatisticalTest> statTestList = new ArrayList<StatisticalTest>();
        statTestList.add(new StatisticalTest(StatisticalTestTypeEnum.UNIREP));
        studyDesign.setStatisticalTestList(statTestList);

        // Add Beta Scale List
        List<BetaScale> betaScaleList = new ArrayList<BetaScale>(1);
        betaScaleList.add(new BetaScale(1.0));
        studyDesign.setBetaScaleList(betaScaleList);

        // Add Sigma Scale List
        List<SigmaScale> sigmaScaleList = new ArrayList<SigmaScale>(1);
        sigmaScaleList.add(new SigmaScale(1.0));
        studyDesign.setSigmaScaleList(sigmaScaleList);
    }

    /**
     * Builds the beta matrix.
     * 
     * @param groups
     *            the groups
     * @return the named matrix
     */
    private NamedMatrix buildBetaMatrix(int groups) {
        NamedMatrix beta = new NamedMatrix(MATRIX_BETA);
        beta.setRows(groups);
        beta.setColumns(1);
        return beta;
    }

}
