package ticket.booking.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class User {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("hashed_password")
    private String password;

    @JsonProperty("tickets_booked")
    private List<Ticket> ticketsBooked;

    public User(String userId, String name, String password, List<Ticket> ticketsBooked) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.ticketsBooked = ticketsBooked;
    }

    public User() {}

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String hashPassword) { this.password = hashPassword; }

    public List<Ticket> getTicketsBooked() { return ticketsBooked; }
    public void setTicketsBooked(List<Ticket> ticketsBooked) { this.ticketsBooked = ticketsBooked; }

    public void printTickets() {
        if (ticketsBooked == null || ticketsBooked.isEmpty()) {
            System.out.println("No tickets booked yet");
            return;
        }
        for (Ticket ticket : ticketsBooked) {
            System.out.println(ticket.getTicketInfo());
        }
    }
}
