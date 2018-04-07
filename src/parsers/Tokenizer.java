package parsers;

import java.util.*;

public class Tokenizer {
    private final String[] SERVICE_WORDS={"begin","end","for","do","while","[let]","if","else","then","input","output","bool","float","int","dim","true","false"};
    private final String[] SEPARATORS={"<","=",">",";","+","-","/","*","!=","<>","<=",">=",",","not","or","and","(",")"};
    private final char[] SEPARATOR_CHARS={'<','>','=',',','+','-','!',';',',','(',')','/','*'};
    private final String[] DELIMETRIES={"not","and","or"};
    private String code;
    private Map <String,String> numbers;
    private StringBuilder LexCode;
    private StringBuilder buffer;
    private int charIndex;
    private ArrayList<String> id;
    private ArrayList<String> allCode;
    private String Error;
    private char symbol;
    private ValueIdentificator vl;
    public Tokenizer(){

    }

    public String getError() {
        return Error;
    }
    private char getChar(){
        if(charIndex>=code.length()) return '?';
        symbol=code.charAt(charIndex);
        charIndex++;
        return symbol;
    }
    private int getServiceWordNum(String word){
        for (int i=0;i<SERVICE_WORDS.length;i++){
            if (SERVICE_WORDS[i].equals(word)) return i+1;
        }
        return 0;
    }
    private int getSeparatorNum(String word){
        for (int i=0;i<SEPARATORS.length;i++){
            if (SEPARATORS[i].equals(word)) return i+1;
        }
        return 0;
    }
    public void startAnalyse(String inputCode){
        Error="";
        inputCode=commentsDeleting(inputCode);
        inputCode=inputCode.replaceAll("\\s+", " ");
        this.code=inputCode+" ";
        id=new ArrayList<>();
        allCode=new ArrayList<>();
        LexCode=new StringBuilder();
        buffer=new StringBuilder();
        numbers=new HashMap<String, String>();
         vl=new ValueIdentificator();
        charIndex=0;
        char curChar;
        curChar=getChar();
        while (charIndex<code.length()){
            curChar=symbol;
            if (Character.isLetter(curChar)||curChar=='['||curChar==']'){
                wordAnalyze();
            }
            else if (Character.isDigit(curChar)){
                if(!numAnalyze()){
                    Error="Неверный тип данных!: "+charIndex;
                    break;
                };
            }
            else if (isSeparator(curChar)){
                separatorAnalyze();
            }
            else if(curChar==' '){
                getChar();
            }
            else
            {
                Error="Unknow character at"+charIndex;
                break;
            }
        }
        System.out.println(Error);
    }
    //LEX WORD
    private boolean wordAnalyze() {
        buffer.setLength(0);
        while (isWordChar(symbol)||Character.isDigit(symbol)){
            buffer.append(symbol);
            getChar();
        }
        /*
        while (isWordChar(symbol)||Character.isDigit(symbol)) {
            buffer.append(symbol);
            getChar();
        }
        */
        if(getServiceWordNum(buffer.toString())!=0){
            addLex(1,getServiceWordNum(buffer.toString()));
            allCode.add(buffer.toString());
            if(buffer.toString().equals("true")||buffer.toString().equals("false")) numbers.put("true","bool");
        }
        else if(getSeparatorNum(buffer.toString())!=0){
            addLex(2,getSeparatorNum(buffer.toString()));
            allCode.add(buffer.toString());
        }
        else {
            addLex(3,addID());
            allCode.add(buffer.toString());
        }
        return true;
    }
    //LEX NUMEROS
    private boolean numAnalyze(){
        buffer.setLength(0);
        while (Character.isDigit(symbol)){
            buffer.append(symbol);
            getChar();
        }
        if(isWordChar(symbol)){
            if(symbol=='e'||symbol=='E') {
                buffer.append(symbol);
                getChar();
                if (symbol == '+' || symbol == '-') {
                    buffer.append(symbol);
                    getChar();
                    if (Character.isDigit(symbol)) {
                        while (Character.isDigit(symbol)) {
                            buffer.append(symbol);
                            getChar();
                        }
                        if(!isSeparator(symbol))return false;
                        numbers.put(buffer.toString(), "float");
                        addLex(4, numbers.size() - 1);
                        allCode.add(buffer.toString());
                        return true;
                    } else return false;
                } else return false;
            }
            buffer.append(symbol);
            getChar();
        }
        else {
            return false;
        }
        if(vl.isTotal(buffer.toString())){
            numbers.put(buffer.toString(),"int");
            addLex(4,numbers.size()-1);
            allCode.add(buffer.toString());
            return true;
        }
        return false;
    }
    //LEX SEPARATORS
    private boolean separatorAnalyze(){
        buffer.setLength(0);
        /*while(isSeparator(symbol)){
            buffer.append(symbol);
            getChar();
        }
        int index=getSeparatorNum(buffer.toString());
        if(index!=0){
            addLex(2,index);
            allCode.add(buffer.toString());
            return true;
        }
        else Error="unknow separator"+buffer.toString()+"at "+charIndex;
*/
        if(isSeparator(symbol)){
            buffer.append(symbol);
            if(symbol=='<'){
                if(code.charAt(charIndex)=='>'){
                    getChar();
                    buffer.append(symbol);
                }
            }
            if(symbol=='<'||symbol=='>'||symbol=='!'){
                if(code.charAt(charIndex)=='='){
                    getChar();
                    buffer.append(symbol);
                }
            }
            getChar();
            int index=getSeparatorNum(buffer.toString());
            if(index!=0){
                addLex(2,index);
                allCode.add(buffer.toString());
                return true;
            }

        }
        else Error="Unknow separator: "+buffer.toString()+" at "+charIndex;
        return false;
    }

    //ПРОВЕРКИ
    //
    private boolean isWordChar(char ichar){
        if (Character.isLetter(ichar)||ichar=='['||ichar==']'){
            return true;
        }
        return false;
    }
    private boolean isSeparator(char symbol){
        for (char x:
                SEPARATOR_CHARS) {
            if(x==symbol)return  true;
        }
        return false;
    }

    //add lexem
    private void addLex(int type,int num){
        LexCode.append("("+type+","+num+")");
    }
   //
    private void addLex(String var){
        boolean existID=false;
        for(int i=0;i<id.size();i++){
            if(id.get(i).equals(var)){
                existID=true;
            }
        }
        if(!existID){

        }
    }
    //
    // GET SET METHODS
    //

    public ArrayList<String> getAllCode() {
        return allCode;
    }
    public ArrayList<String> getId(){return id;}
    public Map<String,String> getNumbers(){return numbers;}

    public StringBuilder getLexCode() {
        return LexCode;
    }
    private int addID() {
        if (id.size() == 0) {
            id.add(buffer.toString());
            return 0;
        }
        int i = 0;
        for (String var : id
                ) {
            i++;
            if (buffer.toString().equals(var)) return i + 1;
        }
        id.add(buffer.toString());
        return id.size()-1;
    }
    private String commentsDeleting(String code){
        String delString;
        int secondCommIndex,firstCommIndex;
        do {

            firstCommIndex = code.indexOf("(*");
            if(firstCommIndex==-1) break;
            secondCommIndex = code.indexOf("*)");
            if(secondCommIndex==-1) secondCommIndex=code.length()-3;
            delString=code.substring(firstCommIndex,secondCommIndex+2);
            code=code.replace(delString,"");
        }
        while (firstCommIndex!=secondCommIndex);
        return code;
    }
}
