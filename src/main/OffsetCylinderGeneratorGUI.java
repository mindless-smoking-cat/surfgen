
package main;

import generators.BowlDoubleGen;
import generators.BowlGenerator;
import generators.OffsetCylDoubleGen;
import generators.OffsetCylinderGenerator;
import tools.MapFactory;
import ulben.NumericTextField;
import ulben.UlbenUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;


public class OffsetCylinderGeneratorGUI extends javax.swing.JFrame {

    public final static int SHINGLES_TYPE_CYL = 0;
    public final static int SHINGLES_TYPE_RAMP_SQTOP = 1;
    public final static int SHINGLES_TYPE_RAMP_TRITOP = 2;

    private Config configuration;

    private int outerRadius = 768;
    private int innerRadius = 512;

    private Color shaded = new Color(200, 200, 200);
    private Color normal = this.getBackground();

    private String outerRadiusTitle = "Outer Radius";
    private String outerRadiusDescription = "The radius of the outside of the cylinder.";

    private String innerRadiusTitle = "Inner Radius";
    private String innerRadiusDescription = "The radius of the inside of the cylinder. This may be zero to generate a disc. ";

    private String slicesTitle = "Slices";
    private String slicesDescription = "The number of individual brushes to be generated.";

    private String heightTitle = "Height";
    private String heightDescription = "How tall the cylinder is.";

    private String adjustSeamForJoiningTitle = "Adjust Seam For Joining";
    private String adjustSeamForJoiningDescription = "This affects the shape of the seam. Typically, this brush sticks out past the midline of the cylinder due to the offset. If this flag is checked, the brush is cut short by the midline (the plane x = 0), and its end vertices are moved down to lie exactly outerRadius and innerRadius units directly above the circular center of the cylinder. ";

    private String generateOnlySeamTitle = "Generate Only Seam";
    private String generateOnlySeamDescription = "Generates only the brush at the very top of the quarter cylinder. This is the brush that extends past the plane x = 0 (the midway divider).";

    private String opNormalTitle = "Generate normal non-overlapping brushes";
    private String opNormalDescription = "Generates ordinary brushes. To make a cylinder wall to fling you around at high speeds, you will either have to add shingles to the cylinder or generate overlapping brushes in order to prevent \"bouncing\". Another purpose for normal brushes is to define the visual component of a structure, for example by using surfaceparm nosolid.";

    private String opOverlappingTitle = "Generate overlapping brushes";
    private String opOverlappingDescription = "Overlapping brushes are useful for making cylinders such that the player doesn't \"bounce\" off the surface when flying against the wall of the inside of the cylinder at high speeds. However, it is not advised to texture the overlapping brush faces with drawn textures. Instead, apply caulk to all of the overlapping brushes, then superimpose a set of normal non-overlapping brushes that have surfaceparm nonsolid.";

    private String opCylinderShinglesTitle = "Generate cylinder shingles";
    private String opCylinderShinglesDescription = "Can be used together with non-overlapping brushes to create smooth surfaces that allow for high speeds while clinging to the inside of a cylinder. These shingles are one-directional.";

    private String opSquareRampShinglesTitle = "Generate ramp shingles, top shingle square";
    private String opSquareRampShinglesDescription = "Shingles useful if the cylinder is turned upwards to form a vertical ramp. The top shingle is square to prevent lemmings.";

    private String opTriRampShinglesTitle = "Generate ramp shingles, top shingle triangular";
    private String opTriRampShinglesDescription = "Shingles useful if the cylinder is turned upwards to form a vertical ramp. The top shingle is triangular, making the shingles suitable for the last quarter of a vertical loop.";

    private String opOuterCapTitle = "Generate outer cap";
    private String opOuterCapDescription = "Can be applied to the outside of a cylinder. This is especially useful when the cylinder is flipped horizontally, and when players will be falling onto the top of the cylinder. The cap prevents lemmings.";


    public OffsetCylinderGeneratorGUI(Config configuration) {
        initComponents();
        this.configuration = configuration;
    }


