/**
 * 
 */
package studentadmindom;

/**
 * Klasse voor het beheren van informatie van reguliere studenten.
 * @author Johan Elzinga
 *
 */
class Regulier extends Student {

  private double behaaldePunten = 0.0;

  /**
   * Maakt een nieuw object van een reguliere student aan.
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   * @param naam De naam van de student.
   * @param opleiding Een opleidings object.
   */
  Regulier(String naam, Opleiding opleiding) throws StudentAdminException {
    super(naam, opleiding);
  }

  /**
   * Vehoogt het aantal behaalde punten van de student.
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   * @param punten Het aantal punten toegevoegd moet worden.
   */
  void verhoogPunten(double punten) {
    behaaldePunten = behaaldePunten + punten;
  }

  /**
   * Geeft het behaalde studie resultaat van de student
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   * @return het aantal behaalde punten
   */
  double getBehaaldePunten() {
    return behaaldePunten;
  }

  /**
   * Geeft een string terug met daarin het studie resultaat van de student
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   * @return een string met het aantal behaalde punten gevolgd door de tekst ' punten'
   */
  String getStudieResultaat() {
    return "" + behaaldePunten +  " punten";
  }

  /**
   * Controleert of de inhoud van dit object gelijk is aan het gegeven object
   * @param obj Het te controleren object
   * @return True wanneer de objecten inhoudelijk gelijk zijn anders false
   */
  public boolean equals(Object obj) {
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    String naam2 = ((Regulier)obj).getNaam();
    Opleiding opleiding2 = ((Regulier)obj).getOpleiding();
    return (this.getNaam().equals(naam2) && this.getOpleiding().equals(opleiding2));
  }

  /**
   * Geeft een string representatie terug van dit object
   * @return Een string representatie van dit object
   */
  public String toString() {
    return "Student: " + this.getNaam() + ". Studie: " + this.getOpleidingsNaam() + ". Punten: " + this.behaaldePunten + ".";
  }
}
