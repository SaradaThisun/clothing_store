package clothes.store;

import View.login_view;
import Model.login_model;
import Controller.login_controller;

public class ClothesStore {

    public static void main(String[] args) {
        // Optional: make the UI look modern
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //  Create MVC components
        login_view view = new login_view();
        login_model model = new login_model();
        login_controller controller = new login_controller(view, model);

        // Show the login window
        java.awt.EventQueue.invokeLater(() -> view.setVisible(true));
    }
}
