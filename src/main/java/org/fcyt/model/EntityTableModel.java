package org.fcyt.model;

import javax.swing.table.AbstractTableModel;
import java.util.*;
public class EntityTableModel extends AbstractTableModel {
    public EntityTableModel(String[] columnNames) {
        this.columnName = new String[columnNames.length+2];
        int i=0;
        for(i=0; i<columnNames.length; i++) {
            this.columnName[i] = "action";
            this.columnName[i] = columnNames[i];
        }
        this.columnName[i] = "action";
        this.columnName[i+1] = "action";
    }

    public List<Entity> list;

    public void setList(List<Entity> list) {
        this.list = list;
    }

    public String[] columnName;

    public String getColumnName(int index) {
        return columnName[index];
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return columnName.length;
    }

    @Override
    public String getValueAt(int rowIndex, int columnIndex) {
        Entity e = list.get(rowIndex);
        return switch(columnIndex) {
            case 0 -> e.getAttrib0();
            case 1 -> e.getAttrib1();
            case 2 -> e.getAttrib2();
            case 3 -> e.getAttrib3();
            case 4 -> e.getAttrib4();
            case 5 -> e.getAttrib5();
            default -> "";
        };
    }

    public Entity getSelectedEntity(int index) {
        return list.get(index);
    }
}
