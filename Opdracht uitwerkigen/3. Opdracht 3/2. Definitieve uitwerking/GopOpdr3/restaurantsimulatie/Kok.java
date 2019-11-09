/**
 * 
 */
package restaurantsimulatie;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Klasse voor het aanmaken en het beheer van de Kok.
 * @author Johan Elzinga
 */
public class Kok implements Runnable {

  private static final int BEREIDINGSTIJD = 4000;
  private String naam = "";
  private Uitgiftebalie balie = null;
  private List<String> maaltijdnamen = new ArrayList<String>();

  /**
   * Maakt een nieuw kok object aan met de gegeven naam en het gegeven balie object.
   * @param naam De naam van de kok.
   * @param balie Het balie object waar de kok zijn maaltijden op moet plaatsen.
   */
  public Kok(String naam, Uitgiftebalie balie) {
    this.naam = naam;
    this.balie = balie;
    initMaaltijdNamen();
  }

  /**
   * Methode voor het uitvoeren van de stappen in de thread van een instantie van deze klasse.
   */
  public void run() {
    System.out.println("---\t" + LocalTime.now() + " : Kok " + naam + " is gestart met werken.");
    // Blijf koken zolang de huidige kok thread geen interrupt heeft ontvangen.
    while (!Thread.currentThread().isInterrupted()) {
      kook();
    }
    System.out.println("sks\t" + LocalTime.now() + " : Kok " + naam + " stopt met werken. ");
  }


  /**
   * Zorgt voor het koken van de maaltijd (duurt BEREIDINGSTIJD miliseconden) en het plaatsen op de uitgiftebalie.
   */
  private void kook() {
    Maaltijd maaltijd = new Maaltijd(kiesMaaltijdNaam(), kiesTafel());
    System.out.println("kkk\t" + LocalTime.now() + " : Kok " + naam + " is begonnen met het bereiden van " + maaltijd.toString() + ".");
    try {
      Thread.sleep(BEREIDINGSTIJD);
      System.out.println("<=-\t" + LocalTime.now() + " : Kok " + naam + " heeft " + maaltijd.toString() + " op de uitgiftebalie geplaatst.");
      balie.plaatsMaaltijd(maaltijd);
    } catch (InterruptedException ex) {
      // De sleep(BEREIDINGSTIJD) heeft een Interrupt ontvangen.
      System.out.println("kkk\t" + LocalTime.now() + " : Kok " + naam + " heeft " + maaltijd.toString() + " laten verbranden.");
      // Omdat de Inturrupted flag nu gecleared is moet er weer een Interrupt signaal gegeven worden om de kok thread te stoppen (zie ook https://www.javaspecialists.eu/archive/Issue056.html voor uitleg hierover).
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Geeft een random tafelnummer terug.
   * @return Een integer die het tafelnummer voorstelt.
   */
  private int kiesTafel() {
    return ThreadLocalRandom.current().nextInt(1, Restaurant.AANTALTAFELS)+1;
  }

  /**
   * Kiest een random maaltijdnaam uit de lijst met maaltijdnamen.
   * @return een string met de naam van de maaltijd.
   */
  private String kiesMaaltijdNaam() {
    return maaltijdnamen.get(ThreadLocalRandom.current().nextInt(25));
  }

  /**
   * Vult de ArrayList maaltijdnamen met maaltijdnamen.
   */
  private void initMaaltijdNamen() {
    maaltijdnamen.add("Pizza Bambino");
    maaltijdnamen.add("Pizza Margheritta");
    maaltijdnamen.add("Pizza Funghi");
    maaltijdnamen.add("Pizza Salami");
    maaltijdnamen.add("Pizza Prosciutto");
    maaltijdnamen.add("Pizza Hawaï");
    maaltijdnamen.add("Pizza Vesuvio");
    maaltijdnamen.add("Pizza Vulcano");
    maaltijdnamen.add("Pizza Cipolla");
    maaltijdnamen.add("Pizza Napolitana");
    maaltijdnamen.add("Pizza Peperoni");
    maaltijdnamen.add("Pizza Vegetariana");
    maaltijdnamen.add("Pizza Vegetariana alla gorgonzola");
    maaltijdnamen.add("Pizza Quattro formaggi");
    maaltijdnamen.add("Pizza Montanara");
    maaltijdnamen.add("Pizza Campagnola");
    maaltijdnamen.add("Pizza Parma");
    maaltijdnamen.add("Pizza Dinamite");
    maaltijdnamen.add("Pizza Paesana");
    maaltijdnamen.add("Pizza Paesana alla gorgonzola");
    maaltijdnamen.add("Pizza Carbonara speciale");
    maaltijdnamen.add("Pizza Filetto vulcano");
    maaltijdnamen.add("Pizza Pomodoro Special");
    maaltijdnamen.add("Pizza Etna");
    maaltijdnamen.add("Pizza Frutti di mare");
  }
}
