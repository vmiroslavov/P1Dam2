package GenerarXml;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class generarXML {
		
		
		public static void main(String[] args) throws ClassNotFoundException, Throwable {
			
			//Direccio on guardarem els arxius xml
			String Ruta = "\\\\192.168.2.105\\xml\\" + (new java.sql.Date(System.currentTimeMillis()))+"\\"+ new java.sql.Date(System.currentTimeMillis()) + ".xml";
			String Ruta2 = "\\\\192.168.2.105\\xml\\" + (new java.sql.Date(System.currentTimeMillis())); 
			
			File carpeta = new File(Ruta2);
			if(!carpeta.exists()) {
				carpeta.mkdir();
			}
			
				 Class.forName("org.postgresql.Driver");
			try (Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.2.105:5432/infinity",
					"postgres", "1234")) {
				
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM paquets;");

				DocumentBuilderFactory docbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder docb = docbf.newDocumentBuilder();
				Document document = docb.newDocument(); 
				
				// Arrel		
				Element arrel = document.createElement("infinity");
				document.appendChild(arrel);
		   	     
				while (rs.next()) {
					var estat1 = rs.getBoolean("estat");
					String entregat;
					if (estat1 == true) {
						entregat = "Entregat";

					} else {
						 entregat = "No entregat";
					}	

						// Pare
						Element pare = document.createElement("comanda");
						arrel.appendChild(pare); 

						// Declarar id a l'element pare

						Attr id = document.createAttribute("id");
						id.setValue(rs.getString("idpaquet"));
						pare.setAttributeNode(id);

						// id del paquet
						Element IDPaquet = document.createElement("IDPaquet");
						IDPaquet.appendChild(document.createTextNode(rs.getString("idpaquet")));
						pare.appendChild(IDPaquet);

						// id del client
						Element IDClient = document.createElement("DNIClient");
						IDClient.appendChild(document.createTextNode(rs.getString("dniclient")));
						pare.appendChild(IDClient);

						// id del treballador
						Element IDRepartidor = document.createElement("DNIRep");
						IDRepartidor.appendChild(document.createTextNode(rs.getString("dnirep")));
						pare.appendChild(IDRepartidor);

						// direcció
						Element dir = document.createElement("direccio");
						dir.appendChild(document.createTextNode(rs.getString("direccio")));
						pare.appendChild(dir);

						// latitud
						Element latitud = document.createElement("latitud");
						latitud.appendChild(document.createTextNode(rs.getString("latitud")));
						pare.appendChild(latitud);

						// longitud
						Element longitud = document.createElement("longitud");
						longitud.appendChild(document.createTextNode(rs.getString("longitud")));
						pare.appendChild(longitud);

						// pes
						Element pes = document.createElement("pes");
						pes.appendChild(document.createTextNode(rs.getString("pes")));
						pare.appendChild(pes);

						// cp
						Element cp = document.createElement("codip");
						cp.appendChild(document.createTextNode(rs.getString("codip")));
						pare.appendChild(cp);

						// estat
						Element estat = document.createElement("estat");
						estat.appendChild(document.createTextNode(entregat));
						pare.appendChild(estat);

						// data entrega
						Element dataEnt = document.createElement("dataEnt");
						dataEnt.appendChild(document.createTextNode(rs.getString("dataent")));
						pare.appendChild(dataEnt); 
					

					
				}
	
				//Escrivim el contingut en un fitxer XML
				
						  TransformerFactory transformerFactory = TransformerFactory.newInstance();
					      Transformer transformer = transformerFactory.newTransformer();
					      DOMSource source = new DOMSource(document);
					      StreamResult result = new StreamResult(new File(Ruta));
					      transformer.transform(source, result);
						
					
					
			}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}catch (TransformerException tf) {
					tf.printStackTrace();
			}catch (ParserConfigurationException pe) {
					pe.printStackTrace();
			}
		}
}
				
		
		


