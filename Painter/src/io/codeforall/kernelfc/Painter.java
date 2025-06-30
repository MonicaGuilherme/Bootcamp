package io.codeforall.kernelfc;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.*;

public class Painter implements KeyboardHandler {

    private final Keyboard keyboard;
    private final Grid grid;

    private int currentRow;
    private int currentCol;

    private Rectangle cursor;

    // Color palette and index
    private final Color[] palette = {Color.BLUE, Color.DARK_GRAY, Color.GREEN, Color.MAGENTA, Color.YELLOW};
    private int currentColorIndex = 0;

    /**
     * Constructor: initializes the painter and sets up the keyboard
     */
    public Painter() {
        this.keyboard = new Keyboard(this);
        this.grid = new Grid();

        currentRow = 0;
        currentCol = 0;

        // Create the visual cursor (player)
        cursor = new Rectangle(grid.colToX(currentCol), grid.rowToY(currentRow), Grid.CELL_SIZE, Grid.CELL_SIZE);
        cursor.setColor(Color.RED);
        cursor.draw();

        createKeyboardEvents();
    }

    /**
     * Creates and registers all keyboard events
     */
    public void createKeyboardEvents() {
        int[] keys = {
                KeyboardEvent.KEY_UP,
                KeyboardEvent.KEY_DOWN,
                KeyboardEvent.KEY_LEFT,
                KeyboardEvent.KEY_RIGHT,
                KeyboardEvent.KEY_SPACE,
                KeyboardEvent.KEY_P // New: change color
        };

        for (int key : keys) {
            KeyboardEvent event = new KeyboardEvent();
            event.setKey(key);
            event.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(event);
        }
    }

    /**
     * Handles key presses and performs actions
     */
    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {

            case KeyboardEvent.KEY_UP:
                if (currentRow > 0) {
                    currentRow--;
                    moveCursor();
                }
                break;

            case KeyboardEvent.KEY_DOWN:
                if (currentRow < Grid.ROWS - 1) {
                    currentRow++;
                    moveCursor();
                }
                break;

            case KeyboardEvent.KEY_LEFT:
                if (currentCol > 0) {
                    currentCol--;
                    moveCursor();
                }
                break;

            case KeyboardEvent.KEY_RIGHT:
                if (currentCol < Grid.COLS - 1) {
                    currentCol++;
                    moveCursor();
                }
                break;

            case KeyboardEvent.KEY_SPACE:
                paintCell(); // Paints with current color
                break;

            case KeyboardEvent.KEY_P:
                changeColor(); // Rotates to next color in palette
                break;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        // Not used
    }

    /**
     * Moves the cursor rectangle to the new row and column
     */
    private void moveCursor() {
        cursor.delete();
        cursor = new Rectangle(grid.colToX(currentCol), grid.rowToY(currentRow), Grid.CELL_SIZE, Grid.CELL_SIZE);
        cursor.setColor(Color.RED);
        cursor.draw();
    }

    /**
     * Paints the current cell with the current selected color
     */
    private void paintCell() {
        Rectangle painted = new Rectangle(grid.colToX(currentCol), grid.rowToY(currentRow), Grid.CELL_SIZE, Grid.CELL_SIZE);
        painted.setColor(palette[currentColorIndex]);
        painted.fill();
    }

    /**
     * Changes to the next color in the palette
     */
    private void changeColor() {
        currentColorIndex = (currentColorIndex + 1) % palette.length;
        System.out.println("Current color: " + palette[currentColorIndex]); // Optional: feedback
    }
}
