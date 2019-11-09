/**
 * 
 */
package studentadmindom;

/**
 * @author Johan Elzinga
 *
 */
public abstract class Student {

  private String naam = null;
  private Opleiding opleiding = null;

  /**
   * Maakt een nieuw student object aan met gegeven naam en opleiding object
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   * @param naam De naam van de student.
   * @param opleiding Een opleidings object.
   */
  Student(String naam, Opleiding opleiding) {
    this.naam = naam;
    this.opleiding = opleiding;
  }

  /**
   * Vehoogt het aantal behaalde punten of behaalde modules van de student.
   * @param punten Het aantal punten of modules wat toegevoegd moet worden.
   */
  public abstract void verhoogDoel(double punten);

  /**
   * Geeft een string terug met daarin het studie resultaat van de student
   * @return een string met het aantal behaalde punten of behaalde modules gevolgd door een melding 'geslaagd' of 'niet geslaagd'
   */
  public abstract String getStudieResultaat();

  /**
   * Geeft de naam van de gevolgde opleiding terug.
   * @return De naam van de opleiding.
   */
  public String getOpleidingsNaam() {
    return getOpleiding().getNaam();
  }

  /**
   * Geeft de naam van de student.
   * @return De naam van de student.
   */
  public String getNaam() {
    return naam;
  }

  /**
   * Geeft een object van de gevolgde opleiding terug
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   * @return Het opleidings object
   */
  Opleiding getOpleiding() {
    return opleiding;
  }
}
