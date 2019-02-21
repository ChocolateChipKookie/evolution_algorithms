package hr.fer.zemris.optjava.dz11.drawing_window;

import hr.fer.zemris.optjava.dz11.genetic_algorithm.RectangleGenome;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class DrawFrame extends JFrame {
    private Object mutex;
    private Canvas canvas;

    public DrawFrame(int width, int height, int zoom){
        super("Draw frame");
        JPanel buttonPannel = new JPanel();
        canvas = new Canvas(width, height, zoom);

        add(canvas, BorderLayout.CENTER);
        add(buttonPannel, BorderLayout.SOUTH);
    }

    public void drawGenome(RectangleGenome rg){
        canvas.setImage(rg);
        canvas.repaint();
    }

    public void drawIteration(int iteration){
        canvas.setCurrentIteration(iteration);
        canvas.repaint();
    }
    public void drawScore(int score){
        canvas.setCurrentScore(score);
        canvas.repaint();
    }
}
