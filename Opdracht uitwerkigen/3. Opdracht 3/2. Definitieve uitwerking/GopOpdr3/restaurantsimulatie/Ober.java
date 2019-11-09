/**
 * 
 */
package restaurantsimulatie;

import java.time.LocalTime;

/**
 * Klasse voor het aanmaken en het beheer van de Ober.
 * @author Johan Elzinga
 */
public class Ober  implements Runnable {

  private static final int LOOPTIJD = 500;
  private static final int WACHTTIJD = 1000;
  private String naam = null;
  private Uitgiftebalie balie = null;

  /**
   * Maakt een nieuw ober object aan met de gegeven naam en het gegeven balie object.
   * @param naam De naam van de ober.
   * @param balie Het balie object waar de ober zijn maaltijden op moet halen.
   */
  public Ober(String naam, Uitgiftebalie balie) {
    this.naam = naam;
    this.balie = balie;
  }

  /**
   * Methode voor het uitvoeren van de stappen in de thread van een instantie van deze klasse.
   */
  public void run() {
    System.out.println("---\t" + LocalTime.now() + " : Ober " + naam + " is gestart met werken.");
    // Blijf serveren zolang de huidige ober thread geen interrupt heeft ontvangen.
    while (!Thread.currentThread().isInterrupted()) {
      serveer(balie.pakMaaltijd());
    }
    System.out.println("sos\t" + LocalTime.now() + " : Ober " + naam + " stopt met werken.");
  }

  /**
   * Serveert de gegeven maaltijd of wacht WACHTTIJD
   * @param maaltijd Het maaltijd object wat naar een tafel gebracht moet worden (of null als er geen maaltijden op de uitgiftebalie staan).
   */
  private void serveer(Maaltijd maaltijd) {
    if (maaltijd != null) {
      int serveertijd = LOOPTIJD * maaltijd.getTafelnummer();
      System.out.println("-=>\t" + LocalTime.now() + " : Ober " + naam + " heeft " + maaltijd.toString() + " van de uitgiftebalie gepakt en loopt nu naar de tafel.");
      try {
        // Tijd voor het lopen naar de tafel.
        Thread.sleep(serveertijd);
        System.out.println("ooo\t" + LocalTime.now() + " : Ober " + naam + " heeft " + maaltijd.toString() + " geserveerd en loopt nu terug naar de uitgifte balie.");
        try {
          // Tijd voor het teruglopen naar de uitgiftebalie.
          Thread.sleep(serveertijd);
          System.out.println("uuu\t" + LocalTime.now() + " : Ober " + naam + " is terug bij de uitgifte balie.");
        } catch (InterruptedException ex) {
          // De sleep(serveertijd) heeft een Interrupt ontvangen.
          // Omdat de Inturrupted flag nu gecleared is moet er weer een Interrupt signaal gegeven worden om de ober thread te stoppen (zie ook https://www.javaspecialists.eu/archive/Issue056.html voor uitleg hierover).
          Thread.currentThread().interrupt();
        }
      } catch (InterruptedException ex) {
        // De sleep(serveertijd) heeft een Interrupt ontvangen.
        System.out.println("ooo\t" + LocalTime.now() + " : Ober " + naam + " heeft " + maaltijd.toString() + " laten vallen. Hij ruimt de rommel op.");
        // Omdat de Inturrupted flag nu gecleared is moet er weer een Interrupt signaal gegeven worden om de ober thread te stoppen (zie ook https://www.javaspecialists.eu/archive/Issue056.html voor uitleg hierover).
        Thread.currentThread().interrupt();
      }
    } else {
      try {
        System.out.println("zzz\t" + LocalTime.now() + " : Er staat niets op de uitgifte balie, ober " + naam + " wacht even.");
        Thread.sleep(WACHTTIJD);
      } catch (InterruptedException ex) {
        // De sleep(WACHTTIJD) heeft een Interrupt ontvangen.
        // Omdat de Inturrupted flag nu gecleared is moet er weer een Interrupt signaal gegeven worden om de ober thread te stoppen (zie ook https://www.javaspecialists.eu/archive/Issue056.html voor uitleg hierover).
        Thread.currentThread().interrupt();
      }
    }
  }
}
