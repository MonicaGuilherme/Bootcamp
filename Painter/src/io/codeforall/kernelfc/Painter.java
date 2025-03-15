package io.codeforall.kernelfc;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

public class Painter implements KeyboardHandler{

        private final Keyboard keyboard;
        private final Rectangle player;


        public Painter(){
            this.player = new Rectangle(10,10, 20,20);
            this.player.setColor(Color.BLUE);
            this.player.fill();
            this.keyboard = new Keyboard(this);
            createKeyboardEvents();
        }



        public void createKeyboardEvents(){

            KeyboardEvent keyboardEventSpace = new KeyboardEvent();
            keyboardEventSpace.setKey(KeyboardEvent.KEY_SPACE);
            keyboardEventSpace.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(keyboardEventSpace);

            KeyboardEvent keyboardEventL = new KeyboardEvent();
            keyboardEventL.setKey(KeyboardEvent.KEY_L);
            keyboardEventL.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(keyboardEventL);

            KeyboardEvent keyboardEventS = new KeyboardEvent();
            keyboardEventS.setKey(KeyboardEvent.KEY_S);
            keyboardEventS.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(keyboardEventS);

            KeyboardEvent keyboardEventC = new KeyboardEvent();
            keyboardEventC.setKey(KeyboardEvent.KEY_L);
            keyboardEventC.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(keyboardEventC);

            KeyboardEvent keyboardEventRight = new KeyboardEvent();
            keyboardEventRight.setKey(KeyboardEvent.KEY_RIGHT);
            keyboardEventRight.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(keyboardEventRight);

            KeyboardEvent keyboardEventLeft = new KeyboardEvent();
            keyboardEventLeft.setKey(KeyboardEvent.KEY_LEFT);
            keyboardEventLeft.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(keyboardEventLeft);

            KeyboardEvent keyboardEventUp = new KeyboardEvent();
            keyboardEventUp.setKey(KeyboardEvent.KEY_UP);
            keyboardEventUp.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(keyboardEventUp);

            KeyboardEvent keyboardEventDown = new KeyboardEvent();
            keyboardEventDown.setKey(KeyboardEvent.KEY_DOWN);
            keyboardEventDown.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(keyboardEventDown);



        }



        @Override
        public void keyPressed(KeyboardEvent keyboardEvent) {
            switch (keyboardEvent.getKey()) {
                case KeyboardEvent.KEY_LEFT:
                    player.translate(-20,0);
                    break;
                case KeyboardEvent.KEY_RIGHT:
                    player.translate(20,0);
                    break;
                case KeyboardEvent.KEY_UP:
                    player.translate(0,-20);
                    break;
                case KeyboardEvent.KEY_DOWN:
                    player.translate(0,20);
                    break;
                case KeyboardEvent.KEY_SPACE:
                    paint();

                break;


            }
        }

        public void paint(){
            if(player.getColor() == Color.BLUE){
                player.setColor(Color.DARK_GRAY);
            } else {
                player.setColor(Color.BLUE);
            }
            player.fill();
        }



        @Override
        public void keyReleased(KeyboardEvent keyboardEvent) {

        }



    }
