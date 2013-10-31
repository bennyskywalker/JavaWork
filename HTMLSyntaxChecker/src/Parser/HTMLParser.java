package Parser;

import Models.TestCase;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: btsui
 * Date: 30/10/13
 * Time: 7:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class HTMLParser implements IHTMLParser
{
    private final int findingStartBracket = 0;
    private final int findingEndBracket = 1;

    private final int startTag = 0;
    private final int endTag = 1;
    private final int startEndTag = 2;

    private List<String> nonClosedTags;
    private List<ParserEventListener>  _listeners;

    /**
     * This class will parse an HTML chunk, and fire back events for all the tags.
     * We need to supply it with input - in the form of lines.
     */
    public HTMLParser()
    {
        //Init the list
        this.nonClosedTags = new ArrayList<String>();
        _listeners = new ArrayList();
    }

    public void addListener(ParserEventListener listener)
    {
        synchronized (_listeners)
        {
            if(!this._listeners.contains(listener))
            {
                this._listeners.add(listener);
            }
        }
    }

    public void removeListener(ParserEventListener listener)
    {
        synchronized (this._listeners)
        {
            if(this._listeners.contains(listener))
            {
                this._listeners.remove(listener);
            }
        }
    }


    public List<String> nonClosedTags()
    {
        return nonClosedTags;
    }

    private void fireStartTagEvent(Tag startTag)
    {
        synchronized (this._listeners)
        {
            Iterator i = _listeners.iterator();
            while(i.hasNext())  {
                ((ParserEventListener) i.next()).startTagFound (startTag);
            }
        }
    }

    private void fireEndTagEvent(Tag endTag)
    {
        synchronized (this._listeners)
        {
            Iterator i = _listeners.iterator();
            while(i.hasNext())  {
                ((ParserEventListener) i.next()).endTagFound (endTag);
            }
        }
    }

    private void fireEndOfDocument()
    {
        synchronized (this._listeners)
        {
            Iterator i = _listeners.iterator();
            while(i.hasNext())
            {
                ((ParserEventAdapter) i.next()).endOfDocument();
            }
        }
    }

    private void fireDeadTagEvent(Tag deadTag)
    {
        synchronized (this._listeners)
        {
            Iterator i = _listeners.iterator();
            while(i.hasNext())
            {
                ((ParserEventListener) i.next()).deadTagFound(deadTag);
            }
        }
    }

    /**
     * Parse this test case.  Will end up parsing much like a SAX parser.
     * We need to know the line number we are erroring on.  Each test case
     * Contains a line - in a string array - so we know which line we are
     * on.
     * @param testCase
     * @throws IOException
     */
    public void parse(TestCase testCase) throws IOException
    {
        int parsingState;
        int value;
        int position = 0;
        String html;
        Iterator lineIter;
        int lineNumber;
        StringBuffer tagName = new StringBuffer();

        //Need to look for the start of a tag - '<' character
        //Then, need to find the '>' character

        //Then determine if this was a opening tag <tag> or closing tag </tag>
        //It could be a all inclusive tag <tag/> <- even though the test cases don't want this.  For completeness.

        parsingState = 0;
        lineNumber = 0;

        //Need to load up each line into an input stream.
        //I'm going to parse each line character by character.
        lineIter = testCase.getLines().iterator();

        //I'm torn with leaving this outside the iteration.  In this case
        //when 1 lines dies, the iteration entire parse will die for this test case.
        try
        {
            while(lineIter.hasNext())
            {
                html = (String)lineIter.next();
                lineNumber++;

                InputStream is = new ByteArrayInputStream(html.getBytes());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                tagName = new StringBuffer();
                position = 0;

                //Start parsing
                // reads to the end of the stream
                while((value = br.read()) != -1)
                {
                    // converts int to character
                    char c = (char)value;
                    position++;

                    if(isStartBracket(c))
                    {
                        if(parsingState==findingEndBracket)
                        {
                            //If we were looking for an end bracket - it means
                            //we were still looking for a start bracket.  So this
                            //is a dead tag case
                            fireDeadTagEvent(new Tag(tagName.toString(), position, lineNumber));
                        }

                        parsingState = findingEndBracket;

                        //Reset String
                        tagName = new StringBuffer();
                    }
                    else if(isEndBracket(c))
                    {
                        String szTag;
                        int tagType;

                        parsingState = findingStartBracket;
                        szTag = tagName.toString().trim();

                        //If this tag contains the / character, then fire both start & end
                        //and string the / character
                        if(szTag.length()==0)
                        {
                            //In reality - empty tag
                            tagType = startTag;
                            szTag = "";
                        }
                        else if(szTag.indexOf('/')==szTag.length()-1)
                        {
                            tagType = startEndTag;
                            szTag = szTag.substring(0, szTag.indexOf('/'));
                        }
                        else if(szTag.indexOf('/')==0)
                        {
                            tagType = endTag;
                            szTag = szTag.substring(1);
                        }
                        else
                        {
                            tagType = startTag;
                        }

                        Tag newTag = new Tag(szTag.toString(),position, lineNumber);

                        switch(tagType)
                        {
                            case startTag:
                                fireStartTagEvent(newTag);
                                break;
                            case endTag:
                                fireEndTagEvent(newTag);
                                break;
                            case startEndTag:
                                fireStartTagEvent(newTag);
                                fireEndTagEvent(newTag);
                                break;
                        }
                    }
                    else
                    {
                        if(parsingState==findingEndBracket)
                        {
                            tagName.append(c);
                        }
                    }
                }
            }
        }
        catch(IOException ioException)
        {
            //error handling here - no parsing - report error?
            throw ioException;
        }

        //If we are at the end of the doc - but no finsh to the tag
        //Need to log some sort of error
        if(parsingState==findingEndBracket)
        {
            //System.out.println("error end bracket");
            fireDeadTagEvent(new Tag(tagName.toString(), position, lineNumber));
        }

        fireEndOfDocument();
        return;
    }

    private boolean isStartBracket(char c)
    {
        if(c == '<')
            return true;
        else
            return false;
    }

    private boolean isEndBracket(char c)
    {
        if(c=='>')
            return true;
        else
            return false;
    }

}
