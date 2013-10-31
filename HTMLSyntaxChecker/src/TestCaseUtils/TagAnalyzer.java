package TestCaseUtils;

import Parser.ParserEventAdapter;
import Parser.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: btsui
 * Date: 30/10/13
 * Time: 7:38 PM
 * To change this template use File | Settings | File Templates.
 * This is where the guts of the analysis will be.  I'll need to take all the real time data
 * Inculding line numbers and analyze what went wrong with the tags.
 */
public class TagAnalyzer extends ParserEventAdapter
{
    private List<Tag> startTags;
    private List<Tag> errorTags;

    private List<TagError> errorList;

    public TagAnalyzer()
    {
        startTags = new ArrayList<Tag>();
        errorTags = new ArrayList<Tag>();
        errorList = new ArrayList<TagError>();
    }

    public void startTagFound(Tag startTag)
    {
        //Check on this start tag - correctness
        //10 upper-case alphabetic characters?
        if(startTag.getName().length()>10 || startTag.getName().length()==0)
        {
            //not enough or too many
            errorList.add(new TagError(startTag.getLineNumber(), TagError.TAGMALFORMED));
        }
        else if(!startTag.getName().matches("[A-Z]+"))
        {
            //Bad character
            errorList.add(new TagError(startTag.getLineNumber(), TagError.BADCHARINTAG));
        }
        else
        {
            //Add each start tag
            startTags.add(startTag);
        }

    }

    public void endTagFound(Tag endTag)
    {
        //Remove from the start tag list - the left overs
        //are start tags that were not terminated.
        if(!startTags.contains(endTag))
        {
            if(startTags.size()==0)
                errorList.add(new TagError(endTag.getLineNumber(), TagError.NOSTARTTAG));
            else if(startTags.size()>0)
            {
                //This may be an expected non matching pair.
                Tag tag = startTags.get(0);
                //String errorMsg = EXPECTEDTAG+" </"+tag.getName()+">";
                errorList.add(new TagError( endTag.getLineNumber(), TagError.EXPECTEDTAG, tag.getName()));
            }
        }
        else
        {
            startTags.remove(endTag);
        }
    }

    @Override
    public void deadTagFound(Tag deadTag) {
        //This is tags that are orphaned - dead - add to list
        errorTags.add(deadTag);

        errorList.add(new TagError(deadTag.getLineNumber(), TagError.BADCHARINTAG));
    }

    public List<Tag> listAllOrphanedTags()
    {
        return startTags;
    }

    public List<Tag> listAllDeadTags()
    {
        return errorTags;
    }

    @Override
    public void endOfDocument() {
        super.endOfDocument();    //To change body of overridden methods use File | Settings | File Templates.

        //See what we have left for errors.
        if(errorList.size()==0)
        {
            errorList.add(new TagError(0, TagError.OK));
        }

    }

    public List<TagError> getErrorList()
    {
        return errorList;
    }
}
