package ulben;

import com.nerius.math.geom.Point3D;
import main.Config;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class UlbenUtils {

    public static String readSetting(String setting) throws FileNotFoundException {

        File file = new File("settings.txt");

        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            if (data.startsWith(setting)) {
                myReader.close();
                try {
                    return data.split(";")[1];
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return null;
    }

    public static String getRandomName(String structure) {
        return structure + "_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
    }

    public static File getSaveFile(JFrame frame, String structure) throws FileNotFoundException {
        File defFolder = null;
        String defName = null;

        File saveFile;

        Config configuration = new Config();

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Quake Map File (.map)", "map");
        chooser.setFileFilter(filter);

        if (UlbenUtils.readSetting("DEF_PATH") != null)
            defFolder = new File(UlbenUtils.readSetting("DEF_PATH"));
        if (UlbenUtils.readSetting("RAND_NAMES") != null)
            defName = UlbenUtils.getRandomName(structure);
        if (UlbenUtils.readSetting("FIXED_NAMES") != null)
            defName = structure;

        int returnValue;

        if (defFolder != null && defName != null) {
            saveFile = new File(defFolder + File.separator + defName);
        } else if (defFolder != null) {
            chooser.setCurrentDirectory(defFolder);
            returnValue = chooser.showSaveDialog(frame);
            if (returnValue == JFileChooser.CANCEL_OPTION || returnValue == JFileChooser.ERROR_OPTION) {
                return null;
            }
            saveFile = chooser.getSelectedFile();
        } else if (defName != null) {
            chooser.setCurrentDirectory(configuration.getWorkingDirectory());
            chooser.setSelectedFile(new File(defName));
            returnValue = chooser.showSaveDialog(frame);
            if (returnValue == JFileChooser.CANCEL_OPTION || returnValue == JFileChooser.ERROR_OPTION) {
                return null;
            }
            saveFile = chooser.getSelectedFile();
        } else {
            if ((configuration.getWorkingDirectory() != null) && (configuration.getWorkingDirectory().isDirectory())) {
                chooser.setCurrentDirectory(configuration.getWorkingDirectory());
                returnValue = chooser.showSaveDialog(frame);
                if (returnValue == JFileChooser.CANCEL_OPTION || returnValue == JFileChooser.ERROR_OPTION) {
                    return null;
                }
            }
            saveFile = chooser.getSelectedFile();
        }
        return saveFile;
    }

    public static Point3D[] addElement(Point3D[] arr, Point3D element) {
        Point3D newArr[] = new Point3D[arr.length + 1];
        newArr[0] = element;
        System.arraycopy(arr, 0, newArr, 1, arr.length);
        return newArr;
    }
}