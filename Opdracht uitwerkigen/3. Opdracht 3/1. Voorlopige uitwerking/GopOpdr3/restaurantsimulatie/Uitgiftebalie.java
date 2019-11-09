/**
 * 
 */
package restaurantsimulatie;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Klasse die verantwoordelijk is voor het beheer van de de lijst met maaltijden
 * @author Johan Elzinga
 */
public class Uitgiftebalie {

  private volatile ArrayList<Maaltijd> maaltijden;
  private final ReentrantLock lock = new ReentrantLock();

  /**
   * Maakt een nieuw uitgiftebalie object aan en een arraylist voor de maailtijden.
   */
  public Uitgiftebalie() {
    maaltijden = new ArrayList<>();
  }

  /**
   * Voegt het gegeven maaltijd object toe aan de lijst met maaltijden.
   * @param maaltijd Het maaltijd object
   */
  public synchronized void plaatsMaaltijd(Maaltijd maaltijd) {
    lock.lock();
    try {
      maaltijden.add(maaltijd);
    } finally {
      lock.unlock();
    }
  }

  /**
   * Kijkt of er in de lijst met maaltijden iets staat en geeft, wanneer de lijst niet leeg is, een maaltijd object van de eerste maaltijd in de lijst terug en verwijderd deze daarna uit de maaltijdlijst.
   * @return Het maaltijd object of null waneer de lijst met maaltijden leeg is
   */
  public Maaltijd pakMaaltijd() {
    lock.lock();
    try {
      Maaltijd maaltijd = null;
      if (maaltijden.size() > 0) {
        maaltijd = maaltijden.get(0);
        maaltijden.remove(maaltijd);
      }
      return maaltijd;
    } finally {
      lock.unlock();
    }
  }
}
