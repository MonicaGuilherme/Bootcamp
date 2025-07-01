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
         * Draws the entire grid as outlined rectangles
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
         * Converts column index to screen X coordinate
         */
        public int colToX(int col) {
            return PADDING + col * CELL_SIZE;
        }

        /**
         * Converts row index to screen Y coordinate
         */
        public int rowToY(int row) {
            return PADDING + row * CELL_SIZE;
        }

        public void paintCell(int row, int col, Color color) {
            // If cell is already painted, delete it
            if (paintedCells[row][col] != null) {
                paintedCells[row][col].delete();
            }

            Rectangle cell = new Rectangle(colToX(col), rowToY(row), CELL_SIZE, CELL_SIZE);
            cell.setColor(color);
            cell.fill();

            paintedCells[row][col] = cell;
        }

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

        public void clear() {
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    if (paintedCells[row][col] != null) {
                        paintedCells[row][col].delete();
                        paintedCells[row][col] = null;
                    }
                }
            }

            // Redraw grid lines (optional)
            init();
        }


    }





