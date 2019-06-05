import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import hu.dundyvega.taxiexport.managment.FileOperator;
import hu.dundyvega.taxiexport.objects.Graf;
import hu.dundyvega.taxiexport.objects.Staff;
import hu.dundyvega.taxiexport.objects.Taxi;
import jdk.incubator.http.*;

import java.lang.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;

class DistanceCalculator
{
	public static void main (String[] args) throws java.lang.Exception
	{
		//System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, "M") + " Miles\n");
		//System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, "K") + " Kilometers\n");
		
		//System.out.println(String.format("%.2f", distanceAireCordinates(46.760893, 23.613462, 46.759571, 23.546319)) + " Kilometers\n");
		
		//System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, "N") + " Nautical Miles\n");
		
		//System.out.println(getDistance(46.760893, 23.613462, 46.759571, 23.546319));
		FileOperator.createXML();
		
		
		FileOperator.newItem("Medve Alíz", "Óperenciás tenger", 46.765035, 23.528973);
		FileOperator.newItem("Veres Attila", "Fsega", 46.773357, 23.620956);
		FileOperator.newItem("Kurva", "maraest..", 46.788587, 23.616681);
		
		
		//FileOperator.modifyStaffInformationsOnXML(2, "Tasnad 29/22", 23.46786, -98.53586);
		
		//ArrayList<Staff> staffs = FileOperator.allWalkers();
		//System.out.println(staffs.size());
		rendezes();
		
		
		
	}


	
	
	
	public static void rendezes() {
		
		
		
		//egy lista, ebben vannak az emberek, ezt abból a listából mentem ki, amit a csopvezek beállítottak 11-re
		ArrayList<Staff> staffs = FileOperator.allWalkers();
		
		Graf gr = new Graf();
		Staff origo = new Staff(-1, "UPC János", "str. Brancusi 147", 46.760893, 23.613569);
		gr.appendChild(origo);

		for (int i = 0; i < staffs.size(); ++i) {
			gr.appendChild(staffs.get(i));
		}
		
		staffs = new ArrayList<Staff>();
		gr.tombosit(staffs);
		
		staffs.remove(0);
		
		ArrayList<Taxi> taxik = new ArrayList<Taxi>();
	
		
		
	
		
		
		System.out.println("");

		while (staffs.size() > 0) { 
			
			// a lista első emberét beleültetjük az üres taxiba, és kitörüljük azok közül, akik még nem találták meg ahelyüket
			
			Taxi taxi = new Taxi();
			taxi.addStaff(staffs.get(0));
			
			
			staffs.remove(0);
			
			for (int i = 0; i < staffs.size(); ++i) {
				
				
				/*Ha beülne a taxiban rövidebb lenne az út mintha két taxi menne az origotól?
				 * + van-e hely a taxiban még egy embernek?*/
				
				if (taxi.fullLengthIfPlus(staffs.get(i)) < taxi.fullLengthOfTheRoad() + 
						origo.getDifrance(staffs.get(i)) && taxi.notFullWalkers()) {
					
					taxi.addStaff(staffs.get(i));
					
				
					
					
				} 
				
			}
	
	
			
			
			// kiszedjük azokat a listából, akiknek már van taxijuk
			for (int j = 0; j < taxi.getTaxi().size(); ++j) {
				staffs.remove(taxi.getTaxi().get(j));
			}
			//megtelt taxit útnak eresszük
			taxik.add(taxi);
			
		}
	
		System.out.println("összesen: " + taxik.size());
		
		/*Staff csere = taxik.get(0).getTaxi().get(1);
		taxik.get(0).getTaxi().set(1, taxik.get(2).getTaxi().get(0));
		taxik.get(2).getTaxi().set(0, csere);
		*/
		for (int i = 0; i < taxik.size(); ++i) {
			System.out.println(i+ ". taxi: " + taxik.get(i).fullLengthOfTheRoad());
			
			for (int j = 0; j < taxik.get(i).getTaxi().size(); ++j) {
				System.out.println(taxik.get(i).getTaxi().get(j));
			}
			
			System.out.println("");
		}
		
	}
	
	
}