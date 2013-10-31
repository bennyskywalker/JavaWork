package TestCaseUtils;

import Models.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.omg.IOP.TAG_ALTERNATE_IIOP_ADDRESS;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: btsui
 * Date: 30/10/13
 * Time: 8:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class TagErrorTest {

    private TagError tagError;

    @Before
    public void setUp() throws Exception {
        tagError = new TagError(1, TagError.EXPECTEDTAG, "TestTagName");
    }

    @Test
    public void testGetErrorCode() throws Exception {
        assertEquals("EC", TagError.EXPECTEDTAG, tagError.getErrorCode());
    }

    @Test
    public void testGetLineNumber() throws Exception {
        assertEquals("LN", 1, tagError.getLineNumber());
    }

    @Test
    public void testGetTagName() throws Exception {
        assertEquals("TN", "TestTagName", tagError.getTagName());
    }

    @Test
    public void testDecodeError() throws Exception {
        assertEquals("DC", TagError.szEXPECTEDTAG, TagError.decodeError(tagError.getErrorCode()));
    }
}
