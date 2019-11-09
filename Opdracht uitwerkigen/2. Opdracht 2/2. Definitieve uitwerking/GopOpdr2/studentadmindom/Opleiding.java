/**
 * 
 */
package studentadmindom;

/**
 * Abstracte klasse voor het beheer van de opleidings informatie.
 * @author Johan Elzinga
 *
 */
abstract class Opleiding implements Cloneable {

  private String naam = null;

  /**
   * Maakt een nieuw opleidings object aan met de gegeven naam.
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   * @param naam De naam van de opleiding.
   */
  Opleiding(String naam) {
    this.naam = naam;
  }

  /**
   * Geeft de naam van de opleiding.
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   * @return De naam van de opleiding.
   */
  String getNaam() {
    return naam;
  }

  /**
   * Controleert of de inhoud van dit object gelijk is aan het gegeven object
   * @param obj Het te controleren object
   * @return True wanneer de objecten inhoudelijk gelijk zijn anders false
   */
  public abstract boolean equals(Object obj);

  /**
   * Geeft een string representatie terug van dit object
   * @return Een string representatie van dit object
   */
  public abstract String toString();

  /**
   * Maakt een kopie van dit object.
   * @return Een nieuw object welke een exacte kopie is van het huidige object.
   */
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      System.out.println("CloneNotSupportedException comes out : "+e.getMessage());
      return null;
    }
  }
}
