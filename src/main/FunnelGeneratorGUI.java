
package main;

import generators.FunnelDoubleGen;
import generators.FunnelGenerator;
import tools.MapFactory;
import ulben.NumericTextField;
import ulben.UlbenUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;


public class FunnelGeneratorGUI extends javax.swing.JFrame {

    private Config configuration;

    private int topRadius = 640;
    private int bottomRadius = 512;

    private Color shaded = new Color(200, 200, 200);
    private Color normal = this.getBackground();

    private String topRadiusTitle = "topRadius";
    private String topRadiusDescription = "The radius of the top of the funnel. You should probably have this be greater than bottomRadius.";

    private String bottomRadiusTitle = "bottomRadius";
    private String bottomRadiusDescription = "The radius of the bottom of the funnel. You should probably have this be less than topRadius. This radius may be zero. ";

    private String heightTitle = "height";
    private String heightDescription = "The difference in height between the top and bottom of the funnel. Unless you know what you are doing, this value should be positive.";

    private String slicesTitle = "slices";
    private String slicesDescription = "The number of slices to chop the top circumference and the bottom circumference into. The funnel structure ends up having slices * 2 triangular faces making up the funnel surface.";

    private String crossSectionTitle = "crossSection";
    private String crossSectionDescription = "Unless extendDownInsteadOfSquare is checked, this specifies the length of the square cross section of the total structure making up a quarter of a funnel. If extendDownInsteadOfSquare is checked, the cross section takes on a slightly different meaning, which is the distance from the \"top\" of the funnel to the very bottom of the base. \n\n Note: if overlapping brushes are being generated, you will need to set this to a large value relative to the radius values. ";

    private String offsetTopInsteadOfBottomTitle = "offsetTopInsteadOfBottom";
    private String offsetTopInsteadOfBottomDescription = "To make the funnel, either the top or the bottom circumference must be rotated counter-clockwise by half a slice, so that triangles can be created on the surface. Normally, the bottom circumference is rotated by this amount. use this flag to rotate the top circumference instead.";

    private String extendDownInsteadOfSquareTitle = "extendDownInsteadOfSquare";
    private String extendDownInsteadOfSquareDescription = "Project the triangular faces of the funnel downwards to make the brushes. Normally, the faces are projected in the +x and +y directions. ";

    private String generateOnlySeamTitle = "generateOnlySeam";
    private String generateOnlySeamDescription = "Generate only the last brush. Useful for making seams.";

    private String adjustSeamForJoiningTitle = "adjustSeamForJoining";
    private String adjustSeamForJoiningDescription = "Adjust the seam brush if it is to be joined to another structure such as a slanted wall. ";

    private String opNormalTitle = "Generate normal non-overlapping brushes";
    private String opNormalDescription = "Generates ordinary brushes. To make a funnel wall to fling you around at high speeds, you will have to generate overlapping brushes in order to prevent \"bouncing\". Another purpose for normal brushes is to define the visual component of a structure, for example by using surfaceparm nonsolid. ";

    private String opOverlappingTitle = "Generate overlapping brushes";
    private String opOverlappingDescription = "Overlapping brushes are useful for making funnels such that the player doesn't \"bounce\" off the surface when flying against the funnel wall. However, it is not advised to texture the overlapping brush faces with drawn textures. Instead, apply [slick] caulk to all of the overlapping brushes, then superimpose a set of normal non-overlapping brushes that have surfaceparm nonsolid. \n\n NOTE: If you are generating overlapping brushes make sure that crossSection is very high relative to the other values such as radius. When you are done generating overlapping brushes, you will likely want to cut everything off that is above z = 0 and everything that is below z = -height. ";

    private String offsetAngleTitle = "Offset Angle";
    private String offsetAngleDescription = "The angle, in degrees, by which to rotate the funnel counter-clockwise. This only applies if extendDownInsteadOfSquare is true.";


