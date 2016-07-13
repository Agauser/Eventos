package es.pue.eventos.utilitieslayer;


public abstract class AppUtils {

    public static enum PersistenceTechnologies{FLAT_FILE,REST,SQL_LITE}

    public final static String EVENTOS_DB="eventosDB";
    public final static int EVENTOS_DB_VERSION=1;
    public final static String TABLA_EVENTOS="eventos";
    public final static String TABLA_EVENTOS_ID="_id";
    public final static String TABLA_EVENTOS_EVENTO="evento";



}
