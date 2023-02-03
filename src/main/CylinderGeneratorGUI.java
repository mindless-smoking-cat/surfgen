
package main;

import generators.*;
import tools.MapFactory;
import ulben.NumericTextField;
import ulben.UlbenUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;


public class CylinderGeneratorGUI extends javax.swing.JFrame {

    public final static int SHINGLES_TYPE_CYL = 0;
    public final static int SHINGLES_TYPE_RAMP_SQTOP = 1;
    public final static int SHINGLES_TYPE_RAMP_TRITOP = 2;

    private Config configuration;

    private int outerRadius = 768;
    private int innerRadius = 512;
    private int slices = 24;
    private int height = 256;
    private double offsetAngle = 0.0;

    private Color shaded = new Color(200, 200, 200);
    private Color normal = this.getBackground();

    private String outerRadiusTitle = "Outer Radius";
    private String outerRadiusDescription = "The radius of the outside of the cylinder. This value is ignored if the shingles are being generated.";

    private String innerRadiusTitle = "Inner Radius";
    private String innerRadiusDescription = "The radius of the inside of the cylinder. This may be zero to generate a disc, but the Disc Generator is recommended for that. If shingles are being generated, this specifies the radius of the circle made by the shingles. This parameter is ignored if a cap is being generated.";

    private String slicesTitle = "Slices";
    private String slicesDescription = "The number of individual brushes to be generated.";

    private String heightTitle = "Height";
    private String heightDescription = "How tall the cylinder is.";

    private String adjustForOverlappingCylinderCutsTitle = "Adjust For Overlapping Cylinder Cuts";
    private String adjustForOverlappingCylinderCutsDescription = "If cuts will be made at the planes x=0 and y=0 to cut a cylinder made of overlapping brushes into a perfect quarter, set this flag to ensure that the resulting cut brushes lie on integer coordinates.";

    private String offsetAngleTitle = "Offset Angle";
    private String offsetAngleDescription = "The angle, in degrees, by which to rotate the cylinder counter-clockwise. This only applies if normal brushes or overlapping brushes are being generated, not shingles or cap.";

    private String opNormalTitle = "Generate normal non-overlapping brushes";
    private String opNormalDescription = "Generates ordinary brushes. To make a cylinder wall to fling you around at high speeds, you will either have to add shingles to the cylinder or generate overlapping brushes in order to prevent \"bouncing\". Another purpose for normal brushes is to define the visual component of a structure, for example by using surfaceparm nosolid.";

    private String opOverlappingTitle = "Generate overlapping brushes";
    private String opOverlappingDescription = "Overlapping brushes are useful for making cylinders such that the player doesn't \"bounce\" off the surface when flying against the wall of the inside of the cylinder at high speeds. However, it is not advised to texture the overlapping brush faces with drawn textures. Instead, apply caulk to all of the overlapping brushes, then superimpose a set of normal non-overlapping brushes that have surfaceparm nonsolid. \n\n ulben note: doesn't work, don't use unless you know what you're doing";

    private String opCylinderShinglesTitle = "Generate cylinder shingles";
    private String opCylinderShinglesDescription = "Can be used together with non-overlapping brushes to create smooth surfaces that allow for high speeds while clinging to the inside of a cylinder. These shingles are one-directional.";

    private String opSquareRampShinglesTitle = "Generate ramp shingles, top shingle square";
    private String opSquareRampShinglesDescription = "Shingles useful if the cylinder is turned upwards to form a vertical ramp. The top shingle is square to prevent lemmings. \n\n ulben note: useless, use triangular and g_nodamage 1";

    private String opTriRampShinglesTitle = "Generate ramp shingles, top shingle triangular";
    private String opTriRampShinglesDescription = "Shingles useful if the cylinder is turned upwards to form a vertical ramp. The top shingle is triangular, making the shingles suitable for the last quarter of a vertical loop.";

    private String opOuterCapTitle = "Generate outer cap";
    private String opOuterCapDescription = "Can be applied to the outside of a cylinder. This is especially useful when the cylinder is flipped horizontally, and when players will be falling onto the top of the cylinder. The cap prevents lemmings.";


    public CylinderGeneratorGUI(Config configuration) {
        initComponents();
        this.configuration = configuration;
    }

