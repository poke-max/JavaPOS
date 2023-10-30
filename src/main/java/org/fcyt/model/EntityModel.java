package org.fcyt.model;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class EntityModel
{
    Connection connection;
    PreparedStatement st;
    ResultSet rs;
    public SQLStrings strings;
    public String[] columnName;

    public String table;
    ResultSetMetaData rsmd;
    public Field[] field;
    String[] columnClassName;
    public EntityModel(String table) {
        this.table = table;
        connection = new ConnectDB().connection;
    }
    public void insert(Entity e) {
        try {
            st = connection.prepareStatement(strings.insertSQL);
            //clases de  las columnas de la base de datos
            for(int i=1; i<rsmd.getColumnCount(); i++) {
                switch (columnClassName[i]) {
                    case "java.lang.Integer" :
                        st.setInt(i, Integer.parseInt((String) field[i].get(e)));
                        break;
                    case "java.lang.String" :
                        st.setString(i, (String) field[i].get(e));
                        break;
                }
            }
            st.executeUpdate();
        } catch(SQLException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void update(Entity e) {
        try {
            st = connection.prepareStatement(strings.updateSQL);
            int i =1;
            for(i=1; i<rsmd.getColumnCount(); i++) {
                switch (columnClassName[i]) {
                    case "java.lang.Integer" :
                        st.setInt(i, Integer.parseInt((String) field[i].get(e)));
                        break;
                    case "java.lang.String" :
                        st.setString(i, (String) field[i].get(e));
                        break;
                }
            }
            st.setInt(i, Integer.parseInt((String) field[0].get(e)));
            st.executeUpdate();
        } catch(SQLException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }
    public void delete(Entity e) {
        try {
            st = connection.prepareStatement(strings.deleteSQL);
            st.setInt(1, Integer.parseInt((String) field[0].get(e)));
            st.executeUpdate();
        } catch(SQLException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
    public String[][] selectCombo(String t, String c) {
            String sql = "select "+c+ " from "+t;
            List<Entity> list = new ArrayList<>();
            field = Entity.class.getDeclaredFields();

        try {
            st = connection.prepareStatement(sql);
            rs = st.executeQuery();
            rsmd = rs.getMetaData();
            String[] columnName =new String[rsmd.getColumnCount()];
            for(int column=0; column<rsmd.getColumnCount(); column++){
                columnName[column] = rsmd.getColumnName(column+1);
            }


            while (rs.next()) {
                Entity e = new Entity();
                for (int i=0; i< rsmd.getColumnCount(); i++){
                    field[i].set(e, rs.getString(columnName[i]));
                }
                list.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        String[][] opciones = new String[list.size()][2];

        for (int row=0; row<list.size(); row++) {
                opciones[row][0] = list.get(row).getAttrib0().toString();
                opciones[row][1] = list.get(row).getAttrib1().toString();
        }
        return opciones;
    }
    public List<Entity> select(String v) {
        strings = new SQLStrings(table, v);
        List<Entity> list = new ArrayList<>();
        field = Entity.class.getDeclaredFields();
        try {
            st = connection.prepareStatement(strings.selectSQL);
            rs = st.executeQuery();

            // metadatos del resultset
            rsmd = rs.getMetaData();
            columnName = new String[rsmd.getColumnCount()];
            for(int i=0; i<rsmd.getColumnCount(); i++) {
                columnName[i] = rsmd.getColumnName(i+1);
            }
            while(rs.next()) {
                Entity e = new Entity();
                for(int i=0; i<rsmd.getColumnCount(); i++) {
                    field[i].set(e, rs.getString(columnName[i]));
                }
                list.add(e);
            }

            // metadatos de la tabla sin referencias
            columnClassName = new String[rsmd.getColumnCount()];
            String selectSQL2 = "select * from "+ table + " limit 1";
            PreparedStatement st2 = connection.prepareStatement(selectSQL2);
            ResultSet rs = st2.executeQuery();
            ResultSetMetaData rsmd2 = rs.getMetaData();
            for(int i=0; i<rsmd2.getColumnCount(); i++) {
                columnClassName[i] = rsmd2.getColumnClassName(i+1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static void main( String[] args ) {

    }
}
