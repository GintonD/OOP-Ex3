package dataStructure;

import java.awt.Component;
import java.util.List;

import org.json.JSONObject;

import utils.Point3D;

public class Robot 
{
	private int id;
	private double value;
	private int src;
	private int dest;
	private double speed; //maybe delete
	private Point3D pos;
	
	private graph temp_gr;
	private edge_data temp_edge;
	private node_data temp_vertex;
	private List<node_data> temp_path;
	
	
	public Robot(int id1,double val1, int sr,int dst,double spd,Point3D loc)
	{
		this.id = id1;
		this.value=val1;
		this.src=sr;
		this.dest=dst;
		this.speed=spd;
		this.pos=loc;
		
	}
	public Robot(int id1,double val1, int sr,int dst,Point3D loc)
	{
		this.id = id1;
		this.value=val1;
		this.src=sr;
		this.dest=dst;
		//.speed=spd;
		this.pos=loc;
		
	}
	public Robot() 
	{
		
		// TODO Auto-generated constructor stub
	}
	public Robot(int counter, Point3D location, int i, node_data node1, graph gr) 
	{
		this.id=counter;
		this.pos=location;
		this.speed=i;
		this.temp_vertex=node1;
		this.temp_gr=gr;
			
	}
	

	public Point3D getPos() 
	{
		// TODO Auto-generated method stub
		return this.pos;
	}
	public int getId() 
	{
		// TODO Auto-generated method stub
		return this.id;
	}
	public void initRobot(String Rstring) 
	{
		if(!Rstring.isEmpty())
		{
			try
			{
				JSONObject obj = new JSONObject(Rstring);
				JSONObject robot = (JSONObject) obj.get("Robot");
				String pos = robot.getString("pos");
				String[] arr = pos.split(",");
				double x = Double.parseDouble(arr[0]);
				double y = Double.parseDouble(arr[1]);
				double z = Double.parseDouble(arr[2]);
				this.pos = new Point3D(x, y, z);
				int id = robot.getInt("id");
				this.id = id;
				int speed = robot.getInt("speed");
				this.speed = speed;

				if(this.temp_gr != null)
				{
					int src = robot.getInt("src");
					this.temp_vertex  = this.temp_gr.getNode(src);
				}

			}
			catch (Exception e) 
			{
				System.out.println("fail to init robot");
			}
		}
	}
	public void UpdateGraph(graph gr) 
	{
		this.temp_gr=gr;
		
	}
	public void setEdge(edge_data e) 
	{
		this.temp_edge=e;
		
	}
	public node_data getVertex() 
	{
	
		return this.temp_vertex;
	}
	public void setVertex(node_data node) 
	{
		this.temp_vertex= node;
		
	}
	public void setPath(List<node_data> nextPath)
	{
		this.temp_path=nextPath;
		
	}
	public List<node_data> getPath() 
	{
		// TODO Auto-generated method stub
		return this.temp_path;
	}



		
	}




