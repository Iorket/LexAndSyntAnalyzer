package words;

import java.util.ArrayList;

public class Variables {
    ArrayList<Variable> variables;
    public Variables(){
        variables=new ArrayList<>();
    }
    public boolean addVar(String name,String type){
        variables.add(new Variable(name,type));
        return true;
    }
    public boolean foundVar(String name) {
        if (variables.size() > 0)
        {   for (Variable v :
                    variables) {
                if (v.getName().equals(name)) return true;
            }
    }
        return false;
    }
    public void clear(){
        variables.clear();
    }
    public String getType(String name){
        if (variables.size() > 0)
        {   for (Variable v :
                variables) {
            if (v.getName().equals(name)) {
                return v.getType();
            }
        }
        }
        return null;
    }
    public int getSize(){
        return variables.size();
    }
    public String getAllVar() {
        String x = " ";
        if (variables.size() > 0)
            for (Variable v :
                    variables) {
                x = x + v.getName() + "," + v.getType() + ";";
            }
        return x;
    }
}
