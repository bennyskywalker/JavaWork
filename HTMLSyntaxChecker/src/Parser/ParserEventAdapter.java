package Parser;

/**
 * Created with IntelliJ IDEA.
 * User: btsui
 * Date: 30/10/13
 * Time: 7:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParserEventAdapter implements ParserEventListener {
    @Override
    public void startTagFound(Tag startTag)
    {
        return;
    }

    @Override
    public void endTagFound(Tag endTag)
    {
        return;
    }

    @Override
    public void deadTagFound(Tag deadTag)
    {
        return;
    }

    @Override
    public void endOfDocument()
    {
        return;
    }
}