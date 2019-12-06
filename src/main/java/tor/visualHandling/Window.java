package tor.visualHandling;

import tor.Manager;

import javax.swing.*;

public class Window extends JFrame
{
    public static final int width = 800, height = 500;

    public Window(Manager manager){
        Renderer renderer = new Renderer(manager);
        add(renderer);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
