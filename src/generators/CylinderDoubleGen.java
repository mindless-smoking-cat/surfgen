package generators;

public class CylinderDoubleGen extends GenerationUtils {
    public static void main(String[] args, String[] args2) {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        CylinderGenerator.main(args, 1);
        CylinderShinglesGenerator.main(args2, 2);
    }
}
