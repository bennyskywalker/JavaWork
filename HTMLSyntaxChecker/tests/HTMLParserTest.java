
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import Parser.HTMLParser;
import Parser.ParserEventAdapter;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: btsui
 * Date: 30/10/13
 * Time: 8:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class HTMLParserTest {

    private HTMLParser htmlParser;

    @org.junit.Before
    public void setUp() throws Exception {
        htmlParser = new HTMLParser();
    }

    @org.junit.Test
    public void testAllClosedTag() throws Exception {

    }

    @org.junit.Test
    public void testOneOpenTag() throws Exception {

    }

    @org.junit.Test
    public void testAddRemoveEventListener() throws Exception {

        ParserEventAdapter eventAdapter = new ParserEventAdapter();
        htmlParser.addListener(eventAdapter);
        htmlParser.removeListener(eventAdapter);
    }
}
