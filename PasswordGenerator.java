import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

public class PasswordGenerator extends JFrame {

    private JCheckBox checkbox1, checkbox2, checkbox3, checkbox4, checkbox5, checkbox6, checkbox7, checkbox8, checkbox9,
            checkbox10;
    private JButton generateButton;
    private JLabel passwordLabel;
    private JLabel title = new JLabel("Password Generator");
    private Color blue, bg, coal;

    private void setColor(JCheckBox box, Color color, Font font){
        // box.setBackground(Color.WHITE);
        box.setOpaque(false);
        box.setFocusPainted(false);
        box.setForeground(Color.GRAY);
        box.setMargin(new Insets(10, 0, 10, 20));
        box.setFont(font);
    }



    public PasswordGenerator() {
        super("Password Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Font medium = null;
        Font regular = null;
        try {
            medium = Font.createFont(Font.TRUETYPE_FONT,
                    new File("SFPRODISPLAYMEDIUM.OTF"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        try {
            regular = Font.createFont(Font.TRUETYPE_FONT,
                    new File("SFPRODISPLAYREGULAR.OTF"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }        

        // Create a new Font object with the desired font name, style, and size
        Font cFont = medium.deriveFont(Font.PLAIN, 23);
        Font bFont = regular.deriveFont(Font.BOLD, 14);
        Font dFont = medium.deriveFont(Font.BOLD, 18);

        blue = new Color(48, 129, 237);
        bg = new Color(29, 29, 29);
        coal = new Color(2, 2, 2);
        title.setFont(cFont);


        JPanel panel = new JPanel(new GridLayout(14, 1));
        panel.setBackground(bg);        

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(bg);
        JPanel passwordPanel = new JPanel();
        passwordPanel.setBackground(Color.DARK_GRAY);


        checkbox1 = new JCheckBox("Use Uppercase Letters");
        checkbox2 = new JCheckBox("Use Lowercase Letters");
        checkbox3 = new JCheckBox("Use Numbers");
        checkbox4 = new JCheckBox("Use Special Characters");
        checkbox5 = new JCheckBox("Require at least one Uppercase Letter");
        checkbox6 = new JCheckBox("Require at least one Lowercase Letter");
        checkbox7 = new JCheckBox("Require at least one Number");
        checkbox8 = new JCheckBox("Require at least one Special Character");
        checkbox9 = new JCheckBox("Exclude Similar Characters (i, l, 1, L, o, 0, O)");
        checkbox10 = new JCheckBox(
                "Exclude Ambiguous Characters ({}, [], (), /, \\, ', \", `, ~, ^, _, -, ., :, ;, <, >, ,)");

        setColor(checkbox1, bg, bFont);
        setColor(checkbox2, bg, bFont);
        setColor(checkbox3, bg,bFont);
        setColor(checkbox4, bg,bFont);
        setColor(checkbox5, bg,bFont);
        setColor(checkbox6, bg,bFont);
        setColor(checkbox7, bg,bFont);
        setColor(checkbox8, bg,bFont);
        setColor(checkbox9, bg,bFont);
        setColor(checkbox10, bg,bFont);

        generateButton = new JButton("Generate");
        generateButton.setBackground(blue);
        generateButton.setForeground(Color.WHITE);
        generateButton.setFont(bFont);
        generateButton.setPreferredSize(new Dimension(120,30));
        generateButton.setFocusPainted(false);

        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String password = generatePassword();
                passwordLabel.setText(password);
            }
        });

        passwordLabel = new JLabel();
        passwordLabel.setHorizontalAlignment(JLabel.CENTER);
        passwordLabel.setFont(dFont);
        passwordLabel.setForeground(Color.WHITE);
        title.setHorizontalAlignment(JLabel.LEFT);
        title.setForeground(Color.WHITE);


        panel.add(title);
        panel.add(checkbox1);
        panel.add(checkbox2);
        panel.add(checkbox3);
        panel.add(checkbox4);
        panel.add(checkbox5);
        panel.add(checkbox6);
        panel.add(checkbox7);
        panel.add(checkbox8);
        panel.add(checkbox9);
        panel.add(checkbox10);
        buttonPanel.add(generateButton);
        passwordPanel.add(passwordLabel);
        panel.setBorder(BorderFactory.createLineBorder(bg, 50)); // add 10 pixels of padding to all sides


        setContentPane(panel);
        add(buttonPanel);
        add(passwordPanel);

        setBackground(bg);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String generatePassword() {
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialCharacters = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
        String similarCharacters = "il1Lo0O";
        String ambiguousCharacters = "{}[]()/\\'\"`~,^_-.:;<>,";

        StringBuilder passwordBuilder = new StringBuilder();

        // Add uppercase letters
        if (checkbox1.isSelected()) {
            passwordBuilder.append(uppercaseLetters);
        }

        // Add lowercase letters
        if (checkbox2.isSelected()) {
            passwordBuilder.append(lowercaseLetters);
        }

        // Add numbers
        if (checkbox3.isSelected()) {
            passwordBuilder.append(numbers);
        }

        // Add special characters
        if (checkbox4.isSelected()) {
            passwordBuilder.append(specialCharacters);
        }

        // Exclude similar characters
        if (checkbox9.isSelected()) {
            passwordBuilder = new StringBuilder(
                    passwordBuilder.toString().replaceAll("[" + similarCharacters + "]", ""));
        }

        // Exclude ambiguous characters
        if (checkbox10.isSelected()) {
            passwordBuilder = new StringBuilder(
                    passwordBuilder.toString().replaceAll("[" + ambiguousCharacters + "]", ""));

        }

        String passwordCharacterSet = passwordBuilder.toString();

        // Check if at least one uppercase letter is required
        if (checkbox5.isSelected()) {
            boolean containsUppercaseLetter = false;
            for (int i = 0; i < uppercaseLetters.length(); i++) {
                if (passwordCharacterSet.indexOf(uppercaseLetters.charAt(i)) >= 0) {
                    containsUppercaseLetter = true;
                    break;
                }
            }
            if (!containsUppercaseLetter) {
                passwordCharacterSet += uppercaseLetters;
            }
        }

        // Check if at least one lowercase letter is required
        if (checkbox6.isSelected()) {
            boolean containsLowercaseLetter = false;
            for (int i = 0; i < lowercaseLetters.length(); i++) {
                if (passwordCharacterSet.indexOf(lowercaseLetters.charAt(i)) >= 0) {
                    containsLowercaseLetter = true;
                    break;
                }
            }
            if (!containsLowercaseLetter) {
                passwordCharacterSet += lowercaseLetters;
            }
        }

        // Check if at least one number is required
        if (checkbox7.isSelected()) {
            boolean containsNumber = false;
            for (int i = 0; i < numbers.length(); i++) {
                if (passwordCharacterSet.indexOf(numbers.charAt(i)) >= 0) {
                    containsNumber = true;
                    break;
                }
            }
            if (!containsNumber) {
                passwordCharacterSet += numbers;
            }
        }

        // Check if at least one special character is required
        if (checkbox8.isSelected()) {
            boolean containsSpecialCharacter = false;
            for (int i = 0; i < specialCharacters.length(); i++) {
                if (passwordCharacterSet.indexOf(specialCharacters.charAt(i)) >= 0) {
                    containsSpecialCharacter = true;
                    break;
                }
            }
            if (!containsSpecialCharacter) {
                passwordCharacterSet += specialCharacters;
            }
        }

        StringBuilder password = new StringBuilder();
        int passwordLength = 8; // default password length
        try {
            passwordLength = Integer.parseInt(JOptionPane.showInputDialog("Enter password length (8-64):"));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid password length. Default length of 8 will be used.");
        }
        if (passwordLength < 8 || passwordLength > 64) {
            JOptionPane.showMessageDialog(null, "Invalid password length. Default length of 8 will be used.");
            passwordLength = 8;
        }
        for (int i = 0; i < passwordLength; i++) {
            int index = (int) (Math.random() * passwordCharacterSet.length());
            password.append(passwordCharacterSet.charAt(index));
        }
        return password.toString();
    }

    public static void main(String[] args) {
        new PasswordGenerator();
    }
}