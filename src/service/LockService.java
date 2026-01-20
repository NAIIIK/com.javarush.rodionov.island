package service;

import entity.island.Location;

public final class LockService {
    private static LockService instance;

    private LockService() {}

    public static synchronized LockService getInstance() {
        if (instance == null) instance = new LockService();
        return instance;
    }

    public void withLocationLock(Location location, Runnable action) {
        location.getLock().lock();
        try {
            action.run();
        } finally {
            location.getLock().unlock();
        }
    }

    public void withDoubleLocationLock(Location from, Location to, Runnable action) {
        Location firstLock = from.getId() < to.getId() ? from : to;
        Location secondLock = from.getId() < to.getId() ? to : from;

        firstLock.getLock().lock();
        secondLock.getLock().lock();
        try {
            action.run();
        } finally {
            secondLock.getLock().unlock();
            firstLock.getLock().unlock();
        }
    }
}
