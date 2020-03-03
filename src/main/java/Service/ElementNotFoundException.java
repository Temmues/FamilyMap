package Service;

public class ElementNotFoundException extends Exception
{
    ElementNotFoundException(String message)
    {
        super(message);
    }

    ElementNotFoundException()
    {
        super();
    }
}
