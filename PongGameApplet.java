
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;



@SuppressWarnings("serial")
public class PongGameApplet extends JApplet implements Runnable, KeyListener
{
    final int size = 50;
    final int speedP = 70;
    final int speedXc = 70;
    int x, y, p1x, p1y, ex, cx, cy, score, speedX, speedY;
    Thread thread;
    boolean started = false;
    boolean loss;




    public void init ()
    {
	setSize (500, 500);
	setUp ();
	thread = new Thread (this);
	addKeyListener (this);
	setFocusable(true);
    }


    public void setUp ()
    {
	x = 250;
	y = 300;
	speedX = 20;
	speedY = 20;
	p1x = 300;
	p1y = 470;
	cx = 300;
	cy = 20;
	score = 0;
	loss = false;
    }


    public void moveBall ()
    {

	x = x + speedX;
	y = y + speedY;

	if (x >= 499 || x <= 0)
	{
	    speedX *= -1;
	}

	if (x + size >= p1x && x <= p1x + 100 && y + size == p1y)
	{
	    speedY *= -1;
	    score++;
	}
	else if (y + size > p1y)
	{

	    loss = true;
	}
    }


    public void run ()
    {
	while (true)
	{
	    if (loss == false)
	    {
		movePaddleComp ();
		moveBall ();
		repaint ();
	    }

	    try
	    {
		Thread.sleep (40);
	    }
	    catch (Exception e)
	    {
	    }
	}
    }


    public void paint (Graphics g)
    {
	super.paint (g);
	g.setFont (new Font ("times new roman", Font.BOLD, 20));
	g.setColor (Color.green);
	g.fillRect (0, 0, getWidth (), getHeight ());
	g.setColor (Color.red);
	paintBall (g);
	paintPaddlePlayer (g);
	paintPaddleComp (g);
	g.drawString ("Your Score: " + score, 20, 80);

	if (started == false)
	{
	    g.setFont (new Font ("times new roman", Font.BOLD, 20));
	    g.drawString ("Press S to start game. ", 150, 250);
	}
	if (loss == true)
	{
	    g.setFont (new Font ("times new roman", Font.BOLD, 20));
	    g.drawString ("You Lost, press R to restart. ", 120, 250);
	}


    }



    public void paintBall (Graphics g)
    {
	g.setColor (Color.BLACK);
	g.fillOval (x, y, size, size);
    }


    public void paintPaddlePlayer (Graphics g)
    {
	g.fillRect (p1x, p1y, 100, 20);
    }


    public void paintPaddleComp (Graphics g)
    {
	g.fillRect (cx, cy, 100, 20);
    }


    public void movePaddleComp ()
    {
	if (cx > x + size && cx > 0)
	{
	    cx -= speedXc;
	}
	else if (cx + 100 < x && cx <= 499)
	{
	    cx += speedXc;
	}
	if (x + size >= cx && x <= cx + 100 && y == cy)
	{
	    speedY *= -1;
	}
    }


    public void keyTyped (KeyEvent ke)
    {
    }


    public void keyPressed (KeyEvent ke)
    {
	int key = ke.getKeyCode ();

	if (key == KeyEvent.VK_LEFT && p1x >= 0)
	{
	    ex = p1x;
	    p1x -= speedP;
	}
	if (key == KeyEvent.VK_RIGHT && p1x + 100 <= 499)
	{
	    ex = p1x;
	    p1x += speedP;
	}
	if (key == KeyEvent.VK_R)
	{
	    setUp ();
	}
	if (key == KeyEvent.VK_S && started == false)
	{
	    started = true;
	    thread.start ();
	}

    }


    public void keyReleased (KeyEvent ke)
    {
    }
} // Ball class
