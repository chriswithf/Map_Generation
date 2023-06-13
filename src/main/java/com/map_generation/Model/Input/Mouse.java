package com.map_generation.Model.Input;

import java.awt.event.*;

/**
 * The Mouse class is responsible for reading the input from the mouse.
 * It implements the MouseListener, MouseMotionListener, and MouseWheelListener
 * interfaces from Java AWT and provides methods to get the difference in the
 * x and y coordinates of the mouse, whether the mouse is being dragged, and
 * the wheel rotation of the mouse.
 * The class also provides a method to reset the difference in the x and y
 * coordinates and the wheel rotation.
 * 
 * @author chris
 */

public final class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {
    private static int latestX;
    private static int latestY;
    private static int diffX;
    private static int diffY;
    private static boolean dragged;
    private static int wheelRotation;

    public int getDiffX() {
        return diffX;
    }

    public int getDiffY() {
        return diffY;
    }

    public boolean isDragged() {
        return dragged;
    }

    public void reset() {
        diffX = 0;
        diffY = 0;
        wheelRotation = 0;
    }

    public int getWheelRotation() {
        return wheelRotation;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        int actualX = mouseEvent.getX();
        int actualY = mouseEvent.getY();

        diffX = actualX - latestX;
        diffY = actualY - latestY;

        latestX = actualX;
        latestY = actualY;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        dragged = true;

        latestX = mouseEvent.getX();
        latestY = mouseEvent.getY();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        dragged = false;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        wheelRotation = mouseWheelEvent.getWheelRotation();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
