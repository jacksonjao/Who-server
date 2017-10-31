package objetoXml;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class ObjetoXml implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7181363360409114292L;
	private static ObjetoXml instance;

	Document doc;
	Element usuarios,nickName,password,registros,post,contenido,posteador,ruta,comentario,comentador,idComentador;
	
	public ObjetoXml() {

		instance=null;
		
	
		
		try {
		DocumentBuilderFactory fabricarDoc = DocumentBuilderFactory.newInstance();
		DocumentBuilder construirDoc = fabricarDoc.newDocumentBuilder();
	
		
		try {
			doc=construirDoc.parse("data/file.xml");
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			
			doc= construirDoc.newDocument();
			registros = doc.createElement("registros");
			doc.appendChild(registros);

			// usuarios elements
			usuarios = doc.createElement("usuarios");
			registros.appendChild(usuarios);
			
			
			post = doc.createElement("post");
			registros.appendChild(post);
			
			
			comentador = doc.createElement("comentador");
			registros.appendChild(comentador);
		}
		
		//doc= construirDoc.newDocument();
		
		
		
		
	
	
		  } catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			  } 
		
		
	}

	
	public void escribirXml(){
		
		try {

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("data/file.xml"));
			transformer.transform(source, result);
	
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
		
	}



	
	public void ingresarDatos(String name, String pass){
		// user elements
		nickName = doc.createElement(name);
		doc.getChildNodes().item(0).getChildNodes().item(0).appendChild(nickName);
		// password elements
		password = doc.createElement("password");
		password.appendChild(doc.createTextNode(pass));
		nickName.appendChild(password);
	}

	public void ingresarPost(String name, String contenido){
		// user elements
		posteador = doc.createElement(name);
		doc.getChildNodes().item(0).getChildNodes().item(1).appendChild(posteador);
		// likes elements
		this.contenido = doc.createElement("contenido");
		this.contenido.appendChild(doc.createTextNode(contenido));
		posteador.appendChild(this.contenido);
	}
	
	public void ingresarComentario(String coment, String numPub){
		// likes elements
				idComentador = doc.createElement(numPub);
				doc.getChildNodes().item(0).getChildNodes().item(2).appendChild(idComentador);
				comentario = doc.createElement("comentario");
				comentario.appendChild(doc.createTextNode(coment));
				idComentador.appendChild(comentario);
	}

	public void ingresarImagen(String imagen){
		ruta= doc.createElement("ruta");
		ruta.appendChild(doc.createTextNode(imagen));
		posteador.appendChild(ruta);
	}
	


	public Document getDoc() {
		return doc;
	}


	public static ObjetoXml getInstance() {
		
		if(instance==null){
			instance=new ObjetoXml();
		}
		return instance;
	}

	
	
}
