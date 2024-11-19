import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Event Class
class Event {
    private int eventId;
    private String eventName;
    private String description;
    private String date;
    private String location;
    private String organizer;
    private double ticketPrice;
    private String duration;
    private int capacity;
    private int ticketsSold;
    private String eventType;
    private String contactNumber;
    private String additionalInfo;

    public Event(int eventId, String eventName, String description, String date, String location,
                 String organizer, double ticketPrice, String duration, int capacity,
                 String eventType, String contactNumber, String additionalInfo) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.description = description;
        this.date = date;
        this.location = location;
        this.organizer = organizer;
        this.ticketPrice = ticketPrice;
        this.duration = duration;
        this.capacity = capacity;
        this.eventType = eventType;
        this.contactNumber = contactNumber;
        this.additionalInfo = additionalInfo;
        this.ticketsSold = 0; // Initialize tickets sold to 0
    }

    public String getEventName() {
        return eventName;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public boolean isFullyBooked() {
        return ticketsSold >= capacity;
    }

    public void incrementTicketsSold() {
        if (!isFullyBooked()) {
            ticketsSold++;
        }
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    @Override
    public String toString() {
        return "<html><b>Event Name:</b> " + eventName + "<br>" +
                "<b>Description:</b> " + description + "<br>" +
                "<b>Date:</b> " + date + "<br>" +
                "<b>Location:</b> " + location + "<br>" +
                "<b>Organizer:</b> " + organizer + "<br>" +
                "<b>Ticket Price:</b> ₹" + ticketPrice + "<br>" +
                "<b>Duration:</b> " + duration + "<br>" +
                "<b>Capacity:</b> " + capacity + "<br>" +
                "<b>Tickets Sold:</b> " + ticketsSold + "<br>" +
                "<b>Event Type:</b> " + eventType + "<br>" +
                "<b>Contact:</b> " + contactNumber + "<br>" +
                "<b>Additional Info:</b> " + additionalInfo + "</html>";
    }
}

// Booking Class
class Booking {
    private String userName;
    private Event event;

    public Booking(String userName, Event event) {
        this.userName = userName;
        this.event = event;
    }

    @Override
    public String toString() {
        return "Booking for " + userName + " at event: " + event.getEventName();
    }
}

// PaymentSystem Class
class PaymentSystem {
    public boolean processPayment(double amount) {
        String[] paymentOptions = {"Card Payment", "UPI Payment", "QR Code Payment"};
        String selectedOption = (String) JOptionPane.showInputDialog(
                null,
                "Choose your payment method:",
                "Payment Method",
                JOptionPane.QUESTION_MESSAGE,
                null,
                paymentOptions,
                paymentOptions[0]
        );

        if (selectedOption != null) {
            switch (selectedOption) {
                case "Card Payment":
                    return processCardPayment(amount);

                case "UPI Payment":
                    return processUPIPayment(amount);

                case "QR Code Payment":
                    return processQRCodePayment(amount);

                default:
                    JOptionPane.showMessageDialog(null, "Invalid payment method selected.");
            }
        }
        return false;
    }

    private boolean processCardPayment(double amount) {
        JTextField cardNumberField = new JTextField(16);
        JPasswordField cvvField = new JPasswordField(3);
        Object[] message = {
                "Card Number (16 digits):", cardNumberField,
                "CVV (3 digits):", cvvField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Enter Card Details", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String cardNumber = cardNumberField.getText();
            String cvv = new String(cvvField.getPassword());

            if (validateCardDetails(cardNumber, cvv)) {
                JOptionPane.showMessageDialog(null, "Payment of ₹" + amount + " processed successfully via Card!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid card details. Please try again.", "Payment Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }

    private boolean processUPIPayment(double amount) {
        JTextField upiIdField = new JTextField();
        Object[] message = {
                "Enter UPI ID (e.g., user@upi):", upiIdField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Enter UPI Details", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String upiId = upiIdField.getText();
            if (validateUPI(upiId)) {
                JOptionPane.showMessageDialog(null, "Payment of ₹" + amount + " processed successfully via UPI!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid UPI ID. Please try again.", "Payment Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }

    private boolean processQRCodePayment(double amount) {
        JFrame qrFrame = new JFrame("QR Code Payment");
        qrFrame.setLayout(new BorderLayout());
        qrFrame.setSize(300, 300);
        qrFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel qrLabel = new JLabel("Scan this QR Code to Pay ₹" + amount, JLabel.CENTER);
        qrFrame.add(qrLabel, BorderLayout.NORTH);

        // Mock QR Code Image (Replace this with an actual QR code image in real applications)
        JLabel qrImage = new JLabel(new ImageIcon("path_to_your_qr_code_image.png")); // Add your QR code image path
        qrFrame.add(qrImage, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Payment Completed");
        confirmButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(qrFrame, "Payment of ₹" + amount + " confirmed via QR Code!");
            qrFrame.dispose();
        });

        qrFrame.add(confirmButton, BorderLayout.SOUTH);
        qrFrame.setVisible(true);

        return true; // Assuming payment is successful
    }

    private boolean validateCardDetails(String cardNumber, String cvv) {
        return cardNumber.matches("\\d{16}") && cvv.matches("\\d{3}");
    }

    private boolean validateUPI(String upiId) {
        return upiId.matches("^[\\w.-]+@[\\w]+$");
    }
}


// Main Application
public class EventManagerModernApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Apply a modern look-and-feel (FlatLaf or Nimbus)
                UIManager.setLookAndFeel("com.formdev.flatlaf.FlatIntelliJLaf");
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MainWindowWithPaymentModern();
        });
    }
}

class MainWindowWithPaymentModern {
    private JFrame frame;
    private JTabbedPane tabbedPane;
    private JPanel eventPanel, searchPanel, bookingPanel;
    private JList<String> eventList;
    private DefaultListModel<String> eventListModel;
    private JEditorPane eventDetailsArea;
    private DefaultListModel<String> bookingListModel;
    private ArrayList<Event> events;
    private ArrayList<Booking> bookings;
    private PaymentSystem paymentSystem;

    public MainWindowWithPaymentModern() {
        frame = new JFrame("Event Manager - Modern UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        events = new ArrayList<>();
        bookings = new ArrayList<>();
        paymentSystem = new PaymentSystem();

        loadSampleEvents();

        tabbedPane = new JTabbedPane();

        // Create individual panels
        createEventPanel();
        createSearchPanel();
        createBookingPanel();

        // Add panels to the tabbed pane
        tabbedPane.addTab("Events", eventPanel);
        tabbedPane.addTab("Search", searchPanel);
        tabbedPane.addTab("Bookings", bookingPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void createEventPanel() {
        eventPanel = new JPanel(new BorderLayout());
        eventListModel = new DefaultListModel<>();
        for (Event event : events) {
            String displayName = event.getEventName() + (event.isFullyBooked() ? " [Fully Booked]" : "");
            eventListModel.addElement(displayName);
        }
        eventList = new JList<>(eventListModel);
        eventList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(eventList);

        eventDetailsArea = new JEditorPane();
        eventDetailsArea.setEditable(false);
        eventDetailsArea.setContentType("text/html");
        JScrollPane detailsScrollPane = new JScrollPane(eventDetailsArea);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, detailsScrollPane);
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0.3);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton bookButton = new JButton("Book Event");
        bookButton.addActionListener(e -> bookEvent());
        buttonPanel.add(bookButton);

        eventPanel.add(splitPane, BorderLayout.CENTER);
        eventPanel.add(buttonPanel, BorderLayout.SOUTH);

        eventList.addListSelectionListener(e -> {
            String selectedEventName = eventList.getSelectedValue();
            Event selectedEvent = getEventByName(selectedEventName);
            if (selectedEvent != null) {
                eventDetailsArea.setText(selectedEvent.toString());
            }
        });
    }

    private void createSearchPanel() {
        searchPanel = new JPanel(new BorderLayout());
        JTextField searchField = new JTextField();
        searchField.setToolTipText("Enter event type to search (e.g., 'concert')");
        JButton searchButton = new JButton("Search");

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        JEditorPane searchResults = new JEditorPane();
        searchResults.setEditable(false);
        searchResults.setContentType("text/html");

        searchPanel.add(topPanel, BorderLayout.NORTH);
        searchPanel.add(new JScrollPane(searchResults), BorderLayout.CENTER);

        searchButton.addActionListener(e -> {
            String query = searchField.getText().toLowerCase();
            if (query.isEmpty()) {
                searchResults.setText("<html><i>Please enter a search term.</i></html>");
                return;
            }

            StringBuilder results = new StringBuilder();
            for (Event event : events) {
                if (event.getEventName().toLowerCase().contains(query)) {
                    results.append(event.toString()).append("<hr>");
                }
            }

            if (results.length() == 0) {
                searchResults.setText("<html><i>No events found for your search query.</i></html>");
            } else {
                searchResults.setText("<html>" + results + "</html>");
            }
        });
    }

    private void createBookingPanel() {
        bookingPanel = new JPanel(new BorderLayout());
        bookingListModel = new DefaultListModel<>();
        JList<String> bookingList = new JList<>(bookingListModel);
        bookingPanel.add(new JScrollPane(bookingList), BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> updateBookingList());
        bookingPanel.add(refreshButton, BorderLayout.SOUTH);
    }

    private void loadSampleEvents() {
        events.add(new Event(1, "Concert Night", "An amazing night of live music.", "2024-12-05", "New York", "Rock Band Inc.", 100, "3 hours", 500, "Concert", "555-1234", "Be there early!"));
        events.add(new Event(2, "Tech Expo", "A showcase of the latest tech innovations.", "2024-12-10", "San Francisco", "Tech World", 150, "6 hours", 300, "Expo", "555-5678", "Bring your gadgets."));
    }

    private Event getEventByName(String eventName) {
        for (Event event : events) {
            if (event.getEventName().equals(eventName)) {
                return event;
            }
        }
        return null;
    }

    private void bookEvent() {
        String selectedEventName = eventList.getSelectedValue();
        if (selectedEventName == null) {
            JOptionPane.showMessageDialog(frame, "Please select an event to book.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Event selectedEvent = getEventByName(selectedEventName);
        if (selectedEvent == null || selectedEvent.isFullyBooked()) {
            JOptionPane.showMessageDialog(frame, "Sorry, this event is fully booked.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame, "The ticket price is ₹" + selectedEvent.getTicketPrice() + ". Proceed to payment?");
        if (confirm == JOptionPane.YES_OPTION) {
            boolean paymentSuccess = paymentSystem.processPayment(selectedEvent.getTicketPrice());
            if (paymentSuccess) {
                String userName = JOptionPane.showInputDialog(frame, "Enter your name to confirm the booking:");
                if (userName != null && !userName.trim().isEmpty()) {
                    selectedEvent.incrementTicketsSold();
                    bookings.add(new Booking(userName, selectedEvent));
                    updateBookingList();
                    JOptionPane.showMessageDialog(frame, "Booking successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private void updateBookingList() {
        bookingListModel.clear();
        for (Booking booking : bookings) {
            bookingListModel.addElement(booking.toString());
        }
    }
}
