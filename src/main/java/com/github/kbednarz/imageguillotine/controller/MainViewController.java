package com.github.kbednarz.imageguillotine.controller;

import com.github.kbednarz.imageguillotine.service.A4PaperSizeService;
import com.github.kbednarz.imageguillotine.model.ImagePosition;
import com.github.kbednarz.imageguillotine.service.GenerateGridService;
import com.github.kbednarz.imageguillotine.service.ImageService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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
    private Canvas gridCanvas;

    private ImagePosition imagePosition;
    private double imageScale;
    private A4PaperSizeService paperSizeService;
    private Image fxImage;
    private GenerateGridService gridService;
    private  ImageService imageService;


    public void initialize(URL location, ResourceBundle resources) {
        imagePosition = new ImagePosition();
        gridCanvas.heightProperty().bind(imagePane.heightProperty());
        gridCanvas.widthProperty().bind(imagePane.widthProperty());

        imagePane.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
            if (imageService != null) {
                updateImagePane();

            }
        });
        imagePane.heightProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
            if (imageService != null) {
                updateImagePane();
            }
        });
    }

    public void loadImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open image");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Image files", "*.jpg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File imageFile = fileChooser.showOpenDialog(null);
        try {
            imageService = new ImageService(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ImageReadException e) {
            e.printStackTrace();
        }

        if(imageService != null) {
            fxImage = imageService.getFxImage();
            imageView.setImage(fxImage);
            paperSizeService = new A4PaperSizeService(fxImage.getWidth(), fxImage.getHeight(), imageService.getDpi());
            gridService = new GenerateGridService(gridCanvas.getGraphicsContext2D(),paperSizeService);

            updateImagePane();
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

    private double getScale(){
        if (imagePane.getWidth() / paperSizeService.getPaneWidth() < imagePane.getHeight()/paperSizeService.getPaneHeight()){
            return imagePane.getWidth() / paperSizeService.getPaneWidth();
        } else {
            return imagePane.getHeight()/paperSizeService.getPaneHeight();
        }
    }

    private void updateImagePane(){
        imageScale = getScale();
        imageView.setFitWidth(fxImage.getWidth() * imageScale);
        imageView.setFitHeight(fxImage.getHeight() * imageScale);
        gridService.updateGrid(imageScale);
        imagePane.setClip(new Rectangle(paperSizeService.getPaneWidth() * imageScale, paperSizeService.getPaneHeight() * imageScale));
    }
}
