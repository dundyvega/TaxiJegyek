
package hu.dundyvega.taxiexport.objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * @author dundyvega
 * Egy osztály, amiben tároljni tudjuk a kollégák adatait: név, cím, lat, lon, kel-nem kell taxi
 */
public class Staff  implements Comparable<Staff> {

	private int id;
	private String name;
	private String adress;
	private double lat;
	private double lon;
	private String taxi;

	
	
	/**
	 * Ez lehet, hogy nem lesz benne, és az eredeti koncepciot használom
	 */
	
	
	private double length;
	//dátum, mikori
	private String dt;
	
	public void setLength(double length) {
		this.length = length;
	}
	
	public double getLength() {
		return length;
	}
	
	/***
	 * 
	 * 
	 * Eddig
	 */
	
	
	
	/**
	 * Konstruktor, ami létrehozza az objektumot...
	 * @param id
	 * @param name
	 * @param adress
	 * @param lat
	 * @param lon
	 * @param taxi
	 */
	public Staff(int id, String name, String adress, double lat, double lon, String taxi) {
		
		setId(id);
		setName(name);
		setAdress(adress);
		setLat(lat);
		setLon(lon);
		setTaxi(taxi);
	}
	
	/**
	 * A nevet returnolja
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * Beállítja a nevet
	 * @param name
	 */
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * A címet returnolja
	 * @return
	 */
	public String getAdress() {
		return adress;
	}
	
	
	/**
	 * A címet állítja be
	 * @param adress
	 */
	public void setAdress(String adress) {
		this.adress = adress;
	}
	
	/**
	 * És a többi getter/setter függvény
	 * @return
	 */
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * Visszatéríti a légvonali távolságot két cím között
	 * @param staff
	 * @return
	 */
	
	/**
	 * Egy térképet használva kiszámolja az utat
	 * @param staff
	 * @return
	 */
	public double getDifranceApi(Staff staff) {
		
		
		if ((getLat() == staff.getLat()) && (getLon() == staff.getLon())) {
			return 0;
		} else {
			return getDistance(this.getLat(), this.getLon(), staff.getLat(), staff.getLon());
			
		}
		
	}
	
	
	
	private double  getDistance(final double lat1, final double lon1, final double lat2, final double lon2){

		double distance = 0;
		try {
           String s = "http://router.project-osrm.org/route/v1/driving/" + lon1 + "," + lat1 + ";" + lon2 + "," + lat2 + "?overview=false";
    
           String result = doHttpUrlConnectionAction(s);
           
           //System.out.println(result);
         
           JSONObject jSONObject = new JSONObject(result);
           JSONArray array = jSONObject.getJSONArray("routes");
           JSONObject routes = array.getJSONObject(0);
           JSONArray legs = routes.getJSONArray("legs");
           JSONObject steps = legs.getJSONObject(0);
           
  
           
           distance = steps.getDouble("distance");
           
           
		} catch (Exception ex) {System.out.println(ex);}
		
		return distance/1000;
		
            

	}
	
	
	
	 private  String doHttpUrlConnectionAction(String desiredUrl)
			  throws Exception
			  {
			    URL url = null;
			    BufferedReader reader = null;
			    StringBuilder stringBuilder;

			    try
			    {
			      // create the HttpURLConnection
			      url = new URL(desiredUrl);
			      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			      
			      // just want to do an HTTP GET here
			      connection.setRequestMethod("GET");
			      
			      // uncomment this if you want to write output to this url
			      //connection.setDoOutput(true);
			      
			      // give it 15 seconds to respond
			      connection.setReadTimeout(15*1000);
			      connection.connect();

			      // read the output from the server
			      reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			      stringBuilder = new StringBuilder();

			      String line = null;
			      while ((line = reader.readLine()) != null)
			      {
			        stringBuilder.append(line + "\n");
			      }
			      return stringBuilder.toString();
			    }
			    catch (Exception e)
			    {
			      e.printStackTrace();
			      throw e;
			    }
			    finally
			    {
			      // close the reader; this can throw an exception too, so
			      // wrap it in another try/catch block.
			      if (reader != null)
			      {
			        try
			        {
			          reader.close();
			        }
			        catch (IOException ioe)
			        {
			          ioe.printStackTrace();
			        }
			      }
			    }
			  }
	 
	 
	 public double getDifrance(Staff staff, int szamitas) {
		 
		 if (szamitas == 1) { //gömbi
			 
			 return getDifrance(staff);
			 
		 } else { //api
			 
			 return getDifranceApi(staff);
		 }
	 }
	
	
	public double getDifrance(Staff staff) {
		
		
		if ((getLat() == staff.getLat()) && (getLon() == staff.getLon())) {
			return 0;
		}
		else {
			
			/*kimentjük a koordinátákat változóban, mivel ezzel időt sporolunk, nem kell mindig lekérnünk*/
			
			double lat1 = this.getLat();
			double lon1 = this.getLon();
			double lat2 = staff.getLat();
			double lon2 = staff.getLon();
			
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			dist = dist * 1.609344;
			
			return (dist);
		}
		}
	
	
	public String toString() {
		return getName() + "(" + getAdress() + ")";
	}

	public String getTaxi() {
		return taxi;
	}

	public void setTaxi(String taxi) {
		this.taxi = taxi;
	}


   /**
    * Visszatéríti azt, hogy mikorra kérte a taxit
    * @return
    */
  	public String getDt() {
		return dt;
	}

  	/**
  	 * Beállítsa a dátumot (az időt) az export fájlból kinyert információ szerint
  	 * @param dt
  	 */
	public void setDt(String dt) {
		this.dt = dt;
	}

	@Override
	public int compareTo(Staff o) {
		// TODO Auto-generated method stub
		Staff origo = new Staff(-1, "UPC János", "str. Brancusi 147", 46.760893, 23.613569, "nem");
		
		Double db1 = origo.getDifrance(this);
		Double db2 = origo.getDifrance(o);
		
		return db1.compareTo(db2);
	}


	
}
