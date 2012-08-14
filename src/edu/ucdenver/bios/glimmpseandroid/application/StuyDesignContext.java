package edu.ucdenver.bios.glimmpseandroid.application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.domain.Category;
import edu.ucdenver.bios.webservice.common.domain.Covariance;
import edu.ucdenver.bios.webservice.common.domain.Hypothesis;
import edu.ucdenver.bios.webservice.common.domain.HypothesisBetweenParticipantMapping;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrixSet;
import edu.ucdenver.bios.webservice.common.domain.RelativeGroupSize;
import edu.ucdenver.bios.webservice.common.domain.ResponseNode;
import edu.ucdenver.bios.webservice.common.domain.SampleSize;
import edu.ucdenver.bios.webservice.common.domain.StandardDeviation;
import edu.ucdenver.bios.webservice.common.domain.StatisticalTest;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.domain.TypeIError;
import edu.ucdenver.bios.webservice.common.enums.CovarianceTypeEnum;
import edu.ucdenver.bios.webservice.common.enums.HypothesisTrendTypeEnum;
import edu.ucdenver.bios.webservice.common.enums.HypothesisTypeEnum;
import edu.ucdenver.bios.webservice.common.enums.SolutionTypeEnum;
import edu.ucdenver.bios.webservice.common.enums.StatisticalTestTypeEnum;

public class StuyDesignContext {
    /*--------------------
     * Singleton Instance Variable
     *--------------------*/
    /** The instance. */
    private static StuyDesignContext instance = null;

    private static StudyDesign studyDesign;

    /** The progress. */
    private Integer[] progress = { 0, 0, 0, 0, 0, 0, 0 };

    static final String DEFAULT_GROUP_NAME = "Group";
    static final int DEFAULT_GROUPS = 2;
    static final double DEFAULT_ALPHA = 0.01;
    static final String DEFAULT_RESPONSE = "Response";
    static final int DEFAULT_RELATIVE_GROUP_SIZE = 1;
    static final CovarianceTypeEnum DEFAULT_COVARIANCE_TYPE = CovarianceTypeEnum.UNSTRUCTURED_CORRELATION;
    static final double DEFAULT_VARIANCE = 1.0;
    static final double DEFAULT_MEAN = 0.0;
    static final int DEFAULT_MEAN_COLUMN = 0;
    static final double[][] DEFAULT_CORELATION_MATRIX = { { 1 } };

    static final String MATRIX_BETA = "beta";

    /*--------------------
     * Constructors
     *--------------------*/
    /**
     * Instantiates a new global variables.
     */
    StuyDesignContext() {
        studyDesign = new StudyDesign();
    }

    /*--------------------
     * Getter/Setter Methods
     *--------------------*/

    public StudyDesign getStudyDesign() {
        return studyDesign;
    }

    public void setStudyDesign(StudyDesign study) {
        studyDesign = study;
    }

    /**
     * Gets the single instance of GlobalVariables.
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

    public Integer[] getProgress() {
        return this.progress;
    }

    public int getTotalProgress() {
        int sum = 0;
        int index = 0;
        while (index < this.progress.length) {
            sum = sum + this.progress[index++];
        }
        return sum;
    }

    public int getIndividualProgress(int position) {
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
        while (index < this.progress.length) {
            this.progress[index++] = 0;
        }
    }

    /*--------------------
     * # Groups
     *--------------------*/
    /*
     * Convinienec Method which sets default Number of Groups
     */
    public void setDefaultGroups() {
        setGroups(DEFAULT_GROUPS);
    }

    /*
     * Convinienec Method which sets given Number of Groups
     */
    public void setGroups(int groups) {
        List<BetweenParticipantFactor> list = studyDesign
                .getBetweenParticipantFactorList();
        List<Category> categoryList;
        if (list == null || list.isEmpty()) {
            list = new ArrayList<BetweenParticipantFactor>();
        } else {
            if (list.size() > 0) {
                categoryList = list.get(0).getCategoryList();
                if (categoryList == null || categoryList.isEmpty())
                    ;
                else {
                    list.get(0).setCategoryList(null);
                    list.set(0, null);
                    System.gc();
                }
            }
        }
        list.add(0, createCategory(groups));
        studyDesign.setBetweenParticipantFactorList(list);
    }

