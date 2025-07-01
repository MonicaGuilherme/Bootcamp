package io.codeforall.kernelfc;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class ColorPalette {

    private static final int BOX_SIZE = 20;
    private static final int SPACING = 10;
    private static final int X_OFFSET = Grid.PADDING + Grid.COLS * Grid.CELL_SIZE + 30; // Right of grid
    private static final int Y_OFFSET = Grid.PADDING;

    private Rectangle[] boxes;

    /**
     * Draws the palette on the right side of the grid
     * Highlights the currently selected color
     */
    public void drawPalette(Color[] palette, int selectedIndex) {
        // Clear previous palette (if any)
        clearPalette();

        boxes = new Rectangle[palette.length];

        for (int i = 0; i < palette.length; i++) {
            int x = X_OFFSET;
            int y = Y_OFFSET + i * (BOX_SIZE + SPACING);

            Rectangle colorBox = new Rectangle(x, y, BOX_SIZE, BOX_SIZE);
            colorBox.setColor(palette[i]);
            colorBox.fill();
            boxes[i] = colorBox;

            // If current color, draw black border
            if (i == selectedIndex) {
                Rectangle border = new Rectangle(x - 1, y - 1, BOX_SIZE + 2, BOX_SIZE + 2);
                border.setColor(Color.BLACK);
                border.draw();
            }
        }
    }

    /**
     * Clears the previously drawn palette boxes
     */
    private void clearPalette() {
        if (boxes != null) {
            for (Rectangle box : boxes) {
                if (box != null) {
                    box.delete();
                }
            }
        }
    }
}

