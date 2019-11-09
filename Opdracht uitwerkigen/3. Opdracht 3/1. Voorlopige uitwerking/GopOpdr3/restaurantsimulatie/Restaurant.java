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

    Kok kok1 = new Kok("Aad", ilVulcano.balie);
    kok1.start();

    // kleine pauze voor het opstarten van de volgende kok
    try {
      Thread.sleep(100);
    }
    catch (InterruptedException e2) {}
    Kok kok2 = new Kok("Bert", ilVulcano.balie);
    kok2.start();

    // kleine pauze voor het opstarten van de volgende kok
    try {
      Thread.sleep(100);
    }
    catch (InterruptedException e2) {}
    Kok kok3 = new Kok("Cees", ilVulcano.balie);
    kok3.start();

    Ober ober1 = new Ober("Salvatore", ilVulcano.balie);
    ober1.start();

    // kleine pauze voor het opstarten van de volgende ober
    try {
      Thread.sleep(105);
    }
    catch (InterruptedException e2) {}
    Ober ober2 = new Ober("Toni", ilVulcano.balie);
    ober2.start();

    // Wacht SIMULATIETIJD voordat de threads van de diverse objecten worden gestopt.
    try {
      Thread.sleep(SIMULATIETIJD);
    }
    catch (InterruptedException e) {}

    kok1.stop();
    kok2.stop();
    kok3.stop();
    ober1.stop();
    ober2.stop();

    // Wacht 2 seconden en toon dan wat er nog op de uitgiftebalie staat.
    try {
      Thread.sleep(2000);
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
