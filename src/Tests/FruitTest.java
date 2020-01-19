package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.Fruit;
import dataStructure.edge;
import dataStructure.edge_data;
import utils.Point3D;

class FruitTest {

	 @Test
	    public void getLocation() {
	        Fruit f1 = new Fruit();
	        Fruit f2 = new Fruit();
	        Fruit f3 = new Fruit();
	        Fruit f4 = new Fruit();
	        Fruit f5 = new Fruit();
	        Point3D p1 = new Point3D(1,1,1);
	        Point3D p2 = new Point3D(2,2,2);
	        Point3D p3 = new Point3D(3,3,3);
	        Point3D p4 = new Point3D(4,4,4);
	        Point3D p5 = new Point3D(5,5,5);
	        f1.SetPos(p1);
	        f2.SetPos(p2);
	        f3.SetPos(p3);
	        f4.SetPos(p4);
	        f5.SetPos(p5);
	        assertEquals(p1,f1.getPos());
	        assertEquals(p2,f2.getPos());
	        assertEquals(p3,f3.getPos());
	        assertEquals(p4,f4.getPos());
	        assertEquals(p5,f5.getPos());
	    }

	    @Test
	    public void setLocation() {
	        Fruit f1 = new Fruit();
	        Fruit f2 = new Fruit();
	        Fruit f3 = new Fruit();
	        Fruit f4 = new Fruit();
	        Fruit f5 = new Fruit();
	        Point3D p1 = new Point3D(1,1,1);
	        Point3D p2 = new Point3D(2,2,2);
	        Point3D p3 = new Point3D(3,3,3);
	        Point3D p4 = new Point3D(4,4,4);
	        Point3D p5 = new Point3D(5,5,5);
	        f1.SetPos(p1);
	        f2.SetPos(p2);
	        f3.SetPos(p3);
	        f4.SetPos(p4);
	        f5.SetPos(p5);
	        assertEquals(p1,f1.getPos());
	        assertEquals(p2,f2.getPos());
	        assertEquals(p3,f3.getPos());
	        assertEquals(p4,f4.getPos());
	        assertEquals(p5,f5.getPos());
	    }

	    
	    
	    
	    
	    @Test
	    public void getTag() {
	        Fruit f1 = new Fruit();
	        Fruit f2 = new Fruit();
	        Fruit f3 = new Fruit();
	        Fruit f4 = new Fruit();
	        Fruit f5 = new Fruit();
	        f1.setVisited(true);
	        f2.setVisited(true);
	        f3.setVisited(false);
	        f4.setVisited(true);
	        f5.setVisited(false);
	        assertEquals(true,f1.getVisited());
	        assertEquals(true,f2.getVisited());
	        assertEquals(false,f3.getVisited());
	        assertEquals(true,f4.getVisited());
	        assertEquals(false,f5.getVisited());

	    }


	    @Test
	    void getEdge() {
	        game_service game = Game_Server.getServer(0); // you have [0,23] games
	        String g = game.getGraph();
	        DGraph gg = new DGraph();
	        gg.init(g);

	        Fruit fruits = new Fruit(3,1,new Point3D(0,0));
	        fruits.setEdge(new edge(1,2,3.5));
	        assertNotNull(fruits.getEdge());
	        //System.out.println(fruits.getEdge(0).getSrc());
	        int expected =1;
	        assertEquals(expected,fruits.getEdge().getSrc());
	        game.stopGame();
	    }

}
