import Parser.HTMLParser;
import Parser.IHTMLParser;
import Parser.Tag;
import com.sun.corba.se.impl.logging.ORBUtilSystemException;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Main {

    public static void main(String[] args) {

        IHTMLParser htmlParser;
        TagAnalyzer tagAnalyzer;
        TagDebugger tagDebugger;
        StringBuilder strBuilder;
        String input;

        //test reading stdin - from a pipe of a test.txt file
        //Print out.
        strBuilder = new StringBuilder();

        try{
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(System.in));

            while((input=br.readLine())!=null){
                strBuilder.append(input);
            }

        }catch(IOException io){
            io.printStackTrace();
        }
        //strBuilder.append("<html></html>");


        tagAnalyzer = new TagAnalyzer();
        tagDebugger = new TagDebugger();

        //Parse the html
        //testing string parsing
        htmlParser = new HTMLParser();
        htmlParser.addListener(tagAnalyzer);   //add the tag analyzer - recieves start/end tags
        htmlParser.addListener(tagDebugger);   //add a debugger system out

        InputStream is = new ByteArrayInputStream(strBuilder.toString().getBytes());
        try
        {
            htmlParser.parse(is);
        }
        catch(IOException ex)
        {
            System.out.println("Error parsing html: " + ex.toString());
        }

        //Any Orphans
        List<Tag> orphans = tagAnalyzer.listAllOrphanedTags();

        System.out.println("Orphaned tags: ");
        Iterator iter = orphans.iterator();
        while(iter.hasNext())
        {
            Tag orphanedTag = (Tag)iter.next();
            System.out.println(orphanedTag.getName()+" char:"+orphanedTag.getPosition());
        }

        List<Tag> dead = tagAnalyzer.listAllDeadTags();
        System.out.println("Dead tags: ");
        iter = dead.iterator();
        while(iter.hasNext())
        {
            Tag deadTag = (Tag)iter.next();
            System.out.println(deadTag.getName()+" char:"+deadTag.getPosition());
        }
    }
}
