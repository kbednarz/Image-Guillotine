package com.github.kbednarz.imageguillotine.controller;

import com.github.kbednarz.imageguillotine.service.A4PaperSizeService;
import com.github.kbednarz.imageguillotine.model.ImagePosition;
import com.github.kbednarz.imageguillotine.service.GenerateGridService;
import com.github.kbednarz.imageguillotine.service.ImageService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import org.apache.sanselan.ImageReadException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable{
    @FXML
    private GridPane mainPane;
    @FXML
    private ImageView imageView;
    @FXML
    private Pane imagePane;
    @FXML
    private GridPane imageGrid;

    private ImagePosition imagePosition;
    private double imageScale;

    public void initialize(URL location, ResourceBundle resources) {
        imagePosition = new ImagePosition();

        //imageView.fitWidthProperty().bind(imagePane.widthProperty());
        //imageView.fitHeightProperty().bind(imagePane.heightProperty());



    }

    public void loadImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open image");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Image files", "*.jpg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File imageFile = fileChooser.showOpenDialog(null);
        ImageService imageService = null;
        try {
            imageService = new ImageService(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ImageReadException e) {
            e.printStackTrace();
        }

        if(imageService != null) {
            Image fxImage = imageService.getFxImage();
            imageView.setImage(fxImage);
            A4PaperSizeService paperSizeService = new A4PaperSizeService(fxImage.getWidth(), fxImage.getHeight(), imageService.getDpi());

            imageScale = imagePane.getWidth() / paperSizeService.getPaneWidth();
            imageView.setFitWidth(fxImage.getWidth() * imageScale);
            imageView.setFitHeight(fxImage.getHeight() * imageScale);
            imagePane.setClip(new Rectangle(paperSizeService.getPaneWidth() * imageScale, paperSizeService.getPaneHeight() * imageScale));

            imageGrid.setPrefWidth(paperSizeService.getPaneWidth()*imageScale);
            imageGrid.setPrefHeight(paperSizeService.getPaneHeight()*imageScale);

            GenerateGridService gridService = new GenerateGridService(
                    paperSizeService.getPaneWidth(),
                    paperSizeService.getPaneHeight(),
                    paperSizeService.getWidthInPixels(),
                    paperSizeService.getHeightInPixels(),
                    imageScale);
            imageGrid.getRowConstraints().remove(0);
            imageGrid.getRowConstraints().addAll(gridService.getRowConstraints());
            imageGrid.getColumnConstraints().remove(0);
            imageGrid.getColumnConstraints().addAll(gridService.getColumnConstraints());
            imageGrid.setVisible(true);
        }

    }


    public void imagePaneMouseDragListener(MouseEvent event){
        if (imageView != null) {
            imagePosition.calculateTranslation(event.getX(), event.getY());
            imageView.setX(imageView.getX() + imagePosition.getxTranslation());
            imageView.setY(imageView.getY() + imagePosition.getyTranslation());
        }
    }

    public void imagePaneMouseClickListener(MouseEvent event) {
        if (imageView != null) {
            imagePosition.setDraggedPos(event.getX(), event.getY());
        }
    }

}
