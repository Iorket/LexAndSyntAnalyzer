import parsers.*;

import javax.swing.*;

public class Main {
    //ТЕСТОВЫЙ МЕТОД
    public static void main(String args[]){
        WordParser wp =new WordParser();
        if(wp.isVar("abe3")){
            System.out.println("YEH");
        }
        else System.out.println("NOOOOOOOOOO");
        ValueIdentificator vl=new ValueIdentificator();
        if(vl.isDigit("1232d")) System.out.println("DIGIT OK");
        if(vl.isBoolean("true")) System.out.println("BOOL OK");
        if(vl.isBinar("010b")) System.out.println("BIN OK");
        Tokenizer tokenizer=new Tokenizer();
        tokenizer.startAnalyse("begin\n" +
                "dim int1,int2,int3 int;\n" +
                "[let] int1=123d;\n" +
                "dim bool1 bool;\n"+
                "[let] bool1=true;\n"+
                "[let] int1=123d+123d/123d;\n"+
                "input(int1 int2);\n" +
                "do while bool1 and int1=int2 dim int3 int loop;\n"+
                "if not bool1 then begin end;\n"+
                "end");
        System.out.println(tokenizer.getAllCode().toString());
        Parser parser=new Parser();
        parser.setToParser(tokenizer.getAllCode(),tokenizer.getId(),tokenizer.getNumbers());
        parser.startParser();
        System.out.println(parser.getError());
        System.out.println(parser.variablesToString());
        System.out.println(parser.getVariables().getType("bool1"));
        System.out.println(tokenizer.getNumbers().toString());
        JFrame newForm=new Form();
        newForm.setVisible(true);

    }
}
