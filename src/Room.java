import java.io.Serializable;

public class Room implements Serializable {
    private int roomNumber;
    private String type;
    private int capacity;
    private boolean isBooked;

    public Room(int roomNumber, String type, int capacity)
        {
        this.roomNumber = roomNumber;
        this.type = type;
        this.capacity = capacity;
        this.isBooked = false;
        }

    public int getRoomNumber()
    {
        return roomNumber;
    }

    public String getType()
    {
        return type;
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
        isBooked = booked;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + type + ", Capacity: " + capacity + ")";
    }
}
