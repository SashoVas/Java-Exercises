package converters;

public class HeadingConverter implements Converter{
    private static final String HTML_SYMBOL_START="<h";
    private String generateHtmlSymbol(int count,boolean isStart){
        return HTML_SYMBOL_START+count+ (isStart?">":"/>");
    }
    private int countSymbols(String line,char symbol,int startPos){
        int current=0;
        while(line.charAt(startPos+current)==symbol){
            current++;
        }
        return current;
    }
    @Override
    public boolean isApplicable(String line) {
        int symbolCount=countSymbols(line,'#',0);
        return symbolCount>0 && symbolCount<=6;
    }

    @Override
    public String apply(String line) {
        int symbolCount=countSymbols(line,'#',0);
        return generateHtmlSymbol(symbolCount,true)
                +line.substring(symbolCount)
                +generateHtmlSymbol(symbolCount,false);
    }
}
