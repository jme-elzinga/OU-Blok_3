package theater;

import java.util.*;
import theaterdata.*;

/**
 * Representeert (de kassa van) een theater.
 * @author Open Universiteit
 */
public class Theater {
  public static final int AANTALPERRIJ = 10;
  public static final int AANTALRIJEN = 15;
  private String naam = null;
  private Voorstelling huidigeVoorstelling = null;
  
 
  /**
   * Creeert een theater.
   * 
   * @param naam theaternaam
   * @throws TheaterException Fouten vanuit de datalaag.
   */
  public Theater(String naam) throws TheaterException {
    this.naam = naam;
    Klantbeheer.init();
    Voorstellingbeheer.init();
  }

  /**
   * Zorgt ervoor dat de database verbindingen netjes worden gesloten.
   * 
   * @throws TheaterException Fouten vanuit de datalaag.
   */
  public void applicationClose() throws TheaterException {
    Voorstellingbeheer.shutdown();
    Klantbeheer.shutdown();
  }
  /**
   * Geeft de naam van het theater.
   * 
   * @return naam van het theater
   */
  public String getNaam() {
    return naam;
  }
  
  /**
   * Geeft een lijst van data waarop voorstellingen zijn gepland.
   * 
   * @return lijst met data.
   * @throws TheaterException Fouten vanuit de datalaag.
   */
  public ArrayList<GregorianCalendar> geefVoorstellingsData() throws TheaterException {
    return Voorstellingbeheer.geefVoorstellingsData();
  }
  
  /**
   * Wisselt de huidige voorstelling naar voorstelling met gegeven datum.
   * 
   * @param datum datum van gevraagde voorstelling
   * @throws TheaterException Fouten vanuit de datalaag.
   */
  public void wisselVoorstelling(GregorianCalendar datum) throws TheaterException {
    huidigeVoorstelling = Voorstellingbeheer.geefVoorstelling(datum);
  }

  /**
   * Geeft de huidige voorstelling.
   * 
   * @return de huidige voorstelling
   */
  public Voorstelling getHuidigeVoorstelling() {
    return huidigeVoorstelling;
  }
  
  /**
   * Plaatst een klant op alle gereserveerde stoelen van de huidige uitvoering.
   * Wanneer een klant nog niet bestaat, dan wordt deze eerst gecreeerd.
   * 
   * @param naam naam van klant
   * @param telefoon telefoonnummer van klant
   * @throws TheaterException Fouten vanuit de datalaag en fouten wanneer er geen plaats is geselecteerd of wanneer den naam en/of telefoonnummer niet gevuld is.
   */
  public void plaatsKlant(String naam, String telefoon) throws TheaterException {
    boolean reserveringen = false;
    if (naam.trim().length()>0 && telefoon.trim().length()>0) {
      Klant klant = Klantbeheer.geefKlant(naam, telefoon);
      if (huidigeVoorstelling != null) {
        reserveringen=huidigeVoorstelling.plaatsKlant(klant);
      }
      if (!reserveringen) {
        // Geen reserveringen aangeklikt, verwijder een eventuele nieuwe klant uit de database.
        Klantbeheer.verwijderKlant(klant);
        throw new TheaterException("U heeft geen plaats(en) geselecteerd!");
      }
    } else {
      throw new TheaterException("U moet zowel een naam als een telefoonnummer opgeven.");
    }
  }

  /**
   * Verandert de reserveringsstatus (VRIJ<=>GERESERVEERD) van een plaats
   * in de huidige voorstelling.
   * 
   * @param rijnummer rijnummer van plaats
   * @param stoelnummer stoelnummer van plaats
   * @return true als verandering is gelukt, anders false
   */
  public boolean veranderReservering(int rijnummer, int stoelnummer) {
    return huidigeVoorstelling.veranderReservering(rijnummer, stoelnummer);
    
  }

  /**
   * Geeft informatie over een plaats in de huidige voorstelling.
   * 
   * @param rijnummer rijnummer van plaats
   * @param stoelnummer stoelnummer van plaats
   * @return informatie over plaats
   */
  public String geefPlaatsInfo(int rijnummer, int stoelnummer) {
    Plaats plaats = huidigeVoorstelling.getPlaats(rijnummer, stoelnummer);
    return plaats.toString();
  }
}
