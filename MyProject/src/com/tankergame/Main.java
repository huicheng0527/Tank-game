package com.tankergame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Main extends JFrame implements ActionListener{

	JMenuBar jmb=null;
	JMenu jm=null;
	JMenuItem jmi1=null;
	JMenuItem jmi2=null;
	JMenuItem jmi3=null;
	JMenuItem jmi4=null;
	
	Panel p = null;
	
	StartPanel sp = null;
	private final int width = 600;
	private final int length = 500;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main m = new Main();
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m.setVisible(true);
		
	}

	public Main(){
		super("Tanker game by Hongkai");
		
		sp = new StartPanel();
		Thread t = new Thread(sp);
		t.start();
		jmb= new JMenuBar();
		jm=new JMenu("Game(G)");
		jm.setMnemonic('G');
		jmi1=new JMenuItem("Start new Game(S)");
		jmi1.setMnemonic('S');
		//listen jmi1
		jmi1.addActionListener(this);
		
		jmi2=new JMenuItem("Exit(E)");
		jmi2.setMnemonic('E');
		//listen jmi2
		jmi2.addActionListener(this);
		
		jmi3=new JMenuItem("Save & Exit(A)");
		jmi3.setMnemonic('A');
		//listen jmi2
		jmi3.addActionListener(this);
		
		jmi4=new JMenuItem("Continue(C");
		jmi4.setMnemonic('C');
		//listen jmi2
		jmi4.addActionListener(this);
		
		jm.add(jmi1);
		jm.add(jmi2);
		jm.add(jmi3);
		jm.add(jmi4);
		jmb.add(jm);
		
		this.setJMenuBar(jmb);
		this.add(sp);
		
		this.setSize(width,length);
		this.setLocation(200, 30);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==jmi1) {
			p = new Panel("new game");
			this.addKeyListener(p);
			Thread t = new Thread(p);
			t.start();
			this.remove(sp);
			this.add(p);
			this.setVisible(true);
		}
		else if(arg0.getSource()==jmi2) {
			Record.saveRecord();
			System.exit(0);
		}
		else if(arg0.getSource()==jmi3) {
			Record.setEts(p.ets);
			Record.savelocation();
			System.exit(0);

		}
		else if(arg0.getSource()==jmi4) {
			
			p = new Panel("continue");
			
			this.addKeyListener(p);
			Thread t = new Thread(p);
			t.start();
			this.remove(sp);
			this.add(p);
			this.setVisible(true);
		}
	}
}
