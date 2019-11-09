package theaterdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

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
   * 
   * @return Het Connection object.
   */
  static Connection getVerbinding() {
    return con;
  }

  /**
   * Maakt een connectie met de database
   * 
   * @throws TheaterException als het opzetten van de verbinding mislukt.
   */
  final static void openDB() throws TheaterException {
    // Laad de driver
    loadDriver();
    
    // open een verbinding naar de database
    try {
      DriverManager.setLoginTimeout(DBConst.LOGINTIMEOUT);
      con = DriverManager.getConnection(DBConst.URL, DBConst.GEBRUIKERSNAAM, DBConst.WACHTWOORD);
    }
    catch (SQLTimeoutException e) {
      throw new TheaterException("Het verbinden met de database server duurde langer dan " + DBConst.LOGINTIMEOUT + " seconden.");      
    }
    catch (SQLException e) {
      throw new TheaterException("Fout tijdens het maken van de verbinding met de database server (Server offline of fout in inlog gegevens).");
    }
  }

  /**
   * Laad de database driver.
   * 
   * @throws TheaterException als er tijdens het laden iets mis gaat.
   */
  private static void loadDriver() throws TheaterException {
    try {
      // The newInstance() call is a work around for some
      // broken Java implementations
      Class.forName(DBConst.DRIVERNAAM).newInstance();
    }
    catch (ClassNotFoundException e) {
      throw new TheaterException("Database driver bestaat niet.");
    }
    catch (InstantiationException e) {
      throw new TheaterException("Er is een probleem opgetreden tijdens het laden van de database driver.");
    }
    catch (IllegalAccessException e) {
      throw new TheaterException("Er is een toegangs probleem opgetreden tijdens het gebruik van de database driver.");
    }
  }

  /**
   */
  /**
   * Sluit de connectie met de database.
   * 
   * @return Het connectie object (zou null moeten zijn als alles goed verloopt).
   * @throws TheaterException als het sluiten van de verbinding en/of opruimen van de driver mislukt.
   */
  static Connection closeDB() throws TheaterException {
    if (con != null) {
      // sluiten database verbinding
      try {
        con.close();
      }
      catch (SQLException e) {
        throw new TheaterException("Fout tijdens het sluiten van de database verbinding.");
      }

      // opruimen driver
      try {
        if (con.isClosed()) {
          unloadDriver();
        }
      }
      catch (SQLException e) {
        throw new TheaterException("Database verbinding is niet (juist) afgesloten.");
      }

      // connectie object weer op null zetten. Hierdoor kan deze methode zonder problemen meerder keren worden aangeroepen.
      setConNull();
    }

    return con;
  }


  private static void setConNull() {
    con=null;
  }

  /**
   * Ruimt de database driver op.
   * 
   * @throws TheaterException als er tijdens het opruimen iets mis gaat.
   */
  private static void unloadDriver() throws TheaterException {
    try {
      DriverManager.deregisterDriver(DriverManager.getDriver(DBConst.URL));
    }
    catch (SQLException e) {
      throw new TheaterException("Fout tijdens het opruimen van de database driver.");
    }
  }

}