    public FunnelGeneratorGUI(Config configuration) {
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
        funnelNote = new javax.swing.JDialog();
        calculatorPanel = new javax.swing.JPanel();
        calculatePanel = new javax.swing.JPanel();
        useInterestingValues = new javax.swing.JButton();
        calculatorDescriptionPanel = new javax.swing.JPanel();
        calculatorDescription = new javax.swing.JTextArea();
        scrollPane = new javax.swing.JScrollPane();
        viewPanel = new javax.swing.JPanel();
        descriptionPanel = new javax.swing.JPanel();
        descriptionScrollPane = new javax.swing.JScrollPane();
        description = new javax.swing.JTextArea();
        descriptionTitle = new javax.swing.JLabel();
        subtitlePanel = new javax.swing.JPanel();
        subtitle = new javax.swing.JLabel();
        statusPanel = new javax.swing.JPanel();
        generate = new javax.swing.JButton();
        statusScrollPane = new javax.swing.JScrollPane();
        status = new javax.swing.JTextArea();
        valuesPanel = new javax.swing.JPanel();
        topRadiusPanel = new javax.swing.JPanel();
        labTopRadius = new javax.swing.JLabel();
        inTopRadius = new NumericTextField(64);
        crossSectionPanel = new javax.swing.JPanel();
        labCrossSection = new javax.swing.JLabel();
        inCrossSection = new NumericTextField(64);
        heightPanel = new javax.swing.JPanel();
        labHeight = new javax.swing.JLabel();
        inHeight = new NumericTextField(64);
        slicesPanel = new javax.swing.JPanel();
        labSlicesSlope = new javax.swing.JLabel();
        inSlices = new NumericTextField(1);
        extendDownInsteadOfSquarePanel = new javax.swing.JPanel();
        extendDownInsteadOfSquare = new javax.swing.JCheckBox();
        offsetTopInsteadOfBottomPanel = new javax.swing.JPanel();
        offsetTopInsteadOfBottom = new javax.swing.JCheckBox();
        bottomRadiusPanel = new javax.swing.JPanel();
        labBottomRadius = new javax.swing.JLabel();
        inBottomRadius = new NumericTextField(64);
        adjustSeamForJoiningPanel = new javax.swing.JPanel();
        adjustSeamForJoining = new javax.swing.JCheckBox();
        generateOnlySeamPanel = new javax.swing.JPanel();
        generateOnlySeam = new javax.swing.JCheckBox();
        offsetAnglePanel = new javax.swing.JPanel();
        labOffsetAngle = new javax.swing.JLabel();
        inOffsetAngle = new javax.swing.JTextField();
        iconPanel = new javax.swing.JPanel();
        icon = new javax.swing.JLabel();
        optionsPanel = new javax.swing.JPanel();
        normalOptionPanel = new javax.swing.JPanel();
        opNormal = new javax.swing.JCheckBox();
        overlappingBrushOptionPanel = new javax.swing.JPanel();
        opOverlapping = new javax.swing.JCheckBox();
        menu = new javax.swing.JMenuBar();
        note = new javax.swing.JMenu();
        interestingValuesNote = new javax.swing.JMenuItem();

        funnelNote.setTitle("Note on Interesting Values");
        funnelNote.setMinimumSize(new java.awt.Dimension(330, 372));

        useInterestingValues.setText("Try these values");
        useInterestingValues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useInterestingValuesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout calculatePanelLayout = new javax.swing.GroupLayout(calculatePanel);
        calculatePanel.setLayout(calculatePanelLayout);
        calculatePanelLayout.setHorizontalGroup(
                calculatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, calculatePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(useInterestingValues, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        calculatePanelLayout.setVerticalGroup(
                calculatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, calculatePanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(useInterestingValues)
                                .addContainerGap())
        );

        calculatorDescription.setEditable(false);
        calculatorDescription.setBackground(new java.awt.Color(240, 240, 240));
        calculatorDescription.setColumns(20);
        calculatorDescription.setLineWrap(true);
        calculatorDescription.setRows(5);
        calculatorDescription.setText(" Note that it is possible to create interesting shapes that are not exactly funnels if you know what you are doing. For example, try the following parameters:\ntopRadius:\t768\nbottomRadius: 256\nheight:\t-512\nslices:\t16\ncrossSection:\t128\nextendDownInsteadOfSquare: checked");
        calculatorDescription.setWrapStyleWord(true);

        javax.swing.GroupLayout calculatorDescriptionPanelLayout = new javax.swing.GroupLayout(calculatorDescriptionPanel);
        calculatorDescriptionPanel.setLayout(calculatorDescriptionPanelLayout);
        calculatorDescriptionPanelLayout.setHorizontalGroup(
                calculatorDescriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(calculatorDescriptionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(calculatorDescription)
                                .addContainerGap())
        );
        calculatorDescriptionPanelLayout.setVerticalGroup(
                calculatorDescriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, calculatorDescriptionPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(calculatorDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(172, 172, 172))
        );

        javax.swing.GroupLayout calculatorPanelLayout = new javax.swing.GroupLayout(calculatorPanel);
        calculatorPanel.setLayout(calculatorPanelLayout);
        calculatorPanelLayout.setHorizontalGroup(
                calculatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(calculatorPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(calculatorDescriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(calculatorPanelLayout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(calculatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        calculatorPanelLayout.setVerticalGroup(
                calculatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(calculatorPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(calculatorDescriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addComponent(calculatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout funnelNoteLayout = new javax.swing.GroupLayout(funnelNote.getContentPane());
        funnelNote.getContentPane().setLayout(funnelNoteLayout);
        funnelNoteLayout.setHorizontalGroup(
                funnelNoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(funnelNoteLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(calculatorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        funnelNoteLayout.setVerticalGroup(
                funnelNoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(funnelNoteLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(calculatorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        setTitle("Funnel Generator");
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
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(descriptionTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(descriptionScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE))
                                .addContainerGap())
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

        subtitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        subtitle.setText("Generates a quarter of a funnel as a .map file to be opened in radiant.");

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
                                .addContainerGap()
                                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(generate)
                                        .addComponent(statusScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        topRadiusPanel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                topRadiusPanelFocusGained(evt);
            }
        });

        labTopRadius.setLabelFor(inTopRadius);
        labTopRadius.setText("topRadius:");

        inTopRadius.setText("640");
        inTopRadius.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inTopRadiusActionPerformed(evt);
            }
        });
        inTopRadius.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inTopRadiusFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inTopRadiusFocusLost(evt);
            }
        });

        javax.swing.GroupLayout topRadiusPanelLayout = new javax.swing.GroupLayout(topRadiusPanel);
        topRadiusPanel.setLayout(topRadiusPanelLayout);
        topRadiusPanelLayout.setHorizontalGroup(
                topRadiusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(topRadiusPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labTopRadius)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inTopRadius, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        topRadiusPanelLayout.setVerticalGroup(
                topRadiusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topRadiusPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(topRadiusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labTopRadius)
                                        .addComponent(inTopRadius, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        labCrossSection.setLabelFor(inCrossSection);
        labCrossSection.setText("crossSection:");

        inCrossSection.setText("1024");
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

        labHeight.setLabelFor(inHeight);
        labHeight.setText("height:");

        inHeight.setText("256");
        inHeight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inHeightActionPerformed(evt);
            }
        });
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
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, heightPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(heightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labHeight)
                                        .addComponent(inHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        labSlicesSlope.setLabelFor(inSlices);
        labSlicesSlope.setText("slices:");

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
                                .addComponent(labSlicesSlope)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inSlices, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        slicesPanelLayout.setVerticalGroup(
                slicesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(slicesPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(slicesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labSlicesSlope)
                                        .addComponent(inSlices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        extendDownInsteadOfSquare.setText("extendDownInsteadOfSquare");
        extendDownInsteadOfSquare.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        extendDownInsteadOfSquare.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        extendDownInsteadOfSquare.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                extendDownInsteadOfSquareFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                extendDownInsteadOfSquareFocusLost(evt);
            }
        });

        javax.swing.GroupLayout extendDownInsteadOfSquarePanelLayout = new javax.swing.GroupLayout(extendDownInsteadOfSquarePanel);
        extendDownInsteadOfSquarePanel.setLayout(extendDownInsteadOfSquarePanelLayout);
        extendDownInsteadOfSquarePanelLayout.setHorizontalGroup(
                extendDownInsteadOfSquarePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(extendDownInsteadOfSquarePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(extendDownInsteadOfSquare, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        extendDownInsteadOfSquarePanelLayout.setVerticalGroup(
                extendDownInsteadOfSquarePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, extendDownInsteadOfSquarePanelLayout.createSequentialGroup()
                                .addGap(0, 12, Short.MAX_VALUE)
                                .addComponent(extendDownInsteadOfSquare, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        offsetTopInsteadOfBottom.setText("offsetTopInsteadOfBottom");
        offsetTopInsteadOfBottom.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        offsetTopInsteadOfBottom.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        offsetTopInsteadOfBottom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                offsetTopInsteadOfBottomActionPerformed(evt);
            }
        });
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
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, offsetTopInsteadOfBottomPanelLayout.createSequentialGroup()
                                .addGap(0, 10, Short.MAX_VALUE)
                                .addComponent(offsetTopInsteadOfBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bottomRadiusPanel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                bottomRadiusPanelFocusGained(evt);
            }
        });

        labBottomRadius.setLabelFor(inTopRadius);
        labBottomRadius.setText("bottomRadius:");

        inBottomRadius.setText("512");
        inBottomRadius.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inBottomRadiusActionPerformed(evt);
            }
        });
        inBottomRadius.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inBottomRadiusFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inBottomRadiusFocusLost(evt);
            }
        });

        javax.swing.GroupLayout bottomRadiusPanelLayout = new javax.swing.GroupLayout(bottomRadiusPanel);
        bottomRadiusPanel.setLayout(bottomRadiusPanelLayout);
        bottomRadiusPanelLayout.setHorizontalGroup(
                bottomRadiusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(bottomRadiusPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labBottomRadius)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inBottomRadius, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        bottomRadiusPanelLayout.setVerticalGroup(
                bottomRadiusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bottomRadiusPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(bottomRadiusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labBottomRadius)
                                        .addComponent(inBottomRadius, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        adjustSeamForJoining.setText("adjustSeamForJoining");
        adjustSeamForJoining.setSelected(true);
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

        labOffsetAngle.setLabelFor(inCrossSection);
        labOffsetAngle.setText("offsetAngle:");

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
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(bottomRadiusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(adjustSeamForJoiningPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(topRadiusPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(crossSectionPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(slicesPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(heightPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(offsetTopInsteadOfBottomPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(extendDownInsteadOfSquarePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(generateOnlySeamPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addComponent(offsetAnglePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        valuesPanelLayout.setVerticalGroup(
                valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(valuesPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(topRadiusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bottomRadiusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(heightPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slicesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(crossSectionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(offsetTopInsteadOfBottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(extendDownInsteadOfSquarePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(generateOnlySeamPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(adjustSeamForJoiningPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(offsetAnglePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/funnel.png")));
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

        javax.swing.GroupLayout viewPanelLayout = new javax.swing.GroupLayout(viewPanel);
        viewPanel.setLayout(viewPanelLayout);
        viewPanelLayout.setHorizontalGroup(
                viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(viewPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(viewPanelLayout.createSequentialGroup()
                                                .addComponent(optionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(iconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(subtitlePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(statusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(viewPanelLayout.createSequentialGroup()
                                                .addComponent(valuesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(15, Short.MAX_VALUE))
        );
        viewPanelLayout.setVerticalGroup(
                viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(viewPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(subtitlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(viewPanelLayout.createSequentialGroup()
                                                .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(iconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(viewPanelLayout.createSequentialGroup()
                                                .addComponent(valuesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(optionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(statusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        scrollPane.setViewportView(viewPanel);

        note.setText("Note");

        interestingValuesNote.setText("Note on interesting values");
        interestingValuesNote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                interestingValuesNoteActionPerformed(evt);
            }
        });
        note.add(interestingValuesNote);

        menu.add(note);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(scrollPane))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }

    private void inTopRadiusFocusGained(java.awt.event.FocusEvent evt) {
        topRadiusPanel.setBackground(shaded);
        descriptionTitle.setText(topRadiusTitle);
        description.setText(topRadiusDescription);
    }

    private void inTopRadiusFocusLost(java.awt.event.FocusEvent evt) {
        checkGenerateOK();
    }

    private void topRadiusPanelFocusGained(java.awt.event.FocusEvent evt) {
    }

    private void inCrossSectionFocusGained(java.awt.event.FocusEvent evt) {
        crossSectionPanel.setBackground(shaded);
        descriptionTitle.setText(crossSectionTitle);
        description.setText(crossSectionDescription);
    }

    private void inCrossSectionFocusLost(java.awt.event.FocusEvent evt) {
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

    private void offsetTopInsteadOfBottomFocusGained(java.awt.event.FocusEvent evt) {
        offsetTopInsteadOfBottomPanel.setBackground(shaded);
        descriptionTitle.setText(offsetTopInsteadOfBottomTitle);
        description.setText(offsetTopInsteadOfBottomDescription);
    }

    private void offsetTopInsteadOfBottomFocusLost(java.awt.event.FocusEvent evt) {
        offsetTopInsteadOfBottomPanel.setBackground(normal);
    }

    private void generateActionPerformed(java.awt.event.ActionEvent evt) throws IOException {

        generate.setEnabled(false);

        status.setText("Generating funnel...");

        if (opNormal.isSelected() ^ opOverlapping.isSelected()) {
            String[] args = {inTopRadius.getText(), inBottomRadius.getText(), inHeight.getText(), inSlices.getText(), inCrossSection.getText(), Boolean.toString(offsetTopInsteadOfBottom.isSelected()), Boolean.toString(extendDownInsteadOfSquare.isSelected()), Boolean.toString(opOverlapping.isSelected()), Boolean.toString(generateOnlySeam.isSelected()), Boolean.toString(adjustSeamForJoining.isSelected()), inOffsetAngle.getText()};
            FunnelGenerator.main(args);
        } else if (opNormal.isSelected() && opOverlapping.isSelected()) {
            String[] args = {inTopRadius.getText(), inBottomRadius.getText(), inHeight.getText(), inSlices.getText(), inCrossSection.getText(), Boolean.toString(offsetTopInsteadOfBottom.isSelected()), Boolean.toString(extendDownInsteadOfSquare.isSelected()), Boolean.toString(opOverlapping.isSelected()), Boolean.toString(generateOnlySeam.isSelected()), Boolean.toString(adjustSeamForJoining.isSelected()), inOffsetAngle.getText()};
            String[] args2 = {inTopRadius.getText(), inBottomRadius.getText(), inHeight.getText(), inSlices.getText(), inCrossSection.getText(), Boolean.toString(offsetTopInsteadOfBottom.isSelected()), Boolean.toString(extendDownInsteadOfSquare.isSelected()), Boolean.toString(!opOverlapping.isSelected()), Boolean.toString(generateOnlySeam.isSelected()), Boolean.toString(adjustSeamForJoining.isSelected()), inOffsetAngle.getText()};
            FunnelDoubleGen.main(args, args2);
        } else {
            status.setText("Nothing to generate");
            generate.setEnabled(true);
            return;
        }

        status.setText("Generated - select save file name and location");

        File saveFile = UlbenUtils.getSaveFile(this, "FUNNEL");

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

    private void offsetTopInsteadOfBottomActionPerformed(java.awt.event.ActionEvent evt) {

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

    private void extendDownInsteadOfSquareFocusGained(java.awt.event.FocusEvent evt) {
        extendDownInsteadOfSquarePanel.setBackground(shaded);
        descriptionTitle.setText(extendDownInsteadOfSquareTitle);
        description.setText(extendDownInsteadOfSquareDescription);
    }

    private void extendDownInsteadOfSquareFocusLost(java.awt.event.FocusEvent evt) {

        extendDownInsteadOfSquarePanel.setBackground(normal);
    }

    private void inBottomRadiusFocusGained(java.awt.event.FocusEvent evt) {

        bottomRadiusPanel.setBackground(shaded);
        descriptionTitle.setText(bottomRadiusTitle);
        description.setText(bottomRadiusDescription);
    }

    private void inBottomRadiusFocusLost(java.awt.event.FocusEvent evt) {
        checkGenerateOK();
    }

    private void bottomRadiusPanelFocusGained(java.awt.event.FocusEvent evt) {

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

    private void inSlicesFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void inSlicesFocusGained(java.awt.event.FocusEvent evt) {

        slicesPanel.setBackground(shaded);
        descriptionTitle.setText(slicesTitle);
        description.setText(slicesDescription);
    }

    private void generateOnlySeamActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void inOffsetAngleFocusGained(java.awt.event.FocusEvent evt) {

        offsetAnglePanel.setBackground(shaded);
        descriptionTitle.setText(offsetAngleTitle);
        description.setText(offsetAngleDescription);
    }

    private void inOffsetAngleFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void inOffsetAngleActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inCrossSectionActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inSlicesActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inHeightActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inBottomRadiusActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inTopRadiusActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void useInterestingValuesActionPerformed(java.awt.event.ActionEvent evt) {

        inTopRadius.setText("768");
        inBottomRadius.setText("256");
        inHeight.setText("-512");
        inSlices.setText("16");
        inCrossSection.setText("128");
        extendDownInsteadOfSquare.setSelected(true);
        checkGenerateOK();

    }

    private void interestingValuesNoteActionPerformed(java.awt.event.ActionEvent evt) {

        funnelNote.setVisible(true);
    }


    private void checkGenerateOK() {
        boolean generateOK = true;
        statusPanel.setBackground(normal);
        status.setText("");
        String statusText = "";


        try {
            int temp = Integer.parseInt(inTopRadius.getText());

            if (temp <= 1)
                throw new Exception("topRadius must be positive");
            if (temp < bottomRadius)
                throw new Exception("topRadius must be greater than or equal to bottomRadius");

            topRadius = temp;
            topRadiusPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number\n";
            else
                statusText += ex.getMessage() + "\n";
            topRadiusPanel.setBackground(Color.red);
        }


        try {
            int temp = Integer.parseInt(inBottomRadius.getText());

            if (temp < 0)
                throw new Exception("topRadius cannot be negative");
            if (temp > topRadius)
                throw new Exception("topRadius must be less than or equal to bottomRadius");

            bottomRadius = temp;
            bottomRadiusPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number\n";
            else
                statusText += ex.getMessage() + "\n";
            bottomRadiusPanel.setBackground(Color.red);
        }


        try {
            int temp = Integer.parseInt(inHeight.getText());

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
            int temp = Integer.parseInt(inCrossSection.getText());

            if (temp <= 0)
                throw new Exception("inCrossSection must be positive");

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


        try {
            double temp = Double.parseDouble(inOffsetAngle.getText());

            if (temp < 0)
                throw new Exception("Please enter a positive number");

            offsetAnglePanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number for the offsetAngle\n";
            else
                statusText += ex.getMessage() + "\n";
            offsetAnglePanel.setBackground(Color.red);
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
            java.util.logging.Logger.getLogger(FunnelGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FunnelGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FunnelGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FunnelGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FunnelGeneratorGUI(new Config()).setVisible(true);
            }
        });
    }

    private javax.swing.JCheckBox adjustSeamForJoining;
    private javax.swing.JPanel adjustSeamForJoiningPanel;
    private javax.swing.JPanel bottomRadiusPanel;
    private javax.swing.JPanel calculatePanel;
    private javax.swing.JTextArea calculatorDescription;
    private javax.swing.JPanel calculatorDescriptionPanel;
    private javax.swing.JPanel calculatorPanel;
    private javax.swing.JPanel crossSectionPanel;
    private javax.swing.JTextArea description;
    private javax.swing.JPanel descriptionPanel;
    private javax.swing.JScrollPane descriptionScrollPane;
    private javax.swing.JLabel descriptionTitle;
    private javax.swing.JCheckBox extendDownInsteadOfSquare;
    private javax.swing.JPanel extendDownInsteadOfSquarePanel;
    private javax.swing.JDialog funnelNote;
    private javax.swing.JButton generate;
    private javax.swing.JCheckBox generateOnlySeam;
    private javax.swing.JPanel generateOnlySeamPanel;
    private javax.swing.JPanel heightPanel;
    private javax.swing.JLabel icon;
    private javax.swing.JPanel iconPanel;
    private NumericTextField inBottomRadius;
    private NumericTextField inCrossSection;
    private NumericTextField inHeight;
    private javax.swing.JTextField inOffsetAngle;
    private NumericTextField inSlices;
    private NumericTextField inTopRadius;
    private javax.swing.JMenuItem interestingValuesNote;
    private javax.swing.JLabel labBottomRadius;
    private javax.swing.JLabel labCrossSection;
    private javax.swing.JLabel labHeight;
    private javax.swing.JLabel labOffsetAngle;
    private javax.swing.JLabel labSlicesSlope;
    private javax.swing.JLabel labTopRadius;
    private javax.swing.JMenuBar menu;
    private javax.swing.JPanel normalOptionPanel;
    private javax.swing.JMenu note;
    private javax.swing.JPanel offsetAnglePanel;
    private javax.swing.JCheckBox offsetTopInsteadOfBottom;
    private javax.swing.JPanel offsetTopInsteadOfBottomPanel;
    private javax.swing.JCheckBox opNormal;
    private javax.swing.JCheckBox opOverlapping;
    private javax.swing.JPanel optionsPanel;
    private javax.swing.JPanel overlappingBrushOptionPanel;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JPanel slicesPanel;
    private javax.swing.JTextArea status;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JScrollPane statusScrollPane;
    private javax.swing.JLabel subtitle;
    private javax.swing.JPanel subtitlePanel;
    private javax.swing.JPanel topRadiusPanel;
    private javax.swing.JButton useInterestingValues;
    private javax.swing.JPanel valuesPanel;
    private javax.swing.JPanel viewPanel;

}
