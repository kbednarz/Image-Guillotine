package com.github.kbednarz.imageguillotine.service;

import java.math.RoundingMode;

public final class A4PaperSizeService implements PaperSizeServiceInterface{
    private final double widthInInches = 8.267;
    private double widthInPixels;
    private final double heightInInches = 11.692;
    private double heightInPixels;
    private double imageWidth,imageHeight,imageDpi;
    private double paneWidth,paneHeight;

    public double getPageWidthInPx() {
        return widthInPixels;
    }

    public double getPageHeightInPx() {
        return heightInPixels;
    }

    public A4PaperSizeService(double imageWidth, double imageHeight, double imageDpi) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imageDpi = imageDpi;

        calculatePaneSize();
    }

    private void calculatePaneSize(){
        widthInPixels = widthInInches*imageDpi;
        paneWidth = imageWidth/widthInPixels;
        paneWidth = Math.ceil(paneWidth)*widthInPixels;

        heightInPixels = heightInInches*imageDpi;
        paneHeight = imageHeight/heightInPixels;
        paneHeight = Math.ceil(paneHeight)*heightInPixels;
    }

    public double getPaneWidth() {
        return paneWidth;
    }

    public double getPaneHeight() {
        return paneHeight;
    }
}
