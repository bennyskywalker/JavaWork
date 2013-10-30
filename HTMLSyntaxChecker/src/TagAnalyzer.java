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
    private List<Tag> errorTags;

    public TagAnalyzer()
    {
        startTags = new ArrayList<Tag>();
        errorTags = new ArrayList<Tag>();
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

    @Override
    public void deadTagFound(Tag deadTag) {
        //This is tags that are orphaned - dead - add to list
        errorTags.add(deadTag);
    }

    public List<Tag> listAllOrphanedTags()
    {
        return startTags;
    }

    public List<Tag> listAllDeadTags()
    {
        return errorTags;
    }
}
