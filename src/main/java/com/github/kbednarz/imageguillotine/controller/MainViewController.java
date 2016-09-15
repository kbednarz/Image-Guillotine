package com.github.kbednarz.imageguillotine.controller;

import com.github.kbednarz.imageguillotine.service.A4PaperSizeService;
import com.github.kbednarz.imageguillotine.model.ImagePosition;
import com.github.kbednarz.imageguillotine.service.ImageService;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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

    private ImagePosition imagePosition;

    public void initialize(URL location, ResourceBundle resources) {
        imagePosition = new ImagePosition();

        imageView.fitWidthProperty().bind(mainPane.widthProperty());
        imageView.fitHeightProperty().bind(mainPane.heightProperty());

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
            A4PaperSizeService paperSizeService = new A4PaperSizeService(fxImage.getWidth(),fxImage.getHeight(),imageService.getDpi());
            imagePane.setClip(new Rectangle(paperSizeService.getPaneWidth(),paperSizeService.getPaneHeight()));
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
