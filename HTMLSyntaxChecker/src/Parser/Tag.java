package Parser;

/**
 * Created with IntelliJ IDEA.
 * User: btsui
 * Date: 30/10/13
 * Time: 7:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class Tag {
    private String name;
    private int characterPosition;
    private int lineNumber;

    public Tag(String name, int characterPosition, int lineNumber)
    {
        this.name = name;
        this.characterPosition = characterPosition;
        this.lineNumber = lineNumber;
    }

    public String getName()
    {
        return this.name;
    }

    public int getLineNumber()
    {
        return this.lineNumber;
    }

    public int getPosition()
    {
        return this.characterPosition;
    }

    public boolean equals(Object anObject)
    {
        if(anObject instanceof Tag)
        {
            if(  ((Tag)anObject).getName().equalsIgnoreCase( this.getName() ))
            {
                return true;
            }
        }

        return false;
    }
}
