package hr.fer.zemris.optjava.dz9.ArtificialAnt;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class GameFrame extends JFrame {

    private Object mutex;
    private Canvas canvas;


    public GameFrame(PlayingField playingField, Object mutex){
        super("Game frame");
        this.mutex = mutex;

        JPanel buttonPannel = new JPanel();
        canvas = new Canvas(playingField.x, playingField.y, playingField);
        JButton button = new JButton("Next move!");
        button.setPreferredSize(new Dimension(100, 20));
        button.addActionListener( actionEvent -> {
            synchronized (mutex){
                mutex.notify();
            }
            canvas.repaint();
        });
        buttonPannel.add(button);

        JButton button1 = new JButton("Auto!");
        button1.setPreferredSize(new Dimension(100, 20));
        button1.addActionListener( actionEvent -> {

            button1.setEnabled(false);

            Thread auto = new Thread(() -> {
                while (playingField.moves > 0){
                    synchronized (mutex){
                        mutex.notify();
                    }
                    canvas.repaint();

                    try {
                        sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            auto.start();
        });
        buttonPannel.add(button1);


        add(canvas, BorderLayout.CENTER);
        add(buttonPannel, BorderLayout.SOUTH);
    }

    private static class Canvas extends JPanel{

        private int width;
        private int height;
        private static final int FIELD_SIZE = 15;
        private PlayingField playingField;

        public Canvas(int width, int height, PlayingField pf) {
            this.width = width;
            this.height = height;
            playingField = pf;
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }

        public Dimension getPreferredSize() {
            return new Dimension(width * FIELD_SIZE + 2,height * FIELD_SIZE + 1);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            for(int i = 0; i < height; ++i){
                for (int j = 0; j < width; ++j){

                    if(playingField.getPlayingField()[i][j] == '1'){
                        g.setColor(Color.BLUE);
                    }
                    else if(playingField.getPlayingField()[i][j] == '.'){
                        g.setColor(Color.WHITE);
                    }
                    else if(playingField.getPlayingField()[i][j] == '0'){
                        g.setColor(Color.CYAN);
                    }
                    g.fillRect(1 + j*FIELD_SIZE, 1 + i*FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);
                    g.setColor(Color.BLACK);
                    g.drawRect(1 + j*FIELD_SIZE, 1 + i*FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);
                }
            }
            g.setColor(Color.RED);
            g.fillRect(1 + playingField.getAnt().x *FIELD_SIZE , 1 + playingField.getAnt().y *FIELD_SIZE , FIELD_SIZE, FIELD_SIZE);
            g.setColor(Color.BLACK);
            g.drawRect(1 + playingField.getAnt().x *FIELD_SIZE , 1 + playingField.getAnt().y *FIELD_SIZE , FIELD_SIZE, FIELD_SIZE);


            g.setColor(Color.GRAY);

            switch (playingField.getAnt().direction){
                case 0:
                    g.fillRect(1 + playingField.getAnt().x *FIELD_SIZE  + FIELD_SIZE/3*2, 1 + playingField.getAnt().y *FIELD_SIZE , FIELD_SIZE/3, FIELD_SIZE);
                    break;
                case 1:
                    g.fillRect(1 + playingField.getAnt().x *FIELD_SIZE , 1 + playingField.getAnt().y *FIELD_SIZE + FIELD_SIZE/3*2, FIELD_SIZE, FIELD_SIZE/3);
                    break;
                case 2:
                    g.fillRect(1 + playingField.getAnt().x *FIELD_SIZE , 1 + playingField.getAnt().y *FIELD_SIZE , FIELD_SIZE/3, FIELD_SIZE);
                    break;
                case 3:
                    g.fillRect(1 + playingField.getAnt().x *FIELD_SIZE , 1 + playingField.getAnt().y *FIELD_SIZE , FIELD_SIZE, FIELD_SIZE/3);
                    break;
            }

        }
    }

}
