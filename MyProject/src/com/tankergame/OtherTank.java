package com.tankergame;

import java.util.Vector;


public class OtherTank extends Tank implements Runnable{

	Vector<OtherTank> ets = new Vector<OtherTank>();
	
	int times = 0;
	Vector<Bullet> ss = new Vector<Bullet>();
	int randomChangeDir = (int)(Math.random()*20+5);
	
	public OtherTank(int x, int y) {
		super(x, y);
		this.setColor(1);
		this.setDirect(2);
		this.setLive(true);
		this.setSpeed(3);
		// TODO Auto-generated constructor stub
	}
	
	public void setEts(Vector<OtherTank> vo) {
		this.ets=vo;
	}
	
	public boolean isTouchTank() {
		
		boolean b=false;
		
		switch(this.direct) {
		case 0://this.direct is 0
			for(int i=0;i<ets.size();i++) {
				OtherTank et=ets.get(i);
				
				if(et!=this) {
					if(et.direct==0||et.direct==2) {
						if(this.y>=et.y && this.y<=et.y+30 && this.x>=et.x && this.x<=et.x+20) {
							return true;
						}
						if(this.y>=et.y && this.y<=et.y+30 && this.x+20>=et.x && this.x+20<=et.x+20) {
							return true;
						}
					}
					if(et.direct==1||et.direct==3) {
						if(this.y>=et.y && this.y<=et.y+20 && this.x>=et.x && this.x<=et.x+30) {
							return true;
						}
						if(this.y>=et.y && this.y<=et.y+20 && this.x+20>=et.x && this.x+20<=et.x+30) {
							return true;
						}
					}
				}
			}
			break;
		case 1:
			for(int i=0;i<ets.size();i++) {
				OtherTank et=ets.get(i);
				
				if(et!=this) {
					if(et.direct==0||et.direct==2) {
						if(this.y>=et.y && this.y<=et.y+30 && this.x+30>=et.x && this.x+30<=et.x+20) {
							return true;
						}
						if(this.y+20>=et.y && this.y+20<=et.y+30 && this.x+30>=et.x && this.x+30<=et.x+20) {
							return true;
						}
					}
					if(et.direct==1||et.direct==3) {
						if(this.y>=et.y && this.y<=et.y+20 && this.x+30>=et.x && this.x+30<=et.x+30) {
							return true;
						}
						if(this.y+20>=et.y && this.y+20<=et.y+20 && this.x+30>=et.x && this.x+30<=et.x+30) {
							return true;
						}
					}
				}
			}
			break;
		case 2:
			for(int i=0;i<ets.size();i++) {
				OtherTank et=ets.get(i);
				
				if(et!=this) {
					if(et.direct==0||et.direct==2) {
						if(this.y+30>=et.y && this.y+30<=et.y+30 && this.x+20>=et.x && this.x+20<=et.x+20) {
							return true;
						}
						if(this.y+30>=et.y && this.y+30<=et.y+30 && this.x>=et.x && this.x<=et.x+20) {
							return true;
						}
					}
					if(et.direct==1||et.direct==3) {
						if(this.y+30>=et.y && this.y+30<=et.y+20 && this.x+20>=et.x && this.x+20<=et.x+30) {
							return true;
						}
						if(this.y+30>=et.y && this.y+30<=et.y+20 && this.x>=et.x && this.x<=et.x+30) {
							return true;
						}
					}
				}
			}
			break;
		case 3:
			for(int i=0;i<ets.size();i++) {
				OtherTank et=ets.get(i);
				
				if(et!=this) {
					if(et.direct==0||et.direct==2) {
						if(this.y>=et.y && this.y<=et.y+30 && this.x>=et.x && this.x<=et.x+20) {
							return true;
						}
						if(this.y+20>=et.y && this.y+20<=et.y+30 && this.x>=et.x && this.x<=et.x+20) {
							return true;
						}
					}
					if(et.direct==1||et.direct==3) {
						if(this.y>=et.y && this.y<=et.y+20 && this.x>=et.x && this.x<=et.x+30) {
							return true;
						}
						if(this.y+20>=et.y && this.y+20<=et.y+20 && this.x>=et.x && this.x<=et.x+30) {
							return true;
						}
					}
				}
			}
			break;
		}
		
		return b;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			//Math.random()
			switch(this.direct)
			{
			case 0:
				//move up
				for(int i = 0; i<randomChangeDir;i++)
				{
					if(y>0 && !this.isTouchTank())
					{y-=speed;}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case 1:
				for(int i = 0; i<randomChangeDir;i++)
				{
					if(x<Panel.panelWidth-30 && !this.isTouchTank())
					{x+=speed;}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case 2:
				for(int i = 0; i<randomChangeDir;i++)
				{
					if(y<Panel.panelLength-30 && !this.isTouchTank())
					{y+=speed;}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case 3:
				for(int i = 0; i<randomChangeDir;i++)
				{
					if(x>0 && !this.isTouchTank())
					{x-=speed;}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			}
			
			//let the tank randomly create a direction
			this.direct = (int)(Math.random()*4);
			
			
			//panduan the enemy is live or not
			if(this.isLive==false)
			{
				//let the tank log out the thread;
				break;
			}
			
			this.times++;
			if(times%2==0)
			{
				//panduan the bullet is gone or not
					if(isLive)
					{
						if(ss.size()<3)
						{
							Bullet s =null;

							switch(direct)
							{
							case 0:
								s = new Bullet(getX()+10,getY(),direct);
								ss.add(s);
								break;
							case 1:
								s = new Bullet(getX()+30,getY()+10,direct);
								ss.add(s);
								break;
							case 2:
								s = new Bullet(getX()+10,getY()+30,direct);
								ss.add(s);
								break;
							case 3:
								s = new Bullet(getX()+0,getY()+10,direct);
								ss.add(s);
								break;
							}
							
							//qidong the thread of bullet
							Thread t = new Thread(s);
							t.start();
						}
					}
					
				}
			}
		
	}

}
