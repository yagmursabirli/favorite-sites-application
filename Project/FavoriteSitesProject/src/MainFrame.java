import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainFrame extends JFrame {
    private String username;
    private JButton updateButton;

    public MainFrame(String username) {
        this.username = username;
        setTitle("Main Frame");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(4, 2, 10, 10));
        Color backgroundColor = new Color(242, 252, 225 ); // background color
        panel.setBackground(backgroundColor);

        Color buttonColor = new Color(234, 249, 208 ); //color for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 13);


        JButton addVisitButton = new JButton("Add Visit");
        JButton deleteVisitButton = new JButton("Delete Visit");
        JButton bestFoodButton = new JButton("Best Food Locations");
        JButton visitsByYearButton = new JButton("Visits by Year");
        JButton mostVisitedButton = new JButton("Most Visited Countries");
        JButton visitedInSpringButton = new JButton("Visited in Spring");
        JButton shareFavoriteButton = new JButton("Share Favorite Visit");
        JButton displaySharedVisitButton = new JButton("Display Shared Visits");
        JButton displayImageButton = new JButton("Display Image");
        JButton getVisitDetailsButton = new JButton("Get Visit Details");
        JButton editButton = new JButton("Edit");
        editButton.setVisible(false);

        //button colors
        addVisitButton.setBackground(buttonColor);
        deleteVisitButton.setBackground(buttonColor);
        bestFoodButton.setBackground(buttonColor);
        visitsByYearButton.setBackground(buttonColor);
        mostVisitedButton.setBackground(buttonColor);
        visitedInSpringButton.setBackground(buttonColor);
        shareFavoriteButton.setBackground(buttonColor);
        displaySharedVisitButton.setBackground(buttonColor);
        displayImageButton.setBackground(buttonColor);
        getVisitDetailsButton.setBackground(buttonColor);

        //button fonts
        addVisitButton.setFont(buttonFont);
        deleteVisitButton.setFont(buttonFont);
        bestFoodButton.setFont(buttonFont);
        visitsByYearButton.setFont(buttonFont);
        mostVisitedButton.setFont(buttonFont);
        visitedInSpringButton.setFont(buttonFont);
        shareFavoriteButton.setFont(buttonFont);
        displaySharedVisitButton.setFont(buttonFont);
        displayImageButton.setFont(buttonFont);
        getVisitDetailsButton.setFont(buttonFont);




        panel.add(addVisitButton);
        panel.add(deleteVisitButton);
        panel.add(bestFoodButton);
        panel.add(visitsByYearButton);
        panel.add(mostVisitedButton);
        panel.add(visitedInSpringButton);
        panel.add(shareFavoriteButton);
        panel.add(displaySharedVisitButton);
        panel.add(displayImageButton);
        panel.add(getVisitDetailsButton);
        panel.add(editButton);




        add(panel);



        // Add Visit Functionality
        addVisitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField countryField = new JTextField();
                JTextField cityField = new JTextField();
                JTextField yearField = new JTextField();
                JTextField seasonField = new JTextField();
                JTextField featureField = new JTextField();
                JTextField commentField = new JTextField();
                JTextField ratingField = new JTextField();

                Object[] message = {
                        "Country:", countryField,
                        "City:", cityField,
                        "Year:", yearField,
                        "Season:", seasonField,
                        "Feature:", featureField,
                        "Comment:", commentField,
                        "Rating:", ratingField,
                };

                int option = JOptionPane.showConfirmDialog(null, message, "Add Visit", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String country = countryField.getText();
                    String city = cityField.getText();
                    String yearText = yearField.getText();
                    String season = seasonField.getText();
                    String feature = featureField.getText();
                    String comment = commentField.getText();
                    String ratingText = ratingField.getText();

                    if (country.isEmpty() || city.isEmpty() || yearText.isEmpty() || season.isEmpty() ||
                            feature.isEmpty() || comment.isEmpty() || ratingText.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            int year = Integer.parseInt(yearText);
                            int rating = Integer.parseInt(ratingText);

                            DatabaseHelper.addVisit(username, country, city, year, season, feature, comment, rating);
                            JOptionPane.showMessageDialog(null, "Visit added successfully.");
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Year and Rating must be valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });


        // Delete Visit Functionality
        deleteVisitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String visitIdStr = JOptionPane.showInputDialog("Enter the visit ID to delete:");
                if (visitIdStr != null && !visitIdStr.isEmpty()) {
                    try {
                        int visitId = Integer.parseInt(visitIdStr);
                        if (DatabaseHelper.isVisitIdValid(visitId)) {
                            DatabaseHelper.deleteVisit(visitId);
                            JOptionPane.showMessageDialog(null, "Visit deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Visit ID does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid visit ID format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Best Food Locations Functionality
        bestFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet rs = DatabaseHelper.getBestFoodLocations();
                try {
                    StringBuilder result = new StringBuilder();
                    while (rs.next()) {
                        result.append("Country: ").append(rs.getString("country")).append(", Rating: ").append(rs.getInt("rating")).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, result.toString(), "Best Food Locations", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Visits by Year Functionality
        visitsByYearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int year = Integer.parseInt(JOptionPane.showInputDialog("Enter year:"));
                ResultSet rs = DatabaseHelper.getVisitsByYear(year);
                try {
                    StringBuilder result = new StringBuilder();
                    while (rs.next()) {
                        result.append("Country: ").append(rs.getString("country")).append(", City: ").append(rs.getString("city")).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, result.toString(), "Visits by Year", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Most Visited Countries Functionality
        mostVisitedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet rs = DatabaseHelper.getMostVisitedCountries();
                try {
                    StringBuilder result = new StringBuilder();
                    while (rs.next()) {
                        result.append("Country: ").append(rs.getString("country")).append(", Visits: ").append(rs.getInt("visit_count")).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, result.toString(), "Most Visited Countries", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Countries Visited in Spring Functionality
        visitedInSpringButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet rs = DatabaseHelper.getCountriesVisitedInSpring();
                try {
                    StringBuilder result = new StringBuilder();
                    while (rs.next()) {
                        result.append("Country: ").append(rs.getString("country")).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, result.toString(), "Countries Visited in Spring", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Share Favorite Visit Functionality

        shareFavoriteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int visitId = Integer.parseInt(JOptionPane.showInputDialog("Enter visit ID to share:"));
                String friendUsername = JOptionPane.showInputDialog("Enter friend's username:");

                if (!DatabaseHelper.isUsernameValid(friendUsername)) {
                    JOptionPane.showMessageDialog(null, "Error: The specified user does not exist.");
                    return;
                }

                if (!DatabaseHelper.isVisitIdValid(visitId)) {
                    JOptionPane.showMessageDialog(null, "Error: The specified visit ID does not exist.");
                    return;
                }

                DatabaseHelper.shareVisit(username, friendUsername, visitId);
                JOptionPane.showMessageDialog(null, "Visit shared successfully.");
            }
        });




        // Display Shared Visits Functionality
        displaySharedVisitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet rs = DatabaseHelper.getSharedVisits(username);
                try {
                    StringBuilder result = new StringBuilder();
                    while (rs.next()) {
                        result.append("Visit ID: ").append(rs.getInt("visit_id")).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, result.toString(), "Shared Visits", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        displayImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String visitIdStr = JOptionPane.showInputDialog("Enter the visit ID:");


                if (visitIdStr == null || visitIdStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Visit ID is required.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int visitId = Integer.parseInt(visitIdStr);


                    ResultSet rs = DatabaseHelper.getVisitById(visitId);

                    if (rs != null && rs.next()) {

                        String directory = "images/";


                        String imagePath = directory + "Location" + rs.getString("visit_id") + ".jpg";


                        File imageFile = new File(imagePath);
                        if (imageFile.exists()) {
                            
                            try {
                                ImageIcon icon = new ImageIcon(imagePath);
                                JLabel label = new JLabel(icon);
                                JOptionPane.showMessageDialog(null, label, "Location Image", JOptionPane.PLAIN_MESSAGE);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Error displaying image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Image not found for the visit ID: " + visitId, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No visit found with the provided ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid visit ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error fetching visit details: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        getVisitDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String visitIdStr = JOptionPane.showInputDialog("Enter Visit ID:");
                if (visitIdStr != null && !visitIdStr.trim().isEmpty()) {
                    try {
                        int visitId = Integer.parseInt(visitIdStr);
                        ResultSet rs = DatabaseHelper.getVisitById(visitId);

                        if (rs != null && rs.next()) {
                            JTextField countryField = new JTextField(rs.getString("country"));
                            JTextField cityField = new JTextField(rs.getString("city"));
                            JTextField yearField = new JTextField(String.valueOf(rs.getInt("year")));
                            JTextField seasonField = new JTextField(rs.getString("season"));
                            JTextField featureField = new JTextField(rs.getString("feature"));
                            JTextField commentField = new JTextField(rs.getString("comment"));
                            JTextField ratingField = new JTextField(String.valueOf(rs.getInt("rating")));

                            Object[] message = {
                                    "Country:", countryField,
                                    "City:", cityField,
                                    "Year:", yearField,
                                    "Season:", seasonField,
                                    "Feature:", featureField,
                                    "Comment:", commentField,
                                    "Rating:", ratingField,
                            };

                            JButton editButton = new JButton("Edit");
                            JButton updateButton = new JButton("Update");

                            editButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    int option = JOptionPane.showOptionDialog(null, message, "Edit Visit", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{updateButton, "Cancel"}, updateButton);
                                    SwingUtilities.getWindowAncestor((Component)e.getSource()).dispose();

                                    if (option == 0) {
                                        try {
                                            String country = countryField.getText();
                                            String city = cityField.getText();
                                            int year = Integer.parseInt(yearField.getText());
                                            String season = seasonField.getText();
                                            String feature = featureField.getText();
                                            String comment = commentField.getText();
                                            int rating = Integer.parseInt(ratingField.getText());


                                            DatabaseHelper.updateVisit(visitId, country, city, year, season, feature, comment, rating);
                                            JOptionPane.showMessageDialog(null, "Visit updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                                        } catch (NumberFormatException ex) {
                                            JOptionPane.showMessageDialog(null, "Invalid input! Please enter valid values.", "Error", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                }
                            });

                            updateButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        String country = countryField.getText();
                                        String city = cityField.getText();
                                        int year = Integer.parseInt(yearField.getText());
                                        String season = seasonField.getText();
                                        String feature = featureField.getText();
                                        String comment = commentField.getText();
                                        int rating = Integer.parseInt(ratingField.getText());

                                        DatabaseHelper.updateVisit(visitId, country, city, year, season, feature, comment, rating);
                                        JOptionPane.showMessageDialog(null, "Visit updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                                        SwingUtilities.getWindowAncestor((Component)e.getSource()).dispose();
                                    } catch (NumberFormatException ex) {
                                        JOptionPane.showMessageDialog(null, "Invalid input! Please enter valid values.", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            });

                            JOptionPane.showOptionDialog(null, message, "Edit Visit", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{editButton, "Cancel"}, editButton);
                        } else {
                            JOptionPane.showMessageDialog(null, "Visit not found!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid Visit ID!", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Visit ID cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });




    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame("testuser").setVisible(true);
        });
    }
}
