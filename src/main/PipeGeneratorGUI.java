package main;

import generators.*;
import tools.MapFactory;
import ulben.NumericTextField;
import ulben.UlbenUtils;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

public class PipeGeneratorGUI extends javax.swing.JFrame {

    public final static int SHINGLES_TYPE_CYL = 0;
    public final static int SHINGLES_TYPE_RAMP_SQTOP = 1;
    public final static int SHINGLES_TYPE_RAMP_TRITOP = 2;

    private Config configuration;

    private Color shaded = new Color(200, 200, 200);
    private Color normal = this.getBackground();

    private String radiusTitle = "Radius";
    private String radiusDescription = "The radius of the face of the ramp.";

    private String slicesTitle = "Slices";
    private String slicesDescription = "The number of individual surfaces that will make up the ramp.";

    private String crossSectionTitle = "Cross Section";
    private String crossSectionDescription = "The overall height and depth of the ramp. This parameter is ignored if shingles are being generated.";

    private String lengthTitle = "Length";
    private String lengthDescription = "How long the pipe is. Can also be described as the width of the ramp face.";

    private String extrudeTopNSlicesTitle = "extrudeTopNSlices";
    private String extrudeTopNSlicesDescription = "If this is greater than zero and if extrudeRadiusFactor is greater than one, the topmostTopNSlices vertices of the ramp will be pushed back so that the top of the ramp is no longer vertical. It \"relaxes\" the top of the ramp to have a less steep slope.";

    private String extrudeRadiusFactorTitle = "Extrude Radius Factor";
    private String extrudeRadiusFactorDescription = "Used together with extrudeTopNSlices. This value should be greater than one and extrudeTopNSlices should be greater than zero to make the top of the map less steep. It is possible to use values slightly less than one here to generate an \"oversteep\" top, but if used improperly, poor results will be generated, or [possibly] nothing will be generated at all. A value of 1.0 corresponds to a normal ramp with perfectly vertical top. Otherwise, the top vertices are generated based on a radius of\n(radius * extrudeRadiusFactor).\n\nUse the calculator in the top menu to calculate this value for a given lip offset.";

    private String adjustForOverlappingPipeCutsTitle = "Adjust For Overlapping Pipe Cuts";
    private String adjustForOverlappingPipeCutsDescription = "In the case where overlapping pipe brushes are needed, the overlapping brushes may be cut at the top of the ramp (at z = 0) and a the bottom of the ramp (at x = 0) in order to make a perfect quarter pipe with no brushes sticking out beyond these two planes. Use this flag to adjust vertex positions so that after the cuts, the resulting brushes are aligned along integer coordinates. Use this flag on non-overlapping brush generation also in case normal brushes are superimposed on top of overlapping brushes.";

    private String adjustForOverlappingBowlCutsTitle = "Adjust For Overlapping Bowl Cuts";
    private String adjustForOverlappingBowlCutsDescription = "If this pipe is going to fit perfectly at the seam of a quarter bowl, and if that bowl will be made of overlapping brushes which will be cut at the top (at z = 0), use this flag on both the bowl and on the pipe so that the resulting cut brushes from the bowl will lie on integer coordinates.";

    private String opNormalTitle = "Generate normal non-overlapping brushes";
    private String opNormalDescription = "Generates ordinary brushes. To make a slick ramp for jumping purposes, you will either have to add shingles to the ramp or generate overlapping brushes in order to prevent \"bouncing\". Another purpose for normal brushes is to define the visual component of a structure, for example by using surfaceparm nonsolid.";

    private String opOverlappingTitle = "Generate overlapping brushes";
    private String opOverlappingDescription = "Overlapping brushes are useful for making slick ramps (for jumping) such that the player doesn't \"bounce\" off the surface. However, it is not advises to texture the overlapping brush faces with drawn textures. Instead, apply slick caulk which does no damage to all of the overlapping brushes, then superimpose a set of non-overlapping brushes that have surfaceparm nonsolid. \n\n ulben note: only useful for 2 way loops, just use triangular shingles for any other case";

    private String opCylinderShinglesTitle = "Generate cylinder shingles";
    private String opCylinderShinglesDescription = "Can be used together with non-overlapping brushes to create smooth surfaces that allow for high speeds while clinging to the inside of a cylinder. These shingles are one-directional. Cylinder shingles are useful when the pipe is put on its side to form a cylinder. They are also used for the top half of a vertical loop.";

    private String opSquareRampShinglesTitle = "Generate ramp shingles, top shingle square";
    private String opSquareRampShinglesDescription = "Can be used together with non-overlapping brushes to make smooth slick ramps for jumping. You may want to generate cylinder shingles if the pipe brushes are to be used as an upright cylinder or as the top part of a tube or loop. The top shingle is square to prevent lemmings. \n\n ulben note: useless, use triangular and g_nodamage 1";

    private String opTriRampShinglesTitle = "Generate ramp shingles, top shingle triangular";
    private String opTriRampShinglesDescription = "Same as the one down, only top shingle is just like the rest of them. These shingles should be used for the fourth quarter of a loop.";


    public PipeGeneratorGUI(Config configuration) {
        initComponents();
        this.configuration = configuration;
        Dimension frameSize = getSize();
        int frameHeight = frameSize.height;
        int frameWidth = frameSize.width;
        if (frameHeight > configuration.getScreenHeight()) {
            setSize(frameWidth, configuration.getScreenHeight());
        }
    }

