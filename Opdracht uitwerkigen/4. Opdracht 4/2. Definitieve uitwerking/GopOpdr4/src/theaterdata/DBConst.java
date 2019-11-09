package theaterdata;

/**
 * Deze klasse bevat de constanten die gebruikt worden om verbinding
 * te maken met de database.
 */
public class DBConst {
  protected static final String DRIVERNAAM = "com.mysql.cj.jdbc.Driver";
  protected static final String URL = "jdbc:mysql://localhost/theater?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin";
  // Inlog gegevens
  protected static final String GEBRUIKERSNAAM = "cppjava";
  protected static final String WACHTWOORD = "theater";
  // Maximale tijd (in seconden) voordat het inloggen wordt afgebroken
  protected static final int LOGINTIMEOUT = 2;
}
