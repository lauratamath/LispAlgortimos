/**
 * 
 * Universidad del Valle de Guatemala
 * Algoritmos y estructuras de datos
 * Proyecto LISP
 * 
 * @author Julio Herrera	19402
 * @author Oliver de León	19270
 * @author Laurta Tamath	19365
 * @version 1.0
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

	/**
	 * 
	 * Este método es utilizado para leer el archivo datos.txt. La lectura se
	 * realiza para todas las líneas del archivo y separa cada caracter que tenga el
	 * archivo para agregarlo a la lista de Strings que devolverá.
	 * 
	 * @return una lista de todos los elementos del archivo separados
	 * @throws Exception excepción general para la lectura del archivo
	 */
	public static String textReader(String fileName) throws Exception {
		final String bar = File.separator;
		final String dir = System.getProperty("user.dir");
		/**
		 * AQUI SE LEE EL ARCHIVO TXT si no corre se debe de reemplazar en el parentesis
		 * (dir + barra +"NOMBRE DEL FOLDER EN DONDE ESTA EL PROYECTO" +barra+
		 * "datos.txt") El error del archivo de texto puede pasar si se corre el
		 * programa en eclipse y no en consola o tambien sucede al trabajar con paquetes
		 */
        final File file = new File(dir + bar + fileName);
		if (!file.exists()) {
			throw new FileNotFoundException("No se encontro el archivo, ver lineas comentadas");
		}
		FileReader fr;
		fr = new FileReader(file);
        final BufferedReader br = new BufferedReader(fr);
        String oneLine = "";
		String line = "";
		while ((line = br.readLine()) != null) {
            oneLine += line;
		}
		br.close();
		return oneLine;
	}

	public static void main(final String[] args) throws Exception {
		/**
		 * Menu de eleccion de archivo a leer
		 */
		Scanner scan = new Scanner(System.in);
		boolean isCorrect = false;
		String fileName = "";
		while (!isCorrect) {
            System.out.println("Escriba el nombre del archivo que va a leer");
            System.out.println("Si presiona solo enter se escoge por default ('programaPrueba.txt'): ");
            fileName = scan.nextLine();
            if (fileName.equals("")) {
                fileName = "programaPrueba.txt";
                isCorrect = true;
            } else {
                if (fileName.split(".").length < 1) {
                    isCorrect = false;
                    System.out.println("Indique la extension del archivo .txt");
                } else {
                    isCorrect = true;
                }
            }
		}
		/**
		 * Se lee el archivo de texto
		 */
        String program = textReader(fileName);
		System.out.println(program);
        /**
         * Se separa el programa para obtener las funciones
         */
		Runtime runtime = new Runtime(program);
		runtime.compile();
		/**
		 * Se pide al usuario que funcion quiere ejecutar
		 */
		scan.close();
	}
}