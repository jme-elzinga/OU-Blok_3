/**
 * 
 */
package studentadmindom;

/**
 * @author Johan Elzinga
 *
 */
public abstract class Opleiding {

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
   * GEeft de naam van de opleiding.
   * @return De naam van de opleiding.
   */
  public String getNaam() {
    return naam;
  }

  /**
   * Controleert het aantal punten en geeft een string terug met daarin de tekst 'geslaagd' of 'niet geslaagd'.
   * @param behaald Het aantal behaalde punten of modules
   * @return Een string met de tekst 'geslaagd' of 'niet geslaagd'.
   */
  public abstract String geefSlagingsInfo(double behaald);
}
