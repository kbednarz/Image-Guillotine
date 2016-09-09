package com.github.kbednarz.imageguillotine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainViewController{
    @FXML
    private VBox mainPane;
    @FXML
    private MenuItem loadImageMenuItem;
    @FXML
    private ImageView imageView;
    private Stage stage;

    @Override
    protected void finalize() throws Throwable {
        stage = (Stage) mainPane.getScene().getWindow();
    }

    public void loadImage(ActionEvent actionEvent){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open image");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Image files", "*.jpg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File imageFile = fileChooser.showOpenDialog(stage);

    }
}
