package org.fcyt.controller;

import org.fcyt.model.Entity;
import org.fcyt.model.EntityModel;
import org.fcyt.model.EntityTableModel;
import org.fcyt.view.GUICja;
import org.fcyt.view.GUIEntity;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CajaController implements ActionListener {
    GUIEntity gui;
    EntityModel abm;
    GUICja guiForm = new GUICja(new Frame(),true);
    EntityTableModel model;
    List<Entity> list;
    JLabel[] guiLabel = {
            guiForm.jLabel1,
            guiForm.jLabel2,
            guiForm.jLabel3,
            guiForm.jLabel4,
            guiForm.jLabel5
    };
    char operation;
    String[][] listaOpciones;
    int selectedRow;

    public CajaController(EntityModel abm, GUIEntity gui) {
        this.abm = abm;
        this.gui = gui;
        addComboOptions(guiForm.jComboBox1, "empresa", "id, nombre");
        gui.btnNuevo.addActionListener(this);
        guiForm.btnGuardar.addActionListener(this);
        updateUI();
        gui.table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                selectedRow = gui.table.getSelectedRow();
                setForm();
                if(gui.table.getSelectedColumn() == abm.columnName.length) {
                    operation = 'E';
                    showForm();
                }
                if(gui.table.getSelectedColumn() == abm.columnName.length+1) {
                    int ok = JOptionPane.showConfirmDialog(gui, "¿Deseas eliminar este registro?", "Confirmar",JOptionPane.YES_NO_OPTION ,JOptionPane.QUESTION_MESSAGE);
                    if(ok==0) {
                        Entity e = model.getSelectedEntity(selectedRow);
                        abm.delete(e);
                        updateUI();
                    }
                }
            }
        });
        gui.setLocationRelativeTo(null);
        tableView();
        gui.setVisible(true);
    }

    public void updateUI() {
        list = abm.select("");
        model = new EntityTableModel(abm.columnName);
        model.setList(list);
        gui.table.setModel(model);
        tableView();
    }

    public void tableView() {
        gui.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int tableSize = 0;
        for (int column = 0; column < gui.table.getColumnCount(); column++) {
            int maxWidth = 0;

            TableCellRenderer headerRenderer = gui.table.getTableHeader().getDefaultRenderer();
            Object headerValue = gui.table.getColumnModel().getColumn(column).getHeaderValue();
            Component headerComp = headerRenderer.getTableCellRendererComponent(gui.table, headerValue, false, false, 0, column);
            int headerWidth = headerComp.getPreferredSize().width;

            for (int row = 0; row < gui.table.getRowCount(); row++) {
                TableCellRenderer renderer = gui.table.getCellRenderer(row, column);
                Component c = gui.table.prepareRenderer(renderer, row, column);
                int cellWidth = c.getPreferredSize().width;
                maxWidth = Math.max(maxWidth, cellWidth);
            }

            // Asegurarse de que la cabecera sea lo suficientemente ancha para mostrar el contenido
            headerWidth = Math.max(headerWidth, maxWidth);

            // Establecer el ancho preferido tanto para la columna como para la cabecera
            gui.table.getColumnModel().getColumn(column).setPreferredWidth(maxWidth + 30);
            gui.table.getTableHeader().getColumnModel().getColumn(column).setPreferredWidth(headerWidth + 30);

            tableSize = tableSize + maxWidth + 30;
        }

        int headerWidthTotal = 0;
        for (int column = 0; column < gui.table.getColumnCount(); column++) {
            headerWidthTotal += gui.table.getTableHeader().getColumnModel().getColumn(column).getPreferredWidth();
        }
        if (tableSize < headerWidthTotal) {
            tableSize = headerWidthTotal;
        }

        for(int i=0; i<model.getColumnCount(); i++) {
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            gui.table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == gui.btnNuevo) {
            operation = 'N';
            showForm();
            guiLabel[0].setText("REGISTRAR CAJA");
        }
        if(e.getSource()== guiForm.btnGuardar) {
            switch (operation) {
                case 'N' :
                    abm.insert(getForm());
                    clearFields();
                    updateUI();
                    guiForm.setVisible(false);
                    break;
                case 'E' :
                    abm.update(getForm());
                    clearFields();
                    updateUI();
                    guiForm.setVisible(false);
                    break;
            }
        }
    }

    public void showForm() {
        for(int i=0; i<abm.columnName.length; i++) {
            String columnName = abm.columnName[i];
            guiLabel[i+1].setText((columnName.substring(0,1).toUpperCase() + columnName.substring(1).replace("_", " ")));
        }
        guiForm.jTextField1.setEnabled(false);
        guiForm.setLocationRelativeTo(null);
        guiForm.setVisible(true);
    }

    public void addComboOptions(JComboBox combo, String empresa, String column) {
        listaOpciones = abm.selectCombo(empresa, column);
        Object[] opciones = new String[listaOpciones.length];
        for(int i=0; i< listaOpciones.length; i++) {
            opciones[i] = listaOpciones[i][1];
        }
        DefaultComboBoxModel comboModel = new DefaultComboBoxModel<>(opciones);
        combo.setModel(comboModel);
    }

    public Entity getForm() {
        Entity e = new Entity();
        abm.select("");
        e.setAttrib0(guiForm.jTextField1.getText());
        e.setAttrib1(guiForm.jTextField2.getText());
        e.setAttrib2(guiForm.jTextField3.getText());
        int selectedIndex = guiForm.jComboBox1.getSelectedIndex();
        String selectedId = listaOpciones[selectedIndex][0];
        e.setAttrib3(selectedId);
        return e;
    }

    public void setForm() {
        guiForm.jTextField1.setText(model.getSelectedEntity(selectedRow).attrib0);
        guiForm.jTextField2.setText(model.getSelectedEntity(selectedRow).attrib1);
        guiForm.jTextField3.setText(model.getSelectedEntity(selectedRow).attrib2);
    }

    public void clearFields() {
        guiForm.jTextField1.setText("");
        guiForm.jTextField2.setText("");
        guiForm.jTextField3.setText("");
    }
}
