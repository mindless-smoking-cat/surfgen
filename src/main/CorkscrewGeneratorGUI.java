
package main;

import generators.CorkscrewCapGenerator;
import generators.CorkscrewGenerator;
import generators.CorkscrewShinglesGenerator;
import generators.CorkscrewTilesGenerator;
import tools.MapFactory;
import ulben.UlbenUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class CorkscrewGeneratorGUI extends javax.swing.JFrame {

    public final static int SHINGLES_TYPE_CYL = 0;
    public final static int SHINGLES_TYPE_RAMP_SQTOP = 1;
    public final static int SHINGLES_TYPE_RAMP_TRITOP = 2;

    public Config configuration;

    private int outerRadius = 768;
    private int innerRadius = 512;
    private int outerThickness = 256;
    ;
    private int innerThickness = 64;

    private Color shaded = new Color(200, 200, 200);
    private Color normal = this.getBackground();

    private String outerRadiusTitle = "Outer Radius";
    private String outerRadiusDescription = "The radius of the outside of the corkscrew. This parameter is ignored if shingles are being generated.";

    private String innerRadiusTitle = "Inner Radius";
    private String innerRadiusDescription = "The radius of the inside of the corkscrew. When shingles are being generated, this defines the radius of the shingles structure. This parameter is ignored when a cap is being generated. ";

    private String slicesTitle = "Slices";
    private String slicesDescription = "The number of slices that the outer and inner circumferences are chopped into. The total number of brushes generated is 2 * slices. If shingles are being generated then the total number of brushes is half that. A cap contains contains one brush more than shingles do.";

    private String altitudeTitle = "Altitude";
    private String altitudeDescription = "The amount of height you gain going along a quarter of the corkscrew, following either the inner or outer circumference. For smoothest results this should be a multiple of slices. If tiles are being generated, you may want to try negative values here in order to come up with the correct tile orientation.";

    private String adjustForOverlappingCylinderCutsTitle = "Adjust For Overlapping Cylinder Cuts";
    private String adjustForOverlappingCylinderCutsDescription = "If aligning [either the outer or inner circumference of] this corkscrew with a cylinder generated with Cylinder Generator and the adjustForOverlappingCylinderCuts flag is set for that cylinder, use the flag here as well so that the two would align perfectly. Also applies on Pipe Generator and adjustForOverlappingPipeCuts.";

    private String lipTitle = "Lip";
    private String lipDescription = "The difference in height between the outer circumference and the inner circumference, taken at the top surface of the corkscrew, not the bottom. If shingles are being generated, this parameter only affects the overall position (translation) of the shingles. This parameter is ignored when a cap is being generated.";

    private String outerThicknessTitle = "Outer Thickness";
    private String outerThicknessDescription = "The thickness, measured vertically, of the corkscrew at the outer circumference. This parameter is ignored if shingles or tiles are being generated.";

    private String innerThicknessTitle = "Inner Thickness";
    private String innerThicknessDescription = "The thickness, measured vertically, of the corkscrew at the inner circumference. When shingles are generated this defines the \"height\" of the shingles. This parameter is ignored when a cap is being generated, and also when tiles are being generated.";

    private String offsetAngleTitle = "Offset Angle";
    private String offsetAngleDescription = "The angle, in degrees, by which to rotate the corkscrew counter-clockwise. This only applies if normal brushes or tiles are being generated, not shingles or cap.";

    private String opNormalTitle = "Generate normal brushes";
    private String opNormalDescription = "Generates ordinary brushes. You may consider generating shingles for the inner wall of the corkscrew to prevent \"bouncing\" if the player is going to be flung around the inner wall. Also you may want to experiment with tiles and/or an outer cap.";

    private String opCylinderShinglesTitle = "Generate cylinder shingles";
    private String opCylinderShinglesDescription = "Can be applied to the inside of a corkscrew. These are one-directional when going around. Use this with the default corkscrew orientation (not rotated). ";

    private String opSquareRampShinglesTitle = "Generate ramp shingles, top shingle square";
    private String opSquareRampShinglesDescription = "Can be applied to the inside of a corkscrew. Use this when the corkscrew is rotated 90 degrees along the X or Y axis, becoming more like a ramp. The shingle at the top of the ramp is square to prevent lemmings when falling on that shingle.";

    private String opTriRampShinglesTitle = "Generate ramp shingles, top shingle triangular";
    private String opTriRampShinglesDescription = "Can be applied to the inside of a corkscrew. Use this when the corkscrew is rotated 90 degrees along the X or Y axis, becoming more like a ramp. The shingle at the top of the ramp is triangular in cross section, making the shingles suitable for the last quarter of a vertical loop.";

    private String opGenerateTilesTitle = "Generate tiles";
    private String opGenerateTilesDescription = "Can be applied to the top surface of a corkscrew. This is especially useful when the corkscrew is flipped upright and used as the wall of a loop. For normal use (when going around the corkscrew horizontally), be aware that the tiles may have sharp corners pointing upwards, which causes lemmings when falling on them.";

    private String opGenerateTilesReverseDirectionTitle = "Generate tiles, reverse direction";
    private String opGenerateTilesReverseDirectionDescription = "Exactly like above, except that the direction of the tiles is flipped.";

    private String opGenerateOuterCapTitle = "Generate outer cap";
    private String opGenerateOuterCapDescription = "Can be applied to the outside of a corkscrew. This is especially useful when the corkscrew is flipped upright to form a loop, and when players will be falling onto the top of that loop. The cap prevents lemmings";


    public CorkscrewGeneratorGUI(Config configuration) {
        initComponents();
        this.configuration = configuration;

        Dimension frameSize = getSize();
        int frameHeight = frameSize.height;
        int frameWidth = frameSize.width;
        if (frameHeight > configuration.getScreenHeight()) {
            setSize(frameWidth, configuration.getScreenHeight());
        }
    }


    @SuppressWarnings("unchecked")

    private void initComponents() {

        options = new javax.swing.ButtonGroup();
        corkscrewNote = new javax.swing.JDialog();
        noteDescription = new javax.swing.JTextArea();
        scrollPane1 = new javax.swing.JScrollPane();
        viewPanel = new javax.swing.JPanel();
        valuesPanel = new javax.swing.JPanel();
        outerRadiusPanel = new javax.swing.JPanel();
        labOuterRadius = new javax.swing.JLabel();
        inOuterRadius = new javax.swing.JTextField();
        innerRadiusPanel = new javax.swing.JPanel();
        labInnerRadius = new javax.swing.JLabel();
        inInnerRadius = new javax.swing.JTextField();
        slicesPanel = new javax.swing.JPanel();
        labSlices = new javax.swing.JLabel();
        inSlices = new javax.swing.JTextField();
        altitudePanel = new javax.swing.JPanel();
        labAltitude = new javax.swing.JLabel();
        inAltitude = new javax.swing.JTextField();
        adjustForOverlappingCutsPanel = new javax.swing.JPanel();
        adjustForOverlappingCylinderCuts = new javax.swing.JCheckBox();
        lipPanel = new javax.swing.JPanel();
        labLip = new javax.swing.JLabel();
        inLip = new javax.swing.JTextField();
        innerThicknessPanel = new javax.swing.JPanel();
        labInnerThickness = new javax.swing.JLabel();
        inInnerThickness = new javax.swing.JTextField();
        outerThicknessPanel = new javax.swing.JPanel();
        labOuterThickness = new javax.swing.JLabel();
        inOuterThickness = new javax.swing.JTextField();
        offsetAnglePanel = new javax.swing.JPanel();
        labOffsetAngle = new javax.swing.JLabel();
        inOffsetAngle = new javax.swing.JTextField();
        subtitlePanel = new javax.swing.JPanel();
        subtitle = new javax.swing.JLabel();
        descriptionPanel = new javax.swing.JPanel();
        descriptionScrollPane = new javax.swing.JScrollPane();
        description = new javax.swing.JTextArea();
        descriptionTitle = new javax.swing.JLabel();
        statusPanel = new javax.swing.JPanel();
        generate = new javax.swing.JButton();
        statusScrollPane = new javax.swing.JScrollPane();
        status = new javax.swing.JTextArea();
        iconPanel = new javax.swing.JPanel();
        icon = new javax.swing.JLabel();
        optionsPanel = new javax.swing.JPanel();
        normalOptionPanel = new javax.swing.JPanel();
        opNormal = new javax.swing.JRadioButton();
        cylinderShinglesOptionPanel = new javax.swing.JPanel();
        opCylinderShingles = new javax.swing.JRadioButton();
        squareShinglesOptionPanel = new javax.swing.JPanel();
        opSquareRampShingles = new javax.swing.JRadioButton();
        triShinglesOptionPanel = new javax.swing.JPanel();
        opTriRampShingles = new javax.swing.JRadioButton();
        generateTilesOptionPanel = new javax.swing.JPanel();
        opGenerateTiles = new javax.swing.JRadioButton();
        generateTilesReverseDirectionOptionPanel = new javax.swing.JPanel();
        opGenerateOuterCap = new javax.swing.JRadioButton();
        generateOuterCapPanel = new javax.swing.JPanel();
        opGenerateTilesReverseDirection = new javax.swing.JRadioButton();
        menu = new javax.swing.JMenuBar();
        note = new javax.swing.JMenu();
        noteOnShingles = new javax.swing.JMenuItem();

        corkscrewNote.setTitle("Note on shingles");
        corkscrewNote.setMinimumSize(new java.awt.Dimension(330, 372));

        noteDescription.setEditable(false);
        noteDescription.setBackground(new java.awt.Color(240, 240, 240));
        noteDescription.setColumns(20);
        noteDescription.setLineWrap(true);
        noteDescription.setRows(5);
        noteDescription.setText(" The fact that there are so many different types of shingles that can be generated is confusing. The three types are: shingles, tiles, and cap. Shingles go along the inside wall of the corkscrew, much like shingles on the inside of a cylinder. Tiles go on the main surface of the corkscrew (the top) and may help prevent \"bounching\", especially when flipped upright and used as the wall of a loop. A cap goes on the outside wall of the corkscrew and is useful to prevent lemmings when the corkscrew is flipped upright to form a loop; the cap would then be on top of the loop.");
        noteDescription.setWrapStyleWord(true);
        noteDescription.setMinimumSize(new java.awt.Dimension(220, 150));
        noteDescription.setPreferredSize(new java.awt.Dimension(220, 150));

        javax.swing.GroupLayout corkscrewNoteLayout = new javax.swing.GroupLayout(corkscrewNote.getContentPane());
        corkscrewNote.getContentPane().setLayout(corkscrewNoteLayout);
        corkscrewNoteLayout.setHorizontalGroup(
                corkscrewNoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(corkscrewNoteLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(noteDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                                .addContainerGap())
        );
        corkscrewNoteLayout.setVerticalGroup(
                corkscrewNoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(corkscrewNoteLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(noteDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setTitle("Corkscrew Generator");

        outerRadiusPanel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                outerRadiusPanelFocusGained(evt);
            }
        });

        labOuterRadius.setLabelFor(inOuterRadius);
        labOuterRadius.setText("outerRadius:");

        inOuterRadius.setText("1024");
        inOuterRadius.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inOuterRadiusActionPerformed(evt);
            }
        });
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
        inInnerRadius.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inInnerRadiusActionPerformed(evt);
            }
        });
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

        inSlices.setText("16");
        inSlices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inSlicesActionPerformed(evt);
            }
        });
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

        labAltitude.setLabelFor(inAltitude);
        labAltitude.setText("altitude:");

        inAltitude.setText("320");
        inAltitude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inAltitudeActionPerformed(evt);
            }
        });
        inAltitude.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inAltitudeFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inAltitudeFocusLost(evt);
            }
        });

        javax.swing.GroupLayout altitudePanelLayout = new javax.swing.GroupLayout(altitudePanel);
        altitudePanel.setLayout(altitudePanelLayout);
        altitudePanelLayout.setHorizontalGroup(
                altitudePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(altitudePanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labAltitude)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inAltitude, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        altitudePanelLayout.setVerticalGroup(
                altitudePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(altitudePanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(altitudePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labAltitude)
                                        .addComponent(inAltitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        adjustForOverlappingCylinderCuts.setText("adjustForOverlappingCylinderCuts");
        adjustForOverlappingCylinderCuts.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        adjustForOverlappingCylinderCuts.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
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
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adjustForOverlappingCutsPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(adjustForOverlappingCylinderCuts)
                                .addContainerGap())
        );
        adjustForOverlappingCutsPanelLayout.setVerticalGroup(
                adjustForOverlappingCutsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(adjustForOverlappingCutsPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(adjustForOverlappingCylinderCuts)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        labLip.setLabelFor(inLip);
        labLip.setText("lip:");

        inLip.setText("192");
        inLip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inLipActionPerformed(evt);
            }
        });
        inLip.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inLipFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inLipFocusLost(evt);
            }
        });

        javax.swing.GroupLayout lipPanelLayout = new javax.swing.GroupLayout(lipPanel);
        lipPanel.setLayout(lipPanelLayout);
        lipPanelLayout.setHorizontalGroup(
                lipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(lipPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labLip)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inLip, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        lipPanelLayout.setVerticalGroup(
                lipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lipPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(lipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labLip)
                                        .addComponent(inLip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        innerThicknessPanel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                innerThicknessPanelFocusGained(evt);
            }
        });

        labInnerThickness.setLabelFor(inOuterRadius);
        labInnerThickness.setText("innerThickness:");

        inInnerThickness.setText("128");
        inInnerThickness.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inInnerThicknessActionPerformed(evt);
            }
        });
        inInnerThickness.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inInnerThicknessFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inInnerThicknessFocusLost(evt);
            }
        });

        javax.swing.GroupLayout innerThicknessPanelLayout = new javax.swing.GroupLayout(innerThicknessPanel);
        innerThicknessPanel.setLayout(innerThicknessPanelLayout);
        innerThicknessPanelLayout.setHorizontalGroup(
                innerThicknessPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(innerThicknessPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labInnerThickness)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inInnerThickness, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        innerThicknessPanelLayout.setVerticalGroup(
                innerThicknessPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, innerThicknessPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(innerThicknessPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labInnerThickness)
                                        .addComponent(inInnerThickness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        outerThicknessPanel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                outerThicknessPanelFocusGained(evt);
            }
        });

        labOuterThickness.setLabelFor(inOuterRadius);
        labOuterThickness.setText("outerThickness:");

        inOuterThickness.setText("256");
        inOuterThickness.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inOuterThicknessActionPerformed(evt);
            }
        });
        inOuterThickness.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inOuterThicknessFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inOuterThicknessFocusLost(evt);
            }
        });

        javax.swing.GroupLayout outerThicknessPanelLayout = new javax.swing.GroupLayout(outerThicknessPanel);
        outerThicknessPanel.setLayout(outerThicknessPanelLayout);
        outerThicknessPanelLayout.setHorizontalGroup(
                outerThicknessPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(outerThicknessPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labOuterThickness)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inOuterThickness, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        outerThicknessPanelLayout.setVerticalGroup(
                outerThicknessPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, outerThicknessPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(outerThicknessPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labOuterThickness)
                                        .addComponent(inOuterThickness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        offsetAnglePanel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                offsetAnglePanelFocusGained(evt);
            }
        });

        labOffsetAngle.setLabelFor(inOuterRadius);
        labOffsetAngle.setText("offsetAngle");

        inOffsetAngle.setText("0.0");
        inOffsetAngle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inOffsetAngleActionPerformed(evt);
            }
        });
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
                                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(valuesPanelLayout.createSequentialGroup()
                                                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(outerThicknessPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(innerThicknessPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(outerRadiusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(innerRadiusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(slicesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(altitudePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(adjustForOverlappingCutsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(lipPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(offsetAnglePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        valuesPanelLayout.setVerticalGroup(
                valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(valuesPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(outerRadiusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(innerRadiusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(altitudePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lipPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slicesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(outerThicknessPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(innerThicknessPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(adjustForOverlappingCutsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(offsetAnglePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        subtitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        subtitle.setText("Generates a quarter of a corkscrew as a .map file to be opened in radiant.");

        javax.swing.GroupLayout subtitlePanelLayout = new javax.swing.GroupLayout(subtitlePanel);
        subtitlePanel.setLayout(subtitlePanelLayout);
        subtitlePanelLayout.setHorizontalGroup(
                subtitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(subtitlePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(subtitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        descriptionScrollPane.setViewportView(description);

        descriptionTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout descriptionPanelLayout = new javax.swing.GroupLayout(descriptionPanel);
        descriptionPanel.setLayout(descriptionPanelLayout);
        descriptionPanelLayout.setHorizontalGroup(
                descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, descriptionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(descriptionTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(descriptionScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE))
                                .addContainerGap())
        );
        descriptionPanelLayout.setVerticalGroup(
                descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, descriptionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(descriptionTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(descriptionScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
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
                                .addComponent(statusScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(generate)
                                .addGap(18, 18, 18))
        );
        statusPanelLayout.setVerticalGroup(
                statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(statusPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(statusScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(statusPanelLayout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addComponent(generate)))
                                .addContainerGap())
        );

        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/corkscrew.png")));
        icon.setFocusable(false);

        javax.swing.GroupLayout iconPanelLayout = new javax.swing.GroupLayout(iconPanel);
        iconPanel.setLayout(iconPanelLayout);
        iconPanelLayout.setHorizontalGroup(
                iconPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, iconPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        iconPanelLayout.setVerticalGroup(
                iconPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, iconPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(icon, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        normalOptionPanel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                normalOptionPanelFocusLost(evt);
            }
        });

        options.add(opNormal);
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

        options.add(opCylinderShingles);
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

        options.add(opSquareRampShingles);
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
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(opSquareRampShingles, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        options.add(opTriRampShingles);
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
                        .addGroup(triShinglesOptionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(opTriRampShingles, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        options.add(opGenerateTiles);
        opGenerateTiles.setText("generate tiles");
        opGenerateTiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opGenerateTilesActionPerformed(evt);
            }
        });
        opGenerateTiles.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                opGenerateTilesFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                opGenerateTilesFocusLost(evt);
            }
        });

        javax.swing.GroupLayout generateTilesOptionPanelLayout = new javax.swing.GroupLayout(generateTilesOptionPanel);
        generateTilesOptionPanel.setLayout(generateTilesOptionPanelLayout);
        generateTilesOptionPanelLayout.setHorizontalGroup(
                generateTilesOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(generateTilesOptionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(opGenerateTiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        generateTilesOptionPanelLayout.setVerticalGroup(
                generateTilesOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, generateTilesOptionPanelLayout.createSequentialGroup()
                                .addGap(0, 4, Short.MAX_VALUE)
                                .addComponent(opGenerateTiles, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        options.add(opGenerateOuterCap);
        opGenerateOuterCap.setText("generate outer cap");
        opGenerateOuterCap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opGenerateOuterCapActionPerformed(evt);
            }
        });
        opGenerateOuterCap.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                opGenerateOuterCapFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                opGenerateOuterCapFocusLost(evt);
            }
        });

        javax.swing.GroupLayout generateTilesReverseDirectionOptionPanelLayout = new javax.swing.GroupLayout(generateTilesReverseDirectionOptionPanel);
        generateTilesReverseDirectionOptionPanel.setLayout(generateTilesReverseDirectionOptionPanelLayout);
        generateTilesReverseDirectionOptionPanelLayout.setHorizontalGroup(
                generateTilesReverseDirectionOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(generateTilesReverseDirectionOptionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(opGenerateOuterCap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        generateTilesReverseDirectionOptionPanelLayout.setVerticalGroup(
                generateTilesReverseDirectionOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, generateTilesReverseDirectionOptionPanelLayout.createSequentialGroup()
                                .addGap(0, 8, Short.MAX_VALUE)
                                .addComponent(opGenerateOuterCap, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        options.add(opGenerateTilesReverseDirection);
        opGenerateTilesReverseDirection.setText("generate tiles, reverse direction");
        opGenerateTilesReverseDirection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opGenerateTilesReverseDirectionActionPerformed(evt);
            }
        });
        opGenerateTilesReverseDirection.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                opGenerateTilesReverseDirectionFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                opGenerateTilesReverseDirectionFocusLost(evt);
            }
        });

        javax.swing.GroupLayout generateOuterCapPanelLayout = new javax.swing.GroupLayout(generateOuterCapPanel);
        generateOuterCapPanel.setLayout(generateOuterCapPanelLayout);
        generateOuterCapPanelLayout.setHorizontalGroup(
                generateOuterCapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(generateOuterCapPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(opGenerateTilesReverseDirection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        generateOuterCapPanelLayout.setVerticalGroup(
                generateOuterCapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, generateOuterCapPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(opGenerateTilesReverseDirection, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout optionsPanelLayout = new javax.swing.GroupLayout(optionsPanel);
        optionsPanel.setLayout(optionsPanelLayout);
        optionsPanelLayout.setHorizontalGroup(
                optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(optionsPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionsPanelLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(normalOptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(cylinderShinglesOptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(squareShinglesOptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(triShinglesOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(generateTilesOptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(generateTilesReverseDirectionOptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(generateOuterCapPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        optionsPanelLayout.setVerticalGroup(
                optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(optionsPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(normalOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cylinderShinglesOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(squareShinglesOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(triShinglesOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(generateTilesOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(generateOuterCapPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(generateTilesReverseDirectionOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout viewPanelLayout = new javax.swing.GroupLayout(viewPanel);
        viewPanel.setLayout(viewPanelLayout);
        viewPanelLayout.setHorizontalGroup(
                viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(viewPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewPanelLayout.createSequentialGroup()
                                                        .addComponent(optionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(iconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(12, 12, 12))
                                                .addComponent(subtitlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewPanelLayout.createSequentialGroup()
                                                        .addComponent(valuesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(viewPanelLayout.createSequentialGroup()
                                                .addComponent(statusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(12, 12, 12)))
                                .addContainerGap(91, Short.MAX_VALUE))
        );
        viewPanelLayout.setVerticalGroup(
                viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(viewPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(subtitlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(valuesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(optionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewPanelLayout.createSequentialGroup()
                                                .addComponent(iconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(10, 10, 10)))
                                .addComponent(statusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(95, Short.MAX_VALUE))
        );

        scrollPane1.setViewportView(viewPanel);

        note.setText("Note");
        note.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noteActionPerformed(evt);
            }
        });

        noteOnShingles.setText("Note on shingles");
        noteOnShingles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noteOnShinglesActionPerformed(evt);
            }
        });
        note.add(noteOnShingles);

        menu.add(note);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(scrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
                                .addGap(0, 1, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(scrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
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

    private void inAltitudeFocusGained(java.awt.event.FocusEvent evt) {

        altitudePanel.setBackground(shaded);
        descriptionTitle.setText(altitudeTitle);
        description.setText(altitudeDescription);
    }

    private void inAltitudeFocusLost(java.awt.event.FocusEvent evt) {

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

    private void inLipFocusGained(java.awt.event.FocusEvent evt) {

        lipPanel.setBackground(shaded);
        descriptionTitle.setText(lipTitle);
        description.setText(lipDescription);
    }

    private void inLipFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void generateActionPerformed(java.awt.event.ActionEvent evt) throws IOException {

        generate.setEnabled(false);

        if (opNormal.isSelected()) {
            String[] args = {inOuterRadius.getText(), inInnerRadius.getText(), inAltitude.getText(), inLip.getText(), inSlices.getText(), inOuterThickness.getText(), inInnerThickness.getText(), Boolean.toString(adjustForOverlappingCylinderCuts.isSelected()), inOffsetAngle.getText()};
            status.setText("Generating corkscrew...");
            CorkscrewGenerator.main(args);
        } else if (opCylinderShingles.isSelected() || opSquareRampShingles.isSelected() || opTriRampShingles.isSelected()) {
            int shingles = 0;

            if (opCylinderShingles.isSelected())
                shingles = SHINGLES_TYPE_CYL;
            if (opSquareRampShingles.isSelected())
                shingles = SHINGLES_TYPE_RAMP_SQTOP;
            if (opTriRampShingles.isSelected())
                shingles = SHINGLES_TYPE_RAMP_TRITOP;

            String[] args = {inInnerRadius.getText(), inAltitude.getText(), inLip.getText(), inSlices.getText(), inInnerThickness.getText(), Boolean.toString(adjustForOverlappingCylinderCuts.isSelected()), Integer.toString(shingles)};
            status.setText("Generating shingles...");
            CorkscrewShinglesGenerator.main(args);
        } else if (opGenerateTiles.isSelected() || opGenerateTilesReverseDirection.isSelected()) {
            String[] args = {inOuterRadius.getText(), inInnerRadius.getText(), inAltitude.getText(), inLip.getText(), inSlices.getText(), Boolean.toString(adjustForOverlappingCylinderCuts.isSelected()), Boolean.toString(opGenerateTilesReverseDirection.isSelected()), inOffsetAngle.getText()};
            status.setText("Generating tiles...");
            CorkscrewTilesGenerator.main(args);
        } else if (opGenerateOuterCap.isSelected()) {
            String[] args = {inOuterRadius.getText(), inAltitude.getText(), inSlices.getText(), inOuterThickness.getText(), Boolean.toString(adjustForOverlappingCylinderCuts.isSelected())};
            status.setText("Generating cap...");
            CorkscrewCapGenerator.main(args);
        }


        status.setText("Generated - select save file name and location");

        File saveFile = UlbenUtils.getSaveFile(this, "CORKSCREW");

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

    private void inInnerThicknessFocusGained(java.awt.event.FocusEvent evt) {

        innerThicknessPanel.setBackground(shaded);
        descriptionTitle.setText(innerThicknessTitle);
        description.setText(innerThicknessDescription);
    }

    private void inInnerThicknessFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void innerThicknessPanelFocusGained(java.awt.event.FocusEvent evt) {

    }

    private void inOuterThicknessFocusGained(java.awt.event.FocusEvent evt) {

        outerThicknessPanel.setBackground(shaded);
        descriptionTitle.setText(outerThicknessTitle);
        description.setText(outerThicknessDescription);
    }

    private void inOuterThicknessFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void outerThicknessPanelFocusGained(java.awt.event.FocusEvent evt) {

    }

    private void opNormalActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void opNormalFocusGained(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
        normalOptionPanel.setBackground(shaded);
        descriptionTitle.setText(opNormalTitle);
        description.setText(opNormalDescription);
    }

    private void opNormalFocusLost(java.awt.event.FocusEvent evt) {

        normalOptionPanel.setBackground(normal);
        checkGenerateOK();
    }

    private void normalOptionPanelFocusLost(java.awt.event.FocusEvent evt) {

    }

    private void opCylinderShinglesActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void opCylinderShinglesFocusGained(java.awt.event.FocusEvent evt) {

        cylinderShinglesOptionPanel.setBackground(shaded);
        descriptionTitle.setText(opCylinderShinglesTitle);
        description.setText(opCylinderShinglesDescription);
    }

    private void opCylinderShinglesFocusLost(java.awt.event.FocusEvent evt) {

        cylinderShinglesOptionPanel.setBackground(normal);
        checkGenerateOK();
    }

    private void opSquareRampShinglesActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void opSquareRampShinglesFocusGained(java.awt.event.FocusEvent evt) {

        squareShinglesOptionPanel.setBackground(shaded);
        descriptionTitle.setText(opSquareRampShinglesTitle);
        description.setText(opSquareRampShinglesDescription);
    }

    private void opSquareRampShinglesFocusLost(java.awt.event.FocusEvent evt) {

        squareShinglesOptionPanel.setBackground(normal);
        checkGenerateOK();
    }

    private void opTriRampShinglesActionPerformed(java.awt.event.ActionEvent evt) {

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

    private void inOffsetAngleFocusGained(java.awt.event.FocusEvent evt) {

        offsetAnglePanel.setBackground(shaded);
        descriptionTitle.setText(offsetAngleTitle);
        description.setText(offsetAngleDescription);
    }

    private void inOffsetAngleFocusLost(java.awt.event.FocusEvent evt) {
        checkGenerateOK();
    }

    private void offsetAnglePanelFocusGained(java.awt.event.FocusEvent evt) {

    }

    private void opGenerateTilesFocusGained(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
        generateTilesOptionPanel.setBackground(shaded);
        descriptionTitle.setText(opGenerateTilesTitle);
        description.setText(opGenerateTilesDescription);
    }

    private void opGenerateTilesFocusLost(java.awt.event.FocusEvent evt) {

        generateTilesOptionPanel.setBackground(normal);
        checkGenerateOK();
    }

    private void opGenerateTilesReverseDirectionFocusGained(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
        generateTilesReverseDirectionOptionPanel.setBackground(shaded);
        descriptionTitle.setText(opGenerateTilesReverseDirectionTitle);
        description.setText(opGenerateTilesReverseDirectionDescription);
    }

    private void opGenerateTilesReverseDirectionFocusLost(java.awt.event.FocusEvent evt) {

        generateTilesReverseDirectionOptionPanel.setBackground(normal);
        checkGenerateOK();
    }

    private void opGenerateOuterCapFocusGained(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
        generateOuterCapPanel.setBackground(shaded);
        descriptionTitle.setText(opGenerateOuterCapTitle);
        description.setText(opGenerateOuterCapDescription);
    }

    private void opGenerateOuterCapFocusLost(java.awt.event.FocusEvent evt) {

        generateOuterCapPanel.setBackground(normal);
        checkGenerateOK();
    }

    private void opGenerateTilesActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void opGenerateTilesReverseDirectionActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void opGenerateOuterCapActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inOuterRadiusActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inInnerRadiusActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inAltitudeActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inLipActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inSlicesActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inOuterThicknessActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inInnerThicknessActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inOffsetAngleActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void noteActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void noteOnShinglesActionPerformed(java.awt.event.ActionEvent evt) {

        corkscrewNote.setVisible(true);
    }


    private void checkGenerateOK() {
        boolean generateOK = true;
        statusPanel.setBackground(normal);
        status.setText("");
        String statusText = "";


        try {
            int temp = Integer.parseInt(inAltitude.getText());

            altitudePanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a whole number for altitude\n";
            else
                statusText += ex.getMessage() + "\n";
            altitudePanel.setBackground(Color.red);
        }


        try {
            int temp = Integer.parseInt(inSlices.getText());

            if (temp < 1)
                throw new Exception("Min slices is 1");

            slicesPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a whole number for slices\n";
            else
                statusText += ex.getMessage() + "\n";
            slicesPanel.setBackground(Color.red);

        }


        if (opGenerateOuterCap.isSelected()) {
            inLip.setEnabled(false);
            labLip.setEnabled(false);
            lipPanel.setBackground(normal);
        } else {
            inLip.setEnabled(true);
            labLip.setEnabled(true);

            try {
                double temp = Double.parseDouble(inLip.getText());

                if (temp < 0)
                    throw new Exception("lip cannot be negative");

                lipPanel.setBackground(normal);
            } catch (Exception ex) {
                generateOK = false;
                if (ex instanceof NumberFormatException)
                    statusText += "Please enter a number\n";
                else
                    statusText += ex.getMessage() + "\n";
                lipPanel.setBackground(Color.red);
            }
        }


        if (opCylinderShingles.isSelected() || opSquareRampShingles.isSelected() || opTriRampShingles.isSelected()) {
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
        }


        if (opGenerateOuterCap.isSelected()) {
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

                if (temp >= outerRadius)
                    throw new Exception("innerRadius must be less than outerRadius");

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


        if (opCylinderShingles.isSelected() || opSquareRampShingles.isSelected() || opTriRampShingles.isSelected() || opGenerateTiles.isSelected() || opGenerateTilesReverseDirection.isSelected()) {
            inOuterThickness.setEnabled(false);
            labOuterThickness.setEnabled(false);
            outerThicknessPanel.setBackground(normal);
        } else {
            inOuterThickness.setEnabled(true);
            labOuterThickness.setEnabled(true);

            try {
                int temp = Integer.parseInt(inOuterThickness.getText());

                if (temp <= 0)
                    throw new Exception("outerThickness must be positive");

                if (temp <= innerThickness)
                    throw new Exception("outerThickness must be greater than innerThickness");

                outerThickness = temp;
                outerThicknessPanel.setBackground(normal);
            } catch (Exception ex) {
                generateOK = false;
                if (ex instanceof NumberFormatException)
                    statusText += "Please enter a number\n";
                else
                    statusText += ex.getMessage() + "\n";
                outerRadiusPanel.setBackground(Color.red);
            }
        }


        if (opGenerateOuterCap.isSelected() || opGenerateTiles.isSelected() || opGenerateTilesReverseDirection.isSelected()) {
            inInnerThickness.setEnabled(false);
            labInnerThickness.setEnabled(false);
            innerThicknessPanel.setBackground(normal);
        } else {
            inInnerThickness.setEnabled(true);
            labInnerThickness.setEnabled(true);

            try {
                int temp = Integer.parseInt(inInnerThickness.getText());

                if (temp <= 0)
                    throw new Exception("innerThickness cannot be negative");

                if (temp >= outerThickness)
                    throw new Exception("innerThickness must be less than outerThickness");

                innerThickness = temp;
                innerThicknessPanel.setBackground(normal);
            } catch (Exception ex) {
                generateOK = false;
                statusPanel.setBackground(Color.yellow);
                if (ex instanceof NumberFormatException)
                    statusText += "Please enter a number\n";
                else
                    statusText += ex.getMessage() + "\n";
                innerThicknessPanel.setBackground(Color.red);
            }
        }


        if (opCylinderShingles.isSelected() || opSquareRampShingles.isSelected() || opTriRampShingles.isSelected() || opGenerateOuterCap.isSelected()) {
            inOffsetAngle.setEnabled(false);
            labOffsetAngle.setEnabled(false);
            offsetAnglePanel.setBackground(normal);
        } else {
            inOffsetAngle.setEnabled(true);
            labOffsetAngle.setEnabled(true);

            try {
                double temp = Double.parseDouble(inOffsetAngle.getText());

                if (temp < 0.0)
                    throw new Exception("offsetAngle cannot be negative");

                offsetAnglePanel.setBackground(normal);
            } catch (Exception ex) {
                if (ex instanceof NumberFormatException)
                    status.setText("Please enter a number for the offsetAngle");
                else
                    status.setText(ex.getMessage());

                generateOK = false;
                offsetAnglePanel.setBackground(Color.red);
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
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException ex) {
            java.util.logging.Logger.getLogger(CorkscrewGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CorkscrewGeneratorGUI(new Config()).setVisible(true);
            }
        });
    }

    private javax.swing.JPanel adjustForOverlappingCutsPanel;
    private javax.swing.JCheckBox adjustForOverlappingCylinderCuts;
    private javax.swing.JPanel altitudePanel;
    private javax.swing.JDialog corkscrewNote;
    private javax.swing.JPanel cylinderShinglesOptionPanel;
    private javax.swing.JTextArea description;
    private javax.swing.JPanel descriptionPanel;
    private javax.swing.JScrollPane descriptionScrollPane;
    private javax.swing.JLabel descriptionTitle;
    private javax.swing.JButton generate;
    private javax.swing.JPanel generateOuterCapPanel;
    private javax.swing.JPanel generateTilesOptionPanel;
    private javax.swing.JPanel generateTilesReverseDirectionOptionPanel;
    private javax.swing.JLabel icon;
    private javax.swing.JPanel iconPanel;
    private javax.swing.JTextField inAltitude;
    private javax.swing.JTextField inInnerRadius;
    private javax.swing.JTextField inInnerThickness;
    private javax.swing.JTextField inLip;
    private javax.swing.JTextField inOffsetAngle;
    private javax.swing.JTextField inOuterRadius;
    private javax.swing.JTextField inOuterThickness;
    private javax.swing.JTextField inSlices;
    private javax.swing.JPanel innerRadiusPanel;
    private javax.swing.JPanel innerThicknessPanel;
    private javax.swing.JLabel labAltitude;
    private javax.swing.JLabel labInnerRadius;
    private javax.swing.JLabel labInnerThickness;
    private javax.swing.JLabel labLip;
    private javax.swing.JLabel labOffsetAngle;
    private javax.swing.JLabel labOuterRadius;
    private javax.swing.JLabel labOuterThickness;
    private javax.swing.JLabel labSlices;
    private javax.swing.JPanel lipPanel;
    private javax.swing.JMenuBar menu;
    private javax.swing.JPanel normalOptionPanel;
    private javax.swing.JMenu note;
    private javax.swing.JTextArea noteDescription;
    private javax.swing.JMenuItem noteOnShingles;
    private javax.swing.JPanel offsetAnglePanel;
    private javax.swing.JRadioButton opCylinderShingles;
    private javax.swing.JRadioButton opGenerateOuterCap;
    private javax.swing.JRadioButton opGenerateTiles;
    private javax.swing.JRadioButton opGenerateTilesReverseDirection;
    private javax.swing.JRadioButton opNormal;
    private javax.swing.JRadioButton opSquareRampShingles;
    private javax.swing.JRadioButton opTriRampShingles;
    private javax.swing.ButtonGroup options;
    private javax.swing.JPanel optionsPanel;
    private javax.swing.JPanel outerRadiusPanel;
    private javax.swing.JPanel outerThicknessPanel;
    private javax.swing.JScrollPane scrollPane1;
    private javax.swing.JPanel slicesPanel;
    private javax.swing.JPanel squareShinglesOptionPanel;
    private javax.swing.JTextArea status;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JScrollPane statusScrollPane;
    private javax.swing.JLabel subtitle;
    private javax.swing.JPanel subtitlePanel;
    private javax.swing.JPanel triShinglesOptionPanel;
    private javax.swing.JPanel valuesPanel;
    private javax.swing.JPanel viewPanel;

}
