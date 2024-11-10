import java.io.Serializable;

public class Room implements Serializable
{
    // Variables
    private int roomNumber;
    private String roomType;
    private int capacity;
    private boolean isBooked;

    public Room(int roomNumber, String roomType, int capacity)
    {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.capacity = capacity;
        this.isBooked = false;
    }

    public int getRoomNumber()
    {
        return roomNumber;
    }

    public String getRoomType()
    {
        return roomType;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public boolean isBooked()
    {
        return isBooked;
    }

    public void setBooked(boolean booked)
    {
        this.isBooked = booked;
    }

    @Override
    public String toString()
    {
        return "Room " + roomNumber + " [" + roomType + ", Capacity: " + capacity + ", " +
                (isBooked ? "Booked" : "Available") + "]";
    }
}
