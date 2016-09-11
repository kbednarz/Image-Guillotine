package com.github.kbednarz.imageguillotine.model;

public class ImagePosition {
    private double xDraggedPos,yDraggedPos,xTranslation,yTranslation;

    public ImagePosition() {
        xDraggedPos = 0;
        yDraggedPos = 0;
    }


    public void setDraggedPos(double xDraggedPos, double yDraggedPos){
        this.xDraggedPos = xDraggedPos;
        this.yDraggedPos = yDraggedPos;
    }

    public void calculateTranslation(double xDraggedPos, double yDraggedPos){
        xTranslation = xDraggedPos - this.xDraggedPos;
        yTranslation = yDraggedPos - this.yDraggedPos;

        this.xDraggedPos = xDraggedPos;
        this.yDraggedPos = yDraggedPos;
    }

    public double getxTranslation() {
        return xTranslation;
    }

    public double getyTranslation() {
        return yTranslation;
    }
}
