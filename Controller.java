package sample;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Controller {
    @FXML
    private int BOARD_SIZE;
    @FXML
    private int numberOfBombs;
    @FXML
    private GridPane mf;
    @FXML
    private ChoiceBox<String> difficulty;
    @FXML
    private Button newGame;
    @FXML
    private Button quitGame;
    @FXML
    private Label counter;
    @FXML
    private Label message;
    @FXML
    private BorderPane window;

    @FXML
    private Button[][] gameBoard;
    private mineField game = null;


    @FXML
    private void initialize(){
            difficulty.getItems().addAll("Easy", "Intermediate", "Hard");
            difficulty.setValue("Easy");
            message.setText("Welcome to Mine Sweeper!");
            newGame.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent event){
                    if(game != null) {
                        mf.getChildren().clear();
                    }
                    startGame();
                }

            });
            quitGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Platform.exit();
                }
            });
    }
    public void startGame(){
        message.setText("Welcome to Mine Sweeper!");
        String diffSet = difficulty.getSelectionModel().getSelectedItem().toString();
        if (diffSet.equals("Easy")){
            BOARD_SIZE = 10;
            numberOfBombs = 10;

        }
        if (diffSet.equals("Intermediate")){
            BOARD_SIZE = 20;
            numberOfBombs = 50;

        }
        if (diffSet.equals("Hard")){
            BOARD_SIZE = 20;
            numberOfBombs = 99;

        }
        game = new mineField();
        game.constructBoard(BOARD_SIZE,BOARD_SIZE);
        for(int x = 0; x < BOARD_SIZE; x++){
            for(int y = 0; y < BOARD_SIZE; y++){
                mf.add(game.getButton(x,y), x, y);

            }
        }
        game.mineBoard(numberOfBombs, BOARD_SIZE);
        counter.setText(Integer.toString(game.exposedCounter(BOARD_SIZE)));
        for(int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                int row = x;
                int col = y;

                Button playSpace = game.getButton(row, col);
                playSpace.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        game.exposeSpace(row, col, BOARD_SIZE);
                        counter.setText(Integer.toString(game.exposedCounter(BOARD_SIZE)));
                        if(game.exposedCounter(BOARD_SIZE) == 0){
                            message.setText(game.congrats());
                        }

                    }
                    if(event.getButton() == MouseButton.SECONDARY){
                        game.markSpace(row,col, BOARD_SIZE);
                    }
                });
            }
        }
    }

}


