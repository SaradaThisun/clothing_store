package Controller;

import Model.dashboard_model;
import View.dashboard_view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class dashboard_controller {

    private final dashboard_view view;
    private final dashboard_model model;

    public dashboard_controller(dashboard_view view, dashboard_model model) {
        this.view = view;
        this.model = model;

      
        this.view.addItemsListener(new ItemsListener());
        this.view.addOrderDetailsListener(new OrderDetailsListener());
        this.view.addReportsListener(new ReportsListener());

        
        showSummary();
    }

    private void showSummary() {
        int itemCount = model.getItemCount();
        int orderCount = model.getOrderCount();
        int detailCount = model.getOrderDetailCount();

        System.out.println("Items: " + itemCount);
        System.out.println("Orders: " + orderCount);
        System.out.println("Order Details: " + detailCount);
    }

    
    class ItemsListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            View.inventory_view invView = new View.inventory_view();
            Model.inventory_model invModel = new Model.inventory_model();
            new Controller.inventory_controller(invView, invModel);
            invView.setVisible(true);
            view.dispose();
        }
    }


   
    class OrderDetailsListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            View.order_details_view orderView = new View.order_details_view();
            Model.order_details_model orderModel = new Model.order_details_model();
            new Controller.order_details_controller(orderView, orderModel);
            orderView.setVisible(true);
            view.dispose();
        }
    }

        
        class ReportsListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(view, "Opening Reports...");
                // new reports_view().setVisible(true);
                // view.dispose();
            }
        }
    }

