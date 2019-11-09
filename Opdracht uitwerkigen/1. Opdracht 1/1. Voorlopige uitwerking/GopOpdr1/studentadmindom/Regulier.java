/**
 * 
 */
package studentadmindom;

/**
 * @author Johan Elzinga
 *
 */
public class Regulier extends Student {

  private double behaaldePunten = 0.0;

  /**
   * Maakt een nieuw object van een reguliere student aan.
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   * @param naam De naam van de student.
   * @param opleiding Een opleidings object.
   */
  Regulier(String naam, Opleiding opleiding) {
    super(naam, opleiding);
  }

  /**
   * Vehoogt het aantal behaalde punten van de student.
   * @param punten Het aantal punten toegevoegd moet worden.
   */
  public void verhoogDoel(double punten) {
    behaaldePunten = behaaldePunten + punten;
  }

  /**
   * Geeft een string terug met daarin het studie resultaat van de student
   * @return een string met het aantal behaalde punten gevolgd door een melding 'geslaagd' of 'niet geslaagd'
   */
  public String getStudieResultaat() {
    return "" + behaaldePunten + " studiepunten, " + getOpleiding().geefSlagingsInfo(behaaldePunten);
  }
}
