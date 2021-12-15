package GenerarXml;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.itextpdf.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class generarPDF {

	public static final String ruta = "\\\\192.168.2.105\\xml\\" + (new java.sql.Date(System.currentTimeMillis()))+"\\"+ new java.sql.Date(System.currentTimeMillis()) + ".xml";
	
	public static void main(String[] args) throws ClassNotFoundException, Throwable {
		
		try {
			File archivo = new File (ruta);
			
			DocumentBuilderFactory docbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder docb = docbf.newDocumentBuilder();
			org.w3c.dom.Document  document = docb.parse(archivo);
			
			document.getDocumentElement();
			NodeList comanda = document.getElementsByTagName("comanda");
			
			for (int temp = 0; temp < comanda.getLength(); temp++) {
				Node nodo = comanda.item(temp);
				
				if(nodo.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) nodo;
					
					String idpaquet = ("ID del paquet: " + element.getElementsByTagName("IDPaquet").item(0).getTextContent()) + "\n";
	                String DNIClient = ("DNI del client: " + element.getElementsByTagName("DNIClient").item(0).getTextContent()) + "\n" ;
	                String DNIRep = ("DNI del repartidor: " + element.getElementsByTagName("DNIRep").item(0).getTextContent()) + "\n";
	                String direccio = ("Direccio: " + element.getElementsByTagName("direccio").item(0).getTextContent()) + "\n";
	                String latitud = ("latitud: " + element.getElementsByTagName("latitud").item(0).getTextContent()) + "\n";
	                String longitud = ("longitud: " + element.getElementsByTagName("longitud").item(0).getTextContent()) +" \n";
	                String pes = ("pes: " + element.getElementsByTagName("pes").item(0).getTextContent()) + "\n";
	                String codiP = ("Codi Postal:  " + element.getElementsByTagName("codip").item(0).getTextContent()) + "\n";
	                String dataEnt = ("Data entrega:  " + element.getElementsByTagName("dataEnt").item(0).getTextContent()) + "\n";
	                String estat = ("Estat: " + element.getElementsByTagName("estat").item(0).getTextContent()) + "\n \n";
	                
	           if(element.getElementsByTagName("estat").item(0).getTextContent().equals("Entregat")) {
	        	   
	           
	                DocumentBuilderFactory dbpdf = DocumentBuilderFactory.newInstance( );
	                Document docpdf = new Document();
	                PdfWriter pfw = PdfWriter.getInstance(docpdf, new FileOutputStream("\\\\192.168.2.105\\xml\\" + (new java.sql.Date(System.currentTimeMillis()))+"\\"+element.getElementsByTagName("IDPaquet").item(0).getTextContent()+"-"+ new java.sql.Date(System.currentTimeMillis()) + ".pdf"));
	                
	                docpdf.open();
	                
	                docpdf.add(new Paragraph("               INFINITY EXPRESS               "));
	                docpdf.add(new Paragraph(" "));
	                docpdf.add(new Paragraph( idpaquet));
	                docpdf.add(new Paragraph (DNIClient));
	                docpdf.add(new Paragraph (DNIRep));
	                docpdf.add(new Paragraph (direccio));
	                docpdf.add(new Paragraph (latitud));
	                docpdf.add(new Paragraph (longitud));
	                docpdf.add(new Paragraph (pes));
	                docpdf.add(new Paragraph (codiP));
	                docpdf.add(new Paragraph (dataEnt));
	                docpdf.add(new Paragraph (estat));
	                
	                docpdf.add(new Paragraph ("©infinity"+ "                      " + new java.sql.Date(System.currentTimeMillis()) ));
	                
	                docpdf.close();
	                pfw.close();
	           }
	                
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
