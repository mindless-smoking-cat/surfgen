package tools;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author Quark**
 */
public class MapFactory {

    static String lines = "";

    public static void newText() {
        lines = "";
    }

    public static void addLine(String line) {
        String newLine = System.getProperty("line.separator");
        lines += newLine + line;
    }

    public static void add(String string) {
        lines += string;
    }

    public static void saveText(String filename) {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
            pw.println(lines);
            pw.close();

        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
    }
}
