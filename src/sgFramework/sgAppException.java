package sgFramework;

/**
 * La clase sgAppException es una excepcion personalizada que se utiliza para manejar errores en la aplicacion.
 * Extiende de la clase Exception de Java y proporciona funcionalidad adicional para registrar informacion de depuracion.
 */
public class sgAppException extends Exception {

    /**
     * Constructor que acepta una causa, una clase y un mensaje de error.
     *
     * @param cause     La causa original del error.
     * @param className El nombre de la clase en la que se produjo el error.
     * @param message   El mensaje de error detallado.
     */
    public sgAppException(Throwable cause, Object className, String message) {
        super(className + ":" + message, cause);
        setDebuggingLog();
    }

    /**
     * Constructor que acepta una causa y una clase, con un mensaje de error por defecto.
     *
     * @param cause     La causa original del error.
     * @param className El nombre de la clase en la que se produjo el error.
     */
    public sgAppException(Throwable cause, Object className) {
        super(className + ":" + "Error no especificado", cause);
        setDebuggingLog();
    }

    /**
     * Constructor que acepta un mensaje de error.
     *
     * @param message El mensaje de error detallado.
     */
    public sgAppException(String message) {
        super("Error en clase no especificada " + ":" + message);
        setDebuggingLog();
    }

    /**
     * Este metodo se llama internamente para imprimir un registro de depuracion cuando se lanza la excepcion.
     * Muestra informacion sobre la excepcion, incluido el mensaje y la causa.
     */
    void setDebuggingLog() {
        System.out.println("-------------------------");
        System.out.println("{AppException}");
        System.out.println(getMessage());
        System.out.println(getCause());
        System.out.println("-------------------------");
    }
}