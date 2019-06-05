
package hu.dundyvega.taxiexport.objects;

/**
 * 
 * @author dundyvega
 * Egy osztály, amiben tároljni tudjuk a kollégák adatait: név, cím, lat, lon
 */
public class Staff  {

	private int id;
	private String name;
	private String adress;
	private double lat;
	private double lon;

	
	
	/**
	 * Ez lehet, hogy nem lesz benne, és az eredeti koncepciot használom
	 */
	
	
	private double length;
	
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
	 */
	public Staff(int id, String name, String adress, double lat, double lon) {
		
		setId(id);
		setName(name);
		setAdress(adress);
		setLat(lat);
		setLon(lon);
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


	
}
