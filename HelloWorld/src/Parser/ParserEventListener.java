package Parser;

/**
 * Created with IntelliJ IDEA.
 * User: btsui
 * Date: 29/10/13
 * Time: 3:34 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ParserEventListener
{
    public void startTagFound(Tag startTag);
    public void endTagFound(Tag endTag);
    public void deadTagFound(Tag deadTag);
}
