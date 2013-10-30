import Parser.ParserEventAdapter;
import Parser.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: btsui
 * Date: 29/10/13
 * Time: 3:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class TagAnalyzer extends ParserEventAdapter
{
    private List<Tag> startTags;

    public TagAnalyzer()
    {
        startTags = new ArrayList<Tag>();
    }

    public void startTagFound(Tag startTag)
    {
        //Add each start tag
        startTags.add(startTag);
    }

    public void endTagFound(Tag endTag)
    {
        //Remove from the start tag list - the left overs
        //are start tags that were not terminated.
        startTags.remove(endTag);
    }

    public List<Tag> listAllOrphanedTags()
    {
        return startTags;
    }
}
