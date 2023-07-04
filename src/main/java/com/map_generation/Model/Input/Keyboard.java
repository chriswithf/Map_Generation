package com.map_generation.Model.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The Keyboard class is responsible for reading the input from the keyboard.
 * It implements the KeyListener interface from Java AWT and provides methods
 * to get the current key code and the current released key code.
 * The class also provides a method to reset the released key code.
 * Overall, the Keyboard class enables the reading of input from the keyboard.
 *
 * @see KeyListener
 * @see KeyEvent
 * @see java.awt
 * @see java.awt.event
 */

public final class Keyboard implements KeyListener {
    private static int keyCode;
    private static int releasedKeyCode;

    public int getCurrentKeyCode() {
        return keyCode;
    }

    public int getCurrentReleasedKeyCode() {
        return releasedKeyCode;
    }

    public void reset() {
        releasedKeyCode = -1;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        keyCode = keyEvent.getKeyCode();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        releasedKeyCode = keyCode;
        keyCode = -1;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }
}