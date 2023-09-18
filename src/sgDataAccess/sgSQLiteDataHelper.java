package sgDataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sgFramework.sgAppException;

public abstract class sgSQLiteDataHelper {

    private static final String DBPathConnection = "jdbc:sqlite:database\\SG-SUPLETORIO-DB.db";
    private static final String SELECT_QUERY_AUTENTICACION = "SELECT * FROM credenciales WHERE usuario = ? and contrasena = ?";
    private static Connection conn = null;

    public sgSQLiteDataHelper() {

    }

    /**
     * Abre una conexiÃƒÂ³n a la base de datos SQLite.
     *
     * @return La conexiÃƒÂ³n a la base de datos.
     * @throws sgAppException Si ocurre un error al abrir la conexiÃƒÂ³n.
     */
    public static synchronized Connection openConnection() throws sgAppException {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(DBPathConnection);
            }
        } catch (SQLException e) {
            throw new sgAppException(e, "SQLiteDataHelper", "Fallo la coneccion a la base de datos");
        }
        return conn;
    }

    /**
     * Cierra la conexiÃƒÂ³n a la base de datos.
     *
     * @throws sgAppException Si ocurre un error al cerrar la conexiÃƒÂ³n.
     */
    protected static void closeConnection() throws sgAppException {
        try {
            if (conn != null)
                conn.close();
        } catch (Exception e) {
            throw new sgAppException(e, "SQLiteDataHelper", "Fallo la conexion con la base de datos");
        }
    }

    /**
     * Cierra los recursos de la base de datos, como el ResultSet, PreparedStatement y Connection.
     *
     * @param resultSet  El ResultSet a cerrar.
     * @param statement  El PreparedStatement a cerrar.
     * @param connection La Connection a cerrar.
     * @throws sgAppException Si ocurre un error al cerrar los recursos.
     */
    protected static void closeResources(ResultSet resultSet, PreparedStatement statement, Connection connection)
            throws sgAppException {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new sgAppException(e, "SQLiteDataHelper", "Error al cerrar el ResultSet");
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new sgAppException(e, "SQLiteDataHelper", "Error al cerrar el PreparedStatement");
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new sgAppException(e, "SQLiteDataHelper", "Error al cerrar la Connection");
            }
        }

    }

    /**
     * Valida las credenciales de un usuario en la base de datos.
     *
     * @param nombreUsuario El nombre de usuario a validar.
     * @param contrasena    La contraseÃƒÂ±a a validar.
     * @return true si las credenciales son vÃƒÂ¡lidas, false en caso contrario.
     * @throws SQLException    Si ocurre un error al interactuar con la base de datos.
     * @throws sgAppException Si ocurre un error de la aplicaciÃƒÂ³n.
     */
    public static boolean validarCredenciales(String nombreUsuario, String contrasena)
            throws SQLException, sgAppException {
        try (Connection connection = sgSQLiteDataHelper.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY_AUTENTICACION)) {
            preparedStatement.setString(1, nombreUsuario);
            preparedStatement.setString(2, contrasena);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (((ResultSet) resultSet).next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

/**
     * Crea las tablas necesarias en la base de datos.
     *
     * @throws sgAppException Si ocurre un error al crear las tablas.
     */
    public static void sgCrearTablas() throws sgAppException {
        String[] sqlQueries = {
            "CREATE TABLE IF NOT EXISTS SG_USUARIOS (" +
                "IdUsuario INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "Usuario VARCHAR(30) UNIQUE NOT NULL," +
                "Contrasenia VARCHAR(10) NOT NULL);",
    
            "CREATE TABLE IF NOT EXISTS ArsenalTipo (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ArsenalTipo VARCHAR(20));",
    
            "CREATE TABLE IF NOT EXISTS Horarios (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Lunes VARCHAR(10)," +
                "Martes VARCHAR(10)," +
                "Miercoles VARCHAR(10)," +
                "Jueves VARCHAR(10)," +
                "Viernes VARCHAR(10));",
    
            "CREATE TABLE IF NOT EXISTS HoraAtaque (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "HoraAtaque VARCHAR(10));"
        };
    
    
    

        try (Connection conn = sgSQLiteDataHelper.openConnection()) {
            for (String sql : sqlQueries) {
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
  
}
