import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class PasswordGenerator extends JFrame {

    // Custom Colors
    private Color blue = new Color(48, 129, 237);
    private Color backgroundColor = new Color(22, 27, 34);
    private Color coal = new Color(27, 34, 43);

    private JLabel title = new JLabel("Password Generator");
    private JLabel passwordLabel;

    private JButton generateButton;

    private JPanel mainPane, checkBoxPane, buttonPane, passwordPane, tfPane;

    JCheckBox[] checkboxes = new JCheckBox[6];
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
            light = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Nunito-Regular.ttf"));
            regular = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Nunito-Medium.ttf"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        // Create a new Font Object
        Font lightFont = light.deriveFont(Font.PLAIN, 20);
        Font regularFont = regular.deriveFont(Font.PLAIN, 14);
        Font passFont = light.deriveFont(Font.PLAIN, 13);
        Font passLenFont = light.deriveFont(Font.PLAIN, 14);

        // Title Configuration
        title.setFont(lightFont);
        title.setForeground(Color.WHITE);

        // Checkbox Configuration
        checkBoxPane = new JPanel(new GridLayout(6, 1));
        checkBoxPane.setOpaque(false);

        checkboxes[0] = new JCheckBox("Uppercase Letters");
        checkboxes[1] = new JCheckBox("Lowercase Letters");
        checkboxes[2] = new JCheckBox("Numbers");
        checkboxes[3] = new JCheckBox("Special Characters");
        checkboxes[4] = new JCheckBox("Exclude Similar Characters");
        checkboxes[5] = new JCheckBox("Exclude Ambiguous Characters");

        checkBoxConfig(regularFont);

        // Password Length Configuration(8-20)
        tfPane = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = 1;
        g.insets = new Insets(0, 0, 10, 0);
        g.anchor = GridBagConstraints.WEST;

        passwordLabel = new JLabel("Enter Password Length (Maximum 32 Characters):");
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
        tfPane.add(tf, g);
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

        // Frame Configuration
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    String extractedSet(String type) {
        Random random = new Random();
        int desiredLength = random.nextInt(type.length()) + 1;

        // Extract the desired number of characters from the string
        StringBuilder extractedCharacters = new StringBuilder();
        for (int i = 0; i < desiredLength; i++) {
            int randomIndex = random.nextInt(type.length());
            extractedCharacters.append(type.charAt(randomIndex));
        }

        // Use the extracted characters for further processing
        String extractedString = extractedCharacters.toString();
        return extractedString;
    }

    private String generatePassword() {

        // Character Sets
        String defaultCharacterSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()";
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialCharacters = "!#$%&*=?@";
        String ambiguousCharacters = "{}[]()/\\\\'\"`~,^_\\-.:;<>,]";
        String similarCharacters = "il1Lo0O";
        StringBuilder passwordBuilder = new StringBuilder();

        // Add Uppercase letters
        if (checkboxes[0].isSelected()) {
            passwordBuilder.append(extractedSet(uppercaseLetters));
            passwordBuilder.append(defaultCharacterSet);
        }

        // Add Lowercase letters
        if (checkboxes[1].isSelected()) {
            passwordBuilder.append(extractedSet(lowercaseLetters));
            passwordBuilder.append(defaultCharacterSet);

        }

        // Add Numbers
        if (checkboxes[2].isSelected()) {
            passwordBuilder.append(extractedSet(numbers));
            passwordBuilder.append(defaultCharacterSet);
        }

        // Add Special Characters
        if (checkboxes[3].isSelected()) {
            passwordBuilder.append(specialCharacters);
            passwordBuilder.append(defaultCharacterSet);
        }

        // Exclude Similar Characters
        if (checkboxes[4].isSelected()) {
            passwordBuilder = new StringBuilder(
                    passwordBuilder.toString().replaceAll("[" + similarCharacters + "]", ""));
            passwordBuilder.append(defaultCharacterSet);
        }

        // Exclude Ambiguous Characters
        if (checkboxes[5].isSelected()) {
            passwordBuilder = new StringBuilder(
                    passwordBuilder.toString().replaceAll("[" + ambiguousCharacters + "]", ""));
            passwordBuilder.append(defaultCharacterSet);
        }

        String passwordCharacterSet = passwordBuilder.toString();

        // If no checkboxes are selected
        if (passwordBuilder.length() == 0) {
            passwordCharacterSet += defaultCharacterSet;
        }

        StringBuilder password = new StringBuilder();
        int passwordLength = 8;

        // Password Length Validation
        if (tf.getText().isEmpty()) {
            passwordLength = (int) (8 + (Math.random() * 32));
            ;
        } else {
            // Trim leading/trailing whitespaces
            String tfText = tf.getText().trim();
            if (!tfText.isEmpty()) {
                try {
                    int tfLength = Integer.parseInt(tfText);
                    if (tfLength >= 8 && tfLength <= 32) {
                        passwordLength = tfLength;
                    } else if (tfLength < 8) {
                        passwordLength = 8;
                    } else {
                        passwordLength = 32;
                    }
                } catch (NumberFormatException e) {
                    // Handle invalid input
                    passwordLength = 8;
                }
            } else {
                passwordLength = 8;
            }
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