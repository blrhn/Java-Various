package processors.worker;

import processing.Status;
import processing.StatusListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public final class ProcessWorker {
    private static final AtomicInteger counter = new AtomicInteger(0);

    private ProcessWorker() {}

    public static boolean runTask(Worker worker, StatusListener statusListener) {
        int currentCounter = counter.getAndIncrement();
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            try {
                for (int i = 1; i <= 99; i++) {
                    Thread.sleep(10);
                    statusListener.statusChanged(new Status(currentCounter, i));
                }

                worker.work();
                statusListener.statusChanged(new Status(currentCounter, 100));
            } catch (InterruptedException _) {
                Thread.currentThread().interrupt();
            } finally {
                executor.shutdown();
            }
        });

        return true;
    }
}
