/**
* Universidad del Valle de Guatemala
* Algoritmos y estructuras de datos
* Proyecto LISP
* @author Laura Tamath 19365
* @author Julio Herrera 19402
* @author Oliver de León 19270
* @version 1; 18/03/2020
**/

import java.util.ArrayList;

/**
 * Un predicado no va a dar como contexto a sus hijos a si misma
 * va a dar a su contexto
 */

public class Predicates extends Instruction {

	public Predicates(Context context) {
		setContext(context);
	}

//Operaciones aritmeticas
	Object op1o;
	Object op2o;
	boolean isOp1 = false;
	boolean isOp2 = false;
	//Metodo que realiza la operacion: reta
	public boolean equal(){
        System.out.println("a comparar si " + op1o + " y " + op2o + " son iguales");
        return op1o.equals(op2o);
	}

    //Llamar el metodo del operando ingresado
    public boolean comparar(String operador, Object op1, Object op2){
		boolean result = false;
		setOperators(op1, op2);
    	switch(operador){
            case "=": 
            	result = equal();
            break;
		}
		System.out.println("El resultado de la comprobacion es: " + result);
		return result;
	}
	
	private void setOperators(Object op1, Object op2) {
        //TODO: por ahora solo esta comprobando numeros
		try {
			op1o = Integer.parseInt((String)op1);
		} catch(Exception e) {
			isOp1 = true;
			System.out.println("El operador 1 no es numero");
		}
		try {
			op2o = Integer.parseInt((String)op2);
		} catch(Exception e) {
			isOp2 = true;
			System.out.println("El operador 2 no es numero");
		}
		if (isOp1) {
			if (op1.toString().contains("[")) { //es una instruccion
				op1o = Integer.parseInt(String.valueOf(selectInstruction((ArrayList<Object>)op1)));
			} else { //es algo mas
				op1o = Integer.parseInt(searchVariable(op1.toString()).toString());
			}
		}
		if (isOp2) {
			if (op2.toString().contains("[")) { //es una instruccion
				op2o = Integer.parseInt(String.valueOf(selectInstruction((ArrayList<Object>)op2)));
			} else {//es algo mas
				op2o = Integer.parseInt(searchVariable(op1.toString()).toString());
			}
		}
	}
    
}

