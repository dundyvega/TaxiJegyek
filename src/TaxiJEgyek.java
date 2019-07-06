import java.awt.BorderLayout;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.json.JSONArray;
import org.json.JSONObject;

import hu.dundyvega.taxiexport.managment.FileOperator;
import hu.dundyvega.taxiexport.objects.Graf;
import hu.dundyvega.taxiexport.objects.Staff;
import hu.dundyvega.taxiexport.objects.Taxi;


class DistanceCalculator extends JFrame
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar mb;
	private JList<Staff> jl;
	private JMenu fajl;
	private JMenuItem kollegak;
	String response;
	String parsedDistance;
	private JMenuItem export;
	
	
	DistanceCalculator(ArrayList<Staff> ar) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Series");
		
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		
		DefaultListModel<Staff> model = new DefaultListModel<>();
		jl = new JList<Staff>(model);
		
		for (int i = 0; i < ar.size(); ++i) {
			model.addElement(ar.get(i));
		}
		
		jp.add(new JScrollPane(jl));
		this.setContentPane(jp);
		
		mb = new JMenuBar();
		
		kollegak = new JMenuItem("Kollégák");
		export = new JMenuItem ("Export");
		
		fajl = new JMenu("Fájl");
		fajl.add(kollegak);
		fajl.add(export);
		
		mb.add(fajl);
		
		
		
		this.setJMenuBar(mb);
		this.setBounds(30, 40, 600, 600);
		this.setVisible(true);
		
		
		
	}
	
	
	public static void main (String[] args) throws java.lang.Exception
	{
		//System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, "M") + " Miles\n");
		//System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, "K") + " Kilometers\n");
		
		//System.out.println(String.format("%.2f", distanceAireCordinates(46.760893, 23.613462, 46.759571, 23.546319)) + " Kilometers\n");
		
		//System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, "N") + " Nautical Miles\n");
		
		//System.out.println(getDistance(46.760893, 23.613462, 46.759571, 23.546319));
		//FileOperator.createXML();
		
		
		//FileOperator.newItem("Medve Alíz", "Óperenciás tenger", 46.765035, 23.528973, "igen");
		//FileOperator.newItem("Veres Attila", "Fsega", 46.773357, 23.620956, "igen");
		//FileOperator.newItem("Kurva", "maraest..", 46.788587, 23.616681, "igen");
		
		
		//FileOperator.modifyStaffInformationsOnXML(2, "Tasnad 29/22", 23.46786, -98.53586);
		
		//ArrayList<Staff> staffs = FileOperator.allWalkers();
		//System.out.println(staffs.size());
		//
		
		//rendezes();
		
		//FileOperator.walkersFromExportFile();
		
		
		//ArrayList<Staff> ar = FileOperator.WalkersFromExcel();
		ArrayList<Staff> ar = new ArrayList<Staff>();
		new DistanceCalculator(ar);
		
		//FileOperator.xlsxToXml();
		
		
		
	}


	
	
	
	public static void rendezes() {
		
		
		
		//egy lista, ebben vannak az emberek, ezt abból a listából mentem ki, amit a csopvezek beállítottak 11-re
		ArrayList<Staff> staffs = FileOperator.allWalkers();
		
		Graf gr = new Graf();
		Staff origo = new Staff(-1, "UPC János", "str. Brancusi 147", 46.760893, 23.613569, "nem");
		gr.appendChild(origo);

		for (int i = 0; i < staffs.size(); ++i) {
			gr.appendChild(staffs.get(i));
		}
		
		staffs = new ArrayList<Staff>();
		gr.tombosit(staffs);
		
		staffs.remove(0);
		
		ArrayList<Taxi> taxik = new ArrayList<Taxi>();
	
		
		ArrayList<Staff> nemKellTaxi = new ArrayList<Staff>();
		
		
		ArrayList<Staff> kellTaxi = new ArrayList<Staff>();
		
		
		ArrayList<Staff> nemTudomHogyKellETaxi = new ArrayList<Staff>();
		
		for (int i = 0; i < staffs.size(); ++i) {
			if (staffs.get(i).getTaxi().equals("igen")) {
				kellTaxi.add(staffs.get(i));
			} else if (staffs.get(i).getTaxi().equals("nem")) {
				nemKellTaxi.add(staffs.get(i));
			} else {
				nemTudomHogyKellETaxi.add(staffs.get(i));
			}
			
		}
		
		System.out.println("");

		while (kellTaxi.size() > 0) { 
			
			// a lista első emberét beleültetjük az üres taxiba, és kitörüljük azok közül, akik még nem találták meg ahelyüket
			
			Taxi taxi = new Taxi();
			taxi.addStaff(kellTaxi.get(0));
			
			
			kellTaxi.remove(0);
			
			for (int i = 0; i < kellTaxi.size(); ++i) {
				
				
				/*Ha beülne a taxiban rövidebb lenne az út mintha két taxi menne az origotól?
				 * + van-e hely a taxiban még egy embernek?*/
				
				if (taxi.fullLengthIfPlus(kellTaxi.get(i)) < taxi.fullLengthOfTheRoad() + 
						origo.getDifrance(staffs.get(i)) && taxi.notFullWalkers()) {
					
					taxi.addStaff(kellTaxi.get(i));
					
					
				} 
				
			}
			

	
	
			
			
			// kiszedjük azokat a listából, akiknek már van taxijuk
			for (int j = 0; j < taxi.getTaxi().size(); ++j) {
				kellTaxi.remove(taxi.getTaxi().get(j));
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
		
		
		
		//System.out.println(taxik.get(29).getTaxi().get(2).getTaxi());
		
	}
	
	
	
	
	public String getDistance(final double lat1, final double lon1, final double lat2, final double lon2){


        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL("http://maps.googleapis.com/maps/api/directions/json?origin=" + lat1 + "," + lon1 + "&destination=" + lat2 + "," + lon2 + "&sensor=false&units=metric&mode=driving");
                    final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                     response = iStreamToString(in);

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("routes");
                    JSONObject routes = array.getJSONObject(0);
                    JSONArray legs = routes.getJSONArray("legs");
                    JSONObject steps = legs.getJSONObject(0);
                    JSONObject distance = steps.getJSONObject("distance");
                     parsedDistance=distance.getString("text");

                    Log.v("Distsnce","Distance>>"+parsedDistance);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return parsedDistance;
    }


  public String iStreamToString(InputStream is1)
    {
        BufferedReader rd = new BufferedReader(new InputStreamReader(is1), 4096);
        String line;
        StringBuilder sb =  new StringBuilder();
        try {
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String contentOfMyInputStream = sb.toString();
        return contentOfMyInputStream;
    }
	

	
	
}