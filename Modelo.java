package PrimerTrimestre.AE2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Modelo {

	Modelo() {
	}

	/**
	 * Envía las columnas de la base de datos, se utiliza para 
	 * cuando se hace una consulta mostrar las columnas y 
	 * que se vea la informacion más clara
	 * 
	 * @param usuario		Le pasamos el usuario para conectarse a la base de datos
	 * @param contraseña	Le pasamos la contraseña para conectarse a la base de datos
	 * @param consulta		Le pasamos las columnas que queremos seleccionar
	 * @param tabla			Le pasamos la tabla que queremos consultar
	 * @param where			Le pasamos las condiciones del resultado
	 * 
	 * @return				Retorna un String con las columnas separadas con un \t para que se vea más claro
	 */
	public static String getColumnasBaseDatos(String usuario, String contraseña, String consulta, String tabla,
			String where) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", usuario, contraseña);
			Statement stmt = con.createStatement();
			if (!where.equals("")) {
				where = "WHERE " + where;
			}
			if (tabla.equals("")) {
				tabla = "population";
			}
			if (consulta.equals("")) {
				consulta = "*";
			}
			ResultSet rs = stmt.executeQuery("SELECT " + consulta + " FROM " + tabla + " " + where + ";");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columCount = rsmd.getColumnCount();
			String columnes = "";
			for (int i = 1; i <= columCount; i++) {
				columnes += rsmd.getColumnName(i) + "\t";
			}
			rs.close();
			stmt.close();
			con.close();
			return columnes;

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	/**
	 * Se conecta a la base de datos, verifica que el usuario que queremos crear no
	 * existe y si no existe añade la tabla users el usuario, la contraseña
	 * codificada y el tipo cliente. 
	 * Luego crea el usuario con la contraseña codificada y le da permisos 
	 * de lectura de la tabla population.
	 * 
	 * @param usuarioAdmin    Le pasamos el usuario para conectarse a la base de datos
	 * @param contraseñaAdmin Le pasamos la contraseña para conectarse a la base de datos
	 * @param nuevoUsuario    Le pasamos el nombre que tendra el nuevo usuario
	 * @param nuevoPassword   Le pasamos la contraseña que tendra el nuevo usuario
	 * 
	 * @return Retorna un true si se crea el usuario nuevo y false si hay algún  error
	 */
	public static boolean crearUsuario(String usuarioAdmin, String contraseñaAdmin, String nuevoUsuario,
			String nuevoPassword) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", usuarioAdmin,
					contraseñaAdmin);
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE login = '" + nuevoUsuario + "';");
			if (rs.next()) {
				return false;
			} else {
				nuevoPassword = hashMD5(nuevoPassword);
				PreparedStatement psAfegir = con
						.prepareStatement("INSERT INTO `users` (`login`, `password`, `type`) VALUES (?,?,?);");

				psAfegir.setString(1, nuevoUsuario);
				psAfegir.setString(2, nuevoPassword);
				psAfegir.setString(3, "client");

				psAfegir.executeUpdate();

				stmt.executeUpdate("CREATE USER '" + nuevoUsuario + "' IDENTIFIED BY '" + nuevoPassword + "'");
				stmt.executeUpdate("GRANT SELECT ON population.population TO '" + nuevoUsuario + "';");
				stmt.executeUpdate("FLUSH PRIVILEGES;");

				stmt.close();
				con.close();
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	/**
	 * Se conecta a la base de datos y hace una consulta en la tabla users 
	 * buscando por nombre y contraseña, si encuentra un usuario retorna 
	 * el tipo de usuario
	 * 
	 * @param user		El usuario que buscamos
	 * @param password	La contraseña del usuario que buscamos
	 * 
	 * @return			El tipo de usuario que encuentra
	 */
	public static String iniciarSesion(String user, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", "root", "");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT type FROM users WHERE login = '" + user + "' AND password = '" + hashMD5(password) + "';");

			if (!rs.next()) {
				rs.close();
				stmt.close();
				con.close();
				return "error";
			}
			String contenido = rs.getString(1);

			rs.close();
			stmt.close();
			con.close();
			return contenido;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	/**
	 * Se conecta a la base de datos y hace una consulta SELECT, nos retorna el valor
	 * de las tablas
	 * 
	 * @param usuario		Le pasamos el usuario para conectarse a la base de datos
	 * @param contraseña	Le pasamos la contraseña para conectarse a la base de datos
	 * @param consulta		Le pasamos las columnas que queremos seleccionar
	 * @param tabla			Le pasamos la tabla que queremos consultar
	 * @param where			Le pasamos las condiciones del resultado
	 * 
	 * @return				Retorna el valor de la consulta SELECT
	 */
	public static String consultaSelect(String usuario, String contraseña, String consulta, String tabla,
			String where) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", usuario, contraseña);
			Statement stmt = con.createStatement();
			if (!where.equals("")) {
				where = "WHERE " + where;
			}
			if (tabla.equals("")) {
				tabla = "population";
			}
			if (consulta.equals("")) {
				consulta = "*";
			}
			ResultSet rs = stmt.executeQuery("SELECT " + consulta + " FROM " + tabla + " " + where + ";");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columCount = rsmd.getColumnCount();
			String contenido = "";
			while (rs.next()) {

				for (int i = 1; i <= columCount; i++) {
					contenido += rs.getString(i) + "\t";
				}
				contenido += "\n";
			}

			rs.close();
			stmt.close();
			con.close();
			return contenido;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	/**
	 * Lee el fichero CSV y crea un fichero XML por cada fila del archivo CSV,
	 * las propiedades de los XML son la primera fila del CSV, cada XML obtiene 
	 * el nombre del primer valor del CSV, en este caso el nombre del país.
	 */
	public static void CSVaXML() {
		try {
			FileReader fr = new FileReader("./AE02_population.csv", StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(fr);
			String linea = br.readLine();
			String[] columnas = linea.split(";");
			String[] valores;

			linea = br.readLine();

			while (linea != null) {
				valores = linea.split(";");
				DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
				DocumentBuilder build = dFact.newDocumentBuilder();
				Document doc = build.newDocument();
				Element raiz = doc.createElement("populations");
				doc.appendChild(raiz);
				Element element = doc.createElement("population");
				raiz.appendChild(element);
				for (int i = 0; i < valores.length; i++) {
					Element elemento = doc.createElement(columnas[i]);
					elemento.appendChild(doc.createTextNode(valores[i]));
					element.appendChild(elemento);
				}
				TransformerFactory tranFactory = TransformerFactory.newInstance();
				Transformer aTransformer = tranFactory.newTransformer();
				aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
				aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				try {
					FileWriter fw = new FileWriter("./src/PrimerTrimestre/AE2/xml/" + valores[0] + ".xml");
					StreamResult result = new StreamResult(fw);
					aTransformer.transform(source, result);
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				linea = br.readLine();
			}
			fr.read();
			br.close();
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Lee todos los ficheros XML y los pasa a String, separados por una 
	 * fila de asteriscos para que quede mas claro
	 * 
	 * @return	String de todos los XML
	 */
	public static String XMLaString() {
		try {
			File carpetaXML = new File("./src/PrimerTrimestre/AE2/xml");
			String todosXML = "";
			String[] listaArchivos = carpetaXML.list();
			for (int i = 0; i < listaArchivos.length; i++) {
				File xml = new File(carpetaXML, carpetaXML.list()[i]);
				FileReader fr = new FileReader(xml, StandardCharsets.UTF_8);
				int valor = fr.read();
				while (valor != -1) {
					todosXML += (char) valor;
					valor = fr.read();
				}
				todosXML += "*************************************************" + "\n";
				fr.close();
			}
			return todosXML;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Se conecta a la base de datos, llama a la función  crearTablaDesdeCSV(), 
	 * borra el contenido de la tabla population y llama a la función  
	 * leerXMLyLlenarBD()
	 * 
	 * @param usuario		Le pasamos el usuario para conectarse a la base de datos
	 * @param contraseña	Le pasamos la contraseña para conectarse a la base de datos
	 */
	public static void CrearLlenarBaseDatos(String user, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", user, password);
			Statement stmt = con.createStatement();

			int crearTabla = stmt.executeUpdate(crearTablaDesdeCSV());
			int resultadoVaciar = stmt.executeUpdate("DELETE FROM `population`");
			leerXMLyLlenarBD();

			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Lee el CSV y crea una consulta a la base de datos para crear una tabla 
	 * population con los valores de la primera fila del CSV como columnas
	 * 
	 * @return	Retorna un String con la consulta 
	 */
	public static String crearTablaDesdeCSV() {
		try {
			String csv = "./AE02_population.csv";
			FileReader fr = new FileReader(csv);
			BufferedReader br = new BufferedReader(fr);
			String linea = br.readLine();
			String[] columnas = linea.split(";");
			String sentencia = "CREATE TABLE IF NOT EXISTS `population` (";
			for (int i = 0; i < columnas.length; i++) {
				sentencia += "`" + columnas[i] + "`" + "VARCHAR(30) NOT NULL DEFAULT '',";
			}
			return sentencia += "PRIMARY KEY (`" + columnas[0] + "`) )ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;";
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	/**
	 * Le mandamos un array, y prepara una consulta INSERT añadiendo como 
	 * columnas cada valor de el array
	 * 
	 * @param columnas	Array con las columnas de la tabla de la base de datos
	 * 
	 * @return			Consulta INSERT preparada para meterle los valores
	 */
	public static String prepararSentenciaInsert(String[] columnas) {
		String sentencia = "INSERT INTO `population` (";
		for (int i = 0; i < columnas.length - 1; i++) {
			sentencia += "`" + columnas[i] + "`, ";
		}

		sentencia += columnas[columnas.length - 1] + ") VALUES (";
		for (int i = 0; i < columnas.length - 1; i++) {
			sentencia += "?,";
		}
		return sentencia += "?);";
	}

	/**
	 * Lee los ficheros XML, y por cada fichero se conecta a la base de 
	 * datos si no está conectado y llama a la función 
	 * prepararSentenciaInsert() y le mete los valores del XML
	 * a la consulta preparada
	 */
	private static void leerXMLyLlenarBD() {
		try {
			Connection con = null;
			PreparedStatement psAfegir = null;
			File carpetaXML = new File("./src/PrimerTrimestre/AE2/xml");
			String[] listaArchivos = carpetaXML.list();
			for (int i = 0; i < listaArchivos.length; i++) {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document document = dBuilder.parse(new File(carpetaXML, listaArchivos[i]));
				Element raiz = document.getDocumentElement();
				NodeList nodeList = document.getElementsByTagName("population");
				Node node = nodeList.item(0);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					NodeList childNodes = eElement.getChildNodes();
					List<String> columnas = new ArrayList<>();
					List<String> valores = new ArrayList<>();

					for (int j = 0; j < childNodes.getLength(); j++) {
						Node childNode = childNodes.item(j);
						if (childNode.getNodeType() == Node.ELEMENT_NODE) {
							columnas.add(childNode.getNodeName());
							valores.add(childNode.getTextContent());
						}
					}
					if (con == null) {
						Class.forName("com.mysql.cj.jdbc.Driver");
						con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", "root", "");
					}
					Statement stmt = con.createStatement();
					String[] arrayColumnas = new String[columnas.size()];
					for (int l = 0; l <= columnas.size() - 1; l++) {
						arrayColumnas[l] = columnas.get(l);
					}
					String sentencia = prepararSentenciaInsert(arrayColumnas);
					psAfegir = con.prepareStatement(sentencia);
					for (int l = 0; l <= valores.size() - 1; l++) {
						psAfegir.setString(l + 1, valores.get(l));
					}
					int resultado = psAfegir.executeUpdate();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Guarda un archivo CSV con el resultado de la consulta SELECT que hemos 
	 * realizado anteriormente.
	 * 
	 * @param columnas		String con las columnas de la consulta SELECT
	 * @param resultado		String con los valores de la consulta SELECT
	 * @param carpetaInput	File de la dirección donde queremos guardar el CSV
	 */
	public static void pasarConsultaCSV(String columnas, String resultado, File carpetaInput) {
		try {
			String csv = carpetaInput.getPath() + "/consulta.csv";
			FileWriter fw = new FileWriter(csv, StandardCharsets.UTF_8);
			BufferedWriter bw = new BufferedWriter(fw);
			String resultadoFinal = columnas + "\n" + resultado;

			String[] fila = resultadoFinal.split("\n");
			for (int i = 0; i < fila.length; i++) {
				String[] palabra = fila[i].split("\t");
				for (int j = 0; j < palabra.length; j++) {
					bw.write(palabra[j] + ";");
				}
				bw.write("\n");
			}

			bw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Le pasamos la contraseña, la codifica a hash MD5 para que sea más segura
	 * 
	 * @param password Le mandamos la contraseña que queremos codificar
	 * 
	 * @return Nos devuelve la contraseña codificada
	 */
	public static String hashMD5(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();

			for (byte b : digest) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
}
