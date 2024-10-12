import java.io.*;
import java.util.*;

public class PhoneBook {
    private List<Contact> contacts;
    private static final String FILE_NAME = "contacts.dat";

    public PhoneBook() {
        contacts = new ArrayList<>();
        loadContacts();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        sortContacts();
        saveContacts();
    }

    public Contact searchContact(String query) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(query) || contact.getPhoneNumber().contains(query)) {
                return contact;
            }
        }
        return null;
    }

    public List<Contact> advancedSearch(String query) {
        List<Contact> results = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getName().toLowerCase().contains(query.toLowerCase()) ||
                    contact.getPhoneNumber().contains(query) ||
                    contact.getCategory().toLowerCase().contains(query.toLowerCase())) {
                results.add(contact);
            }
        }
        return results;
    }

    public boolean deleteContact(String name) {
        Contact contact = searchContact(name);
        if (contact != null) {
            contacts.remove(contact);
            saveContacts();
            return true;
        }
        return false;
    }

    public boolean updateContact(String name, String newPhoneNumber, String newCategory) {
        Contact contact = searchContact(name);
        if (contact != null) {
            contact.setPhoneNumber(newPhoneNumber);
            contact.setCategory(newCategory);
            saveContacts();
            return true;
        }
        return false;
    }

    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts);
    }

    public List<Contact> getContactsByCategory(String category) {
        List<Contact> categoryContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getCategory().equalsIgnoreCase(category)) {
                categoryContacts.add(contact);
            }
        }
        return categoryContacts;
    }

    private void sortContacts() {
        Collections.sort(contacts);
    }

    private void saveContacts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadContacts() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                contacts = (List<Contact>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}