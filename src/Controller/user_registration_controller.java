
package Controller;

import Model.user_registration_model;
import View.user_registration;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class user_registration_controller {
    private final user_registration view;
    private final user_registration_model model;

    public user_registration_controller(user_registration view, user_registration_model model) {
        this.view = view;
        this.model = model;

        // attach register button listener
        this.view.addbtnRegisterListener(new RegisterListener());
    }

    // Inner class for handling registration
    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String userId = view.getUserId();
            String username = view.getUsername();
            String password = view.getPassword();

            // Validation before sending to DB
            if (userId.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(view, "All fields are required!");
                return;
            }

            boolean success = model.registerUser(userId, username, password);

            if (success) {
                JOptionPane.showMessageDialog(view, "User Registered Successfully!");

                // Optionally go back to login screen
                View.login_view loginView = new View.login_view();
                Model.login_model loginModel = new Model.login_model(); // if you already have one
                new Controller.login_controller(loginView, loginModel);
                loginView.setVisible(true);
                view.dispose();

            } else {
                JOptionPane.showMessageDialog(view, "Registration Failed! Try again or use a different User ID.");
            }
        }
    }
    
}
