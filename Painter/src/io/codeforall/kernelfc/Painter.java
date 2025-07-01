package io.codeforall.kernelfc;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Handles user interaction, grid painting, color selection,
 * and drawing state control (undo/redo/save/load).
 */
public class Painter implements KeyboardHandler {

    private final Keyboard keyboard;
    private final Grid grid;
    private int currentRow;
    private int currentCol;
    private Rectangle cursor;
    private final Stack<List<PaintedCell>> undoStack = new Stack<>();
    private final Stack<List<PaintedCell>> redoStack = new Stack<>();

    // Color palette and current selection index
    private final ColorPalette colorPalette = new ColorPalette();
    private final Color[] palette = {Color.BLUE, Color.RED, Color.GREEN, Color.MAGENTA, Color.YELLOW};
    private int currentColorIndex = 0;

    /**
     * Constructor: initializes painter, grid, and sets up UI and keyboard events
     */
    public Painter() {
        this.keyboard = new Keyboard(this);
        this.grid = new Grid();

        currentRow = 0;
        currentCol = 0;

        // Create initial cursor
        cursor = new Rectangle(grid.colToX(currentCol), grid.rowToY(currentRow), Grid.CELL_SIZE, Grid.CELL_SIZE);
        cursor.setColor(Color.BLACK);
        cursor.fill();

        colorPalette.drawPalette(palette, currentColorIndex);
        createKeyboardEvents();
    }

    /**
     * Registers keyboard events for movement, painting, color, and file operations
     */
    public void createKeyboardEvents() {
        int[] keys = {
                KeyboardEvent.KEY_UP,
                KeyboardEvent.KEY_DOWN,
                KeyboardEvent.KEY_LEFT,
                KeyboardEvent.KEY_RIGHT,
                KeyboardEvent.KEY_SPACE,
                KeyboardEvent.KEY_P,
                KeyboardEvent.KEY_C,
                KeyboardEvent.KEY_S,
                KeyboardEvent.KEY_L,
                KeyboardEvent.KEY_Z,
                KeyboardEvent.KEY_Y
        };

        for (int key : keys) {
            KeyboardEvent event = new KeyboardEvent();
            event.setKey(key);
            event.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(event);
        }
    }

    /**
     * Handles all key press events
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
                paintCell();
                break;
            case KeyboardEvent.KEY_P:
                changeColor();
                break;
            case KeyboardEvent.KEY_C:
                saveStateForUndo();
                grid.clear();
                colorPalette.drawPalette(palette, currentColorIndex);
                moveCursor();
                break;
            case KeyboardEvent.KEY_S:
                saveDrawing();
                break;
            case KeyboardEvent.KEY_L:
                loadDrawing();
                break;
            case KeyboardEvent.KEY_Z:
                undo();
                break;
            case KeyboardEvent.KEY_Y:
                redo();
                break;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        // Not used
    }

    /**
     * Updates the cursor's position on the grid
     */
    private void moveCursor() {
        cursor.delete();
        cursor = new Rectangle(grid.colToX(currentCol), grid.rowToY(currentRow), Grid.CELL_SIZE, Grid.CELL_SIZE);
        cursor.setColor(Color.BLACK);
        cursor.fill();
    }

    /**
     * Saves current painted state for undo tracking
     */
    private void saveStateForUndo() {
        List<PaintedCell> snapshot = new ArrayList<>(grid.getPaintedCells());
        undoStack.push(snapshot);
        redoStack.clear();
    }

    /**
     * Paints the selected cell using the current color
     */
    private void paintCell() {
        saveStateForUndo();
        grid.paintCell(currentRow, currentCol, palette[currentColorIndex]);
    }

    /**
     * Rotates through the color palette
     */
    private void changeColor() {
        currentColorIndex = (currentColorIndex + 1) % palette.length;
        colorPalette.drawPalette(palette, currentColorIndex);
    }

    /**
     * Converts a Color to a String for file saving
     */
    private String colorToString(Color color) {
        if (color == Color.BLUE) return "BLUE";
        if (color == Color.RED) return "RED";
        if (color == Color.GREEN) return "GREEN";
        if (color == Color.MAGENTA) return "MAGENTA";
        if (color == Color.YELLOW) return "YELLOW";
        return "UNKNOWN";
    }

    /**
     * Saves the painted grid to a text file
     */
    private void saveDrawing() {
        List<PaintedCell> cells = grid.getPaintedCells();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("drawing.txt"))) {
            for (PaintedCell cell : cells) {
                writer.write(cell.row + "," + cell.col + "," + colorToString(cell.color));
                writer.newLine();
            }
            System.out.println("Drawing saved.");
        } catch (IOException e) {
            System.out.println("Error saving drawing: " + e.getMessage());
        }
    }

    /**
     * Converts a saved color name back to Color object
     */
    private Color stringToColor(String colorName) {
        return switch (colorName) {
            case "BLUE" -> Color.BLUE;
            case "RED" -> Color.RED;
            case "GREEN" -> Color.GREEN;
            case "MAGENTA" -> Color.MAGENTA;
            case "YELLOW" -> Color.YELLOW;
            default -> null;
        };
    }

    /**
     * Loads a drawing from file and restores painted cells
     */
    private void loadDrawing() {
        try (BufferedReader reader = new BufferedReader(new FileReader("drawing.txt"))) {
            String line;
            saveStateForUndo();
            grid.clear();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) continue;

                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                Color color = stringToColor(parts[2]);

                if (color != null) {
                    grid.paintCell(row, col, color);
                }
            }

            colorPalette.drawPalette(palette, currentColorIndex);
            moveCursor();

            System.out.println("Drawing loaded.");

        } catch (IOException e) {
            System.out.println("Error loading drawing: " + e.getMessage());
        }
    }

    /**
     * Restores the previous grid state from undo stack
     */
    private void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }

        List<PaintedCell> currentState = new ArrayList<>(grid.getPaintedCells());
        redoStack.push(currentState);

        List<PaintedCell> previous = undoStack.pop();
        applyState(previous);

        System.out.println("Undo.");
    }

    /**
     * Reapplies a state that was undone
     */
    private void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo.");
            return;
        }

        List<PaintedCell> currentState = new ArrayList<>(grid.getPaintedCells());
        undoStack.push(currentState);

        List<PaintedCell> next = redoStack.pop();
        applyState(next);

        System.out.println("Redo.");
    }

    /**
     * Applies a given list of painted cells to the grid
     */
    private void applyState(List<PaintedCell> state) {
        grid.clear();
        for (PaintedCell cell : state) {
            grid.paintCell(cell.row, cell.col, cell.color);
        }

        colorPalette.drawPalette(palette, currentColorIndex);
        moveCursor();
    }
}