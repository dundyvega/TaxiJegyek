
package hu.dundyvega.taxiexport.objects;

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
