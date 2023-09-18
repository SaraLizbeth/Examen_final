package sgBusinessLogic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sgDataAccess.sgSQLiteDataHelper;

public class sgCargarArchivoMostrarArchivo implements sgInterfaz {

    /**
     * Este metodo realiza la carga y procesamiento de un archivo de datos.
     */
    @Override
    public void sgGetAll() {
        String nombreArchivo = "sgArchivo/GuayasaminSara.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            int totalLineas = 0;

            System.out.println("Loading | Geoposicion | Lunes | Martes | Miercoles | Jueves | Viernes | Tipo Arsenal");

            while ((linea = br.readLine()) != null) {

                char[] signos = { '\\', '|', '/', '-', '|' };
                for (int carga = 0; carga <= 100; carga++) {
                    System.out.print("\r" + (totalLineas == 0 ? "Loading" : "        ") + "| "
                            + (carga == 0 ? " " : carga + "%") + " [");
                    System.out.print(signos[carga % signos.length] + "]");

                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                String[] partes = linea.split(" ; ");

                if (partes.length == 7) {
                    String coordenada = partes[0];
                    String lunes = partes[1];
                    String martes = partes[2];
                    String miercoles = partes[3];
                    String jueves = partes[4];
                    String viernes = partes[5];
                    String tipoArsenal = partes[6];

                    System.out.printf("%n          %s    ; %s ;  %s ;   %s ;    %s ; %s   ; %s%n",
                            coordenada, lunes, martes, miercoles, jueves, viernes, tipoArsenal);

                    totalLineas++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sgGetHorarioArsenal() {
        String nombreArchivo = "sgArchivo/GuayasaminSara.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
                Connection connection = sgSQLiteDataHelper.openConnection()) {

            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ; ");

                if (data.length >= 7) {
                    String lunes = data[1];
                    String martes = data[2];
                    String miercoles = data[3];
                    String jueves = data[4];
                    String viernes = data[5];
                    String tipoArsenal = data[6];

                    // Verificar si los valores cumplen con la condicin
                    if (cumpleCondicion(lunes) && cumpleCondicion(martes)
                            && cumpleCondicion(miercoles) && cumpleCondicion(jueves)
                            && cumpleCondicion(viernes)) {
                        // Asignar los valores a la tabla "Horarios"
                        asignarHorarios(connection, lunes, martes, miercoles, jueves, viernes, tipoArsenal);
                    }
                }
            }

            System.out.println("\n Datos asignados Correctamente !!!\n");


            sgImprimirHorario();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean cumpleCondicion(String valor) {
        // Verificar si el valor inicia con 0 y tiene una longitud mayor o igual a 5
        return valor.startsWith("0") && valor.length() >= 5;
    }

    public static void asignarHorarios(Connection connection, String lunes, String martes, String miercoles,
            String jueves, String viernes, String tipoArsenal) {
        try {
            // Insertar o actualizar los valores en la tabla "Horarios"
            String insertOrUpdateHorariosQuery = "INSERT INTO Horarios (Lunes, Martes, Miercoles, Jueves, Viernes) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatementHorarios = connection.prepareStatement(insertOrUpdateHorariosQuery);
            preparedStatementHorarios.setString(1, lunes);
            preparedStatementHorarios.setString(2, martes);
            preparedStatementHorarios.setString(3, miercoles);
            preparedStatementHorarios.setString(4, jueves);
            preparedStatementHorarios.setString(5, viernes);
            preparedStatementHorarios.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sgImprimirHorario() {

        String url = "jdbc:sqlite:database\\SG-SUPLETORIO-DB.db";

        try (Connection connection = DriverManager.getConnection(url)) {

            String sgConsulta = "SELECT * FROM SG_JOIN";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sgConsulta);

            // Recorre los resultados y los imprime
            System.out.println("HoraAtaque      |Lunes \t  |    Martes \t   |   Miercoles| \t Jueves | \tViernes");

            while (resultSet.next()) {
                String sgHoraAtaque = resultSet.getString("sgHoraAtaque");
                String sgLunes = resultSet.getString("sgLunes");
                String sgMartes = resultSet.getString("sgMartes");
                String sgMiercoles = resultSet.getString("sgMiercoles");
                String sgJueves = resultSet.getString("sgJueves");
                String sgViernes = resultSet.getString("sgViernes");
                System.out.println("  " + sgHoraAtaque +"\t\t| " +sgLunes +"\t  |\t" + sgMartes +"\t   |\t" + sgMiercoles +"\t|\t" + sgJueves +"\t|\t" +  sgViernes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
