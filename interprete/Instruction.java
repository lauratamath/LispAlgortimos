import java.util.ArrayList;

abstract class Instruction implements Context {
    Context context;
    
    ArrayList<Variable<String, Object>> variables = new ArrayList<>();

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Object selectInstruction(ArrayList<Object> ins) {
        Object result = null;
        String operation = (String)(ins.get(0));
        if (operation.equals("/") || operation.equals("-")) {
            OpAritmeticas aritmeticOp = new OpAritmeticas(this); //se le pone a la operacion el contexto del padre
            result = aritmeticOp.operar(operation, ins.get(1), ins.get(2));
        }
        return result;
    }

    public Object selectInstruction(ArrayList<Object> ins, Context context) {
        Object result = null;
        String operation = (String)(ins.get(0));
        if (operation.equals("/") || operation.equals("-")) {
            OpAritmeticas aritmeticOp = new OpAritmeticas(context); //se le pone a la operacion el contexto del padre
            result = aritmeticOp.operar(operation, ins.get(1), ins.get(2));
        }
        return result;
    }

    public Object searchVariable(String name) {
        for (Variable<String,Object> variable : getContext().getVariables()) {
            if (variable.getKey().equals(name)) {
                System.out.println("encontroooo " + variable.getValue());
                return variable.getValue();
            }
        }
        return null;
    }

    public ArrayList<Variable<String, Object>> getVariables() {
        return this.variables;
    }

    public void addToVariable(Variable<String, Object> variable) {
        System.out.println(variable.getKey() + " - " + variable.getValue());
        Variable<String,Object> toAdd = new Variable<String,Object>(variable.getKey(), variable.getValue());
        this.variables.add(toAdd);
    }
    
}