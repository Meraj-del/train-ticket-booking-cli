package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entity.Ticket;
import ticket.booking.entity.Train;
import ticket.booking.entity.User;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UserBookingService {

    private User user;
    private List<User> userList = new ArrayList<>();
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String USER_PATH = "app/src/main/java/ticket/booking/localDB/user.json";

    public UserBookingService(User user1) throws IOException {
        this.user = user1;
        loadUserList();
    }

    public UserBookingService() throws IOException {
        loadUserList();
    }

    public List<User> loadUserList() throws IOException {
        File file = new File(USER_PATH);
        if (!file.exists()) objectMapper.writeValue(file, new ArrayList<User>());
        userList = objectMapper.readValue(file, new TypeReference<List<User>>() {});
        return userList;
    }

    public boolean loginUser() {
        Optional<User> found = userList.stream()
                .filter(u -> u.getName().equalsIgnoreCase(user.getName()) &&
                        UserServiceUtil.checkPassword(user.getPassword(), u.getPassword()))
                .findFirst();

        if (found.isPresent()) {
            user = found.get();
            return true;
        }
        return false;
    }

    public boolean signUp(User user1) {
        try {
            if (userList.stream().anyMatch(u -> u.getName().equalsIgnoreCase(user1.getName())))
                return false;
            userList.add(user1);
            saveUserListToFile();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void saveUserListToFile() throws IOException {
        objectMapper.writeValue(new File(USER_PATH), userList);
    }

    public void fetchBookings() {
        if (user == null) {
            System.out.println("Login first");
            return;
        }
        user.printTickets();
    }

    public boolean cancelTickets(String ticketId) throws IOException {
        if (user == null) {
            System.out.println("Login first");
            return false;
        }
        boolean removed = user.getTicketsBooked().removeIf(t -> t.getTicketId().equals(ticketId));
        if (removed) saveUserListToFile();
        return removed;
    }

    public List<Train> getTrains(String source, String destination) {
        try {
            return new TrainService().searchTrains(source, destination);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public boolean bookTicket(Train train, String source, String destination) throws IOException {
        if (user == null) {
            System.out.println("Login first");
            return false;
        }

        TrainService trainService = new TrainService();
        boolean seatBooked = trainService.bookSeat(train);
        if (!seatBooked) {
            System.out.println("No seats available on this train!");
            return false;
        }

        Ticket ticket = new Ticket(
                UUID.randomUUID().toString(),
                user.getUserId(),
                source,
                destination,
                new java.util.Date(),
                train
        );

        if (user.getTicketsBooked() == null) user.setTicketsBooked(new java.util.ArrayList<>());
        user.getTicketsBooked().add(ticket);

        saveUserListToFile(); // save updated tickets
        return true;
    }


    public User getCurrentUser() { return user; }
    public void setCurrentUser(User user) { this.user = user; }
}
