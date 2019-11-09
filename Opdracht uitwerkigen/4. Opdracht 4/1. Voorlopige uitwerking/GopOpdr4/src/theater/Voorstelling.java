package theater;

import java.util.GregorianCalendar;

import theater.Plaats.Status;
import theaterdata.TheaterException;
import theaterdata.Voorstellingbeheer;

/**
 * Representeert een voorstelling.
 * @author Open Universiteit
 */
public class Voorstelling {
  
  private String naam = "";
  private GregorianCalendar datum = null;
  private Plaats[][] plaatsen = null;
  
  /**
   * Creeert een voorstelling.
   * @param naam naam van de voorstelling
   * @param datum datum van de voorstelling
   */
  public Voorstelling(String naam, GregorianCalendar datum) {
    this.naam = naam;
    this.datum = datum;
    plaatsen = new Plaats[Theater.AANTALRIJEN + 1][Theater.AANTALPERRIJ + 1];
    for (int i = 1; i < plaatsen.length; i++) {
      for (int j = 1; j < plaatsen[i].length; j++) {
        plaatsen[i][j] = new Plaats(i,j);
      }
    }
  }
  
  /**
   * Geeft naam van de voorstelling.
   * @return naam van de voorstelling
   */
  public String getNaam() {
    return naam;
  }

  /**
   * Geeft datum van de voorstelling.
   * @return datum van de voorstelling
   */
  public GregorianCalendar getDatum() {
    return datum;
  }
  
  /**
   * Reserveert deze plaats.
   * Voorwaarde: de plaats is nog vrij
   * @param rijnummer rijnummer van de plaats
   * @param stoelnummer stoelnummer van de plaats
   * @return true als de reservering gelukt is, anders false
   */
  public boolean reserveer(int rijnummer, int stoelnummer) {
    if (inZaal(rijnummer, stoelnummer)) {
      return plaatsen[rijnummer][stoelnummer].reserveer();
    }
    else {
      return false;
    }
  }
  
  /**
   * Geef deze (gereserveerde) plaats vrij
   * @param rijnummer rijnummer van de plaats
   * @param stoelnummer stoelnummer van de plaats
   * @return  true als het vrijgeven gelukt is, anders false
   */
  public boolean geefVrij(int rijnummer, int stoelnummer) {
    if (inZaal(rijnummer, stoelnummer)) {
      return plaatsen[rijnummer][stoelnummer].geefVrij();
    }
    else {
      return false;
    }    
  }

  /**
   * Verander reserveringsstatus van stoel (VRIJ <=> GERESERVEERD).
   * Voorwaarde: rijnummer en stoelnummer vallen binnen de zaal
   * @param rijnummer rijnummer van de plaats
   * @param stoelnummer stoelnummer van de plaats
   * @return  true als er een statusverandering plaatsvindt, anders false
   */
  public boolean veranderReservering(int rijnummer, int stoelnummer) {
    if (!inZaal(rijnummer, stoelnummer)) {
      return false;
    }
    Plaats plaats = plaatsen[rijnummer][stoelnummer];
    Plaats.Status status = plaats.getStatus();
    if (status.equals(Plaats.Status.VRIJ)) {
      return plaats.reserveer();
    }
    else if (status.equals(Plaats.Status.GERESERVEERD)) {
      return plaats.geefVrij();
    }
    else {
      return false;
    }
  }
  
  /**
   * Plaatst de klant op de aangegeven plaats.
   * @param rijnummer rijnummer van de plaats
   * @param stoelnummer stoelnummer van de plaats
   * @param klant de klant
   * @return true als er een statusverandering plaatsvindt, anders false
   */
  public boolean plaatsKlant(int rijnummer, int stoelnummer, Klant klant) {
    if (inZaal(rijnummer, stoelnummer)) {
//      System.out.println("rijnummer: "+rijnummer+", stoelnummer: "+stoelnummer+", klantnummer: "+klant.getKlantnummer());
      return plaatsen[rijnummer][stoelnummer].plaatsKlant(klant);
    }
    else {
      return false;
    } 
  }  
    
  /**
   * Plaatst de klant op alle gereserveerde plaatsen.
   * @param klant de klant
   * @throws TheataerException 
   */
  public void plaatsKlant(Klant klant) throws TheaterException {
    for (int i = 1; i < plaatsen.length; i++) {
      for (int j = 1; j < plaatsen[i].length; j++) {
        if (plaatsen[i][j].getStatus().equals(Status.GERESERVEERD)) {
          // wanneer een stoel gereserveerd is wordt eerst geprobeerd om de gegevens in de database op te slaan,
          // daarna pas de stoel bezetten (voorkomt een bezette stoel wanneer het opslaan in de database mislukt).
          Voorstellingbeheer.setBezetting(getDatum(), i, j, klant.getKlantnummer());
          plaatsen[i][j].plaatsKlant(klant);
        }
      }
    }
  }
   
  /**
   * Geeft een plaats in de zaal
   * @param rijnummer  rijnummer van de plaats
   * @param stoelnummer stoelnummer van de plaats
   * @return
   */
  public Plaats getPlaats(int rijnummer, int stoelnummer) {
    if (inZaal(rijnummer, stoelnummer)) {
      return plaatsen[rijnummer][stoelnummer];
    }
    else {
      return null;
    }
  }
  
  // private methode
  
  /**
   * Controleert of er een plaats is met dit rij- en stoelnummer
   * @param rijnummer  het rijnummer van de plaats
   * @param stoelnummer  het stoelnummer van de plaats
   * @return  true als de zaal een plaats heeft met dit rij-
   *          en stoelnummer; anders false
   */
  private boolean inZaal(int rijnummer, int stoelnummer) {
    return rijnummer >= 1 && rijnummer <= Theater.AANTALRIJEN
         && stoelnummer >= 1 && stoelnummer <= Theater.AANTALPERRIJ;
  }

}
