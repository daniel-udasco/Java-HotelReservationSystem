import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Hotel_Reservation_System {
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
            System.out.println("\n============================");
            System.out.println("  Hotel Reservation System");
            System.out.println("============================");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel a Reservation");
            System.out.println("4. View Reservations");
            System.out.println("5. Credits");
            System.out.println("6. Exit");
            System.out.println("============================");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice)
            {
                case 1 -> viewAvailableRooms();
                case 2 -> bookRoom(scanner);
                case 3 -> cancelReservation(scanner);
                case 4 -> viewReservations();
                case 5 -> showCredits();
                case 6 -> System.out.println("Exiting the system...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);

        saveReservations();
        System.out.println("Reservations saved. Goodbye!");
    }

    private static void initializeRooms()
    {
        rooms.add(new Room(101, "Single", 1));
        rooms.add(new Room(102, "Double", 2));
        rooms.add(new Room(103, "Suite", 4));
        rooms.add(new Room(104, "Double", 2));
        rooms.add(new Room(105, "Double", 2));
        rooms.add(new Room(106, "Single", 1));
        rooms.add(new Room(107, "Cabana", 2));
        rooms.add(new Room(108, "Suite", 4));
        rooms.add(new Room(109, "Double", 2));
        rooms.add(new Room(110, "Cabana", 3));
        rooms.add(new Room(111, "Double", 2));
        rooms.add(new Room(112, "Single", 1));
        rooms.add(new Room(113, "Single", 1));
        rooms.add(new Room(114, "Suite", 4));
        rooms.add(new Room(115, "Double", 2));
    }

    private static void viewAvailableRooms()
    {
        System.out.println("\nRoom Availability:");
        for (Room room : rooms) {
            String status = room.isBooked() ? "Unavailable" : "Available";
            System.out.println("Room " + room.getRoomNumber() + " (" + room.getType() + ", Capacity: " + room.getCapacity() + ") - " + status);
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

        if (room == null)
        {
            System.out.println("Room does not exist.");
            return;
        }

        if (room.isBooked())
        {
            System.out.println("Room is already booked.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        try
        {
            System.out.print("Enter check-in date (MM/DD/YYYY): ");
            LocalDate checkInDate = LocalDate.parse(scanner.next(), formatter);
            System.out.print("Enter check-out date (MM/DD/YYYY): ");
            LocalDate checkOutDate = LocalDate.parse(scanner.next(), formatter);

            if (!checkAvailability(room, checkInDate, checkOutDate))
            {
                System.out.println("Room is not available for the selected dates.");
                return;
            }

            Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
            reservations.add(reservation);
            room.setBooked(true);
            System.out.println("Room booked successfully!");
        } catch (DateTimeParseException e){
            System.out.println("Invalid date format. Please use MM/DD/YYYY.");
        }
    }

    private static boolean checkAvailability(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        for (Reservation reservation : reservations)
        {
            if (reservation.getRoom().getRoomNumber() == room.getRoomNumber())
            {
                if ((checkInDate.isBefore(reservation.getCheckOutDate()) && checkOutDate.isAfter(reservation.getCheckInDate()))
                        || checkInDate.equals(reservation.getCheckInDate()) || checkOutDate.equals(reservation.getCheckOutDate())) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void cancelReservation(Scanner scanner)
    {
        System.out.print("Enter room number to cancel: ");
        int roomNumber = scanner.nextInt();
        Reservation reservationToRemove = null;

        for (Reservation reservation : reservations)
        {
            if (reservation.getRoom().getRoomNumber() == roomNumber)
            {
                reservationToRemove = reservation;
                break;
            }
        }

        if (reservationToRemove != null)
        {
            reservations.remove(reservationToRemove);
            reservationToRemove.getRoom().setBooked(false);
            System.out.println("Reservation cancelled successfully.");
        } else{
            System.out.println("No reservation found for the given room number.");
        }
    }

    private static void viewReservations()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        if (reservations.isEmpty())
        {
            System.out.println("\nNo reservations available.");
            return;
        }
        System.out.println("\nCurrent Reservations:");
        for (Reservation reservation : reservations)
        {
            System.out.println("Customer: " + reservation.getCustomer());
            System.out.println("Room: " + reservation.getRoom());
            System.out.println("Check-in: " + reservation.getCheckInDate().format(formatter));
            System.out.println("Check-out: " + reservation.getCheckOutDate().format(formatter));
            System.out.println();
        }
    }

    private static void showCredits()
    {
        System.out.println("=======================================");
        System.out.println("\t\tCredits");
        System.out.println("=======================================");
        System.out.println("Daniel Udasco - Lead Programmer");
        System.out.println("Rica Galagate - Feature Designer");
        System.out.println("Stephen Paspie - Software Tester");
        System.out.println("---------------------------------------");
        System.out.println("More like this on Github:");
        System.out.println("https://github.com/danieldan64/");
    }

    private static Room findRoom(int roomNumber) {
        for (Room room : rooms)
        {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    private static void loadReservations() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME)))
        {
            reservations = (List<Reservation>) ois.readObject();
            System.out.println("Reservations loaded successfully: " + reservations.size() + " entries.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous reservations found: " + e.getMessage());
        }
    }

    private static void saveReservations()
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(reservations);
            System.out.println("Reservations saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving reservations: " + e.getMessage());
        }
    }
}
