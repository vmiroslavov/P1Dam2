package M9Proves;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;

public class Firma {
	public static void main(String[] args) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
		Scanner lector = new Scanner (System.in);
		KeyPair claus;				
		claus = randomGenerate(2048);

		PrivateKey privada = claus.getPrivate();
		PublicKey publica = claus.getPublic();
		
		 
		   System.out.println("Tria una opció: ");
		   System.out.println("  1 -Signar arxiu");
		   System.out.println("  2 -Validar signatura");
		   String introduit = lector.nextLine();
		   
		   if (introduit.equals("1")) {
			   
			   System.out.println("Quin fitxer vols signar? indica la ruta: ");
			   String ruta = lector.nextLine();
			   File arxiu = new File (ruta);	
			   FileReader fic = new FileReader(arxiu);
			   
			   int i;
			   
			   String contingut = null;
			   
			   while((i=fic.read()) != -1){
				 contingut = contingut + ( (char) i);
				   
			   }
			   
			   
			   byte[] signatura = signData(contingut.getBytes(), privada);
			    FileWriter fw = new FileWriter("signatura.txt");
				fw.write(Base64.getEncoder().encodeToString(signatura));
				fw.close();
				
			//	byte[] bSignatura = Base64.getDecoder().decode(contingut);
				
				byte[] cPublica = publica.getEncoded();
				FileWriter fw2 = new FileWriter("claup.txt");
				fw2.write(Base64.getEncoder().encodeToString(cPublica));
				fw2.close();
				
				System.out.println("fitxer signat correctament!");
			   
		   }
		   
		  
		   if (introduit.equals("2")) {
			  	
		//		System.out.println(Base64.getEncoder().encodeToString(cPublica));
				
			  System.out.println("Quin fitxer vols validar? Indica la ruta: "); 
			   String ruta2 = lector.nextLine();
			   File arxiu = new File (ruta2);	
			   FileReader fic = new FileReader(arxiu);
			   
			   int i;
			   
			   String contingut = null;
			   
			   while((i=fic.read()) != -1){
				 contingut = contingut + ( (char) i);
				   
			   }
			   
			   
				FileReader fr1 = new FileReader("signatura.txt");
				BufferedReader br1 = new BufferedReader(fr1);
				
				FileReader fr2 = new FileReader("claup.txt");
				BufferedReader br2 = new BufferedReader(fr2); 
				
				String linia;
				String linia2;
				byte[] bSignatura = null;
				byte[] bPublica = null;
				
				while((linia = br1.readLine())	 != null) {
					
					bSignatura = Base64.getDecoder().decode(linia.getBytes());
					
				}
				
				while((linia2 = br2.readLine()) != null) {
					
					bPublica = Base64.getDecoder().decode(linia2.getBytes());
				}
			
				
				
				 KeyFactory kFactory = KeyFactory.getInstance("RSA");  
	                // decode base64 of your key
	               
	                // generate the public key
	             X509EncodedKeySpec spec =  new X509EncodedKeySpec(bPublica);
	             PublicKey publicKey = (PublicKey) kFactory.generatePublic(spec);
				
				
				boolean validacio = validateSignature(contingut.getBytes(),bSignatura,publicKey);
				
				System.out.println("la validació és: "+validacio);
			   
			   
			   
		   }
		

		//  System.out.println(Base64.getEncoder().encodeToString(signatura));
		
	
	}
	
	

	public static byte[] signData(byte[] data, PrivateKey priv) {
		byte[] signature = null;

		try {
			Signature signer = Signature.getInstance("SHA1withRSA");
			signer.initSign(priv);
			signer.update(data);
			signature = signer.sign();
		} catch (Exception ex) {
			System.err.println("Error signant les dades: " + ex);
		}
		return signature;
	}

	public static KeyPair randomGenerate(int len) {
		KeyPair keys = null;

		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(len);
			keys = keyGen.genKeyPair();

		} catch (Exception ex) {

			System.err.println("Generador no disponible.");
		}
		return keys;
	}

	public static byte[] encryptData(byte[] data, PublicKey pub) {
		byte[] encryptedData = null;
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "SunJCE");
			cipher.init(Cipher.ENCRYPT_MODE, pub);
			encryptedData = cipher.doFinal(data);
		} catch (Exception ex) {
			System.err.println("Error xifrant: " + ex);
		}
		return encryptedData;
	}

	

	public static boolean validateSignature(byte[] data, byte[] signature, PublicKey pub) {
		boolean isValid = false;
		try {
			Signature signer = Signature.getInstance("SHA1withRSA");

			signer.initVerify(pub);
			signer.update(data);
			isValid = signer.verify(signature);
		} catch (Exception ex) {
			System.err.println("Error validant les dades: " + ex);
		}
		return isValid;
	}

}
