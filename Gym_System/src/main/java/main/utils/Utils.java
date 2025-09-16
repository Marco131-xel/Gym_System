package main.utils;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author marco
 */
public class Utils {

    public static void cambiarPanel(JPanel actual, JPanel nuevoPanel) {
        nuevoPanel.setSize(1000, 600);
        nuevoPanel.setLocation(0, 0);

        java.awt.Window parentWindow = SwingUtilities.getWindowAncestor(actual);
        if (parentWindow instanceof JFrame) {
            JFrame frame = (JFrame) parentWindow;
            frame.getContentPane().removeAll();
            frame.getContentPane().add(nuevoPanel);
            frame.getContentPane().revalidate();
            frame.getContentPane().repaint();
        }
    }

    public static void mostrarPanel(JPanel puerta, JPanel p) {
        p.setSize(puerta.getSize());
        p.setSize(1000, 470);
        p.setLocation(0, 0);
        
        puerta.removeAll();
        puerta.add(p, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
        puerta.revalidate();
        puerta.repaint();
    }

    public static int obtenerIdRol(String rol) {
        return switch (rol) {
            case "Recepcionista" ->
                1;
            case "Entrenador" ->
                2;
            case "Inventario" ->
                3;
            default ->
                0;
        };
    }

    public static int obtenerIdSucursal(String sucursal) {
        return switch (sucursal) {
            case "Central" ->
                1;
            case "Norte" ->
                2;
            case "Sur" ->
                3;
            default ->
                0;
        };
    }

    public static String getSucursalNombre(int idSucursal) {
        return switch (idSucursal) {
            case 1 ->
                "Central";
            case 2 ->
                "Norte";
            case 3 ->
                "Sur";
            default ->
                "Desconocida";
        };
    }

    public static String getRolNombre(int idRol) {
        return switch (idRol) {
            case 1 ->
                "Recepcionista";
            case 2 ->
                "Entrenador";
            case 3 ->
                "Inventario";
            default ->
                "Desconocido";
        };
    }
}
