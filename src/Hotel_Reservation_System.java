import java.io.*;
import java.util.*;
import java.time.LocalDate;

public class Hotel_Reservation_System
{
    private static List<Room> rooms = new ArrayList<>();
    private static List<Reservation> reservations = new ArrayList<>();
    private static final String FILE_NAME = "reservations.dat";


    public static void main(String[] args)
    {
        loadReservations();
        initializeRooms();


        Scanner scanner = new Scanner(System.in);
        int choice;

        do
        {
            System.out.println("\nHotel Reservation System");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel a Reservation");
            System.out.println("4. View Reservations");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice)
            {
                case 1 -> viewAvailableRooms();
                case 2 -> bookRoom(scanner);
                case 3 -> cancelReservation(scanner);
                case 4 -> viewReservations();
                case 5 -> saveReservations();
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void initializeRooms()
    {
        rooms.add(new Room(101, "Single", 1));
        rooms.add(new Room(102, "Double", 2));
        rooms.add(new Room(103, "Suite", 4));
        rooms.add(new Room(104, "Double", 2));
    }

    private static void viewAvailableRooms()
    {
        System.out.println("\nAvailable Rooms:");
        for (Room room : rooms)
        {
            if (!room.isBooked())
            {
                System.out.println(room);
            }
        }
    }

    private static void bookRoom(Scanner scanner)
    {
        System.out.print("Enter customer name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Enter customer contact: ");
        String contact = scanner.nextLine();
        Customer customer = new Customer(name, contact);

        System.out.print("Enter room number to book: ");
        int roomNumber = scanner.nextInt();
        Room room = findRoom(roomNumber);

        if (room == null || room.isBooked())
        {
            System.out.println("Room is not available.");
            return;
        }

        System.out.print("Enter check-in date (YYYY-MM-DD): ");
        LocalDate checkInDate = LocalDate.parse(scanner.next());
        System.out.print("Enter check-out date (YYYY-MM-DD): ");
        LocalDate checkOutDate = LocalDate.parse(scanner.next());

        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        room.setBooked(true);
        System.out.println("Room booked successfully!");
    }

    private static void cancelReservation(Scanner scanner)
    {
        System.out.print("Enter room number to cancel: ");
        int roomNumber = scanner.nextInt();
        Reservation reservationToRemove = null;

        for (Reservation reservation : reservations)
        {
            if (reservation.getRoom().getRoomNumber() == roomNumber) {
                reservationToRemove = reservation;
                break;
            }
        }

        if (reservationToRemove != null)
        {
            reservations.remove(reservationToRemove);
            reservationToRemove.getRoom().setBooked(false);
            System.out.println("Reservation cancelled successfully.");
        } else
        {
            System.out.println("No reservation found for the given room number.");
        }
    }

    private static void viewReservations()
    {
        System.out.println("\nCurrent Reservations:");
        for (Reservation reservation : reservations)
        {
            System.out.println(reservation);
        }
    }

    private static Room findRoom(int roomNumber)
    {
        for (Room room : rooms)
        {
            if (room.getRoomNumber() == roomNumber)
            {
                return room;
            }
        }
        return null;
    }

    private static void loadReservations()
    {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME)))
        {
            reservations = (List<Reservation>) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            System.out.println("No previous reservations found.");
        }
    }

    private static void saveReservations()
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME)))
        {
            oos.writeObject(reservations);
            System.out.println("Reservations saved.");
        } catch (IOException e)
        {
            System.out.println("Error saving reservations.");
        }
    }
}
