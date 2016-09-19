package com.github.kbednarz.imageguillotine.service;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.ColumnConstraints;

import java.util.ArrayList;
import java.util.List;

public class GenerateGridService {
    private double scale;
    private GraphicsContext gc;
    private PaperSizeServiceInterface paperService;

    public GenerateGridService(GraphicsContext gc,PaperSizeServiceInterface paperService){
        this.gc = gc;
        this.paperService = paperService;
    }

    public void updateGrid(double scale) {
        this.scale = scale;
        gc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
        generateRows();
        generateColumns();
    }

    private void generateRows(){
        double rowHeight = paperService.getPageHeightInPx()*scale;

        for (int y=0; y <= paperService.getPaneHeight(); y += rowHeight){
            gc.strokeLine(0,y,paperService.getPaneWidth(),y);
        }
    }

    private void generateColumns(){
        double columnWidth = paperService.getPageWidthInPx()*scale;

        for (int x=0; x <= paperService.getPaneWidth(); x += columnWidth){
            gc.strokeLine(x,0,x,paperService.getPaneHeight());
        }
    }

}
