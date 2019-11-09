/**
 * 
 */
package restaurantsimulatie;

/**
 * Klasse voor het aanmaken en het beheer van de Ober.
 * @author Johan Elzinga
 */
public class Ober  implements Runnable {

  private static final int LOOPTIJD = 500;
  private static final int WACHTTIJD = 1000;
  private String naam = null;
  private Uitgiftebalie balie = null;
  private volatile boolean stoppen = false;
  private Thread thread = null;

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
   * Methode voor het uitvoeren van de stappen in de thread van dit object.
   */
  public void run() {
    while (!stoppen) {
      serveer(balie.pakMaaltijd());
    }
  }

  /**
   * Methode voor het aanmaken en het starten van de thread van dit object
   */
  public void start() {
    thread = new Thread(this);
    thread.start();
    System.out.println("---\t Ober " + naam + " is gestart.");
  }

  /**
   * Methode voor het stoppen van de thread van dit object.
   */
  public void stop() {
    thread.interrupt();
  }

  /**
   * Serveert de gegeven maaltijd of wacht WACHTTIJD
   * @param maaltijd Het maaltijd object wat geserveert moet worden (of null als er geen maaltijden op de uitgiftebalie staan).
   */
  private synchronized void serveer(Maaltijd maaltijd) {
    if (maaltijd != null) {
      int serveertijd = LOOPTIJD * maaltijd.getTafelnummer();
      System.out.println("-=>\t Ober " + naam + " heeft " + maaltijd.toString() + " van de uitgiftebalie gepakt en loopt nu naar de tafel.");
      try {
        // Tijd voor het lopen naar de tafel.
        Thread.sleep(serveertijd);
        System.out.println("ooo\t Ober " + naam + " heeft " + maaltijd.toString() + " geserveerd en loopt nu terug naar de uitgifte balie.");
        try {
          // Tijd voor het teruglopen naar de uitgiftebalie.
          Thread.sleep(serveertijd);
          System.out.println("uuu\t Ober " + naam + " is terug bij de uitgifte balie.");
        } catch (InterruptedException ex) {
          stoppen = true;
          System.out.println("sos\t Ober " + naam + " stop met werken.");
          Thread.currentThread().interrupt();
        }
      } catch (InterruptedException ex) {
        stoppen = true;
        System.out.println("sos\t Ober " + naam + " heeft " + maaltijd.toString() + " laten vallen. Hij ruimt de rommel op en stop met werken.");
        Thread.currentThread().interrupt();
      }
    } else {
      try {
        System.out.println("zzz\t Er staat niets op de uitgifte balie, ober " + naam + " wacht even.");
        Thread.sleep(WACHTTIJD);
      } catch (InterruptedException ex) {
        Thread.currentThread().interrupt();
      }
    }
  }
}
