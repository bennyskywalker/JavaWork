import Models.TestCase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static public final int LookingForTestLineCount = 0;
    static public final int ReadingTestLines = 1;
    static public final int TerminateProgram = 2;

    /**
     * HTML Syntax Checker
     * @param args
     */
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

        try
        {
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
                }
            }

        }catch(IOException io){
            io.printStackTrace();
        }


    }
}
