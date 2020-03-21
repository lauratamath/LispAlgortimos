import java.util.ArrayList;

abstract class Instruction implements Context {
    Context context;
    
    ArrayList<Variable<String, Object>> variables = new ArrayList<>();
    ArrayList<Function> functions = new ArrayList<>();

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Object selectInstruction(ArrayList<Object> ins) {
        Object result = null;
        String operation = (String)(ins.get(0));
        if (operation.equals("/") || operation.equals("-") || operation.equals("+")) {
            System.out.println("Se hara una operacion aritmetica");
            OpAritmeticas aritmeticOp = new OpAritmeticas(this); //se le pone a la operacion el contexto del padre
            result = aritmeticOp.operar(operation, ins.get(1), ins.get(2));
        } else if (operation.equals("COND")) {
            System.out.println("Se va a operar un Cond");
            Cond cond = new Cond(this);
            result = cond.doBlocks((ArrayList<Object>)ins.get(1), (ArrayList<Object>)ins.get(2));
        } else {
            System.out.println("Se verifica si la operacion es una funcion");
            searchFunction(ins);
        }
        return result;
    }

    public Object selectInstruction(ArrayList<Object> ins, Context context) {
        Object result = null;
        String operation = (String)(ins.get(0));
        if (operation.equals("/") || operation.equals("-") || operation.equals("+")) {
            System.out.println("Se hara una operacion aritmetica");
            OpAritmeticas aritmeticOp = new OpAritmeticas(context); //se le pone a la operacion el contexto del padre
            result = aritmeticOp.operar(operation, ins.get(1), ins.get(2));
        } else if (operation.equals("COND")) {
            System.out.println("Se va a operar un Cond");
            Cond cond = new Cond(context);
            result = cond.doBlocks((ArrayList<Object>)ins.get(1), (ArrayList<Object>)ins.get(2));
        } else {
            System.out.println("Se verifica si la operacion es una funcion");
            searchFunction(ins);
        }
        return result;
    }

    public Object selectInstruction(Object value) {
        Object result = null;
        try {
            Integer.valueOf(value.toString());
            result = value;
        } catch(Exception e) {
            System.out.println("El valor no es un numero");
        }
        return result;
    }

    public Object searchVariable(String name) {
        for (Variable<String,Object> variable : getVariables()) {
            if (variable.getKey().equals(name)) {
                System.out.println("Se encontro la variable " + variable.getKey() + " con el valor: " + variable.getValue());
                return variable.getValue();
            }
        }
        System.out.println("No se encontraron variables, se buscaran en el contexto padre");
        return getContext().searchVariable(name);
    }

    public Object searchFunction(ArrayList<Object> ins) {
        for (Function function : functions) {
            if (function.getName().equals(String.valueOf(ins.get(0)))) {
                return function.execute((ArrayList<Object>)(selectInstruction((ArrayList<Object>)(ins.get(1)))));
            }
        }
        System.out.println("No se encontraron funciones, se buscaran en el contexto padre");
        return getContext().searchFunction(ins);
    }

    public ArrayList<Variable<String, Object>> getVariables() {
        return this.variables;
    }

    public ArrayList<Function> getFunctions() {
        return this.functions;
    }

    public void addToVariable(Variable<String, Object> variable) {
        System.out.println("Se agrega la variable " + variable.getKey() + " con valor: " + variable.getValue());
        Variable<String,Object> toAdd = new Variable<String,Object>(variable.getKey(), variable.getValue());
        this.variables.add(toAdd);
    }

    public void addToFunctions(Function function) {
        System.out.println("Se agrega la funcion " + function.getName() + " a las funciones");
        this.functions.add(function);
    }

    public void setVariables(ArrayList<Variable<String, Object>> variables) {
        this.variables = variables;
    }
    
}