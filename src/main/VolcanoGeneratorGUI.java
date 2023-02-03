
package main;

import generators.ExtrudeRadiusFactorCalculator;
import generators.OverlappingVolcanoGenerator;
import generators.VolcanoGenerator;
import tools.MapFactory;
import ulben.UlbenUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;


public class VolcanoGeneratorGUI extends javax.swing.JFrame {

    private Config configuration;

    private Color shaded = new Color(200, 200, 200);
    private Color normal = this.getBackground();

    private String topCircumferenceRadiusTitle = "topCircumferenceRadius";
    private String topCircumferenceRadiusDescription = "The radius of the circumference at the top of the volcano. You probably want to make this value significantly greater than zero.";

    private String slopeRadiusTitle = "slopeRadius";
    private String slopeRadiusDescription = "The radius of the upward slope of the volcano.";

    private String slicesCircumferenceTitle = "slicesCirumference";
    private String slicesCircumferenceDescription = "How many slices to chop the quarter circumference of the volcano into. The total number of triangular surfaces making up this volcano will be 2 * slicesCircumference * slicesSlope.";

    private String slicesSlopeTitle = "slicesSlope";
    private String slicesSlopeDescription = "How many slices to chop the vertical slope of the volcano into. The total number of triangular surfaces making up this volcano will be 2 * slicesCircumference * slicesSlope. ";

    private String crossSectionTitle = "crossSection";
    private String crossSectionDescription = "The distance from the top of the volcano to the bottom of the brushes extending downwards from the volcano.";

    private String offsetTopInsteadOfBottomTitle = "offsetTopInsteadOfBottom";
    private String offsetTopInsteadOfBottomDescription = "Alters the pattern of the mesh by flipping the even-odd rule in which vertices get rotated by half a slice along the circumference. This is useful when joining other offset objects to the circumference of the volcano, such as offset cylinders or funnels. ";

    private String extrudeTopNSlicesTitle = "extrudeTopNSlices";
    private String extrudeTopNSlicesDescription = "f this is greater than zero and if extrudeRadiusFactor is greater than one, the topmost extrudeTopNSlices vertices of the volcano will be pushed back so that the top of the volcano is no longer vertical. It \"relaxes\" the top of the volcano to have a less steep slope. ";

    private String extrudeRadiusFactorTitle = "Extrude Radius Factor";
    private String extrudeRadiusFactorDescription = "Used together with extrudeTopNSlices. This value should be greater than one and extrudeTopNSlices should be greater than zero to make the top of the volcano less steep. It is possible to use values slightly less than one here to generate an \"oversteep\" top, but if used improperly, poor results will be generated, or [possibly] nothing will be generated at all. A value of 1.0 corresponds to a normal volcano with perfectly vertical top. Otherwise, the top vertices are generated based on a radius of slopeRadius * extrudeRadiusFactor.\n\nYou can use the calculator to calculate the extrudeRadiusFactor. ";

    private String adjustForOverlappingPipeCutsTitle = "Adjust For Overlapping Pipe Cuts";
    private String adjustForOverlappingPipeCutsDescription = "n the case where pipe brushes are joined at the seam of this volcano, set this flag whenever the pipe brushes are generated with adjustForOverlappingPipeCuts also.";

    private String adjustForOverlappingBowlCutsTitle = "Adjust For Overlapping Bowl Cuts";
    private String adjustForOverlappingBowlCutsDescription = "Use this when joining other structures that have this flag set.";

    private String invertSlopeVerticesTitle = "Invert Slope Vertices";
    private String invertSlopeVerticesDescription = "The original vertices that make up the slope of the volcano are flipped after all of the adjust and/or extrude flags are applied; the vertex at the rim of the volcano becomes the vertex at the bottom and vice-versa. So, for example, if we are extruding vertices and using this flag together, the bottom of the volcano will no longer be horizontal. ";

    private String generateOnlySeamTitle = "generateOnlySeam";
    private String generateOnlySeamDescription = "Generates only the seam brushes.";

    private String adjustSeamForJoiningTitle = "adjustSeamForJoining";
    private String adjustSeamForJoiningDescription = "Perturb the vertices of the seam brushes so that other structures such as pipes can be joined perfectly to this quarter volcano.";

    private String cutTopTitle = "cutTop";
    private String cutTopDescription = "Automatically cuts the volcano at the top to make a flat top. The brushes at the top are constructed so that they don't extend beyond the top. You can only be sure that no brushes extend beyond the top if adjustForOverlappingPipeCuts is true. (I also think that if you set invertSlopeVertices, you cannot guarantee that no brush will extend beyond the top.)";

    private String cutBottomTitle = "cutBottom";
    private String cutBottomDescription = "The brushes at the bottom are cut. I believe that chances are pretty good that some 2nd-level brushes will happen to extend just a tad beyond.";

