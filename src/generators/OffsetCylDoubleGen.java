package generators;

public class OffsetCylDoubleGen extends GenerationUtils {
    public static void main(String[] args, String[] args2) {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        OffsetCylinderGenerator.main(args, 1);
        OffsetCylinderGenerator.main(args2, 2);
    }
}
