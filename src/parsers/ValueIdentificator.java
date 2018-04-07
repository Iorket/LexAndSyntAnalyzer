package parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValueIdentificator {
    final private String DIGIT_PATTERN="[0-9]+[d|D]$";
    final private String BIN_PATTERN="[0-1]+[b|B]$";
    final private String HEX_PATTERN="[0-7]+[o|O]$";
    final private String BOOLPATTERN="(true|false)";
    Pattern digitPattern,binPattern,hPattern,boolPattern,realPattern;
    Matcher digitMatcher,binMatcher,hMatcher,booMatcher,realMatcher;
    public ValueIdentificator(){
    digitPattern=Pattern.compile(DIGIT_PATTERN);
    binPattern=Pattern.compile(BIN_PATTERN);
    boolPattern=Pattern.compile(BOOLPATTERN);
    hPattern=Pattern.compile(HEX_PATTERN);
    }
    public boolean isDigit(String value){
        digitMatcher=digitPattern.matcher(value);
        return digitMatcher.matches();
    }
    public boolean isBoolean(String value){
        booMatcher=boolPattern.matcher(value);
        return booMatcher.matches();
    }
    public boolean isBinar(String value){
        binMatcher=binPattern.matcher(value);
        return binMatcher.matches();
    }
    public boolean isHex(String value){
        hMatcher=hPattern.matcher(value);
        return hMatcher.matches();
    }
    public boolean isTotal(String value){
        if(isDigit(value)||isBinar(value)||isHex(value))return true;
        return false;
    }
    public boolean typeCompare(String type,String value){
        switch (type){
            case ("int"):{
                if(isTotal(value)) return true;
                else return false;
            }
            case("bool"):{
                if(isBoolean(value)){
                    return true;
                }
                else return false;
            }
        }
        return false;
    }
    public boolean operandTypeCompare(String operand){
                for (String x:
                        new String[]{"+", "-", "/", "*"}) {
                    if(x.equals(operand)) return true;
                }
                return false;
            }

}
