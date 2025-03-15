package io.codeforall.kernelfc;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import java.util.Arrays;

public class Grid {

        private static final int PADDING = 10;
        private static final int CELL_SIZE = 20;
        private static final int COLS = 30;
        private static final int ROWS = 30;

        private Grid[] paintedPositions;




        public Grid(){
            init();
        }

        public static int getCellSize() {
            return CELL_SIZE;
        }

        public static int getCOLS() {
            return COLS;
        }

        public static int getROWS() {
            return ROWS;
        }


        public int colToX(int column){
            return PADDING + CELL_SIZE *column;
        }


        public int rowToY(int row){
            return PADDING + CELL_SIZE *row;
        }


        public void init(){
            for(int col = 0; col < COLS; col++){
                for(int row = 0; row < ROWS; row++){
                    Rectangle canvas = new Rectangle(colToX(col), rowToY(row), CELL_SIZE, CELL_SIZE);
                    canvas.setColor(Color.BLACK);
                    canvas.draw();
                }
            }
        }






    }




