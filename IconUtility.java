import javax.swing.*;
import java.awt.*;

public class IconUtility {
    public static ImageIcon createIcon(String path, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(IconUtility.class.getResource(path));
        Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    public static JButton createIconButton(String iconPath, String tooltip) {
        JButton button = new JButton(createIcon(iconPath, 24, 24));
        button.setToolTipText(tooltip);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }
}