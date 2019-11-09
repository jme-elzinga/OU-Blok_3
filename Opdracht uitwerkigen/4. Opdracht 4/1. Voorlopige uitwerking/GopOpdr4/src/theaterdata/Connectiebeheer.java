package theaterdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Beheert de connectie met de database.
 * Bevat methoden voor openen en sluiten van connectie met database,
 * en voor opvragen van de connectie.
 * 
 * @author Open Universiteit
 */
class Connectiebeheer {

  private static Connection con = null; // verbinding met gegevensbank 

  /**
   * Geeft het Connection object.
   * @return Het Connection object.
   */
  static Connection getVerbinding() {
    return con;
  }
  /**
   * Maakt een connectie met de database en initialiseert
   * Klantbeheer en VoostellingBeheer.
   * @throws TheaterException als de initialisatie mislukt.
   */
  final static void openDB() throws TheaterException {
    try {
      Class.forName(DBConst.DRIVERNAAM);
      con = DriverManager.getConnection(DBConst.URL, DBConst.GEBRUIKERSNAAM, DBConst.WACHTWOORD);
    }
    catch (ClassNotFoundException e) {
      throw new TheaterException("Driver niet geladen.");
    }
    catch (SQLException e) {
      throw new TheaterException("Verbinding maken is mislukt.");      
    }
  }

  /**
   * Sluit de connectie met de database
   */
  static void closeDB() throws TheaterException {
    if (con != null) {
      try {
        con.close();
      }
      catch (SQLException e) {      
        throw new TheaterException("Het sluiten van de verbinding is mislukt.");
      }
    }
  }
}
