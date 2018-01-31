package com.project;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class MovingTank3 extends JFrame{
	
	MyPanel mp=null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MovingTank3 ts = new MovingTank3();
	}
		
	public MovingTank3()
	{
		mp=new MyPanel(); 
		
		this.addKeyListener(mp);
		Thread td = new Thread(mp);
		td.start();
		
		this.add(mp);
		this.setSize(400,400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}


class MyPanel extends JPanel implements KeyListener, Runnable
{
	//defind a mytank
	MyTank mt = null;
	
	Vector<EnemyTank> ets = new Vector<EnemyTank>();
	
	//defind a bomb 
	Vector<Bomb> bombs = new Vector<Bomb>();
	
	int enSize = 3;
	
	Image image1=null;
	Image image2=null;
	Image image3=null;
	
	public  MyPanel()
	{
		mt = new MyTank(200,320);
		
		//initilize enemytank
		for(int i= 0; i<enSize;i++)
		{
			//create a tank
			EnemyTank et =new EnemyTank((i)*170+10, 0);
			et.setColor(1);
			et.setDirect(2);
			//start the thread for emnemy
			Thread t =new Thread(et);
			t.start();
			//put bullet into et
			shot s = new shot(et.getX()+10, et.getY()+30, et.getDirect());
			et.ss.add(s);
			
			Thread t2 = new Thread(s);
			t2.start();
			
			//add to the vector
			ets.add(et);
		}
		
		//Toolkit toolkit = Toolkit.getDefaultToolkit();
//		image1 = toolkit.getImage("1.png");
//		image2 = toolkit.getImage("2.png");
//		image3 = toolkit.getImage("3.png");
		
//		image1 = ImageIO.read(new File("1.png"));
//		image2 = ImageIO.read(new File("2.png"));
//		image3 = ImageIO.read(new File("3.png"));
		
		try {
			image1 = ImageIO.read(new File("1.png"));
			image2 = ImageIO.read(new File("2.png"));
			image3 = ImageIO.read(new File("3.png"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		
		//draw the tank
		g.fillRect(0, 0, 400, 400);
		
		if(mt.isLive)
		{
			this.drawTank(mt.getX(), mt.getY(), g, mt.getDirect(), mt.getColor());
		}
		
		//get each bullet in ss and draw it
		for(int i=0;i<this.mt.ss.size();i++)
		{
			shot myshot = mt.ss.get(i);
			// draw bullet but only one bullet, need to improve by iterator
			if(myshot!=null && myshot.isLive == true)
			{
				g.draw3DRect(myshot.x, myshot.y, 1, 1, false);
			}
			
			if(myshot.isLive == false)
			{
				//delect the bullet in Vector<ss>
				mt.ss.remove(myshot);
			}
		}
		
		//draw enemy tank and bullet
		for(int i=0;i<enSize;i++)
		{
			EnemyTank et = ets.get(i);
					
			if(et.isLive)
			{
				this.drawTank(et.getX(), et.getY(), g, et.getDirect(), et.getColor());
				
				for(int j=0;j<et.ss.size();j++)
				{
					shot emshot = et.ss.get(j);
					
					// draw bullet but only one bullet, need to improve by iterator
					if(emshot!=null && emshot.isLive == true)
					{
						g.draw3DRect(emshot.x, emshot.y, 1, 1, false);
					}
					
					if(emshot.isLive == false)
					{
						//delect the bullet in Vector<ss>
						et.ss.remove(emshot);
					}
				}
				
				
			}
		}
		
		//draw the bomb
		for(int i=0;i<bombs.size();i++)
		{
			
			//System.out.println("bomb size " + bombs.size());
			Bomb b = bombs.get(i);
			
			if(b.life >6)
			{
				g.drawImage(image1, b.x, b.y, 30, 30, this);
			}
			else if (b.life > 3)
			{
				g.drawImage(image2, b.x, b.y, 30, 30, this);
			}
			else
			{
				g.drawImage(image3, b.x, b.y, 30, 30, this);
			}
			
			b.lifeDown();
			
			if(b.life ==0)
			{
				bombs.remove(b);
			}
			
		}
		
		
	}
	
	public void hitme()
	{
		for(int i=0;i<this.ets.size();i++)
		{
			EnemyTank et = ets.get(i);
			
			for(int j=0;j<et.ss.size();j++)
			{
				shot emshot = et.ss.get(j);
				if(emshot.isLive)
				{
					if(mt.isLive)
					{
						this.hitTank(emshot, mt);
					}
				}			
			}
		}
	}
	
	public void hitEnemytank()
	{
		for(int i=0;i<mt.ss.size();i++)
		{
			shot myshot = mt.ss.get(i);
			if(myshot.isLive)
			{
				for(int j=0;j<ets.size();j++)
				{
					EnemyTank et = ets.get(j);
					if(et.isLive)
					{
						this.hitTank(myshot, et);
					}
				}
			}
		}
	}
	//code a function to panduan the bullet is on enemy's tank
	public void hitTank(shot s, Tank et)
	{
		//panduan fangxianng
		switch(et.direct)
		{
		case 0:
		case 2:
			if(s.x>et.getX() && s.x<et.getX()+20 && s.y>et.getY() && s.y<et.getY()+30)
			{
				s.isLive = false;
				et.isLive =false;
				
				//create a bomb and put it into vector
				Bomb b = new Bomb(et.getX(),et.getY());
				bombs.add(b);
				
			}
		case 1:
		case 3:
			if(s.x>et.getX() && s.x<et.getX()+30 && s.y>et.getY() && s.y<et.getY()+20)
			{
				s.isLive = false;
				et.isLive =false;
				
				Bomb b = new Bomb(et.getX(),et.getY());
				bombs.add(b);
			}
		}
	}
	
	
	
	public void drawTank(int x, int y, Graphics g, int direct, int type)
	{
		switch(type)
		{
		case 0:
			g.setColor(Color.cyan);
			break;
		case 1:
			g.setColor(Color.green);
			break;
		}
		
		switch(direct)
		{
		case 0:
			g.fill3DRect(x, y, 5, 30, false);
			g.fill3DRect(x+15, y, 5, 30, false);
			g.fill3DRect(x+5, y+5, 10, 20, false);
			g.fillOval(x+5, y+10, 10, 10);
			g.drawLine(x+10, y+15, x+10, y);
			break;
		case 1:
			g.fill3DRect(x, y, 30, 5, false);
			g.fill3DRect(x, y+15, 30, 5, false);
			g.fill3DRect(x+5, y+5, 20, 10, false);
			g.fillOval(x+10, y+5, 10, 10);
			g.drawLine(x+15, y+10, x+30, y+10);
			break;
		case 2:
			g.fill3DRect(x, y, 5, 30, false);
			g.fill3DRect(x+15, y, 5, 30, false);
			g.fill3DRect(x+5, y+5, 10, 20, false);
			g.fillOval(x+5, y+10, 10, 10);
			g.drawLine(x+10, y+15, x+10, y+30);
			break;
		case 3:
			g.fill3DRect(x, y, 30, 5, false);
			g.fill3DRect(x, y+15, 30, 5, false);
			g.fill3DRect(x+5, y+5, 20, 10, false);
			g.fillOval(x+10, y+5, 10, 10);
			g.drawLine(x+15, y+10, x, y+10);
			break;
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode() == KeyEvent.VK_W)
		{
			//set the direct of my tank
			this.mt.setDirect(0);
			this.mt.moveUp();
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_D)
		{
			//set the direct of my tank
			this.mt.setDirect(1);
			this.mt.moveRight();
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_S)
		{
			//set the direct of my tank
			this.mt.setDirect(2);
			this.mt.moveDown();
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_A)
		{
			//set the direct of my tank
			this.mt.setDirect(3);
			this.mt.moveLeft();
		}
		
		if(arg0.getKeyCode() == KeyEvent.VK_J)
		{
			//play press j to shot
			if(this.mt.ss.size()<5)
			{
				this.mt.shotEnemy();
			}
		}
		
		this.repaint();
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//repaint the panel in 100ms
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.hitEnemytank();
			
			this.hitme();
			
			
			this.repaint();
		}
	}

}
