package dataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import utils.Point3D;

public class DGraph implements graph,Serializable
{

private static final long serialVersionUID = 1L;
public HashMap<Integer, HashMap<Integer,edge_data/*edge*/>> Edges; 	
public HashMap<Integer, node_data/*vertex*/> Vertices ;  	
private int size_edges;
private static int MC = 0;
//private GraphListener listener;

/**
 * Constructor
 */
public DGraph()
{
	this.Edges=	new HashMap<Integer, HashMap<Integer,edge_data>>();
	this.Vertices= new HashMap<Integer, node_data>();
	this.size_edges=0;
	dataStructure.vertex.idBuilder=0;
}




/**
 * return the node_data by the node_id,
 * @param key - the node_id
 * @return the node_data by the node_id, null if none.
 */
@Override
public node_data getNode(int key) 
{

	return this.Vertices.get(key);

}




/**
 * return the data of the edge (src,dest), null if none.
 * Note: this method should run in O(1) time.
 * @param src
 * @param dest
 * @return
 */
@Override
public edge_data getEdge(int src, int dest) 
{
	
	return ( (this.Edges.get(src).get(dest)));
	
}




/**
 * add a new node to the graph with the given node_data.
 * Note: this method should run in O(1) time.
 * @param n
 */
@Override
public void addNode(node_data n) 
{
	this.Vertices.put(n.getKey(),  n);
	MC++; 
	this.Edges.put(n.getKey(), new HashMap<Integer, edge_data>());  
	
}


/**
 * Connect an edge with weight w between node src to node dest.
 * * Note: this method should run in O(1) time.
 * @param src - the source of the edge.
 * @param dest - the destination of the edge.
 * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
 */
@Override
public void connect(int src, int dest, double w)  
{
	if(this.Vertices.get(src)!=null && Vertices.get(dest)!=null) 
	{
		edge NewEdge = new edge(src,dest,w);
		
		if (this.Edges.get(src) == null)	//create the edge and then connect
		{
			this.Edges.put(src, new HashMap<Integer,edge_data>());
			size_edges++;
			this.Edges.get(src).put(dest, NewEdge);
			MC++;
		}
		
		else // just connect
		{
			
			this.Edges.get(src).put(dest, NewEdge);
			size_edges++;
			MC++;
			
		}

	}

}




/**
 * This method return a pointer (shallow copy) for the
 * collection representing all the nodes in the graph. 
 * Note: this method should run in O(1) time.
 * @return Collection<node_data>
 */
@Override
public Collection<node_data> getV() 
{
	
	//if (this.Vertices.isEmpty())
		//return null; 
	//else
		return this.Vertices.values();
}




/**
 * This method return a pointer (shallow copy) for the
 * collection representing all the edges getting out of 
 * the given node (all the edges starting (source) at the given node). 
 * Note: this method should run in O(1) time.
 * @return Collection<edge_data>
 */
@Override
public Collection<edge_data> getE(int node_id) //one more!!!!!
{
	return this.Edges.get(node_id).values();
}




/**
 * Delete the node (with the given ID) from the graph -
 * and removes all edges which starts or ends at this node.
 * This method should run in O(n), |V|=n, as all the edges should be removed.
 * @return the data of the removed node (null if none). 
 * @param key
 */
@Override
public node_data removeNode(int key) 
{
	if (this.Vertices.get(key)==null) // the node is not exist
		return null;
	
	ArrayList<Integer> ForDelete = new ArrayList<Integer>();
	node_data VerAftDel = /*(vertex)*/Vertices.get(key);
	
	
	//remove all edges going into key-node.
	this.Edges.forEach((ky, edg) -> 
	{
		if (edg.get(key)!=null) 
		{
			edg.remove(key);
			size_edges--;
			MC++;
			
			if (edg.isEmpty()) //collect the edge with no dst
				ForDelete.add(ky);
		}
	});
	for (int ky : ForDelete) //delete them
		this.Edges.remove(ky);
	
	
	
	//remove all edges coming out of key-node.
	size_edges =size_edges - this.Edges.get(key).size();
	this.Edges.remove(key);
	
	
	//remove the key-node.
	this.Vertices.remove(key);
	MC++;

	return VerAftDel;
	
}




/**
 * Delete the edge from the graph, 
 * Note: this method should run in O(1) time.
 * @param src
 * @param dest
 * @return the data of the removed edge (null if none).
 */
@Override
public edge_data removeEdge(int src, int dest) 
{
	MC++;
	size_edges--;
	return Edges.get(src).remove(dest);
}




/** return the number of vertices (nodes) in the graph.
 * Note: this method should run in O(1) time. 
 * @return
 */
@Override
public int nodeSize() 
{
	return this.Vertices.size();
}





/** 
 * return the number of edges (assume directional graph).
 * Note: this method should run in O(1) time.
 * @return
 */
@Override
public int edgeSize() 
{
	return this.size_edges;
}



/**
 * return the Mode Count - for testing changes in the graph.
 * @return
 */
@Override
public int getMC() 
{
	return MC;
}


/** 
 * Compute a deep copy of this graph.
 * @return copy of the current graph
 */
public graph copy() 
{
	DGraph g = new DGraph();
	Collection<node_data> v = Vertices.values();
	Iterator<node_data> itr = v.iterator();
	while(itr.hasNext()) 
	{
		node_data n = itr.next();
		g.addNode(n);
	}
	Collection<HashMap<Integer,edge_data>> e1 = Edges.values();
	Iterator<HashMap<Integer,edge_data>> itr1 = e1.iterator();
	while(itr1.hasNext()) 
	{
		HashMap<Integer, edge_data> h = itr1.next();
		Collection<edge_data> e2 = h.values();
		Iterator<edge_data> itr2 = e2.iterator();
		while(itr2.hasNext()) 
		{
			edge_data edge = itr2.next();
			g.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
		}
	}
	g.size_edges = this.size_edges;
	g.MC = this.MC;
	return g;
}



/**
 * Check if the vertex is exist
 * @param k
 * @return true or false
 */
public boolean VertexIsExist(int k) 
{
	if (this.Vertices.containsKey(k))
		return true;
	return false;
}


/**
 * Check if the Edges is exist
 * @param s - source
 * @param d - derstination
 * @return true or false.
 */
public boolean EdgeIsExist(int s, int d) 
{
	if (this.Edges.containsKey(s)) 
	{
		if (this.Edges.get(s).containsKey(d))
			return true; 
	}
	return false;
	}




public void init(String g) 
{
	// TODO Auto-generated method stub
try {
		JSONObject Jobj = new JSONObject(g);
		JSONArray JEdges = Jobj.getJSONArray("Edges");
		JSONArray JVerticies = Jobj.getJSONArray("Nodes");
		
		

		for (int i = 0; i < JVerticies.length(); i++) 
		{
			JSONObject jvertex= (JSONObject) JVerticies.get(i);
			String location = (String) jvertex.getString("pos");
			String[] points = location.split(",");
			double x = Double.parseDouble(points[0]);
			double y = Double.parseDouble(points[1]);	
			double z = Double.parseDouble(points[2]);
			
			int id = jvertex.getInt("id");
			Point3D p = new Point3D(x,y,z);
			node_data n = new vertex(id, p);
			
			this.addNode(n);
		}
		
		for (int i = 0; i < JEdges.length(); i++) 
		{
			JSONObject edgeE= (JSONObject) JEdges.get(i);
			int src = edgeE.getInt("src");
			int dest = edgeE.getInt("dest");
			double weight = edgeE.getDouble("w");
			this.connect(src, dest, weight);
		}
	}
	catch (Exception e) 
	{
		e.printStackTrace();
		
	} 
}


//public void addListener(GraphListener listener){
//	this.listener = listener;
//}
//
//public void updateListener(){
//	if(listener != null)
//		listener.graphUpdater();
//}

}
