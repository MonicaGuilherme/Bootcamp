package io.codeforall.kernelfc;

import org.academiadecodigo.simplegraphics.graphics.Color;

/**
 * Represents a painted cell on the grid.
 * Stores the row, column, and color used for painting.
 */
public class PaintedCell {

    public int row;    // The row index of the painted cell
    public int col;    // The column index of the painted cell
    public Color color; // The color used to fill the cell

    /**
     * Constructs a PaintedCell with specified position and color.
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @param color the color used to paint the cell
     */
    public PaintedCell(int row, int col, Color color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }
}

