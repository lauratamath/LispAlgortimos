import java.util.HashMap;
import java.util.List;

public class function{

	//Attributes
	private Object name;//Function's name
	private HashMap<Object,Object> parameter;//Hold function and content
	private Object content; //Function's instruction (content)

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
		name = function.get(0).toString();
		content = function.get(2).toString();
		//parameters are added
		parameter.put(name, content);
		
	}
	/**
	 * pre: call must no be empty 
	 * post: Read the function, and execute the already saved content
	 * @param call
	 * @return
	 */
	public Object function(List call) {
		return null;
	}
}