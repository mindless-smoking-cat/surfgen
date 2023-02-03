package main;

import ulben.UlbenUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SettingsGUI extends JFrame {
    private JLabel pathLabel;
    private JTextField pathTextField;
    private JButton pathButton;
    private JPanel rootPanel;
    private JCheckBox useRandomNamesCheckbox;
    private JPanel defaultnamePanel;
    private JPanel defPathPanel;
    private JButton saveButton;
    private JPanel savePanel;
    private JPanel roundPanel;
    private JCheckBox roundCheckbox;
    private JPanel openPanel;
    private JCheckBox openCheckbox;
    private JPanel fixednamePanel;
    private JCheckBox fixednameCheckBox;

    public SettingsGUI() throws FileNotFoundException {
        super();
        pack();
        setContentPane(rootPanel);

        setTitle("Settings");
        setSize(500, 250);

        compileSettings();

        pathButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new java.io.File("."));
                fileChooser.setDialogTitle("Choose default output folder");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    pathTextField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    File file = new File("settings.txt");
                    Writer fileWriter = new FileWriter(file, false);
                    fileWriter.append("DEF_PATH;").append(pathTextField.getText()).append("\n");
                    if (useRandomNamesCheckbox.isSelected())
                        fileWriter.append("RAND_NAMES;").append("Y").append("\n");
                    if (fixednameCheckBox.isSelected())
                        fileWriter.append("FIXED_NAMES;").append("Y").append("\n");
                    if (roundCheckbox.isSelected())
                        fileWriter.append("ROUND;").append("Y").append("\n");
                    if (openCheckbox.isSelected())
                        fileWriter.append("OPEN;").append("Y").append("\n");
//                    if (!textureTextField.getText().equals(""))
//                        fileWriter.append("TEXTURE;").append(textureTextField.getText()).append("\n");
                    fileWriter.close();
                    setVisible(false);
                    compileSettings();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void compileSettings() throws FileNotFoundException {
        pathTextField.setText(UlbenUtils.readSetting("DEF_PATH"));
//        if (UlbenUtils.readSetting("TEXTURE") == null)
//            textureTextField.setText("common/caulk");
//        else
//            textureTextField.setText(UlbenUtils.readSetting("TEXTURE"));
        if (UlbenUtils.readSetting("RAND_NAMES") != null)
            useRandomNamesCheckbox.setSelected(true);
        if (UlbenUtils.readSetting("FIXED_NAMES") != null)
            fixednameCheckBox.setSelected(true);
        if (UlbenUtils.readSetting("ROUND") != null)
            roundCheckbox.setSelected(true);
        if (UlbenUtils.readSetting("OPEN") != null)
            openCheckbox.setSelected(true);

    }

}
