/**
 * 
 */
package theaterdata;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import theater.Klant;
import theater.Voorstelling;

/**
 * Klasse met alle SQL commando's die op de database uitgevoerd worden.
 * 
 * @author Johan Elzinga
 *
 */
class SqlStatments {

  /**
   * Haalt de datums van de voorstellingen op uit de database.
   * @return een ArrayList met datums.
   * @throws TheataerException
   */
  static ArrayList<GregorianCalendar> getVoorstellingsData() throws TheaterException {
    GregorianCalendar nu = new GregorianCalendar();
    ArrayList<GregorianCalendar> data = new ArrayList<GregorianCalendar>();
    String sql = "Select datum from voorstelling order by datum";
    PreparedStatement prepStmt;
    try {
      Connection con = Connectiebeheer.getVerbinding();
      prepStmt = con.prepareStatement(sql);
      ResultSet res = prepStmt.executeQuery();
      while (res.next()) {
        Date sqlDatum = Date.valueOf(res.getString("datum"));
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
    return data;
  }

  /**
   * Haalt de voorstellingsnaam op behorende bij de gegeven datum.
   * @param datum De datum waarvan de naam moet worden opgehaald.
   * @return Een string met de naam van de voorstelling.
   * @throws TheataerException
   */
  static String getVoorstellingsnaam(GregorianCalendar datum) throws TheaterException {
    String voorstellingnaam = null;
    String sql = "Select naam from voorstelling where datum = ? ";
    PreparedStatement prepStmt;
    try {
      Connection con = Connectiebeheer.getVerbinding();
      prepStmt = con.prepareStatement(sql);
      Date sqlDatum = new Date(datum.getTimeInMillis());
      prepStmt.setString(1, sqlDatum.toString());
      ResultSet res = prepStmt.executeQuery();
      while (res.next()) {
        voorstellingnaam = res.getString("naam");
      }
    }
    catch (SQLException e) {
      throw new TheaterException("Er is iets fout gegaan tijdens het ophalen van de voorstellingsnaam.");
    }
    return voorstellingnaam;
  }

  /**
   * Haalt de bezetting op behorende bij de gegeven voorstellingsdatum en vult de gegeven voorstelling met de juiste plaatsingsdata.
   * @param voorstelling Het voorstellingsobject welke gevuld moet worden met de plaats data
   * @param datum De datum van de voorstelling welke opgehaald moet worden.
   * @throws TheataerException
   */
  static void setVoorstellingPlaatsGegevens(Voorstelling voorstelling, GregorianCalendar datum) throws TheaterException {
    Klant klant = null;
    String sql = "Select rijnummer, stoelnummer, klant from bezetting where voorstelling = ? order by klant";
    PreparedStatement prepStmt;
    try {
      Connection con = Connectiebeheer.getVerbinding();
      prepStmt = con.prepareStatement(sql);
      Date sqlDatum = new Date(datum.getTimeInMillis());
      prepStmt.setString(1, sqlDatum.toString());
      ResultSet res = prepStmt.executeQuery();
      int laatsteKlantNr = 0;
      while (res.next()) {
        int klantnummer = res.getInt("klant");
        int rijnummer = res.getInt("rijnummer");
        int stoelnummer = res.getInt("stoelnummer");
        if (laatsteKlantNr != klantnummer) {
          // maak nieuw klant object aan
          klant = getKlant(klantnummer);
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

  /**
   * Haalt de klant gegevens op bij een gegeven klantnummer.
   * @param klantnr Het klantnummer waarvan de gegevens opgehaald moeten worden.
   * @return Een gevuld klantobject of null wanneer de klant niet gevonden wordt.
   * @throws TheataerException
   */
  private static Klant getKlant(int klantnr) throws TheaterException {
    String sql = "Select naam, telefoon from klant where klantnummer = ?";
    PreparedStatement prepStmt;
    try {
      Connection con = Connectiebeheer.getVerbinding();
      prepStmt = con.prepareStatement(sql);
      prepStmt.setInt(1, klantnr);
      ResultSet res = prepStmt.executeQuery();
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
   * Haalt het klantnummer op behorend bij een gegeven naam en telefoonnummer
   * @param naam De naam waar op gezocht moet worden.
   * @param telefoon Het telefoonnummer waarop gezocht moet worden.
   * @return een klant object met gegevens of null wanneer de klant niet gevonden wordt.
   * @throws TheataerException
   */
  static Klant getKlant(String naam, String telefoon) throws TheaterException {
    String sql = "Select klantnummer from klant where naam = ? and telefoon = ?";
    PreparedStatement prepStmt;
    try {
      Connection con = Connectiebeheer.getVerbinding();
      prepStmt = con.prepareStatement(sql);
      prepStmt.setString(1, naam);
      prepStmt.setString(2, telefoon);
      ResultSet res = prepStmt.executeQuery();
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
   * Haalt het hoogste klantnummer op wat is gebruikt in de database. 
   * @return Het hoogste in gebruik zijnde klantnummer (of 0 wanneer de klant tabel nog leeg is).
   * @throws TheataerException
   */
  static int getHoogsteKlantNr() throws TheaterException {
    int klantnummer=0;
    String sql = "SELECT MAX(klantnummer) FROM klant";
    PreparedStatement prepStmt;
    try {
      Connection con = Connectiebeheer.getVerbinding();
      prepStmt = con.prepareStatement(sql);
      ResultSet res = prepStmt.executeQuery();
      while (res.next()) {
        klantnummer = res.getInt(1);
      }
    }
    catch (SQLException e) {
      throw new TheaterException("Fout tijdens het ophalen van het hoogste klantnummer.");
    }
    return klantnummer;
  }

  /**
   * Slaat de gegegeven klant op in de klant tabel
   * @param klantnr Het klantnummer van de op te slaan klant.
   * @param naam De naam van de op te slaan klant.
   * @param telefoon Het telefoonnummer van de op te slaan klant.
   * @throws TheataerException
   */
  static void storeKlant(int klantnr, String naam, String telefoon) throws TheaterException {
    String sql = "INSERT into klant set klantnummer = ?, naam = ?, telefoon = ?";
    PreparedStatement prepStmt;
    try {
      Connection con = Connectiebeheer.getVerbinding();
      prepStmt = con.prepareStatement(sql);
      prepStmt.setInt(1, klantnr);
      prepStmt.setString(2, naam);
      prepStmt.setString(3, telefoon);
      prepStmt.executeUpdate();
    }
    catch (SQLException e) {
      throw new TheaterException("Fout tijdens het opslaan van de nieuwe klant gegevens.");
    }
  }

  /**
   * Slaat de bezettingsdata op in de database
   * @param datum De datum van de voorstelling.
   * @param rijnummer Het rijnummer
   * @param stoelnummer Het stoelnummer.
   * @param klantnummer Het klantnummer.
   * @throws TheataerException
   */
  static void storeBezetting(String datum, int rijnummer, int stoelnummer, int klantnummer) throws TheaterException {
    String sql = "INSERT into bezetting set voorstelling = ?, rijnummer = ?, stoelnummer = ?, klant = ?";
    PreparedStatement prepStmt;
    try {
      Connection con = Connectiebeheer.getVerbinding();
      prepStmt = con.prepareStatement(sql);
      prepStmt.setString(1, datum);
      prepStmt.setInt(2, rijnummer);
      prepStmt.setInt(3, stoelnummer);
      prepStmt.setInt(4, klantnummer);
      prepStmt.executeUpdate();
    }
    catch (SQLException e) {
      throw new TheaterException("Fout tijdens het opslaan van de bezettingsdata.");
    }
  }
}
