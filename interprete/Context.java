import java.util.ArrayList;

interface Context {
    public Context getContext();

    public void setContext(Context context);
    public ArrayList<Variable<String, Object>> getVariables();
}