    private void initComponents() {
        calculateExtrudeRadiusPopup = new javax.swing.JDialog();
        calculatorPanel = new javax.swing.JPanel();
        calculatorOptionPanel = new javax.swing.JPanel();
        laLipOffset = new javax.swing.JLabel();
        inLipOffset = new javax.swing.JTextField();
        calculatorStatusPanel = new javax.swing.JPanel();
        calculatorStatus = new javax.swing.JLabel();
        calculatePanel = new javax.swing.JPanel();
        calculate = new javax.swing.JButton();
        calculatorDescriptionPanel = new javax.swing.JPanel();
        calculatorDescription = new javax.swing.JTextArea();
        scrollPane = new javax.swing.JScrollPane();
        viewPanel = new javax.swing.JPanel();
        iconPanel = new javax.swing.JPanel();
        icon = new javax.swing.JLabel();
        statusPanel = new javax.swing.JPanel();
        generate = new javax.swing.JButton();
        statusScrollPane = new javax.swing.JScrollPane();
        status = new javax.swing.JTextArea();
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
        descriptionPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        description = new javax.swing.JTextArea();
        descriptionTitle = new javax.swing.JLabel();
        subtitlePanel = new javax.swing.JPanel();
        subtitle = new javax.swing.JLabel();
        valuesPanel = new javax.swing.JPanel();
        radiusPanel = new javax.swing.JPanel();
        labRadius = new javax.swing.JLabel();
        inRadius = new NumericTextField(64);
        crossSectionPanel = new javax.swing.JPanel();
        labCrossSection = new javax.swing.JLabel();
        inCrossSection = new NumericTextField(64);
        slicesPanel = new javax.swing.JPanel();
        labSlices = new javax.swing.JLabel();
        inSlices = new NumericTextField(1);
        lengthPanel = new javax.swing.JPanel();
        labLength = new javax.swing.JLabel();
        inLength = new NumericTextField(64);
        extrudeRadiusFactorPanel = new javax.swing.JPanel();
        labExtrudeRadiusFactor = new javax.swing.JLabel();
        inExtrudeRadiusFactor = new javax.swing.JTextField();
        adjustForOverlappingBowlCutsPanel = new javax.swing.JPanel();
        adjustForOverlappingBowlCuts = new javax.swing.JCheckBox();
        extrudeTopNSlicesPanel = new javax.swing.JPanel();
        labExtrudeTopNSlices = new javax.swing.JLabel();
        inExtrudeTopNSlices = new javax.swing.JTextField();
        adjustForOverlappingPipeCutsPanel = new javax.swing.JPanel();
        adjustForOverlappingPipeCuts = new javax.swing.JCheckBox();
        menuBar = new javax.swing.JMenuBar();
        calculator = new javax.swing.JMenu();
        calculateExtrudeRadiusFactor = new javax.swing.JMenuItem();

        calculateExtrudeRadiusPopup.setTitle("Extrude Radius Factor Calculator");
        calculateExtrudeRadiusPopup.setMinimumSize(new java.awt.Dimension(330, 372));

        laLipOffset.setFont(new java.awt.Font("Tahoma", 0, 12));
        laLipOffset.setText("lipOffset:");

        inLipOffset.setFont(new java.awt.Font("Tahoma", 0, 12));
        inLipOffset.setText("5");
        inLipOffset.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                inLipOffsetFocusLost(evt);
            }
        });
        inLipOffset.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                inLipOffsetPropertyChange(evt);
            }
        });
        inLipOffset.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inLipOffsetKeyPressed(evt);
            }

            public void keyTyped(java.awt.event.KeyEvent evt) {
                inLipOffsetKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout calculatorOptionPanelLayout = new javax.swing.GroupLayout(calculatorOptionPanel);
        calculatorOptionPanel.setLayout(calculatorOptionPanelLayout);
        calculatorOptionPanelLayout.setHorizontalGroup(
                calculatorOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(calculatorOptionPanelLayout.createSequentialGroup()
                                .addContainerGap(37, Short.MAX_VALUE)
                                .addComponent(laLipOffset)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inLipOffset, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        calculatorOptionPanelLayout.setVerticalGroup(
                calculatorOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, calculatorOptionPanelLayout.createSequentialGroup()
                                .addGap(0, 30, Short.MAX_VALUE)
                                .addGroup(calculatorOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(inLipOffset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(laLipOffset)))
        );

        calculatorStatus.setFont(new java.awt.Font("Tahoma", 0, 12));
        calculatorStatus.setText("Enter a value for the lip offset");

        javax.swing.GroupLayout calculatorStatusPanelLayout = new javax.swing.GroupLayout(calculatorStatusPanel);
        calculatorStatusPanel.setLayout(calculatorStatusPanelLayout);
        calculatorStatusPanelLayout.setHorizontalGroup(
                calculatorStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(calculatorStatusPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(calculatorStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                                .addContainerGap())
        );
        calculatorStatusPanelLayout.setVerticalGroup(
                calculatorStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, calculatorStatusPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(calculatorStatus)
                                .addContainerGap())
        );

        calculate.setText("Calculate");
        calculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout calculatePanelLayout = new javax.swing.GroupLayout(calculatePanel);
        calculatePanel.setLayout(calculatePanelLayout);
        calculatePanelLayout.setHorizontalGroup(
                calculatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, calculatePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(calculate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        calculatePanelLayout.setVerticalGroup(
                calculatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, calculatePanelLayout.createSequentialGroup()
                                .addGap(0, 24, Short.MAX_VALUE)
                                .addComponent(calculate))
        );

        calculatorDescription.setEditable(false);
        calculatorDescription.setBackground(new java.awt.Color(240, 240, 240));
        calculatorDescription.setColumns(20);
        calculatorDescription.setLineWrap(true);
        calculatorDescription.setRows(5);
        calculatorDescription.setText("Calculates the extrudeRadiusFactor based on the values of radius, slices and topNSlices.\n\nThe lipOffset is the number of units the top edge of the circular arc is to be nudged outward.\n\nNote that specifying a lipOffset of greater than what is possible to extrude (by setting a radius to be very large)  will generate erroneous results. ");
        calculatorDescription.setWrapStyleWord(true);

        javax.swing.GroupLayout calculatorDescriptionPanelLayout = new javax.swing.GroupLayout(calculatorDescriptionPanel);
        calculatorDescriptionPanel.setLayout(calculatorDescriptionPanelLayout);
        calculatorDescriptionPanelLayout.setHorizontalGroup(
                calculatorDescriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(calculatorDescriptionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(calculatorDescription)
                                .addContainerGap(13, Short.MAX_VALUE))
        );
        calculatorDescriptionPanelLayout.setVerticalGroup(
                calculatorDescriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, calculatorDescriptionPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(calculatorDescription)
                                .addGap(245, 245, 245))
        );

        javax.swing.GroupLayout calculatorPanelLayout = new javax.swing.GroupLayout(calculatorPanel);
        calculatorPanel.setLayout(calculatorPanelLayout);
        calculatorPanelLayout.setHorizontalGroup(
                calculatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(calculatorPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(calculatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(calculatorPanelLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(calculatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(calculatorStatusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(calculatorPanelLayout.createSequentialGroup()
                                                                .addComponent(calculatorOptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(calculatePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(calculatorDescriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        calculatorPanelLayout.setVerticalGroup(
                calculatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(calculatorPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(calculatorDescriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(calculatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(calculatorOptionPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(calculatePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(calculatorStatusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout calculateExtrudeRadiusPopupLayout = new javax.swing.GroupLayout(calculateExtrudeRadiusPopup.getContentPane());
        calculateExtrudeRadiusPopup.getContentPane().setLayout(calculateExtrudeRadiusPopupLayout);
        calculateExtrudeRadiusPopupLayout.setHorizontalGroup(
                calculateExtrudeRadiusPopupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(calculateExtrudeRadiusPopupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(calculatorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        calculateExtrudeRadiusPopupLayout.setVerticalGroup(
                calculateExtrudeRadiusPopupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(calculateExtrudeRadiusPopupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(calculatorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        setTitle("Quarter Pipe Generator");
        setLocationByPlatform(true);

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/quarterPipeIcon.png")));
        icon.setFocusable(false);

        javax.swing.GroupLayout iconPanelLayout = new javax.swing.GroupLayout(iconPanel);
        iconPanel.setLayout(iconPanelLayout);
        iconPanelLayout.setHorizontalGroup(
                iconPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(iconPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        iconPanelLayout.setVerticalGroup(
                iconPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, iconPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(icon, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                .addGap(18, 18, 18)
                                .addComponent(generate)
                                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
                statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(statusPanelLayout.createSequentialGroup()
                                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(statusPanelLayout.createSequentialGroup()
                                                .addGap(25, 25, 25)
                                                .addComponent(generate))
                                        .addGroup(statusPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(statusScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                } else {
                    opCylinderShingles.setEnabled(true);
                    opSquareRampShingles.setEnabled(true);
                    opTriRampShingles.setEnabled(true);

                }
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

        opTriRampShingles.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (opTriRampShingles.isSelected()) {
                    opSquareRampShingles.setSelected(false);
                    opCylinderShingles.setSelected(false);
                }
            }
        });

        opTriRampShingles.setSelected(true);
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
                        .addComponent(opTriRampShingles)
        );

        javax.swing.GroupLayout optionsPanelLayout = new javax.swing.GroupLayout(optionsPanel);
        optionsPanel.setLayout(optionsPanelLayout);
        optionsPanelLayout.setHorizontalGroup(
                optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionsPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(overlappingBrushOptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(normalOptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cylinderShinglesOptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(squareShinglesOptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(triShinglesOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        optionsPanelLayout.setVerticalGroup(
                optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(optionsPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(normalOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(overlappingBrushOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(25, 25, 25)
                                .addComponent(triShinglesOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(squareShinglesOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cylinderShinglesOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(163, 163, 163))
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
                                .addComponent(descriptionTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, descriptionPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26))
        );
        descriptionPanelLayout.setVerticalGroup(
                descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, descriptionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(descriptionTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                                .addContainerGap())
        );

        subtitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        subtitle.setText("Generates a quarter pipe ramp as a .map file to be opened in radiant.");

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

        radiusPanel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                radiusPanelFocusGained(evt);
            }
        });

        labRadius.setLabelFor(inRadius);
        labRadius.setText("radius:");

        inRadius.setText("512");
        inRadius.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inRadiusFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inRadiusFocusLost(evt);
            }
        });

        javax.swing.GroupLayout radiusPanelLayout = new javax.swing.GroupLayout(radiusPanel);
        radiusPanel.setLayout(radiusPanelLayout);
        radiusPanelLayout.setHorizontalGroup(
                radiusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(radiusPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labRadius)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inRadius, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        radiusPanelLayout.setVerticalGroup(
                radiusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, radiusPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(radiusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labRadius)
                                        .addComponent(inRadius, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        labCrossSection.setLabelFor(inCrossSection);
        labCrossSection.setText("crossSection:");

        inCrossSection.setText("768");
        inCrossSection.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inCrossSectionFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inCrossSectionFocusLost(evt);
            }
        });

        javax.swing.GroupLayout crossSectionPanelLayout = new javax.swing.GroupLayout(crossSectionPanel);
        crossSectionPanel.setLayout(crossSectionPanelLayout);
        crossSectionPanelLayout.setHorizontalGroup(
                crossSectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(crossSectionPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labCrossSection)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inCrossSection, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        crossSectionPanelLayout.setVerticalGroup(
                crossSectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crossSectionPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(crossSectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labCrossSection)
                                        .addComponent(inCrossSection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        labLength.setLabelFor(inLength);
        labLength.setText("length:");

        inLength.setText("256");
        inLength.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inLengthFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inLengthFocusLost(evt);
            }
        });

        javax.swing.GroupLayout lengthPanelLayout = new javax.swing.GroupLayout(lengthPanel);
        lengthPanel.setLayout(lengthPanelLayout);
        lengthPanelLayout.setHorizontalGroup(
                lengthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(lengthPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labLength)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inLength, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        lengthPanelLayout.setVerticalGroup(
                lengthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(lengthPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(lengthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labLength)
                                        .addComponent(inLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        labExtrudeRadiusFactor.setLabelFor(inExtrudeRadiusFactor);
        labExtrudeRadiusFactor.setText("extrudeRadiusFactor:");
        labExtrudeRadiusFactor.setEnabled(false);

        inExtrudeRadiusFactor.setText("1.0");
        inExtrudeRadiusFactor.setEnabled(false);
        inExtrudeRadiusFactor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inExtrudeRadiusFactorFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inExtrudeRadiusFactorFocusLost(evt);
            }
        });

        javax.swing.GroupLayout extrudeRadiusFactorPanelLayout = new javax.swing.GroupLayout(extrudeRadiusFactorPanel);
        extrudeRadiusFactorPanel.setLayout(extrudeRadiusFactorPanelLayout);
        extrudeRadiusFactorPanelLayout.setHorizontalGroup(
                extrudeRadiusFactorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(extrudeRadiusFactorPanelLayout.createSequentialGroup()
                                .addComponent(labExtrudeRadiusFactor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inExtrudeRadiusFactor)
                                .addContainerGap())
        );
        extrudeRadiusFactorPanelLayout.setVerticalGroup(
                extrudeRadiusFactorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, extrudeRadiusFactorPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(extrudeRadiusFactorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labExtrudeRadiusFactor)
                                        .addComponent(inExtrudeRadiusFactor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        adjustForOverlappingBowlCuts.setText("adjustForOverlappingBowlCuts");
        adjustForOverlappingBowlCuts.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        adjustForOverlappingBowlCuts.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        adjustForOverlappingBowlCuts.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                adjustForOverlappingBowlCutsFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                adjustForOverlappingBowlCutsFocusLost(evt);
            }
        });

        javax.swing.GroupLayout adjustForOverlappingBowlCutsPanelLayout = new javax.swing.GroupLayout(adjustForOverlappingBowlCutsPanel);
        adjustForOverlappingBowlCutsPanel.setLayout(adjustForOverlappingBowlCutsPanelLayout);
        adjustForOverlappingBowlCutsPanelLayout.setHorizontalGroup(
                adjustForOverlappingBowlCutsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adjustForOverlappingBowlCutsPanelLayout.createSequentialGroup()
                                .addContainerGap(82, Short.MAX_VALUE)
                                .addComponent(adjustForOverlappingBowlCuts)
                                .addContainerGap())
        );
        adjustForOverlappingBowlCutsPanelLayout.setVerticalGroup(
                adjustForOverlappingBowlCutsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adjustForOverlappingBowlCutsPanelLayout.createSequentialGroup()
                                .addGap(0, 12, Short.MAX_VALUE)
                                .addComponent(adjustForOverlappingBowlCuts, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        labExtrudeTopNSlices.setText("extrudeTopNSlices:");

        inExtrudeTopNSlices.setText("0");
        inExtrudeTopNSlices.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inExtrudeTopNSlicesFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inExtrudeTopNSlicesFocusLost(evt);
            }
        });

        javax.swing.GroupLayout extrudeTopNSlicesPanelLayout = new javax.swing.GroupLayout(extrudeTopNSlicesPanel);
        extrudeTopNSlicesPanel.setLayout(extrudeTopNSlicesPanelLayout);
        extrudeTopNSlicesPanelLayout.setHorizontalGroup(
                extrudeTopNSlicesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(extrudeTopNSlicesPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labExtrudeTopNSlices)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inExtrudeTopNSlices, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        extrudeTopNSlicesPanelLayout.setVerticalGroup(
                extrudeTopNSlicesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, extrudeTopNSlicesPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(extrudeTopNSlicesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labExtrudeTopNSlices)
                                        .addComponent(inExtrudeTopNSlices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        adjustForOverlappingPipeCuts.setText("adjustForOverlappingPipeCuts");
        adjustForOverlappingPipeCuts.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        adjustForOverlappingPipeCuts.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        adjustForOverlappingPipeCuts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adjustForOverlappingPipeCutsActionPerformed(evt);
            }
        });
        adjustForOverlappingPipeCuts.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                adjustForOverlappingPipeCutsFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                adjustForOverlappingPipeCutsFocusLost(evt);
            }
        });

        javax.swing.GroupLayout adjustForOverlappingPipeCutsPanelLayout = new javax.swing.GroupLayout(adjustForOverlappingPipeCutsPanel);
        adjustForOverlappingPipeCutsPanel.setLayout(adjustForOverlappingPipeCutsPanelLayout);
        adjustForOverlappingPipeCutsPanelLayout.setHorizontalGroup(
                adjustForOverlappingPipeCutsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adjustForOverlappingPipeCutsPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(adjustForOverlappingPipeCuts)
                                .addContainerGap())
        );
        adjustForOverlappingPipeCutsPanelLayout.setVerticalGroup(
                adjustForOverlappingPipeCutsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adjustForOverlappingPipeCutsPanelLayout.createSequentialGroup()
                                .addGap(0, 10, Short.MAX_VALUE)
                                .addComponent(adjustForOverlappingPipeCuts, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout valuesPanelLayout = new javax.swing.GroupLayout(valuesPanel);
        valuesPanel.setLayout(valuesPanelLayout);
        valuesPanelLayout.setHorizontalGroup(
                valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(valuesPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(radiusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(slicesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(crossSectionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lengthPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(extrudeTopNSlicesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(extrudeRadiusFactorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(adjustForOverlappingPipeCutsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(adjustForOverlappingBowlCutsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        valuesPanelLayout.setVerticalGroup(
                valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(valuesPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(radiusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slicesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(crossSectionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lengthPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(extrudeTopNSlicesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(extrudeRadiusFactorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(adjustForOverlappingPipeCutsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(adjustForOverlappingBowlCutsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout viewPanelLayout = new javax.swing.GroupLayout(viewPanel);
        viewPanel.setLayout(viewPanelLayout);
        viewPanelLayout.setHorizontalGroup(
                viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(viewPanelLayout.createSequentialGroup()
                                                .addComponent(optionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(62, 62, 62)
                                                .addComponent(iconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(statusPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(subtitlePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(viewPanelLayout.createSequentialGroup()
                                                        .addComponent(valuesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap())
        );
        viewPanelLayout.setVerticalGroup(
                viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(viewPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(subtitlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(valuesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(iconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(optionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(statusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        scrollPane.setViewportView(viewPanel);

        calculator.setText("Calculator");
        calculator.setToolTipText("Calculates the extrudeRadiusFactor for a given lip offset");

        calculateExtrudeRadiusFactor.setText("Calculate the extrudeRadiusFactor");
        calculateExtrudeRadiusFactor.setToolTipText("extrudeTopNSlices must be positive to use the calculator");
        calculateExtrudeRadiusFactor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateExtrudeRadiusFactorActionPerformed(evt);
            }
        });
        calculator.add(calculateExtrudeRadiusFactor);

        menuBar.add(calculator);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(scrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(scrollPane)
                                .addGap(0, 0, 0))
        );

        pack();
    }

    private void inRadiusFocusGained(java.awt.event.FocusEvent evt) {
        radiusPanel.setBackground(shaded);
        descriptionTitle.setText(radiusTitle);
        description.setText(radiusDescription);
    }

    private void inRadiusFocusLost(java.awt.event.FocusEvent evt) {
        checkGenerateOK();
    }

    private void radiusPanelFocusGained(java.awt.event.FocusEvent evt) {
    }

    private void inCrossSectionFocusGained(java.awt.event.FocusEvent evt) {
        crossSectionPanel.setBackground(shaded);
        descriptionTitle.setText(crossSectionTitle);
        description.setText(crossSectionDescription);
    }

    private void inCrossSectionFocusLost(java.awt.event.FocusEvent evt) {
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

    private void inLengthFocusGained(java.awt.event.FocusEvent evt) {
        lengthPanel.setBackground(shaded);
        descriptionTitle.setText(lengthTitle);
        description.setText(lengthDescription);
    }

    private void inLengthFocusLost(java.awt.event.FocusEvent evt) {
        checkGenerateOK();
    }

    private void adjustForOverlappingPipeCutsFocusGained(java.awt.event.FocusEvent evt) {
        adjustForOverlappingPipeCutsPanel.setBackground(shaded);
        descriptionTitle.setText(adjustForOverlappingPipeCutsTitle);
        description.setText(adjustForOverlappingPipeCutsDescription);
    }

    private void adjustForOverlappingPipeCutsFocusLost(java.awt.event.FocusEvent evt) {
        adjustForOverlappingPipeCutsPanel.setBackground(normal);
    }

    private void inExtrudeRadiusFactorFocusGained(java.awt.event.FocusEvent evt) {
        extrudeRadiusFactorPanel.setBackground(shaded);
        descriptionTitle.setText(extrudeRadiusFactorTitle);
        description.setText(extrudeRadiusFactorDescription);
    }

    private void inExtrudeRadiusFactorFocusLost(java.awt.event.FocusEvent evt) {
        checkGenerateOK();
    }

    private void generateActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        generate.setEnabled(false);

        if (opNormal.isSelected() && opOverlapping.isSelected()) {
            status.setText("Generating pipe...");
            String[] args = {inRadius.getText(), inSlices.getText(), inCrossSection.getText(), inLength.getText(), Boolean.toString(!opOverlapping.isSelected()), inExtrudeTopNSlices.getText(), inExtrudeRadiusFactor.getText(), Boolean.toString(adjustForOverlappingPipeCuts.isSelected()), Boolean.toString(adjustForOverlappingBowlCuts.isSelected())};
            String[] args2 = {inRadius.getText(), inSlices.getText(), inCrossSection.getText(), inLength.getText(), Boolean.toString(opOverlapping.isSelected()), inExtrudeTopNSlices.getText(), inExtrudeRadiusFactor.getText(), Boolean.toString(adjustForOverlappingPipeCuts.isSelected()), Boolean.toString(adjustForOverlappingBowlCuts.isSelected())};
            PipeDoubleGen2.main(args, args2);
        } else if ((opNormal.isSelected() || opOverlapping.isSelected()) && !(opCylinderShingles.isSelected() || opSquareRampShingles.isSelected() || opTriRampShingles.isSelected())) {
            status.setText("Generating pipe...");
            String[] args = {inRadius.getText(), inSlices.getText(), inCrossSection.getText(), inLength.getText(), Boolean.toString(opOverlapping.isSelected()), inExtrudeTopNSlices.getText(), inExtrudeRadiusFactor.getText(), Boolean.toString(adjustForOverlappingPipeCuts.isSelected()), Boolean.toString(adjustForOverlappingBowlCuts.isSelected())};
            PipeGenerator.main(args);
        } else if (!(opNormal.isSelected() || opOverlapping.isSelected()) && (opCylinderShingles.isSelected() || opSquareRampShingles.isSelected() || opTriRampShingles.isSelected())) {
            int shingles = 0;

            if (opCylinderShingles.isSelected())
                shingles = SHINGLES_TYPE_CYL;
            if (opSquareRampShingles.isSelected())
                shingles = SHINGLES_TYPE_RAMP_SQTOP;
            if (opTriRampShingles.isSelected())
                shingles = SHINGLES_TYPE_RAMP_TRITOP;

            String[] args = {inRadius.getText(), inSlices.getText(), inLength.getText(), inExtrudeTopNSlices.getText(), inExtrudeRadiusFactor.getText(), Boolean.toString(adjustForOverlappingPipeCuts.isSelected()), Boolean.toString(adjustForOverlappingBowlCuts.isSelected()), Integer.toString(shingles)};
            status.setText("Generating shingles...");
            PipeShinglesGenerator.main(args);
        } else if ((opNormal.isSelected() || opOverlapping.isSelected()) && (opCylinderShingles.isSelected() || opSquareRampShingles.isSelected() || opTriRampShingles.isSelected())) {
            status.setText("Generating pipe and shingles...");

            String[] args = {inRadius.getText(), inSlices.getText(), inCrossSection.getText(), inLength.getText(), Boolean.toString(opOverlapping.isSelected()), inExtrudeTopNSlices.getText(), inExtrudeRadiusFactor.getText(), Boolean.toString(adjustForOverlappingPipeCuts.isSelected()), Boolean.toString(adjustForOverlappingBowlCuts.isSelected())};
            int shingles = 0;

            if (opCylinderShingles.isSelected())
                shingles = SHINGLES_TYPE_CYL;
            if (opSquareRampShingles.isSelected())
                shingles = SHINGLES_TYPE_RAMP_SQTOP;
            if (opTriRampShingles.isSelected())
                shingles = SHINGLES_TYPE_RAMP_TRITOP;

            String[] args2 = {inRadius.getText(), inSlices.getText(), inLength.getText(), inExtrudeTopNSlices.getText(), inExtrudeRadiusFactor.getText(), Boolean.toString(adjustForOverlappingPipeCuts.isSelected()), Boolean.toString(adjustForOverlappingBowlCuts.isSelected()), Integer.toString(shingles)};
            PipeDoubleGen.main(args, args2);
        } else {
            status.setText("Nothing to generate");
            generate.setEnabled(true);
            return;
        }

        status.setText("Generated - select save file name and location");

        File saveFile = UlbenUtils.getSaveFile(this, "PIPE");
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

    private void adjustForOverlappingPipeCutsActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void opTriRampShinglesFocusLost(java.awt.event.FocusEvent evt) {
        triShinglesOptionPanel.setBackground(normal);
    }

    private void opTriRampShinglesFocusGained(java.awt.event.FocusEvent evt) {
        checkGenerateOK();
        triShinglesOptionPanel.setBackground(shaded);
        descriptionTitle.setText(opTriRampShinglesTitle);
        description.setText(opTriRampShinglesDescription);
    }

    private void opTriRampShinglesActionPerformed(java.awt.event.ActionEvent evt) {
        checkGenerateOK();
    }

    private void opSquareRampShinglesFocusLost(java.awt.event.FocusEvent evt) {
        squareShinglesOptionPanel.setBackground(normal);
        checkGenerateOK();
    }

    private void opSquareRampShinglesFocusGained(java.awt.event.FocusEvent evt) {
        squareShinglesOptionPanel.setBackground(shaded);
        descriptionTitle.setText(opSquareRampShinglesTitle);
        description.setText(opSquareRampShinglesDescription);
    }

    private void opSquareRampShinglesActionPerformed(java.awt.event.ActionEvent evt) {
        checkGenerateOK();
    }

    private void opCylinderShinglesFocusLost(java.awt.event.FocusEvent evt) {
        cylinderShinglesOptionPanel.setBackground(normal);
        checkGenerateOK();
    }

    private void opCylinderShinglesFocusGained(java.awt.event.FocusEvent evt) {
        cylinderShinglesOptionPanel.setBackground(shaded);
        descriptionTitle.setText(opCylinderShinglesTitle);
        description.setText(opCylinderShinglesDescription);
    }

    private void opCylinderShinglesActionPerformed(java.awt.event.ActionEvent evt) {
        checkGenerateOK();
    }

    private void opOverlappingFocusLost(java.awt.event.FocusEvent evt) {
        overlappingBrushOptionPanel.setBackground(normal);
        checkGenerateOK();
    }

    private void opOverlappingFocusGained(java.awt.event.FocusEvent evt) {
        overlappingBrushOptionPanel.setBackground(shaded);
        descriptionTitle.setText(opOverlappingTitle);
        description.setText(opOverlappingDescription);
    }

    private void opOverlappingActionPerformed(java.awt.event.ActionEvent evt) {
        checkGenerateOK();
    }

    private void normalOptionPanelFocusLost(java.awt.event.FocusEvent evt) {
    }

    private void opNormalFocusLost(java.awt.event.FocusEvent evt) {
        normalOptionPanel.setBackground(normal);
        checkGenerateOK();
    }

    private void opNormalFocusGained(java.awt.event.FocusEvent evt) {
        checkGenerateOK();
        normalOptionPanel.setBackground(shaded);
        descriptionTitle.setText(opNormalTitle);
        description.setText(opNormalDescription);
    }

    private void opNormalActionPerformed(java.awt.event.ActionEvent evt) {
        checkGenerateOK();
    }

    private void inExtrudeTopNSlicesFocusGained(java.awt.event.FocusEvent evt) {
        extrudeTopNSlicesPanel.setBackground(shaded);
        descriptionTitle.setText(extrudeTopNSlicesTitle);
        description.setText(extrudeTopNSlicesDescription);
    }

    private void inExtrudeTopNSlicesFocusLost(java.awt.event.FocusEvent evt) {
        checkGenerateOK();
    }

    private void adjustForOverlappingBowlCutsFocusGained(java.awt.event.FocusEvent evt) {
        adjustForOverlappingBowlCutsPanel.setBackground(shaded);
        descriptionTitle.setText(adjustForOverlappingBowlCutsTitle);
        description.setText(adjustForOverlappingBowlCutsDescription);
    }

    private void adjustForOverlappingBowlCutsFocusLost(java.awt.event.FocusEvent evt) {
        adjustForOverlappingBowlCutsPanel.setBackground(normal);
    }

    private void calculateExtrudeRadiusFactorActionPerformed(java.awt.event.ActionEvent evt) {
        calculateExtrudeRadiusPopup.setVisible(true);
    }

    private void inLipOffsetFocusLost(java.awt.event.FocusEvent evt) {

        status.setText("");

        try {
            int temp = Integer.parseInt(inLipOffset.getText());

            if (temp < 0)
                throw new Exception("lipOffset cannot be negative");

            calculatorOptionPanel.setBackground(normal);
            calculatorStatusPanel.setBackground(normal);
            calculate.setEnabled(true);
        } catch (Exception ex) {
            calculate.setEnabled(false);

            if (ex instanceof NumberFormatException)
                calculatorStatus.setText("Please enter a number");
            else
                calculatorStatus.setText(ex.getMessage());

            calculatorOptionPanel.setBackground(Color.red);
            calculatorStatusPanel.setBackground(Color.yellow);
        }
    }

    private void inLipOffsetPropertyChange(java.beans.PropertyChangeEvent evt) {
    }

    private void calculateActionPerformed(java.awt.event.ActionEvent evt) {
        String[] args = {inRadius.getText(), inSlices.getText(), inExtrudeTopNSlices.getText(), inLipOffset.getText()};
        inExtrudeRadiusFactor.setText(Double.toString(ExtrudeRadiusFactorCalculator.calculate(args)));
        calculateExtrudeRadiusPopup.setVisible(false);
    }

    private void inLipOffsetKeyPressed(java.awt.event.KeyEvent evt) {
    }

    private void inLipOffsetKeyTyped(java.awt.event.KeyEvent evt) {
    }


    private void checkGenerateOK() {
        boolean generateOK = true;
        boolean calculateOK = true;
        statusPanel.setBackground(normal);
        status.setText("");
        String statusText = "";

        try {
            int temp = Integer.parseInt(inRadius.getText());

            if (temp <= 0)
                throw new Exception("outerRadius must be positive");

            radiusPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            calculateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number\n";
            else
                statusText += ex.getMessage() + "\n";
            radiusPanel.setBackground(Color.red);
        }

        try {
            int temp = Integer.parseInt(inSlices.getText());

            if (temp < 1)
                throw new Exception("Min slices is 1");

            slicesPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            calculateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number\n";
            else
                statusText += ex.getMessage() + "\n";
            slicesPanel.setBackground(Color.red);
        }

        try {
            int temp = Integer.parseInt(inLength.getText());

            if (temp <= 1)
                throw new Exception("Min length is 1");

            lengthPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number\n";
            else
                statusText += ex.getMessage() + "\n";
            lengthPanel.setBackground(Color.red);
        }

        try {
            int temp = Integer.parseInt(inExtrudeTopNSlices.getText());


            if (temp <= 0) {
                calculateOK = false;
                labExtrudeRadiusFactor.setEnabled(false);
                inExtrudeRadiusFactor.setEnabled(false);
            } else {
                labExtrudeRadiusFactor.setEnabled(true);
                inExtrudeRadiusFactor.setEnabled(true);
            }

            if (temp < 0)
                throw new Exception("ExtrudeTopNSlices cannot be negative");

            extrudeTopNSlicesPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            calculateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number\n";
            else
                statusText += ex.getMessage() + "\n";
            extrudeTopNSlicesPanel.setBackground(Color.red);
        }

        try {
            double temp = Double.parseDouble(inExtrudeRadiusFactor.getText());

            if (temp <= 0)
                throw new Exception("Please enter a positive number");

            extrudeRadiusFactorPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            calculateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number\n";
            else
                statusText += ex.getMessage() + "\n";
            extrudeRadiusFactorPanel.setBackground(Color.red);
        }

        if ((opTriRampShingles.isSelected() || opSquareRampShingles.isSelected() || opCylinderShingles.isSelected()) && !opNormal.isSelected()) {
            labCrossSection.setEnabled(false);
            inCrossSection.setEnabled(false);
        } else {
            labCrossSection.setEnabled(true);
            inCrossSection.setEnabled(true);
            try {
                int temp = Integer.parseInt(inCrossSection.getText());

                if (temp <= 0)
                    throw new Exception("inCrossSection cannot be negative");

                crossSectionPanel.setBackground(normal);
            } catch (Exception ex) {
                generateOK = false;
                statusPanel.setBackground(Color.yellow);
                if (ex instanceof NumberFormatException)
                    statusText += "Please enter a number\n";
                else
                    statusText += ex.getMessage() + "\n";
                crossSectionPanel.setBackground(Color.red);
            }
        }

        if (calculateOK) {
            calculateExtrudeRadiusFactor.setEnabled(true);
        } else {
            calculateExtrudeRadiusFactor.setEnabled(false);
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
            java.util.logging.Logger.getLogger(PipeGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PipeGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PipeGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PipeGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PipeGeneratorGUI(new Config()).setVisible(true);
            }
        });
    }


    private javax.swing.JCheckBox adjustForOverlappingBowlCuts;
    private javax.swing.JPanel adjustForOverlappingBowlCutsPanel;
    private javax.swing.JCheckBox adjustForOverlappingPipeCuts;
    private javax.swing.JPanel adjustForOverlappingPipeCutsPanel;
    private javax.swing.JButton calculate;
    private javax.swing.JMenuItem calculateExtrudeRadiusFactor;
    private javax.swing.JDialog calculateExtrudeRadiusPopup;
    private javax.swing.JPanel calculatePanel;
    private javax.swing.JMenu calculator;
    private javax.swing.JTextArea calculatorDescription;
    private javax.swing.JPanel calculatorDescriptionPanel;
    private javax.swing.JPanel calculatorOptionPanel;
    private javax.swing.JPanel calculatorPanel;
    private javax.swing.JLabel calculatorStatus;
    private javax.swing.JPanel calculatorStatusPanel;
    private javax.swing.JPanel crossSectionPanel;
    private javax.swing.JPanel cylinderShinglesOptionPanel;
    private javax.swing.JTextArea description;
    private javax.swing.JPanel descriptionPanel;
    private javax.swing.JLabel descriptionTitle;
    private javax.swing.JPanel extrudeRadiusFactorPanel;
    private javax.swing.JPanel extrudeTopNSlicesPanel;
    private javax.swing.JButton generate;
    private javax.swing.JLabel icon;
    private javax.swing.JPanel iconPanel;
    private javax.swing.JTextField inCrossSection;
    private javax.swing.JTextField inExtrudeRadiusFactor;
    private javax.swing.JTextField inExtrudeTopNSlices;
    private javax.swing.JTextField inLength;
    private javax.swing.JTextField inLipOffset;
    private javax.swing.JTextField inRadius;
    private javax.swing.JTextField inSlices;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel laLipOffset;
    private javax.swing.JLabel labCrossSection;
    private javax.swing.JLabel labExtrudeRadiusFactor;
    private javax.swing.JLabel labExtrudeTopNSlices;
    private javax.swing.JLabel labLength;
    private javax.swing.JLabel labRadius;
    private javax.swing.JLabel labSlices;
    private javax.swing.JPanel lengthPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel normalOptionPanel;
    private javax.swing.JCheckBox opCylinderShingles;
    private javax.swing.JCheckBox opNormal;
    private javax.swing.JCheckBox opOverlapping;
    private javax.swing.JCheckBox opSquareRampShingles;
    private javax.swing.JCheckBox opTriRampShingles;
    private javax.swing.JPanel optionsPanel;
    private javax.swing.JPanel overlappingBrushOptionPanel;
    private javax.swing.JPanel radiusPanel;
    private javax.swing.JScrollPane scrollPane;
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
