package com.tankergame;

import java.util.*;


public class MyTank extends Tank{

	public MyTank(int x, int y) {
		super(x, y);
		this.setColor(0);
		this.setDirect(0);
		this.setLive(true);
		this.setSpeed(3);
		// TODO Auto-generated constructor stub
	}

	
	//tank is moving up
			public void moveUp()
			{
				if(y>0)
				{y-=speed;}
			}
			public void moveRight()
			{
				if(x<Panel.panelWidth-30)
				{x+=speed;}
			}
			public void moveDown()
			{
				if(y<Panel.panelLength-30)
				{y+=speed;}
			}
			public void moveLeft()
			{
				if(x>0)
				{x-=speed;}
			}
			
			Vector<Bullet> ss=new Vector<Bullet>();
			Bullet s=null;
			
			public void shotEnemy()
			{	
				
				switch(this.direct)
				{
				case 0:
					s = new Bullet(x+10,y,0);
					ss.add(s);
					break;
				case 1:
					s = new Bullet(x+30,y+10,1);
					ss.add(s);
					break;
				case 2:
					s = new Bullet(x+10,y+30,2);
					ss.add(s);
					break;
				case 3:
					s = new Bullet(x,y+10,3);
					ss.add(s);
					break;
				}
				
				Thread t = new Thread(s);
				t.start();
			}
}
