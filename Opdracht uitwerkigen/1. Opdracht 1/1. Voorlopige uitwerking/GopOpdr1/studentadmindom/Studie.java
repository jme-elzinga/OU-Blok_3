/**
 * 
 */
package studentadmindom;

/**
 * @author Johan Elzinga
 *
 */
public class Studie extends Opleiding {

  private int punten = 0;

  /**
   * Maakt een nieuw object van een reguliere opleiding aan met de gegeven naam.
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   * @param naam De naam van de reguliere opleiding.
   * @param punten Het aantal punten wat benodigd is om te slagen.
   */
  Studie(String naam, int punten) {
    super(naam);
    this.punten = punten;
  }

  /**
   * Controleert het aantal punten en geeft een string terug met daarin de tekst 'geslaagd' of 'niet geslaagd'.
   * @param behaald Het aantal behaalde punten
   * @return Een string met de tekst 'geslaagd' of 'niet geslaagd'.
   */
  public String geefSlagingsInfo(double behaald) {
    if (this.punten <= (int) behaald) {
      return "geslaagd";
    }
    return "niet geslaagd";
  }
}
