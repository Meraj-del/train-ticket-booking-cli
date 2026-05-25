package ticket.booking;

import ticket.booking.entity.Train;
import ticket.booking.entity.User;
import ticket.booking.services.UserBookingService;
import ticket.booking.util.UserServiceUtil;

import java.util.*;

public class App {
    public static void main(String[] args) {
        System.out.println("Running Train Booking System");
        Scanner input = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService;
        Train trainSelectedForBooking = null;

        try {
            userBookingService = new UserBookingService();
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
            return;
        }

        while (option != 7) {
            System.out.println("\nChoose one of the following:");
            System.out.println("1: Sign up");
            System.out.println("2: Login");
            System.out.println("3: Fetch booking");
            System.out.println("4: Search Trains");
            System.out.println("5: Book a Seat");
            System.out.println("6: Cancel my Booking");
            System.out.println("7: Exit");

            try {
                option = Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input");
                continue;
            }

            switch (option) {
                case 1:
                    System.out.print("Enter your username: ");
                    String username = input.nextLine();
                    System.out.print("Enter your password: ");
                    String password = input.nextLine();

                    User newUser = new User(
                            UUID.randomUUID().toString(),
                            username,
                            UserServiceUtil.hashPassword(password),
                            new ArrayList<>()
                    );
                    if (userBookingService.signUp(newUser)) {
                        System.out.println("Sign up successful!");
                    } else {
                        System.out.println("Username already exists!");
                    }
                    break;

                case 2:
                    System.out.print("Enter your username: ");
                    String userName = input.nextLine();
                    System.out.print("Enter your password: ");
                    String loginPassword = input.nextLine();

                    User loginUser = new User(null, userName, loginPassword, null);
                    userBookingService.setCurrentUser(loginUser);

                    if (userBookingService.loginUser()) {
                        System.out.println("Login successful!");
                    } else {
                        System.out.println("Invalid username or password!");
                    }
                    break;

                case 3:
                    System.out.println("Fetching bookings...");
                    userBookingService.fetchBookings();
                    break;

                case 4:
                    System.out.print("Type your source Station: ");
                    String sourceStation = input.nextLine();
                    System.out.print("Type your destination Station: ");
                    String destinationStation = input.nextLine();

                    List<Train> trains = userBookingService.getTrains(sourceStation, destinationStation);
                    if (trains.isEmpty()) {
                        System.out.println("No trains found!");
                        break;
                    }

                    for (int i = 0; i < trains.size(); i++) {
                        System.out.println((i + 1) + ": " + trains.get(i).getTrainInfo()
                                + " | Available Seats: " + trains.get(i).getAvailableSeats());
                    }

                    System.out.print("Select a train: ");
                    int index = Integer.parseInt(input.nextLine()) - 1;
                    if (index >= 0 && index < trains.size()) {
                        trainSelectedForBooking = trains.get(index);
                        System.out.println("Train selected: " + trainSelectedForBooking.getTrainInfo());
                    } else {
                        System.out.println("Invalid selection!");
                    }
                    break;

                case 5:
                    if (trainSelectedForBooking == null) {
                        System.out.println("No train selected! Search and select a train first.");
                        break;
                    }
                    System.out.print("Enter source station: ");
                    String src = input.nextLine();
                    System.out.print("Enter destination station: ");
                    String dest = input.nextLine();
                    try {
                        if (userBookingService.bookTicket(trainSelectedForBooking, src, dest)) {
                            System.out.println("Ticket booked successfully!");
                            System.out.println("Remaining seats: " + trainSelectedForBooking.getAvailableSeats());
                        } else {
                            System.out.println("Booking failed!");
                        }
                    } catch (Exception e) {
                        System.out.println("Error booking ticket: " + e.getMessage());
                    }
                    break;


                case 6:
                    System.out.print("Enter Ticket ID to cancel: ");
                    String ticketId = input.nextLine();
                    try {
                        if (userBookingService.cancelTickets(ticketId)) {
                            System.out.println("Ticket cancelled successfully!");
                        } else {
                            System.out.println("Ticket not found!");
                        }
                    } catch (Exception e) {
                        System.out.println("Error cancelling ticket: " + e.getMessage());
                    }
                    break;

                case 7:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}
