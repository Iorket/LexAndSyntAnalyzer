package parsers;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordParser {
    final private String[] SERVICE_WORDS ={"begin","end","for","dim","[let]","if","then","else","do","while","output","input","loop"};
    final private String[] TYPE_WORDS ={"int","bool","float"};
    final private String VARIABLE_PATTERN="^[a-zA-Z][a-zA-Z_0-9]+";
    private Pattern regexp;
    private Matcher matcher;
    public WordParser(){
        regexp = Pattern.compile(VARIABLE_PATTERN);
    }
    public boolean isServWord(String word){
        if(findString(SERVICE_WORDS,word)) return true;
        else return false;
    }
    public boolean isType(String word){
        if(findString(TYPE_WORDS,word)){
          return true;
        }
        return false;
    }
    public boolean isVar(String word){
        if(!isServWord(word)&&!isType(word)){
            matcher=regexp.matcher(word);
            if(matcher.matches())return true;
        }
        return false;
    }

    private boolean findString(String[] words,String searchString){
        for (String word:
             words) {
            if(searchString.equals(word)) return true;
        }
        return false;
    }

}
