/**
 * 
 */
package studentadmindom;

/**
 * @author Johan Elzinga
 *
 */
public class Cpp extends Opleiding {

  private int modules = 0;

  /**
   * Maakt een nieuw object van een CPP opleiding aan met de gegeven naam.
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   * @param naam De naam van de CPP opleiding.
   * @param modules Het aantal modules wat benodigd is om te slagen.
   */
  Cpp(String naam, int modules) {
    super(naam);
    this.modules = modules;
  }
  
  /**
   * Controleert het aantal behaalde modules en geeft een string terug met daarin de tekst 'geslaagd' of 'niet geslaagd'.
   * @param behaald Het aantal behaalde modules.
   * @return Een string met de tekst 'geslaagd' of 'niet geslaagd'.
   */
  public String geefSlagingsInfo(double behaald) {
    if (modules <= (int) behaald) {
      return "geslaagd";
    }
    return "niet geslaagd";
  }
}
