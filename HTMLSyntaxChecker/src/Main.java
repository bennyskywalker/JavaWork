import Models.TestCase;
import Parser.HTMLParser;
import Parser.IHTMLParser;
import TestCaseUtils.TagAnalyzer;
import TestCaseUtils.TagDebugger;
import TestCaseUtils.TagError;

import java.io.*;
import java.util.List;

public class Main {

    static public final int LookingForTestLineCount = 0;
    static public final int ReadingTestLines = 1;
    static public final int TerminateProgram = 2;

    public static void main(String[] args) {

        //States that this reader can be in
        String input;
        int testNum;
        int numLines;
        int numExpectedLines;
        int readingState = Main.LookingForTestLineCount;
        TestCase testCase = null;


        //Read in the lines of the stdin - each set is a test - so we will need to validate
        //each test during each read.
        //Read in a bundle, store each line in an Object - so that we can tell where
        //we are in the parsing to give the correct error.
        String testData = "1 \n" +
                "    Missing start symbol:   <OK></OK></NOTOK>   more garbage... \n";

        try
        {
            //Test string for debugger
            /*
            InputStream is = new ByteArrayInputStream(testData.getBytes());
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(is));
            */
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(System.in));

            //Read each line - and group them into tests
            testNum = 0;
            numLines = 0;
            numExpectedLines = 0;

            while((input=br.readLine())!=null && readingState!=Main.TerminateProgram)
            {
                switch(readingState)
                {
                    case Main.LookingForTestLineCount:
                        numExpectedLines = Integer.parseInt(input.trim());
                        readingState = Main.ReadingTestLines;
                        numLines = 0;

                        if(numExpectedLines==0)
                        {
                            //Program Ends
                            readingState = Main.TerminateProgram;
                        }
                        else
                        {

                            //Create a new test case
                            testCase = new TestCase(testNum); //Remember tests are ref from 0 - but start at 1 in the output
                        }
                        break;
                    case Main.ReadingTestLines:
                        //Each one of these represents a test line
                        if(testCase!=null)
                            testCase.addTestLine(input);
                        numLines++;
                        break;
                }
                //strBuilder.append(input);

                //Switch state if we have read all the lines for this current
                //test case - and add the test case to the test cases
                if(numLines>=numExpectedLines && readingState!=Main.TerminateProgram)
                {
                    readingState = Main.LookingForTestLineCount;

                    //Parse out this test case
                    //testCase.debugStdOut();  //This debugs to screen - removing for now.

                    //Now we want to parse the HTML for correctness
                    //I want to create a class - that I can dump the test case into
                    //To analize in real time.
                    IHTMLParser parser = new HTMLParser();

                    //the html parser does not contain the anaylsis of the tags.
                    //We'll introduce a tag analyzer here.
                    TagAnalyzer tagAnalyzer = new TagAnalyzer();
                    TagDebugger tagDebugger = new TagDebugger();
                    parser.addListener(tagAnalyzer);
                    //parser.addListener(tagDebugger); //only added to visual tag errors

                    parser.parse(testCase);

                    //Talk to the tag Analyzer to see what went wrong if anything.
                    List<TagError> tagErrors = tagAnalyzer.getErrorList();
                    TagError tagError = tagErrors.get(0);
                    String errorMsg;

                    switch(tagError.getErrorCode())
                    {
                        case TagError.OK:
                            errorMsg = String.format("Test Case %d\nOK", testNum+1);
                            System.out.println(errorMsg);
                            break;
                        case TagError.EXPECTEDTAG:
                            errorMsg = String.format("Test Case %d\nline %d: %s %s",
                                    testNum+1,
                                    tagError.getLineNumber(),
                                    TagError.decodeError(tagError.getErrorCode()),
                                    tagError.getTagName());
                            System.out.println(errorMsg);
                            break;
                        default:
                            errorMsg = String.format("Test Case %d\nline %d: %s",
                                    testNum+1,
                                    tagError.getLineNumber(),
                                    TagError.decodeError(tagError.getErrorCode()));
                            System.out.println(errorMsg);
                            break;
                    }
                    System.out.println("");
                    testNum++;
                }
            }

        }catch(IOException io){
            io.printStackTrace();
        }


        /*
        IHTMLParser htmlParser;
        TestCaseUtils.TagAnalyzer tagAnalyzer;
        TestCaseUtils.TagDebugger tagDebugger;
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


        tagAnalyzer = new TestCaseUtils.TagAnalyzer();
        tagDebugger = new TestCaseUtils.TagDebugger();

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
        */
    }
}
