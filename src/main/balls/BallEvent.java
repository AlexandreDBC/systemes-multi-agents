package main.balls;

import main.events.Event;
import main.events.EventManager;

public class BallEvent extends Event {

    private final Balls balls;
    private final BallsSimulator simulator;
    private final EventManager manager;

    public BallEvent(long date, Balls balls, BallsSimulator simulator, EventManager manager) {
        super(date);
        this.balls = balls;
        this.simulator = simulator;
        this.manager = manager;
    }

    @Override
    public void execute() {
        balls.translate(1, 1);
        simulator.draw();
        manager.addEvent(new BallEvent(getDate() + 1, balls, simulator, manager));
    }
}