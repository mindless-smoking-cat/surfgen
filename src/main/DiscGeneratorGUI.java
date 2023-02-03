
package main;

import generators.DiscGenerator;
import tools.MapFactory;
import ulben.NumericTextField;
import ulben.UlbenUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class DiscGeneratorGUI extends javax.swing.JFrame {

    private Config configuration;

    private Color shaded = new Color(200, 200, 200);
    private Color normal = this.getBackground();

    private String radiusTitle = "Radius";
    private String radiusDescription = "The radius of the disc.";

    private String facesTitle = "Faces";
    private String facesDescription = "The number of faces along the circumference on this half-disc. The number of faces on a full disc would be twice this number. If the disc is to be chopped into a quarter disc, you should probably use an even number of faces there.";

    private String heightTitle = "Height";
    private String heightDescription = "How tall the profile of the disc is.";

    private String offsetAngleTitle = "Extrude Radius Factor";
    private String offsetAngleDescription = "The angle, in degrees, by which to rotate the disc counter-clockwise.";

    private String adjustForOverlappingCylinderCutsTitle = "Adjust For Overlapping Cylinder Cuts";
    private String adjustForOverlappingCylinderCutsDescription = "This flag only has an effect if faces is even. If that is the case, use this flag to generate a disc that will align perfectly with a cylinder that was generated with the Cylinder Generator using adjustForOverlappingCylinderCuts.";


    public DiscGeneratorGUI(Config configuration) {
        initComponents();
        this.configuration = configuration;
    }


    @SuppressWarnings("unchecked")

    private void initComponents() {

        options = new javax.swing.ButtonGroup();
        valuesPanel = new javax.swing.JPanel();
        radiusPanel = new javax.swing.JPanel();
        labRadius = new javax.swing.JLabel();
        inRadius = new NumericTextField(64);
        facesPanel = new javax.swing.JPanel();
        labFaces = new javax.swing.JLabel();
        inFaces = new NumericTextField(1);
        heightPanel = new javax.swing.JPanel();
        labHeight = new javax.swing.JLabel();
        inHeight = new NumericTextField(64);
        offsetAnglePanel = new javax.swing.JPanel();
        labOffsetAngle = new javax.swing.JLabel();
        inOffsetAngle = new javax.swing.JTextField();
        adjustForOverlappingCylinderCutsPanel = new javax.swing.JPanel();
        adjustForOverlappingCylinderCuts = new javax.swing.JCheckBox();
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

        setTitle("Disc Generator");
        setLocationByPlatform(true);

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

        labFaces.setLabelFor(inFaces);
        labFaces.setText("faces:");

        inFaces.setText("48");
        inFaces.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inFacesFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inFacesFocusLost(evt);
            }
        });

        javax.swing.GroupLayout facesPanelLayout = new javax.swing.GroupLayout(facesPanel);
        facesPanel.setLayout(facesPanelLayout);
        facesPanelLayout.setHorizontalGroup(
                facesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(facesPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labFaces)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inFaces, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        facesPanelLayout.setVerticalGroup(
                facesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, facesPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(facesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labFaces)
                                        .addComponent(inFaces, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        labHeight.setLabelFor(inHeight);
        labHeight.setText("height:");

        inHeight.setText("64");
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

        adjustForOverlappingCylinderCuts.setText("adjustForOverlappingCylinderCuts");
        adjustForOverlappingCylinderCuts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adjustForOverlappingCylinderCutsActionPerformed(evt);
            }
        });
        adjustForOverlappingCylinderCuts.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                adjustForOverlappingCylinderCutsFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                adjustForOverlappingCylinderCutsFocusLost(evt);
            }
        });

        javax.swing.GroupLayout adjustForOverlappingCylinderCutsPanelLayout = new javax.swing.GroupLayout(adjustForOverlappingCylinderCutsPanel);
        adjustForOverlappingCylinderCutsPanel.setLayout(adjustForOverlappingCylinderCutsPanelLayout);
        adjustForOverlappingCylinderCutsPanelLayout.setHorizontalGroup(
                adjustForOverlappingCylinderCutsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adjustForOverlappingCylinderCutsPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(adjustForOverlappingCylinderCuts)
                                .addContainerGap())
        );
        adjustForOverlappingCylinderCutsPanelLayout.setVerticalGroup(
                adjustForOverlappingCylinderCutsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adjustForOverlappingCylinderCutsPanelLayout.createSequentialGroup()
                                .addGap(0, 10, Short.MAX_VALUE)
                                .addComponent(adjustForOverlappingCylinderCuts, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout valuesPanelLayout = new javax.swing.GroupLayout(valuesPanel);
        valuesPanel.setLayout(valuesPanelLayout);
        valuesPanelLayout.setHorizontalGroup(
                valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(valuesPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(adjustForOverlappingCylinderCutsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(radiusPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(facesPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(heightPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(offsetAnglePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        valuesPanelLayout.setVerticalGroup(
                valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(valuesPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(radiusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(facesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(heightPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(adjustForOverlappingCylinderCutsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(offsetAnglePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        subtitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        subtitle.setText("Generates the top half of a disc as a .map file to be opened in radiant.");

        javax.swing.GroupLayout subtitlePanelLayout = new javax.swing.GroupLayout(subtitlePanel);
        subtitlePanel.setLayout(subtitlePanelLayout);
        subtitlePanelLayout.setHorizontalGroup(
                subtitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(subtitlePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(subtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                .addGroup(descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(descriptionTitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(descriptionPanelLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(descriptionScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                                .addComponent(statusScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(generate)
                                .addGap(18, 18, 18))
        );
        statusPanelLayout.setVerticalGroup(
                statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(statusPanelLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(generate)
                                .addContainerGap(21, Short.MAX_VALUE))
                        .addGroup(statusPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(statusScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addContainerGap())
        );

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/disc.png")));
        icon.setFocusable(false);

        javax.swing.GroupLayout iconPanelLayout = new javax.swing.GroupLayout(iconPanel);
        iconPanel.setLayout(iconPanelLayout);
        iconPanelLayout.setHorizontalGroup(
                iconPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, iconPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(icon, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1))
        );
        iconPanelLayout.setVerticalGroup(
                iconPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(iconPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(icon, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(subtitlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(statusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(valuesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(iconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(subtitlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(iconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(valuesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(statusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void inFacesFocusGained(java.awt.event.FocusEvent evt) {
        facesPanel.setBackground(shaded);
        descriptionTitle.setText(facesTitle);
        description.setText(facesDescription);
    }

    private void inFacesFocusLost(java.awt.event.FocusEvent evt) {
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
        adjustForOverlappingCylinderCutsPanel.setBackground(shaded);
        descriptionTitle.setText(adjustForOverlappingCylinderCutsTitle);
        description.setText(adjustForOverlappingCylinderCutsDescription);
    }

    private void adjustForOverlappingCylinderCutsFocusLost(java.awt.event.FocusEvent evt) {
        adjustForOverlappingCylinderCutsPanel.setBackground(normal);
    }

    private void inOffsetAngleFocusGained(java.awt.event.FocusEvent evt) {
        offsetAnglePanel.setBackground(shaded);
        descriptionTitle.setText(offsetAngleTitle);
        description.setText(offsetAngleDescription);
    }

    private void inOffsetAngleFocusLost(java.awt.event.FocusEvent evt) {
        checkGenerateOK();
    }

    private void generateActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        generate.setEnabled(false);

        String[] args = {inRadius.getText(), inFaces.getText(), inHeight.getText(), Boolean.toString(adjustForOverlappingCylinderCuts.isSelected()), inOffsetAngle.getText()};
        status.setText("Generating disc...");
        DiscGenerator.main(args);

        status.setText("Generated - select save file name and location");

        File saveFile = UlbenUtils.getSaveFile(this, "DISK");

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

    private void adjustForOverlappingCylinderCutsActionPerformed(java.awt.event.ActionEvent evt) {
    }


    private void checkGenerateOK() {
        boolean generateOK = true;
        statusPanel.setBackground(normal);
        status.setText("");
        String statusText = "";

        try {
            int temp = Integer.parseInt(inRadius.getText());

            if (temp <= 1)
                throw new Exception("radius must be at least 1");

            radiusPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number\n";
            else
                statusText += ex.getMessage() + "\n";
            radiusPanel.setBackground(Color.red);
        }

        try {
            int temp = Integer.parseInt(inFaces.getText());
            if (temp < 1)
                throw new Exception("Min faces is 1");
            facesPanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number\n";
            else
                statusText += ex.getMessage() + "\n";
            facesPanel.setBackground(Color.red);

        }

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
            double temp = Double.parseDouble(inOffsetAngle.getText());

            if (temp < 0)
                throw new Exception("Please enter a positive number");

            offsetAnglePanel.setBackground(normal);
        } catch (Exception ex) {
            generateOK = false;
            if (ex instanceof NumberFormatException)
                statusText += "Please enter a number\n";
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
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException ex) {
            java.util.logging.Logger.getLogger(DiscGeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DiscGeneratorGUI(new Config()).setVisible(true);
            }
        });
    }

    private javax.swing.JCheckBox adjustForOverlappingCylinderCuts;
    private javax.swing.JPanel adjustForOverlappingCylinderCutsPanel;
    private javax.swing.JTextArea description;
    private javax.swing.JPanel descriptionPanel;
    private javax.swing.JScrollPane descriptionScrollPane;
    private javax.swing.JLabel descriptionTitle;
    private javax.swing.JPanel facesPanel;
    private javax.swing.JButton generate;
    private javax.swing.JPanel heightPanel;
    private javax.swing.JLabel icon;
    private javax.swing.JPanel iconPanel;
    private javax.swing.JTextField inFaces;
    private javax.swing.JTextField inHeight;
    private javax.swing.JTextField inOffsetAngle;
    private javax.swing.JTextField inRadius;
    private javax.swing.JLabel labFaces;
    private javax.swing.JLabel labHeight;
    private javax.swing.JLabel labOffsetAngle;
    private javax.swing.JLabel labRadius;
    private javax.swing.JPanel offsetAnglePanel;
    private javax.swing.ButtonGroup options;
    private javax.swing.JPanel radiusPanel;
    private javax.swing.JTextArea status;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JScrollPane statusScrollPane;
    private javax.swing.JLabel subtitle;
    private javax.swing.JPanel subtitlePanel;
    private javax.swing.JPanel valuesPanel;
}
