package com.tankergame;

public class Bullet implements Runnable {

	int x;
	int y;
	int direct;
	int speed = 5;
	boolean isLive = true;
	
	public Bullet(int x, int y, int direct)
	{
		this.x = x;
		this.y = y;
		this.direct = direct;
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			switch(direct)
			{
			case 0:
				y-=speed;
				break;
			case 1:
				x+=speed;
				break;
			case 2:
				y+=speed;
				break;
			case 3:
				x-=speed;
				break;
			}
			
			//System.out.println("the coordinate of bullet is " +x +"," +y);
			
			if(x<0 || x>Panel.panelWidth || y<0 || y>Panel.panelLength)
			{
				isLive = false;
				break;
			}
		}
	}
			
}
