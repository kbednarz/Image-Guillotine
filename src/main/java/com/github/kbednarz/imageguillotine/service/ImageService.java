package com.github.kbednarz.imageguillotine.service;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageService {
    private File imageFile;
    private BufferedImage bufferedImage;
    private ImageInfo imageInfo;

    public ImageService(File imageFile) throws IOException, ImageReadException{
        this.imageFile = imageFile;
        bufferedImage = ImageIO.read(this.imageFile);
        imageInfo =  Sanselan.getImageInfo(imageFile);
    }

    public Image getFxImage(){
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        return image;
    }

    public double getDpi(){
        return imageInfo.getPhysicalHeightDpi();
    }

}
