package main.automatons.grid;

import main.events.Event;
import main.events.EventManager;

public class GridEvent extends Event {

    private final GridSimulator simulator;
    private final EventManager manager;

    public GridEvent(long date, GridSimulator simulator, EventManager manager) {
        super(date);
        this.simulator = simulator;
        this.manager = manager;
    }

    @Override
    public void execute() {
        simulator.updateState();
        manager.addEvent(new GridEvent(getDate() + 1, simulator, manager));
    }
}