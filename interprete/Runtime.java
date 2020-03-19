import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Runtime {

    /**
     * String que contiene el programa completo para separar
     */
    private String program;

    public Runtime(String program) {
        this.program = program;
    }

    /**
     * Aqui se deben de pasar las instrucciones a sus respectivas listas
     */
    public void compile() {
        split();
        for (ArrayList<Object> function : functions) {
            Function newFunction = new Function();
            System.out.println("La funcion es: " + function);
            newFunction.denifition(
                (String)function.get(0),
                (ArrayList<Object>)function.get(1),
                (ArrayList<Object>)function.get(2)
            );
            compiledFunctios.add(newFunction);
        }
    }

    ArrayList<Function> compiledFunctios = new ArrayList<>(); 
    /**
     * para ejecutar un comando del usuario
     */
    public void executeCommand(String command) {
        separateCommand(command);
        String name = (String)separateCommand(command).get(0);
        ArrayList<Object> params = (ArrayList<Object>)separateCommand(command).get(1); //unsafe cast
        for (Function function : compiledFunctios) {
            if (function.getName().equals(name)) {
                function.function(params);
            }
        }
    }

    /**
     * Aqui se separa el string y se leen cada de cuantas cosas va a haber
     * Siguen siendo solo strings
     */
    /**
     * listas que guardan las operaciones ya separadas
     */
    ArrayList<ArrayList<Object>> functions = new ArrayList<>();
    ArrayList<ArrayList<Object>> aritmetics = new ArrayList<>();
    /**
     * variables necesarias para separar
     */
    Stack<Integer> contDefun = new Stack<>();
    Stack<Integer> contOp = new Stack<>();
    Stack<String> contIns = new Stack<>();
    /**
     * instruccion actual
     */
    ArrayList<Object> actual = new ArrayList<>();
    /**
     * separar por parentesis iniciales
     */
    public void split() {
        String[] starting = program.split("\\(");
        for (String string : starting) {
            selectTasks(string);
        }
        /**
         * TODO: verificar si siempre una funcion va a necesitar que se le pasen las operaciones
         */
        functions.get(contDefun.get(contDefun.size() - 1)).add(aritmetics.get(contOp.get(contOp.size() - 1)));
        aritmetics.remove(aritmetics.size() - 1);
    }

    /**
     * a partir del primer split, se separa cada vez por parentesis finales
     * pero se tiene este metodo aparte para aplicar recursividad
     */
    public void selectTasks(String string) {
        if (string.contains("DEFUN")) {
            contIns.add("DEFUN");
            string = string.replaceAll("DEFUN", "");
            string = string.replaceAll(" ", "");
            actual.add(string);
            functions.add(actual);
            contDefun.add(functions.size() - 1);
            actual = new ArrayList<>();
        } else if (string.contains("/")) {
            contIns.add("ARITMETIC");
            if (string.split(" ").length == 1) {
                actual.add("/");
                aritmetics.add(actual);
                contOp.add(aritmetics.size() - 1);
                actual = new ArrayList<>();
            }
        } else if (string.contains("-")) {
            contIns.add("ARITMETIC");
            if (string.split(" ").length > 2) {
                actual.add("-");
                actual.add(string.split(" ")[1]);
                actual.add(string.split(" ")[2].replaceAll("\\)", ""));
                if (contIns.size() > 0) { //no se si es necesario
                    if (contIns.get(contIns.size() - 1).equals("ARITMETIC")) {
                        aritmetics.get(contOp.get(contOp.size() - 1)).add(actual);
                        actual = new ArrayList<>();
                    } else {
                        aritmetics.add(actual);
                        actual = new ArrayList<>();
                    }
                }
                contIns.remove(contIns.size() - 1);
            }
            String[] finishing = string.split("\\)");
            if (finishing.length > 1) {
                for (int i = 1; i < finishing.length; i++) {
                    selectTasks(finishing[i].replaceAll(" ", ""));
                }
            }
        } else if (string.replaceAll(" ", "").length() != 0) {
            if (contIns.get(contIns.size() - 1).equals("DEFUN")) {
                if (functions.get(contDefun.get(contDefun.size() - 1)).size() == 1) {
                    String[] params = string.split(","); //revisar si separa bien
                    ArrayList<String> paramsList = new ArrayList<>();
                    for (String param : params) {
                        param = param.replaceAll(" ", "");
                        param = param.replaceAll("\\)", "");
                        paramsList.add(param);
                    }
                    functions.get(contDefun.get(contDefun.size() - 1)).add(paramsList);
                }
            } else if (contIns.get(contIns.size() - 1).equals("ARITMETIC")) {
                if (aritmetics.get(contOp.get(contOp.size() - 1)).size() == 2) {
                    aritmetics.get(contOp.get(contOp.size() - 1)).add(string);
                }
            }
        }
        //TODO: al cerrar una igualar al contador la cantidad de instrucciones que se llevan
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

}