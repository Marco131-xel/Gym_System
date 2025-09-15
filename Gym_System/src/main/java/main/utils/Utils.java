package main.utils;

/**
 *
 * @author marco
 */
public class Utils {
    public static int obtenerIdRol(String rol) {
        return switch (rol) {
            case "Recepcionista" -> 1;
            case "Entrenador"    -> 2;
            case "Inventario"    -> 3;
            default              -> 0;
        };
    }

    public static int obtenerIdSucursal(String sucursal) {
        return switch (sucursal) {
            case "Central" -> 1;
            case "Norte"   -> 2;
            case "Sur"     -> 3;
            default        -> 0;
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
