package Parser;

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

    public void parse(String html)
    {
        this.html = html;

        //Parse the HTML - while parsing fire the start & end tags out
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
}
