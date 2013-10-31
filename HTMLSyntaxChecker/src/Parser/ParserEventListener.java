package Parser;

/**
 * Created with IntelliJ IDEA.
 * User: btsui
 * Date: 30/10/13
 * Time: 7:34 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ParserEventListener
{
    /**
     * start tag found
     * @param startTag
     */
    public void startTagFound(Tag startTag);

    /**
     * end tag found
     * @param endTag
     */
    public void endTagFound(Tag endTag);

    /**
     * Found when a tag has no closing bracket
     * @param deadTag
     */
    public void deadTagFound(Tag deadTag);

    /**
     * Fired when the document is finished parsing.
     */
    public void endOfDocument();
}
