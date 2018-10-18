package model;

public class BookingModel {
    private String bookingId;
    private String source;
    private String destination;

    public BookingModel(String bookingId, String source, String destination) {
        this.bookingId = bookingId;
        this.source = source;
        this.destination = destination;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBooking_id(String booking_id) {
        this.bookingId = booking_id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
