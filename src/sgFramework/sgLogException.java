package sgFramework;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * La clase sgLogException proporciona métodos para registrar excepciones y mensajes de información en un archivo de registro.
 */
public class sgLogException {
    /**
     * Ruta del archivo de registro.
     */
    private static final String sgArchivoLog = "LogException.log";

    /**
     * Objeto Logger para registrar información en el archivo de registro.
     */
    private static final Logger logger = Logger.getLogger(sgLogException.class.getName());

    /**
     * Registra una excepción en el archivo de registro.
     *
     * @param ex La excepción que se registrará en el archivo de registro.
     */
    public static void logException(Exception ex) {
        try (FileWriter fileWriter = new FileWriter(sgArchivoLog, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            ex.printStackTrace(printWriter);
        } catch (IOException e) {
            // Manejo de excepción en caso de que no se pueda escribir en el archivo de registro.
            e.printStackTrace();
        }
    }

    /**
     * Registra un mensaje de información en el archivo de registro.
     *
     * @param message El mensaje de información que se registrará en el archivo de registro.
     */
    public static void logInfo(String message) {
        logger.info(message);
    }
}
