package tor.visualHandling;

import tor.controller.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class Window extends JFrame
{
    public static final int width = 800, height = 500;
    private Renderer renderer;

    public Window(Manager manager){
        renderer = new Renderer(manager);
        add(renderer);
        pack();
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        System.out.println(isFocusable());
        addKeyListener(manager.getMover());
        //TODO: for custom cursor, being blank that is, only movement matters right now
        //getContentPane().setCursor(Toolkit.getDefaultToolkit().createCustomCursor());
    }

    public Renderer getRenderer(){
        return renderer;
    }
}
