package task;

import java.util.concurrent.Phaser;

public class SimulationTask implements Runnable {
    private final Runnable task;
    private final Phaser phaser;

    public SimulationTask(Runnable task, Phaser phaser) {
        this.task = task;
        this.phaser = phaser;
    }

    @Override
    public void run() {
        try {
            task.run();
        } finally {
            phaser.arriveAndDeregister();
        }
    }
}
