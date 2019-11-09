package theaterdata;

/**
 * Deze klasse bevat de constanten die gebruikt worden om verbinding
 * te maken met de database.
 */
public class DBConst {
  // Oude driver constanten
//  protected static final String DRIVERNAAM = "com.mysql.jdbc.Driver";
//  protected static final String URL = "jdbc:mysql://localhost/theater";
  // Nieuwe driver constanten, extra URL toevoeging: ?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
  protected static final String DRIVERNAAM = "com.mysql.cj.jdbc.Driver";
  protected static final String URL = "jdbc:mysql://localhost/theater?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
  // Inlog gegevens
  protected static final String GEBRUIKERSNAAM = "cppjava";
  protected static final String WACHTWOORD = "theater"; 
}
