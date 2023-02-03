package generators;

public class PipeDoubleGen2 extends GenerationUtils {
    public static void main(String[] args, String[] args2) {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        PipeGenerator.main(args, 1);
        PipeGenerator.main(args2, 2);
    }
}
