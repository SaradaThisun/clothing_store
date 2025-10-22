package Controller;

import View.login_view;
import View.user_registration;
import Model.login_model;
import Model.user_registration_model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login_controller {

   private final login_view view;
    private final login_model model;

    public login_controller(login_view view, login_model model) {
        this.view = view;
        this.model = model;

        this.view.addbtnloginListener(new LoginListener());
        this.view.signupListener(new SignupListener()); 
    }

    
    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = view.getUserName();
            String password = view.getPassword();

            boolean isAuthenticated = model.checkLogin(username, password);

            if (isAuthenticated) {
                view.showMessage("Login Successful!");
                
                // Open Dashboard
                View.dashboard_view dashView = new View.dashboard_view();
                Model.dashboard_model dashModel = new Model.dashboard_model();
                new Controller.dashboard_controller(dashView, dashModel);
                dashView.setVisible(true);
                view.dispose();
            } else {
                view.showMessage("Invalid Username or Password.");
            }
        }
    }

    // Handles Sign Up button
    class SignupListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Open the registration page
            user_registration regView = new user_registration();
            user_registration_model regModel = new user_registration_model();
            new Controller.user_registration_controller(regView, regModel);
            regView.setVisible(true);

            // Close login page
            view.dispose();
        }
    }

}

