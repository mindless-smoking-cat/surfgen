package generators;

public class BowlDoubleGen extends GenerationUtils {
    public static void main(String[] args, String[] args2) {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        BowlGenerator.main(args, 1);
        BowlGenerator.main(args2, 2);
    }
}
