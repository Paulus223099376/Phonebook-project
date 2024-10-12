import java.io.Serializable;

public class Contact implements Comparable<Contact>, Serializable {
    private String name;
    private String phoneNumber;
    private String category;

    public Contact(String name, String phoneNumber, String category) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return name + " - " + phoneNumber + " (" + category + ")";
    }

    @Override
    public int compareTo(Contact other) {
        return this.name.compareToIgnoreCase(other.name);
    }
}