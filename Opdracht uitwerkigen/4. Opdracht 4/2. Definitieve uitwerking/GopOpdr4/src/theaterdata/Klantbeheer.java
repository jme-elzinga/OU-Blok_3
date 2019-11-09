package theaterdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import theater.Klant;

/**
 * Deze klasse die klanten beheert.
 * 
 * @author Open Universiteit & Johan Elzinga
 */
public class Klantbeheer {

  private static PreparedStatement prepGetHoogsteKlantNr = null;
  private static PreparedStatement prepGetKlantNummer = null;
  private static PreparedStatement prepGetKlant = null;
  private static PreparedStatement prepSetKlant = null;
  private static PreparedStatement prepDelKlant = null;
  private static Connection con = null;

  /**
   * Maakt de verbinding met de database wanneer deze nog niet bestaat en roept de initialisatie methode van de PreparedStatements aan.
   * 
   * @throws TheaterException wanneer er iets fout gaat bij het maken van de verbinding of het initialiseren van de PreparedStatements.
   */
  public static void init() throws TheaterException {
    con = Connectiebeheer.getVerbinding();
    // Controleer of er al een verbinding is met de database, zo niet dan moet deze gemaakt worden en vervolgens opgehaald worden.
    if (con == null) {
      Connectiebeheer.openDB();
      con = Connectiebeheer.getVerbinding();
    }
    initialiseerPrepStatements();
  }

  /**
   * Probeert de verbinding met de database netjes af te sluiten.
   * 
   * @throws TheaterException Wanneer het sluiten mislukt.
   */
  public static void shutdown() throws TheaterException {
    // Sluit de database verbinding op een nette manier.
    con=Connectiebeheer.closeDB();
  }

  /**
   * Initialiseert twee PreparedStatements voor de
   * SQL-opdrachten om:
   * - het hoogst gebruikte klantnummer op te halen.
   * - Een klantnummer op te vragen.
   * - Een klant op te slaan.
   * - de naam & telefoon gegevens van een klant op te halen.
   * 
   * @throws TheaterException als de SQL-opdracht een fout bevat
   *         of niet gecompileerd kan worden.
   */
  private static void initialiseerPrepStatements() throws TheaterException {
    if (con != null) {
      try {
        prepGetHoogsteKlantNr = con.prepareStatement("SELECT MAX(klantnummer) AS hoogsteklantnr FROM klant");
        prepGetKlantNummer = con.prepareStatement("Select klantnummer from klant where naam = ? and telefoon = ?");
        prepSetKlant = con.prepareStatement("INSERT into klant (klantnummer,naam,telefoon) VALUES (?,?,?)");
        prepGetKlant = con.prepareStatement("Select naam, telefoon from klant where klantnummer = ?");
        prepDelKlant = con.prepareStatement("Delete from klant where klantnummer = ?");
      }
      catch (SQLException e) {
        //als er nu een fout optreedt, moet de verbinding eerst gesloten worden!
        shutdown();
        throw new TheaterException("Fout bij het formuleren van een sql-opdracht.");
      }
    } else {
      throw new TheaterException("Er is geen verbinding met de database, sql-opdrachten kunnen niet geformuleerd worden.");
    }
  }

  /**
   * Genereert het volgende beschikbare klantnummer.
   * 
   * @return nieuw klantnummer
   * @throws TheaterException Als de er tijdens het uitvoeren van het SQL commando iets fout gaat.
   */
  public static int getVolgendKlantNummer() throws TheaterException {
    int klantnummer=0;
    try {
      ResultSet res = prepGetHoogsteKlantNr.executeQuery();
      while (res.next()) {
        klantnummer = res.getInt("hoogsteklantnr");
      }
    }
    catch (SQLException e) {
      throw new TheaterException("Fout tijdens het ophalen van het hoogste klantnummer.");
    }
    return klantnummer + 1;
  }

  /**
   * Geeft een klant object met de gegeven naam en het gegeven telefoonnummer
   * Als de klant al in de database zit, wordt die teruggegeven; anders
   * wordt er een nieuwe klant in de database aangemaakt en terug gegeven.
   * 
   * @param naam  naam van de klant
   * @param telefoon  telefoonnummer van de klant
   * @return  een klant met de ingevoerde naam en telefoon.
   * @throws TheaterException Doorgekregen van de private methoden.
   */
  public static Klant geefKlant(String naam, String telefoon) throws TheaterException {
    Klant klant = zoekKlant(naam, telefoon);
    if (klant == null) {
      klant = nieuweKlant(naam, telefoon);
    }
    return klant;
  }
  
