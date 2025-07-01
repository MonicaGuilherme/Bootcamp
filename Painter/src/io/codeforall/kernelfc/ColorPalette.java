package io.codeforall.kernelfc;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

/**
 * Manages the display of the color palette alongside the grid.
 * Draws selectable color boxes and highlights the currently selected color.
 */
public class ColorPalette {

    private static final int BOX_SIZE = 20;      // Width and height of each color box
    private static final int SPACING = 10;       // Space between boxes
    private static final int X_OFFSET = Grid.PADDING + Grid.COLS * Grid.CELL_SIZE + 30; // Right of grid
    private static final int Y_OFFSET = Grid.PADDING; // Top alignment with grid

    private Rectangle[] colorBoxes;   // Holds the drawn color boxes
    private Rectangle currentBorder;  // The highlighted border for the selected color

    /**
     * Draws the color palette to the screen.
     * Deletes any previous palette before drawing new one.
     * Highlights the currently selected color.
     *
     * @param palette the array of available colors
     * @param selectedIndex the index of the currently selected color
     */
    public void drawPalette(Color[] palette, int selectedIndex) {
        clearPalette();

        colorBoxes = new Rectangle[palette.length];

        for (int i = 0; i < palette.length; i++) {
            int x = X_OFFSET;
            int y = Y_OFFSET + i * (BOX_SIZE + SPACING);

            Rectangle colorBox = new Rectangle(x, y, BOX_SIZE, BOX_SIZE);
            colorBox.setColor(palette[i]);
            colorBox.fill();

            colorBoxes[i] = colorBox;

            if (i == selectedIndex) {
                currentBorder = new Rectangle(x - 1, y - 1, BOX_SIZE + 2, BOX_SIZE + 2);
                currentBorder.setColor(Color.BLACK);
                currentBorder.draw();
            }
        }
    }

    /**
     * Deletes all previously drawn color boxes and the border.
     * This ensures the palette is redrawn cleanly on color change.
     */
    private void clearPalette() {
        if (colorBoxes != null) {
            for (Rectangle box : colorBoxes) {
                if (box != null) {
                    box.delete();
                }
            }
        }

        if (currentBorder != null) {
            currentBorder.delete();
        }
    }
}



