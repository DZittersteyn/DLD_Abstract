
package afstudeerproject.Basic.Integration.Integrator;

import afstudeerproject.Basic.UsableWeighedSegment;
import afstudeerproject.Basic.Vector;
import java.util.ArrayList;

public class IntegrationResult extends Step{

    public double realAmp = 0;
    public double displayAmp = 0;
    
    public IntegrationResult(){
        super();
        
    }
    public IntegrationResult(Vector point){
        super(point);
    }
    public IntegrationResult(double amp, Vector point, ArrayList<UsableWeighedSegment> considered){
        super(point, considered); 
        this.realAmp = amp;
    }
    
    
}
