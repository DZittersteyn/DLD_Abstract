package afstudeerproject.Settings;

public class ModelSettings {
    
    public final static boolean isWeighed = true;
    public final static String filename = "SLD/roel1.sld";
   
    
    public final static int[] gridResolution = {
      // 17*5,
      //  19*5,
      //  12*5,
        16,
        16,
        9*4
        
    };
    public final static double[][] gridValue = {
        
       // {30, 200},
       // {30, 220},
       // { -10, 110}, // For DTI
        
        {-0.10, 0.30},
        {-0.10, 0.30},
        {-0.10, 0.80}, // For Dataset Roel
    };
    public final static double[] gridSize = {
        gridValue[0][1] - gridValue[0][0],
        gridValue[1][1] - gridValue[1][0],
        gridValue[2][1] - gridValue[2][0],
    };
    public final static double[] gridCellSize = {
        gridSize[0]/(double)(gridResolution[0]-1),
        gridSize[1]/(double)(gridResolution[1]-1),
        gridSize[2]/(double)(gridResolution[2]-1),
    };
    public final static double[] gridCellRange = {
        gridCellSize[0]/2,
        gridCellSize[1]/2,
        gridCellSize[2]/2,
    };
}
