package converters;

public abstract class TextSegmentConverter implements Converter{
    private final String markdownSymbol;
    private final String htmlSymbolStart;
    private final String htmlSymbolEnd;

    protected TextSegmentConverter(String markdownSymbol, String htmlSymbolStart,String htmlSymbolEnd) {
        this.markdownSymbol = markdownSymbol;
        this.htmlSymbolStart = htmlSymbolStart;
        this.htmlSymbolEnd = htmlSymbolEnd;
    }

    protected TextSegmentConverter(String markdownSymbol, String htmlTagName) {
        this(markdownSymbol,"<"+htmlTagName+">","<"+htmlTagName+"/>");
    }

    @Override
    public boolean isApplicable(String line) {
        int start=line.indexOf(markdownSymbol);
        if (start==-1){
            return false;
        }
        int end=line.lastIndexOf(markdownSymbol);
        return start<end;
    }

    @Override
    public String apply(String line) {
        int start=line.indexOf(markdownSymbol);
        int end=line.lastIndexOf(markdownSymbol);

        return line.substring(0,start)
                +htmlSymbolStart
                +line.substring(start+markdownSymbol.length(),end)
                +htmlSymbolEnd
                +line.substring(end+markdownSymbol.length());
    }
}
