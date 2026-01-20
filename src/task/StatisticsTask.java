package task;

import view.View;

public class StatisticsTask implements Runnable {
    private final View view;

    public StatisticsTask(View view) {
        this.view = view;
    }

    @Override
    public void run() {
        view.print();
    }
}
