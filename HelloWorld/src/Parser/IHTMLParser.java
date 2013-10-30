package Parser;

/**
 * Created with IntelliJ IDEA.
 * User: btsui
 * Date: 29/10/13
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IHTMLParser
{
    public void parse(String html);
    public void addListener(ParserEventListener listener);
    public void removeListener(ParserEventListener listener);
}