    /*
     * Convinienec Method which returns Number of Groups
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

    /*
     * Convinienec Method which returns Number of Groups
     */
    private BetweenParticipantFactor createCategory(int groups) {
        List<Category> categoryList = new ArrayList<Category>();
        int index = 0;
        while (index < groups) {
            categoryList.add(new Category(Integer.toString(index++)));
        }
        return new BetweenParticipantFactor(DEFAULT_GROUP_NAME, categoryList);
    }

    /*--------------------
     * Type I Error
     *--------------------*/
    /*
     * Convinienec Method which returns Number of Groups
     */
    public void setDefaultTypeIError() {
        setTypeIError(DEFAULT_ALPHA);
    }

    public void setTypeIError(Double alpha) {
        List<TypeIError> list = studyDesign.getAlphaList();
        if (list == null || list.isEmpty()) {
            list = new ArrayList<TypeIError>();
        } else {
            if (list.size() > 0) {
                list.set(0, null);
                System.gc();
            }
        }
        list.add(0, new TypeIError(alpha));
        studyDesign.setAlphaList(list);
    }

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
     * Smallest Group Size
     *--------------------*/
    public void setDefaultSmallestGroupSize() {
        setSmallestGroupSize(0);
    }

    public void setSmallestGroupSize(int sampleSize) {
        List<SampleSize> list = studyDesign.getSampleSizeList();
        if (list == null || list.isEmpty()) {
            list = new ArrayList<SampleSize>();
        } else {
            if (list.size() > 0) {
                list.set(0, null);
                System.gc();
            }
        }
        list.add(0, new SampleSize(sampleSize));
        studyDesign.setSampleSizeList(list);
    }

    public int getSmallestGroupSize() {
        List<SampleSize> list = studyDesign.getSampleSizeList();
        if (list == null || list.isEmpty()) {
            return 0;
        } else {
            if (list.size() < 1) {
                return 0;
            }
        }
        return list.get(0).getValue();
    }

    /*--------------------
     * Relative Group Size
     *--------------------*/
    public void setDefaultRelativeGroupSize(int position) {
        setRelativeGroupSize(DEFAULT_RELATIVE_GROUP_SIZE, position);
    }

    public void setRelativeGroupSize(int relativeGroupSize, int position) {
        List<RelativeGroupSize> list = studyDesign.getRelativeGroupSizeList();
        if (list == null || list.isEmpty()) {
            // System.out.println("List Empty");
            list = new ArrayList<RelativeGroupSize>(getGroups());
            list.add(position, new RelativeGroupSize(relativeGroupSize));
        } else {
            if (list.size() > position) {
                if (list.get(position) != null) {
                    // System.out.println("List.size > position && list(position) != null");
                    list.set(position, new RelativeGroupSize(relativeGroupSize));
                } else {
                    list.add(position, new RelativeGroupSize(relativeGroupSize));
                }
            } else {
                // System.out.println("List.size > position && list(position) != null");
                list.add(position, new RelativeGroupSize(relativeGroupSize));
            }
        }
        studyDesign.setRelativeGroupSizeList(list);
    }

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

    private void synchForRemovedGroups(int numberOfGroups) {
        /* Relative Group Size */
        List<RelativeGroupSize> list = studyDesign.getRelativeGroupSizeList();
        int index;
        if (list != null && !list.isEmpty()) {
            index = list.size() - numberOfGroups;
            for (int inc = (numberOfGroups - 1); inc >= 0; inc--) {
                // System.out.println("deleted  obj at "+(index+inc));
                studyDesign.getRelativeGroupSizeList().remove(index + inc);
            }
            System.gc();
        }
        /* Means */
        NamedMatrix beta = studyDesign.getNamedMatrix(MATRIX_BETA);
        if (beta != null) {            
            int originalRows = beta.getRows();
            double[][] originalData = beta.getData().getData();
            index = originalRows-numberOfGroups;
            System.out.println("rows after removing : "+index);
            double[][] changedData = new double[index][1];
            for (int inc = 0; inc < index ; inc++) {
                System.out.println("transfering data ...");
                changedData[inc][0] = originalData[inc][0];
            }
            beta.setRows(index);
            beta.setDataFromArray(changedData);
        }
        studyDesign.setNamedMatrix(beta);
        System.gc();
    }

