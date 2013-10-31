package Models;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: btsui
 * Date: 30/10/13
 * Time: 6:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestCase
{
    private List<String> testLines;
    private int testCase;

    /**
     * Constructor
     */
    public TestCase(int testCase)
    {
        testLines = new ArrayList<String>();
        this.testCase = testCase;
    }

    /**
     * Adds a test line
     * @param testLine
     */
    public void addTestLine(String testLine)
    {
        testLines.add(testLine);
    }

    /**
     * Debug test to make sure I could read in the sampledata.txt file from stdin.
     */
    public void debugStdOut()
    {
        Iterator iter = testLines.iterator();
        int lineNum = 1;

        while(iter.hasNext())
        {
            System.out.println("Line "+lineNum+": "+iter.next());
            lineNum++;
        }
    }

    /**
     * Return the lines of the test case.
     * @return
     */
    public List<String> getLines()
    {
        return testLines;
    }
}
