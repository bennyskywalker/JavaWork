import Parser.HTMLParser;
import Parser.IHTMLParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        IHTMLParser htmlParser;
        TagAnalyzer tagAnalyzer;
        StringBuilder strBuilder;
        String input;

        System.out.println("Hello World!");

        //test reading stdin - from a pipe of a test.txt file
        //Print out.
        strBuilder = new StringBuilder();

        try{
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(System.in));



            while((input=br.readLine())!=null){
                System.out.print("ln:");
                System.out.println(input);
                strBuilder.append(input);
            }

        }catch(IOException io){
            io.printStackTrace();
        }

        System.out.println(strBuilder.toString());

        tagAnalyzer = new TagAnalyzer();


        //Parse the html
        //testing string parsing
        htmlParser = new HTMLParser();
        htmlParser.addListener(tagAnalyzer);   //add the tag analyzer - recieves start/end tags
        htmlParser.parse(strBuilder.toString());
    }
}
