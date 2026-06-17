# Island Simulation - Multithreading Project

A multithreaded island ecosystem simulation written in Java.
Animals eat, move, breed, and die across a grid of locations,
all driven by a concurrent tick-based engine.

---

## Architecture

The simulation runs on a tick cycle managed by **ScheduledExecutorService**.
Each tick launches three parallel tasks via **ExecutorService** + **Phaser**:

| Task | Responsibility |
|---|---|
| AnimalActivityTask | Each animal eats, moves, breeds, or dies |
| PlantGrowthTask | Plants grow across all locations |
| StatisticsTask | Console output via ConsoleUI |

**Phaser** synchronizes all tasks — the next tick only starts after all three complete.

---

## Concurrency

- *ScheduledExecutorService* — fixed-rate tick loop
- *ExecutorService* (thread pool) — parallel task execution per tick
- *Phaser* — barrier synchronization between tasks
- *__AtomicBoolean__ alive* — thread-safe animal death flag
- *__volatile__ Location* — visibility guarantee for animal position across threads

---

## Entity Model

Animal hierarchy:

Animal → Herbivore / Carnivore → concrete species (e.g.`Wolf`, `Rabbit`)

- Herbivores eat Plant only
- Carnivores eat any Animal of a different species
- Each animal has satiety — decreases each tick, animal dies if too low
- Actions: eat(), move(), breed(), die()

Island:
- Singleton Island with a grid of Location cells
- Animals and plants are distributed across locations

---

## Key Design Decisions

- **Singleton** pattern for `Island`, `AnimalService`, `PlantService`
- Strategy-like behavior via `canEat(Eatable food)` polymorphism
- Graceful shutdown — waits up to 10s for threads to finish before force-stopping
- Animal stats (weight, speed, hunger) loaded from `Settings` via AnimalStat

---

## Getting Started

1. Clone the repository

2. Run the application

Open in IntelliJ IDEA and run src/app/Application.java

---

## Stack

Java • OOP • Multithreading • ExecutorService • Phaser • ScheduledExecutorService
