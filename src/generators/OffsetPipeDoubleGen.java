package generators;

public class OffsetPipeDoubleGen extends GenerationUtils {
    public static void main(String[] args, String[] args2) {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        OffsetPipeGenerator.main(args, 1);
        OffsetPipeGenerator.main(args2, 2);
    }
}
