import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class PasswordGenerator extends JFrame {

    private JLabel title = new JLabel("Password Generator");
    private JButton generateButton;
    private JLabel passwordLabel;
    private Color blue = new Color(48, 129, 237);
    private Color backgroundColor = new Color(22, 27, 34);
    private Color coal = new Color(27, 34, 43);
    private JPanel mainPane, checkBoxPane, buttonPane, passwordPane, tfPane;
    JCheckBox[] checkboxes = new JCheckBox[10];
    private JTextField tf = new JTextField(2);

    // Checkbox Configuration Method
    private void checkBoxConfig(Font font) {
        for (int i = 0; i <= checkboxes.length - 1; i++) {
            checkboxes[i].setOpaque(false);
            checkboxes[i].setFocusPainted(false);
            checkboxes[i].setForeground(new Color(137, 145, 155));
            checkboxes[i].setFont(font);
            checkboxes[i].setIconTextGap(15);
            checkBoxPane.add(checkboxes[i]);
        }
    }

    // Constructor Function
    public PasswordGenerator() {

        super("Password Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set Up Main Pane
        mainPane = new JPanel();
        mainPane.setBackground(backgroundColor);

        // Set Custom Fonts
        Font light = null;
        Font regular = null;

        try {
            light = Font.createFont(Font.TRUETYPE_FONT, new File("Segoe UI Light.ttf"));
            regular = Font.createFont(Font.TRUETYPE_FONT, new File("Segoe UI.ttf"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        // Create a new Font Object
        Font lightFont = light.deriveFont(Font.PLAIN, 28);
        Font regularFont = regular.deriveFont(Font.PLAIN, 14);
        Font passFont = light.deriveFont(Font.PLAIN, 16);
        Font passLenFont = light.deriveFont(Font.PLAIN, 16);

        // Title Configuration
        title.setFont(lightFont);
        title.setForeground(Color.WHITE);

        // Checkbox Configuration
        checkBoxPane = new JPanel(new GridLayout(10, 1));
        checkBoxPane.setOpaque(false);

        checkboxes[0] = new JCheckBox("Uppercase Letters");
        checkboxes[1] = new JCheckBox("Lowercase Letters");
        checkboxes[2] = new JCheckBox("Numbers");
        checkboxes[3] = new JCheckBox("Special Characters");
        checkboxes[4] = new JCheckBox("At least One Uppercase Letter");
        checkboxes[5] = new JCheckBox("At least One Lowercase Letter");
        checkboxes[6] = new JCheckBox("At least One Number");
        checkboxes[7] = new JCheckBox("At least One Special Character");
        checkboxes[8] = new JCheckBox("Exclude Similar Characters");
        checkboxes[9] = new JCheckBox("Exclude Ambiguous Characters");

        checkBoxConfig(regularFont);

        // Password Length Configuration(8-20)
        tfPane = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = 1;
        g.insets = new Insets(0, 0, 10, 0);
        g.anchor = GridBagConstraints.WEST;
        
        passwordLabel = new JLabel("Enter Password Length (8-20 characters):");
        passwordLabel.setFont(passLenFont);
        passwordLabel.setForeground(Color.WHITE);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridwidth = GridBagConstraints.REMAINDER; // added this line
        
        tfPane.add(passwordLabel, g);
        
        tf.setBackground(coal);
        tf.setBorder(null);
        tf.setForeground(new Color(137, 145, 155));
        tf.setFont(regularFont);
        tf.setPreferredSize(new Dimension(120, 30));
        g.gridy = 2;
        g.insets = new Insets(0, 0, 0, 0);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.WEST;
        g.gridwidth = GridBagConstraints.REMAINDER; // added this line
        g.weightx = 1.0;
        tfPane.add(tf,g);
        tfPane.setOpaque(false);
        
        // Button Configuration
        buttonPane = new JPanel(new FlowLayout());
        buttonPane.setOpaque(false);
        generateButton = new JButton("Generate");
        generateButton.setBackground(blue);
        generateButton.setForeground(Color.WHITE);
        generateButton.setFont(regularFont);
        generateButton.setPreferredSize(new Dimension(120, 30));
        generateButton.setFocusPainted(false);
        generateButton.setRolloverEnabled(false);
        buttonPane.add(generateButton);

        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String password = generatePassword();
                passwordLabel.setText(password);
            }
        });

        // Password Label Configuration
        passwordPane = new JPanel(new GridBagLayout());
        passwordPane.setPreferredSize(new Dimension(100, 40));
        GridBagConstraints gb = new GridBagConstraints();
        gb.gridx = 0;
        gb.gridy = 0;
        gb.fill = GridBagConstraints.BOTH;
        passwordLabel = new JLabel();
        passwordLabel.setFont(passFont);
        passwordLabel.setForeground(Color.WHITE);
        passwordPane.add(passwordLabel, gb);
        passwordPane.setBackground(coal);

        // Add all components to Main Panel
        setContentPane(mainPane);

        mainPane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(45, 45, 0, 200);
        mainPane.add(title, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(25, 45, 0, 45);
        gbc.fill = GridBagConstraints.BOTH;
        mainPane.add(checkBoxPane, gbc);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(15, 45, 0, 45);
        mainPane.add(tfPane, gbc);

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(25, 45, 0, 45);
        mainPane.add(buttonPane, gbc);

        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 45, 40, 45);
        mainPane.add(passwordPane, gbc);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String generatePassword() {
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialCharacters = "!#$%&*=?@";
        String ambiguousCharacters = "{}[]()/\\\\'\"`~,^_\\-.:;<>,]";
        String similarCharacters = "il1Lo0O";
        StringBuilder passwordBuilder = new StringBuilder();

        // Add Uppercase letters
        if (checkboxes[0].isSelected()) {
            passwordBuilder.append(uppercaseLetters);
        }

        // Add Lowercase letters
        if (checkboxes[1].isSelected()) {
            passwordBuilder.append(lowercaseLetters);
        }

        // Add Numbers
        if (checkboxes[2].isSelected()) {
            passwordBuilder.append(numbers);
        }

        // Add Special Characters
        if (checkboxes[3].isSelected()) {
            passwordBuilder.append(specialCharacters);
        }

        // Exclude Similar Characters
        if (checkboxes[4].isSelected()) {
            passwordBuilder = new StringBuilder(
                    passwordBuilder.toString().replaceAll("[" + similarCharacters + "]", ""));
        }

        // Exclude Ambiguous Characters
        if (checkboxes[5].isSelected()) {
            passwordBuilder = new StringBuilder(
                    passwordBuilder.toString().replaceAll("[" + ambiguousCharacters + "]", ""));
        }

        String passwordCharacterSet = passwordBuilder.toString();

        // Check if at least one uppercase letter is required
        if (checkboxes[6].isSelected()) {
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
        if (checkboxes[7].isSelected()) {
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
        if (checkboxes[8].isSelected()) {
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
        if (checkboxes[9].isSelected()) {
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
        int passwordLength = 8;
        int tfLength = Integer.parseInt(tf.getText());

        // Password Length Validation
        if(tf.getText() != null && tfLength > 8 && tfLength <= 20){
            passwordLength = Integer.parseInt(tf.getText());
        }else{
            passwordLength = 8;
        }

        // Generate Password
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