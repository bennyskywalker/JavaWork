package Parser;

/**
 * Created with IntelliJ IDEA.
 * User: btsui
 * Date: 29/10/13
 * Time: 5:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class Tag {
    private String name;
    private int characterPosition;

    public Tag(String name, int characterPosition)
    {
        this.name = name;
        this.characterPosition = characterPosition;
    }

    public String getName()
    {
        return this.name;
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
