package sgBusinessLogic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import sgUtility.Utility;

public class sgLogin {
    public static Scanner SG = new Scanner(System.in);
    public static final String SG_CEDULA  = "1727851709";
    public static final String SG_NOMBRE  = "Sara Lizbeth Guayasamin Nacimba";
    public static final String SG_CORREO  = "sara.guayasamin01@epn.edu.ec";
    public static final String SG_CEDULA1 = "1768503626";
    public static final String SG_NOMBRE1 = "Pepito Juanito Lopez Perez";
    public static final String SG_CORREO1 = "pepito.lopez@epn.edu.ec";
    public static String sgUsuarioLogeado;

    /**
     * Realiza un intento de inicio de sesión. Se le pide al usuario que ingrese su
     * nombre de usuario y contraseña hasta 3 intentos.
     *
     * @return true si el inicio de sesión es exitoso, false en caso contrario.
     */

    public static void sgPresentarCredenciales(){
        System.out.println(SG_CEDULA);
        System.out.println(SG_CORREO.toLowerCase());
        System.out.println(SG_NOMBRE.toUpperCase() + "\n");
        System.out.println(SG_CEDULA1);
        System.out.println(SG_CORREO1.toLowerCase());
        System.out.println(SG_NOMBRE1.toUpperCase());
        System.out.println();
    }
    public static boolean sgInicioSesion() {
        int sgIntentos = 1;
        while (sgIntentos <= 3) {
            Utility.borrarConsola();
            sgPresentarCredenciales();
            System.out.println("------------------------");
            System.out.print("+ Usuario: ");
            sgUsuarioLogeado = SG.nextLine();
            System.out.println();
            System.out.print("+ Clave: ");
            String sgClave = SG.nextLine();
            String sgClaveEncriptada = sgEncriptarMD5(sgClave); // Encripta la contraseña ingresada
            System.out.println();
            System.out.println("------------------------");
            System.out.print("* Numero de Intentos: " + sgIntentos);
            System.out.println();

            boolean sgCredencialesValidas = false;
            try {
                sgCredencialesValidas = sgValidarCredenciales(sgUsuarioLogeado, sgClaveEncriptada); // Compara con la
                                                                                              // contraseña encriptada
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (sgCredencialesValidas) {
                System.out.println("Bienvenido " + sgUsuarioLogeado.toUpperCase());
                System.out.println("\nPress Any Key to Continue...");
                SG.nextLine();
                return true;
            }

            sgIntentos++;
        }
        System.out.println("\nLo sentimos, tu usuario y clave son incorrectos..!");
        System.out.println("Gracias!");

        Utility.pausa(1000);
        System.exit(-1);
        return false;
    }

    /**
     * Encripta una cadena de texto utilizando el algoritmo MD5.
     *
     * @param sgTexto El texto a encriptar.
     * @return El hash MD5 en formato hexadecimal.
     */
    public static String sgEncriptarMD5(String sgTexto) {
        try {
            MessageDigest sgMd = MessageDigest.getInstance("MD5");
            sgMd.update(sgTexto.getBytes());
            byte[] sgDigest = sgMd.digest();
            StringBuilder sgSb = new StringBuilder();
            for (byte b : sgDigest) {
                sgSb.append(String.format("%02x", b & 0xff));
            }
            return sgSb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Manejo de la excepción NoSuchAlgorithmException
            throw new RuntimeException("Error al encriptar: Algoritmo MD5 no disponible", e);
        }
    }

    /**
     * Valida las credenciales de un usuario en la base de datos SQLite.
     *
     * @param sgNombreUsuario El nombre de usuario a validar.
     * @param sgContrasena    La contraseña a validar.
     * @return true si las credenciales son válidas, false en caso contrario.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    private static boolean sgValidarCredenciales(String sgNombreUsuario, String sgContrasena) throws SQLException {
        String sgDB_URL = "jdbc:sqlite:database\\SG-SUPLETORIO-DB.db"; 
        Connection sgConnection = null;
        PreparedStatement sgPreparedStatement = null;
        ResultSet sgResultSet = null;

        try {
            sgConnection = DriverManager.getConnection(sgDB_URL);

            String sgSELECT_QUERY_AUTENTICACION = "SELECT * FROM SG_USUARIOS WHERE Usuario = ? AND Contrasenia = ?";
            sgPreparedStatement = sgConnection.prepareStatement(sgSELECT_QUERY_AUTENTICACION);
            sgPreparedStatement.setString(1, sgNombreUsuario);
            sgPreparedStatement.setString(2, sgContrasena);

            sgResultSet = sgPreparedStatement.executeQuery();

            if (sgResultSet.next()) {
                return true;
            }
        } finally {
            // Cierra los recursos
            if (sgResultSet != null) {
                sgResultSet.close();
            }
            if (sgPreparedStatement != null) {
                sgPreparedStatement.close();
            }
            if (sgConnection != null) {
                sgConnection.close();
            }
        }

        return false;
    }

}
