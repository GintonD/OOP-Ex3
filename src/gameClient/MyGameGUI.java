package gameClient;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Fruit;
import dataStructure.Robot;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;
import utils.StdDraw;

public class MyGameGUI implements ActionListener, Serializable
{
	private final double EPSILON = 0.0001;
	private static DecimalFormat df2 = new DecimalFormat("#.###");
	game_service game;
	ArrayList<Fruit> FruitsList;
	ArrayList<Robot> RobotsList;

	graph gr;
	KML_Logger kml;
	Graph_Algo GrAl = new Graph_Algo();
	double min_x=Integer.MAX_VALUE;
	double max_x=Integer.MIN_VALUE;
	double min_y=Integer.MAX_VALUE;
	double max_y=Integer.MIN_VALUE;
	GameManager gm;
	double MouseX;
	double MouseY;
	Robot r12 = null;
	boolean robots = true;
	int moveRobot;
	private int GameCounter=0;
	static int robotChoosen=0;
//************Constructors*****************************

	public MyGameGUI() 
	{
		this.gr = null;
		this.FruitsList = new ArrayList<Fruit>();
		this.RobotsList = new ArrayList<Robot>();
		
		initGUI();
	}

	public MyGameGUI(DGraph gr) 
	{
		this.gr = gr;
		this.FruitsList = new ArrayList<Fruit>();
		this.RobotsList = new ArrayList<Robot>();
		initGUI();
	}

/**
 * init the Gui via StdDraw class
 */
	void initGUI()
	{
		StdDraw.enableDoubleBuffering();
		if(!StdDraw.getIsPaint()) 
		{
			StdDraw.setCanvasSize(800, 600);
			StdDraw.setIsPaint();
		}
		//scale..
		if(gr != null)
		{
			Collection<node_data> nodes = gr.getV();
			for (node_data node_data : nodes) 
			{
				Point3D p = node_data.getLocation();
				if(p.x() < min_x) min_x = p.x();
				if(p.x() > max_x) max_x = p.x();
				if(p.y() > max_y) max_y = p.y();
				if(p.y() < min_y) min_y = p.y();
			}
		}
		
		StdDraw.setXscale(min_x , max_x);
		StdDraw.setYscale(min_y, max_y);
		StdDraw.setGraphGUI(this);
		StdDraw.show();
		//paint(game);
	}

	
	
	
	/**
	 * paint and update all detials in the gui from the server
	 * @param game - data from the server
	 */
	public void paint(game_service game) 
	{
		StdDraw.clear();
		StdDraw.setPenRadius(0.0005);
		//vertex
		Collection<node_data> nodes = gr.getV();
		for(node_data n: nodes) 
		{
			Point3D p = n.getLocation();
			StdDraw.setPenColor(Color.blue);
			StdDraw.filledCircle(p.x(), p.y(),0.00007);
			StdDraw.text(p.x(),p.y()+0.00015, ""+ n.getKey());
			
			//edge
			Collection<edge_data> edges = gr.getE(n.getKey());
			for(edge_data e: edges) 
			{	
				StdDraw.setPenColor(Color.RED);
				node_data n2 = gr.getNode(e.getDest());
				Point3D p2 = n2.getLocation();
				StdDraw.line(p.x(), p.y(), p2.x() , p2.y());
				//weight
//				double midx= (p.x() + p2.x())/2;
//				double midy = (p.y() + p2.y())/2;
//				StdDraw.setFont(new Font("msyh", Font.BOLD, 10));
//				StdDraw.text((midx+ p2.x())/2 , (midy+p2.y())/2+0.00005, ""+ ""+df2.format(e.getWeight()));
//				StdDraw.setPenColor(Color.YELLOW);
//				StdDraw.filledCircle((midx+ p2.x())/2 , (midy+p2.y())/2, 0.00005);
			}
		}
		
		// draw clock and logo
		StdDraw.picture(min_x+0.0003, max_y-0.0003, "supermario.png", 0.003, 0.001);
		StdDraw.text(max_x-0.0001,min_y+0.0001,"00:" + game.timeToEnd()/1000);
		
		//extract from json and draw the score
		int score=0;
		try 
		{
			JSONObject object;
			object = new JSONObject(game.toString());
			JSONObject cgame = (JSONObject) object.get("GameServer");
			score = cgame.getInt("grade");
		} 
		catch (JSONException e1)
		{
			e1.printStackTrace();
		}
		//draw score
		StdDraw.text(min_x-0.0001,min_y+0.0001,"Score:" + score);
		
		//calculate and create fruits list
		gm = new GameManager(game);
		FruitsList  = gm.GetFruitList();


		
		
		
		// draw the fruit on the graph
		if(!FruitsList.isEmpty())
		{
			for(Fruit f : FruitsList) 
			{
				
				try 
				{
					this.kml.SetFruit(f);
				} 
				catch (ParseException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				findFruitEdge(f);
				Point3D p = f.getPos();
				
				if(f.getType() == 1)
					StdDraw.picture(p.x(), p.y(), "apple2.jpg", 0.0010, 0.0008);
				else 
				{
					StdDraw.picture(p.x(), p.y(), "banana1.png", 0.0010, 0.0008);
				}
			}
		}
		
		
		//clear Robots collection if needed
		if(RobotsList!= null) 
		{	
			RobotsList.clear();
			RobotsList = new ArrayList<Robot>(5+this.GameCounter);
		}
		else
			RobotsList = new ArrayList<Robot>(+this.GameCounter);
			
		
		RobotsList = gm.initRobots(game,gr);	
		
		// draw the robots on the graph
		if(!RobotsList.isEmpty())
		{
			
			for(Robot r : RobotsList) 
			{
				//normalize the appering of the robot in the kml file..

				try {
					this.kml.SetRobot(r); //add placemark of robot to the kml
				} 
				catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//}
				
				
				Point3D p = r.getPos();
				if (r.getId()==0) {
				StdDraw.picture(p.x(), p.y(), "robot4.png", 0.0012, 0.0008);}
				else if (r.getId()==1) {
				StdDraw.picture(p.x(), p.y(), "BobSfog.png", 0.0012, 0.0008);	}
				else {
					StdDraw.picture(p.x(), p.y(), "robot3.jpg", 0.0012, 0.0008);	
				}
			}
		}
		
		StdDraw.show();
	}
	
	
//******************** Private Help Functins***********************	
	
	
	//init  robot
	/*private void initRobots(game_service game)
	{
		
		RobotsList = gm.initRobots(game,gr);

	}
	
	*/
	// find the edge the fruit is on
	private void findFruitEdge(Fruit f) 
	{
		Collection<node_data> v = gr.getV();
		for(node_data n : v) 
		{
			Collection<edge_data> e = gr.getE(n.getKey());
			for(edge_data ed: e) 
			{
				Point3D p =gr.getNode(ed.getSrc()).getLocation();
				Point3D p2 =gr.getNode(ed.getDest()).getLocation();
				//check if the fruit is on the edge
				if(Math.abs((p.distance2D(p2)-(Math.abs((f.getPos().distance2D(p)))+(Math.abs((f.getPos().distance2D(p2))))))) <= EPSILON){
					int low=n.getKey();
					int high=ed.getDest();
					if(n.getKey()>ed.getDest()) 
					{
						low= ed.getDest();
						high= n.getKey();
					}
					if(f.getType()==1) 
					{
						edge_data FruitEdge = gr.getEdge(low, high);
						if(FruitEdge!= null) f.setEdge(FruitEdge);
					}
					//the reverse edge is the way to eat the fruit
					if(f.getType()==-1) 
					{
						edge_data edF = gr.getEdge(high,low);
						if(edF!= null)f.setEdge(edF);
					}
				}

			}
		}
	}


	public void playManual()
	{
		kml = new KML_Logger();
		try{

			this.GameCounter++;
			JFrame input = new JFrame();
			String num = JOptionPane.showInputDialog(input, "Enter Scenario Number:\n(Between 0-23) ");
			int scenario_num = Integer.parseInt(num);
			if(scenario_num>=0 && scenario_num<=23) 
			{
				 game = Game_Server.getServer(scenario_num);
				String g = game.getGraph();
				DGraph gg = new DGraph();
				gg.init(g);
				this.gr = gg;
				Iterator<String> fruit_iter = game.getFruits().iterator();
				//clear fruits collection if needed
				
				if(FruitsList!= null)
					FruitsList.clear();
				else
					FruitsList = new ArrayList<Fruit>();
				
				
				//set all fruits in their places
				while(fruit_iter.hasNext())
				{
					String sFruit = fruit_iter.next();
					Fruit f = new Fruit();
					f.initFruit(sFruit);
					FruitsList.add(f);
					findFruitEdge(f);
				}
				min_x=Integer.MAX_VALUE;
				max_x=Integer.MIN_VALUE;
				min_y=Integer.MAX_VALUE;
				max_y=Integer.MIN_VALUE;
				initGUI();
				paint(game);
				String gameString = game.toString();
				JSONObject obj = new JSONObject(gameString);
				JSONObject CurrGame = (JSONObject) obj.get("GameServer");
				int nomOfRobots = CurrGame.getInt("robots");
				int counter = 0;
				//	if the robots arrayList not empty clear
				if(RobotsList != null)RobotsList.clear();
				else{
					RobotsList = new ArrayList<Robot>();
				}

				//put the robots manually update the list and repaint using gui
				JOptionPane.showMessageDialog(null, "Please add " + nomOfRobots + " Robots.\nClick on the certain Vertex for adding Robot");
				while(counter < nomOfRobots)	
				{
					
//					String locrob1 = JOptionPane.showInputDialog(input, "Enter Vertex Number to locate robot:\nOptional: ");
//					int loc_rob1 = Integer.parseInt(locrob1);
					
					Point3D pos = new Point3D(MouseX, MouseY);
					
					Collection<node_data> nd = gr.getV();
					for (node_data node_data1 : nd) 
					{
						
						Point3D ndPos = node_data1.getLocation();
						if(pos.distance2D(ndPos) <= EPSILON)
						{
							game.addRobot(node_data1.getKey());
							Robot r = new Robot(counter, node_data1.getLocation(), 1, node_data1, gr);
							counter++;
							r.UpdateGraph(gg);
							RobotsList.add(r);
							paint(game);
							this.MouseX = 0;
							this.MouseY = 0;
							break;
						}
					}
				}
				StdDraw.pause(50);
				game.startGame();
				while(game.isRunning()) 
				{
					moveManualRobots(game);	
					
				}
		
				//return result
				String res = game.toString();
				JSONObject object = new JSONObject(res);
				JSONObject cgame = (JSONObject) object.get("GameServer");
				int points = cgame.getInt("grade");
				int moves = cgame.getInt("moves");
				
				JOptionPane.showMessageDialog(null, "Your points: " + points +"\nYour move: " + moves);	
				JFrame inputKML = new JFrame();
				String numKML = JOptionPane.showInputDialog(inputKML, "Enter 1 for create KML ");
				
				int kml_num = Integer.parseInt(numKML);
				if (kml_num==1)
				this.kml.CreatFile();
				}
			//the scenario is not exist throw error
			else
			{
				JOptionPane.showMessageDialog(null, "Error: iiligal scenario number!! ");
			}
		}
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	
	private void moveManualRobots(game_service game) throws InterruptedException 
	{
		

		
		List<String> log= game.move();
		
		if(log!= null) 
		{
			int destMove = nextVertexAuto(game);
			if(destMove!= -1) 
			{
				//Robot r = RobotsList.get(robotChoosen);
				if(r12!= null) //r
				{
					game.chooseNextEdge(r12.getId(), destMove); //r
					//System.out.println("r+ " + r.getId()+ " , dest "+ destMove);
					Thread.sleep(10); //this is thread!!
					game.move();
					//System.out.println("MOVE");
					//System.out.println(game.getFruits());
					//System.out.println(game.getRobots());
					paint(game);
				}
			}
		}
		paint(game);
	}
	

	
	
	private int nextVertexAuto(game_service game)
	{
	
	Point3D robPos = new Point3D(MouseX, MouseY);

	
	if(robots)	
	{
		for (Robot TempRob : RobotsList) 
		{
			Point3D p2= TempRob.getPos();
			
			//click on the robot?
			if(p2.distance2D(robPos)<= EPSILON) 
			{
				//search for edge for the robot
				r12= TempRob;
				robotChoosen = TempRob.getId();
				robots = false;
				this.MouseX =0;
				this.MouseY =0;
				System.out.println("Robot number:"+TempRob.getId()+ " has been Choosed!");
				break;
			}
		}
	}
	
	//search next node for movement from the node the robot is placed on
	else if (robots== false) 
	{	


		Collection<edge_data> eg = gr.getE(/*RobotsList.get(robotChoosen)*/r12.getVertex().getKey());
		//check if the pressed  dest is one of the edges of the current node
//		Point3D dest = new Point3D(MouseX, MouseY);
		String DestList="";
		ArrayList<Integer> destArray = new ArrayList<>();
		for (edge_data e : eg) 
		{	
			destArray.add(e.getDest());
			DestList=DestList+","+e.getDest();
		}
		
		JFrame input = new JFrame();
		String destStr = JOptionPane.showInputDialog(input, "Enter Destination vertex for Robot number:"+robotChoosen+" \n DON'T PREES X OR CANCEL!.\nThe Optional Vertices are:\n"+DestList);
		int ChoDest = Integer.parseInt(destStr);
		if(destArray.contains(ChoDest)) 
		{
//			Point3D temp = gr.getNode(e.getDest()).getLocation();
//			if(dest.distance2D(temp)<=0.0003)
//			{
//
//				System.out.println("FOUND EDGE");
				/*r= RobotsList.get(robotChoosen);*/
//				System.out.println("The dest that choose is:"+ e.getDest());
				
				r12.setEdge(gr.getEdge(r12.getVertex().getKey(), ChoDest));
				/*RobotsList.get(robotChoosen)*/r12.setVertex(gr.getNode(ChoDest));
				robots= true;
				this.MouseX = 0;
				this.MouseY =0;
//				return e.getDest();
				return ChoDest;
//			}
		//}
	  }
	}
	this.MouseX = 0;
	this.MouseY =0;
	return -1;
}
	
	

	
	
//	private int nextVertexAuto(game_service game)
//	{
//		String OptRob="";
//		JFrame input1 = new JFrame();
//		Robot r = null;
////		Point3D robPos = new Point3D(MouseX, MouseY);
////		if(robots)	
////		{
//			
//
//			
//			
//			for (Robot TempRob : RobotsList) 
//			{
//				OptRob=OptRob+" "+TempRob.getId();
////				Point3D p2= TempRob.getPos();
//				//click on the robot?
////				if(p2.distance2D(robPos)<= EPSILON) 
////				{
//					//search for edge for the robot
////					r= TempRob;
////					robotChoosen = TempRob.getId();
////					robots = false;
////					this.MouseX =0;
////					this.MouseY =0;
////					System.out.println("Robot number:"+TempRob.getId()+ " has been Choosed!");
////					break;
////				}
////			}
//		}
//			
//		
//			String numrob1 = JOptionPane.showInputDialog(input1, "Enter Robot Number:\nOptional: "+OptRob);
//			int robotChoosen = Integer.parseInt(numrob1);
//			if(RobotsList.get(robotChoosen)!=null)
//		for (Robot TempRob : RobotsList) 
//		{
//		if(TempRob.getId()==robotChoosen)
//		{
//			robots=false;
//			
//			break;
//		}
//		}
//		
//		//search next node for movement from the node the robot is placed on
//		if(!robots) 
//		{
//			Collection<edge_data> eg = gr.getE(RobotsList.get(robotChoosen).getVertex().getKey());
//			//check if the pressed  dest is one of the edges of the current node
////			Point3D dest = new Point3D(MouseX, MouseY);
//			String DestList="";
//			ArrayList<Integer> destArray = new ArrayList<>();
//			for (edge_data e : eg) 
//			{	
//				destArray.add(e.getDest());
//				DestList=DestList+","+e.getDest();
//			}
//			
//			JFrame input = new JFrame();
//			String destStr = JOptionPane.showInputDialog(input, "Enter Destination vertex for Robot number:"+robotChoosen+".\nThe Optional Vertices are:\n"+DestList);
//			int ChoDest = Integer.parseInt(destStr);
//			if(destArray.contains(ChoDest)) 
//			{
////				Point3D temp = gr.getNode(e.getDest()).getLocation();
////				if(dest.distance2D(temp)<=0.0003)
////				{
////
////					System.out.println("FOUND EDGE");
//					r= RobotsList.get(robotChoosen);
////					System.out.println("The dest that choose is:"+ e.getDest());
//					
//					RobotsList.get(robotChoosen).setEdge(gr.getEdge(r.getVertex().getKey(), ChoDest));
//					RobotsList.get(robotChoosen).setVertex(gr.getNode(ChoDest));
//					robots= true;
//					this.MouseX = 0;
//					this.MouseY =0;
////					return e.getDest();
//					return ChoDest;
////				}
//			//}
//		  }
//		}
//		this.MouseX = 0;
//		this.MouseY =0;
//		return -1;
//		}
//	private  int nextNode(graph g, int src) 
//	{
//		int ans = -1;
//		Collection<edge_data> ee = g.getE(src);
//		Iterator<edge_data> itr = ee.iterator();
//		int s = ee.size();
//		int r = (int)(Math.random()*s);
//		int i=0;
//		while(i<r) {itr.next();i++;}
//		ans = itr.next().getDest();
//		return ans;
//	}
	
	
	//function that used to pick the cordinate from user
	public void setPoint(double x, double y) 
	{
		this.MouseX = x;
		this.MouseY = y;
	}

	public void playAuto() 
	{
		kml = new KML_Logger();
		try
		{
			this.GameCounter++;
			JFrame input = new JFrame();
			String num = JOptionPane.showInputDialog( input, "Enter Scenario Number:\n(Between 0-23) ");
			int scenario_num = Integer.parseInt(num);
			if(scenario_num>=0 && scenario_num<=23) 
			{
				game = Game_Server.getServer(scenario_num);
				String g = game.getGraph();
				DGraph gg = new DGraph();
				gg.init(g);
				this.gr = gg;
				Iterator<String> fruit_iter = game.getFruits().iterator();
				//clear fruits collection if needed
				if(FruitsList!= null)
					FruitsList.clear();
				else
					FruitsList = new ArrayList<Fruit>();
				
				//set all fruits in their places
				while(fruit_iter.hasNext())
				{
					String sFruit = fruit_iter.next();
					Fruit f = new Fruit();
					f.initFruit(sFruit);
					FruitsList.add(f);
					findFruitEdge(f);
			//		System.out.println(f.getEdge().getSrc() + " " + f.getEdge().getDest());
				}
				min_x=Integer.MAX_VALUE;
				max_x=Integer.MIN_VALUE;
				min_y=Integer.MAX_VALUE;
				max_y=Integer.MIN_VALUE;
				initGUI();
				paint(game);
				String gameString = game.toString();
				JSONObject obj = new JSONObject(gameString);
				JSONObject CurrGame = (JSONObject) obj.get("GameServer");
				int nomOfRobots = CurrGame.getInt("robots");
				int check = 0;
				//	if the robots arrayList not empty clear
				if(RobotsList != null)RobotsList.clear();
				else
					RobotsList = new ArrayList<Robot>();
				

				//put the robots automatically , update the list and repaint using gui
				while(check < nomOfRobots)	
				{
					putRobot(game, check);
					check++;
				}
				paint(game);
				StdDraw.pause(50);
				

				
				game.startGame();
				
				

				
				
				
				while(game.isRunning()) 
					moveAutomaticallyRobots(game);	
				

				
				//return result
				//return result
				String res = game.toString();
				JSONObject object = new JSONObject(res);
				JSONObject cgame = (JSONObject) object.get("GameServer");
				int points = cgame.getInt("grade");
				int moves = cgame.getInt("moves");
				
				JOptionPane.showMessageDialog(null, "Computer points: " + points +"\nComputer move: " + moves);	
				JFrame inputKML = new JFrame();
				String numKML = JOptionPane.showInputDialog(inputKML, "Enter 1 for create KML ");
				
				int kml_num = Integer.parseInt(numKML);
				if (kml_num==1)
				this.kml.CreatFile();
			}

			//the scenario is not exist throw error
			else
				JOptionPane.showMessageDialog(null, "Error , the scenario you choose does not exist. ");
			
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void moveAutomaticallyRobots(game_service game) throws InterruptedException 
	{
		Fruit temp = new Fruit();
		Graph_Algo a = new Graph_Algo();
		a.init(gr);
		for (Robot  tempRob : RobotsList) {
			double dist = Double.MAX_VALUE;
			for (Fruit f : FruitsList) 
			{
				if(dist>(a.shortestPathDist(tempRob.getVertex().getKey(), f.getEdge().getSrc())+f.getEdge().getWeight())&&(!f.getVisited())) {
					dist =a.shortestPathDist(tempRob.getVertex().getKey(), f.getEdge().getSrc())+f.getEdge().getWeight();
					temp=f;
			}
			}
			robotChoosen = tempRob.getId();
		//	System.out.println("temp"+game.getFruits());
		//	System.out.println(tempRob.getVertex().getKey() + " " + temp.getEdge().getSrc());
			tempRob.setPath(a.shortestPath(tempRob.getVertex().getKey(), temp.getEdge().getSrc()));
			tempRob.getPath().remove(0); // remove your current place
			tempRob.getPath().add(gr.getNode(temp.getEdge().getDest())); //** try to delete this line
		//	System.out.println("value "+ temp.getValue());
			for (Fruit f : FruitsList) {
				if(f.getPos()==temp.getPos()) {
					f.setVisited(true);
				}
			}
			//this.fruits.get(temp.getValue()).setVisited(true);
		}

		List<String> log= game.move();
		if(log!= null) {
			for (int i = 0; i < RobotsList.size(); i++) {
				Robot roby = RobotsList.get(i);
				while(roby.getPath().size() != 0) {
					//System.out.println(roby.getId()+"roby id");
				//	System.out.println(rob.size());
					//System.out.println("path"+roby.getPath().get(0).getKey());
					game.chooseNextEdge(roby.getId(), roby.getPath().get(0).getKey());
					//paint(game);
					Thread.sleep(10); //this is thread!!
					game.move();
					

					roby.getPath().remove(0);
				}
			}	
		}
		paint(game);
	}


	private void putRobot(game_service game, int check) {
		for (Fruit f : FruitsList) {
			if(!f.getVisited()) {
				node_data n = gr.getNode(f.getEdge().getSrc());
				game.addRobot(n.getKey());
				Robot r = new Robot(check, n.getLocation(), 1 , n , gr);
				RobotsList.add(r);
				r.UpdateGraph(gr);
				f.setVisited(true);
				break;	
			}
		}
	}
	public game_service getGameService () {
		return this.game;
	}
	
	// get list of fruits
	public ArrayList<Fruit> getFruitsList () 
	{
		return this.FruitsList;
	}
	
	
	public ArrayList<Robot> getRobotsList () 
	{
		return this.RobotsList;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub

	}
	
	
}