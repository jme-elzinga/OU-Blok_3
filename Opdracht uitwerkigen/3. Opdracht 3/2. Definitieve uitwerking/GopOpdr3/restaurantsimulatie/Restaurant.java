/**
 * 
 */
package restaurantsimulatie;

/**
 * Klasse die verantwoordelijk is voor het restaurant, het aanmaken van de Uitgiftebalie, het aanmaken van de koks, obers en het starten/stoppen van deze personen.
 * @author Johan Elzinga
 */
public class Restaurant {

  /**
   * Het aantal tafels in dit restaurant.
   */
  public static final int AANTALTAFELS = 10;
  private static final int SIMULATIETIJD = 120000;
  private Uitgiftebalie balie = null;

  /**
   * Constructor, maakt een nieuw restaurant met bijbehorende uitgiftebalie aan.
   */
  public Restaurant() {
    balie = new Uitgiftebalie();
  }

  /**
   * Main thread van het Restaurant. Hierin wordt het restaurant object aangemaakt en worden de objecten van de koks en obers aangemaakt en gestart/gestopt.
   * @param args Default parameterstring. Wordt niet gebruikt.
   */
  public static void main(String[] args) {
    Restaurant ilVulcano = new Restaurant();

    // Kok 1
    String naam = "Alessandro";
    Kok kok1 = new Kok(naam, ilVulcano.balie);
    Thread k1 = new Thread(kok1);
    k1.start();

    // Kok 2
    naam = "Giovanni";
    Kok kok2 = new Kok(naam, ilVulcano.balie);
    Thread k2 = new Thread(kok2);
    k2.start();

    // Kok 3
    naam = "Mario";
    Kok kok3 = new Kok(naam, ilVulcano.balie);
    Thread k3 = new Thread(kok3);
    k3.start();

    // Ober 1
    naam = "Salvatore";
    Ober ober1 = new Ober(naam, ilVulcano.balie);
    Thread o1 = new Thread(ober1);
    o1.start();

    // Ober 2
    naam = "Toni";
    Ober ober2 = new Ober(naam, ilVulcano.balie);
    Thread o2 = new Thread(ober2);
    o2.start();

    // Wacht SIMULATIETIJD voordat de threads van de diverse objecten worden gestopt.
    try {
      Thread.sleep(SIMULATIETIJD);
    }
    catch (InterruptedException e) {}

    // Stoppen van de Kok's en Obers. 
    k1.interrupt();
    k2.interrupt();
    k3.interrupt();
    o1.interrupt();
    o2.interrupt();

    // Wacht even en toon dan wat er nog op de uitgiftebalie staat.
    try {
      Thread.sleep(1000);
    }
    catch (InterruptedException e) {}
    System.out.println("\nDe volgende maaltijden zijn blijven staan op de uitgiftebalie:");
    Maaltijd maaltijd = ilVulcano.balie.pakMaaltijd();
    while (maaltijd != null) {
      System.out.println(maaltijd.toString());
      maaltijd = ilVulcano.balie.pakMaaltijd();
    }
  }
}
