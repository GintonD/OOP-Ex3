package dataStructure;

import org.json.JSONObject;

import utils.Point3D;

public class Fruit 
{
//	enum Type 
//	{ 
//	    Banana, Apple;
//	} 
	
	private double value;
	private int type;
	private Point3D pos;
	private boolean visited;
	private edge_data FruitEdge;
	
	
	public Fruit()
	{

			
	}
	
	public Fruit(double val, int ty,Point3D p)
	{
		this.value=val;

		this.type=ty;
		
		this.pos=p;
			
	}

	public Point3D getPos() 
	{
		// TODO Auto-generated method stub
		return this.pos;
	}

	public int getType() 
	{
		return this.type; // check if its right
	}

	public void initFruit(String sFruit) 
	{
		if(!sFruit.isEmpty()){
			try{
				JSONObject Fobj = new JSONObject(sFruit);
				JSONObject fruit = (JSONObject) Fobj.get("Fruit");
				int value = fruit.getInt("value");
				this.value = value;
				int type = fruit.getInt("type");
				this.type = type;
				String pos = fruit.getString("pos");
				String[] point = pos.split(",");
				double x = Double.parseDouble(point[0]);
				double y = Double.parseDouble(point[1]);
				double z = Double.parseDouble(point[2]);
				this.pos = new Point3D(x, y, z);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		
	}

	public void setEdge(edge_data fruitEdge) 
	{
		this.FruitEdge=fruitEdge;
		
	}

	public edge_data getEdge() 
	{
		// TODO Auto-generated method stub
		return this.FruitEdge;
	}

	public boolean getVisited() 
	{
		// TODO Auto-generated method stub
		return this.visited;
	}

	public void setVisited(boolean b) 
	{
		this.visited=b;
		
	}

	



}
