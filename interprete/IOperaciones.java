/**
* Universidad del Valle de Guatemala
* Algoritmos y estructuras de datos
* Proyecto LISP
* @author Laura Tamath 19365
* @author Julio Herrera 19402
* @author Oliver de León 19270
* @version 1; 18/03/2020
* Interface que define todas las operaciones aritmeticas
**/
import java.util.ArrayList;

public interface IOperaciones{

	/**
	* @param operacion para indicar cual operacion se utilizara
	* @param num arraylist con todos los numeros
	pre: recive los dos parametros
	post: retorna resultado
	**/
	public double operaciones(String operacion, ArrayList<Double> num);

	/**
	@param numeros los que seran operados
	pre: recibe los numeros que se restaran
	post: retorna el resultado
	**/
	public double restar (ArrayList<Double> numeros);

	/**
	@param numeros los que seran operados
	pre: recibe los numeros que se sumaran
	post: retorna el resultado
	**/
	public double sumar (ArrayList<Double> numeros);

	/**
	@param numeros los que seran operados
	pre: recibe los numeros que se multiplicaran
	post: retorna el resultado
	**/
	public double multiplicar (ArrayList<Double> numeros);

	/**
	@param numeros los que seran operados
	pre: recibe los numeros que se dividiran
	post: retorna el resultado
	**/
	public double dividir (ArrayList<Double> numeros);
}