
package Controller;

import Model.inventory_model;
import View.inventory_view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;


public class inventory_controller {
    private final inventory_view view;
    private final inventory_model model;

    public inventory_controller(inventory_view view, inventory_model model) {
        this.view = view;
        this.model = model;

        
        viewAddListeners();
        loadTable();
    }

    private void viewAddListeners() {
        viewAddAction(view, view.btnadd, new AddAction());
        viewAddAction(view, view.btnupdate, new UpdateAction());
        viewAddAction(view, view.btndelete, new DeleteAction());
        viewAddAction(view, view.btnsearch, new SearchAction());
        viewAddAction(view, view.btnback, new BackAction());
    }

    private void viewAddAction(inventory_view v, JButton btn, ActionListener listener) {
        btn.addActionListener(listener);
    }

    private void loadTable() {
        DefaultTableModel modelTable = (DefaultTableModel) view.tbldetails.getModel();
        model.loadItemsToTable(modelTable);
    }

    //  add
    class AddAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (model.addItem(
                    view.itemId.getText(),
                    view.itemName.getText(),
                    view.itemQty.getText(),
                    view.itemType.getText(),
                    view.itemPrice.getText(),
                    view.itemSize.getText()
            )) {
                JOptionPane.showMessageDialog(view, "Item Added Successfully!");
                loadTable();
            } else {
                JOptionPane.showMessageDialog(view, "Error Adding Item!");
            }
        }
    }

    //  update
    class UpdateAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (model.updateItem(
                    view.itemId.getText(),
                    view.itemName.getText(),
                    view.itemQty.getText(),
                    view.itemType.getText(),
                    view.itemPrice.getText(),
                    view.itemSize.getText()
            )) {
                JOptionPane.showMessageDialog(view, "Item Updated Successfully!");
                loadTable();
            } else {
                JOptionPane.showMessageDialog(view, "Error Updating Item!");
            }
        }
    }

    //  delete
    class DeleteAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (model.deleteItem(view.itemId.getText())) {
                JOptionPane.showMessageDialog(view, "Item Deleted Successfully!");
                loadTable();
            } else {
                JOptionPane.showMessageDialog(view, "Error Deleting Item!");
            }
        }
    }

    //  search
    class SearchAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ResultSet rs = model.searchItem(view.itemId.getText());
            try {
                if (rs != null && rs.next()) {
                    view.itemName.setText(rs.getString("name"));
                    view.itemQty.setText(rs.getString("qty"));
                    view.itemType.setText(rs.getString("type"));
                    view.itemPrice.setText(rs.getString("price"));
                    view.itemSize.setText(rs.getString("size"));
                } else {
                    JOptionPane.showMessageDialog(view, "Item Not Found!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //  back
    class BackAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            View.dashboard_view dashView = new View.dashboard_view();
            Model.dashboard_model dashModel = new Model.dashboard_model();
            new Controller.dashboard_controller(dashView, dashModel);
            dashView.setVisible(true);
            view.dispose();
        }
    }
    
}

