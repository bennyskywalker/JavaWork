package TestCaseUtils;

/**
 * Created with IntelliJ IDEA.
 * User: btsui
 * Date: 30/10/13
 * Time: 7:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class TagError
{
    static public final int OK = 0;
    static public final int TAGMALFORMED = 1;
    static public final int BADCHARINTAG = 2;
    static public final int EXPECTEDTAG = 3;
    static public final int NOSTARTTAG = 4;

    static public final String szOK = "OK";
    static public final String szTAGMALFORMED = "too many/few characters in tag name.";
    static public final String szBADCHARINTAG = "bad character in tag name.";
    static public final String szEXPECTEDTAG = "expected"; //Need to add tag name  Normally I'd put a string format template here.
    static public final String szNOSTARTTAG = "no matching begin tag.";


    private int lineNumber;
    private int error;
    private String tagName;

    public TagError(int lineNumber, int error)
    {
        this.lineNumber = lineNumber;
        this.error = error;
    }

    public TagError(int lineNumber, int error, String tagName)
    {
        this(lineNumber, error);
        this.tagName = tagName;
    }


    public int getErrorCode()
    {
        return error;
    }

    public int getLineNumber()
    {
        return lineNumber;
    }

    public String getTagName()
    {
        return tagName;
    }

    public static String decodeError(int error)
    {
        switch(error)
        {
            default:
            case OK:
                return szOK;
            case TAGMALFORMED:
                return szTAGMALFORMED;
            case BADCHARINTAG:
                return szBADCHARINTAG;
            case EXPECTEDTAG:
                return szEXPECTEDTAG;
            case NOSTARTTAG:
                return szNOSTARTTAG;
        }
    }
}
