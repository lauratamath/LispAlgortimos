import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Function extends Instruction {

	public Function(Context context) {
		setContext(context);
	}

	/*class Param<K, V> {
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
	}*/

	//Attributes
	private String name;//Function's name
	private ArrayList<Variable<String, Object>> parameters = new ArrayList<>();
	private HashMap<String,Object> defun = new HashMap<>();//Hold function and content
	private List<ArrayList<Object>> content; //Function's instruction (content)
	private List<Object> nameParameter; //Parameter's name
	private Object parameter; //Function's parameter
	private String[] instr; //Store the functions  by parts
	private ArrayList<Double> num; //Store numbers
	private ArrayList<String> sign; //Store signs
	private Double result = 0.0; //Result
	private ArrayList<Object> condition; //Store the condition
	private ArrayList<Object> elseContent; //Store the else content


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
			this.parameters.add(new Variable<String,Object>((String)param, contParam));
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
	public Object execute(ArrayList<Object> call) {
		Object result = null;
		int contParam = 0;
		for (Object value : call) {
			for (Variable<String, Object> param : parameters) {
				if ((int)param.getValue() == contParam) {
					param.setValue(value);
					System.out.println("una vez");
					this.addToVariable(param);
				}
			}
		}
		System.out.println("en variables " + getVariables().get(0).getValue());
		for (ArrayList<Object> ins : content) { //por el momento solo devuelve un resultado
			result = selectInstruction(ins);
		}
		return result;
	}
	/**
	 * A conditional is submitted, contains conditions with their
	 * content and else executable content 
	 * @param condition
	 * @return
	 */
	public Object cond(ArrayList<ArrayList<Object>> statement) {
		condition = statement.get(0); //Store the conditional part
		elseContent = statement.get(1); //Store the else conditional part
		
	}
}