package afstudeerproject;

import afstudeerproject.Basic.Curve;
import afstudeerproject.Basic.Integration.Integrator.EulerIntegrator;
import afstudeerproject.Basic.Integration.Integrator.Integrator;
import afstudeerproject.Basic.Integration.SegmentKernel.DirectionalKernel;
import afstudeerproject.Basic.Integration.SegmentKernel.LocationalKernel;
import afstudeerproject.Basic.Segment;
import afstudeerproject.Basic.Vector;
import afstudeerproject.Models.Dataset;
import afstudeerproject.Models.DatasetGrid;
import afstudeerproject.Settings.IntegrationSettings;
import afstudeerproject.Settings.ModelSettings;
import afstudeerproject.Settings.OutputSettings;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import sun.security.pkcs11.P11TlsKeyMaterialGenerator;

public class Afstudeerproject {

    public static Dataset dataset = null;
    static public boolean drawclustertoggle = false;
    public static ArrayList<Integrator> ints = new ArrayList<Integrator>();
    public static ArrayList<Curve> curves = new ArrayList<Curve>();

    public static void main(String[] args) {

        Log.log("Program start");

        if (ModelSettings.gridCellSize[0] != ModelSettings.gridCellSize[1]
                || ModelSettings.gridCellSize[0] != ModelSettings.gridCellSize[2]) {
            System.err.println("Grid spacing not equal in all directions, this may impact results.");
            System.err.println("x: " + ModelSettings.gridCellSize[0]);
            System.err.println("y: " + ModelSettings.gridCellSize[1]);
            System.err.println("z: " + ModelSettings.gridCellSize[2]);
        }

        File f = new File(ModelSettings.filename);

        try {
            dataset = new DatasetGrid(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Log.log("dataset loaded");


        runIntegration();


        Log.log("Integration done");


        writeVTK();
        Log.log(".vtk files written");
        Log.showtime();


    }

    public static void runIntegration() {
        PercPrettyPrinter p = new PercPrettyPrinter("Integration of " + IntegrationSettings.numIntegrations + " curves", IntegrationSettings.numIntegrations);


        Vector[][] startpoints = new Vector[20][2];
        /*
         Vector dir = new Vector(new double[]{0,0,1});
        
         double x = 0.05; //0-0.25
         for (int i = 0; i < startpoints.length; i++) {
         startpoints[i][0] = new Vector(new double[]{x,0.01,0.005});
         startpoints[i][1] = dir;
         x+=0.01;
         }
         */
        
        /*
        int i = 0;
        startpoints[i][0] = new Vector(new double[]{110,   150, 50  }); i++;
        
        startpoints[i][0] = new Vector(new double[]{110.5, 148, 50.5  }); i++;
        startpoints[i][0] = new Vector(new double[]{111,   146, 51    }); i++;
        startpoints[i][0] = new Vector(new double[]{111.5, 144, 51.5  }); i++;
        startpoints[i][0] = new Vector(new double[]{112,   142, 52    }); i++;
        
        startpoints[i][0] = new Vector(new double[]{112.5, 140, 52.5  }); i++;
        
        startpoints[i][0] = new Vector(new double[]{113,   138, 52.6  }); i++;
        startpoints[i][0] = new Vector(new double[]{113.5, 136, 52.7  }); i++;
        startpoints[i][0] = new Vector(new double[]{114,   134, 52.8  }); i++;
        startpoints[i][0] = new Vector(new double[]{114.5, 132, 52.9  }); i++;
        
        startpoints[i][0] = new Vector(new double[]{115,   130, 53    }); i++;
        
        startpoints[i][0] = new Vector(new double[]{115.5, 128, 53    }); i++;
        startpoints[i][0] = new Vector(new double[]{116,   126, 53    }); i++;
        startpoints[i][0] = new Vector(new double[]{116.5, 124, 53    }); i++;
        startpoints[i][0] = new Vector(new double[]{117,   122, 53    }); i++;
        
        startpoints[i][0] = new Vector(new double[]{117.5, 120, 53}); i++;
        
        startpoints[i][0] = new Vector(new double[]{118,   118, 52.5  }); i++;
        startpoints[i][0] = new Vector(new double[]{118.5, 116, 52    }); i++;
        startpoints[i][0] = new Vector(new double[]{119,   114, 51.5  }); i++;
        startpoints[i][0] = new Vector(new double[]{119.5, 112, 51    }); i++;
        
        for (Vector[] vectors : startpoints) {
            vectors[1] = new Vector(new double[]{1,0,0});
        }
*/

        for (int ka = 0; ka < IntegrationSettings.numIntegrations; ka++) {

            Vector[] start = dataset.max();
            //Vector[] start = startpoints[ka];

            ints.add(new EulerIntegrator(
                    start[0],
                    start[1],
                    new LocationalKernel(IntegrationSettings.locationphi1, IntegrationSettings.locationphi2),
                    new DirectionalKernel(IntegrationSettings.directionphi1, IntegrationSettings.directionphi2)));


            EulerIntegrator ei = new EulerIntegrator(
                    start[0],
                    Vector.mul(start[1], -1),
                    new LocationalKernel(IntegrationSettings.locationphi1, IntegrationSettings.locationphi2),
                    new DirectionalKernel(IntegrationSettings.directionphi1, IntegrationSettings.directionphi2));
            //ei.alive = false;

            ints.add(ei);

            for (Integrator e : ints) {
                while (e.alive) {
                    e.step();
                }
            }

            Curve c = new Curve(ints.get(0), ints.get(1));
            ints.remove(1);
            ints.remove(0);
            Afstudeerproject.curves.add(c);


            c.compensate();


            p.print(ka + 1);
        }
        p.close();


    }

    public static void writeVTK() {

        if (OutputSettings.writeVTK) {
            if (OutputSettings.writeDataset) {
                try {
                    FileWriter datasetfile = new FileWriter(new File(OutputSettings.datasetFile));

                    String header =
                            "# vtk DataFile Version 2.1\n"
                            + "Dataset\n"
                            + "ASCII\n"
                            + "DATASET POLYDATA\n";

                    int numpoints = 0;

                    StringBuilder pointdata = new StringBuilder();
                    StringBuilder linedata = new StringBuilder();
                    StringBuilder pointdatadata = new StringBuilder();
                    int numpaths = 0;

                    int num = 0;
                    int num2 = 0;
                    PercPrettyPrinter p = new PercPrettyPrinter("Writing vtk", Afstudeerproject.dataset.segments.size());
                    for (Segment segment : Afstudeerproject.dataset.segments) {
                        num++;
                        if (num > 100000) {
                            String s = pointdata.toString();
                            pointdata = new StringBuilder(s);
                            s = linedata.toString();
                            linedata = new StringBuilder(s);
                            s = pointdatadata.toString();
                            pointdatadata = new StringBuilder(s);
                            num = 0;
                            num2++;
                            System.out.println("triggered overflow protection");
                            p.print(num2 * 100000);
                        }
                        pointdata.append(segment.coords[0].toVTK()).append("\n").append(segment.coords[1].toVTK()).append("\n");
                        linedata.append("2 ").append(numpoints++).append(" ").append(numpoints++).append("\n");
                        pointdatadata.append(segment.weight).append("\n");
                        pointdatadata.append(segment.weight).append("\n");
                        numpaths++;

                    }
                    p.close();


                    String pointheader = "POINTS " + numpoints + " float\n";
                    String lineheader = "LINES " + numpaths + " " + (numpoints + numpaths) + "\n";
                    String pointdataheader = "POINT_DATA " + numpoints + " \nSCALARS realAmp float 1 \nLOOKUP_TABLE default \n";

                    datasetfile.write(header);
                    datasetfile.write(pointheader);
                    datasetfile.write(pointdata.toString());
                    datasetfile.write(lineheader);
                    datasetfile.write(linedata.toString());
                    datasetfile.write(pointdataheader.toString());
                    datasetfile.write(pointdatadata.toString());
                    datasetfile.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (OutputSettings.writeGrid) {
                try {
                    FileWriter grid = new FileWriter(new File(OutputSettings.gridFile));

                    String header =
                            "# vtk DataFile Version 2.1\n"
                            + "Grid\n"
                            + "ASCII\n"
                            + "DATASET STRUCTURED_POINTS\n"
                            + "DIMENSIONS " + ModelSettings.gridResolution[0] + " " + ModelSettings.gridResolution[1] + " " + ModelSettings.gridResolution[2]
                            + "ORIGIN " + ModelSettings.gridValue[0][0] + " " + ModelSettings.gridValue[1][0] + " " + ModelSettings.gridValue[2][0]
                            + "SPACING " + ModelSettings.gridCellSize[0] + " " + ModelSettings.gridCellSize[1] + " " + ModelSettings.gridCellSize[2];
                    grid.write(header);
                    grid.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (OutputSettings.writeIntegration) {
                try {

                    FileWriter integration = new FileWriter(new File(OutputSettings.integrationFile));
                    String header =
                            "# vtk DataFile Version 2.1\n"
                            + "Curves\n"
                            + "ASCII\n"
                            + "DATASET POLYDATA\n";

                    int numpoints = 0;

                    StringBuilder points = new StringBuilder();
                    StringBuilder lines = new StringBuilder();
                    StringBuilder realamp = new StringBuilder();
                    StringBuilder displayamp = new StringBuilder();

                    int numpaths = 0;


                    for (int i = 0; i < Afstudeerproject.curves.size(); i++) {
                        if (OutputSettings.filterInts && (OutputSettings.filterIntsLow > i || OutputSettings.filterIntsHigh < i)) {
                            continue;
                        }
                        Curve c = Afstudeerproject.curves.get(i);


                        if (!OutputSettings.filteronweight || c.weight() > OutputSettings.minweight) {
                            c.calcDisplayAmp();
                            for (Integrator e : c.integrators) {

                                numpaths++;
                                StringBuilder pathpoints = new StringBuilder((e.points.size()) + " ");

                                for (int j = 0; j < e.points.size(); j++) {
                                    points.append(e.points.get(j).point.toVTK()).append("\n");
                                    realamp.append(e.points.get(j).realAmp).append("\n");
                                    displayamp.append(e.points.get(j).displayAmp).append("\n");

                                    pathpoints.append(numpoints).append(" ");
                                    numpoints++;
                                }
                                lines.append(pathpoints).append("\n");

                            }
                        }
                    }
                    String pointheader = "POINTS " + numpoints + " float\n";
                    String lineheader = "LINES " + numpaths + " " + (numpoints + numpaths) + "\n";
                    String realampheader = "POINT_DATA " + numpoints + " \nSCALARS realAmp float 1 \nLOOKUP_TABLE default \n";
                    String displayampheader = "\n" + "SCALARS displayAmp float 1 \nLOOKUP_TABLE default \n";


                    integration.write(header);
                    integration.write(pointheader);
                    integration.write(points.toString());
                    integration.write(lineheader);
                    integration.write(lines.toString());
                    integration.write(realampheader);
                    integration.write(realamp.toString());
                    integration.write(displayampheader);
                    integration.write(displayamp.toString());


                    integration.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
