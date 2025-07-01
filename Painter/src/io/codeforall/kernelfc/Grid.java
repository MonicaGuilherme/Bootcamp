package io.codeforall.kernelfc;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Grid {

    public static final int COLS = 40;
    public static final int ROWS = 30;
    public static final int CELL_SIZE = 20;
    public static final int PADDING = 10;

    private Rectangle[][] paintedCells = new Rectangle[ROWS][COLS];

    /**
     * Initializes the grid by drawing all cell outlines.
     * This is purely visual and doesn't fill any cell.
     */
    public void init() {
        for (int col = 0; col < COLS; col++) {
            for (int row = 0; row < ROWS; row++) {
                Rectangle cell = new Rectangle(colToX(col), rowToY(row), CELL_SIZE, CELL_SIZE);
                cell.setColor(Color.BLACK);
                cell.draw();
            }
        }
    }

    /**
     * Converts a column index to its corresponding x-coordinate on the screen.
     * @param col the column index
     * @return the x-coordinate in pixels
     */
    public int colToX(int col) {
        return PADDING + col * CELL_SIZE;
    }

    /**
     * Converts a row index to its corresponding y-coordinate on the screen.
     * @param row the row index
     * @return the y-coordinate in pixels
     */
    public int rowToY(int row) {
        return PADDING + row * CELL_SIZE;
    }

    /**
     * Paints a specific cell with the given color.
     * If a cell is already painted, it gets deleted before repainting.
     * @param row the row index
     * @param col the column index
     * @param color the color to fill the cell with
     */
    public void paintCell(int row, int col, Color color) {
        if (paintedCells[row][col] != null) {
            paintedCells[row][col].delete();
        }

        Rectangle cell = new Rectangle(colToX(col), rowToY(row), CELL_SIZE, CELL_SIZE);
        cell.setColor(color);
        cell.fill();

        paintedCells[row][col] = cell;
    }

    /**
     * Returns a list of all painted cells (non-null entries) as PaintedCell objects.
     * Each includes row, column, and color information.
     * @return list of painted cell data
     */
    public List<PaintedCell> getPaintedCells() {
        List<PaintedCell> painted = new ArrayList<>();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (paintedCells[row][col] != null) {
                    Color color = paintedCells[row][col].getColor();
                    painted.add(new PaintedCell(row, col, color));
                }
            }
        }

        return painted;
    }

    /**
     * Clears the entire grid by deleting all filled cells.
     * Keeps the outline intact (drawn separately via init()).
     */
    public void clear() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (paintedCells[row][col] != null) {
                    paintedCells[row][col].delete();
                    paintedCells[row][col] = null;
                }
            }
        }
    }
}






