package hu.dundyvega.taxiexport.objects;

import java.util.ArrayList;

/**
 * 
 * @author dundyvega
 * Egy taxi, amiben kollégák vannak, az egyik paramétere az origo (a UPC koordinátái), a másik a taxiban ülő kollégák
 */
public class Taxi {
	
	private ArrayList<Staff> walkers;
	final private Staff origo = new Staff(-1, "UPC János", "str. Brancusi 147", 46.760893, 23.613569, "nem");
	
	public Taxi() {
		
		walkers = new ArrayList<Staff>();
	}
	
	/**
	 * Visszatéríti az Origo-t, amivel különféle összehasonlításokat lehet majd végezni
	 * @return
	 */
	public Staff getOrigo() {
		
		return origo;
	}
	
	/**
	 * Visszatéríti a taxiban ülő kollégákat
	 * @return
	 */
	public ArrayList<Staff> getTaxi() {
		return walkers;
	}
	
	/**
	 * Hozzáadunk egy utazót a taxihoz
	 * @param staff
	 */
	public void addStaff(Staff staff) {
		walkers.add(staff);
	}
	
	
	/**
	 * Mennyit tenne meg a taxi a benne ülő személyekkel, ha repülhetne
	 * @return
	 */
	public double fullLengthOfTheRoad() {
		
		double dist = origo.getDifrance(walkers.get(0));
		
		for (int i = 1; i < walkers.size(); ++i) {
			dist += walkers.get(i-1).getDifrance(walkers.get(i));
		}
		
		return dist;
	}
	
	
	/**
	 * Ha f beülne a taxiba, akkor mennyi lenne a teljes út
	 * @param f
	 * @return
	 */
	public double fullLengthIfPlus(Staff f) {
		
		return fullLengthOfTheRoad() + walkers.get(walkers.size() - 1).getDifrance(f);
	}
	
	
	/**
	 * Van-e még hely a taxiban?
	 * @return
	 */
	public boolean notFullWalkers() {
		

		return walkers.size() < 4;
	}
	
	
}
