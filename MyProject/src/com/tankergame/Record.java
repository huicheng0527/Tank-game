package com.tankergame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

public class Record {
	
	private static int totalOt = 20;
	private static int totalMt = 3;
	private static int OtDead = 0;
	
	static Vector<Node> nodes = new Vector<Node>();
	
	public Vector<Node> getNodes() {
		try {
			fr =new FileReader("F:\\temp\\myRecord.txt");
			br =new BufferedReader(fr);
			String n ="";
			//read first line
			n=br.readLine();
			OtDead=Integer.parseInt(n);
			//read next line
			while((n=br.readLine())!=null) {
				String []xyd=n.split(" ");
				Node node=new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]),Integer.parseInt(xyd[2]));
				nodes.add(node);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			
			try {
				br.close();
				fr.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		return nodes;
	}
	
	
	private static FileWriter fw=null;
	private static BufferedWriter bw=null;
	private static FileReader fr=null;
	private static BufferedReader br=null;
	
	private static Vector<OtherTank> ets=new Vector<OtherTank>();
	
	public static void getRecord() {
		try {
			fr =new FileReader("F:\\temp\\myRecord.txt");
			br =new BufferedReader(fr);
			String n =br.readLine();
			OtDead=Integer.parseInt(n);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			
			try {
				br.close();
				fr.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public static void saveRecord() {
		try {
			fw =new FileWriter("F:\\temp\\myRecord.txt");
			bw =new BufferedWriter(fw);
			bw.write(OtDead+"\r\n");
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			
			try {
				bw.close();
				fw.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public static void savelocation() {
		try {
			fw =new FileWriter("F:\\temp\\myRecord.txt");
			bw =new BufferedWriter(fw);
			bw.write(OtDead+"\r\n");
			
			for(int i=0;i<ets.size();i++) {
				OtherTank et=ets.get(i);
				
				if(et.isLive) {
					
					String coor=et.x+" "+et.y+" "+et.direct;
					
					bw.write(coor+"\r\n");
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			
			try {
				bw.close();
				fw.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public static int getTotalOt() {
		return totalOt;
	}
	public static void setTotalOt(int totalOt) {
		Record.totalOt = totalOt;
	}
	public static int getTotalMt() {
		return totalMt;
	}
	public static void setTotalMt(int totalMt) {
		Record.totalMt = totalMt;
	}
	public static int getOtDead() {
		return OtDead;
	}
	public static void setOtDead(int otDead) {
		OtDead = otDead;
	}
	
	public static void changeNum(){
		totalOt--;
		OtDead++;
	}
	
	public static void changeMyNum() {
		totalMt--;
	}

	public static Vector<OtherTank> getEts() {
		return ets;
	}

	public static void setEts(Vector<OtherTank> es) {
		ets = es;
	}
	
}
