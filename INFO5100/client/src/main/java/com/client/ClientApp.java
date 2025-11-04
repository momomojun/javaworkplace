package com.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

public class ClientApp {
    // GUI components
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Login panel
    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;

    // Chat panel
    private JPanel chatPanel;
    private JTextArea chatArea;
    private JTextField messageField;

    // Network
    private NetworkService networkService;

    public ClientApp() {
        networkService = new NetworkService();
        buildGui();
    }

    // main gui seTup
    private void buildGui() {
        frame = new JFrame("JZ CHAT CLIENT");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add window close listener to disconnect when window is closed
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                networkService.disconnect();
            }
        });

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        createLoginPanel();
        createChatPanel();

        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(chatPanel, "CHAT");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // create login panel
    private void createLoginPanel() {
        loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        usernameField = new JTextField(15);
        loginPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(new JLabel("password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        passwordField = new JPasswordField(15);
        loginPanel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel();
        loginButton = new JButton("LOGIN");
        signupButton = new JButton("SIGNUP");
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginPanel.add(buttonPanel, gbc);

        // LOGIN BUTTON EVENT
        loginButton.addActionListener(e -> handleLogin());

        signupButton.addActionListener(e -> handleSignup());
    }

    // create chat panel
    private void createChatPanel() {
        chatPanel = new JPanel(new BorderLayout(5, 5));

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        chatPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        messageField = new JTextField();
        JButton sendButton = new JButton("Send");
        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        chatPanel.add(bottomPanel, BorderLayout.SOUTH);

        // send button event
        sendButton.addActionListener(e -> handleSendMessage());
        // also send on enter key in input field
        messageField.addActionListener(e -> handleSendMessage());
    }

    // event handling logic

    private void handleLogin() {
        String user = usernameField.getText();
        String pass = new String(passwordField.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Username and password cannot be empty.", "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        loginButton.setEnabled(false);
        signupButton.setEnabled(false);

        new AuthenticationWorker(user, pass, "LOGIN").execute();
    }

    private void handleSignup() {
        String user = usernameField.getText();
        String pass = new String(passwordField.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Username and password cannot be empty.", "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        loginButton.setEnabled(false);
        signupButton.setEnabled(false);

        new AuthenticationWorker(user, pass, "SIGNUP").execute();
    }

    // for connection, read and login register
    private class AuthenticationWorker extends SwingWorker<String, Void> {
        private String user;
        private String pass;
        private String commandType; // "LOGIN" or "SIGNUP"

        public AuthenticationWorker(String user, String pass, String commandType) {
            this.user = user;
            this.pass = pass;
            this.commandType = commandType;
        }

        protected String doInBackground() throws Exception {
            if (!networkService.connect("localhost", 8080)) {
                return "ERROR:CONNECT:Cannot connect to server.";
            }

            BufferedReader in = networkService.getInputStream();
            PrintWriter out = networkService.getPrintWriter();

            try {
                for (int i = 0; i < 5; i++) {
                    in.readLine(); // read "Welcome", "register or...", "SIGNUP...", "LOGIN...", "---"
                }
            } catch (IOException e) {
                return "ERROR:BANNER:Failed to read server welcome banner.";
            }

            // send the actual command
            String command = commandType + " " + user + " " + pass;
            out.println(command);

            try {
                return in.readLine();
            } catch (IOException e) {
                return "ERROR:RESPONSE:Failed to get response from server.";
            }
        }

        // after doinbackground() finished
        protected void done() {
            String response = null;
            try {
                response = get();

                if (response == null) {
                    response = "ERROR:NULL:No response from server.";
                }

                if (commandType.equals("LOGIN")) {
                    if (response.startsWith("LOGIN_SUCCESS")) {
                        switchToChatPanel();
                    } else {
                        JOptionPane.showMessageDialog(frame, "LOGIN fail: " + response, "Login Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (commandType.equals("SIGNUP")) {
                    if (response.startsWith("SIGNUP_SUCCESS")) {
                        JOptionPane.showMessageDialog(frame, "Registration successful! Please log in now.",
                                "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Registration failed: " + response, "Registration Failed",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Error during authentication: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                response = "ERROR:EXCEPTION:" + e.getMessage();
            } finally {
                if (response == null || !response.startsWith("LOGIN_SUCCESS")) {
                    loginButton.setEnabled(true);
                    signupButton.setEnabled(true);
                    networkService.disconnect();
                }
            }
        }
    }

    private void handleSendMessage() {
        String message = messageField.getText();
        if (!message.trim().toLowerCase().startsWith("/msg")) {
            String myUsername = usernameField.getText();
            chatArea.append(myUsername + ": " + message + "\n");
        }
        networkService.sendMessage(message);
        messageField.setText("");
    }

    // switch to chat panel and start listening for messages
    private void switchToChatPanel() {
        cardLayout.show(mainPanel, "CHAT");
        frame.setTitle("JZ CHAT ROOM - Logged in as: " + usernameField.getText());

        // start a background worker to listen for messages
        MessageListenerWorker listener = new MessageListenerWorker(
                networkService.getInputStream(),
                chatArea);
        listener.execute();
    }

    // USE swingworker to listen for messages in background
    // and update chat area safely on gui thread
    private class MessageListenerWorker extends SwingWorker<Void, String> {

        private BufferedReader in;
        private JTextArea chatArea;

        public MessageListenerWorker(BufferedReader in, JTextArea chatArea) {
            this.in = in;
            this.chatArea = chatArea;
        }

        // forever loop reading messages from server
        protected Void doInBackground() throws Exception {
            String serverMessage;
            try {
                while ((serverMessage = in.readLine()) != null) {
                    publish(serverMessage);
                }
            } catch (IOException e) {
                publish("--- Disconnected from server ---");
            }
            return null;
        }

        // append messages to JTextArea
        protected void process(List<String> chunks) {
            for (String message : chunks) {
                chatArea.append(message + "\n");
            }
        }
    }

    // main entry point
    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClientApp();
            }
        });
    }
}
