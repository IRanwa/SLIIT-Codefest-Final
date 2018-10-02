/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Imesh Ranawaka
 */
public class Stats {
    private double errorAvg;
    private double processAvg;

    public Stats(double errorAvg, double processAvg) {
        this.errorAvg = errorAvg;
        this.processAvg = processAvg;
    }

    public double getErrorAvg() {
        return errorAvg;
    }

    public double getProcessAvg() {
        return processAvg;
    }

    public void setErrorAvg(double errorAvg) {
        this.errorAvg = errorAvg;
    }

    public void setProcessAvg(double processAvg) {
        this.processAvg = processAvg;
    }
    
}
