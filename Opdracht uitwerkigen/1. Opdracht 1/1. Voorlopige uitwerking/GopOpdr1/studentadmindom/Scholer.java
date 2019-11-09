/**
 * 
 */
package studentadmindom;

/**
 * @author Johan Elzinga
 *
 */
public class Scholer extends Student {

  private int behaaldeModules = 0;

  /**
   * Maakt een nieuw object van een scholer aan.
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   * @param naam De naam van de student.
   * @param opleiding Een opleidings object.
   */
  Scholer(String naam, Opleiding opleiding) {
    super(naam, opleiding);
  }

  /**
   * Vehoogt het aantal behaalde modules van de student.
   * @param punten Het aantal modules wat toegevoegd moet worden.
   */
  public void verhoogDoel(double punten) {
    behaaldeModules = behaaldeModules + (int) punten;
  }

  /**
   * Geeft een string terug met daarin het studie resultaat van de student
   * @return een string met het aantal behaalde modules gevolgd door een melding 'geslaagd' of 'niet geslaagd'
   */
  public String getStudieResultaat() {
    return "" + behaaldeModules + " modules, " + this.getOpleiding().geefSlagingsInfo(behaaldeModules);
  }
}
