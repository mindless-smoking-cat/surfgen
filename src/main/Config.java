
package main;

import java.awt.*;
import java.io.*;


public class Config {

    private final File configFile = new File("surface_generator.config");
    private File workingDirectory;
    private int screenWidth;
    private int screenHeight;


    public Config() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;

        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line = null;

            while ((line = reader.readLine()) != null) {
                String[] token = line.split("\t");
                if (token[0].equals("workingDirectory"))
                    setWorkingDirectory(new File(token[1]));
            }
        } catch (IOException io) {

        }
    }

    private void saveConfig() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(configFile))) {
            if (workingDirectory != null)
                writer.println("workingDirectory\t" + workingDirectory.getAbsolutePath());
        } catch (IOException io) {

        }
    }

    public File getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(File newWorkingDirectory) {
        if (newWorkingDirectory != null & newWorkingDirectory.isDirectory()) {
            workingDirectory = newWorkingDirectory;
            saveConfig();
        }
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }
}
