package io.codeforall.kernelfc;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class ColorPalette {

    private static final int BOX_SIZE = 20;
    private static final int SPACING = 10;
    private static final int X_OFFSET = Grid.PADDING + Grid.COLS * Grid.CELL_SIZE + 30;
    private static final int Y_OFFSET = Grid.PADDING;

    private Rectangle[] colorBoxes;
    private Rectangle currentBorder;

    /**
     * Draws the palette on the right side of the grid
     * Highlights the currently selected color
     */
    public void drawPalette(Color[] palette, int selectedIndex) {
        clearPalette(); // Clear previous boxes and border

        colorBoxes = new Rectangle[palette.length];

        for (int i = 0; i < palette.length; i++) {
            int x = X_OFFSET;
            int y = Y_OFFSET + i * (BOX_SIZE + SPACING);

            // Draw color square
            Rectangle colorBox = new Rectangle(x, y, BOX_SIZE, BOX_SIZE);
            colorBox.setColor(palette[i]);
            colorBox.fill();

            colorBoxes[i] = colorBox;

            // If this is the selected color, draw black border
            if (i == selectedIndex) {
                currentBorder = new Rectangle(x - 1, y - 1, BOX_SIZE + 2, BOX_SIZE + 2);
                currentBorder.setColor(Color.BLACK);
                currentBorder.draw();
            }
        }
    }

    /**
     * Deletes all color boxes and the selection border
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


