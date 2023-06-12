package com.map_generation.Model.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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