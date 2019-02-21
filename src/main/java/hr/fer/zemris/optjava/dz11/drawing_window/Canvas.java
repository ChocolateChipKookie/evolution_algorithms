package hr.fer.zemris.optjava.dz11.drawing_window;

import hr.fer.zemris.optjava.dz11.gray_scale_image.Bounds;
import hr.fer.zemris.optjava.dz11.genetic_algorithm.RectangleGenome;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {

    private int width;
    private int height;
    private int zoom;
    private RectangleGenome currentPicture = RectangleGenome.random(0, 0, new Bounds(0, 0));
    private int currentIteration = 0;
    private JLabel iterationLabel;
    private int currentScore = 0;
    private JLabel scoreLabel;


    public Canvas(int width, int height, int zoom) {
        this.width = width;
        this.height = height;
        this.zoom = zoom;
        iterationLabel = new JLabel("0");
        iterationLabel.setBounds(10, 0, 100, 30);
        iterationLabel.setFont(new Font("Verdana",1,12));
        add(iterationLabel);
        scoreLabel = new JLabel("0");
        scoreLabel.setBounds(width * zoom - 115, 0, 115, 30);
        scoreLabel.setFont(new Font("Verdana",1,12));
        add(scoreLabel);
        setLayout(null);
    }

    public Dimension getPreferredSize() {
        return new Dimension(width*zoom, height*zoom);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int[] genome = currentPicture.getGenome();
        g.setColor(getGrayScale(genome[genome.length - 1]));
        g.fillRect(0, 0, width*zoom, height*zoom);

        for(int i = 0; i < currentPicture.rectangles; ++i){
            int pos = i*5;
            g.setColor(getGrayScale(genome[pos + 4]));
            g.fillRect(genome[pos]*zoom, genome[pos + 1]*zoom, genome[pos + 2]*zoom, genome[pos + 3]*zoom);
        }
    }

    public void setImage(RectangleGenome image){
        this.currentPicture = image;
    }

    public void setCurrentIteration(int iteration){
        this.currentIteration = iteration;
        if(iteration == -1){
            iterationLabel.setText("FINISHED");
        }
        else{
            iterationLabel.setText("Iter: " + String.valueOf(iteration));
        }
    }

    public void setCurrentScore(int score){
        this.currentIteration = score;
        scoreLabel.setText("Score: " + String.valueOf(score));
    }

    private static Color getGrayScale(int i){
        return new Color(i, i, i);
    }
}