    private void synchForAddedGroups(int numberOfGroups) {
        /* Relative Group Size */
        List<RelativeGroupSize> list = studyDesign.getRelativeGroupSizeList();
        int index = 0;
        if (list != null && !list.isEmpty())
            index = list.size();
        /*
         * while (index < numberOfGroups) {
         * setDefaultRelativeGroupSize(index++); }
         */
        for (int inc = index; inc < index + numberOfGroups; inc++) {
            // System.out.println("added group at "+inc);
            setDefaultRelativeGroupSize(inc);
        }
        /* Variance */
        setVariance(getVariance());
        /* Means */
        setDefaultMeans(numberOfGroups);              
    }

    public void synchForGroupChanges(int numberOfGroupsChanged) {
        if (numberOfGroupsChanged > 0) {
            // System.out.println("removed groups "+numberOfGroupsChanged);
            synchForRemovedGroups(numberOfGroupsChanged);
        } else if (numberOfGroupsChanged < 0) {
            int groups = Math.abs(numberOfGroupsChanged);
            synchForAddedGroups(groups);
        }
    }

    public boolean relativeGroupEquality() {
        int index = 0;
        boolean equalityFlagRelativeGp = true;
        int groups = getGroups() - 1;
        while (index < groups) {
            equalityFlagRelativeGp = ((getRelativeGroupSize(index) == getRelativeGroupSize(index + 1)) ? true
                    : false);
            // System.out.println("index: "+getRelativeGroupSize(index)+" index+1: "+getRelativeGroupSize(index+1)+"equality flag : "+equalityFlagRelativeGp);
            if (!equalityFlagRelativeGp)
                return false;
            index++;
        }
        return equalityFlagRelativeGp;
    }

    /*--------------------
     * Solving For
     *--------------------*/
    public void setSolvingFor(String solutionType) {
        if (solutionType != null) {
            if (SolutionTypeEnum.SAMPLE_SIZE.getId().equals(solutionType))
                studyDesign.setSolutionTypeEnum(SolutionTypeEnum.SAMPLE_SIZE);
            else
                studyDesign.setSolutionTypeEnum(SolutionTypeEnum.POWER);
        }
    }

    public String getSolvingFor() {
        SolutionTypeEnum solvingFor = studyDesign.getSolutionTypeEnum();
        if (solvingFor == null || solvingFor.equals("")) {
            return null;
        }
        return solvingFor.getId();
    }

    /*--------------------
     * Variance
     *--------------------*/
    public boolean hasCovariance() {
        Set<Covariance> covarianceSet = studyDesign.getCovariance();
        if (covarianceSet == null || covarianceSet.isEmpty())
            return false;
        Iterator<Covariance> itr = covarianceSet.iterator();
        if (!itr.hasNext())
            return false;
        Covariance covariance = (Covariance) itr.next();
        List<StandardDeviation> sdList = covariance.getStandardDeviationList();
        if (sdList == null || sdList.isEmpty())
            return false;
        return true;
    }

    public double getVariance() {
        if (hasCovariance()) {
            return Math.pow(((Covariance) studyDesign.getCovariance()
                    .iterator().next()).getStandardDeviationList().get(0)
                    .getValue(), 2);
        } else {
            return DEFAULT_VARIANCE;
        }
    }

    public void setDefaultVariance() {
        setVariance(DEFAULT_VARIANCE);
    }

    public void setVariance(double variance) {
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
        covariance = createCovariance(variance);
        // System.out.println("sd in covariance "+covariance.getStandardDeviationList().get(0).getValue());
        studyDesign.addCovariance(covariance);
    }

    public Covariance createCovariance(double variance) {
        // System.out.println("createVariance");
        Covariance covariance = new Covariance();
        covariance.setBlobFromArray(DEFAULT_CORELATION_MATRIX);
        StandardDeviation sd = new StandardDeviation();
        sd.setValue(Math.sqrt(variance));
        List<StandardDeviation> sdList = new ArrayList<StandardDeviation>(1);
        sdList.add(sd);
        covariance.setStandardDeviationList(sdList);
        return covariance;
    }

    /*--------------------
     * Mean
     *--------------------*/
    
