
package main;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;


public class SurfaceGenerator extends javax.swing.JFrame {

    private final Dimension smallButton = new Dimension(174, 144);
    private final Dimension smallPanel = new Dimension(820, 490);
    private final Dimension largeButton = new Dimension(255, 209);
    private final Dimension largePanel = new Dimension(1074, 705);

    private Dimension frameSize;
    private int frameWidth;
    private int frameHeight;

    private CylinderGeneratorGUI cylinderGenerator;
    private PipeGeneratorGUI pipeGenerator;
    private DiscGeneratorGUI discGenerator;
    private BowlGeneratorGUI bowlGenerator;
    private CorkscrewGeneratorGUI corkscrewGenerator;
    private FunnelGeneratorGUI funnelGenerator;
    private OffsetCylinderGeneratorGUI offsetCylinderGenerator;
    private OffsetPipeGeneratorGUI offsetPipeGenerator;
    private GearGeneratorGUI gearGenerator;
    private SineWaveGeneratorGUI sineWaveGenerator;
    private VolcanoGeneratorGUI volcanoGenerator;
    private AboutGUI aboutPage;
    private SettingsGUI settingsPage;

    Config configuration = new Config();
    int screenWidth = configuration.getScreenWidth();
    int screenHeight = configuration.getScreenHeight();


    public SurfaceGenerator() {
        initComponents();
        frameSize = getSize();
        frameWidth = frameSize.width;
        frameHeight = frameSize.height;


        if (frameWidth > screenWidth || frameHeight > screenHeight)
            setSmallSize();

    }


    @SuppressWarnings("unchecked")

