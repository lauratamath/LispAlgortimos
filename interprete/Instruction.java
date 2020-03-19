import java.util.ArrayList;

abstract class Instruction implements Context {
    Context context;

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
            OpAritmeticas aritmeticOp = new OpAritmeticas(this);
            result = aritmeticOp.operar(operation, ins.get(1), ins.get(2));
        }
        return result;
    }
}