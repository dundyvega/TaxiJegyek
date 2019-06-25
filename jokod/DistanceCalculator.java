import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.metal.MetalLookAndFeel;



import hu.dundyvega.taxiexport.managment.FileOperator;
import hu.dundyvega.taxiexport.objects.Graf;
import hu.dundyvega.taxiexport.objects.Staff;
import hu.dundyvega.taxiexport.objects.Taxi;
import javafx.scene.control.ProgressBar;
/*
import hu.dundyvega.taxiexport.managment.FileOperator;
import hu.dundyvega.taxiexport.objects.Graf;
import hu.dundyvega.taxiexport.objects.Staff;
import hu.dundyvega.taxiexport.objects.Taxi;
*/
import javafx.scene.text.Font;

/**
 * GUI
 * @author dundyvega
 *
 */
class DistanceCalculator extends JFrame
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar mb;
	private DistanceCalculator tihs;
	private JList<Staff> jl21;
	private JList<Staff> jl22;
	private JList<Staff> jlNemKell;
	private JList<Staff> jlNemTudom;
	private JButton jl21but;
	private JButton jl22but;
	private JButton jlNemKellbut;
	private JButton jlNemTudombut;
	private JButton jl21NemKell;
	private JButton jl22NemKell;
	private JMenu fajl;
	//private JMenuItem kollegak;
	private JMenuItem export;
	private JLabel statusBar;
	
	private Thread progressBarThread; 
	
	private JMenu beallitasok;
	
	private boolean vege;
	
	
	private JMenu optimalizalas;
	ButtonGroup optimGroup;
	JRadioButtonMenuItem gomb;
	JRadioButtonMenuItem api;
	
	private JMenu hibaHatar;
	ButtonGroup hibaHatarGroup;
	JRadioButtonMenuItem nulla;
	JRadioButtonMenuItem fel;
	JRadioButtonMenuItem nullaegesznyolc;
	JRadioButtonMenuItem egy;
	JRadioButtonMenuItem egyfel;
	JRadioButtonMenuItem ketto;
	JRadioButtonMenuItem harom;
	
	private ArrayList<Staff> ar;
	private JProgressBar progressBar;
	
	DistanceCalculator() {
		
		//Cím beállítás
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setTitle("Taxi kereső");
		
		
		//jl21 beállítás
		JPanel jp21 = new JPanel();
		jp21.setLayout(new BorderLayout());
		
		DefaultListModel<Staff> model21 = new DefaultListModel<>();
		jl21 = new JList<Staff>(model21);
		jp21.add(new JScrollPane(jl21));
		
		jl21but = new JButton("Taxi: 21:30!!");
		jl21NemKell = new JButton("Nem kell taxi");
		
		

		
		JPanel panel21 = new JPanel();
		panel21.setLayout(new GridLayout(2, 1));
		panel21.add(jl21but);
		panel21.add(jl21NemKell);
		
		jp21.add(panel21, BorderLayout.SOUTH);
		
		
		
		//jl22 beállítás
		JPanel jp22 = new JPanel();
		jp22.setLayout(new BorderLayout());
		
		DefaultListModel<Staff> model22 = new DefaultListModel<>();
		jl22 = new JList<Staff>(model22);
		jp22.add(new JScrollPane(jl22));


		jl22but = new JButton("Taxi a 22:00!!");
		jl22NemKell = new JButton("Nem kell taxi");
		
		JPanel panel22 = new JPanel();
		panel22.setLayout(new GridLayout(2, 1));
		panel22.add(jl22but);
		panel22.add(jl22NemKell);
		
		jp22.add(panel22, BorderLayout.SOUTH);
		
		
		//jl22 beállítás
		JPanel jpNemKell = new JPanel();
		jpNemKell.setLayout(new BorderLayout());
		
		DefaultListModel<Staff> modelNemKell = new DefaultListModel<>();
		jlNemKell = new JList<Staff>(modelNemKell);
		jpNemKell.add(new JScrollPane(jlNemKell));
		jlNemKellbut = new JButton("Kérek taxit!");
		jpNemKell.add(jlNemKellbut, BorderLayout.SOUTH);	
		
		
		//jlnemtudom beállítás
		JPanel jpNemTudom = new JPanel();
		jpNemTudom.setLayout(new BorderLayout());
		
		DefaultListModel<Staff> modelNemTudom = new DefaultListModel<>();
		jlNemTudom = new JList<Staff>(modelNemTudom);
		jpNemTudom.add(new JScrollPane(jlNemTudom));
		jlNemTudombut = new JButton("Kérek taxit!");
		jpNemTudom.add(jlNemTudombut, BorderLayout.SOUTH);	
		
		
		JPanel content = new JPanel();
		content.setLayout(new GridLayout(1, 4));
		
		content.add(jp21);
		content.add(jp22);
		content.add(jpNemKell);
		content.add(jpNemTudom);
		
		mb = new JMenuBar();
		
		//kollegak = new JMenuItem("Kollégák");
		export = new JMenuItem ("Export");
		//export.setEnabled(false);
		
		tihs = this;
		
		
		
		fajl = new JMenu("Fájl");
		//fajl.add(kollegak);
		
		/**
		 * A kollégákat betöltő listener, az export kicsit távolabb lesz
		 */
		/*kollegak.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				 JFileChooser fileChooser = new JFileChooser();
				 
				 
				 String path = "user.home";
				 
				  try {
					path = new File(".").getCanonicalPath() + "/";
					
					System.out.println(path);
					
					
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			        fileChooser.setCurrentDirectory(new File(path));
	
			        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Kollégák névsora", "xlsx"));
			        fileChooser.setAcceptAllFileFilterUsed(true);
			        int result = fileChooser.showOpenDialog(tihs);
			        if (result == JFileChooser.APPROVE_OPTION) {
			            File selectedFile = fileChooser.getSelectedFile();
			            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
			            
			            FileOperator.setTaxi(selectedFile.getAbsolutePath());
			            try {
			            ar = FileOperator.WalkersFromExcel();
			            
			            export.setEnabled(true);
			            
			            } catch (Exception ex) {
			            	
			            	JOptionPane.showMessageDialog(tihs, "Hibás fájl!");
			            	export.setEnabled(false);
			            	
			        		jl21but.setEnabled(false);
			        		jl22but.setEnabled(false);
			        		jlNemKellbut.setEnabled(false);
			        		jlNemTudombut.setEnabled(false);
			        		jl21NemKell.setEnabled(false);
			        		jl22NemKell.setEnabled(false);
			            	
			            	
			            }
			            
			        }
				
			}
			
		});*/
		
		try {
			ar = FileOperator.WalkersFromExcel();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(tihs, "Nem található a taxi.xlsx fájl");
			System.exit(0);
		}
		
		fajl.add(export);
		
		mb.add(fajl);
		
		
		
		beallitasok = new JMenu("Beállítások");
		beallitasok.addSeparator();
		
		optimalizalas = new JMenu("Optimalizálás");
		beallitasok.add(optimalizalas);
		
		gomb = new JRadioButtonMenuItem("gömbi koordináták");
		api = new JRadioButtonMenuItem("open api koordináták");
		
		optimGroup = new ButtonGroup();
		optimGroup.add(gomb);
		optimalizalas.add(gomb);
		gomb.setSelected(true);
		
		
		optimalizalas.add(api);
		optimGroup.add(api);
		
		
		beallitasok.addSeparator();
		hibaHatar = new JMenu("Hibahatár");
		beallitasok.add(hibaHatar);
		
		hibaHatarGroup = new ButtonGroup();
		nulla = new JRadioButtonMenuItem("0 km");
		fel = new JRadioButtonMenuItem("0,5 km");
		nullaegesznyolc = new JRadioButtonMenuItem("0,8 km");
		egy = new JRadioButtonMenuItem("1 km");
		egyfel = new JRadioButtonMenuItem("1,5 km");
		ketto = new JRadioButtonMenuItem("2 km");
		harom = new JRadioButtonMenuItem("3 km");
		
		hibaHatarGroup.add(nulla);
		hibaHatarGroup.add(fel);
		hibaHatarGroup.add(nullaegesznyolc);
		hibaHatarGroup.add(egy);
		hibaHatarGroup.add(egyfel);
		hibaHatarGroup.add(ketto);
		hibaHatarGroup.add(harom);
		
		egyfel.setSelected(true);
		
		
		hibaHatar.add(nulla);
		hibaHatar.add(fel);
		hibaHatar.add(nullaegesznyolc);
		hibaHatar.add(egy);
		hibaHatar.add(egyfel);
		hibaHatar.add(ketto);
		hibaHatar.add(harom);
		
		mb.add(beallitasok);
		
		
		
		/*
		 * private JMenu beallitasok;
	
	
	private JMenu optimalizalas;
	ButtonGroup optimGroup;
	JRadioButtonMenuItem gomb;
	JRadioButtonMenuItem api;
	
	private JMenu hibaHatar;
	ButtonGroup hibaHatarGroup;
	JRadioButtonMenuItem nulla;
	JRadioButtonMenuItem egy;
	JRadioButtonMenuItem egyfel;
	JRadioButtonMenuItem ketto;
	JRadioButtonMenuItem harom;*/
		
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(content);
		
		statusBar = new JLabel("  ");
		JLabel north = new JLabel("  ");
		JLabel west = new JLabel("  ");
		JLabel est = new JLabel(" ");
		//contentPanel.add(statusBar, BorderLayout.SOUTH);
		
		
		progressBar = new JProgressBar();
	    progressBar.setValue(35);
	    progressBar.setStringPainted(true);
	    contentPanel.add(progressBar, BorderLayout.SOUTH);
	    progressBar.setVisible(false);
	    
		
		
		contentPanel.add(north, BorderLayout.NORTH);
		contentPanel.add(est, BorderLayout.EAST);
		contentPanel.add(west, BorderLayout.WEST);
		this.setContentPane(contentPanel);
		
		
		
		/*-
		 * Ez csak a menü megnyítása után fog bejönni csak érdekel, hogy hogyan működik
		 */
/*		

		
*/		
		
		
		
		
		//figyelő hozzáadása, nem kell 21
		jl21NemKell.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				//ha van kiválasztott elem 
				int selected = jl21.getSelectedIndex();
				if (selected != -1) {
					Staff selectedStaff = model21.get(selected);
					model21.remove(selected);
					modelNemKell.addElement(selectedStaff);
					
				}
				
				
			}
			
		});
		
		
		//figyelő hozzáadása, nem kell 22
		jl22NemKell.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				//ha van kiválasztott elem 
				int selected = jl22.getSelectedIndex();
				if (selected != -1) {
					Staff selectedStaff = model22.get(selected);
					model22.remove(selected);
					modelNemKell.addElement(selectedStaff);
					jl22.setSelectedIndex(0);
					
				}
				
				
			}
			
		});
		
		
		//figyelő hozzáadása kell taxi1
		
		jlNemKellbut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selected = jlNemKell.getSelectedIndex();
				//ha ki van választva
				
				if (selected !=-1) {
					Staff selectedStaff = modelNemKell.get(selected);
					modelNemKell.removeElementAt(selected);
					
					//ha 22:00-kor végez a kolléga
					if (selectedStaff.getDt().equals("22:0")) {
						model22.addElement(selectedStaff);
					} else {
						//21:30
						model21.addElement(selectedStaff);
					}
					
				}
				
			}
			
		});
		
		
		
		//figyelő hozzáadása kell taxi2
		
		jlNemTudombut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selected = jlNemTudom.getSelectedIndex();
				//ha ki van választva
				
				if (selected !=-1) {
					Staff selectedStaff = modelNemTudom.get(selected);
					modelNemTudom.removeElementAt(selected);
					
					//ha 22:00-kor végez a kolléga
					if (selectedStaff.getDt().equals("22:0")) {
						model22.addElement(selectedStaff);
					} else {
						//21:30
						model21.addElement(selectedStaff);
					}
					
				}
				
			}
			
		});
		
		
		//kell taxi 22
		/**
		 * Beülteti a 22-es ügyfeleket a taxikba
		 */
		jl21but.addActionListener(new ActionListener() {

			@Override
			synchronized public void actionPerformed(ActionEvent e) {
				beulTaxikba(model21, "taxi 21:30-tól:");
				
			}
			
		});
		
		
		//kell taxi 22
		/**
		 * Beülteti a 22-es ügyfeleket a taxikba
		 */
		jl22but.addActionListener(new ActionListener() {

			@Override
			synchronized public void actionPerformed(ActionEvent e) {
				
				beulTaxikba(model22, "taxi 22:00-tól:");
			}
			
		});
		
		
		
		
		
		
		jl21but.setEnabled(false);
		jl22but.setEnabled(false);
		jlNemKellbut.setEnabled(false);
		jlNemTudombut.setEnabled(false);
		jl21NemKell.setEnabled(false);
		jl22NemKell.setEnabled(false);
		
		
		
		/**
		 * export menü listener
		 */
		
		export.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				 JFileChooser fileChooser = new JFileChooser();
				 String path = "user.home";
				 
				  try {
					path = new File(".").getCanonicalPath() + "/";
					
					System.out.println(path);
					
					
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			        fileChooser.setCurrentDirectory(new File(path));
			        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Kollégák névsora", "xlsx"));
			        fileChooser.setAcceptAllFileFilterUsed(true);
			        int result = fileChooser.showOpenDialog(tihs);
			        if (result == JFileChooser.APPROVE_OPTION) {
			        	
			        	System.out.println("itt vagyunk");
			            File selectedFile = fileChooser.getSelectedFile();
			            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
			            
			            FileOperator.setExport(selectedFile.getAbsolutePath());
			            /**
			             * feltöltjük elemekkel a táblát
			             */
			            
						model21.removeAllElements();
						model22.removeAllElements();
						modelNemKell.removeAllElements();
						modelNemTudom.removeAllElements();
			            
			    		
			    			ArrayList<String> walkers;
							try {
								walkers = FileOperator.walkersFromExportFile();
								
								System.out.println("kacsa");
								

								
				    			for (int i = 0; i < walkers.size(); ++i) {
				    				
				    				boolean talalt = false;
				    				String[] neves = walkers.get(i).split("\\*");
				    				String nev = neves[0];
				    				String dt = neves[1];
				    				
				    				for (int j = 0; j < ar.size() && !talalt; ++j) {
				    						
				    					if (nev.equals(ar.get(j).getName())) {
				    						ar.get(j).setDt(dt);
				    						
				    						//ha a karakternek kell taxi
				    						if (ar.get(j).getTaxi().equals("igen")) {

				    							if (ar.get(j).getLat() == 0) {
				    								
				    								//message box fog felugrani
				    								JOptionPane.showMessageDialog(tihs, "Hibás fájl!: " + ar.get(j) + "-nél nincs megfelelő cím beállítva");
				    								break;
				    							}
				    							//ha 21:30-as
				    							if (dt.equals("21:30")) {
				    								model21.addElement(ar.get(j));
				    							} else {
				    								//ha 22:30-as
				    								model22.addElement(ar.get(j));
				    							}
				    						} else if (ar.get(j).getTaxi().equals("nem")) {
				    							//ha nem kell taxi
				    							modelNemKell.addElement(ar.get(j));
				    						} else {
				    							//ha nem lehet tudni
				    							modelNemTudom.addElement(ar.get(j));
				    						}
				    						
				    						talalt = true;
				    					}
				    					
				    					
				    				}
				    				
				    				if (!talalt) {
	    								JOptionPane.showMessageDialog(tihs, "Hibás fájl!: " + walkers.get(i) + " nem található");

			    					}
				    				
				    				
				    		
				    				
				    			}
				    			
				    			
				    			jl21but.setEnabled(true);
			    				jl22but.setEnabled(true);
			    				jlNemKellbut.setEnabled(true);
			    				jlNemTudombut.setEnabled(true);
			    				jl21NemKell.setEnabled(true);
			    				jl22NemKell.setEnabled(true);
			    				
							} catch (Exception e1) {
								// TODO Auto-generated catc
								
								JOptionPane.showMessageDialog(tihs, "Hibás fájl!");
								System.out.println(e1);
								
								jl21but.setEnabled(false);
								jl22but.setEnabled(false);
								jlNemKellbut.setEnabled(false);
								jlNemTudombut.setEnabled(false);
								jl21NemKell.setEnabled(false);
								jl22NemKell.setEnabled(false);
							}
							   
					        
			        }

			    }

			    });
		
		
		
		this.setJMenuBar(mb);
		this.setBounds(30, 40, 600, 600);
		this.setVisible(true);		
		
	}
	
	public synchronized void beulTaxikba(DefaultListModel<Staff> model, String idopont) {
		
		Staff origo = new Staff(-1, "UPC János", "str. Brancusi 147", 46.760893, 23.613569, "nem");
		
		double hibahatar = 0;
		
		/*hibahatár beállítása a menu alapján*/
		
		if (nulla.isSelected()) {
			
			hibahatar = 0;
			
		} else if (egy.isSelected()) {
			
			hibahatar = 1;
			
		} else if (egyfel.isSelected()) {
			
			hibahatar = 1.5;
			
		} else if (ketto.isSelected()) {
			hibahatar = 2;
			
		} else if (harom.isSelected()){
			hibahatar = 3;
		} else if (fel.isSelected()) {
			hibahatar = 0.5;
		} else if (nullaegesznyolc.isSelected()) {
			hibahatar = 0.8;
		}
		
		
		//számítás beállítása
		
		int szamitas = 0;
		
		if (gomb.isSelected()) {
			
			szamitas = 1;
			
		} else {
			
			szamitas = 0;
		}
		
		ArrayList<Staff> staffs = new ArrayList<Staff>();
		int progress = 0;

		for (int i = 0; i < model.size(); ++i) {
			staffs.add(model.get(i));
			
			
		}
		
		

		
		Collections.sort(staffs);
		//staffs.remove(0);
		
		ArrayList<Taxi> taxik = new ArrayList<Taxi>();
	
		
		//optimalizálás négy emberre

		while (staffs.size() > 0) { 
			
			
			
			// a lista első emberét beleültetjük az üres taxiba, és kitörüljük azok közül, akik még nem találták meg ahelyüket
			
			Taxi taxi = new Taxi();
			taxi.addStaff(staffs.get(0));
			
			
			staffs.remove(0);
			
			for (int i = 0; i < staffs.size(); ++i) {
				
				
				/*Ha beülne a taxiban rövidebb lenne az út mintha két taxi menne az origotól?
				 * + van-e hely a taxiban még egy embernek?*/
				
			
				//System.out.println(staffs.get(i) + " beülzetése " + origo.getDifrance(staff));
				
				if (taxi.fullLengthIfPlus(staffs.get(i), szamitas) + hibahatar < taxi.fullLengthOfTheRoad(szamitas) + 
						origo.getDifrance(staffs.get(i), szamitas) && taxi.notFullWalkers()) {
					
					taxi.addStaff(staffs.get(i));
					//System.out.println("kacsa" + staffs.get(i));
					
				} 
				
			}
			

	
	
			
			
			// kiszedjük azokat a listából, akiknek már van taxijuk
			for (int j = 0; j < taxi.getTaxi().size(); ++j) {
				staffs.remove(taxi.getTaxi().get(j));
			}
			//megtelt taxit útnak eresszük
			taxik.add(taxi);
			
		}
		
		
		
		//optimalizálás több emberre
		
		for (int i = 0; i < taxik.size(); ++i) {
			for (int j = 0; j < taxik.size(); ++j) {
				
				if (i != j && taxik.get(j).notFullWalkers()) {
					
					for (int k = 1; k < taxik.get(i).getTaxi().size(); ++k) {
						
						//ellenőrizzük, hogy ha az i. taxi k. elemét áttennénk a j. taxi utolsó helyére, akkor jobb lenne a költség
						if (taxik.get(j).notFullWalkers() && taxik.get(i).fullLengthOfTheRoadMinusK(k, szamitas) + 
								taxik.get(j).fullLengthPlusOptimalization(taxik.get(i).getTaxi().get(k), szamitas) < 
								
								taxik.get(i).fullLengthOfTheRoad(szamitas) + taxik.get(j).fullLengthOfTheRoad(szamitas)){
							
							Staff elem = taxik.get(i).getTaxi().get(k);
							taxik.get(i).getTaxi().remove(elem);
							taxik.get(j).addStaffOptimalization(elem);
							k = k - 1;
							//System.out.println(taxik.get(i).getTaxi().get(0));
							
							
						}
						
						
						
					}
					
					
				}
				
			}
			
		}
		
		
		
		
		/**
		 * Új ablak létrehozása
		 */
		
		
		
        JFrame frame = new JFrame("Eredmény");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setOpaque(true);
        JTextArea textArea = new JTextArea(15, 50);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        JScrollPane scroller = new JScrollPane(textArea);
		panel.add(scroller);
		
		frame.pack();
		frame.setSize(500, 500);
		frame.setContentPane(panel);
		
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        frame.setResizable(false);
       
		
		String eredmeny = "";
	
		System.out.println("összesen: " + taxik.size());
		
		eredmeny += "Összese: " + taxik.size() + " " + idopont+ "\n\n";
		
		/*Staff csere = taxik.get(0).getTaxi().get(1);
		taxik.get(0).getTaxi().set(1, taxik.get(2).getTaxi().get(0));
		taxik.get(2).getTaxi().set(0, csere);
		*/
		for (int i = 0; i < taxik.size(); ++i) {
			System.out.println((i + 1)+ ". taxi: ");
			
			eredmeny += (i+1)+ ". taxi: " + "\n";
			
			for (int j = 0; j < taxik.get(i).getTaxi().size(); ++j) {
				System.out.println(taxik.get(i).getTaxi().get(j));
				
				eredmeny += taxik.get(i).getTaxi().get(j) + "\n";
			}
			
			eredmeny += "\n";
			
			System.out.println("");
		}
		
		textArea.setText(eredmeny);
		
		StringSelection tx = new StringSelection(eredmeny);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(tx, null);
		
		
		//System.out.println(taxik.get(29).getTaxi().get(2).getTaxi());
		
		
		
	}
	
