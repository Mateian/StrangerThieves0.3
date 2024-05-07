package PaooGame.main;

import PaooGame.Game;

import javax.swing.*;
import java.awt.*;

public class GameWindow
{
    Game gp;
    private JFrame  wndFrame;       /*!< fereastra principala a jocului*/
    private final String  wndTitle;       /*!< titlul ferestrei*/
    private final int     wndWidth;       /*!< latimea ferestrei in pixeli*/
    private final int     wndHeight;      /*!< inaltimea ferestrei in pixeli*/

    public GameWindow(String title, int width, int height, Game gp){
        this.gp = gp;
        wndTitle    = title;    /*!< Retine titlul ferestrei.*/
        wndWidth    = width;    /*!< Retine latimea ferestrei.*/
        wndHeight   = height;   /*!< Retine inaltimea ferestrei.*/
        wndFrame    = null;     /*!< Fereastra nu este construita.*/
    }

    public void BuildGameWindow()
    {
        if(wndFrame != null)
        {
            return;
        }

//        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
//        this.setBackground(Color.black);
//        this.setDoubleBuffered(true);
//        this.addKeyListener(keyH);
//        this.setFocusable(true);

        wndFrame = new JFrame(wndTitle);
        wndFrame.setSize(wndWidth, wndHeight);
        wndFrame.setPreferredSize(new Dimension(wndWidth, wndHeight));
        wndFrame.setBackground(Color.black);
        wndFrame.addKeyListener(this.gp.keyH);
        wndFrame.setFocusable(true);
        wndFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wndFrame.setResizable(false);
        wndFrame.setLocationRelativeTo(null);
        wndFrame.setVisible(true);
        wndFrame.add(gp);
        wndFrame.pack();}


    public int GetWndWidth()
    {
        return wndWidth;
    }

    public int GetWndHeight()
    {
        return wndHeight;
    }
}
