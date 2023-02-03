package generators;

public class FunnelDoubleGen extends GenerationUtils {
    public static void main(String[] args, String[] args2) {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        FunnelGenerator.main(args, 1);
        FunnelGenerator.main(args2, 2);
    }
}
