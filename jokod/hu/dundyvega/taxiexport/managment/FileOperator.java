/**
 * 
 * Ez az osztály végzi el az összes fájlműveletet
 * @dundyvega
 * 
 **/

package hu.dundyvega.taxiexport.managment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import hu.dundyvega.taxiexport.objects.Staff;
import hu.dundyvega.taxiexport.objects.Taxi;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

final public class FileOperator {

	/**
	 * Ez a fájl az a fájl, ahonnan betöltődnek az adatok a címekről
	 */
	final static String xmlLoc = "transportation.staff";
	 static private String  taxi;
	 static private String export;
	
	
	
	/**
	 * Új elemet add hozzá az xml fájlhoz, az alábbi paraméterek alapján
	 * @param name: kolléga neve
	 * @param adress kolléga címe
	 * @param lat: hosszúság
	 * @param lon: szélesség
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static void newItem(String name, String adress, double lat, double lon, String info) {
		
		File file = new File(xmlLoc);
		
		try {
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			
			
			doc.getDocumentElement().normalize();
			
			Element rootElement = doc.getDocumentElement();
			
			int id = rootElement.getChildNodes().getLength();
			
			Element lastElement = (Element) rootElement.getChildNodes().item(id - 1);
			
			id = Integer.parseInt(lastElement.getAttribute("id")) + 1;
			
			Element staff = doc.createElement("Staff");
			

			
			staff.setAttribute("id", "" + id);
			staff.setAttribute("name", name);
			staff.setAttribute("adress", adress); 
			staff.setAttribute("lat", lat + "");
			staff.setAttribute("lon", lon + "");
			staff.setAttribute("taxi", info);
			
			rootElement.appendChild(staff);
			
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	/**
	 * Ez a függvény töröli azt az elemet az xml fájlból, aminek az id atributuma = id
	 * 
	 * @param id: ezt az értéket töröljük
	 */
	
