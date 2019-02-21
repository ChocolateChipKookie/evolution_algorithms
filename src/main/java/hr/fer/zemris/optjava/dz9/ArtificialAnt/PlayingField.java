package hr.fer.zemris.optjava.dz9.ArtificialAnt;

import hr.fer.zemris.optjava.dz9.Function;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PlayingField implements Function<ArtificialAntGPTree> {

    protected byte[][] playingField;
    protected byte[][] testingField;
    protected Ant ant;
    public final int x;
    public final int y;
    protected int startMoves;
    protected int moves;
    protected int score;
    InputWrapper inputWrapper;

    private boolean displayMode = false;
    public Object mutex = new Object();

    public PlayingField(String file, int moves){
        this.startMoves = moves;
        ant = new Ant();
        int x = 0, y = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(file))){

            String[] tmp = br.readLine().split("x");

            x = Integer.parseInt(tmp[0]);
            y = Integer.parseInt(tmp[1]);

            playingField = new byte[y][x];

            String s;
            for(int i = 0; i < y; ++i){
                s = br.readLine();
                playingField[i] = s.getBytes();
            }
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }

        this.x = x;
        this.y = y;
    }

    public PlayingField(PlayingField playingField){
        this.playingField = playingField.playingField;
        this.ant = playingField.ant;
        this.x = playingField.x;
        this.y = playingField.y;
        this.startMoves = playingField.startMoves;
        this.moves = playingField.moves;
    }

    public byte[][] getPlayingField() {
        return testingField;
    }

    public void enterDisplayMode(Object mutex){
        displayMode = true;
        this.mutex = mutex;
    }

    public void move(Action action){
        synchronized (mutex){
            if(displayMode){
                try {
                    mutex.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(moves <= 0){
                return;
            }
            moves--;
            switch (action){
                case Left:
                    ant.direction = (ant.direction + 3)%4;
                    break;
                case Right:
                    ant.direction = (ant.direction + 1)%4;
                    break;
                case Move:
                    switch (ant.direction){
                        case 0:
                            ant.x = (ant.x + 1)%x;
                            break;
                        case 1:
                            ant.y = (ant.y + 1 )%y;
                            break;
                        case 2:
                            ant.x = (ant.x - 1 + x)%x;
                            break;
                        case 3:
                            ant.y = (ant.y - 1 + y)%y;
                            break;
                    }

                    if(testingField[ant.y][ant.x] == '1'){
                        testingField[ant.y][ant.x] = '0';
                        score++;
                    }
                    break;
            }
            inputWrapper.foodAhead = checkIfFoodAhead();
        }
    }

    public void startDisplay(ArtificialAntGPTree ant){
        testAnt(ant);
    }

    public void resetGame(){
        moves = startMoves;
        ant.x = 0;
        ant.y = 0;
        ant.direction = 0;
        score = 0;
        testingField = new byte[y][0];
        for(int i = 0; i < y; ++i){
            testingField[i] = playingField[i].clone();
        }
    }

    protected boolean checkIfFoodAhead(){
        switch (ant.direction){
            case 0:
                return testingField[ant.y][(ant.x + 1)%x] == '1';
            case 1:
                return testingField[(ant.y + 1)%y][ant.x] == '1';
            case 2:
                return testingField[ant.y][(ant.x - 1 + x)%x] == '1';
            case 3:
                return testingField[(ant.y - 1 + y)%y][ant.x] == '1';
        }
        return false;
    }

    private int testAnt(ArtificialAntGPTree gpTree){
        resetGame();
        inputWrapper = gpTree.getInputWrapper();
        inputWrapper.foodAhead = checkIfFoodAhead();

        while (moves > 0){
            gpTree.evaluate(null);
        }

        return score;
    }

    public Ant getAnt(){
        return ant;
    }

    @Override
    public void evaluate(ArtificialAntGPTree tree) {

        double oldFitness = tree.fitness;
        double newFitness = 1./testAnt(tree);
        if(oldFitness - newFitness < 0.00001){
            tree.fitness = newFitness / 0.95;
        }
        else {
            tree.fitness = newFitness;
        }
    }

    @Override
    public int numberOfInputs() {
        return 1;
    }
}
