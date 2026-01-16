package service;

import service.controller.SimulationController;

public class SimulationService implements IslandAppService {
    private final SimulationController controller;

    public SimulationService(SimulationController controller) {
        this.controller = controller;
    }

    @Override
    public void perform() {
        controller.checkSimulation();
    }
}
