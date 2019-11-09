package theater;

import java.util.Observable;

/**
 * Representeert een plaats voor een bepaalde voorstelling. De klasse is
 * Observable opdat bij het veranderen van de status, die verandering in de gui
 * getoond kan worden.
 * 
 * @author Open Universiteit
 */
public class Plaats extends Observable {

  public enum Status {
    VRIJ, GERESERVEERD, BEZET
  };

  private int    rijnummer;
  private int    stoelnummer;
  private Klant  klant  = null;
  private Status status = Status.VRIJ;

  /**
   * Creeert een stoel.
   * 
   * @param rijnummer
   *          nummer van rij
   * @param stoelnummer
   *          nummer van stoel
   */
  public Plaats(int rijnummer, int stoelnummer) {
    super();
    this.rijnummer = rijnummer;
    this.stoelnummer = stoelnummer;
    this.status = Status.VRIJ;
  }

  public Status getStatus() {
    return status;
  }

  public int getRijnummer() {
    return rijnummer;
  }

  public int getStoelnummer() {
    return stoelnummer;
  }

  /**
   * Reserveer deze plaats als die nog vrij is.
   * 
   * @return true als de status gewijzigd is; anders false
   */
  public boolean reserveer() {
    if (status.equals(Status.VRIJ)) {
      status = Status.GERESERVEERD;
      setChanged();
      notifyObservers();
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * Geef deze plaats vrij als die gereserveerd was.
   * 
   * @return true als de status gewijzigd is; anders false
   */
  public boolean geefVrij() {
    if (status.equals(Status.GERESERVEERD)) {
      status = Status.VRIJ;
      setChanged();
      notifyObservers();
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * Wijst de plaats toe aan de gegeven klant als de plaats status gereserveerd
   * had.
   * 
   * @param klant
   *          de klant voor wie de plaats is
   * @return true als de status gewijzigd is; anders false
   */
  public boolean plaatsKlant(Klant klant) {
    if (status.equals(Status.GERESERVEERD)) {
      status = Status.BEZET;
      this.klant = klant;
      setChanged();
      notifyObservers();
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * Maakt stringrepresentatie van plaats.
   * 
   * @return stringrepresentatie
   */
  public String toString() {
    String resultaat = "rij " + rijnummer + " " + "stoel " + stoelnummer
        + " is ";
    switch (status) {
      case VRIJ:
        resultaat = resultaat + "vrij";
        break;
      case GERESERVEERD:
        resultaat = resultaat + "gereserveerd";
        break;
      case BEZET:
        resultaat = resultaat + "bezet door " + klant;
      default:
        break;
    }
    return resultaat;
  }

}
