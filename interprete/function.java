import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class function{
	
	OpAritmeticas OA = new OpAritmeticas();

	//Attributes
	private Object name;//Function's name
	private HashMap<Object,Object> defun;//Hold function and content
	private Object content; //Function's instruction (content)
	private Object nameParameter; //Parameter's name
	private Object parameter; //Function's parameter
	private String[] instr; //Store the functions  by parts
	private ArrayList<Double> num; //Store numbers
	private ArrayList<String> sign; //Store signs

	/**
	 * pre: function must contain name, parameter and content
	 * post:The three attributes are saved
	 * 
	 * Disarm the list in 3 parts:	
	 * 1. Function's name
	 * 2. Function's parameters
	 * 3. Executable content
	 * @param function
	 */
	public void denifition(List function) {
		name = function.get(0);
		content = function.get(2).toString();
		//parameters are added
		defun.put(name, content);
		
	}
	/**
	 * pre: call must no be empty 
	 * post: Read the function, and execute the already saved content
	 * @param call
	 * @return
	 */
	public Object function(List call) {
		//Content is called
		String task = defun.get(call.get(0)).toString();
		parameter = call.get(1); //Parameter is stored
		task.replaceAll(nameParameter.toString(), parameter.toString());
		instr = task.split("");
		
		//Introduce all possible values
		for (int i = 0; i < instr.length; i++) {
			String current = instr[i];
			
			//Identify if it is a sign or a number
			//If it is a sign
			if (current.equalsIgnoreCase("+") || current.equalsIgnoreCase("-")
					|| current.equalsIgnoreCase("/") || current.equalsIgnoreCase("*"))
			{
				sign.add(current);
			}
			//If it is a number
			else{
				num.add(Double.parseDouble(current));
			}
			
			//Operates
			//If there are enough values to operate then...
			if(num.size() == 2) {
				
				String sign0 = sign.remove(sign.size()-1);
				Double result = 0.0;
				
				//Look for the proper operation
				switch(sign0){
				case "+":
					result = OA.sumar(num);
					break;
				case "-":
					result = OA.retar(num);
					break;
				case "/":
					result = OA.dividir(num);
					break;
				case "*":
					result = OA.multiplicar(num);
					break;
				}
				num.clear();
				num.add(result);
			}
		}
		
		

		return null;
	}
}