    private void initComponents() {

        valuesPanel = new javax.swing.JPanel();
        outerRadiusPanel = new javax.swing.JPanel();
        labOuterRadius = new javax.swing.JLabel();
        inOuterRadius = new NumericTextField(64);
        innerRadiusPanel = new javax.swing.JPanel();
        labInnerRadius = new javax.swing.JLabel();
        inInnerRadius = new NumericTextField(64);
        slicesPanel = new javax.swing.JPanel();
        labSlices = new javax.swing.JLabel();
        inSlices = new NumericTextField(1);
        heightPanel = new javax.swing.JPanel();
        labHeight = new javax.swing.JLabel();
        inHeight = new NumericTextField(64);
        adjustForOverlappingCutsPanel = new javax.swing.JPanel();
        adjustForOverlappingCylinderCuts = new javax.swing.JCheckBox();
        offsetAnglePanel = new javax.swing.JPanel();
        labOffsetAngle = new javax.swing.JLabel();
        inOffsetAngle = new javax.swing.JTextField();
        subtitlePanel = new javax.swing.JPanel();
        subtitle = new javax.swing.JLabel();
        descriptionPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        description = new javax.swing.JTextArea();
        descriptionTitle = new javax.swing.JLabel();
        optionsPanel = new javax.swing.JPanel();
        normalOptionPanel = new javax.swing.JPanel();
        opNormal = new javax.swing.JCheckBox();
        overlappingBrushOptionPanel = new javax.swing.JPanel();
        opOverlapping = new javax.swing.JCheckBox();
        cylinderShinglesOptionPanel = new javax.swing.JPanel();
        opCylinderShingles = new javax.swing.JCheckBox();
        squareShinglesOptionPanel = new javax.swing.JPanel();
        opSquareRampShingles = new javax.swing.JCheckBox();
        triShinglesOptionPanel = new javax.swing.JPanel();
        opTriRampShingles = new javax.swing.JCheckBox();
        outerCapOptionPanel = new javax.swing.JPanel();
        opOuterCap = new javax.swing.JCheckBox();
        statusPanel = new javax.swing.JPanel();
        generate = new javax.swing.JButton();
        statusScrollPane = new javax.swing.JScrollPane();
        status = new javax.swing.JTextArea();
        iconPanel = new javax.swing.JPanel();
        icon = new javax.swing.JLabel();

        setTitle("Cylinder Generator");


        outerRadiusPanel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                outerRadiusPanelFocusGained(evt);
            }
        });

        labOuterRadius.setLabelFor(inOuterRadius);
        labOuterRadius.setText("outerRadius:");

        inOuterRadius.setText("768");
        inOuterRadius.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inOuterRadiusFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inOuterRadiusFocusLost(evt);
            }
        });

        javax.swing.GroupLayout outerRadiusPanelLayout = new javax.swing.GroupLayout(outerRadiusPanel);
        outerRadiusPanel.setLayout(outerRadiusPanelLayout);
        outerRadiusPanelLayout.setHorizontalGroup(
                outerRadiusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(outerRadiusPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labOuterRadius)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inOuterRadius, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        outerRadiusPanelLayout.setVerticalGroup(
                outerRadiusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, outerRadiusPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(outerRadiusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labOuterRadius)
                                        .addComponent(inOuterRadius, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        labInnerRadius.setLabelFor(inInnerRadius);
        labInnerRadius.setText("innerRadius:");

        inInnerRadius.setText("512");
        inInnerRadius.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inInnerRadiusFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inInnerRadiusFocusLost(evt);
            }
        });

        javax.swing.GroupLayout innerRadiusPanelLayout = new javax.swing.GroupLayout(innerRadiusPanel);
        innerRadiusPanel.setLayout(innerRadiusPanelLayout);
        innerRadiusPanelLayout.setHorizontalGroup(
                innerRadiusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(innerRadiusPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labInnerRadius)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inInnerRadius, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        innerRadiusPanelLayout.setVerticalGroup(
                innerRadiusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, innerRadiusPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(innerRadiusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labInnerRadius)
                                        .addComponent(inInnerRadius, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        labSlices.setLabelFor(inSlices);
        labSlices.setText("slices:");

        inSlices.setText("24");
        inSlices.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inSlicesFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inSlicesFocusLost(evt);
            }
        });

        javax.swing.GroupLayout slicesPanelLayout = new javax.swing.GroupLayout(slicesPanel);
        slicesPanel.setLayout(slicesPanelLayout);
        slicesPanelLayout.setHorizontalGroup(
                slicesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(slicesPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labSlices)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inSlices, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        slicesPanelLayout.setVerticalGroup(
                slicesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slicesPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(slicesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labSlices)
                                        .addComponent(inSlices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        labHeight.setLabelFor(inHeight);
        labHeight.setText("height:");

        inHeight.setText("256");
        inHeight.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inHeightFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inHeightFocusLost(evt);
            }
        });

        javax.swing.GroupLayout heightPanelLayout = new javax.swing.GroupLayout(heightPanel);
        heightPanel.setLayout(heightPanelLayout);
        heightPanelLayout.setHorizontalGroup(
                heightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(heightPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labHeight)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        heightPanelLayout.setVerticalGroup(
                heightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(heightPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(heightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labHeight)
                                        .addComponent(inHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        adjustForOverlappingCylinderCuts.setText("adjustForOverlappingCylinderCuts");
        adjustForOverlappingCylinderCuts.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                adjustForOverlappingCylinderCutsFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                adjustForOverlappingCylinderCutsFocusLost(evt);
            }
        });

        javax.swing.GroupLayout adjustForOverlappingCutsPanelLayout = new javax.swing.GroupLayout(adjustForOverlappingCutsPanel);
        adjustForOverlappingCutsPanel.setLayout(adjustForOverlappingCutsPanelLayout);
        adjustForOverlappingCutsPanelLayout.setHorizontalGroup(
                adjustForOverlappingCutsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(adjustForOverlappingCutsPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(adjustForOverlappingCylinderCuts)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        adjustForOverlappingCutsPanelLayout.setVerticalGroup(
                adjustForOverlappingCutsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adjustForOverlappingCutsPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(adjustForOverlappingCylinderCuts)
                                .addContainerGap())
        );

        labOffsetAngle.setLabelFor(inOffsetAngle);
        labOffsetAngle.setText("offsetAngle:");

        inOffsetAngle.setText("0.0");
        inOffsetAngle.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inOffsetAngleFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inOffsetAngleFocusLost(evt);
            }
        });

        javax.swing.GroupLayout offsetAnglePanelLayout = new javax.swing.GroupLayout(offsetAnglePanel);
        offsetAnglePanel.setLayout(offsetAnglePanelLayout);
        offsetAnglePanelLayout.setHorizontalGroup(
                offsetAnglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(offsetAnglePanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labOffsetAngle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inOffsetAngle, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        offsetAnglePanelLayout.setVerticalGroup(
                offsetAnglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, offsetAnglePanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(offsetAnglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labOffsetAngle)
                                        .addComponent(inOffsetAngle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        javax.swing.GroupLayout valuesPanelLayout = new javax.swing.GroupLayout(valuesPanel);
        valuesPanel.setLayout(valuesPanelLayout);
        valuesPanelLayout.setHorizontalGroup(
                valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(valuesPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(outerRadiusPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(innerRadiusPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(slicesPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(heightPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(adjustForOverlappingCutsPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(offsetAnglePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        valuesPanelLayout.setVerticalGroup(
                valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(valuesPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(outerRadiusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(innerRadiusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slicesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(heightPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(adjustForOverlappingCutsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(offsetAnglePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        subtitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        subtitle.setText("Generates a quarter cylinder as a .map file to be opened in radiant");

        javax.swing.GroupLayout subtitlePanelLayout = new javax.swing.GroupLayout(subtitlePanel);
        subtitlePanel.setLayout(subtitlePanelLayout);
        subtitlePanelLayout.setHorizontalGroup(
                subtitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(subtitlePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(subtitle, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE))
        );
        subtitlePanelLayout.setVerticalGroup(
                subtitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(subtitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        description.setEditable(false);
        description.setColumns(20);
        description.setLineWrap(true);
        description.setRows(5);
        description.setWrapStyleWord(true);
        description.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        description.setFocusable(false);
        description.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jScrollPane1.setViewportView(description);

        descriptionTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout descriptionPanelLayout = new javax.swing.GroupLayout(descriptionPanel);
        descriptionPanel.setLayout(descriptionPanelLayout);
        descriptionPanelLayout.setHorizontalGroup(
                descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, descriptionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(descriptionTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1))
                                .addContainerGap())
        );
        descriptionPanelLayout.setVerticalGroup(
                descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, descriptionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(descriptionTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        normalOptionPanel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                normalOptionPanelFocusLost(evt);
            }
        });

        opNormal.setSelected(true);
        opNormal.setText("generate normal non-overlapping brushes");
        opNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opNormalActionPerformed(evt);
            }
        });
        opNormal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                opNormalFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                opNormalFocusLost(evt);
            }
        });

        opNormal.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (opNormal.isSelected()) {
                    opOuterCap.setSelected(false);
                }
            }
        });

        javax.swing.GroupLayout normalOptionPanelLayout = new javax.swing.GroupLayout(normalOptionPanel);
        normalOptionPanel.setLayout(normalOptionPanelLayout);
        normalOptionPanelLayout.setHorizontalGroup(
                normalOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(normalOptionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(opNormal)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        normalOptionPanelLayout.setVerticalGroup(
                normalOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(opNormal, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
        );

        opOverlapping.setText("generate overlapping brushes");
        opOverlapping.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opOverlappingActionPerformed(evt);
            }
        });
        opOverlapping.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {

                    opCylinderShingles.setEnabled(false);
                    opSquareRampShingles.setEnabled(false);
                    opTriRampShingles.setEnabled(false);
                    opCylinderShingles.setSelected(false);
                    opSquareRampShingles.setSelected(false);
                    opTriRampShingles.setSelected(false);
                    opOuterCap.setSelected(false);
                } else {
                    if (!opOuterCap.isSelected()) {
                        opCylinderShingles.setEnabled(true);
                        opSquareRampShingles.setEnabled(true);
                        opTriRampShingles.setEnabled(true);
                    }
                }
            }
        });

        opOverlapping.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                opOverlappingFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                opOverlappingFocusLost(evt);
            }
        });

        javax.swing.GroupLayout overlappingBrushOptionPanelLayout = new javax.swing.GroupLayout(overlappingBrushOptionPanel);
        overlappingBrushOptionPanel.setLayout(overlappingBrushOptionPanelLayout);
        overlappingBrushOptionPanelLayout.setHorizontalGroup(
                overlappingBrushOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(overlappingBrushOptionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(opOverlapping)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        overlappingBrushOptionPanelLayout.setVerticalGroup(
                overlappingBrushOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(opOverlapping)
        );


        opCylinderShingles.setSelected(true);
        opCylinderShingles.setText("generate cylinder shingles");
        opCylinderShingles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opCylinderShinglesActionPerformed(evt);
            }
        });
        opCylinderShingles.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                opCylinderShinglesFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                opCylinderShinglesFocusLost(evt);
            }
        });


        opCylinderShingles.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (opCylinderShingles.isSelected()) {
                    opTriRampShingles.setSelected(false);
                    opSquareRampShingles.setSelected(false);
                }
            }
        });

        javax.swing.GroupLayout cylinderShinglesOptionPanelLayout = new javax.swing.GroupLayout(cylinderShinglesOptionPanel);
        cylinderShinglesOptionPanel.setLayout(cylinderShinglesOptionPanelLayout);
        cylinderShinglesOptionPanelLayout.setHorizontalGroup(
                cylinderShinglesOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(cylinderShinglesOptionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(opCylinderShingles)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cylinderShinglesOptionPanelLayout.setVerticalGroup(
                cylinderShinglesOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(opCylinderShingles)
        );


        opSquareRampShingles.setText("generate ramp shingles, top shingle square");
        opSquareRampShingles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opSquareRampShinglesActionPerformed(evt);
            }
        });
        opSquareRampShingles.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                opSquareRampShinglesFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                opSquareRampShinglesFocusLost(evt);
            }
        });

        opSquareRampShingles.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (opSquareRampShingles.isSelected()) {
                    opCylinderShingles.setSelected(false);
                    opTriRampShingles.setSelected(false);
                }
            }
        });

        javax.swing.GroupLayout squareShinglesOptionPanelLayout = new javax.swing.GroupLayout(squareShinglesOptionPanel);
        squareShinglesOptionPanel.setLayout(squareShinglesOptionPanelLayout);
        squareShinglesOptionPanelLayout.setHorizontalGroup(
                squareShinglesOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(squareShinglesOptionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(opSquareRampShingles)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        squareShinglesOptionPanelLayout.setVerticalGroup(
                squareShinglesOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(squareShinglesOptionPanelLayout.createSequentialGroup()
                                .addComponent(opSquareRampShingles)
                                .addGap(0, 4, Short.MAX_VALUE))
        );


        opTriRampShingles.setText("generate ramp shingles, top shingle triangular");
        opTriRampShingles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opTriRampShinglesActionPerformed(evt);
            }
        });

        opTriRampShingles.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                opTriRampShinglesFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                opTriRampShinglesFocusLost(evt);
            }
        });


        opTriRampShingles.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (opTriRampShingles.isSelected()) {
                    opSquareRampShingles.setSelected(false);
                    opCylinderShingles.setSelected(false);
                }
            }
        });

        javax.swing.GroupLayout triShinglesOptionPanelLayout = new javax.swing.GroupLayout(triShinglesOptionPanel);
        triShinglesOptionPanel.setLayout(triShinglesOptionPanelLayout);
        triShinglesOptionPanelLayout.setHorizontalGroup(
                triShinglesOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(triShinglesOptionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(opTriRampShingles)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        triShinglesOptionPanelLayout.setVerticalGroup(
                triShinglesOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(opTriRampShingles)
        );


        opOuterCap.setText("generate outer cap");
        opOuterCap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opOuterCapActionPerformed(evt);
            }
        });
        opOuterCap.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                opOuterCapFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                opOuterCapFocusLost(evt);
            }
        });


        opOuterCap.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (opOuterCap.isSelected()) {
                    opCylinderShingles.setEnabled(false);
                    opSquareRampShingles.setEnabled(false);
                    opTriRampShingles.setEnabled(false);
                    opCylinderShingles.setSelected(false);
                    opSquareRampShingles.setSelected(false);
                    opTriRampShingles.setSelected(false);
                    opNormal.setSelected(false);
                    opOverlapping.setSelected(false);
                } else {
                    if (!opOverlapping.isSelected()) {
                        opCylinderShingles.setEnabled(true);
                        opSquareRampShingles.setEnabled(true);
                        opTriRampShingles.setEnabled(true);
                    }
                }
            }
        });

        javax.swing.GroupLayout outerCapOptionPanelLayout = new javax.swing.GroupLayout(outerCapOptionPanel);
        outerCapOptionPanel.setLayout(outerCapOptionPanelLayout);
        outerCapOptionPanelLayout.setHorizontalGroup(
                outerCapOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(outerCapOptionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(opOuterCap)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        outerCapOptionPanelLayout.setVerticalGroup(
                outerCapOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(opOuterCap, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout optionsPanelLayout = new javax.swing.GroupLayout(optionsPanel);
        optionsPanel.setLayout(optionsPanelLayout);
        optionsPanelLayout.setHorizontalGroup(
                optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(optionsPanelLayout.createSequentialGroup()
                                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(overlappingBrushOptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(normalOptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cylinderShinglesOptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(triShinglesOptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(squareShinglesOptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(outerCapOptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 12, Short.MAX_VALUE))
        );
        optionsPanelLayout.setVerticalGroup(
                optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(optionsPanelLayout.createSequentialGroup()
                                .addComponent(normalOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(overlappingBrushOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(outerCapOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(25, 25, 25)
                                .addComponent(cylinderShinglesOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(squareShinglesOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(triShinglesOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGap(147, 147, 147))
        );

        generate.setText("Generate");
        generate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    generateActionPerformed(evt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        status.setEditable(false);
        status.setColumns(20);
        status.setRows(5);
        status.setFocusable(false);
        statusScrollPane.setViewportView(status);

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
                statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(statusPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(statusScrollPane)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(generate)
                                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
                statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(statusPanelLayout.createSequentialGroup()
                                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(statusPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(statusScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(statusPanelLayout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addComponent(generate)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        icon.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/main/cylinder.png")));
        icon.setFocusable(false);

        javax.swing.GroupLayout iconPanelLayout = new javax.swing.GroupLayout(iconPanel);
        iconPanel.setLayout(iconPanelLayout);
        iconPanelLayout.setHorizontalGroup(
                iconPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, iconPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(icon, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        iconPanelLayout.setVerticalGroup(
                iconPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(icon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());

        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(subtitlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                        .addComponent(valuesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(descriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                        .addComponent(optionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(iconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addComponent(statusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(subtitlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(valuesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(7, 7, 7)
                                                .addComponent(optionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(iconPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(statusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pack();

    }

    private void inOuterRadiusFocusGained(java.awt.event.FocusEvent evt) {

        outerRadiusPanel.setBackground(shaded);
        descriptionTitle.setText(outerRadiusTitle);
        description.setText(outerRadiusDescription);
    }

    private void inOuterRadiusFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void outerRadiusPanelFocusGained(java.awt.event.FocusEvent evt) {

    }

    private void inInnerRadiusFocusGained(java.awt.event.FocusEvent evt) {
        innerRadiusPanel.setBackground(shaded);
        descriptionTitle.setText(innerRadiusTitle);
        description.setText(innerRadiusDescription);
    }

    private void inInnerRadiusFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void inSlicesFocusGained(java.awt.event.FocusEvent evt) {

        slicesPanel.setBackground(shaded);
        descriptionTitle.setText(slicesTitle);
        description.setText(slicesDescription);
    }

    private void inSlicesFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void inHeightFocusGained(java.awt.event.FocusEvent evt) {

        heightPanel.setBackground(shaded);
        descriptionTitle.setText(heightTitle);
        description.setText(heightDescription);
    }

    private void inHeightFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void adjustForOverlappingCylinderCutsFocusGained(java.awt.event.FocusEvent evt) {

        adjustForOverlappingCutsPanel.setBackground(shaded);
        descriptionTitle.setText(adjustForOverlappingCylinderCutsTitle);
        description.setText(adjustForOverlappingCylinderCutsDescription);
    }

    private void adjustForOverlappingCylinderCutsFocusLost(java.awt.event.FocusEvent evt) {

        adjustForOverlappingCutsPanel.setBackground(normal);
    }

    private void inOffsetAngleFocusGained(java.awt.event.FocusEvent evt) {

        offsetAnglePanel.setBackground(shaded);
        descriptionTitle.setText(offsetAngleTitle);
        description.setText(offsetAngleDescription);
    }

    private void inOffsetAngleFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void opNormalFocusGained(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
        normalOptionPanel.setBackground(shaded);
        descriptionTitle.setText(opNormalTitle);
        description.setText(opNormalDescription);
    }

    private void normalOptionPanelFocusLost(java.awt.event.FocusEvent evt) {

    }

    private void opNormalFocusLost(java.awt.event.FocusEvent evt) {

        normalOptionPanel.setBackground(normal);
        checkGenerateOK();
    }

    private void opOverlappingFocusGained(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
        overlappingBrushOptionPanel.setBackground(shaded);
        descriptionTitle.setText(opOverlappingTitle);
        description.setText(opOverlappingDescription);
    }

    private void opOverlappingFocusLost(java.awt.event.FocusEvent evt) {

        overlappingBrushOptionPanel.setBackground(normal);
        checkGenerateOK();
    }

    private void opCylinderShinglesFocusGained(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
        cylinderShinglesOptionPanel.setBackground(shaded);
        descriptionTitle.setText(opCylinderShinglesTitle);
        description.setText(opCylinderShinglesDescription);
    }

    private void opCylinderShinglesFocusLost(java.awt.event.FocusEvent evt) {

        cylinderShinglesOptionPanel.setBackground(normal);
        checkGenerateOK();
    }

    private void opSquareRampShinglesFocusGained(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
        squareShinglesOptionPanel.setBackground(shaded);
        descriptionTitle.setText(opSquareRampShinglesTitle);
        description.setText(opSquareRampShinglesDescription);
    }

    private void opSquareRampShinglesFocusLost(java.awt.event.FocusEvent evt) {

        squareShinglesOptionPanel.setBackground(normal);
        checkGenerateOK();
    }

    private void opTriRampShinglesFocusGained(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
        triShinglesOptionPanel.setBackground(shaded);
        descriptionTitle.setText(opTriRampShinglesTitle);
        description.setText(opTriRampShinglesDescription);
    }

    private void opTriRampShinglesFocusLost(java.awt.event.FocusEvent evt) {

        triShinglesOptionPanel.setBackground(normal);
        checkGenerateOK();
    }

    private void opOuterCapFocusGained(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
        outerCapOptionPanel.setBackground(shaded);
        descriptionTitle.setText(opOuterCapTitle);
        description.setText(opOuterCapDescription);
    }

    private void opOuterCapFocusLost(java.awt.event.FocusEvent evt) {

        outerCapOptionPanel.setBackground(normal);
        checkGenerateOK();
    }

    private void opNormalActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void opOverlappingActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void opCylinderShinglesActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void opSquareRampShinglesActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void opTriRampShinglesActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void opOuterCapActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void generateActionPerformed(java.awt.event.ActionEvent evt) throws IOException {

        generate.setEnabled(false);

        if (opNormal.isSelected() && opOverlapping.isSelected()) {
            status.setText("Generating cylinder...");
            String[] args = {inOuterRadius.getText(), inInnerRadius.getText(), inSlices.getText(), inHeight.getText(), Boolean.toString(!opOverlapping.isSelected()), Boolean.toString(adjustForOverlappingCylinderCuts.isSelected()), inOffsetAngle.getText()};
            String[] args2 = {inOuterRadius.getText(), inInnerRadius.getText(), inSlices.getText(), inHeight.getText(), Boolean.toString(opOverlapping.isSelected()), Boolean.toString(adjustForOverlappingCylinderCuts.isSelected()), inOffsetAngle.getText()};
            CylinderDoubleGen2.main(args, args2);
        } else if ((opNormal.isSelected() || opOverlapping.isSelected()) && !(opCylinderShingles.isSelected() || opSquareRampShingles.isSelected() || opTriRampShingles.isSelected())) {
            status.setText("Generating cylinder...");
            String[] args = {inOuterRadius.getText(), inInnerRadius.getText(), inSlices.getText(), inHeight.getText(), Boolean.toString(opOverlapping.isSelected()), Boolean.toString(adjustForOverlappingCylinderCuts.isSelected()), inOffsetAngle.getText()};
            CylinderGenerator.main(args);
        } else if (!(opNormal.isSelected() || opOverlapping.isSelected()) && (opCylinderShingles.isSelected() || opSquareRampShingles.isSelected() || opTriRampShingles.isSelected())) {
            int shingles = 0;

            if (opCylinderShingles.isSelected())
                shingles = SHINGLES_TYPE_CYL;
            if (opSquareRampShingles.isSelected())
                shingles = SHINGLES_TYPE_RAMP_SQTOP;
            if (opTriRampShingles.isSelected())
                shingles = SHINGLES_TYPE_RAMP_TRITOP;

            String[] args = {inInnerRadius.getText(), inSlices.getText(), inHeight.getText(), Boolean.toString(opOverlapping.isSelected()), Integer.toString(shingles)};
            status.setText("Generating shingles...");
            CylinderShinglesGenerator.main(args);
        } else if ((opNormal.isSelected() || opOverlapping.isSelected()) && (opCylinderShingles.isSelected() || opSquareRampShingles.isSelected() || opTriRampShingles.isSelected())) {

            status.setText("Generating cylinder and shingles...");
            String[] args = {inOuterRadius.getText(), inInnerRadius.getText(), inSlices.getText(), inHeight.getText(), Boolean.toString(opOverlapping.isSelected()), Boolean.toString(adjustForOverlappingCylinderCuts.isSelected()), inOffsetAngle.getText()};

            int shingles = 0;

            if (opCylinderShingles.isSelected())
                shingles = SHINGLES_TYPE_CYL;
            if (opSquareRampShingles.isSelected())
                shingles = SHINGLES_TYPE_RAMP_SQTOP;
            if (opTriRampShingles.isSelected())
                shingles = SHINGLES_TYPE_RAMP_TRITOP;
            String[] args2 = {inInnerRadius.getText(), inSlices.getText(), inHeight.getText(), Boolean.toString(opOverlapping.isSelected()), Integer.toString(shingles)};

            CylinderDoubleGen.main(args, args2);
        } else if (opOuterCap.isSelected()) {
            status.setText("Generating cap...");
            String[] args = {inOuterRadius.getText(), inSlices.getText(), inHeight.getText(), Boolean.toString(opOverlapping.isSelected())};
            CylinderCapGenerator.main(args);
        } else {
            status.setText("Nothing to generate");
            generate.setEnabled(true);
            return;
        }


        status.setText("Generated - select save file name and location");
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Quake Map File (.map)", "map");
        chooser.setFileFilter(filter);

        File saveFile = UlbenUtils.getSaveFile(this, "CYL");

        String filename;

        if (saveFile != null) {
            filename = saveFile.getAbsolutePath();

            if (!(filename.endsWith(".map")))
                filename += ".map";


            status.setText("Saving...");
            MapFactory.saveText(filename);
            statusPanel.setBackground(Color.green);
            status.setText("Save successful\n" + filename);
            configuration.setWorkingDirectory(saveFile.getParentFile());
            if (UlbenUtils.readSetting("OPEN") != null)
                Desktop.getDesktop().open(new File(filename));
        } else
            status.setText("Save Cancelled");

    }


    private void checkGenerateOK() {
        boolean generateOK = true;
        statusPanel.setBackground(normal);
        status.setText("");
        String statusText = "";


        try {
            int temp = Integer.parseInt(inHeight.getText());

            if (temp <= 1)
                throw new Exception("Min height is 1");

            height = temp;
            heightPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number\n";
            else
                statusText += ex.getMessage() + "\n";
            heightPanel.setBackground(Color.red);
        }


        try {
            int temp = Integer.parseInt(inSlices.getText());

            if (temp < 1)
                throw new Exception("Min slices is 1");

            slices = temp;
            slicesPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number\n";
            else
                statusText += ex.getMessage() + "\n";
            slicesPanel.setBackground(Color.red);

        }


        try {
            double temp = Double.parseDouble(inOffsetAngle.getText());

            if (temp < 0)
                throw new Exception("Please enter a positive number");

            offsetAngle = temp;
            offsetAnglePanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number\n";
            else
                statusText += ex.getMessage() + "\n";
            offsetAnglePanel.setBackground(Color.red);
        }

        if ((opTriRampShingles.isSelected() || opSquareRampShingles.isSelected() || opCylinderShingles.isSelected()) && !opNormal.isSelected()) {
            inOuterRadius.setEnabled(false);
            labOuterRadius.setEnabled(false);
            outerRadiusPanel.setBackground(normal);
        } else {

            inOuterRadius.setEnabled(true);
            labOuterRadius.setEnabled(true);

            try {
                int temp = Integer.parseInt(inOuterRadius.getText());

                if (temp <= 0)
                    throw new Exception("outerRadius must be positive");

                if (!(opOuterCap.isSelected())) {
                    if (temp <= innerRadius)
                        throw new Exception("outerRadius must be greater than innerRadius");
                }

                outerRadius = temp;
                outerRadiusPanel.setBackground(normal);
            } catch (Exception ex) {
                generateOK = false;
                if (ex instanceof NumberFormatException)
                    statusText += "Please enter a number\n";
                else
                    statusText += ex.getMessage() + "\n";
                outerRadiusPanel.setBackground(Color.red);
            }
        }

        if (opOuterCap.isSelected()) {
            inInnerRadius.setEnabled(false);
            labInnerRadius.setEnabled(false);
            innerRadiusPanel.setBackground(normal);
        } else {
            inInnerRadius.setEnabled(true);
            labInnerRadius.setEnabled(true);

            try {
                int temp = Integer.parseInt(inInnerRadius.getText());

                if (temp <= 0)
                    throw new Exception("innerRadius cannot be negative");


                if (opNormal.isSelected() || opOverlapping.isSelected()) {
                    if (temp >= outerRadius)
                        throw new Exception("innerRadius must be less than outerRadius");
                }

                innerRadius = temp;
                innerRadiusPanel.setBackground(normal);
            } catch (Exception ex) {
                generateOK = false;
                statusPanel.setBackground(Color.yellow);
                if (ex instanceof NumberFormatException)
                    statusText += "Please enter a number\n";
                else
                    statusText += ex.getMessage() + "\n";
                innerRadiusPanel.setBackground(Color.red);
            }
        }

        if (generateOK)
            generate.setEnabled(true);
        else {
            generate.setEnabled(false);
            statusPanel.setBackground(Color.yellow);
            status.setText(statusText);
        }
    }

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CylinderGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CylinderGeneratorGUI(new Config()).setVisible(true);
            }
        });
    }

    private javax.swing.JPanel adjustForOverlappingCutsPanel;
    private javax.swing.JCheckBox adjustForOverlappingCylinderCuts;
    private javax.swing.JPanel cylinderShinglesOptionPanel;
    private javax.swing.JTextArea description;
    private javax.swing.JPanel descriptionPanel;
    private javax.swing.JLabel descriptionTitle;
    private javax.swing.JButton generate;
    private javax.swing.JPanel heightPanel;
    private javax.swing.JLabel icon;
    private javax.swing.JPanel iconPanel;
    private javax.swing.JTextField inHeight;
    private NumericTextField inInnerRadius;
    private javax.swing.JTextField inOffsetAngle;
    private NumericTextField inOuterRadius;
    private NumericTextField inSlices;
    private javax.swing.JPanel innerRadiusPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labHeight;
    private javax.swing.JLabel labInnerRadius;
    private javax.swing.JLabel labOffsetAngle;
    private javax.swing.JLabel labOuterRadius;
    private javax.swing.JLabel labSlices;
    private javax.swing.JPanel normalOptionPanel;
    private javax.swing.JPanel offsetAnglePanel;
    private javax.swing.JCheckBox opCylinderShingles;
    private javax.swing.JCheckBox opNormal;
    private javax.swing.JCheckBox opOuterCap;
    private javax.swing.JCheckBox opOverlapping;
    private javax.swing.JCheckBox opSquareRampShingles;
    private javax.swing.JCheckBox opTriRampShingles;
    private javax.swing.JPanel optionsPanel;
    private javax.swing.JPanel outerCapOptionPanel;
    private javax.swing.JPanel outerRadiusPanel;
    private javax.swing.JPanel overlappingBrushOptionPanel;
    private javax.swing.JPanel slicesPanel;
    private javax.swing.JPanel squareShinglesOptionPanel;
    private javax.swing.JTextArea status;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JScrollPane statusScrollPane;
    private javax.swing.JLabel subtitle;
    private javax.swing.JPanel subtitlePanel;
    private javax.swing.JPanel triShinglesOptionPanel;
    private javax.swing.JPanel valuesPanel;

}
