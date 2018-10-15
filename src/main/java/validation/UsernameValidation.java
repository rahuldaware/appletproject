package validation;

import javax.swing.*;

public class UsernameValidation extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        return text.length() > 4;
    }
}
