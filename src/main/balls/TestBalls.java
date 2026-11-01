package main.balls;

import gui.GUISimulator;

import java.awt.*;

/**
 * Test le package des balles
 */
public class TestBalls {
    public static void main(String[] args) {
        /*
        Ball redBall = new Ball(10, 10, 30, Color.red);
        Ball greenBall = new Ball(20, 60, 10, Color.green);
        Ball orangeBall = new Ball(10, 50, 20, Color.ORANGE);
        Ball pinkBall = new Ball(100, 60, 40, Color.pink);
        Ball ball = new Ball(70, 30);
        */
        Balls balls = new Balls(); //ball, redBall, greenBall, orangeBall, pinkBall);
        for (int i = 0; i < 150; i++) {
            balls.add(new Ball(i * 3, i * 3, 1 + i / 4, Color.white));
        }

        GUISimulator gui = new GUISimulator(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, Color.BLACK);
        BallsSimulator ballsSimulator = new BallsSimulator(gui, balls);

        gui.setSimulable(ballsSimulator);
    }
}