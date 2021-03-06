package Parser;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: btsui
 * Date: 30/10/13
 * Time: 8:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class TagTest {
    private Tag tag;
    @Before
    public void setUp() throws Exception {
        tag = new Tag("name", 11, 1);
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Name", "name", tag.getName());
    }

    @Test
    public void testGetPosition() throws Exception {
        assertEquals("position", 11, tag.getPosition());
    }

    @Test
    public void testGetLineNumber() throws Exception
    {
        assertEquals("line", 1, tag.getLineNumber());
    }
}
