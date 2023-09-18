package sgUtility;

/**
 * Clase de utilidades que proporciona m�todos comunes para la aplicaci�n.
 */

public class Utility {
    
    /**
     * Pausa la ejecuci�n del programa durante un tiempo determinado en milisegundos.
     *
     * @param milisegundos El tiempo en milisegundos.
     */
    public static void pausa(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * Borra el contenido de la consola.
     */
    public static void borrarConsola() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