  /**
   * Geeft een klant object met het gegeven klantnummer.
   * 
   * @param klantnr Het klantnummer waarvan de gegevens opgehaald moeten worden.
   * @return Een gevuld klantobject of null wanneer de klant niet gevonden wordt.
   * 
   * @throws TheaterException Als de er tijdens het uitvoeren van het SQL commando iets fout gaat.
   */
  public static Klant geefKlant(int klantnr) throws TheaterException {
    try {
      prepGetKlant.setInt(1, klantnr);
      ResultSet res = prepGetKlant.executeQuery();
      while (res.next()) {
        String naam = res.getString("naam");
        String telefoon = res.getString("telefoon");
        return new Klant(klantnr, naam, telefoon);
      }
    }
    catch (SQLException e) {
      throw new TheaterException("Fout tijdens het ophalen van de klant gegevens voor bezettingsdata.");
    }
    return null;
  }

  /**
   * Zoekt klant met gegeven naam.
   * 
   * @param naam naam van te zoeken klant
   * @param telefoon telefoonnummer van te zoeken klant
   * @return de klant of null wanneer klant niet is gevonden
   * @throws TheataerException 
   */
  private static Klant zoekKlant(String naam, String telefoon) throws TheaterException {
    // Zoek de klant in de database
    try {
      prepGetKlantNummer.setString(1, naam);
      prepGetKlantNummer.setString(2, telefoon);
      ResultSet res = prepGetKlantNummer.executeQuery();
      while (res.next()) {
        int klantnr = res.getInt("klantnummer");
        return new Klant(klantnr, naam, telefoon);
      }
    }
    catch (SQLException e) {
      throw new TheaterException("Fout tijdens het ophalen van de klant gegevens voor het bezetten van de plaats(en).");
    }
    return null;
  }

  /**
   * Voegt een nieuwe klant toe aan theater.
   * 
   * @param naam  naam van de nieuwe klant
   * @param telefoon telefoonnummer van de nieuwe klant
   * @throws TheataerException Als de er tijdens het uitvoeren van het SQL commando iets fout gaat.
   */
  private static Klant nieuweKlant(String naam, String telefoon) throws TheaterException {
    int klantnr = getVolgendKlantNummer();
    try {
      prepSetKlant.setInt(1, klantnr);
      prepSetKlant.setString(2, naam);
      prepSetKlant.setString(3, telefoon);
      prepSetKlant.executeUpdate();
    }
    catch (SQLException e) {
      throw new TheaterException("Fout tijdens het opslaan van de nieuwe klant gegevens.");
    }
    Klant k = new Klant(klantnr, naam, telefoon);
    return k;
  }

  /**
   * Controleert of een klant al plaatsen in de databse heeft, zo nee dan wordt de klant verwijderd uit de database.
   * 
   * @param klant Het te controleren klantobject.
   * @throws TheaterException Doorgekregen van andere (private) methoden.
   */
  public static void verwijderKlant(Klant klant) throws TheaterException {
    int klantnummer = klant.getKlantnummer();
    if (!Voorstellingbeheer.checkKlantBezetting(klantnummer)) {
      //geen plaatsen in de database, verwijder klant
      delKlant(klantnummer);
    }
  }

  /**
   * Verwijdert een klant uit de klanten tabel.
   * LET OP! Alleen klanten zonder gegevens in de bezttingstabel kunnen en mogen worden verwijderd!
   * @param klantnummer Het klantnummer van de te verwijderen klant.
   * @throws TheaterException Fout als geprobeerd wordt een klant met bezettings gegevens te verwijderen.
   */
  private static void delKlant(int klantnummer) throws TheaterException {
    try {
      prepDelKlant.setInt(1, klantnummer);
      prepDelKlant.executeUpdate();
    }
    catch (SQLException e) {
      throw new TheaterException("Fout! Alleen klanten zonder bezettingsgegevens kunnen worden verwijderd!");
    }
  }

}
