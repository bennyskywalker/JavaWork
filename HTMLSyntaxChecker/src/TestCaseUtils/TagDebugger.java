package TestCaseUtils;

import Parser.ParserEventAdapter;
import Parser.Tag;

/**
 * Created with IntelliJ IDEA.
 * User: btsui
 * Date: 30/10/13
 * Time: 7:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class TagDebugger extends ParserEventAdapter {
    public void startTagFound(Tag startTag)
    {
        System.out.println("<"+startTag.getName()+">");
    }

    public void endTagFound(Tag endTag)
    {
        System.out.println("</"+endTag.getName()+">");
    }

    @Override
    public void deadTagFound(Tag deadTag) {
        System.out.println("Dead: "+deadTag.getName()+" position:"+deadTag.getPosition());
    }
}
