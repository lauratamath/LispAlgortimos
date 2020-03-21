import java.util.ArrayList;

class Cond extends Instruction {

    public Cond(Context context) {
        setContext(context);
    }

    private Object doBlock(Object block) {
        System.out.println("se hara el bloque: " + block);
        Object result = null;
        if (block.toString().contains("[")) {
            result = selectInstruction((ArrayList<Object>)block, getContext());
        } else {
            System.out.println("El bloque no es una instruccion");
            result = selectInstruction(block);
        }
        return result;
    }

    public Object doBlocks(ArrayList<Object> blocks, ArrayList<Object> telse) {
        Object result = null;
        Predicates predicates = new Predicates(getContext());
        for (Object block : blocks) {
            ArrayList<Object> toPredicate = (ArrayList<Object>)(((ArrayList<Object>)block).get(0));
            if (predicates.comparar(String.valueOf(toPredicate.get(0)), toPredicate.get(1), toPredicate.get(2))) {
                result = doBlock((((ArrayList<Object>)block).get(1)));
            }
        }
        for (Object block : telse) {
            result = doBlock(block);
        }
        return result;
    }
}