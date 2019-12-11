package tor.visualHandling;

import tor.Manager;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame
{
    public static final int width = 800, height = 500;

    public Window(Manager manager){
        Renderer renderer = new Renderer(manager);
        add(renderer);
        pack();
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
