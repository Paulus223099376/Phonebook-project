import javax.swing.*;
import java.awt.*;

class ContactListCellRenderer extends JLabel implements ListCellRenderer<Contact> {
    public ContactListCellRenderer() {
        setOpaque(true);  // Necessary to customize background colors
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Contact> list, Contact contact, int index, boolean isSelected, boolean cellHasFocus) {
        setText(contact.getName() + " - " + contact.getPhoneNumber());

        // Adjusting background colors for better contrast and selection effect
        if (isSelected) {
            setBackground(new Color(52, 152, 219));
            setForeground(Color.WHITE);
        } else {
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
        }

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));  // Add padding around text for better spacing
        return this;
    }
}