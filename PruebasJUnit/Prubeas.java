/**
* Universidad del Valle de Guatemala
* Algoritmos y estructuras de datos
* Proyecto LISP
* @author Laura Tamath 19365
* @author Julio Herrera 19402
* @author Oliver de León 19270
* @version 1; 19/03/2020
* @version 2; 20/03/2020
* Interface que define todas las operaciones aritmeticas
**/

import static org.junit.Assert.*;
import org.junit.Test;

public class Pruebas{

	@Test
	public void testSuma(){
		OpArtimeticas test1 = new OpArtimeticas();
		//Los numeros que se sumaran
		double op1 = 4.1
		double op2 = 8.0
		//El resultado que se espera
		double resultado = test1.sumar(op1, op2);
		assertEquals(12.1, resultado);
	}

	@Test
	public void testMulti(){
		OpArtimeticas test2 = new OpArtimeticas();
		//Numero a multiplicar
		double operando1 = 5
		double operando2 = 5.5
		//Resultado esperado
		double resultado = test2.multiplicar(operando2, operando1);
		assertEquals(27.5, resultado);
	}

	@Test
	public void testResta(){
		OpArtimeticas test3 = new OpArtimeticas();
		//Los numeros que se restaran
		double op1 = 8.0
		double op2 = 4.1
		//El resultado que se espera
		double resultado = test1.restar(op1, op2);
		assertEquals(3.9, resultado);
	}

	@Test
	public void testDivision(){
		OpArtimeticas test4 = new OpArtimeticas();
		//Numero a dividir
		double operando1 = 16
		double operando2 = 2
		//Resultado esperado
		double resultado = test2.dividir(operando1, operando2);
		assertEquals(8, resultado);
	}

	@Test
	public void separar(){
		Runtime test5 = new Runtime();
		String uno = "hola ";
		String dos = "como ";
		String tres = "estas? ";
		String resultadp = test5.separateCommand(uno, dos, tres);
		assertEquals("hola ", "como", "estas, resultadp");
	}

}