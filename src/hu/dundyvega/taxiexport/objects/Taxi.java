package hu.dundyvega.taxiexport.objects;

import java.util.ArrayList;
import java.util.Collections;

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
	public double fullLengthOfTheRoad(int szamitas) {
		
		double dist = origo.getDifrance(walkers.get(0), szamitas);
		
		for (int i = 1; i < walkers.size(); ++i) {
			dist += walkers.get(i-1).getDifrance(walkers.get(i), szamitas);
		}
		
		return dist;
	}
	
	
	/**
	 * Kiszámolja az út hosszát a k. elem nélkül, k != 0
	 * @param k
	 * @return
	 */
	public double fullLengthOfTheRoadMinusK(int k, int szamitas) {
		
		double dist = origo.getDifrance(walkers.get(0));
		
		for (int i = 1; i < walkers.size(); ++i) {
			
			if (i != k && k != i - 1) {
				dist += walkers.get(i-1).getDifrance(walkers.get(i), szamitas);
			} else {
				//az origot nem kell beszámolnunk, mivel 0-tól kezdjük a számolást
				if (k == i - 1) {
					dist += walkers.get(i-2).getDifrance(walkers.get(i), szamitas);
				}
			}
		}
		
		return dist;
	}
	
	
	/**
	 * Ha f beülne a taxiba, akkor mennyi lenne a teljes út
	 * @param f
	 * @return
	 */
	public double fullLengthIfPlus(Staff f, int szamitas) {
		
		return fullLengthOfTheRoad(szamitas) + walkers.get(walkers.size() - 1).getDifrance(f, szamitas);
	}
	
	/**
	 * Visszatéríti, hogy mennyi volna a távolság, ha beraknánk egy f elemet az optimális pozicióba
	 * @param f
	 * @param szamiatas
	 * @return
	 */
	public double fullLengthPlusOptimalization(Staff f, int szamitas) {
		
	
		ArrayList<Staff> staffs = new ArrayList<Staff>();
		
		//létrehozunk egy másolatot a taxiba ülőkről
		for (int i = 0; i < walkers.size(); ++i) {
			staffs.add(walkers.get(i));
		}
		
		//hozzáadjuk az f elemet
		walkers.add(f);
		
		//sorrendbe rendezzük origotól való távolságra
		
		Collections.sort(walkers);
		
		double osszeg = this.fullLengthOfTheRoad(szamitas);
		
		//visszaállítjuk a listát
		
		walkers = staffs;
		
		
		return osszeg;
		
	}
	
	
	
	/**
	 * berakja az elemet egy optimális helyre
	 * @param staff
	 */
	public void addStaffOptimalization(Staff staff) {
		
		walkers.add(staff);
		
	
		Collections.sort(walkers);
	}
	
	
	/**
	 * Van-e még hely a taxiban?
	 * @return
	 */
	public boolean notFullWalkers() {
		

		return walkers.size() < 4;
	}
	
	public double fullRoadElementSwapp(int i, Staff f, int szamitas) {
		
		if (i >= walkers.size()) {

			return 10000000;
			
		} else {
			Staff csere = walkers.get(i);
			walkers.set(i,f);
			double road = this.fullLengthOfTheRoad(szamitas);
			
			walkers.set(i, csere);
			return road;
		}
		
		
	}

	
	
}
