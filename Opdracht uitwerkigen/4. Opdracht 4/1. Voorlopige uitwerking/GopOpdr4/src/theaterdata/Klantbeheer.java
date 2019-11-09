package theaterdata;

import theater.Klant;

/**
 * Deze klasse die klanten beheert.
 */
public class Klantbeheer {

  /**
   * Maakt de verbinding met de database wanneer deze nog niet bestaat.
   */
  public static void init() throws TheaterException {
    // Controleer of er al een database open is, zo nee, open dan de database
    if (Connectiebeheer.getVerbinding() == null) {
      Connectiebeheer.openDB();
    }
  }
  
  /**
   * Genereert het volgende beschikbare klantnummer.
   * 
   * @return nieuw klantnummer
   * @throws TheataerException 
   */
  public static int getVolgendKlantNummer() throws TheaterException {
    return SqlStatments.getHoogsteKlantNr() + 1;
  }
  
  /**
   * Geeft een klant met de gegeven naam en het gegeven telefoonnummer
   * Als de klant al in de lijst zat, wordt die teruggegeven; anders
   * wordt er een nieuwe klant gemaakt.
   * 
   * @param naam  naam van de klant
   * @param telefoon  telefoonnummer van de klant
   * @return  een klant met de ingevoerde naam en telefoon.
   * @throws TheataerException 
   */
  public static Klant geefKlant(String naam, String telefoon) throws TheaterException {
    Klant klant = zoekKlant(naam, telefoon);
    if (klant == null) {
      klant = nieuweKlant(naam, telefoon);
    }
    return klant;
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
    return SqlStatments.getKlant(naam, telefoon);
  }
  
  /**
   * Voegt een nieuwe klant toe aan theater.
   * 
   * @param naam  naam van de nieuwe klant
   * @param telefoon telefoonnummer van de nieuwe klant
   * @throws TheataerException 
   */
  private static Klant nieuweKlant(String naam, String telefoon) throws TheaterException {
    int knr = getVolgendKlantNummer();
    SqlStatments.storeKlant(knr, naam, telefoon);
    Klant k = new Klant(knr, naam, telefoon);
    return k;
  }
 
}
