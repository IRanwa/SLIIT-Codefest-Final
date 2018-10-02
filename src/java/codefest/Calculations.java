/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codefest;

/**
 *
 * @author USER
 */
public class Calculations {

    private Double proceRate5Min = 0.0;
    private Double errorPer5min = 0.0;
    private Double proceRate1Hr = 0.0;
    private Double errorPer1Hr= 0.0;
    private int errorCount;
    private int totalCount;

    public void incrementError(){
        System.out.println(errorCount);
        errorCount++;
    }
    
    public void incrementTotal(){
        System.out.println(totalCount);
        totalCount++;
    }
    
    public Double processingRate( long longNum) {
        if (longNum != 0) {
            double tempPRate = ((double) totalCount / longNum) * 1000;
            proceRate5Min = (proceRate5Min+tempPRate)/2;
            return tempPRate;
        }
        return 0.0;
    }

    public Double ErrorRate() {
        double tempERate = ((double) errorCount / totalCount) * 100.0;
        errorPer5min = (errorPer5min+tempERate)/2;
        return tempERate;
    }

    public void reset1Sec() {
        errorCount=0;
        totalCount=0;
    }
    
    public void reset5Min(){
        proceRate1Hr = (proceRate1Hr+proceRate5Min)/2;
        errorPer1Hr = (errorPer1Hr+proceRate5Min)/2;
        proceRate5Min = 0.0;
        errorPer5min = 0.0;
    }
    
    public void reset1Hr(){
        proceRate1Hr = 0.0;
        errorPer1Hr = 0.0;
    }

    public Double error5MinRate() {
        return this.errorPer5min;
    }
    
    public Double process5MinRate() {
        return this.proceRate5Min;
    }
    
    public Double getProceRate1Hr() {
        return proceRate1Hr;
    }

    public Double getErrorPer1Hr() {
        return errorPer1Hr;
    }
    
}
