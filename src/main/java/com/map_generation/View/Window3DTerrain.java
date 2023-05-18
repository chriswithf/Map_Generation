package com.map_generation.View;

import com.map_generation.Model.Input.Keyboard;
import com.map_generation.Model.Input.Mouse;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public final class Window3DTerrain extends JFrame {
    private final BufferedImage bufferedImage;

    public Window3DTerrain(BufferedImage bufferedImage, Mouse mouse, Keyboard keyboard) {
        this.bufferedImage = bufferedImage;

        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        addMouseWheelListener(mouse);
        addKeyListener(keyboard);

        setTitle("3D Terrain");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(bufferedImage.getWidth(), bufferedImage.getHeight());
        setLocationRelativeTo(null);
        //setResizable(false);
        setFocusable(true);
        setVisible(true);
    }

    public void draw() {
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();

        graphics.drawImage(bufferedImage, 0, 0, null);
        bufferStrategy.show();
        graphics.dispose();
    }
}
