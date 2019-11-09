package theaterdata;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import theater.Voorstelling;

/**
 * Klasse die met voorstellingen beheert. Op elke datum is er maar één
 * voorstelling.
 */
public class Voorstellingbeheer {

  /**
   * Maakt de verbinding met de database wanneer deze nog niet bestaat.
   */
  public static void init() throws TheaterException {
    // Controleer of er al een database open is, zo nee, open dan de database
    if (Connectiebeheer.getVerbinding() == null) {
      Connectiebeheer.openDB();
    }
  }

  public static void shutdown() throws TheaterException {
    if (Connectiebeheer.getVerbinding() == null) {
      Connectiebeheer.closeDB();
    }
  }
  /**
   * Levert alle data op waarop voorstellingen zijn (voor zover die data in de
   * toekomst liggen).
   * 
   * @return lijst met data van voorstellingen
   * @throws TheataerException 
   */
  public static ArrayList<GregorianCalendar> geefVoorstellingsData() throws TheaterException {
    return SqlStatments.getVoorstellingsData();
  }

  /**
   * Zoekt een voorstelling op de gegeven datum.
   * 
   * @param datum
   * @return een voorstelling op de gegeven datum of null wanneer die
   *         voorstelling er niet is.
   * @throws TheataerException 
   */
  public static Voorstelling geefVoorstelling(GregorianCalendar datum) throws TheaterException {
    Voorstelling voorstelling = null;
    // Haal de naam van de voorstelling op.
    String voorstellingnaam = SqlStatments.getVoorstellingsnaam(datum);
    if (voorstellingnaam != null) {
      // maakt een nieuw voorstellings object aan als de naam is gevonden.
      voorstelling = new Voorstelling(voorstellingnaam, datum);
    }
    if (voorstelling != null) {
      // voorstelling bestaat, stel de plaats gegevens in.
      SqlStatments.setVoorstellingPlaatsGegevens(voorstelling, datum);
      return voorstelling;
    }
    return null;
  }

  /**
   * Bewaart de bezettingsdata.
   * 
   * @param datum De datum van de voorstelling.
   * @param rijnummer Het rijnummer
   * @param stoelnummer Het stoelnummer.
   * @param klantnummer Het klantnummer.
   * @throws TheataerException
   */
  public static void setBezetting(GregorianCalendar datum, int rijnummer, int stoelnummer, int klantnummer) throws TheaterException {
    // Zet de datum om in een SqlDatum formaat.
    java.sql.Date sqlDatum = new java.sql.Date(datum.getTimeInMillis());
    // Sla de gegevens op in de database.
    SqlStatments.storeBezetting(sqlDatum.toString(), rijnummer, stoelnummer, klantnummer);
  }
}
