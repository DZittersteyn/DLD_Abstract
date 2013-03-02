/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afstudeerproject.Settings;

/**
 *
 * @author dirk
 */
public class OutputSettings {

    public static final boolean writeVTK = true;
    
    public static final boolean writeGrid = false;
    public static final String gridFile = "grid.vtk";
    
    public static final boolean writeDataset = true;
    public static final String datasetFile = "dataset.vtk";
    
    public static final boolean filteronweight = false;
    public static final double minweight = 1e7;
    public static final boolean fixstart = true;
    public static final boolean writeIntegration = true;
    public static final String integrationFile = "integration.vtk";
    
    public static boolean filterInts = false;
    public static int filterIntsLow = 0;
    public static int filterIntsHigh = 20;
    public static boolean filterData = false;
    public static int filterDataLow = 0;
    public static int filterDataHigh = 200;
}
