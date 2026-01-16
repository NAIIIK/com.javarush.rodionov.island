package service.task;

import service.IslandAppService;

public class IslandAppTask implements Runnable {
    private final IslandAppService service;

    public IslandAppTask(IslandAppService service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.perform();
    }
}
