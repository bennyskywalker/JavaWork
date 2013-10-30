package Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: btsui
 * Date: 29/10/13
 * Time: 1:36 AM
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
    private String html;
    private List<ParserEventListener>  _listeners;

    public HTMLParser()
    {
        //Init the list
        this.nonClosedTags = new ArrayList<String>();
        html = null;
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

    private void fireDeadTagEven(Tag deadTag)
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

    //Parse this html
    public void parse(InputStream htmlstream) throws IOException
    {
        int parsingState;
        int value;
        int position;

        this.html = html;

        //Parse the HTML - while parsing fire the start & end tags out

        //Need to look for the start of a tag - '<' character

        //Then, need to find the '>' character

        //Then determine if this was a opening tag <tag> or closing tag </tag>
        //It could be a all inclusive tag <tag/>

        parsingState = 0;

        try
        {
            //InputStream is = new ByteArrayInputStream(this.html.getBytes());
            BufferedReader br = new BufferedReader(new InputStreamReader(htmlstream));
            StringBuffer tagName = new StringBuffer();
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
                        fireDeadTagEven(new Tag(tagName.toString(), position));
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
                    if(szTag.indexOf('/')==szTag.length()-1)
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

                    Tag newTag = new Tag(szTag.toString(),position);

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
        catch(IOException ioException)
        {
            //error handling here - no parsing - report error?
            throw ioException;
        }

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
