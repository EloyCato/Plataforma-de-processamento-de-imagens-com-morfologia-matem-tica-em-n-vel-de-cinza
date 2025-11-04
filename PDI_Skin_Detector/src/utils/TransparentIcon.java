package utils;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Math
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TransparentIcon implements Icon {
    private final int width;
    private final int height;
    private final Image image;

    public TransparentIcon(int width, int height) {
        this.width = width;
        this.height = height;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // img já é transparente por padrão
        this.image = img;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.drawImage(image, x, y, null);
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }
}
