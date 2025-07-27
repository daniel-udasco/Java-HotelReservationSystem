import java.io.Serializable;

public class Customer implements Serializable {
    private String name;
    private String contact;

    public Customer(String name, String contact)
    {
        this.name = name;
        this.contact = contact;
    }

    public String getName()
    {
        return name;
    }

    public String getContact()
    {
        return contact;
    }

    @Override
    public String toString() {
        return name + " (" + contact + ")";
    }
}
