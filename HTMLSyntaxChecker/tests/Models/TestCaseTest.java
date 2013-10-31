package Models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: btsui
 * Date: 30/10/13
 * Time: 7:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestCaseTest {
    private TestCase testCase;
    private List<String> caseNums;

    @Before
    public void setUp() throws Exception {
        testCase = new TestCase(1);
        caseNums = new ArrayList<String>();
        caseNums.add("1");
        caseNums.add("2");
        caseNums.add("3");
    }

    @Test
    public void testAddTestLine() throws Exception {
        Iterator iter = caseNums.iterator();
        while(iter.hasNext())
        {
            testCase.addTestLine((String)iter.next());
        }

        assertEquals("RightNumLines", caseNums.size(), 3);
        assertEquals("TestCaseRight", testCase.getTestCase(), 1);
    }

    @Test
    public void testContentCorrectness() throws Exception
    {
        //Go through and make sure each added
        int lineNum = 0;
        Iterator iter2 = testCase.getLines().iterator();
        while(iter2.hasNext())
        {
            String line = (String)iter2.next();
            assertEquals("Test line", caseNums.get(lineNum), line);
            lineNum++;
        }
    }
}
