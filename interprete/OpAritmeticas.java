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
	double op1d;
	double op2d;
	boolean isOp1 = false;
	boolean isOp2 = false;
	//Metodo que realiza la operacion: reta
	public double restar (){
		System.out.println("a restar " + op1d + " - " + op2d);
		return op1d - op2d;
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
    public double dividir() {
		System.out.println("a dividir " + op1d + "/" + op2d);
		return op1d/op2d;
	}
	public double suma() {
		System.out.println("a sumar " + op1d + "+" + op2d);
		return op1d+op2d;
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
		setOperators(op1, op2);
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
            	operando = dividir();
            break;
            //Si quiere multiplicar un numero con otro
            case "*": 
            	//operando = multiplicar(nums);
            break;
            //Si quiere sumar un numero con otro
            case "+": 
            	operando = suma();
            break;	
            //Si quiere restar un numero con otro
            case "-": 
            	operando = restar();
            	break;
		}
		System.out.println("El resultado de la aritmetica es: " + operando);
		return operando;
	}
	
	private void setOperators(Object op1, Object op2) {
		try {
			op1d = Double.parseDouble((String)op1);
		} catch(Exception e) {
			isOp1 = true;
			System.out.println("El operador 1 no es numero");
		}
		try {
			op2d = Double.parseDouble((String)op2);
		} catch(Exception e) {
			isOp2 = true;
			System.out.println("El operador 2 no es numero");
		}
		if (isOp1) {
			if (op1.toString().contains("[")) { //es una instruccion
				op1d = Double.parseDouble(String.valueOf(selectInstruction((ArrayList<Object>)op1)));
			} else { //es algo mas
				op1d = Double.parseDouble(searchVariable(op1.toString()).toString());
			}
		}
		if (isOp2) {
			if (op2.toString().contains("[")) { //es una instruccion
				op2d = Double.parseDouble(String.valueOf(selectInstruction((ArrayList<Object>)op2)));
			} else {//es algo mas
				op2d = Double.parseDouble(searchVariable(op1.toString()).toString());
			}
		}
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

	@Override
	public double restar(ArrayList<Double> numeros) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Function> getFunctions() {
		// TODO Auto-generated method stub
		return null;
	}
    
}

