import sgBusinessLogic.sgCargarArchivoMostrarArchivo;
import sgBusinessLogic.sgLogin;
import sgFramework.sgAppException;
import sgFramework.sgLogException;

public class App  {
    public static void main(String[] args) throws sgAppException {

        try {

            sgCargarArchivoMostrarArchivo sgArchivo = new sgCargarArchivoMostrarArchivo();
            sgDataAccess.sgSQLiteDataHelper.sgCrearTablas();
            sgLogin.sgInicioSesion();
            sgArchivo.sgGetAll();
            sgArchivo.sgGetHorarioArsenal();

        } catch (Exception e) {
            sgLogException.logException(e);
        }

    }
}
