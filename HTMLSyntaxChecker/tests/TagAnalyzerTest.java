import Parser.Tag;
import TestCaseUtils.TagAnalyzer;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: btsui
 * Date: 29/10/13
 * Time: 9:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class TagAnalyzerTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testStartTagFound() throws Exception {
        TagAnalyzer tagAnalyzer = new TagAnalyzer();

        tagAnalyzer.startTagFound(new Tag("root", 0, 1));

        List<Tag> orphans = tagAnalyzer.listAllOrphanedTags();

        assertTrue("One Item", (orphans.size()==1 ));
    }

    @Test
    public void testEndTagFound() throws Exception {
        TagAnalyzer tagAnalyzer = new TagAnalyzer();
        List<Tag> orphans;

        tagAnalyzer.startTagFound(new Tag("root", 0, 1));

        orphans = tagAnalyzer.listAllOrphanedTags();

        assertTrue("One Item", (orphans.size()==1 ));

        tagAnalyzer.endTagFound(new Tag("root", 10, 1));

        orphans = tagAnalyzer.listAllOrphanedTags();
        assertEquals("Should be no orphans", orphans.size(), 0);
    }

    @Test
    public void testMultiTags() throws Exception {

        TagAnalyzer tagAnalyzer = new TagAnalyzer();
        List<Tag> orphans;

        tagAnalyzer.startTagFound(new Tag("root", 0, 1));
        tagAnalyzer.startTagFound(new Tag("p", 1, 1));
        tagAnalyzer.endTagFound(new Tag("p", 2, 1));

        tagAnalyzer.startTagFound(new Tag("p", 2, 1));
        tagAnalyzer.startTagFound(new Tag("textblock", 3, 1));
        tagAnalyzer.startTagFound(new Tag("textblock", 4, 1));

        orphans = tagAnalyzer.listAllOrphanedTags();
        assertEquals("Orphans A", orphans.size(), 4);

        tagAnalyzer.endTagFound(new Tag("textblock", 5, 1));
        tagAnalyzer.endTagFound(new Tag("root", 10, 1));

        orphans = tagAnalyzer.listAllOrphanedTags();
        assertEquals("Orphans at end", orphans.size(), 2);

        return;
    }
}
