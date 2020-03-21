import java.util.ArrayList;

interface Context {
    public Context getContext();

    public void setContext(Context context);

    public ArrayList<Variable<String, Object>> getVariables();
    public ArrayList<Function> getFunctions();

    public Object searchVariable(String name);
    public Object searchFunction(ArrayList<Object> ins);
}