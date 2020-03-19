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
 * la operacion no va a dar como contexto a sus hijos a si misma
 * va a dar a su contexto
 */

public class OpAritmeticas extends Instruction implements IOperaciones{

	public OpAritmeticas(Context context) {
		setContext(context);
	}

//Operaciones aritmeticas
	//Metodo que realiza la operacion: reta
	public double restar (ArrayList<Double> numeros){
		double resta = numeros.get(0);
		if (numeros.size() == 1){
		resta =- resta;
		} else{
			for(int i=1; i<numeros.size(); i ++){
				resta =+ numeros.get(i);
			}
		}
		return resta;
	}
	//Metodo que realiza la operacion: suma
	public double sumar(ArrayList<Double> numeros){
		double suma =0;
		if(numeros.size()== 2){
			suma = numeros.get(0);
		}else{
			for (double numeros1: numeros){
				suma += numeros1;
			}
		}
		return suma;
	}
	//Metodo que realiza la operacion: multiplicar
	 public double multiplicar( ArrayList<Double> numeros )  {
    	double multiplica = 1;
        if ( numeros.size()==1 ) {
        	multiplica = numeros.get(0);
        }
        else {
            for (double num: numeros) {
                multiplica*= num;
            }
        }
        return multiplica;
    }
    //Metodo que realiza la operacion: dividir
    public double dividir(Object op1, Object op2) {
		double division = 0;
		System.out.println("param1 " + op1);
		System.out.println("param2 " + op2);
        /*if ( numeros.size() != 1 ){
            for (int i=1; i<numeros.size(); i++) {
                division/= numeros.get(i);
            }
        } else {
        	return 1/division;
        }*/
        return Math.round((division)*1000)/1000;
    }
    //Metodo para comparar el mayor
    private double mayor( ArrayList<Double> numeros ) {
        if ( numeros.size() == 1 ) {
        	return 1;
        } else {
            for ( int i=0; i < numeros.size() - 1; i++) {
                if ( numeros.get(i) < numeros.get(i+1) ) {
                	return 0;
                }
            }
        }
        return 1;
    }
     //Metodo para comparar numeros iguales
     private double igual ( ArrayList<Double> numeros ) {
        if ( numeros.size() == 1 ) { 
        	return 1;
        } else {
            for (int i=0; i<numeros.size()-1; i++) {
                if ( !numeros.get(i).equals(numeros.get(i+1)) ) {
                	return 0;
                }
            }
        }
        return 1;
    }
    //Metodo para comparar el menor
    private double menor( ArrayList<Double> numeros ) {
        if ( numeros.size()==1 ) {
        	return 1;
        } else {
            for ( int i=0; i < numeros.size() - 1 ; i++ ) {
                if ( numeros.get(i) > numeros.get(i+1) ) {
                	return 0;
                }
            }
        }
        return 1;
    }

    //Llamar el metodo del operando ingresado
    public double operar(String operador, Object op1, Object op2){
    	double operando = 0;
    	switch(operador){
			//estos creo que no van aqui, son cosas diferentes
    		/*//Si quiere comparar si un numero es menor a otro
    		case "<":
	    		operando = menor(nums);
	            break;
	         //Si quiere comparar si un numero es igual a otro
	    	 case "=": 
            	operando = igual(nums);
            	break;
            //Si quiere comparar si un numero es mayor a otro
           	case ">": 
            	operando = mayor(nums);
            	break;
            //Si quiere divir un numero con otro*/
            case "/": 
            	operando = dividir(op1, op2);
            	break;
            //Si quiere multiplicar un numero con otro
            case "*": 
            	//operando = multiplicar(nums);
            	break;
            //Si quiere sumar un numero con otro
            case "+": 
            	//operando = sumar(nums);
            	break;	
            //Si quiere restar un numero con otro
            case "-": 
            	//operando = restar(nums);
            	break;
		}
		return operando;
    }
	@Override
	public double operaciones(String operacion, ArrayList<Double> num) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double dividir(ArrayList<Double> numeros) {
		// TODO Auto-generated method stub
		return 0;
	}
    
}

