package com.github.kbednarz.imageguillotine.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainViewController{
    @FXML
    private VBox mainPane;
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

        BufferedImage bufferedImage = null;
        Image image = null;
        try {
            bufferedImage = ImageIO.read(imageFile);
            image = SwingFXUtils.toFXImage(bufferedImage, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImage(image);

    }
}
