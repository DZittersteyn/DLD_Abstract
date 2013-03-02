package afstudeerproject.Settings;

public class IntegrationSettings {
    public static final int maxsteps = 2000;
    public static final double stepsize = 0.001;
    //public static final double stepsize = 0.1;
    public static final int numIntegrations = 25   ;
    
    
    // works for dti data
    //public static final double locationphi1 = 2 * ModelSettings.gridCellRange[0];
    //public static final double locationphi2 = locationphi1 *1.5;
    
    //public static final double directionphi1 = Math.PI / 4;
   // public static final double directionphi2 = directionphi1 *1.5;
    
    // works for laminar flow data
    public static final double locationphi1 = 1 * ModelSettings.gridCellRange[0];
    public static final double locationphi2 = 2 * ModelSettings.gridCellRange[0];
    
    public static final double directionphi1 = 3*Math.PI / 8;
    public static final double directionphi2 = 5 * Math.PI / 8;
}