    private void setDefaultMeans(int numberOfGroups){
        int index = 0;
        NamedMatrix beta = studyDesign.getNamedMatrix(MATRIX_BETA);
        double[][] originalData = null;
        if (beta != null) {      
            System.out.println("BetaMeatrix not null");
            index = beta.getRows();
            originalData = beta.getData().getData();
        }     
        else {
            beta = buildBetaMatrix(numberOfGroups);
        }
        int totalRows = index + numberOfGroups;
        //System.out.println("new groups "+totalRows);
        double[][] changedData = new double[totalRows][1];
        for (int inc = 0; inc < index ; inc++) {
            //System.out.println("transfering data ....");
            changedData[inc][DEFAULT_MEAN_COLUMN] = originalData[inc][DEFAULT_MEAN_COLUMN];
        }
        for (int inc = index; inc < totalRows; inc++) {
            //System.out.println("mean added at "+inc);
            System.out.println("adding row at "+inc);
            changedData[inc][DEFAULT_MEAN_COLUMN] = DEFAULT_MEAN;
        }        
        beta.setDataFromArray(changedData);
        beta.setRows(totalRows);
        studyDesign.setNamedMatrix(beta);
       
        /*index = 0;
        NamedMatrix beta = studyDesign.getNamedMatrix(MATRIX_BETA);
        double[][] originalData = null;
        if (beta != null) {      
            System.out.println("BetaMeatrix not null");
            index = beta.getRows();
            originalData = beta.getData().getData();
        }     
        else {
            beta = buildBetaMatrix(numberOfGroups);
        }
        int totalRows = index + numberOfGroups;
        System.out.println("new groups "+totalRows);
        double[][] changedData = new double[totalRows][1];
        for (int inc = 0; inc < index ; inc++) {
            System.out.println("transfering data ....");
            changedData[inc][0] = originalData[inc][0];
        }
        for (int inc = index; inc < totalRows; inc++) {
            System.out.println("mean added at "+inc);
            changedData[inc][0] = DEFAULT_MEAN;
        }
        beta.setDataFromArray(changedData); */
    }

    public void setMean(double mean, int position) {
        NamedMatrix beta = studyDesign.getNamedMatrix(MATRIX_BETA);
        int groups = getGroups();
        if (beta == null) {
            beta = buildBetaMatrix(groups);
            double[][] data = new double[groups][1];
            for (int index = 0; index < groups; index++) {
                if (index != position)
                    data[index][DEFAULT_MEAN_COLUMN] = 0.0;
                else
                    data[index][DEFAULT_MEAN_COLUMN] = mean;
            }
            beta.setDataFromArray(data);
        } else {
            double[][] originalData = beta.getData().getData();
            int originalRows = beta.getRows();
            System.out.println("originalRows "+originalRows);
            System.out.println("position : "+position);
            if(originalRows > position){
                originalData[position][DEFAULT_MEAN_COLUMN] = mean;
                beta.setDataFromArray(originalData);
            }else {                
                double[][] changedData = new double[position+1][DEFAULT_MEAN_COLUMN];                
                for(int index = 0; index < originalRows; index++){
                    changedData[index][DEFAULT_MEAN_COLUMN] = originalData[index][DEFAULT_MEAN_COLUMN];
                }
                changedData[position][DEFAULT_MEAN_COLUMN] = mean;
                beta.setDataFromArray(changedData);
            }
        }
        studyDesign.setNamedMatrix(beta);
    }

    public double getMean(int position) {
        /*
         * List<ResponseNode> responseList = studyDesign.getResponseList();
         * for(ResponseNode response : responseList) { response.ge }
         * 
         * List<RelativeGroupSize> list =
         * studyDesign.getRelativeGroupSizeList(); if (list == null ||
         * list.isEmpty()) { return 0; } else { if (list.size() <= position) {
         * return 0; } } return list.get(position).getValue();
         */
        NamedMatrix beta = studyDesign.getNamedMatrix(MATRIX_BETA);
        double[][] data = beta.getData().getData();        
        return data[position][DEFAULT_MEAN_COLUMN];
    }

    /*--------------------
     * Called when calculate button is clicked
     *--------------------*/
    public void setDefaults() {
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
    }

    private NamedMatrix buildBetaMatrix(int groups) {
        // double [][] betaData = {{0},{1}};
        NamedMatrix beta = new NamedMatrix(MATRIX_BETA);
        // beta.setDataFromArray(betaData);
        beta.setRows(groups);
        beta.setColumns(1);
        return beta;
    }
}
