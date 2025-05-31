package org.example;

import javax.swing.*;
import java.awt.*;

public class GameImages {
    public static Image loadImage(String path) {
        return new ImageIcon(GameImages.class.getResource(path)).getImage();
    }
}
