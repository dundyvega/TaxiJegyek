/**
 * 
 * Ez az osztály végzi el az összes fájlműveletet
 * @dundyvega
 * 
 **/

package hu.dundyvega.taxiexport.managment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

final public class FileOperator {

	/**
	 * Ez a fájl az a fájl, ahonnan betöltődnek az adatok a címekről
	 */
	final static String xmlLoc = "transportation.staff";
	
	
	
	/**
	 * Új elemet add hozzá az xml fájlhoz, az alábbi paraméterek alapján
	 * @param name: kolléga neve
	 * @param adress kolléga címe
	 * @param lat: hosszúság
	 * @param lon: szélesség
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static void newItem(String name, String adress, double lat, double lon) {
		
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
	
	
	public static void modifyStaffInformationsOnXML(int id, String adress, double lat, double lon) {
		
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
					
					staff.setAttribute("adress", adress); 
					staff.setAttribute("lat", lat + "");
					staff.setAttribute("lon", lon + "");
					
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
			rootElement.appendChild(staff);
			
			staff = doc.createElement("Staff");
			
			staff.setAttribute("id", "1");
			staff.setAttribute("name", "Horváth Hunor");
			staff.setAttribute("adress", "str. Tasnad 29/22"); 
			staff.setAttribute("lat", "46.759600");
			staff.setAttribute("lon", "23.546287");
			
			rootElement.appendChild(staff);
			
			
			
			staff = doc.createElement("Staff");
			
			staff.setAttribute("id", "1");
			staff.setAttribute("name", "p. Tica");
			staff.setAttribute("adress", "str. Tasnad 29/22"); 
			staff.setAttribute("lat", "46.759600");
			staff.setAttribute("lon", "23.546287");
			
			rootElement.appendChild(staff);
	
			
			staff = doc.createElement("Staff");
			staff.setAttribute("id", "2");
			staff.setAttribute("name", "Kis Pista");
			staff.setAttribute("adress", "str. Brates 10"); 
			staff.setAttribute("lat", "46.759534");
			staff.setAttribute("lon", "23.545468");
			
			rootElement.appendChild(staff);
			
			staff = doc.createElement("Staff");
			staff.setAttribute("id", "3");
			staff.setAttribute("name", "Nagy Pista");
			staff.setAttribute("adress", "str. Donath"); 
			staff.setAttribute("lat", "46.765035");
			staff.setAttribute("lon", "23.528973");
			
			rootElement.appendChild(staff);
			
			
			staff = doc.createElement("Staff");
			staff.setAttribute("id", "4");
			staff.setAttribute("name", "Sánta Jutka");
			staff.setAttribute("adress", "str. Titu Maiorescu 8"); 
			staff.setAttribute("lat", "46.758686");
			staff.setAttribute("lon", "23.606271");
			
			rootElement.appendChild(staff);
			
			staff = doc.createElement("Staff");
			staff.setAttribute("id", "5");
			staff.setAttribute("name", "Csöves János");
			staff.setAttribute("adress", "Piata Gari 3"); 
			staff.setAttribute("lat", "46.784066");
			staff.setAttribute("lon", "23.585422");
			
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
						Double.parseDouble(el.getAttribute("lat")), Double.parseDouble(el.getAttribute("lon"))); 
				
				walkers.add(staff);
			}
			
			return walkers;
		
		} catch(Exception e) {
			return null;
		}
		
	}





	
	
}
