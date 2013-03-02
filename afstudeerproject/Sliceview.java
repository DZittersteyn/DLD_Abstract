package afstudeerproject;

import afstudeerproject.Basic.Curve;
import afstudeerproject.Basic.Integration.Integrator.EulerIntegrator;
import afstudeerproject.Basic.Integration.Integrator.Integrator;
import afstudeerproject.Basic.Integration.GridKernel.TrapeziumKernel;
import afstudeerproject.Basic.Segment;
import afstudeerproject.Basic.Vector;
import afstudeerproject.Exceptions.OutOfRangeOfDisplay;
import afstudeerproject.Models.Grid;
import afstudeerproject.Settings.IntegrationSettings;
import afstudeerproject.Settings.ModelSettings;
import afstudeerproject.Settings.OutputSettings;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Sliceview extends javax.swing.JFrame {

    int sliceMinPoint = 100;
    int sliceMaxPoint = 500;
    Vector clickpointpos = null;

    ArrayList<Segment> draw;
    
    public Sliceview() {
        initComponents();
       // draw = Afstudeerproject.segments.segmentsInRange(0, new Vector(new double[]{}));
        this.setVisible(true);
    }

    public double[] toRealCoord(Point p) {
        double x = p.x;
        double y = p.y;


        x -= sliceMinPoint;
        y -= sliceMinPoint;


        x *= ModelSettings.gridValue[0][1] - ModelSettings.gridValue[0][0];
        y *= ModelSettings.gridValue[1][1] - ModelSettings.gridValue[1][0];

        x /= sliceMaxPoint - sliceMinPoint;
        y /= sliceMaxPoint - sliceMinPoint;

        x += ModelSettings.gridValue[0][0];
        y += ModelSettings.gridValue[1][0];

        double z = ModelSettings.gridValue[2][0] + ModelSettings.gridCellSize[2] * (Integer) this.numLayers.getValue();

        return new double[]{x, y, z};
    }

    public Point toScreenCoord(double[] v) throws OutOfRangeOfDisplay {
        return toScreenCoord(new Vector(v));
    }

    public double[] toScreenLength(double[] c) {

        double[] coords = {c[0], c[1], c[2]};

        coords[0] *= sliceMaxPoint - sliceMinPoint;
        coords[1] *= sliceMaxPoint - sliceMinPoint;
        coords[2] *= sliceMaxPoint - sliceMinPoint;

        coords[0] /= ModelSettings.gridValue[0][1] - ModelSettings.gridValue[0][0];
        coords[1] /= ModelSettings.gridValue[1][1] - ModelSettings.gridValue[1][0];
        coords[2] /= ModelSettings.gridValue[2][1] - ModelSettings.gridValue[2][0];

        return coords;

    }

    public Point toScreenCoord(Vector v) throws OutOfRangeOfDisplay {

        if (v.x < ModelSettings.gridValue[0][0]
                || v.x > ModelSettings.gridValue[0][1]
                || v.y < ModelSettings.gridValue[1][0]
                || v.y > ModelSettings.gridValue[1][1]
                || v.z < (Integer) this.numLayers.getValue() * ModelSettings.gridCellSize[2] + ModelSettings.gridValue[2][0] - ModelSettings.gridCellRange[2]
                || v.z > (Integer) this.numLayers.getValue() * ModelSettings.gridCellSize[2] + ModelSettings.gridValue[2][0] + ModelSettings.gridCellRange[2]) {
            throw new OutOfRangeOfDisplay("Vector: " + v + "range was: {"
                    + ModelSettings.gridValue[0][0] + ", "
                    + ModelSettings.gridValue[0][1] + "}, {"
                    + ModelSettings.gridValue[1][0] + ", "
                    + ModelSettings.gridValue[1][1] + "}, {"
                    + ((Integer) this.numLayers.getValue() * ModelSettings.gridCellSize[2] - ModelSettings.gridCellRange[2] + ModelSettings.gridValue[2][0]) + ", "
                    + ((Integer) this.numLayers.getValue() * ModelSettings.gridCellSize[2] + ModelSettings.gridCellRange[2] + ModelSettings.gridValue[2][0]) + "}"
                    + "}");
        }




        double x = v.x;
        double y = v.y;
        x -= ModelSettings.gridValue[0][0];
        y -= ModelSettings.gridValue[1][0];

        x *= sliceMaxPoint - sliceMinPoint;
        y *= sliceMaxPoint - sliceMinPoint;

        x /= ModelSettings.gridValue[0][1] - ModelSettings.gridValue[0][0];
        y /= ModelSettings.gridValue[1][1] - ModelSettings.gridValue[1][0];

        x += sliceMinPoint;
        y += sliceMinPoint;

        return new Point(Math.round(Math.round(x)), Math.round(Math.round(y)));
    }

    public void clearSlice() {
        this.slice.getGraphics().clearRect(0, 0, 600, 600);
    }

    public void drawSlice(int index) {
        Graphics2D graphics = (Graphics2D) this.slice.getGraphics();

        

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        numLayers = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        slice = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        FilterDataLow = new javax.swing.JSpinner();
        FilterDataHigh = new javax.swing.JSpinner();
        FilterData = new javax.swing.JCheckBox();
        FilterInts = new javax.swing.JCheckBox();
        FilterIntsLow = new javax.swing.JSpinner();
        FilterIntsHigh = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        numLayers.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                numLayersStateChanged(evt);
            }
        });

        jLabel1.setText("layer");

        slice.setMaximumSize(new java.awt.Dimension(600, 600));
        slice.setMinimumSize(new java.awt.Dimension(600, 600));
        slice.setPreferredSize(new java.awt.Dimension(600, 600));
        slice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sliceMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout sliceLayout = new javax.swing.GroupLayout(slice);
        slice.setLayout(sliceLayout);
        sliceLayout.setHorizontalGroup(
            sliceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        sliceLayout.setVerticalGroup(
            sliceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jButton1.setText("Redraw");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Write");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        FilterData.setText("FilterData");
        FilterData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FilterDataActionPerformed(evt);
            }
        });

        FilterInts.setText("FilterInts");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(slice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(numLayers, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(FilterData)
                    .addComponent(FilterInts)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(52, 52, 52)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(FilterDataLow, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                                .addComponent(FilterDataHigh, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(FilterIntsHigh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                            .addComponent(FilterIntsLow, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(numLayers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FilterInts)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FilterIntsLow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(FilterIntsHigh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FilterData)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FilterDataLow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FilterDataHigh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 298, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void numLayersStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_numLayersStateChanged

        int s = (Integer) numLayers.getValue();
        this.clearSlice();
        this.drawSlice(s);


    }//GEN-LAST:event_numLayersStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        numLayersStateChanged(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void sliceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sliceMouseClicked
        /*
         clickpointpos = new Vector(this.toRealCoord(evt.getPoint()));

         ints.add(new EulerIntegrator(clickpointpos, new RoundHardLimitKernel(), Afstudeerproject.grid));
         ints.add(ints.get(ints.size() - 1).mirror());

         for (Integrator e : ints) {
         if (e.alive) {
         for (int i = 0; i < 500; i++) {

         e.step();
         if (!e.alive) {
         System.out.println("Done after " + i + " steps");
         Afstudeerproject.grid.compensate(e);
         Afstudeerproject.grid.cluster();
         break;

         }

         }
         e.alive = false;
         System.out.println("Done after " + 500 + " steps");
         Afstudeerproject.grid.compensate(e);
         Afstudeerproject.grid.cluster();
         }

         }

         System.out.println("added clickpoint at " + clickpointpos);
         numLayersStateChanged(null);

         */
    }//GEN-LAST:event_sliceMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        OutputSettings.filterData = this.FilterData.isSelected();
        OutputSettings.filterDataLow = ((Integer) this.FilterDataLow.getValue());
        OutputSettings.filterDataHigh = ((Integer) this.FilterDataHigh.getValue());
        
        OutputSettings.filterInts = this.FilterInts.isSelected();
        OutputSettings.filterIntsLow = ((Integer) this.FilterIntsLow.getValue());
        OutputSettings.filterIntsHigh = ((Integer) this.FilterIntsHigh.getValue());
        
        Afstudeerproject.writeVTK();
       
    }//GEN-LAST:event_jButton2ActionPerformed

    private void FilterDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FilterDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FilterDataActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox FilterData;
    private javax.swing.JSpinner FilterDataHigh;
    private javax.swing.JSpinner FilterDataLow;
    private javax.swing.JCheckBox FilterInts;
    private javax.swing.JSpinner FilterIntsHigh;
    private javax.swing.JSpinner FilterIntsLow;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSpinner numLayers;
    public javax.swing.JPanel slice;
    // End of variables declaration//GEN-END:variables
}
