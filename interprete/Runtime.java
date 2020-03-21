import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Runtime implements Context {

    Context context = this;
    ArrayList<Variable<String, Object>> variables = new ArrayList<>();
    ArrayList<Function> functions = new ArrayList<>();

    /**
     * String que contiene el programa completo para separar
     */
    private String program;

    public Runtime(String program) {
        this.program = program;
    }

    /**
     * para ejecutar un comando del usuario
     */
    public void executeCommand(String command) {
        separateCommand(command);
        String name = (String) separateCommand(command).get(0);
        ArrayList<Object> params = (ArrayList<Object>) separateCommand(command).get(1); // unsafe cast
        for (Function function : compiledFunctios) {
            if (function.getName().equals(name)) {
                System.out.println(function.execute(params));
            }
        }
    }

    /**
     * Aqui se deben de pasar las instrucciones a sus respectivas listas
     */
    public void compile() {
        split();
        for (ArrayList<Object> function : funcs) {
            Function newFunction = new Function(context);
            System.out.println("La funcion es: " + function);
            newFunction.denifition(
                (String) function.get(0),
                (ArrayList<Object>) function.get(1),
                (ArrayList<ArrayList<Object>>) function.get(2)
            );
            compiledFunctios.add(newFunction);
        }
        for (ArrayList<Object> refFunction : reffuncs) {
            for (Function function : compiledFunctios) {
                if (String.valueOf(refFunction.get(0)).equals(function.getName())) {
                    functions.add(function);
                }
            }
        }
    }

    ArrayList<Function> compiledFunctios = new ArrayList<>();

    /**
     * Aqui se separa el string y se leen cada de cuantas cosas va a haber Siguen
     * siendo solo strings
     */
    /**
     * listas que guardan las operaciones ya separadas
     */
    ArrayList<ArrayList<Object>> funcs = new ArrayList<>();
    ArrayList<ArrayList<Object>> aritmetics = new ArrayList<>();
    ArrayList<ArrayList<Object>> conds = new ArrayList<>();
    ArrayList<ArrayList<Object>> predicates = new ArrayList<>();
    ArrayList<ArrayList<Object>> reffuncs = new ArrayList<>();
    int contClosed = 0;
    /**
     * variables necesarias para separar
     */
    Stack<Integer> contDefun = new Stack<>(); // guarda la posicion de la funcion en "funcs"
    Stack<Integer> contOp = new Stack<>(); // guarda la posicion de la operacion en "aritmetics"
    Stack<Integer> contCond = new Stack<>(); // guarda la posicion del cond en "conds"
    Stack<Integer> contPred = new Stack<>(); // guarda la posicion del predicado en "predicates"
    Stack<Integer> contRefDefun = new Stack<>(); // guarda la posicion de la referencia de funcion
    Stack<String> contIns = new Stack<>(); // guarda el nombre de la instruccion
    /**
     * instruccion actual
     */
    ArrayList<Object> actual = new ArrayList<>();

    /**
     * separar por parentesis iniciales
     */
    public void split() {
        System.out.println("--------se empieza a separar el string-----------------");
        System.out.println("se separa por (");
        String[] starting = program.split("\\(");
        for (String string : starting) {
            if (string.replaceAll(" ", "").length() != 0) {
                System.out.println("se manda a separar: " + string);
                selectTasks(string);
            }
        }
        /**
         * TODO: verificar si siempre una funcion va a necesitar que se le pasen las
         * operaciones
         */
        System.out.println("----------se termina de separar el string--------------------");
        /*
         * System.out.println("---------Listas---------");
         * System.out.println("funcs: " + funcs);
         * System.out.println("aritmetics: " + aritmetics); System.out.println("conds: "
         * + conds); System.out.println("predicates: " + predicates);
         * System.out.println("reffuncs: " + reffuncs);
         * System.out.println("---------Contadores-------");
         * System.out.println("funcs: " + contDefun);
         * System.out.println("aritmetics: " + contOp); System.out.println("conds: " +
         * contCond); System.out.println("predicates: " + contPred);
         * System.out.println("reffuncs: " + contRefDefun);
         * System.out.println("instructions: " + contIns);
         */
    }

    /**
     * a partir del primer split, se separa cada vez por parentesis finales pero se
     * tiene este metodo aparte para aplicar recursividad
     */
    public void selectTasks(String string) {
        if (string.replaceAll(" ", "").replaceAll("\\)", "").substring(0, 1).equals(";")) {
            System.out.println("Se encontro un comentario");
        } else {
            if (string.contains("DEFUN")) {
                System.out.println("se encontro un defun");
                contIns.add("DEFUN");
                string = string.replaceAll("DEFUN", "");
                string = string.replaceAll(" ", "");
                actual.add(string);
                System.out.println("se ingresa el nombre " + string + " a la funcion");
                funcs.add(actual);
                System.out.println("se agrego la funcion a las funciones");
                contDefun.add(funcs.size() - 1);
                actual = new ArrayList<>();
            } else if (string.contains("+")) {
                foundAritmetic(string, "+", "suma");
            } else if (string.contains("/")) {
                foundAritmetic(string, "/", "division");
            } else if (string.contains("-")) {
                foundAritmetic(string, "-", "resta");
            } else if (string.contains("COND")) {
                System.out.println("se encontro un COND");
                contIns.add("COND");
                actual.add("COND");
                actual.add(new ArrayList<ArrayList<Object>>());
                conds.add(actual);
                System.out.println("se agrego un nuevo Cond a los conds");
                contCond.add(conds.size() - 1);
                actual = new ArrayList<>();
            } else if (string.contains("=")) {
                System.out.println("se encontro un =");
                contIns.add("EQUAL");
                if (string.split(" ").length > 2) {
                    System.out.println("se encontraron 2 parametros siguientes para el equals");
                    actual.add("=");
                    actual.add(string.split(" ")[1]);
                    actual.add(string.split(" ")[2].replaceAll("\\)", ""));
                    contIns.remove(contIns.size() - 1);
                    if (contIns.size() > 0) {
                        if (contIns.get(contIns.size() - 1).equals("COND")) {
                            System.out.println("hay un COND pendiente");
                            if (conds.get(contCond.get(contCond.size() - 1)).size() == 2) { // solo tiene los predicados
                                if (contClosed == 0) {
                                    System.out.println("Se agrego un nuevo bloque al COND");
                                    ((ArrayList<Object>) (conds.get(contCond.get(contCond.size() - 1)).get(1)))
                                            .add(new ArrayList<Object>());
                                }
                                ArrayList<Object> blocks = (ArrayList<Object>) (conds
                                        .get(contCond.get(contCond.size() - 1)).get(1));
                                ArrayList<Object> block = (ArrayList<Object>) blocks.get(blocks.size() - 1);
                                if (block.size() == 0) {
                                    System.out.println("Al bloque le falta su predicado");
                                    contClosed += 2;
                                    block.add(actual);
                                    System.out.println("Se agrego el Equals como predicado al bloque");
                                    actual = new ArrayList<>();
                                } else if (contClosed != 0) {
                                    System.out.println("Al bloque le faltan sus instrucciones");
                                    contClosed++;
                                    block.add(actual);
                                    actual = new ArrayList<>();
                                    System.out.println("Se agrego el Equals como instruccion del bloque");
                                }
                            } else { // ya va para el else
                                // TODO: si se encuentra un Equals dentro de un cond que ya va por su else
                            }
                        } else {
                            predicates.add(actual);
                            System.out.println("se agrego el Equals a los predicados");
                            actual = new ArrayList<>();
                        }
                    }
                }
            } else if (string.contains("T ") || string.contains(" T ")) {
                System.out.println("se encontro una T");
                if (contIns.get(contIns.size() - 1).equals("COND")) {
                    System.out.println("la actual instruccion es un Cond");
                    if (conds.get(contCond.get(contCond.size() - 1)).size() == 2) { // solo tiene los predicados
                        if (contClosed > 0) {
                            ArrayList<Object> blocks = (ArrayList<Object>) (conds.get(contCond.get(contCond.size() - 1))
                                    .get(1));
                            ArrayList<Object> block = (ArrayList<Object>) blocks.get(blocks.size() - 1);
                            System.out.println("Al bloque le faltan sus instrucciones");
                            block.add(string);
                            System.out.println("Se agrego el T como parte del bloque");
                        } else {
                            conds.get(contCond.get(contCond.size() - 1)).add(new ArrayList<Object>());
                        }
                    } else { // ya va para el else
                        // TODO: si se encuentra una T en las instrucciones del else
                    }
                }
            } else if (string.replaceAll(" ", "").length() != 0) {
                System.out.println("lo que se encontro en " + string + " no fue una instruccion");
                if (string.split("\\)").length == 1) {
                    string = string.split(" ")[string.split(" ").length - 1];
                    System.out.println("Se comprueba solo " + string);
                } else {
                    string = string.split("\\)")[0];
                }
                verifyIfIsFunction(string);
                if (contIns.get(contIns.size() - 1).equals("DEFUN")) {
                    System.out.println("la actual instruccion es una funcion");
                    if (funcs.get(contDefun.get(contDefun.size() - 1)).size() == 1) {
                        System.out.println("la funcion necesita sus parametros");
                        String[] params = string.split(","); // revisar si separa bien
                        ArrayList<String> paramsList = new ArrayList<>();
                        for (String param : params) {
                            param = param.replaceAll(" ", "");
                            param = param.replaceAll("\\)", "");
                            System.out.println("se agrego el parametro " + param + " a la funcion");
                            paramsList.add(param);
                        }
                        funcs.get(contDefun.get(contDefun.size() - 1)).add(paramsList);
                    }
                } else if (contIns.get(contIns.size() - 1).equals("ARITMETIC")) {
                    System.out.println("la actual instruccion es una operacion aritmetica");
                    if (aritmetics.get(contOp.get(contOp.size() - 1)).size() == 2) {
                        System.out.println("la operacion necesita su segundo parametro");
                        aritmetics.get(contOp.get(contOp.size() - 1)).add(string);
                        System.out.println("se agrego " + string + " como segundo parametro");
                        contIns.pop();
                        aritmeticFinished(string, "operacion", "operacion");
                    }
                } else if (contIns.get(contIns.size() - 1).equals("COND")) {
                    System.out.println("la actual instruccion es un Cond");
                    if (conds.get(contCond.get(contCond.size() - 1)).size() == 2) { // solo tiene los predicados
                        ArrayList<Object> blocks = (ArrayList<Object>) (conds.get(contCond.get(contCond.size() - 1))
                                .get(1));
                        ArrayList<Object> block = (ArrayList<Object>) blocks.get(blocks.size() - 1);
                        if (block.size() == 0) { // TODO: para un CLISP completo faltan condiciones
                            System.out.println("Al bloque le falta su predicado");
                            contClosed += 1;
                            block.add(string);
                            System.out.println("Se agrego el " + string + " como predicado al bloque");
                        } else {
                            System.out.println("Al bloque le faltan sus instrucciones");
                            block.add(string);
                            System.out.println("Se agrego el " + string + " como parte del bloque");
                        }
                    } else { // ya va para el else
                        // TODO: si se encuentra un Equals dentro de un cond que ya va por su else
                    }
                }
            }
            for (int n = 0; n < string.length(); n++) { // retrasa el contador de parentesis al encontrar un cierre
                char c = string.charAt(n);
                if (String.valueOf(c).equals(")") && contClosed > 0) {
                    System.out.println("Se encontro un )");
                    contClosed--;
                }
            }
            String[] finishing = string.split("\\)");
            System.out.println("separando por ) de: " + string);
            if (finishing.length > 1) {
                int contAllNull = finishing.length;
                for (int i = 1; i < finishing.length; i++) {
                    if (finishing[i].replaceAll(" ", "").length() != 0) {
                        contAllNull--;
                        System.out.println("se encontraron elementos siguientes a: " + finishing[0]);
                        System.out.println("se manda a separar: " + finishing[i]);
                        selectTasks(finishing[i]);
                    }
                }
                if (contAllNull == finishing.length) {
                    System.out.println("No se encontraron elementos siguientes a: " + finishing[0]);
                }
            } else {
                System.out.println("No se encontraron elementos siguientes a: " + finishing[0]);
            }
            // TODO: al cerrar una igualar al contador la cantidad de instrucciones que se
            // llevan
        }
    }

    public void verifyIfIsFunction(String string) {
        System.out.println("Verificando si lo encontrado en " + string + " es una funcion");
        for (ArrayList<Object> function : funcs) {
            if (String.valueOf(function.get(0)).equals(string)) {
                System.out.println("Lo que se encontro es una funcion");
                contIns.add("REFDEFUN");
                actual.add(string);
                reffuncs.add(actual);
                contRefDefun.add(reffuncs.size() - 1);
                System.out.println("Se agrego la referencia de funcion " + string + " a las referencias");
                actual = new ArrayList<>();
            }
        }
    }

    public void foundAritmetic(String string, String operator, String name) {
        System.out.println("se encontro un " + operator);
        contIns.add("ARITMETIC");
        if (string.split(" ").length == 1) {
            System.out.println("no se encontro otro parametro siguiente para la " + name);
            actual.add(operator);
            aritmetics.add(actual);
            System.out.println("se agrego la " + name + " vacia a las operaciones");
            contOp.add(aritmetics.size() - 1);
            actual = new ArrayList<>();
        } else if (string.split(" ").length > 2) {
            System.out.println("se encontraron 2 parametros siguientes para la " + name);
            actual.add(operator);
            actual.add(string.split(" ")[1]);
            actual.add(string.split(" ")[2].replaceAll("\\)", ""));
            contIns.remove(contIns.size() - 1);
            if (contIns.size() > 0) {
                aritmeticFinished(string, operator, name);
            }
        }
    }

    public void aritmeticFinished(String string, String operator, String name) {
        if (contIns.get(contIns.size() - 1).equals("ARITMETIC")) {
            System.out.println("hay una operacion aritmetica pendiente");
            aritmetics.get(contOp.get(contOp.size() - 1)).add(actual);
            System.out.println("se agrego la " + name + " como parametro de otra operacion");
            actual = new ArrayList<>();
        } else if (contIns.get(contIns.size() - 1).equals("REFDEFUN")) {
            System.out.println("hay una referencia de funcion pendiente");
            reffuncs.get(contRefDefun.get(contRefDefun.size() - 1)).add(actual);
            System.out.println("se agrego la " + name + " como parametro de la referencia funcion");
            actual = new ArrayList<>();
            contIns.remove(contIns.size() - 1);
            refDefunFinished(string);
        } else if (contIns.get(contIns.size() - 1).equals("COND")) {
            System.out.println("hay un COND pendiente");
            if (conds.get(contCond.get(contCond.size() - 1)).size() == 3) {
                if (((ArrayList<Object>) conds.get(contCond.get(contCond.size() - 1)).get(2)).size() == 0) {
                    System.out.println("El else del COND no tiene sus instrucciones");
                    ((ArrayList<Object>) conds.get(contCond.get(contCond.size() - 1)).get(2))
                            .add(aritmetics.get(contOp.get(contOp.size() - 1)));
                    System.out.println("Se agrego la " + name + " al else del Cond");
                    aritmetics.remove((int) (contOp.get(contOp.size() - 1)));
                    contOp.remove(contOp.size() - 1);
                    contIns.remove(contIns.size() - 1);
                    condFinished();
                }
            }
        } else if (contIns.get(contIns.size() - 1).equals("DEFUN")) {
            System.out.println("hay una funcion pendiente");
            if (funcs.get(contDefun.get(contDefun.size() - 1)).size() == 2) {
                System.out.println("La funcion no tiene su contenido");
                funcs.get(contDefun.get(contDefun.size() - 1)).add(aritmetics);
                System.out.println("Se agrego la " + name + " como contenido de la funcion");
                aritmetics = new ArrayList<>();
                contIns.pop();
            }
        } else {
            aritmetics.add(actual);
            System.out.println("se agrego la " + name + " a las operaciones");
            actual = new ArrayList<>();
        }
    }

    public void refDefunFinished(String string) {
        if (contIns.get(contIns.size() - 1).equals("ARITMETIC")) {
            System.out.println("hay una operacion aritmetica pendiente");
            aritmetics.get(contOp.get(contOp.size() - 1))
                    .add(reffuncs.get(contRefDefun.get(contRefDefun.size() - 1)));
            System.out.println("se agrego la referencia de funcion como parametro de una operacion");
            contRefDefun.remove(contRefDefun.size() - 1);
            if (aritmetics.get(contOp.get(contOp.size() - 1)).size() == 3) {
                contIns.remove(contIns.size() - 1);
                if (contIns.size() > 0) {
                    aritmeticFinished(string, "operacion", "operacion");
                }
            }
        }
    }

    public void condFinished() {
        if (contIns.get(contIns.size() - 1).equals("DEFUN")) {
            System.out.println("hay una funcion pendiente");
            if (funcs.get(contDefun.get(contDefun.size() - 1)).size() == 2) {
                System.out.println("La funcion no tiene su contenido");
                funcs.get(contDefun.get(contDefun.size() - 1)).add(conds);
                System.out.println("Se agrego el COND como contenido de la funcion");
                conds = new ArrayList<>();
                contCond.pop();
                // TODO: ver si no es necesario borrar estas referencias de funciones y
                // agregarlas
                // funcs.get(contDefun.get(contDefun.size() - 1)).add(reffuncs.get(contRefDefun.get(contRefDefun.size() - 1)));
                // reffuncs.remove((int)contRefDefun.pop());
                contIns.pop();
            }
        }
    }

    public ArrayList<Object> separateCommand(String command) {
        command = command.replaceAll("\\(", "");
        command = command.replaceAll("\\)", "");
        String name = command.split(" ")[0];
        ArrayList<Object> nameAndParameters = new ArrayList<>();
        nameAndParameters.add(name);
        ArrayList<Object> parameters = new ArrayList<>();
        for (int i = 0; i < command.split(" ").length; i++) {
            if (i > 0) {
                parameters.add(command.split(" ")[i].replaceAll("\\,", ""));
            }
        }
        nameAndParameters.add(parameters);
        return nameAndParameters;
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    @Override
    public void setContext(Context context) {
        // nothing to do;
    }
    
    public Object searchVariable(String name) {
        for (Variable<String,Object> variable : getVariables()) {
            if (variable.getKey().equals(name)) {
                System.out.println("Se encontro la variable " + variable.getKey() + " con el valor: " + variable.getValue());
                return variable.getValue();
            }
        }
        return null;
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

    public Object searchFunction(ArrayList<Object> ins) {
        for (Function function : functions) {
            if (function.getName().equals(String.valueOf(ins.get(0)))) {
                ArrayList<Object> params = new ArrayList<>();
                Object value = selectInstruction((ArrayList<Object>)(ins.get(1)), function);
                System.out.println("valor: " + ((value.toString()).split("\\."))[0]);
                params.add(((value.toString()).split("\\."))[0]);
                Function newFunction = new Function(this);
                newFunction.denifition(
                    (String) function.getName(),
                    (ArrayList<Object>) function.getNameParameter(),
                    (ArrayList<ArrayList<Object>>) function.getContent()
                );
                return newFunction.execute(params);
            }
        }
        return null;
    }

    public ArrayList<Variable<String, Object>> getVariables() {
        return this.variables;
    }

    public ArrayList<Function> getFunctions() {
        return this.functions;
    }

}