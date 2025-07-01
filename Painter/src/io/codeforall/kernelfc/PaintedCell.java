package io.codeforall.kernelfc;

import org.academiadecodigo.simplegraphics.graphics.Color;

public class PaintedCell {
    public int row;
    public int col;
    public Color color;

    public PaintedCell(int row, int col, Color color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }
}