	public static void deleteFromXML(int id) {
		
		try {
			File file = new File(xmlLoc);
			
			File fXmlFile = file;
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.parse(fXmlFile);
			Element rootElement = doc.getDocumentElement();
			
			NodeList nlist = doc.getElementsByTagName("Staff");
			
			for (int i = 0; i < nlist.getLength(); ++i) {
				
				//megkeressük a törölendő elemet
				
				Element deletable = (Element)nlist.item(i);
				
				if (deletable.hasAttribute("id") && deletable.getAttribute("id").equals("" + id)) {
					// megtaláltuk, emiatt töröljük
					
					rootElement.removeChild(deletable);
					
					break;
					
					
				}
			}
			
			//doc.removeChild(deletable);
			
			
			
			/*Fájlba mentés*/
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	/**
	 * Módosítjuk a kolléga címének adatait (pl. költözés esetén)
	 * @param id: id
	 * @param adress: cím
	 * @param lat: szélesség
	 * @param lon: hosszúság
	 */
	
	
	public static void modifyStaffInformationsOnXML(int id, String name, String adress, double lat, double lon, String taxi) {
		
		try {
			File file = new File(xmlLoc);
			
			File fXmlFile = file;
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.parse(fXmlFile);
			Element rootElement = doc.getDocumentElement();
			
			NodeList nlist = doc.getElementsByTagName("Staff");
			
			for (int i = 0; i < nlist.getLength(); ++i) {
				
				//megkeressük a módosítandó elemet
				
				Element staff = (Element)nlist.item(i);
				
				if (staff.hasAttribute("id") && staff.getAttribute("id").equals("" + id)) {
					// megtaláltuk, módosítjuk
					staff.setAttribute("name", name); 
					staff.setAttribute("adress", adress); 
					staff.setAttribute("lat", lat + "");
					staff.setAttribute("lon", lon + "");
					staff.setAttribute("taxi", taxi);
					
					break;
					
					
				}
			}
			
			//doc.removeChild(deletable);
			
			
			
			/*Fájlba mentés*/
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	/**
	 * 
	 * Az XML fájl létrehozása, amiben tárolva vannak az adatok
	 * 
	 */
	
	
	
	
	
	public static void createXML() {
		
		
		try {
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			Element rootElement = doc.createElement("WorkSpace");
			doc.appendChild(rootElement);
			
			Element staff = doc.createElement("Staff");
			
			staff.setAttribute("id", "0");
			staff.setAttribute("name", "Horváth Jónathán");
			staff.setAttribute("adress", "str. Tasnad 29/22"); 
			staff.setAttribute("lat", "46.759600");
			staff.setAttribute("lon", "23.546287");
			staff.setAttribute("taxi", "igen");
			rootElement.appendChild(staff);

			
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(xmlLoc);

			// Output to console for testing
			// StreamResult result = nElement id = (Element)elem.getElementsByTagName("animeID").item(0);ew StreamResult(System.out);

			try {
				transformer.transform(source, result);
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}



	/**
	 * Az összes kollégát beteszi egy listába
	 * @return
	 */
	public static ArrayList<Staff> allWalkers() {
		// TODO Auto-generated method stubá
		
		try {
			ArrayList<Staff> walkers = new ArrayList<Staff>();
			
			File fXmlFile = new File(xmlLoc);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			NodeList nlist = doc.getElementsByTagName("Staff");
			
			for (int i = 0; i < nlist.getLength(); ++i) {
			
				Element el = (Element)nlist.item(i);
				Staff staff = new Staff(Integer.parseInt(el.getAttribute("id")), el.getAttribute("name"), el.getAttribute("adress"),
						Double.parseDouble(el.getAttribute("lat")), Double.parseDouble(el.getAttribute("lon")), el.getAttribute("taxi")); 
				
				walkers.add(staff);
			}
			
			return walkers;
		
		} catch(Exception e) {
			return null;
		}
		
	}


	/**
	 * Egy excelből begyűjti a kollégákat
	 * @throws IOException 
	 * 
	 */
	
	public static ArrayList<Staff> WalkersFromExcel() throws IOException {
		
		
		ArrayList<Staff> walkers = new ArrayList<Staff>();
		
		FileInputStream excelFile = new FileInputStream(taxi);
		XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
		XSSFSheet datatypeSheet = workbook.getSheetAt(0);
		
		Iterator<Row> iterator = datatypeSheet.iterator();
		Row currentRow = iterator.next();
		
		while (iterator.hasNext()) {
			 currentRow = iterator.next();
			Iterator<Cell> cellIterator = currentRow.iterator();
			
			while (cellIterator.hasNext()) {
				//Cell currentCell = cellIterator.next();
                //getCellTypeEnum shown as deprecated for version 3.15
                //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
				
				//System.out.println("kacsa");
				Cell nameC = cellIterator.next();
				String name = nameC.getStringCellValue();
				
				Cell adressC = cellIterator.next();
				String adress = adressC.getStringCellValue();
				
				cellIterator.next();
				cellIterator.next();
				
				Cell taxiC = cellIterator.next();
				String taxi = taxiC.getStringCellValue();
				
				Cell latC = cellIterator.next();
				String lat = latC.getStringCellValue();
				double latD = Double.parseDouble(lat);
				
				Cell lonC = cellIterator.next();
				String lon = lonC.getStringCellValue();
				double lonD = Double.parseDouble(lon);
				
				//System.out.println("lan: " + lat + " lon" + lon);
				
				//FileOperator.newItem(, adress, lat, lon, info);
				
				//FileOperator.newItem(name, adress, latD, lonD, taxi);
				
				Staff st = new Staff(0, name, adress, latD, lonD, taxi);
				
				walkers.add(st);
			} 
		}
		
		
		return walkers;
	}
	
	
	/**
	 * 
	 * @return
	 * @throws IOException 
	 */

	public static ArrayList<String> walkersFromExportFile() throws IOException, NullPointerException {
		
		FileInputStream excelFile = new FileInputStream(export);
		XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
		XSSFSheet datatypeSheet = workbook.getSheetAt(0);
		
		Iterator<Row> iterator = datatypeSheet.iterator();
		Row currentRow = iterator.next();
		
		
		ArrayList<String> walkers = new ArrayList<String>();
		
		while (iterator.hasNext()) {
			 currentRow = iterator.next();
			Iterator<Cell> cellIterator = currentRow.iterator();
			
			while (cellIterator.hasNext()) {
				//Cell currentCell = cellIterator.next();
                //getCellTypeEnum shown as deprecated for version 3.15
                //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
				
				//System.out.println("kacsa");
				cellIterator.next();
				
				Cell nameC = cellIterator.next();
				String name = nameC.getStringCellValue();
				
				cellIterator.next();
				cellIterator.next();
				cellIterator.next();
				cellIterator.next();
				
				Cell datumC = cellIterator.next();
				String datum;
				 
				if (datumC.getCellType() == datumC.getCellTypeEnum().NUMERIC) {
				
				datum = datumC.getDateCellValue().getHours() + ":" + datumC.getDateCellValue().getMinutes();
				
				if (datum.equals("22:0") ||  datum.equals("21:30")) {
				
					walkers.add(name + "*" + datum);
					
					//System.out.println(name + "*" + datum);
				
				}
				
				} else {
					
					throw new NullPointerException("Rossz beviteli fálj vagy rossz adatok");
					
				}
				
				break;
				
			}
		}
		
		
		
		return walkers;
		
	}



	/**
	 * Beállítja az névsor helyét
	 * @param absolutePath
	 */
	public static void setTaxi(String absolutePath) {
		// TODO Auto-generated method stub
		taxi = absolutePath;
		
	}



/**
 * Beéllítja az export fálj helyét
 * @param absolutePath
 */
	public static void setExport(String absolutePath) {
		export = absolutePath;
		
	}
}
