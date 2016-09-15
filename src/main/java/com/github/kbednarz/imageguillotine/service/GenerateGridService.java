package com.github.kbednarz.imageguillotine.service;

import com.sun.rowset.internal.Row;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.List;

public class GenerateGridService {
    private double paneWidth,paneHeight,paperWidthInPx,paperHeightInPx,scale;

    public GenerateGridService(double paneWidth, double paneHeight, double paperWidthInPx, double paperHeightInPx, double scale) {
        this.paneWidth = paneWidth;
        this.paneHeight = paneHeight;
        this.paperWidthInPx = paperWidthInPx;
        this.paperHeightInPx = paperHeightInPx;
        this.scale = scale;
    }

    public List<RowConstraints> getRowConstraints(){
        List<RowConstraints> listOfRows = new ArrayList<>();
        int numberOfRows = (int)Math.ceil(paneHeight/paperHeightInPx);

        for(int i=0; i<numberOfRows; i++){
            RowConstraints row = new RowConstraints(paperHeightInPx*scale);
            listOfRows.add(row);
        }
        return listOfRows;
    }

    public List<ColumnConstraints> getColumnConstraints(){
        List<ColumnConstraints> listOfColumns = new ArrayList<>();
        int numberOfColumns = (int)Math.ceil(paneWidth/paperWidthInPx);

        for(int i=0; i<numberOfColumns; i++){
            ColumnConstraints column = new ColumnConstraints(paperWidthInPx*scale);
            listOfColumns.add(column);
        }
        return listOfColumns;
    }
}
