package domain;

import java.util.Objects;

public class Creditcard {
    private long id;
    private long cardNumber;
    private String expdate;
    private int cvv;
    private long owner;

    public Creditcard() {
    }

    public Creditcard(long id, long cardNumber, String expdate, int cvv, long owner) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.expdate = expdate;
        this.cvv = cvv;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpdate() {
        return expdate;
    }

    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Creditcard that = (Creditcard) o;
        return id == that.id && cardNumber == that.cardNumber && cvv == that.cvv && owner == that.owner && Objects.equals(expdate, that.expdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardNumber, expdate, cvv, owner);
    }

    @Override
    public String toString() {
        return "Creditcard{" +
                "id=" + id +
                ", cardNumber=" + cardNumber +
                ", expdate='" + expdate + '\'' +
                ", cvv=" + cvv +
                ", owner=" + owner +
                '}';
    }
}
