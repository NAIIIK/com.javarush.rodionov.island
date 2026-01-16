package service;

import view.View;

public class StatisticsService implements IslandAppService {
    private final View view;

    public StatisticsService(View view) {
        this.view = view;
    }

    @Override
    public void perform() {
        view.print();
    }
}
