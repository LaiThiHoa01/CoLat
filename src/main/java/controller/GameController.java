package controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class GameController {

    @FXML
    private BorderPane boardGame;

    @FXML
    private GridPane boardGrid;

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnReplay;

    @FXML
    private JFXButton buttonOptionGame;

    @FXML
    private Label countBlack;

    @FXML
    private Label countWhite;

    @FXML
    private VBox vbMenuGame;

}
