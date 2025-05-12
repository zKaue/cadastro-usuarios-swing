package gerenciarusuarios.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Placeholder {
    public static void setPlaceholder(JTextField textField, String placeholder) {
    	
    	 Color placeholderColor = new Color(130, 130, 130); // Cinza discreto ideal para tema escuro
         textField.setForeground(placeholderColor);
         textField.setText(placeholder);
        
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(UIManager.getColor("TextField.foreground"));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    setPlaceholder(textField, placeholder);
                }
            }
        });
    }
}