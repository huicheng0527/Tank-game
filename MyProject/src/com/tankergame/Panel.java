package com.tankergame;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

class StartPanel extends JPanel implements Runnable{
	
	int times =0;
	
	public void paint(Graphics g) {
		super.paint(g);
		g.fillRect(0, 0, 400, 400);
		
		if(times%2==0) {
			g.setColor(Color.blue);
			Font mf = new Font("TimesRoman", Font.BOLD,30);
			g.setFont(mf);
			g.drawString("stage 1", 150, 180);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			times++;
			this.repaint();
		}
	}
}

public class Panel extends JPanel implements KeyListener, Runnable{
	
	public final static int panelWidth = 400;
	public final static int panelLength = 400;
	
	MyTank mt = null;
	
	//determine it is a new game or continue last game
	//String flag="new game";
	
	
	int enSize = 3;
	Vector<OtherTank> ets = new Vector<OtherTank>();
	Vector<Bomb> bombs = new Vector<Bomb>();
	Vector<Node> nodes = new Vector<Node>();
	
	Image image1=null;
	Image image2=null;
	Image image3=null;
	
	//initiate panel
	public Panel(String flag) {
		
		Record.getRecord();
		
		mt = new MyTank(panelWidth/2 -10, panelLength-30);


		
		
		if(flag.equals("new game")) {
			for(int i= 0; i<enSize;i++)
			{
				//create a tank
				OtherTank et =new OtherTank((i)*(panelWidth/2 -10), 0);
				//OtherTank et =new OtherTank((i)*(panelWidth/13 -10), 0);
				et.setEts(ets);
				//start the thread for emnemy
				Thread t =new Thread(et);
				t.start();
				//put bullet into et
				Bullet s = new Bullet(et.getX()+10, et.getY()+30, et.getDirect());
				et.ss.add(s);
				
				Thread t2 = new Thread(s);
				t2.start();
				
				//add to the vector
				ets.add(et);
			}
		}else {

			System.out.println("else is in");
			nodes=new Record().getNodes();
			
			for(int i= 0; i<nodes.size();i++)
			{
				Node node=nodes.get(i);
				
				//create a tank
				OtherTank et =new OtherTank(node.x,node.y);
				et.setDirect(node.direct);
				//OtherTank et =new OtherTank((i)*(panelWidth/13 -10), 0);
				
				et.setEts(ets);
				//start the thread for emnemy
				Thread t =new Thread(et);
				t.start();
				//put bullet into et
				Bullet s = new Bullet(et.getX()+10, et.getY()+30, et.getDirect());
				et.ss.add(s);
				
				Thread t2 = new Thread(s);
				t2.start();
				
				//add to the vector
				ets.add(et);
			}
		}
		
		
		try {
			image1 = ImageIO.read(new File("D:/Java/JavaMaster/image/1.png"));
			image2 = ImageIO.read(new File("D:/Java/JavaMaster/image/2.png"));
			image3 = ImageIO.read(new File("D:/Java/JavaMaster/image/3.png"));
			//g.drawImage(image1, 233, 234, this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//painting
	public void paint(Graphics g) {
		super.paint(g);
		g.fillRect(0, 0, panelWidth, panelLength);
		
		showinfo(g);
		
		if(mt.isLive()) {
			this.drawTank(mt.x, mt.y, g, mt.direct, mt.color);
		}
		
		// draw bullet but only one bullet, need to improve by iterator
		
		for(int i=0;i<this.mt.ss.size();i++)
		{
			Bullet myshot = mt.ss.get(i);

			if(myshot!=null && myshot.isLive == true)
			{
				g.draw3DRect(myshot.x, myshot.y, 1, 1, false);
			}
			
			if(myshot.isLive == false)
			{
				//delete the bullet in Vector<ss>
				mt.ss.remove(myshot);
			}
		}
		
		
		//draw enemy tank and bullet
		for(int i=0;i<enSize;i++)
		{
			OtherTank et = ets.get(i);
					
			if(et.isLive)
			{
				this.drawTank(et.getX(), et.getY(), g, et.getDirect(), et.getColor());
				
			}
			
			for(int j=0;j<et.ss.size();j++)
			{
				Bullet emshot = et.ss.get(j);
				
				if(emshot!=null && emshot.isLive == true)
				{
					g.draw3DRect(emshot.x, emshot.y, 1, 1, false);
				}
				
				if(emshot.isLive == false)
				{
					//delete the bullet in Vector<ss>
					et.ss.remove(emshot);
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
	
	
	public void showinfo(Graphics g) {
		Font mf = new Font("TimesRoman", Font.BOLD,20);
		g.setFont(mf);
		g.setColor(Color.BLACK);
		g.drawString("Total info", 420, 30);
		
		this.drawTank(420, 40, g, 0, 1);
		g.setColor(Color.BLACK);
		g.drawString(Record.getTotalOt()+"", 450, 60);
		this.drawTank(480, 40, g, 0, 0);
		g.setColor(Color.BLACK);
		g.drawString(Record.getTotalMt()+"", 510, 60);
		
		g.setColor(Color.BLACK);
		g.drawString("Result info", 420, 95);
		
		this.drawTank(420, 115, g, 0, 1);
		g.setColor(Color.BLACK);
		g.drawString(Record.getOtDead()+"", 450, 135);
	}
	
	
	
	
	
	public void hitme()
	{
		for(int i=0;i<this.ets.size();i++)
		{
			OtherTank et = ets.get(i);
			
			for(int j=0;j<et.ss.size();j++)
			{
				Bullet emshot = et.ss.get(j);
				if(emshot.isLive)
				{
					if(mt.isLive)
					{
						if(this.hitTank(emshot, mt)) {
							Record.changeMyNum();
						}
					}
				}			
			}
		}
	}
	
	public void hitEnemytank()
	{
		for(int i=0;i<mt.ss.size();i++)
		{
			Bullet myshot = mt.ss.get(i);
			if(myshot.isLive)
			{
				for(int j=0;j<ets.size();j++)
				{
					OtherTank et = ets.get(j);
					if(et.isLive)
					{
						if(this.hitTank(myshot, et)) {
							Record.changeNum();
						}
						
					}
				}
			}
		}
	}
	//code a function to panduan the bullet is on enemy's tank
	public boolean hitTank(Bullet s, Tank et)
	{
		boolean isHit=false;
		//panduan fangxianng
		switch(et.direct)
		{
		case 0:
		case 2:
			if(s.x>et.getX() && s.x<et.getX()+20 && s.y>et.getY() && s.y<et.getY()+30)
			{
				s.isLive = false;
				et.isLive =false;
				//Record.changeNum();
				isHit=true;
				//create a bomb and put it into vector
				Bomb b = new Bomb(et.getX(),et.getY());
				bombs.add(b);
				
			}
			break;
		case 1:
		case 3:
			if(s.x>et.getX() && s.x<et.getX()+30 && s.y>et.getY() && s.y<et.getY()+20)
			{
				s.isLive = false;
				et.isLive =false;
				//Record.changeNum();
				isHit=true;
				Bomb b = new Bomb(et.getX(),et.getY());
				bombs.add(b);
			}
			break;
		}
		return isHit;
	}
	
	//method for drawtank
	public void drawTank(int x, int y, Graphics g, int direct, int color)
	{
		switch(color)
		{
		case 0://MyTankColor
			g.setColor(Color.cyan);
			break;
		case 1://OtherTankColor
			g.setColor(Color.green);
			break;
		}
		
		switch(direct)//0 indicate up, 1 is right, 2 is down, 3 is left
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
			if(this.mt.ss.size()<7)
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
				Thread.sleep(50);
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
