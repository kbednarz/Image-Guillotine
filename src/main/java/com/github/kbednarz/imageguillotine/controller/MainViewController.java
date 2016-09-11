package com.github.kbednarz.imageguillotine.controller;

import com.github.kbednarz.imageguillotine.model.ImagePosition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable{
    @FXML
    private VBox mainPane;
    @FXML
    private ImageView imageView;
    @FXML
    private Pane imagePane;
    private ImagePosition imagePosition;

    public void initialize(URL location, ResourceBundle resources) {
        imagePosition = new ImagePosition();
    }

    public void loadImage(ActionEvent actionEvent){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open image");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Image files", "*.jpg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File imageFile = fileChooser.showOpenDialog(null);

        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);

        imageView.setFitWidth(mainPane.getWidth());
        imageView.setImage(image);

    }


    public void imagePaneMouseDragListener(MouseEvent event){
        imagePosition.calculateTranslation(event.getX(),event.getY());
        imageView.setX(imageView.getX()+imagePosition.getxTranslation());
        imageView.setY(imageView.getY()+imagePosition.getyTranslation());

    }

    public void imagePaneMouseClickListener(MouseEvent event) {
        imagePosition.setDraggedPos(event.getX(),event.getY());
    }

}
