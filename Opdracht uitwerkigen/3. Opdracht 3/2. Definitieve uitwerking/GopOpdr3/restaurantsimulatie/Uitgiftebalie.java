/**
 * 
 */
package restaurantsimulatie;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse die verantwoordelijk is voor het beheer van de de lijst met maaltijden
 * @author Johan Elzinga
 */
public class Uitgiftebalie {

  private List<Maaltijd> maaltijden;

  /**
   * Maakt een nieuw uitgiftebalie object aan met een arraylist voor de maailtijden.
   */
  public Uitgiftebalie() {
    maaltijden = new ArrayList<Maaltijd>();
  }

  /**
   * Voegt het gegeven maaltijd object toe aan de lijst met maaltijden.
   * @param maaltijd Het maaltijd object
   */
  public synchronized void plaatsMaaltijd(Maaltijd maaltijd) {
    maaltijden.add(maaltijd);
  }

  /**
   * Kijkt of er in de lijst met maaltijden iets staat en geeft, wanneer de lijst niet leeg is, een maaltijd object van de eerste maaltijd in de lijst terug en verwijderd deze daarna uit de maaltijdlijst.
   * @return Het maaltijd object of null waneer de lijst met maaltijden leeg is
   */
  public synchronized Maaltijd pakMaaltijd() {
    Maaltijd maaltijd = null;
    if (maaltijden.size() > 0) {
      maaltijd = maaltijden.get(0);
      maaltijden.remove(maaltijd);
    }
    return maaltijd;
  }
}
