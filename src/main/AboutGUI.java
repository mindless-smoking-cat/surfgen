package main;

public class AboutGUI extends javax.swing.JFrame {

    public AboutGUI() {
        initComponents();
    }

    private void initComponents() {

        aboutPanel = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        descriptionScrollPane = new javax.swing.JScrollPane();
        description = new javax.swing.JTextArea();

        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("About This Program");

        description.setEditable(false);
        description.setColumns(20);
        description.setFont(new java.awt.Font("DejaVu Sans", 0, 12)); // NOI18N
        description.setLineWrap(true);
        description.setRows(5);
        description.setText(
                "2023 update by ulbens with some more useful stuff and personal notes reflecting 2023 jump mapping \n\n" +
                        "Version 0.4 Wednesday 28 November 2012 \n\n" +
                        " This program is a GUI frontend for Rambetter's surface generators. \n\n " +
                        "The surface generator backends (included) are subject to the conditions of use set by the author - look for Rambetter's statement in the distribution package for more details. \n\n " +
                        "This program also contains work by Quark** (the MapFactory class), which is taken from Quark**'s implementation of Rambetter's work, and is used with permission. Quark**'s original work is available from urtjumpers.com. \n\n " +
                        "This program is intended to duplicate the content and functions of Rambetter's web surface generators (now back online at http://mapgen.nerius.com/, but I don't know for how long) and content has been reproduced from this source wherever possible.\n \n " +
                        "This program has been improved thanks to the feedback and suggestions of the following people: gsigms, JohnnyEnglish.\n\n " +
                        "All other coding and design is by thelionroars, and is released under the GPLv3 - see the distribution package for a copy of this license (gpl.txt). \n\n ");

        description.setWrapStyleWord(true);
        descriptionScrollPane.setViewportView(description);

        javax.swing.GroupLayout aboutPanelLayout = new javax.swing.GroupLayout(aboutPanel);
        aboutPanel.setLayout(aboutPanelLayout);
        aboutPanelLayout.setHorizontalGroup(
                aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, aboutPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(descriptionScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                                        .addComponent(title, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        aboutPanelLayout.setVerticalGroup(
                aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(aboutPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(descriptionScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(aboutPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(aboutPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AboutGUI().setVisible(true);
            }
        });
    }

    private javax.swing.JPanel aboutPanel;
    private javax.swing.JTextArea description;
    private javax.swing.JScrollPane descriptionScrollPane;
    private javax.swing.JLabel title;

}
