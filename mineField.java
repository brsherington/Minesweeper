package sample;


import javafx.scene.control.Button;

import java.util.Random;

/**
 * Created by bsherington on 4/21/16.
 */
public class mineField {
        private int x_size;
        private int y_size;
        private Space[][] board;

        private class Space{
            private boolean mined;
            private boolean exposed;
            private boolean marked;
            int adjBombs;
            private Button button;
            Space(){
                mined = false;
                exposed = false;
                marked = false;
                adjBombs = 0;
                button = new Button();

            }
        }


        public void constructBoard(int x_size, int y_size) {
            board = new Space[x_size][y_size];
            for (int x = 0; x < x_size; x++) {
                for (int y = 0; y < y_size; y++) {
                        board[x][y] = new Space();
                        board[x][y].button.setPrefSize(30,30);

                }
            }
        }
        public Button getButton(int x, int y){
            return board[x][y].button;
        }
        public void mineBoard(int numOfBombs, int BOARD_SIZE){
            for(int i = 0; i < numOfBombs; i++){
                Random rand = new Random();
                int randX;
                int randY;
                Space space;
                do {
                    randX = Math.abs(rand.nextInt()) % BOARD_SIZE;
                    randY = Math.abs(rand.nextInt()) % BOARD_SIZE;
                    space = board[randX][randY];
                }while(space.mined);
                space.mined = true;

            }

        }
        public void getNeighbors(int x, int y, int BOARD_SIZE){
            for(int i = -1; i <= 1; i++){
                int newX = x + i;
                if(newX < 0 || newX >= BOARD_SIZE){
                    continue;
                }
                for(int j = -1;j <=1; j++){
                    int newY = y + j;
                    if(newY < 0 || newY >= BOARD_SIZE){
                        continue;
                    }
                    if(board[newX][newY].mined){
                        board[x][y].adjBombs++;
                    }
                }
            }
        }

        public void exposeSpace(int x, int y, int BOARD_SIZE){
            int exposed = exposedCounter(BOARD_SIZE);
            if(!board[x][y].exposed){
                if(!board[x][y].marked){
                    board[x][y].exposed = true;
                    board[x][y].button.setDisable(true);
                    board[x][y].button.setStyle("-fx-background-color: darkgrey");
                    if(!board[x][y].mined) {
                        getNeighbors(x,y,BOARD_SIZE);
                        board[x][y].button.setText(Integer.toString(board[x][y].adjBombs));
                        if(board[x][y].adjBombs == 0){
                            board[x][y].button.setText("");
                            exposeAll(x,y, BOARD_SIZE);
                        }
                    }else{
                        if(exposed != 0) {
                            loseGame(x, y, BOARD_SIZE);
                        }
                    }

                }
            }
            exposed--;
            if(exposed == 0){
                winGame(BOARD_SIZE);
            }

        }
        public void exposeAll(int x, int y, int BOARD_SIZE){
            for(int i = -1; i <= 1; i++){
                int newX = x + i;
                if(newX < 0 || newX >= BOARD_SIZE){
                    continue;
                }
                for(int j = -1;j <=1; j++){
                    int newY = y + j;
                    if(newY < 0 || newY >= BOARD_SIZE){
                        continue;
                    }
                    exposeSpace(newX, newY,BOARD_SIZE);
                }
            }
        }
        public void loseGame(int x, int y, int BOARD_SIZE){
            for(int i = 0; i < BOARD_SIZE; i++){
                for(int j = 0; j < BOARD_SIZE; j++){
                    if(board[i][j].mined){
                        board[i][j].exposed = true;
                        board[i][j].button.setDisable(true);
                        board[i][j].button.setStyle("-fx-background-color: red");
                        board[i][j].button.setText("X");
                    }
                    board[x][y].exposed = true;
                    board[x][y].button.setDisable(true);
                    board[x][y].button.setStyle("-fx-background-color: red");
                    board[x][y].button.setText("X");
                }
            }
            disableAll(BOARD_SIZE);
        }
        public void winGame(int BOARD_SIZE){
            for(int i = 0; i < BOARD_SIZE; i++){
                for(int j = 0; j < BOARD_SIZE; j++){
                    if(board[i][j].mined){
                        board[i][j].exposed = true;
                        board[i][j].button.setDisable(true);
                        board[i][j].button.setStyle("-fx-background-color: green");
                        board[i][j].button.setText("X");
                    }
                }
            }
        }


        public void disableAll(int BOARD_SIZE){
            for(int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if(!board[i][j].exposed){
                        board[i][j].button.setDisable(true);
                    }
                }
            }
        }
        public int exposedCounter(int BOARD_SIZE){
            int safeSpaces = 0;
            for(int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (!board[i][j].exposed && !board[i][j].mined) {
                        safeSpaces++;
                    }
                }
            }
            return safeSpaces;
        }
        public void markSpace(int x, int y, int BOARD_SIZE){
            if(!board[x][y].exposed){
                if(!board[x][y].marked){
                    board[x][y].marked = true;
                    board[x][y].button.setText(":)");
                }else{
                    board[x][y].marked = false;
                    board[x][y].button.setText("");
                }
            }
        }

        public String congrats(){
            String congrats = "Congratulations you have survived the minefield!";
            return congrats;
        }
        


}
