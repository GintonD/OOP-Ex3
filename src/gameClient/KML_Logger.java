package gameClient;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dataStructure.Fruit;
import dataStructure.Robot;
import dataStructure.graph;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.IconStyle;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.TimeSpan;
import utils.StdDraw;

public class KML_Logger 
{
	 // MyGameGUI myg;
	  Kml k;
	  Document doc;
	  int i;
	  int l;
	
	public KML_Logger() 
	{
		  k = new Kml ();
		  doc = k.createAndSetDocument();
		  i=0;
		  l=0;
		  
	}


	public void SetFruit(Fruit f) throws ParseException, InterruptedException
	{
		Placemark placmark = doc.createAndAddPlacemark();
	    Icon ff = new Icon();

	    ff.setHref("https://img.favpng.com/0/3/15/super-mario-odyssey-super-mario-bros-luigi-mushroom-png-favpng-qEkUCTh1rLw3PeUCFxR7x3YKb.jpg");
	    ff.setViewBoundScale(1);
	    ff.setViewRefreshTime(1);
	    ff.withRefreshInterval(1);
	    IconStyle pp = new IconStyle();
	    pp.setScale(1);
	    pp.setHeading(1);
	    pp.setColor("ff007db3");
	    pp.setIcon(ff);
	    placmark.createAndAddStyle().setIconStyle(pp);
	    placmark.withDescription("MAC: " + "\nType: fruit").withOpen(Boolean.TRUE).createAndSetPoint().addToCoordinates(f.getPos().x(), f.getPos().y());
	    String time1 = MillisToString(StringToMillis(TimeNow()) + i * 1000);
	    String time2 = MillisToString(StringToMillis(TimeNow()) + (i + 1) * 1000);
	    String[] aa = time1.split(" ");
	    time1 = aa[0] + "T" + aa[1] + "Z";
	    String[] bb = time2.split(" ");
	    time2 = bb[0] + "T" + bb[1] + "Z";
	    TimeSpan b = placmark.createAndSetTimeSpan();
	    b.setBegin(time1);
	    b.setEnd(time2);
		
	}


	public void SetRobot(Robot r) throws ParseException, InterruptedException
	{
		Placemark plmark = doc.createAndAddPlacemark();
	    Icon ff = new Icon();

	    ff.setHref("https://www.freepngimg.com/download/mario_bros/92548-mario-material-super-toy-bros-png-file-hd.png");
	    ff.setViewBoundScale(1);
	    ff.setViewRefreshTime(1);
	    ff.withRefreshInterval(1);
	    IconStyle pp = new IconStyle();
	    pp.setScale(1);
	    pp.setHeading(1);
	    pp.setColor("ff007db3");
	    pp.setIcon(ff);
	    plmark.createAndAddStyle().setIconStyle(pp);
	    plmark.withDescription("Mac: " + "\nType: Robot").withOpen(Boolean.TRUE).createAndSetPoint().addToCoordinates(r.getPos().x(), r.getPos().y());
	    l++;
	    String time1 = MillisToString(StringToMillis(TimeNow()) + i * 1000);
	    String time2 = MillisToString(StringToMillis(TimeNow()) + (i + 1) * 1000);
	    String[] aa = time1.split(" ");
	    time1 = aa[0] + "T" + aa[1] + "Z";
	    String[] bb = time2.split(" ");
	    time2 = bb[0] + "T" + bb[1] + "Z";
	    TimeSpan a = plmark.createAndSetTimeSpan();
	    a.setBegin(time1);
	    a.setEnd(time2);
		
	}


	public void CreatFile() 
	{
		try {
		    k.marshal(new File("KmlRun.kml"));
		    System.out.println("create");
			}

		catch (Exception e)
		{
		    System.out.println("Fail create");
		}
		
	}
	
	

 private String MillisToString(Long millis)
{
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(new Date(millis));
}


 private long StringToMillis(String TimeAsString) throws ParseException
{
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    Date date = format.parse(TimeAsString.toString());
    long millis = date.getTime();
    return millis;
}

private String TimeNow()
{
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
}



	}
