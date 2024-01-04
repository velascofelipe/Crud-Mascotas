/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package crudmascotas;

//PERMITE IMPORTAR TODA LA LIBERRIA DE SQL A JAVA
import java.sql.*;
import java.util.Scanner;

public class CRUDMascotas {

    public static void main(String[] args) {

        
       
        Connection con = null;
        Statement st = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mascotas", "root", "");
            st = con.createStatement();

            Scanner scanner = new Scanner(System.in);
            int choice;

            do {
                System.out.println("\u001B[36m\u001B[4m***** Menu de CRUD para Mascotas *****\u001B[0m");
                System.out.println("\u001B[36m1.\u001B[0m Agregar Mascota");
                System.out.println("\u001B[36m2.\u001B[0m Mostrar Mascotas");
                System.out.println("\u001B[36m3.\u001B[0m Actualizar Mascota");
                System.out.println("\u001B[36m4.\u001B[0m Eliminar Mascota por ID");
                System.out.println("\u001B[36m0.\u001B[0m Salir");
                System.out.println("\u001B[36mIngrese la opcion deseada:\u001B[0m");

                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        agregarMascota(con, st, scanner);
                        break;
                    case 2:
                        mostrarMascotas(con, st);
                        break;
                    case 3:
                        actualizarMascota(con, st, scanner);
                        break;
                    case 4:
                        eliminarMascotaPorId(con, st, scanner);
                        break;
                    case 0:
                        System.out.println("\u001B[36m\u001B[4mSaliendo del programa. Que le vaya super! XD\u001B[0m");
                        break;
                    default:
                        System.out.println("Opcion no válida. Intentelo de nuevo.");
                        break;
                }

            } while (choice != 0);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.toString());
        } finally {
            // Cierra los recursos al finalizar
            try {
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void agregarMascota(Connection con, Statement st, Scanner scanner) throws SQLException {
        System.out.println("Ingresa el nombre de la mascota ");
        String nombre = scanner.next();
        System.out.println("Ingresa la edad de la mascota");
        int edad = scanner.nextInt();

        int i = st.executeUpdate("INSERT INTO mascota(MASCOTA_NOMBRE, MASCOTA_EDAD) VALUES ('" + nombre + "','" + edad + "')");

        if (i > 0)
            System.out.println("Se ha agregado una mascota");
        else
            System.out.println("Algo ocurrio en el proceso..");
    }

    private static void mostrarMascotas(Connection con, Statement st) throws SQLException {
        ResultSet rs = st.executeQuery("SELECT * FROM mascota");

        System.out.println("\u001B[36mLista de Mascotas: \u001B");
        while (rs.next()) {
            int id = rs.getInt("MASCOTA_ID");
            String nombre = rs.getString("MASCOTA_NOMBRE");
            int edad = rs.getInt("MASCOTA_EDAD");
            System.out.println("ID: " + id + ", Nombre: " + nombre + ", Edad: " + edad);
        }
    }

    private static void actualizarMascota(Connection con, Statement st, Scanner scanner) throws SQLException {
        System.out.println("Ingresa el ID de la mascota a actualizar");
        int id = scanner.nextInt();

        // Verifica si la mascota existe antes de intentar actualizar
        ResultSet rs = st.executeQuery("SELECT * FROM mascota WHERE MASCOTA_ID = " + id);
        if (!rs.next()) {
            System.out.println("\u001B[31m No se encontro la mascota con ID " + id + "\u001B[0m");
            return;
        }

        System.out.println("\u001BIngresa el nuevo nombre de la mascota\u001B");
        String nuevoNombre = scanner.next();
        System.out.println("\u001BIngresa la nueva edad de la mascota\u001B");
        int nuevaEdad = scanner.nextInt();

        int i = st.executeUpdate("UPDATE mascota SET MASCOTA_NOMBRE = '" + nuevoNombre + "', MASCOTA_EDAD = " + nuevaEdad +
                " WHERE MASCOTA_ID = " + id);

        if (i > 0)
            System.out.println("Se ha actualizado la información de la mascota");
        else
            System.out.println("No se pudo actualizar la mascota");
    }

    private static void eliminarMascotaPorId(Connection con, Statement st, Scanner scanner) throws SQLException {
        System.out.println("\u001B Ingresa el ID de la mascota a eliminar \u001B");
        int id = scanner.nextInt();

        int i = st.executeUpdate("DELETE FROM mascota WHERE MASCOTA_ID = " + id);

        if (i > 0)
            System.out.println("Se ha eliminado la mascota");
        else
            System.out.println("\033[31mLa mascota no se encontro o no se pudo eliminar\033[31m");
    }
}