    private String opNormalTitle = "Generate normal non-overlapping brushes";
    private String opNormalDescription = "Generates ordinary brushes. To make a bowl wall to fling you around at high speeds, you will have to generate overlapping brushes in order to prevent \"bouncing\". Another purpose for normal brushes is to define the visual component of a structure, for example by using surfaceparm nonsolid.";

    private String opOverlappingTitle = "Generate overlapping brushes";
    private String opOverlappingDescription = "Overlapping brushes are useful for making bowls such that the player doesn't \"bounce\" off the surface when flying along the bowl. However, it is not advised to texture the overlapping brush faces with drawn textures. Instead, apply [slick] caulk to all of the overlapping brushes, then superimpose a set of normal non-overlapping brushes that have surfaceparm nonsolid. NOTE: When you are done generating overlapping brushes, you will likely want to cut everything off that is above z = 0. Use adjustForOverlappingBowlCuts to ensure that the resulting cuts are clean and along integer coordinates. ";


    public VolcanoGeneratorGUI(Config configuration) {
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
        descriptionPanel = new javax.swing.JPanel();
        descriptionScrollPane = new javax.swing.JScrollPane();
        description = new javax.swing.JTextArea();
        descriptionTitle = new javax.swing.JLabel();
        iconPanel = new javax.swing.JPanel();
        icon = new javax.swing.JLabel();
        subtitlePanel = new javax.swing.JPanel();
        subtitle = new javax.swing.JLabel();
        optionsPanel = new javax.swing.JPanel();
        normalOptionPanel = new javax.swing.JPanel();
        opNormal = new javax.swing.JRadioButton();
        overlappingBrushOptionPanel = new javax.swing.JPanel();
        opOverlapping = new javax.swing.JRadioButton();
        statusPanel = new javax.swing.JPanel();
        generate = new javax.swing.JButton();
        statusScrollPane = new javax.swing.JScrollPane();
        status = new javax.swing.JTextArea();
        valuesPanel = new javax.swing.JPanel();
        topCircumferenceRadiusPanel = new javax.swing.JPanel();
        labTopCircumferenceRadius = new javax.swing.JLabel();
        inTopCircumferenceRadius = new javax.swing.JTextField();
        crossSectionPanel = new javax.swing.JPanel();
        labCrossSection = new javax.swing.JLabel();
        inCrossSection = new javax.swing.JTextField();
        slicesCircumferencePanel = new javax.swing.JPanel();
        labSlicesCircumference = new javax.swing.JLabel();
        inSlicesCirumference = new javax.swing.JTextField();
        slicesSlopePanel = new javax.swing.JPanel();
        labSlicesSlope = new javax.swing.JLabel();
        inSlicesSlope = new javax.swing.JTextField();
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
        slopeRadiusPanel = new javax.swing.JPanel();
        labSlopeRadius = new javax.swing.JLabel();
        inSlopeRadius = new javax.swing.JTextField();
        offsetTopInsteadOfBottomPanel = new javax.swing.JPanel();
        offsetTopInsteadOfBottom = new javax.swing.JCheckBox();
        adjustSeamForJoiningPanel = new javax.swing.JPanel();
        adjustSeamForJoining = new javax.swing.JCheckBox();
        generateOnlySeamPanel = new javax.swing.JPanel();
        generateOnlySeam = new javax.swing.JCheckBox();
        invertSlopeVerticesPanel = new javax.swing.JPanel();
        invertSlopeVertices = new javax.swing.JCheckBox();
        cutTopPanel = new javax.swing.JPanel();
        cutTop = new javax.swing.JCheckBox();
        cutBottomPanel = new javax.swing.JPanel();
        cutBottom = new javax.swing.JCheckBox();
        menuBar = new javax.swing.JMenuBar();
        calculator = new javax.swing.JMenu();
        calculateExtrudeRadiusFactor = new javax.swing.JMenuItem();

        calculateExtrudeRadiusPopup.setTitle("Extrude Radius Factor Calculator");
        calculateExtrudeRadiusPopup.setMinimumSize(new java.awt.Dimension(330, 372));

        laLipOffset.setFont(new java.awt.Font("Tahoma", 0, 12));
        laLipOffset.setText("lipOffset:");

        inLipOffset.setFont(new java.awt.Font("Tahoma", 0, 12));
        inLipOffset.setText("5");
        inLipOffset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inLipOffsetActionPerformed(evt);
            }
        });
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inLipOffsetKeyTyped(evt);
            }

            public void keyPressed(java.awt.event.KeyEvent evt) {
                inLipOffsetKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout calculatorOptionPanelLayout = new javax.swing.GroupLayout(calculatorOptionPanel);
        calculatorOptionPanel.setLayout(calculatorOptionPanelLayout);
        calculatorOptionPanelLayout.setHorizontalGroup(
                calculatorOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(calculatorOptionPanelLayout.createSequentialGroup()
                                .addContainerGap(18, Short.MAX_VALUE)
                                .addComponent(laLipOffset)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inLipOffset, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        calculatorOptionPanelLayout.setVerticalGroup(
                calculatorOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, calculatorOptionPanelLayout.createSequentialGroup()
                                .addGap(0, 25, Short.MAX_VALUE)
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
                                .addComponent(calculatorStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
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
        calculatorDescription.setText("Calculates the extrudeRadiusFactor based on the values of slopeRadius, slicesSlope and topNSlices.\n\nThe lipOffset is the number of units the top edge of the circular arc is to be nudged outward.\n\nNote that specifying a lipOffset of greater than what is possible to extrude (by setting a radius to be very large)  will generate erroneous results. ");
        calculatorDescription.setWrapStyleWord(true);

        javax.swing.GroupLayout calculatorDescriptionPanelLayout = new javax.swing.GroupLayout(calculatorDescriptionPanel);
        calculatorDescriptionPanel.setLayout(calculatorDescriptionPanelLayout);
        calculatorDescriptionPanelLayout.setHorizontalGroup(
                calculatorDescriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(calculatorDescriptionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(calculatorDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        calculatorDescriptionPanelLayout.setVerticalGroup(
                calculatorDescriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, calculatorDescriptionPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(calculatorDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                                        .addComponent(calculatorStatusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(calculatorPanelLayout.createSequentialGroup()
                                                                .addComponent(calculatorOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(calculatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                                        .addComponent(calculatorOptionPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(calculatePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(calculatorStatusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        setTitle("Volcano Generator");
        setLocationByPlatform(true);

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
                                .addComponent(descriptionTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, descriptionPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(descriptionScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26))
        );
        descriptionPanelLayout.setVerticalGroup(
                descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, descriptionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(descriptionTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(descriptionScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                                .addContainerGap())
        );

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/volcano.png")));
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

        subtitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        subtitle.setText("Generates a quarter of a volcano shape, which is actually the inner rim of the inside of a donut.");

        javax.swing.GroupLayout subtitlePanelLayout = new javax.swing.GroupLayout(subtitlePanel);
        subtitlePanel.setLayout(subtitlePanelLayout);
        subtitlePanelLayout.setHorizontalGroup(
                subtitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(subtitlePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(subtitle, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE))
        );
        subtitlePanelLayout.setVerticalGroup(
                subtitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(subtitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        options.add(opOverlapping);
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
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionsPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(overlappingBrushOptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(normalOptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        optionsPanelLayout.setVerticalGroup(
                optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(optionsPanelLayout.createSequentialGroup()
                                .addContainerGap()
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(generate)
                                .addGap(18, 18, 18))
        );
        statusPanelLayout.setVerticalGroup(
                statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(statusPanelLayout.createSequentialGroup()
                                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(statusPanelLayout.createSequentialGroup()
                                                .addGap(24, 24, 24)
                                                .addComponent(generate))
                                        .addGroup(statusPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(statusScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        topCircumferenceRadiusPanel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                topCircumferenceRadiusPanelFocusGained(evt);
            }
        });

        labTopCircumferenceRadius.setLabelFor(inTopCircumferenceRadius);
        labTopCircumferenceRadius.setText("topCircumferenceRadius:");

        inTopCircumferenceRadius.setText("384");
        inTopCircumferenceRadius.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inTopCircumferenceRadiusActionPerformed(evt);
            }
        });
        inTopCircumferenceRadius.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inTopCircumferenceRadiusFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inTopCircumferenceRadiusFocusLost(evt);
            }
        });

        javax.swing.GroupLayout topCircumferenceRadiusPanelLayout = new javax.swing.GroupLayout(topCircumferenceRadiusPanel);
        topCircumferenceRadiusPanel.setLayout(topCircumferenceRadiusPanelLayout);
        topCircumferenceRadiusPanelLayout.setHorizontalGroup(
                topCircumferenceRadiusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(topCircumferenceRadiusPanelLayout.createSequentialGroup()
                                .addContainerGap(70, Short.MAX_VALUE)
                                .addComponent(labTopCircumferenceRadius)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inTopCircumferenceRadius, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        topCircumferenceRadiusPanelLayout.setVerticalGroup(
                topCircumferenceRadiusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topCircumferenceRadiusPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(topCircumferenceRadiusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labTopCircumferenceRadius)
                                        .addComponent(inTopCircumferenceRadius, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        labCrossSection.setLabelFor(inCrossSection);
        labCrossSection.setText("crossSection:");

        inCrossSection.setText("640");
        inCrossSection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inCrossSectionActionPerformed(evt);
            }
        });
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

        labSlicesCircumference.setLabelFor(inSlicesCirumference);
        labSlicesCircumference.setText("slicesCircumference:");

        inSlicesCirumference.setText("14");
        inSlicesCirumference.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inSlicesCirumferenceActionPerformed(evt);
            }
        });
        inSlicesCirumference.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inSlicesCirumferenceFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inSlicesCirumferenceFocusLost(evt);
            }
        });

        javax.swing.GroupLayout slicesCircumferencePanelLayout = new javax.swing.GroupLayout(slicesCircumferencePanel);
        slicesCircumferencePanel.setLayout(slicesCircumferencePanelLayout);
        slicesCircumferencePanelLayout.setHorizontalGroup(
                slicesCircumferencePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(slicesCircumferencePanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labSlicesCircumference)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inSlicesCirumference, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        slicesCircumferencePanelLayout.setVerticalGroup(
                slicesCircumferencePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slicesCircumferencePanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(slicesCircumferencePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labSlicesCircumference)
                                        .addComponent(inSlicesCirumference, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        labSlicesSlope.setLabelFor(inSlicesSlope);
        labSlicesSlope.setText("slicesSlope:");

        inSlicesSlope.setText("14");
        inSlicesSlope.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inSlicesSlopeActionPerformed(evt);
            }
        });
        inSlicesSlope.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inSlicesSlopeFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inSlicesSlopeFocusLost(evt);
            }
        });

        javax.swing.GroupLayout slicesSlopePanelLayout = new javax.swing.GroupLayout(slicesSlopePanel);
        slicesSlopePanel.setLayout(slicesSlopePanelLayout);
        slicesSlopePanelLayout.setHorizontalGroup(
                slicesSlopePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(slicesSlopePanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labSlicesSlope)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inSlicesSlope, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        slicesSlopePanelLayout.setVerticalGroup(
                slicesSlopePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(slicesSlopePanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(slicesSlopePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labSlicesSlope)
                                        .addComponent(inSlicesSlope, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        labExtrudeRadiusFactor.setLabelFor(inExtrudeRadiusFactor);
        labExtrudeRadiusFactor.setText("extrudeRadiusFactor:");
        labExtrudeRadiusFactor.setEnabled(false);

        inExtrudeRadiusFactor.setText("1.0");
        inExtrudeRadiusFactor.setEnabled(false);
        inExtrudeRadiusFactor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inExtrudeRadiusFactorActionPerformed(evt);
            }
        });
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
                                .addContainerGap()
                                .addComponent(labExtrudeRadiusFactor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(inExtrudeRadiusFactor, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGroup(adjustForOverlappingBowlCutsPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(adjustForOverlappingBowlCuts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        inExtrudeTopNSlices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inExtrudeTopNSlicesActionPerformed(evt);
            }
        });
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

        slopeRadiusPanel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                slopeRadiusPanelFocusGained(evt);
            }
        });

        labSlopeRadius.setLabelFor(inTopCircumferenceRadius);
        labSlopeRadius.setText("slopeRadius:");

        inSlopeRadius.setText("512");
        inSlopeRadius.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inSlopeRadiusActionPerformed(evt);
            }
        });
        inSlopeRadius.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inSlopeRadiusFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inSlopeRadiusFocusLost(evt);
            }
        });

        javax.swing.GroupLayout slopeRadiusPanelLayout = new javax.swing.GroupLayout(slopeRadiusPanel);
        slopeRadiusPanel.setLayout(slopeRadiusPanelLayout);
        slopeRadiusPanelLayout.setHorizontalGroup(
                slopeRadiusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(slopeRadiusPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labSlopeRadius)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inSlopeRadius, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        slopeRadiusPanelLayout.setVerticalGroup(
                slopeRadiusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slopeRadiusPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(slopeRadiusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labSlopeRadius)
                                        .addComponent(inSlopeRadius, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        offsetTopInsteadOfBottom.setText("offsetTopInsteadOfBottom");
        offsetTopInsteadOfBottom.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        offsetTopInsteadOfBottom.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        offsetTopInsteadOfBottom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                offsetTopInsteadOfBottomFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                offsetTopInsteadOfBottomFocusLost(evt);
            }
        });

        javax.swing.GroupLayout offsetTopInsteadOfBottomPanelLayout = new javax.swing.GroupLayout(offsetTopInsteadOfBottomPanel);
        offsetTopInsteadOfBottomPanel.setLayout(offsetTopInsteadOfBottomPanelLayout);
        offsetTopInsteadOfBottomPanelLayout.setHorizontalGroup(
                offsetTopInsteadOfBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(offsetTopInsteadOfBottomPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(offsetTopInsteadOfBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        offsetTopInsteadOfBottomPanelLayout.setVerticalGroup(
                offsetTopInsteadOfBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(offsetTopInsteadOfBottomPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(offsetTopInsteadOfBottom)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                .addGap(0, 6, Short.MAX_VALUE)
                                .addComponent(adjustSeamForJoining, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        generateOnlySeam.setText("generateOnlySeam");
        generateOnlySeam.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        generateOnlySeam.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        generateOnlySeam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateOnlySeamActionPerformed(evt);
            }
        });
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
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, generateOnlySeamPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(generateOnlySeam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        generateOnlySeamPanelLayout.setVerticalGroup(
                generateOnlySeamPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, generateOnlySeamPanelLayout.createSequentialGroup()
                                .addGap(0, 6, Short.MAX_VALUE)
                                .addComponent(generateOnlySeam, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        invertSlopeVertices.setText("invertSlopeVertices");
        invertSlopeVertices.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        invertSlopeVertices.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        invertSlopeVertices.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                invertSlopeVerticesFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                invertSlopeVerticesFocusLost(evt);
            }
        });

        javax.swing.GroupLayout invertSlopeVerticesPanelLayout = new javax.swing.GroupLayout(invertSlopeVerticesPanel);
        invertSlopeVerticesPanel.setLayout(invertSlopeVerticesPanelLayout);
        invertSlopeVerticesPanelLayout.setHorizontalGroup(
                invertSlopeVerticesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(invertSlopeVerticesPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(invertSlopeVertices, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        invertSlopeVerticesPanelLayout.setVerticalGroup(
                invertSlopeVerticesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, invertSlopeVerticesPanelLayout.createSequentialGroup()
                                .addGap(0, 12, Short.MAX_VALUE)
                                .addComponent(invertSlopeVertices, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        cutTop.setText("cutTop");
        cutTop.setEnabled(false);
        cutTop.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        cutTop.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        cutTop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutTopActionPerformed(evt);
            }
        });
        cutTop.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cutTopFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                cutTopFocusLost(evt);
            }
        });

        javax.swing.GroupLayout cutTopPanelLayout = new javax.swing.GroupLayout(cutTopPanel);
        cutTopPanel.setLayout(cutTopPanelLayout);
        cutTopPanelLayout.setHorizontalGroup(
                cutTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cutTopPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cutTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        cutTopPanelLayout.setVerticalGroup(
                cutTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cutTopPanelLayout.createSequentialGroup()
                                .addGap(0, 6, Short.MAX_VALUE)
                                .addComponent(cutTop, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        cutBottom.setText("cutBottom");
        cutBottom.setEnabled(false);
        cutBottom.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        cutBottom.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        cutBottom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutBottomActionPerformed(evt);
            }
        });
        cutBottom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cutBottomFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                cutBottomFocusLost(evt);
            }
        });

        javax.swing.GroupLayout cutBottomPanelLayout = new javax.swing.GroupLayout(cutBottomPanel);
        cutBottomPanel.setLayout(cutBottomPanelLayout);
        cutBottomPanelLayout.setHorizontalGroup(
                cutBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cutBottomPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cutBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        cutBottomPanelLayout.setVerticalGroup(
                cutBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cutBottomPanelLayout.createSequentialGroup()
                                .addGap(0, 6, Short.MAX_VALUE)
                                .addComponent(cutBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout valuesPanelLayout = new javax.swing.GroupLayout(valuesPanel);
        valuesPanel.setLayout(valuesPanelLayout);
        valuesPanelLayout.setHorizontalGroup(
                valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(valuesPanelLayout.createSequentialGroup()
                                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, valuesPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(offsetTopInsteadOfBottomPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(extrudeTopNSlicesPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(crossSectionPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(slicesSlopePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(slicesCircumferencePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(slopeRadiusPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(topCircumferenceRadiusPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, valuesPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(adjustSeamForJoiningPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(generateOnlySeamPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(valuesPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(cutTopPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(cutBottomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(valuesPanelLayout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(invertSlopeVerticesPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(extrudeRadiusFactorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                                                        .addComponent(adjustForOverlappingBowlCutsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(adjustForOverlappingPipeCutsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addContainerGap())
        );
        valuesPanelLayout.setVerticalGroup(
                valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(valuesPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(topCircumferenceRadiusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slopeRadiusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slicesCircumferencePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slicesSlopePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(crossSectionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(offsetTopInsteadOfBottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(extrudeTopNSlicesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(extrudeRadiusFactorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(adjustForOverlappingPipeCutsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(adjustForOverlappingBowlCutsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(invertSlopeVerticesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(generateOnlySeamPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(adjustSeamForJoiningPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cutTopPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cutBottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout viewPanelLayout = new javax.swing.GroupLayout(viewPanel);
        viewPanel.setLayout(viewPanelLayout);
        viewPanelLayout.setHorizontalGroup(
                viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(viewPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(viewPanelLayout.createSequentialGroup()
                                                .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(valuesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(optionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(viewPanelLayout.createSequentialGroup()
                                                                .addGap(40, 40, 40)
                                                                .addComponent(iconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(44, 44, 44))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewPanelLayout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addComponent(statusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(subtitlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        viewPanelLayout.setVerticalGroup(
                viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(viewPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(subtitlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(viewPanelLayout.createSequentialGroup()
                                                .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(38, 38, 38)
                                                .addComponent(iconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(viewPanelLayout.createSequentialGroup()
                                                .addComponent(valuesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(optionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(statusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        scrollPane.setViewportView(viewPanel);

        calculator.setText("Calculator");
        calculator.setToolTipText("Calculates the extrudeRadiusFactor for a given lip offset");

        calculateExtrudeRadiusFactor.setText("Calculate extrudeRadiusFactor");
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
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void inTopCircumferenceRadiusFocusGained(java.awt.event.FocusEvent evt) {
        topCircumferenceRadiusPanel.setBackground(shaded);
        descriptionTitle.setText(topCircumferenceRadiusTitle);
        description.setText(topCircumferenceRadiusDescription);
    }

    private void inTopCircumferenceRadiusFocusLost(java.awt.event.FocusEvent evt) {
        checkGenerateOK();
    }

    private void topCircumferenceRadiusPanelFocusGained(java.awt.event.FocusEvent evt) {
    }

    private void inCrossSectionFocusGained(java.awt.event.FocusEvent evt) {
        crossSectionPanel.setBackground(shaded);
        descriptionTitle.setText(crossSectionTitle);
        description.setText(crossSectionDescription);
    }

    private void inCrossSectionFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void inSlicesCirumferenceFocusGained(java.awt.event.FocusEvent evt) {

        slicesCircumferencePanel.setBackground(shaded);
        descriptionTitle.setText(slicesCircumferenceTitle);
        description.setText(slicesCircumferenceDescription);
    }

    private void inSlicesCirumferenceFocusLost(java.awt.event.FocusEvent evt) {

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

        if (opNormal.isSelected()) {
            String[] args = {inTopCircumferenceRadius.getText(), inSlopeRadius.getText(), inSlicesCirumference.getText(), inSlicesSlope.getText(), inCrossSection.getText(), Boolean.toString(offsetTopInsteadOfBottom.isSelected()), inExtrudeTopNSlices.getText(), inExtrudeRadiusFactor.getText(), Boolean.toString(adjustForOverlappingPipeCuts.isSelected()), Boolean.toString(adjustForOverlappingBowlCuts.isSelected()), Boolean.toString(invertSlopeVertices.isSelected()), Boolean.toString(generateOnlySeam.isSelected()), Boolean.toString(adjustSeamForJoining.isSelected())};
            status.setText("Generating volcano...");
            VolcanoGenerator.main(args);
        }
        if (opOverlapping.isSelected()) {
            String[] args = {inTopCircumferenceRadius.getText(), inSlopeRadius.getText(), inSlicesCirumference.getText(), inSlicesSlope.getText(), inCrossSection.getText(), Boolean.toString(offsetTopInsteadOfBottom.isSelected()), inExtrudeTopNSlices.getText(), inExtrudeRadiusFactor.getText(), Boolean.toString(adjustForOverlappingPipeCuts.isSelected()), Boolean.toString(adjustForOverlappingBowlCuts.isSelected()), Boolean.toString(invertSlopeVertices.isSelected()), Boolean.toString(generateOnlySeam.isSelected()), Boolean.toString(adjustSeamForJoining.isSelected()), Boolean.toString(cutTop.isSelected()), Boolean.toString(cutBottom.isSelected())};
            status.setText("Generating overlapping volcano...");
            OverlappingVolcanoGenerator.main(args);
        }


        status.setText("Generated - select save file name and location");

        File saveFile = UlbenUtils.getSaveFile(this, "VOLCANO");

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

    private void inSlopeRadiusFocusGained(java.awt.event.FocusEvent evt) {

        slopeRadiusPanel.setBackground(shaded);
        descriptionTitle.setText(slopeRadiusTitle);
        description.setText(slopeRadiusDescription);
    }

    private void inSlopeRadiusFocusLost(java.awt.event.FocusEvent evt) {
        checkGenerateOK();
    }

    private void slopeRadiusPanelFocusGained(java.awt.event.FocusEvent evt) {

    }

    private void adjustSeamForJoiningFocusGained(java.awt.event.FocusEvent evt) {

        adjustSeamForJoiningPanel.setBackground(shaded);
        descriptionTitle.setText(adjustSeamForJoiningTitle);
        description.setText(adjustSeamForJoiningDescription);
    }

    private void adjustSeamForJoiningFocusLost(java.awt.event.FocusEvent evt) {

        adjustSeamForJoiningPanel.setBackground(normal);
    }

    private void generateOnlySeamFocusGained(java.awt.event.FocusEvent evt) {

        generateOnlySeamPanel.setBackground(shaded);
        descriptionTitle.setText(generateOnlySeamTitle);
        description.setText(generateOnlySeamDescription);
    }

    private void generateOnlySeamFocusLost(java.awt.event.FocusEvent evt) {

        generateOnlySeamPanel.setBackground(normal);
    }

    private void inSlicesSlopeFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void inSlicesSlopeFocusGained(java.awt.event.FocusEvent evt) {

        slicesSlopePanel.setBackground(shaded);
        descriptionTitle.setText(slicesSlopeTitle);
        description.setText(slicesSlopeDescription);
    }

    private void generateOnlySeamActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void offsetTopInsteadOfBottomFocusGained(java.awt.event.FocusEvent evt) {

        offsetTopInsteadOfBottomPanel.setBackground(shaded);
        descriptionTitle.setText(offsetTopInsteadOfBottomTitle);
        description.setText(offsetTopInsteadOfBottomDescription);
    }

    private void offsetTopInsteadOfBottomFocusLost(java.awt.event.FocusEvent evt) {

        offsetTopInsteadOfBottomPanel.setBackground(normal);
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

    private void inLipOffsetKeyPressed(java.awt.event.KeyEvent evt) {

    }

    private void inLipOffsetKeyTyped(java.awt.event.KeyEvent evt) {

    }

    private void calculateActionPerformed(java.awt.event.ActionEvent evt) {

        String[] args = {inSlopeRadius.getText(), inSlicesSlope.getText(), inExtrudeTopNSlices.getText(), inLipOffset.getText()};
        inExtrudeRadiusFactor.setText(Double.toString(ExtrudeRadiusFactorCalculator.calculate(args)));
        calculateExtrudeRadiusPopup.setVisible(false);
    }

    private void calculateExtrudeRadiusFactorActionPerformed(java.awt.event.ActionEvent evt) {

        calculateExtrudeRadiusPopup.setVisible(true);
    }

    private void inExtrudeRadiusFactorActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inTopCircumferenceRadiusActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inSlopeRadiusActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inSlicesCirumferenceActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inSlicesSlopeActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inCrossSectionActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inExtrudeTopNSlicesActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inLipOffsetActionPerformed(java.awt.event.ActionEvent evt) {

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

    private void invertSlopeVerticesFocusGained(java.awt.event.FocusEvent evt) {
        invertSlopeVerticesPanel.setBackground(shaded);
        descriptionTitle.setText(invertSlopeVerticesTitle);
        description.setText(invertSlopeVerticesDescription);
    }

    private void invertSlopeVerticesFocusLost(java.awt.event.FocusEvent evt) {
        invertSlopeVerticesPanel.setBackground(normal);
    }

    private void cutTopActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void cutTopFocusGained(java.awt.event.FocusEvent evt) {
        cutTopPanel.setBackground(shaded);
        descriptionTitle.setText(cutTopTitle);
        description.setText(cutTopDescription);
    }

    private void cutTopFocusLost(java.awt.event.FocusEvent evt) {
        cutTopPanel.setBackground(shaded);
    }

    private void cutBottomActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void cutBottomFocusGained(java.awt.event.FocusEvent evt) {
        cutBottomPanel.setBackground(shaded);
        descriptionTitle.setText(cutBottomTitle);
        description.setText(cutBottomDescription);
    }

    private void cutBottomFocusLost(java.awt.event.FocusEvent evt) {
        cutBottomPanel.setBackground(shaded);
    }


    private void checkGenerateOK() {
        boolean generateOK = true;
        boolean calculateOK = true;
        statusPanel.setBackground(normal);
        status.setText("");
        String statusText = "";


        try {
            int temp = Integer.parseInt(inTopCircumferenceRadius.getText());

            if (temp <= 0)
                throw new Exception("topCircumferenceRadius must be positive");

            topCircumferenceRadiusPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number for\n";
            else
                statusText += ex.getMessage() + "\n";
            topCircumferenceRadiusPanel.setBackground(Color.red);
        }


        try {
            int temp = Integer.parseInt(inSlopeRadius.getText());

            if (temp < 0)
                throw new Exception("slopeRadius cannot be negative");

            slopeRadiusPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            calculateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number for slopeRadius\n";
            else
                statusText += ex.getMessage() + "\n";
            slopeRadiusPanel.setBackground(Color.red);
        }


        try {
            int temp = Integer.parseInt(inSlicesCirumference.getText());

            if (temp < 1)
                throw new Exception("Min slices is 1");

            slicesCircumferencePanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number for slicesCircumference\n";
            else
                statusText += ex.getMessage() + "\n";
            slicesCircumferencePanel.setBackground(Color.red);
        }


        try {
            int temp = Integer.parseInt(inSlicesSlope.getText());

            if (temp < 1)
                throw new Exception("Min slices is 1");

            slicesSlopePanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number for slicesSlope\n";
            else
                statusText += ex.getMessage() + "\n";
            slicesSlopePanel.setBackground(Color.red);

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
                throw new Exception("extrudeTopNSlices cannot be negative");

            extrudeTopNSlicesPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            calculateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number for extrudeTopNSlices\n";
            else
                statusText += ex.getMessage() + "\n";
            extrudeTopNSlicesPanel.setBackground(Color.red);
        }


        try {
            double temp = Double.parseDouble(inExtrudeRadiusFactor.getText());

            if (temp < 0)
                throw new Exception("Please enter a positive number");

            extrudeRadiusFactorPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex.getMessage().startsWith("For input String:"))
                statusText += "Please enter a number for inExtrudeRadiusFactor\n";
            else
                statusText += ex.getMessage() + "\n";
            extrudeRadiusFactorPanel.setBackground(Color.red);
        }


        try {
            int temp = Integer.parseInt(inCrossSection.getText());

            if (temp <= 0)
                throw new Exception("inCrossSection cannot be negative");

            crossSectionPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            statusPanel.setBackground(Color.yellow);
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number for inCrossSection\n";
            else
                statusText += ex.getMessage() + "\n";
            crossSectionPanel.setBackground(Color.red);
        }


        if (opNormal.isSelected()) {
            cutTop.setEnabled(false);
            cutBottom.setEnabled(false);
        } else {
            cutTop.setEnabled(true);
            cutBottom.setEnabled(true);
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
            java.util.logging.Logger.getLogger(VolcanoGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VolcanoGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VolcanoGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VolcanoGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VolcanoGeneratorGUI(new Config()).setVisible(true);
            }
        });
    }

    private javax.swing.JCheckBox adjustForOverlappingBowlCuts;
    private javax.swing.JPanel adjustForOverlappingBowlCutsPanel;
    private javax.swing.JCheckBox adjustForOverlappingPipeCuts;
    private javax.swing.JPanel adjustForOverlappingPipeCutsPanel;
    private javax.swing.JCheckBox adjustSeamForJoining;
    private javax.swing.JPanel adjustSeamForJoiningPanel;
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
    private javax.swing.JCheckBox cutBottom;
    private javax.swing.JPanel cutBottomPanel;
    private javax.swing.JCheckBox cutTop;
    private javax.swing.JPanel cutTopPanel;
    private javax.swing.JTextArea description;
    private javax.swing.JPanel descriptionPanel;
    private javax.swing.JScrollPane descriptionScrollPane;
    private javax.swing.JLabel descriptionTitle;
    private javax.swing.JPanel extrudeRadiusFactorPanel;
    private javax.swing.JPanel extrudeTopNSlicesPanel;
    private javax.swing.JButton generate;
    private javax.swing.JCheckBox generateOnlySeam;
    private javax.swing.JPanel generateOnlySeamPanel;
    private javax.swing.JLabel icon;
    private javax.swing.JPanel iconPanel;
    private javax.swing.JTextField inCrossSection;
    private javax.swing.JTextField inExtrudeRadiusFactor;
    private javax.swing.JTextField inExtrudeTopNSlices;
    private javax.swing.JTextField inLipOffset;
    private javax.swing.JTextField inSlicesCirumference;
    private javax.swing.JTextField inSlicesSlope;
    private javax.swing.JTextField inSlopeRadius;
    private javax.swing.JTextField inTopCircumferenceRadius;
    private javax.swing.JCheckBox invertSlopeVertices;
    private javax.swing.JPanel invertSlopeVerticesPanel;
    private javax.swing.JLabel laLipOffset;
    private javax.swing.JLabel labCrossSection;
    private javax.swing.JLabel labExtrudeRadiusFactor;
    private javax.swing.JLabel labExtrudeTopNSlices;
    private javax.swing.JLabel labSlicesCircumference;
    private javax.swing.JLabel labSlicesSlope;
    private javax.swing.JLabel labSlopeRadius;
    private javax.swing.JLabel labTopCircumferenceRadius;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel normalOptionPanel;
    private javax.swing.JCheckBox offsetTopInsteadOfBottom;
    private javax.swing.JPanel offsetTopInsteadOfBottomPanel;
    private javax.swing.JRadioButton opNormal;
    private javax.swing.JRadioButton opOverlapping;
    private javax.swing.ButtonGroup options;
    private javax.swing.JPanel optionsPanel;
    private javax.swing.JPanel overlappingBrushOptionPanel;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JPanel slicesCircumferencePanel;
    private javax.swing.JPanel slicesSlopePanel;
    private javax.swing.JPanel slopeRadiusPanel;
    private javax.swing.JTextArea status;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JScrollPane statusScrollPane;
    private javax.swing.JLabel subtitle;
    private javax.swing.JPanel subtitlePanel;
    private javax.swing.JPanel topCircumferenceRadiusPanel;
    private javax.swing.JPanel valuesPanel;
    private javax.swing.JPanel viewPanel;

}
