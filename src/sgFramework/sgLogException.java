package sgFramework;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * La clase sgLogException proporciona m�todos para registrar excepciones y mensajes de informaci�n en un archivo de registro.
 */
public class sgLogException {
    /**
     * Ruta del archivo de registro.
     */
    private static final String sgArchivoLog = "LogException.log";

    /**
     * Objeto Logger para registrar informaci�n en el archivo de registro.
     */
    private static final Logger logger = Logger.getLogger(sgLogException.class.getName());

    /**
     * Registra una excepci�n en el archivo de registro.
     *
     * @param ex La excepci�n que se registrar� en el archivo de registro.
     */
    public static void logException(Exception ex) {
        try (FileWriter fileWriter = new FileWriter(sgArchivoLog, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            ex.printStackTrace(printWriter);
        } catch (IOException e) {
            // Manejo de excepci�n en caso de que no se pueda escribir en el archivo de registro.
            e.printStackTrace();
        }
    }

    /**
     * Registra un mensaje de informaci�n en el archivo de registro.
     *
     * @param message El mensaje de informaci�n que se registrar� en el archivo de registro.
     */
    public static void logInfo(String message) {
        logger.info(message);
    }
}
