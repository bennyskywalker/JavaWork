package Parser;

import Models.TestCase;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: btsui
 * Date: 30/10/13
 * Time: 7:36 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IHTMLParser
{

    /**
     * This will parse a test case and produce events.  Much like a SAX parser.
     * @param testcase
     * @throws IOException
     */
    public void parse(TestCase testcase) throws IOException;
    public void addListener(ParserEventListener listener);
    public void removeListener(ParserEventListener listener);
}
