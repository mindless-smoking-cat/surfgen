
package main;

import generators.SineWaveGenerator;
import tools.MapFactory;
import ulben.UlbenUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;


public class SineWaveGeneratorGUI extends javax.swing.JFrame {

    private Config configuration;

    private Color shaded = new Color(200, 200, 200);
    private Color normal = this.getBackground();


    public SineWaveGeneratorGUI(Config configuration) {
        initComponents();
        this.configuration = configuration;
    }


    @SuppressWarnings("unchecked")

    private void initComponents() {

        options = new javax.swing.ButtonGroup();
        valuesPanel = new javax.swing.JPanel();
        surfaceLengthPanel = new javax.swing.JPanel();
        labSurfaceLength = new javax.swing.JLabel();
        inSurfaceLength = new javax.swing.JTextField();
        leftAmplitudePanel = new javax.swing.JPanel();
        labLeftAmplitude = new javax.swing.JLabel();
        inLeftAmplitude = new javax.swing.JTextField();
        slicesLengthPanel = new javax.swing.JPanel();
        labSlicesLength = new javax.swing.JLabel();
        inSlicesLength = new javax.swing.JTextField();
        leftHeightPanel = new javax.swing.JPanel();
        labLeftHeight = new javax.swing.JLabel();
        inLeftHeight = new javax.swing.JTextField();
        leftPhasePanel = new javax.swing.JPanel();
        lableftPhase = new javax.swing.JLabel();
        inLeftPhase = new javax.swing.JTextField();
        leftWavelengthPanel = new javax.swing.JPanel();
        labLeftWavelength = new javax.swing.JLabel();
        inLeftWavelength = new javax.swing.JTextField();
        surfaceWidthPanel = new javax.swing.JPanel();
        labSurfaceWidth = new javax.swing.JLabel();
        inSurfaceWidth = new javax.swing.JTextField();
        rightHeightPanel = new javax.swing.JPanel();
        inRightHeight = new javax.swing.JTextField();
        labRightHeight = new javax.swing.JLabel();
        rightAmplitudePanel = new javax.swing.JPanel();
        inRightAmplitude = new javax.swing.JTextField();
        labRightAmplitude = new javax.swing.JLabel();
        rightWavelengthPanel = new javax.swing.JPanel();
        labRightWavelength = new javax.swing.JLabel();
        inRightWavelength = new javax.swing.JTextField();
        slicesWidthPanel = new javax.swing.JPanel();
        labSlicesWidth = new javax.swing.JLabel();
        inSlicesWidth = new javax.swing.JTextField();
        rightPhasePanel = new javax.swing.JPanel();
        labRightPhase = new javax.swing.JLabel();
        inRightPhase = new javax.swing.JTextField();
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

        setTitle("Sine Wave Generator");
        setLocationByPlatform(true);

        surfaceLengthPanel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                surfaceLengthPanelFocusGained(evt);
            }
        });

        labSurfaceLength.setLabelFor(inSurfaceLength);
        labSurfaceLength.setText("surfaceLength:");

        inSurfaceLength.setText("4096");
        inSurfaceLength.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inSurfaceLengthActionPerformed(evt);
            }
        });
        inSurfaceLength.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inSurfaceLengthFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inSurfaceLengthFocusLost(evt);
            }
        });

        javax.swing.GroupLayout surfaceLengthPanelLayout = new javax.swing.GroupLayout(surfaceLengthPanel);
        surfaceLengthPanel.setLayout(surfaceLengthPanelLayout);
        surfaceLengthPanelLayout.setHorizontalGroup(
                surfaceLengthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(surfaceLengthPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labSurfaceLength)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inSurfaceLength, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        surfaceLengthPanelLayout.setVerticalGroup(
                surfaceLengthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, surfaceLengthPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(surfaceLengthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labSurfaceLength)
                                        .addComponent(inSurfaceLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        labLeftAmplitude.setLabelFor(inLeftAmplitude);
        labLeftAmplitude.setText("leftAmplitude:");

        inLeftAmplitude.setText("384");
        inLeftAmplitude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inLeftAmplitudeActionPerformed(evt);
            }
        });
        inLeftAmplitude.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inLeftAmplitudeFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inLeftAmplitudeFocusLost(evt);
            }
        });

        javax.swing.GroupLayout leftAmplitudePanelLayout = new javax.swing.GroupLayout(leftAmplitudePanel);
        leftAmplitudePanel.setLayout(leftAmplitudePanelLayout);
        leftAmplitudePanelLayout.setHorizontalGroup(
                leftAmplitudePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(leftAmplitudePanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labLeftAmplitude)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inLeftAmplitude, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        leftAmplitudePanelLayout.setVerticalGroup(
                leftAmplitudePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftAmplitudePanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(leftAmplitudePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labLeftAmplitude)
                                        .addComponent(inLeftAmplitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        labSlicesLength.setLabelFor(inSlicesLength);
        labSlicesLength.setText("slicesLength:");

        inSlicesLength.setText("96");
        inSlicesLength.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inSlicesLengthActionPerformed(evt);
            }
        });
        inSlicesLength.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inSlicesLengthFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inSlicesLengthFocusLost(evt);
            }
        });

        javax.swing.GroupLayout slicesLengthPanelLayout = new javax.swing.GroupLayout(slicesLengthPanel);
        slicesLengthPanel.setLayout(slicesLengthPanelLayout);
        slicesLengthPanelLayout.setHorizontalGroup(
                slicesLengthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(slicesLengthPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labSlicesLength)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inSlicesLength, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        slicesLengthPanelLayout.setVerticalGroup(
                slicesLengthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slicesLengthPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(slicesLengthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labSlicesLength)
                                        .addComponent(inSlicesLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        labLeftHeight.setLabelFor(inLeftHeight);
        labLeftHeight.setText("leftHeight:");

        inLeftHeight.setText("512");
        inLeftHeight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inLeftHeightActionPerformed(evt);
            }
        });
        inLeftHeight.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inLeftHeightFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inLeftHeightFocusLost(evt);
            }
        });

        javax.swing.GroupLayout leftHeightPanelLayout = new javax.swing.GroupLayout(leftHeightPanel);
        leftHeightPanel.setLayout(leftHeightPanelLayout);
        leftHeightPanelLayout.setHorizontalGroup(
                leftHeightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(leftHeightPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labLeftHeight)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inLeftHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        leftHeightPanelLayout.setVerticalGroup(
                leftHeightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(leftHeightPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(leftHeightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labLeftHeight)
                                        .addComponent(inLeftHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        lableftPhase.setLabelFor(inLeftAmplitude);
        lableftPhase.setText("leftPhase:");

        inLeftPhase.setText("-0.25");
        inLeftPhase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inLeftPhaseActionPerformed(evt);
            }
        });
        inLeftPhase.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inLeftPhaseFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inLeftPhaseFocusLost(evt);
            }
        });

        javax.swing.GroupLayout leftPhasePanelLayout = new javax.swing.GroupLayout(leftPhasePanel);
        leftPhasePanel.setLayout(leftPhasePanelLayout);
        leftPhasePanelLayout.setHorizontalGroup(
                leftPhasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(leftPhasePanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lableftPhase)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inLeftPhase, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        leftPhasePanelLayout.setVerticalGroup(
                leftPhasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftPhasePanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(leftPhasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lableftPhase)
                                        .addComponent(inLeftPhase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        labLeftWavelength.setLabelFor(inLeftAmplitude);
        labLeftWavelength.setText("leftWavelength:");

        inLeftWavelength.setText("2048");
        inLeftWavelength.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inLeftWavelengthActionPerformed(evt);
            }
        });
        inLeftWavelength.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inLeftWavelengthFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inLeftWavelengthFocusLost(evt);
            }
        });

        javax.swing.GroupLayout leftWavelengthPanelLayout = new javax.swing.GroupLayout(leftWavelengthPanel);
        leftWavelengthPanel.setLayout(leftWavelengthPanelLayout);
        leftWavelengthPanelLayout.setHorizontalGroup(
                leftWavelengthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(leftWavelengthPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labLeftWavelength)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inLeftWavelength, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        leftWavelengthPanelLayout.setVerticalGroup(
                leftWavelengthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftWavelengthPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(leftWavelengthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labLeftWavelength)
                                        .addComponent(inLeftWavelength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        surfaceWidthPanel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                surfaceWidthPanelFocusGained(evt);
            }
        });

        labSurfaceWidth.setLabelFor(inSurfaceLength);
        labSurfaceWidth.setText("surfaceWidth:");

        inSurfaceWidth.setText("224");
        inSurfaceWidth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inSurfaceWidthActionPerformed(evt);
            }
        });
        inSurfaceWidth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inSurfaceWidthFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inSurfaceWidthFocusLost(evt);
            }
        });

        javax.swing.GroupLayout surfaceWidthPanelLayout = new javax.swing.GroupLayout(surfaceWidthPanel);
        surfaceWidthPanel.setLayout(surfaceWidthPanelLayout);
        surfaceWidthPanelLayout.setHorizontalGroup(
                surfaceWidthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(surfaceWidthPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labSurfaceWidth)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inSurfaceWidth, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        surfaceWidthPanelLayout.setVerticalGroup(
                surfaceWidthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, surfaceWidthPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(surfaceWidthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labSurfaceWidth)
                                        .addComponent(inSurfaceWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        inRightHeight.setText("512");
        inRightHeight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inRightHeightActionPerformed(evt);
            }
        });
        inRightHeight.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inRightHeightFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inRightHeightFocusLost(evt);
            }
        });

        labRightHeight.setLabelFor(inLeftAmplitude);
        labRightHeight.setText("rightHeight:");

        javax.swing.GroupLayout rightHeightPanelLayout = new javax.swing.GroupLayout(rightHeightPanel);
        rightHeightPanel.setLayout(rightHeightPanelLayout);
        rightHeightPanelLayout.setHorizontalGroup(
                rightHeightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightHeightPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labRightHeight)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inRightHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        rightHeightPanelLayout.setVerticalGroup(
                rightHeightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightHeightPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(rightHeightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(inRightHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labRightHeight))
                                .addContainerGap())
        );

        inRightAmplitude.setText("384");
        inRightAmplitude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inRightAmplitudeActionPerformed(evt);
            }
        });
        inRightAmplitude.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inRightAmplitudeFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inRightAmplitudeFocusLost(evt);
            }
        });

        labRightAmplitude.setLabelFor(inLeftAmplitude);
        labRightAmplitude.setText("rightAmplitude:");

        javax.swing.GroupLayout rightAmplitudePanelLayout = new javax.swing.GroupLayout(rightAmplitudePanel);
        rightAmplitudePanel.setLayout(rightAmplitudePanelLayout);
        rightAmplitudePanelLayout.setHorizontalGroup(
                rightAmplitudePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightAmplitudePanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labRightAmplitude)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inRightAmplitude, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        rightAmplitudePanelLayout.setVerticalGroup(
                rightAmplitudePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(rightAmplitudePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(rightAmplitudePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(inRightAmplitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labRightAmplitude))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        labRightWavelength.setLabelFor(inLeftAmplitude);
        labRightWavelength.setText("rightWavelength:");

        inRightWavelength.setText("4096");
        inRightWavelength.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inRightWavelengthActionPerformed(evt);
            }
        });
        inRightWavelength.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inRightWavelengthFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inRightWavelengthFocusLost(evt);
            }
        });

        javax.swing.GroupLayout rightWavelengthPanelLayout = new javax.swing.GroupLayout(rightWavelengthPanel);
        rightWavelengthPanel.setLayout(rightWavelengthPanelLayout);
        rightWavelengthPanelLayout.setHorizontalGroup(
                rightWavelengthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(rightWavelengthPanelLayout.createSequentialGroup()
                                .addContainerGap(21, Short.MAX_VALUE)
                                .addComponent(labRightWavelength)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inRightWavelength, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        rightWavelengthPanelLayout.setVerticalGroup(
                rightWavelengthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightWavelengthPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(rightWavelengthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labRightWavelength)
                                        .addComponent(inRightWavelength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        slicesWidthPanel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                slicesWidthPanelFocusGained(evt);
            }
        });

        labSlicesWidth.setLabelFor(inSurfaceLength);
        labSlicesWidth.setText("slicesWidth:");

        inSlicesWidth.setText("8");
        inSlicesWidth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inSlicesWidthActionPerformed(evt);
            }
        });
        inSlicesWidth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inSlicesWidthFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inSlicesWidthFocusLost(evt);
            }
        });

        javax.swing.GroupLayout slicesWidthPanelLayout = new javax.swing.GroupLayout(slicesWidthPanel);
        slicesWidthPanel.setLayout(slicesWidthPanelLayout);
        slicesWidthPanelLayout.setHorizontalGroup(
                slicesWidthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(slicesWidthPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labSlicesWidth)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inSlicesWidth, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        slicesWidthPanelLayout.setVerticalGroup(
                slicesWidthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slicesWidthPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(slicesWidthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labSlicesWidth)
                                        .addComponent(inSlicesWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        labRightPhase.setLabelFor(inLeftAmplitude);
        labRightPhase.setText("rightPhase:");

        inRightPhase.setText("-0.25");
        inRightPhase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inRightPhaseActionPerformed(evt);
            }
        });
        inRightPhase.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inRightPhaseFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inRightPhaseFocusLost(evt);
            }
        });

        javax.swing.GroupLayout rightPhasePanelLayout = new javax.swing.GroupLayout(rightPhasePanel);
        rightPhasePanel.setLayout(rightPhasePanelLayout);
        rightPhasePanelLayout.setHorizontalGroup(
                rightPhasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(rightPhasePanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labRightPhase)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inRightPhase, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        rightPhasePanelLayout.setVerticalGroup(
                rightPhasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightPhasePanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(rightPhasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labRightPhase)
                                        .addComponent(inRightPhase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        javax.swing.GroupLayout valuesPanelLayout = new javax.swing.GroupLayout(valuesPanel);
        valuesPanel.setLayout(valuesPanelLayout);
        valuesPanelLayout.setHorizontalGroup(
                valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, valuesPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(surfaceLengthPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(surfaceWidthPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(slicesLengthPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(slicesWidthPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(leftHeightPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(leftAmplitudePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(leftWavelengthPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(leftPhasePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(rightHeightPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(rightAmplitudePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(rightWavelengthPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(rightPhasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        valuesPanelLayout.setVerticalGroup(
                valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(valuesPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(surfaceLengthPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(surfaceWidthPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slicesLengthPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slicesWidthPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(leftHeightPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(leftAmplitudePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(leftWavelengthPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(leftPhasePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rightHeightPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rightAmplitudePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rightWavelengthPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rightPhasePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        subtitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        subtitle.setText("This is, at the moment, undocumented and experimental.");

        javax.swing.GroupLayout subtitlePanelLayout = new javax.swing.GroupLayout(subtitlePanel);
        subtitlePanel.setLayout(subtitlePanelLayout);
        subtitlePanelLayout.setHorizontalGroup(
                subtitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(subtitlePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(subtitle, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE))
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
                                .addComponent(descriptionScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                .addComponent(statusScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(generate)
                                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
                statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(statusPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(statusScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(generate))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/sinewave.png")));
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(subtitlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(statusPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(valuesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                        .addComponent(iconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(40, 40, 40))))))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(subtitlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(iconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(valuesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(statusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pack();
    }

    private void inSurfaceLengthFocusGained(java.awt.event.FocusEvent evt) {

        surfaceLengthPanel.setBackground(shaded);
    }

    private void inSurfaceLengthFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void surfaceLengthPanelFocusGained(java.awt.event.FocusEvent evt) {

    }

    private void inLeftAmplitudeFocusGained(java.awt.event.FocusEvent evt) {
        leftAmplitudePanel.setBackground(shaded);
    }

    private void inLeftAmplitudeFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void inSlicesLengthFocusGained(java.awt.event.FocusEvent evt) {

        slicesLengthPanel.setBackground(shaded);
    }

    private void inSlicesLengthFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void generateActionPerformed(java.awt.event.ActionEvent evt) throws IOException {

        generate.setEnabled(false);

        String[] args = {inSurfaceLength.getText(), inSurfaceWidth.getText(), inSlicesLength.getText(), inSlicesWidth.getText(), inLeftHeight.getText(), inLeftAmplitude.getText(), inLeftWavelength.getText(), inLeftPhase.getText(), inRightHeight.getText(), inRightAmplitude.getText(), inRightWavelength.getText(), inRightPhase.getText()};
        status.setText("Generating sine wave...");
        SineWaveGenerator.main(args);


        status.setText("Generated - select save file name and location");

        File saveFile = UlbenUtils.getSaveFile(this, "SINEWAVE");

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

    private void inSurfaceWidthFocusGained(java.awt.event.FocusEvent evt) {

        surfaceWidthPanel.setBackground(shaded);
    }

    private void inSurfaceWidthFocusLost(java.awt.event.FocusEvent evt) {
        checkGenerateOK();
    }

    private void surfaceWidthPanelFocusGained(java.awt.event.FocusEvent evt) {

    }

    private void inLeftHeightFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void inLeftHeightFocusGained(java.awt.event.FocusEvent evt) {

        leftHeightPanel.setBackground(shaded);
    }

    private void inRightWavelengthFocusGained(java.awt.event.FocusEvent evt) {

        rightWavelengthPanel.setBackground(shaded);
    }

    private void inRightWavelengthFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void inRightWavelengthActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inLeftAmplitudeActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inLeftHeightActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inSlicesLengthActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inSurfaceWidthActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inSurfaceLengthActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inLeftWavelengthActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inLeftWavelengthFocusGained(java.awt.event.FocusEvent evt) {

        leftWavelengthPanel.setBackground(shaded);
    }

    private void inLeftWavelengthFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void inLeftPhaseActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inLeftPhaseFocusGained(java.awt.event.FocusEvent evt) {

        leftPhasePanel.setBackground(shaded);
    }

    private void inLeftPhaseFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void inRightHeightActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inRightHeightFocusGained(java.awt.event.FocusEvent evt) {

        rightHeightPanel.setBackground(shaded);
    }

    private void inRightHeightFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void inRightAmplitudeActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inRightAmplitudeFocusGained(java.awt.event.FocusEvent evt) {

        rightAmplitudePanel.setBackground(shaded);
    }

    private void inRightAmplitudeFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void inSlicesWidthActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inSlicesWidthFocusGained(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void inSlicesWidthFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }

    private void slicesWidthPanelFocusGained(java.awt.event.FocusEvent evt) {

    }

    private void inRightPhaseActionPerformed(java.awt.event.ActionEvent evt) {

        checkGenerateOK();
    }

    private void inRightPhaseFocusGained(java.awt.event.FocusEvent evt) {

        rightPhasePanel.setBackground(shaded);
    }

    private void inRightPhaseFocusLost(java.awt.event.FocusEvent evt) {

        checkGenerateOK();
    }


    private void checkGenerateOK() {
        boolean generateOK = true;
        statusPanel.setBackground(normal);
        status.setText("");
        String statusText = "";


        try {
            int temp = Integer.parseInt(inSurfaceLength.getText());

            surfaceLengthPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number for outerRadius\n";
            else
                statusText += ex.getMessage() + "\n";
            surfaceLengthPanel.setBackground(Color.red);
        }


        try {
            int temp = Integer.parseInt(inSurfaceWidth.getText());

            surfaceWidthPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number for outerRadius\n";
            else
                statusText += ex.getMessage() + "\n";
            surfaceWidthPanel.setBackground(Color.red);
        }


        try {
            int temp = Integer.parseInt(inSlicesLength.getText());

            slicesLengthPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number for centreRadius\n";
            else
                statusText += ex.getMessage() + "\n";
            slicesLengthPanel.setBackground(Color.red);
        }


        try {
            int temp = Integer.parseInt(inSlicesWidth.getText());

            slicesWidthPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number for centreRadius\n";
            else
                statusText += ex.getMessage() + "\n";
            slicesWidthPanel.setBackground(Color.red);
        }


        try {
            int temp = Integer.parseInt(inLeftHeight.getText());

            leftHeightPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number for outerThickness\n";
            else
                statusText += ex.getMessage() + "\n";
            leftHeightPanel.setBackground(Color.red);

        }


        try {
            int temp = Integer.parseInt(inLeftAmplitude.getText());

            leftAmplitudePanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            statusPanel.setBackground(Color.yellow);
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number for innerThickness\n";
            else
                statusText += ex.getMessage() + "\n";

            leftAmplitudePanel.setBackground(Color.red);
        }


        try {
            int temp = Integer.parseInt(inLeftWavelength.getText());

            leftWavelengthPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            statusPanel.setBackground(Color.yellow);
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number for centreThickness\n";
            else
                statusText += ex.getMessage() + "\n";

            leftWavelengthPanel.setBackground(Color.red);
        }


        try {
            double temp = Double.parseDouble(inLeftPhase.getText());

            leftPhasePanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            statusPanel.setBackground(Color.yellow);
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number for teeth\n";
            else
                statusText += ex.getMessage() + "\n";

            leftPhasePanel.setBackground(Color.red);
        }


        try {
            int temp = Integer.parseInt(inRightHeight.getText());

            rightHeightPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            statusPanel.setBackground(Color.yellow);
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number for innerToothLengthRatio\n";
            else
                statusText += ex.getMessage() + "\n";

            rightHeightPanel.setBackground(Color.red);
        }


        try {
            int temp = Integer.parseInt(inRightAmplitude.getText());

            rightAmplitudePanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            statusPanel.setBackground(Color.yellow);
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number for innerToothLengthRatio\n";
            else
                statusText += ex.getMessage() + "\n";

            rightAmplitudePanel.setBackground(Color.red);
        }


        try {
            int temp = Integer.parseInt(inRightWavelength.getText());

            rightWavelengthPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number for the offsetAngle\n";
            else
                statusText += ex.getMessage() + "\n";
            rightWavelengthPanel.setBackground(Color.red);
        }


        try {
            double temp = Double.parseDouble(inRightPhase.getText());

            rightPhasePanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number for the offsetAngle\n";
            else
                statusText += ex.getMessage() + "\n";
            rightPhasePanel.setBackground(Color.red);
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
            java.util.logging.Logger.getLogger(SineWaveGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SineWaveGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SineWaveGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SineWaveGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SineWaveGeneratorGUI(new Config()).setVisible(true);
            }
        });
    }

    private javax.swing.JTextArea description;
    private javax.swing.JPanel descriptionPanel;
    private javax.swing.JScrollPane descriptionScrollPane;
    private javax.swing.JLabel descriptionTitle;
    private javax.swing.JButton generate;
    private javax.swing.JLabel icon;
    private javax.swing.JPanel iconPanel;
    private javax.swing.JTextField inLeftAmplitude;
    private javax.swing.JTextField inLeftHeight;
    private javax.swing.JTextField inLeftPhase;
    private javax.swing.JTextField inLeftWavelength;
    private javax.swing.JTextField inRightAmplitude;
    private javax.swing.JTextField inRightHeight;
    private javax.swing.JTextField inRightPhase;
    private javax.swing.JTextField inRightWavelength;
    private javax.swing.JTextField inSlicesLength;
    private javax.swing.JTextField inSlicesWidth;
    private javax.swing.JTextField inSurfaceLength;
    private javax.swing.JTextField inSurfaceWidth;
    private javax.swing.JLabel labLeftAmplitude;
    private javax.swing.JLabel labLeftHeight;
    private javax.swing.JLabel labLeftWavelength;
    private javax.swing.JLabel labRightAmplitude;
    private javax.swing.JLabel labRightHeight;
    private javax.swing.JLabel labRightPhase;
    private javax.swing.JLabel labRightWavelength;
    private javax.swing.JLabel labSlicesLength;
    private javax.swing.JLabel labSlicesWidth;
    private javax.swing.JLabel labSurfaceLength;
    private javax.swing.JLabel labSurfaceWidth;
    private javax.swing.JLabel lableftPhase;
    private javax.swing.JPanel leftAmplitudePanel;
    private javax.swing.JPanel leftHeightPanel;
    private javax.swing.JPanel leftPhasePanel;
    private javax.swing.JPanel leftWavelengthPanel;
    private javax.swing.ButtonGroup options;
    private javax.swing.JPanel rightAmplitudePanel;
    private javax.swing.JPanel rightHeightPanel;
    private javax.swing.JPanel rightPhasePanel;
    private javax.swing.JPanel rightWavelengthPanel;
    private javax.swing.JPanel slicesLengthPanel;
    private javax.swing.JPanel slicesWidthPanel;
    private javax.swing.JTextArea status;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JScrollPane statusScrollPane;
    private javax.swing.JLabel subtitle;
    private javax.swing.JPanel subtitlePanel;
    private javax.swing.JPanel surfaceLengthPanel;
    private javax.swing.JPanel surfaceWidthPanel;
    private javax.swing.JPanel valuesPanel;

}
