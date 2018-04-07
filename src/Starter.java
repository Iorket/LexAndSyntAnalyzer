import parsers.Parser;
import parsers.Tokenizer;

public class Starter {
    private Tokenizer tokenizer;
    private Parser parser;
    private String outputString,varString,idString,lexString;
    public Starter(){
        tokenizer=new Tokenizer();
        parser=new Parser();
    }
    public void start(String code){
        tokenizer.startAnalyse(code);
        outputString="Лексический анализ начат\n";
        lexString=tokenizer.getLexCode().toString();
        idString=tokenizer.getNumbers().toString();
        if(tokenizer.getError().isEmpty()){
            outputString=outputString+"Ошибок не выявлено!\n";
            parser.setToParser(tokenizer.getAllCode(),tokenizer.getId(),tokenizer.getNumbers());
            parser.startParser();
            outputString=outputString+"Начат синтаксический анализ";
            varString=parser.getVariables().getAllVar();
            if(parser.getError().isEmpty()){outputString=outputString+"Синтаксический анализ успешно завершен";}
            else outputString=outputString+parser.getError();
        }
        else outputString=outputString+tokenizer.getError();
    }

    public String getOutputString() {
        return outputString;
    }

    public String getLexString() {
        return lexString;
    }

    public String getIdString() {
        return idString;
    }

    public String getVarString() {
        return varString;
    }
}
