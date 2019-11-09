package theaterdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import theater.Klant;
import theater.Voorstelling;

/**
 * Klasse die met voorstellingen beheert. Op elke datum is er maar één
 * voorstelling.
 * 
 * @author Open Universiteit & Johan Elzinga
 */
public class Voorstellingbeheer {

  private static PreparedStatement prepGetVoorstellingsData = null;
  private static PreparedStatement prepGetVoorstellingsnaam = null;
  private static PreparedStatement prepGetVoorstellingPlaatsGegevens = null;
  private static PreparedStatement prepSetVoorstellingPlaatsGegevens = null;
  private static PreparedStatement prepGetVoorstellingPlaatsKlantGegevens = null;
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
   * - alle datums van de voorstellingen op te halen.
   * - de voorstellingsnaam op te halen bij een bepaalde datum.
   * - de plaats gegevens van een voorstelling op te halen.
   * - de plaats gegevens van een voorstelling op te slaan.
   * - de plaats gegevens van een klant op te halen.
   * 
   * @throws TheaterException als de SQL-opdracht een fout bevat
   *         of niet gecompileerd kan worden.
   */
  private static void initialiseerPrepStatements() throws TheaterException {
    if (con != null) {
      try {
        prepGetVoorstellingsData = con.prepareStatement("Select datum from voorstelling order by datum");
        prepGetVoorstellingsnaam = con.prepareStatement("Select naam from voorstelling where datum = ?");
        prepGetVoorstellingPlaatsGegevens = con.prepareStatement("Select rijnummer, stoelnummer, klant from bezetting where voorstelling = ? order by klant");
        prepSetVoorstellingPlaatsGegevens = con.prepareStatement("INSERT into bezetting (voorstelling,rijnummer,stoelnummer,klant) VALUES (?,?,?,?)");
        prepGetVoorstellingPlaatsKlantGegevens = con.prepareStatement("Select rijnummer, stoelnummer from bezetting where klant = ?");
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
   * Levert alle data op waarop voorstellingen zijn (voor zover die data in de
   * toekomst liggen).
   * 
   * @return lijst met data van voorstellingen
   * 
   * @throws TheaterException Als de er tijdens het uitvoeren van het SQL commando iets fout gaat.
   */
  public static ArrayList<GregorianCalendar> geefVoorstellingsData() throws TheaterException {
    GregorianCalendar nu = new GregorianCalendar();
    ArrayList<GregorianCalendar> data = new ArrayList<GregorianCalendar>();
    if (prepGetVoorstellingsData != null) {
      try {
        ResultSet res = prepGetVoorstellingsData.executeQuery();
        while (res.next()) {
          java.sql.Date sqlDatum = res.getDate("datum");
          GregorianCalendar datum = new GregorianCalendar();
          datum.setTimeInMillis(sqlDatum.getTime());
          if (datum.after(nu)) {
            data.add(datum);
          }
        }
      }
      catch (SQLException e) {
        throw new TheaterException("Er is iets fout gegaan tijdens het ophalen van datums van de voorstellingen.");
      }
    } else {
      throw new TheaterException("De SQL-opdracht is niet geformuleerd.");
    }
    return data;
  }

  /**
   * Zoekt een voorstelling op de gegeven datum.
   * 
   * @param datum De datum van de voorstelling.
   * @return een voorstelling object gevuld met data op de gegeven datum of null wanneer die
   *         voorstelling er niet is.
   *         
   * @throws TheaterException Als de er tijdens het uitvoeren van het SQL commandos iets fout is gegaan.
   */
  public static Voorstelling geefVoorstelling(GregorianCalendar datum) throws TheaterException {
    java.sql.Date sqlDatum = new java.sql.Date(datum.getTimeInMillis());

    Voorstelling voorstelling = null;

    // Haal de naam van de voorstelling op.
    String voorstellingnaam = getVoorstellingNaam(sqlDatum);

    if (voorstellingnaam != null) {
      // maakt een nieuw voorstellings object aan als de naam is gevonden.
      voorstelling = new Voorstelling(voorstellingnaam, datum);
      vulVoorstelling(voorstelling, sqlDatum);
    }

    return voorstelling;
  }

  /**
   * Vult het voorstellings object met plaats gegevens vanuit de database
   * 
   * @param voorstelling Het voorstellings object
   * @param sqlDatum De datum van de voorstelling in SQL formaat
   * @throws TheaterException  Als de er tijdens het uitvoeren van het SQL commando iets fout gaat.
   */
  private static void vulVoorstelling(Voorstelling voorstelling, java.sql.Date sqlDatum) throws TheaterException {
    if (voorstelling != null) {
      Klant klant = null;
      // voorstelling bestaat, stel de plaats gegevens in.
      try {
        prepGetVoorstellingPlaatsGegevens.setDate(1, sqlDatum);
        ResultSet res = prepGetVoorstellingPlaatsGegevens.executeQuery();
        int laatsteKlantNr = 0;
        while (res.next()) {
          int klantnummer = res.getInt("klant");
          int rijnummer = res.getInt("rijnummer");
          int stoelnummer = res.getInt("stoelnummer");
          if (laatsteKlantNr != klantnummer) {
            // Het klantnummer komt niet overeen met het laatst gebruikte klantnummer, maak nieuw klant object aan
            klant = Klantbeheer.geefKlant(klantnummer);
          }
          // reserveer de plaats
          voorstelling.veranderReservering(rijnummer, stoelnummer);
          // plaats klantobject op de gereserveerde plaats
          voorstelling.plaatsKlant(rijnummer, stoelnummer, klant);
        }
      }
      catch (SQLException e) {
        throw new TheaterException("Er is iets fout gegaan tijdens het ophalen van de bezettingsdata.");
      }
    }
  }

  /**
   * Haalt de naam van de voorstelling op.
   * 
   * @param sqlDatum De datum van de voorstelling (in SQL formaat).
   * @return de naam van de voorstelling bij de gegeven datum.
   * 
   * @throws TheaterException Als de er tijdens het uitvoeren van het SQL commando iets fout gaat.
   */
  private static String getVoorstellingNaam(java.sql.Date sqlDatum) throws TheaterException {
    try {
      prepGetVoorstellingsnaam.setDate(1, sqlDatum);
      ResultSet res = prepGetVoorstellingsnaam.executeQuery();
      while (res.next()) {
        return res.getString("naam");
      }
    }
    catch (SQLException e) {
      throw new TheaterException("Er is iets fout gegaan tijdens het ophalen van de voorstellingsnaam.");
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
   * 
   * @throws TheaterException Als de er tijdens het uitvoeren van het SQL commando iets fout gaat.
   */
  public static void setBezetting(GregorianCalendar datum, int rijnummer, int stoelnummer, int klantnummer) throws TheaterException {
    // Zet de datum om in een SqlDatum formaat.
    java.sql.Date sqlDatum = new java.sql.Date(datum.getTimeInMillis());
    // Sla de gegevens op in de database.
    try {
      prepSetVoorstellingPlaatsGegevens.setDate(1, sqlDatum);
      prepSetVoorstellingPlaatsGegevens.setInt(2, rijnummer);
      prepSetVoorstellingPlaatsGegevens.setInt(3, stoelnummer);
      prepSetVoorstellingPlaatsGegevens.setInt(4, klantnummer);
      prepSetVoorstellingPlaatsGegevens.executeUpdate();
    }
    catch (SQLException e) {
      throw new TheaterException("Fout tijdens het opslaan van de bezettingsdata.");
    }
  }

  /**
   * Controleert of een klant wel plaatsen heeft in de bezettings tabel.
   * @param klantnummer Het klant nummer waarop gecontroleerd moet worden
   * @return true wanneer de klant plaatsen heeft in de bezettingstabel, anders false.
   * @throws TheaterException Als de er tijdens het uitvoeren van het SQL commando iets fout gaat.
   */
  public static boolean checkKlantBezetting(int klantnummer) throws TheaterException {
    //
    boolean plaatsen = false;
    try {
      prepGetVoorstellingPlaatsKlantGegevens.setInt(1, klantnummer);
      ResultSet res = prepGetVoorstellingPlaatsKlantGegevens.executeQuery();
      while (res.next()) {
        plaatsen=true;
      }
    }
    catch (SQLException e) {
      throw new TheaterException("Er is iets fout gegaan tijdens het controleren van de klant bezetting.");
    }
    return plaatsen;
  }

}