    private void initComponents() {

        view = new javax.swing.ButtonGroup();
        selectionPanel = new javax.swing.JPanel();
        selectCylinderGenerator = new javax.swing.JButton();
        selectDiscGenerator = new javax.swing.JButton();
        selectPipeGenerator = new javax.swing.JButton();
        selectBowlGenerator = new javax.swing.JButton();
        selectCorkscrewGenerator = new javax.swing.JButton();
        selectFunnelGenerator = new javax.swing.JButton();
        selectOffsetCylinderGenerator = new javax.swing.JButton();
        selectOffsetPipeGenerator = new javax.swing.JButton();
        selectGearGenerator = new javax.swing.JButton();
        selectSineWaveGenerator = new javax.swing.JButton();
        selectVolcanoGenerator = new javax.swing.JButton();
        selectSettings = new javax.swing.JButton();
        filler = new javax.swing.Box.Filler(new java.awt.Dimension(255, 209), new java.awt.Dimension(255, 209), new java.awt.Dimension(255, 209));
        menu = new javax.swing.JMenuBar();
        settingsMenu = new javax.swing.JMenu();
        about = new javax.swing.JMenuItem();
        settings = new JMenuItem();
        View = new javax.swing.JMenu();
        opSmallIcons = new javax.swing.JMenuItem();
        opLargeIcons = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Surface Generator");
        setLocationByPlatform(true);

        selectionPanel.setMinimumSize(new java.awt.Dimension(1092, 705));
        selectionPanel.setPreferredSize(new java.awt.Dimension(1092, 705));
        selectionPanel.setLayout(new java.awt.GridLayout(3, 4, 18, 18));

        selectCylinderGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/cylinder.png")));
        selectCylinderGenerator.setText("Generate Cylinder (*)");
        selectCylinderGenerator.setToolTipText("Generates a quarter cylinder as a .map file to be opened in radiant.");
        selectCylinderGenerator.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selectCylinderGenerator.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectCylinderGenerator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                selectCylinderGeneratorMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                selectCylinderGeneratorMouseExited(evt);
            }
        });
        selectCylinderGenerator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectCylinderGeneratorActionPerformed(evt);
            }
        });
        selectionPanel.add(selectCylinderGenerator);

        selectDiscGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/disc.png")));
        selectDiscGenerator.setText("Generate Disc (*)");
        selectDiscGenerator.setToolTipText("Generates the top half of a disc as a .map file to be opened in radiant.");
        selectDiscGenerator.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selectDiscGenerator.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectDiscGenerator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                selectDiscGeneratorMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                selectDiscGeneratorMouseExited(evt);
            }
        });
        selectDiscGenerator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectDiscGeneratorActionPerformed(evt);
            }
        });
        selectionPanel.add(selectDiscGenerator);

        selectPipeGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/quarterPipeIcon.png")));
        selectPipeGenerator.setText("Generate Pipe (*)");
        selectPipeGenerator.setToolTipText("Generates a quarter pipe ramp as a .map file to be opened in radiant.");
        selectPipeGenerator.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selectPipeGenerator.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectPipeGenerator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                selectPipeGeneratorMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                selectPipeGeneratorMouseExited(evt);
            }
        });
        selectPipeGenerator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectPipeGeneratorActionPerformed(evt);
            }
        });
        selectionPanel.add(selectPipeGenerator);

        selectBowlGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/bowl.png")));
        selectBowlGenerator.setText("Generate Bowl (*)");
        selectBowlGenerator.setToolTipText("Generates the rim of a quarter bowl as a .map file to be opened in radiant.");
        selectBowlGenerator.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selectBowlGenerator.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectBowlGenerator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                selectBowlGeneratorMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                selectBowlGeneratorMouseExited(evt);
            }
        });
        selectBowlGenerator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectBowlGeneratorActionPerformed(evt);
            }
        });
        selectionPanel.add(selectBowlGenerator);

        selectCorkscrewGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/corkscrew.png")));
        selectCorkscrewGenerator.setText("Generate Corkscrew");
        selectCorkscrewGenerator.setToolTipText("Generates a quarter of a corkscrew as a .map file to be opened in radiant.");
        selectCorkscrewGenerator.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selectCorkscrewGenerator.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectCorkscrewGenerator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                selectCorkscrewGeneratorMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                selectCorkscrewGeneratorMouseExited(evt);
            }
        });
        selectCorkscrewGenerator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectCorkscrewGeneratorActionPerformed(evt);
            }
        });
        selectionPanel.add(selectCorkscrewGenerator);

        selectFunnelGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/funnel.png")));
        selectFunnelGenerator.setText("Generate Funnel (*)");
        selectFunnelGenerator.setToolTipText("Generates a quarter of a funnel as a .map file to be opened in radiant.");
        selectFunnelGenerator.setFocusCycleRoot(true);
        selectFunnelGenerator.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selectFunnelGenerator.setMaximumSize(new java.awt.Dimension(255, 209));
        selectFunnelGenerator.setMinimumSize(new java.awt.Dimension(255, 209));
        selectFunnelGenerator.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectFunnelGenerator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                selectFunnelGeneratorMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                selectFunnelGeneratorMouseExited(evt);
            }
        });
        selectFunnelGenerator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectFunnelGeneratorActionPerformed(evt);
            }
        });
        selectionPanel.add(selectFunnelGenerator);

        selectOffsetCylinderGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/offsetCylinder.png")));
        selectOffsetCylinderGenerator.setText("Generate Offset Cylinder (*)");
        selectOffsetCylinderGenerator.setToolTipText("The vertices are rotated counter-clockwise by a half of a slice.");
        selectOffsetCylinderGenerator.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selectOffsetCylinderGenerator.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectOffsetCylinderGenerator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                selectOffsetCylinderGeneratorMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                selectOffsetCylinderGeneratorMouseExited(evt);
            }
        });
        selectOffsetCylinderGenerator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectOffsetCylinderGeneratorActionPerformed(evt);
            }
        });
        selectionPanel.add(selectOffsetCylinderGenerator);

        selectOffsetPipeGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/offsetPipe.png")));
        selectOffsetPipeGenerator.setText("Generate Offset Pipe (*)");
        selectOffsetPipeGenerator.setToolTipText("The vertices for the curve are rotated downwards by half of a slice.");
        selectOffsetPipeGenerator.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selectOffsetPipeGenerator.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectOffsetPipeGenerator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                selectOffsetPipeGeneratorMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                selectOffsetPipeGeneratorMouseExited(evt);
            }
        });
        selectOffsetPipeGenerator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectOffsetPipeGeneratorActionPerformed(evt);
            }
        });
        selectionPanel.add(selectOffsetPipeGenerator);

        selectGearGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/gear.png")));
        selectGearGenerator.setText("Generate Gear");
        selectGearGenerator.setToolTipText("Generates a gear as a .map file to be opened in radiant.");
        selectGearGenerator.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selectGearGenerator.setMaximumSize(new java.awt.Dimension(255, 209));
        selectGearGenerator.setMinimumSize(new java.awt.Dimension(255, 209));
        selectGearGenerator.setPreferredSize(new java.awt.Dimension(255, 209));
        selectGearGenerator.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectGearGenerator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectGearGeneratorActionPerformed(evt);
            }
        });
        selectionPanel.add(selectGearGenerator);

        selectSineWaveGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/sinewave.png")));
        selectSineWaveGenerator.setText("Generate Sine Wave");
        selectSineWaveGenerator.setToolTipText("This is, at the moment, undocumented and experimental.");
        selectSineWaveGenerator.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selectSineWaveGenerator.setMaximumSize(new java.awt.Dimension(255, 209));
        selectSineWaveGenerator.setMinimumSize(new java.awt.Dimension(255, 209));
        selectSineWaveGenerator.setPreferredSize(new java.awt.Dimension(255, 209));
        selectSineWaveGenerator.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectSineWaveGenerator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectSineWaveGeneratorActionPerformed(evt);
            }
        });
        selectionPanel.add(selectSineWaveGenerator);

        selectVolcanoGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/volcano.png")));
        selectVolcanoGenerator.setText("Generate Volcano");
        selectVolcanoGenerator.setToolTipText("Generates a quarter of a volcano shape, which is actually the inner rim of the inside of a donut. The .map file that is generated is to be opened in radiant.");
        selectVolcanoGenerator.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selectVolcanoGenerator.setMaximumSize(new java.awt.Dimension(255, 209));
        selectVolcanoGenerator.setMinimumSize(new java.awt.Dimension(255, 209));
        selectVolcanoGenerator.setPreferredSize(new java.awt.Dimension(255, 209));
        selectVolcanoGenerator.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectVolcanoGenerator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectVolcanoGeneratorActionPerformed(evt);
            }
        });
        selectionPanel.add(selectVolcanoGenerator);

        filler.setFocusable(false);
        selectionPanel.add(filler);

        settingsMenu.setText("Settings");

        settings.setText("Settings");
        settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    settingsActionPerformed(evt);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });


        about.setText("About This Program");
        about.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutActionPerformed(evt);
            }
        });

        settingsMenu.add(settings);
        settingsMenu.add(about);

        menu.add(settingsMenu);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(selectionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(selectionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void selectCylinderGeneratorActionPerformed(java.awt.event.ActionEvent evt) {
        if (cylinderGenerator == null)
            cylinderGenerator = new CylinderGeneratorGUI(configuration);
        cylinderGenerator.setVisible(true);

    }

    private void aboutActionPerformed(java.awt.event.ActionEvent evt) {
        if (aboutPage == null)
            aboutPage = new AboutGUI();
        aboutPage.setVisible(true);
    }

    private void settingsActionPerformed(java.awt.event.ActionEvent evt) throws FileNotFoundException {
        if (settingsPage == null)
            settingsPage = new SettingsGUI();
        settingsPage.setVisible(true);
    }

    private void selectDiscGeneratorActionPerformed(java.awt.event.ActionEvent evt) {
        if (discGenerator == null)
            discGenerator = new DiscGeneratorGUI(configuration);
        discGenerator.setVisible(true);
    }

    private void selectDiscGeneratorMouseEntered(java.awt.event.MouseEvent evt) {
    }

    private void selectDiscGeneratorMouseExited(java.awt.event.MouseEvent evt) {
    }

    private void selectCylinderGeneratorMouseEntered(java.awt.event.MouseEvent evt) {
    }

    private void selectCylinderGeneratorMouseExited(java.awt.event.MouseEvent evt) {
    }

    private void selectPipeGeneratorMouseEntered(java.awt.event.MouseEvent evt) {
    }

    private void selectPipeGeneratorMouseExited(java.awt.event.MouseEvent evt) {
    }

    private void selectPipeGeneratorActionPerformed(java.awt.event.ActionEvent evt) {
        if (pipeGenerator == null)
            pipeGenerator = new PipeGeneratorGUI(configuration);
        pipeGenerator.setVisible(true);
    }

    private void selectBowlGeneratorMouseEntered(java.awt.event.MouseEvent evt) {
    }

    private void selectBowlGeneratorMouseExited(java.awt.event.MouseEvent evt) {
    }

    private void selectBowlGeneratorActionPerformed(java.awt.event.ActionEvent evt) {
        if (bowlGenerator == null)
            bowlGenerator = new BowlGeneratorGUI(configuration);
        bowlGenerator.setVisible(true);
    }

    private void selectCorkscrewGeneratorMouseEntered(java.awt.event.MouseEvent evt) {
    }

    private void selectCorkscrewGeneratorMouseExited(java.awt.event.MouseEvent evt) {
    }

    private void selectCorkscrewGeneratorActionPerformed(java.awt.event.ActionEvent evt) {
        if (corkscrewGenerator == null)
            corkscrewGenerator = new CorkscrewGeneratorGUI(configuration);
        corkscrewGenerator.setVisible(true);
    }

    private void selectFunnelGeneratorMouseEntered(java.awt.event.MouseEvent evt) {
    }

    private void selectFunnelGeneratorMouseExited(java.awt.event.MouseEvent evt) {
    }

    private void selectFunnelGeneratorActionPerformed(java.awt.event.ActionEvent evt) {
        if (funnelGenerator == null)
            funnelGenerator = new FunnelGeneratorGUI(configuration);
        funnelGenerator.setVisible(true);
    }

    private void selectOffsetCylinderGeneratorMouseEntered(java.awt.event.MouseEvent evt) {
    }

    private void selectOffsetCylinderGeneratorMouseExited(java.awt.event.MouseEvent evt) {
    }

    private void selectOffsetCylinderGeneratorActionPerformed(java.awt.event.ActionEvent evt) {
        if (offsetCylinderGenerator == null)
            offsetCylinderGenerator = new OffsetCylinderGeneratorGUI(configuration);
        offsetCylinderGenerator.setVisible(true);
    }

    private void selectOffsetPipeGeneratorMouseEntered(java.awt.event.MouseEvent evt) {
    }

    private void selectOffsetPipeGeneratorMouseExited(java.awt.event.MouseEvent evt) {
    }

    private void selectOffsetPipeGeneratorActionPerformed(java.awt.event.ActionEvent evt) {
        if (offsetPipeGenerator == null)
            offsetPipeGenerator = new OffsetPipeGeneratorGUI(configuration);
        offsetPipeGenerator.setVisible(true);
    }

    private void selectGearGeneratorActionPerformed(java.awt.event.ActionEvent evt) {
        if (gearGenerator == null)
            gearGenerator = new GearGeneratorGUI(configuration);
        gearGenerator.setVisible(true);
    }

    private void selectSineWaveGeneratorActionPerformed(java.awt.event.ActionEvent evt) {
        if (sineWaveGenerator == null)
            sineWaveGenerator = new SineWaveGeneratorGUI(configuration);
        sineWaveGenerator.setVisible(true);
    }

    private void selectVolcanoGeneratorActionPerformed(java.awt.event.ActionEvent evt) {
        if (volcanoGenerator == null)
            volcanoGenerator = new VolcanoGeneratorGUI(configuration);
        volcanoGenerator.setVisible(true);
    }

    private void setSmallSize() {
        selectDiscGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/disc_sm.png")));
        selectDiscGenerator.setMaximumSize(smallButton);
        selectDiscGenerator.setMinimumSize(smallButton);
        selectDiscGenerator.setPreferredSize(smallButton);

        selectPipeGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/quarterPipeIcon_sm.png")));
        selectPipeGenerator.setMaximumSize(smallButton);
        selectPipeGenerator.setMinimumSize(smallButton);
        selectPipeGenerator.setPreferredSize(smallButton);

        selectCylinderGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/cylinder_sm.png")));
        selectCylinderGenerator.setMaximumSize(smallButton);
        selectCylinderGenerator.setMinimumSize(smallButton);
        selectCylinderGenerator.setPreferredSize(smallButton);

        selectFunnelGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/funnel_sm.png")));
        selectFunnelGenerator.setMaximumSize(smallButton);
        selectFunnelGenerator.setMinimumSize(smallButton);
        selectFunnelGenerator.setPreferredSize(smallButton);

        selectCorkscrewGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/corkscrew_sm.png")));
        selectCorkscrewGenerator.setMaximumSize(smallButton);
        selectCorkscrewGenerator.setMinimumSize(smallButton);
        selectCorkscrewGenerator.setPreferredSize(smallButton);

        selectBowlGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/bowl_sm.png")));
        selectBowlGenerator.setMaximumSize(smallButton);
        selectBowlGenerator.setMinimumSize(smallButton);
        selectBowlGenerator.setPreferredSize(smallButton);

        selectOffsetPipeGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/offsetPipe_sm.png")));
        selectOffsetPipeGenerator.setMaximumSize(smallButton);
        selectOffsetPipeGenerator.setMinimumSize(smallButton);
        selectOffsetPipeGenerator.setPreferredSize(smallButton);

        selectOffsetCylinderGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/offsetCylinder_sm.png")));
        selectOffsetCylinderGenerator.setMaximumSize(smallButton);
        selectOffsetCylinderGenerator.setMinimumSize(smallButton);
        selectOffsetCylinderGenerator.setPreferredSize(smallButton);

        selectGearGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/gear_sm.png")));
        selectGearGenerator.setMaximumSize(smallButton);
        selectGearGenerator.setMinimumSize(smallButton);
        selectGearGenerator.setPreferredSize(smallButton);

        selectSineWaveGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/sinewave_sm.png")));
        selectSineWaveGenerator.setMaximumSize(smallButton);
        selectSineWaveGenerator.setMinimumSize(smallButton);
        selectSineWaveGenerator.setPreferredSize(smallButton);

        selectVolcanoGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/volcano_sm.png")));
        selectVolcanoGenerator.setMaximumSize(smallButton);
        selectVolcanoGenerator.setMinimumSize(smallButton);
        selectVolcanoGenerator.setPreferredSize(smallButton);

        filler.setMaximumSize(smallButton);
        filler.setMinimumSize(smallButton);
        filler.setPreferredSize(smallButton);

        selectionPanel.setMaximumSize(smallPanel);
        selectionPanel.setMinimumSize(smallPanel);
        selectionPanel.setPreferredSize(smallPanel);

        pack();
    }

    private void setLargeSize() {
        selectDiscGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/disc.png")));
        selectDiscGenerator.setMaximumSize(largeButton);
        selectDiscGenerator.setMinimumSize(largeButton);
        selectDiscGenerator.setPreferredSize(largeButton);

        selectPipeGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/quarterPipeIcon.png")));
        selectPipeGenerator.setMaximumSize(largeButton);
        selectPipeGenerator.setMinimumSize(largeButton);
        selectPipeGenerator.setPreferredSize(largeButton);

        selectCylinderGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/cylinder.png")));
        selectCylinderGenerator.setMaximumSize(largeButton);
        selectCylinderGenerator.setMinimumSize(largeButton);
        selectCylinderGenerator.setPreferredSize(largeButton);

        selectFunnelGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/funnel.png")));
        selectFunnelGenerator.setMaximumSize(largeButton);
        selectFunnelGenerator.setMinimumSize(largeButton);
        selectFunnelGenerator.setPreferredSize(largeButton);

        selectCorkscrewGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/corkscrew.png")));
        selectCorkscrewGenerator.setMaximumSize(largeButton);
        selectCorkscrewGenerator.setMinimumSize(largeButton);
        selectCorkscrewGenerator.setPreferredSize(largeButton);

        selectBowlGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/bowl.png")));
        selectBowlGenerator.setMaximumSize(largeButton);
        selectBowlGenerator.setMinimumSize(largeButton);
        selectBowlGenerator.setPreferredSize(largeButton);

        selectOffsetPipeGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/offsetPipe.png")));
        selectOffsetPipeGenerator.setMaximumSize(largeButton);
        selectOffsetPipeGenerator.setMinimumSize(largeButton);
        selectOffsetPipeGenerator.setPreferredSize(largeButton);

        selectOffsetCylinderGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/offsetCylinder.png")));
        selectOffsetCylinderGenerator.setMaximumSize(largeButton);
        selectOffsetCylinderGenerator.setMinimumSize(largeButton);
        selectOffsetCylinderGenerator.setPreferredSize(largeButton);

        selectGearGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/gear.png")));
        selectGearGenerator.setMaximumSize(largeButton);
        selectGearGenerator.setMinimumSize(largeButton);
        selectGearGenerator.setPreferredSize(largeButton);

        selectSineWaveGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/sinewave.png")));
        selectSineWaveGenerator.setMaximumSize(largeButton);
        selectSineWaveGenerator.setMinimumSize(largeButton);
        selectSineWaveGenerator.setPreferredSize(largeButton);

        selectVolcanoGenerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/volcano.png")));
        selectVolcanoGenerator.setMaximumSize(largeButton);
        selectVolcanoGenerator.setMinimumSize(largeButton);
        selectVolcanoGenerator.setPreferredSize(largeButton);

        filler.setMaximumSize(largeButton);
        filler.setMinimumSize(largeButton);
        filler.setPreferredSize(largeButton);

        selectionPanel.setMaximumSize(largePanel);
        selectionPanel.setMinimumSize(largePanel);
        selectionPanel.setPreferredSize(largePanel);

        pack();
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
            java.util.logging.Logger.getLogger(SurfaceGenerator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SurfaceGenerator system = new SurfaceGenerator();
                system.setVisible(true);
            }
        });
    }

    private javax.swing.JMenu View;
    private javax.swing.JMenuItem about;
    private javax.swing.JMenuItem settings;
    private javax.swing.JMenu settingsMenu;
    private javax.swing.Box.Filler filler;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenuItem opLargeIcons;
    private javax.swing.JMenuItem opSmallIcons;
    private javax.swing.JButton selectBowlGenerator;
    private javax.swing.JButton selectCorkscrewGenerator;
    private javax.swing.JButton selectCylinderGenerator;
    private javax.swing.JButton selectDiscGenerator;
    private javax.swing.JButton selectFunnelGenerator;
    private javax.swing.JButton selectGearGenerator;
    private javax.swing.JButton selectOffsetCylinderGenerator;
    private javax.swing.JButton selectOffsetPipeGenerator;
    private javax.swing.JButton selectPipeGenerator;
    private javax.swing.JButton selectSineWaveGenerator;
    private javax.swing.JButton selectVolcanoGenerator;
    private javax.swing.JButton selectSettings;
    private javax.swing.JPanel selectionPanel;
    private javax.swing.ButtonGroup view;

}
