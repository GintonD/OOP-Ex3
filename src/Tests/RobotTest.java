package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dataStructure.Robot;
import utils.Point3D;

class RobotTest {
	Robot p0;

    @Test
    public void getSpeed() {
        Robot p1 = new Robot();
        Robot p2 = new Robot();
        Robot p3 = new Robot();
        p1.setSpeed(1);
        p2.setSpeed(2);
        p3.setSpeed(3);
        assertEquals(3,p3.getSpeed());
        assertEquals(2,p2.getSpeed());
        assertEquals(1,p1.getSpeed());
    }

    @Test
    public void setSpeed() {
        Robot p1 = new Robot();
        Robot p2 = new Robot();
        Robot p3 = new Robot();
        p1.setSpeed(1);
        p2.setSpeed(2);
        p3.setSpeed(3);
        assertEquals(3,p3.getSpeed());
        assertEquals(2,p2.getSpeed());
        assertEquals(1,p1.getSpeed());
    }

    @Test
    public void getDest() {
        Robot p1 = new Robot();
        Robot p2 = new Robot();
        Robot p3 = new Robot();
        p1.setDest(1);
        p2.setDest(2);
        p3.setDest(3);
        assertEquals(3,p3.getDest());
        assertEquals(2,p2.getDest());
        assertEquals(1,p1.getDest());
    }

    @Test
    public void setDest() {
        Robot p1 = new Robot();
        Robot p2 = new Robot();
        Robot p3 = new Robot();
        p1.setDest(1);
        p2.setDest(2);
        p3.setDest(3);
        assertEquals(3,p3.getDest());
        assertEquals(2,p2.getDest());
        assertEquals(1,p1.getDest());
    }

    @Test
    public void getSrc() {
        Robot p1 = new Robot();
        Robot p2 = new Robot();
        Robot p3 = new Robot();
        p1.setSrc(1);
        p2.setSrc(2);
        p3.setSrc(3);
        assertEquals(3,p3.getSrc());
        assertEquals(2,p2.getSrc());
        assertEquals(1,p1.getSrc());
    }

    @Test
    public void setSrc() {
        Robot p1 = new Robot();
        Robot p2 = new Robot();
        Robot p3 = new Robot();
        p1.setSrc(1);
        p2.setSrc(2);
        p3.setSrc(3);
        assertEquals(3,p3.getSrc());
        assertEquals(2,p2.getSrc());
        assertEquals(1,p1.getSrc());
    }

    @Test
    public void getLocation() {
        Robot p1 = new Robot();
        Robot p2 = new Robot();
        Robot p3 = new Robot();
        Point3D PD1 = new Point3D(1,1);
        Point3D PD2 = new Point3D(2,2);
        Point3D PD3 = new Point3D(3,3);
        p1.setPos(PD1);
        p2.setPos(PD2);
        p3.setPos(PD3);
        assertEquals(PD1,p1.getPos());
        assertEquals(PD2,p2.getPos());
        assertEquals(PD3,p3.getPos());
    }

    @Test
    public void setLocation() {
        Robot p1 = new Robot();
        Robot p2 = new Robot();
        Robot p3 = new Robot();
        Point3D PD1 = new Point3D(1,1);
        Point3D PD2 = new Point3D(2,2);
        Point3D PD3 = new Point3D(3,3);
        p1.setPos(PD1);
        p2.setPos(PD2);
        p3.setPos(PD3);
        assertEquals(PD1,p1.getPos());
        assertEquals(PD2,p2.getPos());
        assertEquals(PD3,p3.getPos());
    }

   


}
