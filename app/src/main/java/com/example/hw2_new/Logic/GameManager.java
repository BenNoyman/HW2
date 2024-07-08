package com.example.hw2_new.Logic;
public class GameManager {

    private final int TOTAL_ROWS = 6;
    private final int TOTAL_COLS = 5;
    private final int [][] currentObstacles = new int[TOTAL_ROWS][TOTAL_COLS];
    private final int COIN_POINTS = 10;
    private int crash =0;
    private int lane = (int) (Math.random() * TOTAL_COLS); // Randomly choose a lane
    private int circle =0;
    private int [] currentPlane;
    private int life;
    private int score = 0;



    public GameManager(){
        this(3);
    }
    public GameManager(int life) {
        this.currentPlane = new int[5];
        currentPlane[2] =1;

        for (int i = 0; i < TOTAL_ROWS; i++) {
            for (int j = 0; j < TOTAL_COLS; j++) {
                currentObstacles[i][j] =0;
            }
        }
        this.life = life;
    }

    public void updatePlanePosition(int newPosition) {
        for (int i = 0; i < currentPlane.length; i++) {
            if (i == newPosition) {
                currentPlane[i] = 1;
            } else {
                currentPlane[i] = 0;
            }
        }
    }
    public void updateObstacleUI() {

        for (int i = 0; i < currentObstacles[0].length; i++) {
            if (currentObstacles[currentObstacles.length - 1][i] == 1 ||currentObstacles[currentObstacles.length - 1][i] == 2) {
                currentObstacles[currentObstacles.length - 1][i] = 0;
            }
        }
        for (int i = currentObstacles.length - 1; i > 0; i--) {
            for (int j = 0; j < currentObstacles[0].length; j++) {
                if (currentObstacles[i - 1][j] == 1) {
                    currentObstacles[i - 1][j] = 0;
                    currentObstacles[i][j] = 1;
                }
                if (currentObstacles[i - 1][j] == 2) {
                    currentObstacles[i - 1][j] = 0;
                    currentObstacles[i][j] = 2;
                }
            }
        }

        if (circle % 2 == 0) {
            int lane = (int) (Math.random() * TOTAL_COLS);
            int lane2 = (int) (Math.random() * TOTAL_COLS);
            if(lane > lane2)
                currentObstacles[0][lane] = 1;
            else {
                currentObstacles[0][lane2] = 2;
            }
        }
        if (circle % 3 == 1){
            int lane2 = (int) (Math.random() * TOTAL_COLS);
            currentObstacles[0][lane2] = 1;
        }

        circle++;
        score++;

        for (int i = 0; i < currentObstacles[0].length; i++) {
            if (currentObstacles[currentObstacles.length - 1][i] == 1 && currentPlane[i] == 1) {
                crash++;
            }
            else if (currentObstacles[currentObstacles.length - 1][i] == 2 && currentPlane[i] == 1) {
                score += COIN_POINTS;
            }
        }
    }

    public int getScore() {
        return score;
    }

    public int getCrash() {
        return crash;
    }

    public int[][] getCurrentObstacles() {
        return currentObstacles;
    }

    public int[] getCurrentPlane() {
        return currentPlane;
    }

    public int getTOTAL_ROWS() {
        return TOTAL_ROWS;
    }

}

