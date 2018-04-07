package parsers;
import words.Variables;

import java.util.ArrayList;
import java.util.Map;

public class Parser {
    private ArrayList<String> code;
    private ArrayList<String> id,expressionRight,expressionLeft;
    private Map<String,String> constants;
    private ArrayList<String> currentId;
    private Variables variables;
    private int index;
    private String error=" ";
    private ValueIdentificator valId;
    private String vars;


    public Parser(){

    }
    public void setToParser(ArrayList<String> code,ArrayList<String> id,Map<String,String> constants){
        this.code=code;
        this.id=id;
        this.constants=constants;
    }
    public void startParser() {
        valId=new ValueIdentificator();
        expressionLeft=new ArrayList<>();
        expressionRight=new ArrayList<>();
        boolean operloop=true;
        variables=new Variables();
        index=1;
        error="";
        if (code.size() > 0) {
            if (!code.get(0).equals("begin")) {
                error = "Expected begin";
            } else {
                if (!code.get(code.size() - 1).equals("end")){
                    error="Expected end";
                }
            }
            if(error.equals("")) {
                while (index < code.size() - 1 && operloop) {
                    operloop=false;
                    if (!operators()) {

                        if (error.isEmpty()) error = "Неверно задан оператор.Ошибка в :  "+code.get(index);
                        break;
                    }
                    index++;
                    if (code.get(index).equals(";")) operloop = true;
                    else error="Ожидалось ; после: "+code.get(index-1);
                    index++;
                }
            }
        }
        if(variables.getSize()>0) {
            vars = variables.getAllVar();
        }
    }
    private boolean operators() {
        switch (code.get(index)) {
            case ("[let]"): {
                String type;
                index++;
                if (variables.foundVar(code.get(index))) {
                    type = variables.getType(code.get(index));
                    index++;

                    if (code.get(index).equals("=")) {
                        //Анализ выражения
                        index++;
                        if (expression(type)) return true;
                        else return false;

                    } else {
                        setExpectedError(code.get(index), "=");
                        return false;
                    }

                } else if (isVar(code.get(index))) {
                    error = "Переменная  " + code.get(index) + " не инициализированна";
                    return false;
                } else {
                    error = "Ожидалась переменная вместо: " + code.get(index);
                    return false;
                }

            }
            case ("dim"): {
                currentId = new ArrayList<>();
                do {
                    index++;
                    if (isVar(code.get(index))) {
                        currentId.add(code.get(index));
                        index++;
                    } else {
                        error = "Ожидалась переменная:" + code.get(index);
                        return false;
                    }
                }
                while (code.get(index).equals(","));
                if (isType(code.get(index))) {
                    for (String x :
                            currentId) {
                        variables.addVar(x, code.get(index));
                    }
                } else {
                    error = "Неверный тип данных" + code.get(index);
                }
                return true;
            }
            case ("input"): {
                index++;
                if (code.get(index).equals("(")) {
                    index++;
                    while (!code.get(index).equals(")")) {
                        if (variables.foundVar(code.get(index))) {
                            index++;
                        } else {
                            error = "Ожидалась переменная или ')' вместо: " + code.get(index);
                            return false;
                        }//dvazhno
                    }
                } else {
                    error = "Ожидалась ( вместо " + code.get(index);
                    return false;
                }
                return true;
            }
            case ("output"):
                index++;
                if (code.get(index).equals("(")) {
                    index++;
                    while(!code.get(index).equals( ")")) {
                        if(!expression("int")) return false;
                        index++;
                    }
                    return true;
                } else {
                    error = "Ожидалось (";
                    return false;
                }
            case ("do"): {
                index++;
                if (code.get(index).equals("while")) {
                    index++;
                    if(!complexExpression()) return false;
                    if(!operators()) return false;
                    index++;
                    if(!code.get(index).equals("loop")) {
                        setExpectedError(code.get(index),"loop");
                        return false;
                    }
                    return  true;
                }
                else {
                    setExpectedError(code.get(index),"while");
                    return false;
                }
            }
            case ("for"):
            {
                index++;
                if(!code.get(index).equals("(")) return false;
                for(int i=0;i<3;i++){
                    if(!code.get(index).equals("[")) return false;
                    index++;
                    if(!expression())return false;
                    index++;
                    if(!code.get(index).equals("]")) return false;
                    index++;
                    if(i<2){
                        if(!code.get(index).equals(";")) return false;
                        index++;
                    }
                }
                index++;
                if(!code.get(index).equals(")")) return false;
                index++;
                operators();

            }
            case ("begin"): {
                index++;
                while (true) {
                    if(code.get(index).equals("end")){
                        return true;
                    }
                    if (!operators()) {

                        if (error.isEmpty()) error = "Ожидался оператор вместо :  " + code.get(index);
                        return false;
                    }
                    index++;
                    if (!code.get(index).equals(";")) error="Ожидалось ; вместо: " + code.get(index);
                    index++;
                }
            }
            case("if"):{
                index++;
                if(!complexExpression()){
                    error="Выражение составлено неверно"+code.get(index);
                }
                if(code.get(index).equals("then")) {
                    index++;
                    operators();
                    index++;
                }
                else return false;
                if(code.get(index).equals("else")){
                    index++;
                    operators();
                    index++;
                    if(code.get(index).equals("end")){
                        index++;
                        if(code.get(index).equals("else")){
                            return true;
                        }
                    }
                }
                else {
                    index--;
                    return true;
                }
            }
        }
        return false;
    }
    //
    //
    //
    private boolean isVar(String var){
        for (String x:
             id) {
            if(x.equals(var))return true;
        }
        return false;
    }
    private boolean isType(String word){
        if(word.equals("bool")||word.equals("int")||word.equals("float")){
            return true;
        }
        return false;
    }
    //
    //getset
    //

    public String getError() {
        return error;
    }
    public Variables getVariables(){
        return variables;
    }
    public void setExpectedError(String word,String expWord){
        error="Ожидалось "+expWord+" вместо: "+word;
    }
    //Синтаксический и семантический анализ выражений
    public boolean expression(String type){
        String var=code.get(index);
        while(true){
        if(variables.foundVar(var)){
            if(variables.getType(var).equals(type)) index++;
            else{
                error="Переменная "+var+" не явл: "+type;
                return false;
            }
        }
        else if(valId.typeCompare(type,var)){
            index++;
        }
        else{
            error="Ожидалась переменная или инденификатор вместо: "+var;
            return false;
        }
        if(valId.operandTypeCompare(code.get(index))) index++;
        else {
            index--;
            return true;
        }
        }
    }
    //condition EXPRESSION
    public boolean expression(){
        String word=code.get(index);
        if(word.equals("not")){

                index++;
                word=code.get(index);
        }
        if(word.equals("true")||word.equals("false")){
        return true;
        }
        else if(isVar(word)){
            if(variables.getType(word).equals("bool")){
                return true;
            }
        }
        if(!expression("int")) return false;
        index++;
        word=code.get(index);
        if(word.equals("<>")||word.equals("<=")||word.equals(">=")||word.equals("=")) {
            index++;
            if (expression("int")) {
                return true;
            } else return false;
        }
        return false;
    }
    public boolean complexExpression(){
        do {
            if(!expression())return false;
            index++;
            if(code.get(index).equals("or")||code.get(index).equals("and")) index++;
            else break;
        }
        while (true);
        return true;
    }
    public String variablesToString() {
        return vars;
    }
    public String getIDandNumType(String word){
        if(isVar(word)){
            return variables.getType(word);
        }
        return constants.get(word);
    }
}