    @SuppressWarnings("unchecked")

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
        generateOnlySeamPanel = new javax.swing.JPanel();
        generateOnlySeam = new javax.swing.JCheckBox();
        adjustSeamForJoiningPanel = new javax.swing.JPanel();
        adjustSeamForJoining = new javax.swing.JCheckBox();
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
        statusPanel = new javax.swing.JPanel();
        generate = new javax.swing.JButton();
        statusScrollPane = new javax.swing.JScrollPane();
        status = new javax.swing.JTextArea();
        iconPanel = new javax.swing.JPanel();
        icon = new javax.swing.JLabel();

        setTitle("Offset Cylinder Generator");
        setLocationByPlatform(true);

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

        generateOnlySeam.setText("generateOnlySeam");
        generateOnlySeam.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        generateOnlySeam.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        generateOnlySeam.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                generateOnlySeamFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                generateOnlySeamFocusLost(evt);
            }
        });

        javax.swing.GroupLayout generateOnlySeamPanelLayout = new javax.swing.GroupLayout(generateOnlySeamPanel);
        generateOnlySeamPanel.setLayout(generateOnlySeamPanelLayout);
        generateOnlySeamPanelLayout.setHorizontalGroup(
                generateOnlySeamPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(generateOnlySeamPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(generateOnlySeam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        generateOnlySeamPanelLayout.setVerticalGroup(
                generateOnlySeamPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, generateOnlySeamPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(generateOnlySeam)
                                .addContainerGap())
        );

        adjustSeamForJoining.setText("adjustSeamForJoining");
        adjustSeamForJoining.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        adjustSeamForJoining.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        adjustSeamForJoining.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                adjustSeamForJoiningFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                adjustSeamForJoiningFocusLost(evt);
            }
        });

        javax.swing.GroupLayout adjustSeamForJoiningPanelLayout = new javax.swing.GroupLayout(adjustSeamForJoiningPanel);
        adjustSeamForJoiningPanel.setLayout(adjustSeamForJoiningPanelLayout);
        adjustSeamForJoiningPanelLayout.setHorizontalGroup(
                adjustSeamForJoiningPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(adjustSeamForJoiningPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(adjustSeamForJoining, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        adjustSeamForJoiningPanelLayout.setVerticalGroup(
                adjustSeamForJoiningPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adjustSeamForJoiningPanelLayout.createSequentialGroup()
                                .addGap(0, 9, Short.MAX_VALUE)
                                .addComponent(adjustSeamForJoining))
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
                                        .addComponent(generateOnlySeamPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(adjustSeamForJoiningPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                .addComponent(generateOnlySeamPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(adjustSeamForJoiningPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        subtitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        subtitle.setText("The vertices are rotated counter-clockwise by a half of a slice.");

        javax.swing.GroupLayout subtitlePanelLayout = new javax.swing.GroupLayout(subtitlePanel);
        subtitlePanel.setLayout(subtitlePanelLayout);
        subtitlePanelLayout.setHorizontalGroup(
                subtitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(subtitlePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(subtitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
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
                                .addComponent(descriptionTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        javax.swing.GroupLayout optionsPanelLayout = new javax.swing.GroupLayout(optionsPanel);
        optionsPanel.setLayout(optionsPanelLayout);
        optionsPanelLayout.setHorizontalGroup(
                optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(optionsPanelLayout.createSequentialGroup()
                                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(overlappingBrushOptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(normalOptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 12, Short.MAX_VALUE))
        );
        optionsPanelLayout.setVerticalGroup(
                optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(optionsPanelLayout.createSequentialGroup()
                                .addComponent(normalOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(overlappingBrushOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(generate)
                                .addGap(18, 18, 18))
        );
        statusPanelLayout.setVerticalGroup(
                statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(statusPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(statusScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(statusPanelLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(generate)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/offsetCylinder.png")));
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
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(subtitlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(statusPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(optionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(iconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(valuesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(39, 39, 39)
                                                .addComponent(descriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(subtitlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(valuesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(descriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(iconPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(69, 69, 69)
                                                .addComponent(optionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(86, 86, 86)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(statusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void generateOnlySeamFocusGained(java.awt.event.FocusEvent evt) {

        generateOnlySeamPanel.setBackground(shaded);
        descriptionTitle.setText(generateOnlySeamTitle);
        description.setText(generateOnlySeamDescription);
    }

    private void generateOnlySeamFocusLost(java.awt.event.FocusEvent evt) {

        generateOnlySeamPanel.setBackground(normal);
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

    private void opNormalActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void opOverlappingActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void generateActionPerformed(java.awt.event.ActionEvent evt) throws IOException {

        generate.setEnabled(false);

        status.setText("Generating offset cylinder...");

        if (opNormal.isSelected() ^ opOverlapping.isSelected()) {
            String[] args = {inOuterRadius.getText(), inInnerRadius.getText(), inSlices.getText(), inHeight.getText(), Boolean.toString(opOverlapping.isSelected()), Boolean.toString(generateOnlySeam.isSelected()), Boolean.toString(adjustSeamForJoining.isSelected()),};
            OffsetCylinderGenerator.main(args);
        } else if (opNormal.isSelected() && opOverlapping.isSelected()) {
            String[] args = {inOuterRadius.getText(), inInnerRadius.getText(), inSlices.getText(), inHeight.getText(), Boolean.toString(opOverlapping.isSelected()), Boolean.toString(generateOnlySeam.isSelected()), Boolean.toString(adjustSeamForJoining.isSelected()),};
            String[] args2 = {inOuterRadius.getText(), inInnerRadius.getText(), inSlices.getText(), inHeight.getText(), Boolean.toString(!opOverlapping.isSelected()), Boolean.toString(generateOnlySeam.isSelected()), Boolean.toString(adjustSeamForJoining.isSelected()),};
            OffsetCylDoubleGen.main(args, args2);
        } else {
            status.setText("Nothing to generate");
            generate.setEnabled(true);
            return;
        }

        status.setText("Generated - select save file name and location");

        File saveFile = UlbenUtils.getSaveFile(this, "OFFSETCYL");

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

    private void adjustSeamForJoiningFocusGained(java.awt.event.FocusEvent evt) {

        adjustSeamForJoiningPanel.setBackground(shaded);
        descriptionTitle.setText(adjustSeamForJoiningTitle);
        description.setText(adjustSeamForJoiningDescription);
    }

    private void adjustSeamForJoiningFocusLost(java.awt.event.FocusEvent evt) {

        adjustSeamForJoiningPanel.setBackground(normal);
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
            int temp = Integer.parseInt(inOuterRadius.getText());

            if (temp <= 0)
                throw new Exception("outerRadius must be positive");


            if (temp <= innerRadius)
                throw new Exception("outerRadius must be greater than innerRadius");

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OffsetCylinderGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OffsetCylinderGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OffsetCylinderGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OffsetCylinderGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OffsetCylinderGeneratorGUI(new Config()).setVisible(true);
            }
        });
    }

    private javax.swing.JCheckBox adjustSeamForJoining;
    private javax.swing.JPanel adjustSeamForJoiningPanel;
    private javax.swing.JTextArea description;
    private javax.swing.JPanel descriptionPanel;
    private javax.swing.JLabel descriptionTitle;
    private javax.swing.JButton generate;
    private javax.swing.JCheckBox generateOnlySeam;
    private javax.swing.JPanel generateOnlySeamPanel;
    private javax.swing.JPanel heightPanel;
    private javax.swing.JLabel icon;
    private javax.swing.JPanel iconPanel;
    private javax.swing.JTextField inHeight;
    private javax.swing.JTextField inInnerRadius;
    private javax.swing.JTextField inOuterRadius;
    private javax.swing.JTextField inSlices;
    private javax.swing.JPanel innerRadiusPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labHeight;
    private javax.swing.JLabel labInnerRadius;
    private javax.swing.JLabel labOuterRadius;
    private javax.swing.JLabel labSlices;
    private javax.swing.JPanel normalOptionPanel;
    private javax.swing.JCheckBox opNormal;
    private javax.swing.JCheckBox opOverlapping;
    private javax.swing.JPanel optionsPanel;
    private javax.swing.JPanel outerRadiusPanel;
    private javax.swing.JPanel overlappingBrushOptionPanel;
    private javax.swing.JPanel slicesPanel;
    private javax.swing.JTextArea status;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JScrollPane statusScrollPane;
    private javax.swing.JLabel subtitle;
    private javax.swing.JPanel subtitlePanel;
    private javax.swing.JPanel valuesPanel;

}
