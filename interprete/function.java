import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Function extends Instruction {

	public Function(Context context) {
		setContext(context);
	}

	class Param<K, V> {
		K key;
		V value;
		public Param(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		public K getKey() {
			return this.key;
		}

		public V getValue() {
			return this.value;
		}
	}

	//Attributes
	private String name;//Function's name
	private ArrayList<Param<String, Object>> parameters = new ArrayList<>();
	private HashMap<String,Object> defun = new HashMap<>();;//Hold function and content
	private List<ArrayList<Object>> content; //Function's instruction (content)
	private List<Object> nameParameter; //Parameter's name
	private Object parameter; //Function's parameter
	private String[] instr; //Store the functions  by parts
	private ArrayList<Double> num; //Store numbers
	private ArrayList<String> sign; //Store signs
	private Double result = 0.0; //Result

	public String getName() {
		return this.name;
	}
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
	public void denifition(String name, ArrayList<Object> params, ArrayList<ArrayList<Object>> content) {
		this.name = name;
		this.nameParameter = params;
		int contParam = 0;
		for (Object param: params) {
			this.parameters.add(new Param((String)param, contParam));
			contParam++;
		}
		this.content = content;
		defun.put(name, content);
	}
	/**
	 * pre: call must no be empty 
	 * post: Read the function, and execute the already saved content
	 * @param call
	 * @return
	 */
	public String execute(ArrayList<Object> call) {
		int contParam = 0;
		for (Object value : call) {
			for (Param<String, Object> param : parameters) {
				if ((int)param.getValue() == contParam) {
					param.setValue(value);
				}
			}
		}
		for (ArrayList<Object> ins : content) {
			selectInstruction(ins);
		}



		/*//Content is called
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
		}*/
		return result.toString();
	}
}