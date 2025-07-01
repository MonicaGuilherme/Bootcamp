package io.codeforall.kernelfc;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Painter implements KeyboardHandler {

    private final Keyboard keyboard;
    private final Grid grid;
    private int currentRow;
    private int currentCol;
    private Rectangle cursor;
    private final Stack<List<PaintedCell>> undoStack = new Stack<>();
    private final Stack<List<PaintedCell>> redoStack = new Stack<>();

    // Color palette and index
    private final ColorPalette colorPalette = new ColorPalette();
    private final Color[] palette = {Color.BLUE, Color.RED, Color.GREEN, Color.MAGENTA, Color.YELLOW};
    private int currentColorIndex = 0;

    /**
     * Constructor: initializes the painter, grid, and sets up the keyboard and UI
     */
    public Painter() {
        this.keyboard = new Keyboard(this);
        this.grid = new Grid();

        currentRow = 0;
        currentCol = 0;

        cursor = new Rectangle(grid.colToX(currentCol), grid.rowToY(currentRow), Grid.CELL_SIZE, Grid.CELL_SIZE);
        cursor.setColor(Color.BLACK);
        cursor.fill();

        colorPalette.drawPalette(palette, currentColorIndex);
        createKeyboardEvents();
    }

    /**
     * Registers keyboard keys and binds them to the handler
     */
    public void createKeyboardEvents() {
        int[] keys = {
                KeyboardEvent.KEY_UP,
                KeyboardEvent.KEY_DOWN,
                KeyboardEvent.KEY_LEFT,
                KeyboardEvent.KEY_RIGHT,
                KeyboardEvent.KEY_SPACE, // Paint cell
                KeyboardEvent.KEY_P,     // Change color
                KeyboardEvent.KEY_C,     // Clear grid
                KeyboardEvent.KEY_S,     // Save drawing
                KeyboardEvent.KEY_L,     // Load drawing
                KeyboardEvent.KEY_Z,     // Undo
                KeyboardEvent.KEY_Y      // Redo
        };

        for (int key : keys) {
            KeyboardEvent event = new KeyboardEvent();
            event.setKey(key);
            event.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(event);
        }
    }

    /**
     * Main keyboard logic for moving, painting, clearing, and other actions
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
     * Moves the visual cursor to the current position
     */
    private void moveCursor() {
        cursor.delete();
        cursor = new Rectangle(grid.colToX(currentCol), grid.rowToY(currentRow), Grid.CELL_SIZE, Grid.CELL_SIZE);
        cursor.setColor(Color.BLACK);
        cursor.fill();
    }

    /**
     * Saves current state of the grid before any change
     */
    private void saveStateForUndo() {
        List<PaintedCell> snapshot = new ArrayList<>(grid.getPaintedCells());
        undoStack.push(snapshot);
        redoStack.clear();
    }

    /**
     * Paints current cell with selected color
     */
    private void paintCell() {
        saveStateForUndo();
        grid.paintCell(currentRow, currentCol, palette[currentColorIndex]);
    }

    /**
     * Switches to the next color in the palette
     */
    private void changeColor() {
        currentColorIndex = (currentColorIndex + 1) % palette.length;
        colorPalette.drawPalette(palette, currentColorIndex);
    }

    /**
     * Converts Color object to string representation
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
     * Saves the current painted grid to a text file
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
     * Converts string to Color object
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
     * Loads the drawing from the file and restores the painted grid
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
     * Undo the last action by restoring the previous state from the stack
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
     * Redo the previously undone action
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
     * Applies a saved state to the grid and updates UI
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
