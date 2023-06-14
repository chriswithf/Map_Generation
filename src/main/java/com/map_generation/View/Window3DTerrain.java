package com.map_generation.View;

import com.map_generation.Model.Input.Keyboard;
import com.map_generation.Model.Input.Mouse;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * The Window3DTerrain class is responsible for displaying the 3D terrain.
 * It extends the JFrame class from Java Swing and provides a method to draw
 * the 3D terrain.
 * Overall, the Window3DTerrain class enables the display of the 3D terrain.
 * 
 * @see JFrame
 * @see java.awt
 * @see java.awt.image
 * 
 * @author chris
 */

public final class Window3DTerrain extends JFrame {

    /**
     * The image to be displayed
     */
    private final BufferedImage bufferedImage;

    /**
     * @param bufferedImage the image to be displayed
     * @param mouse        the mouse object
     * @param keyboard     the keyboard object
     */
    public Window3DTerrain(BufferedImage bufferedImage, Mouse mouse, Keyboard keyboard) {
        this.bufferedImage = bufferedImage;

        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        addMouseWheelListener(mouse);
        addKeyListener(keyboard);
        setTitle("3D Terrain");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(bufferedImage.getWidth(), bufferedImage.getHeight());
        setLocationRelativeTo(null);
        setResizable(false);
        setFocusable(true);
        setVisible(true);
    }

    /**
     * Draws the image to the frame
     */
    public void draw() {
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }

        try {
            Graphics graphics = bufferStrategy.getDrawGraphics();
            graphics.drawImage(bufferedImage, 0, 0, null);
            bufferStrategy.show();
            graphics.dispose();
        } catch (Exception e) {

        }
    }

    /**
     * Closes the window
     */
    public void close() {
        dispose();
    }
}
