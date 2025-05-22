package Presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for generating a JTable based of a list of objects using reflection.
 * This is a generic solution to create tables for any model class (like Client, Product, etc.).
 * */

public class TableGenerator {

    /**
     * Generates a JTable from a list of objects of a given class type.
     * Uses reflection to access fields of the class and populate the table.
     *
     *  @param objects the list of objects to display in the table
     *  @param clazz   the class of the objects in the list
     *  @param <T>     the type of the objects
     *  @return JTable representing the data from the object list
     * */

    public static <T> JTable generateTable(List<T> objects, Class<T> clazz) {
        String[] columnNames = getFieldNames(clazz);
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (T obj : objects) {
            Object[] rowData = new Object[columnNames.length];
            for (int i = 0; i < columnNames.length; i++) {
                try {
                    Field field = clazz.getDeclaredField(columnNames[i]);
                    field.setAccessible(true);
                    rowData[i] = field.get(obj);
                } catch (Exception e) {
                    rowData[i] = "N/A";
                }
            }
            model.addRow(rowData);
        }

        return new JTable(model);
    }

    /**
     * Retrieves the names of all non-static fields of the class.
     * These names are used as column headers for the table.
     *
     * @param clazz the class whose fields are inspected
     * @param <T> the type of the class
     * @return an array of fields names (column headers)
     */

    private static <T> String[] getFieldNames(Class<T> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .map(Field::getName)
                .toArray(String[]::new);
    }
}