/*	
	public static String getDistance(final double lat1, final double lon1, final double lat2, final double lon2){

		try {
           String s = "http://router.project-osrm.org/route/v1/driving/" + lat1 + "," + lon1 + ";" + lat2 + "," + lon2 + "?overview=false";
    
           String result = doHttpUrlConnectionAction(s);
           
           System.out.println(result);
         
           JSONObject jSONObject = new JSONObject(result);
           JSONArray array = jSONObject.getJSONArray("routes");
           JSONObject routes = array.getJSONObject(0);
           JSONArray legs = routes.getJSONArray("legs");
           JSONObject steps = legs.getJSONObject(0);
           
           
  
           
           System.out.println(steps.getDouble("distance") / 1758.3);
           
           
		} catch (Exception ex) {System.out.println(ex);}
		
		return "";
		
            

	}
	
	
	
	
	
	
	
	
	
	 private static String doHttpUrlConnectionAction(String desiredUrl)
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
	
	
	*/
	
	
	
	public synchronized void threadsStarting(DefaultListModel<Staff> model) {
		

		
		
		Thread tr = new Thread (new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				/*jl21but.setEnabled(false);
				jl22but.setEnabled(false);
				jlNemKellbut.setEnabled(false);
				jlNemTudombut.setEnabled(false);
				jl21NemKell.setEnabled(false);
				jl22NemKell.setEnabled(false);*/
				
				beulTaxikba(model, "null pointer exception");
				vege = true;
				
				
				/*jl21but.setEnabled(true);
				jl22but.setEnabled(true);
				jlNemKellbut.setEnabled(true);
				jlNemTudombut.setEnabled(true);
				jl21NemKell.setEnabled(true);
				jl22NemKell.setEnabled(true);*/
			}
			
		});
		
		
		progressBarThread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				progressBar.setVisible(true);
				int value = 1;
				
	
				
				while (!vege) {
					
					if (value > 80) {
						
						value = 1;
						
					} else 
					
					value += 5;
					
					progressBar.setValue(value);
					
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
	
				
				progressBar.setVisible(false);
			    progressBarThread.interrupt();
		
				
			}
			
		});
		
		progressBarThread.start();
		tr.start();
		
		



	}
	
	
	
	
	public static void main (String[] args) throws java.lang.Exception
	{
		
		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (Exception ex) {}

		
		DistanceCalculator cl = new DistanceCalculator();
		//getDistance(46.759409,23.5441039, 46.7607093,23.6113426);
		
		
		
		
		
	}
}