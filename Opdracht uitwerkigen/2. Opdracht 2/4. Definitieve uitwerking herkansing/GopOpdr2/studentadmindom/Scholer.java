/**
 * 
 */
package studentadmindom;

/**
 * Klasse voor het beheren van informatie van deelnemers aan scholingstrajecten (Scholers).
 * @author Johan Elzinga
 *
 */
class Scholer extends Student {

  private int behaaldeModules = 0;

  /**
   * Maakt een nieuw object van een scholer aan.
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   * @param naam De naam van de student.
   * @param opleiding Een opleidings object.
   * @throws StudentAdminException Een eventuele exception vanuit de super class (student.java)
   */
  Scholer(String naam, Opleiding opleiding) throws StudentAdminException {
    super(naam, opleiding);
  }

  /**
   * Vehoogt het aantal behaalde modules van de student.
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   */
  void verhoogModules() {
    behaaldeModules++;
  }

  /**
   * Geeft het behaalde studie resultaat van de student
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   * @return het aantal behaalde punten
   */
  int getModules() {
    return behaaldeModules;
  }

  /**
   * Geeft een string terug met daarin het studie resultaat van de student
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   * @return een string met het aantal behaalde modules gevolgd door de tekst ' modules'
   */
  String getStudieResultaat() {
    return "" + behaaldeModules + " modules";
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
    String naam2 = ((Scholer)obj).getNaam();
    Opleiding opleiding2 = ((Scholer)obj).getOpleiding();
    return (this.getNaam().equals(naam2) && this.getOpleiding().equals(opleiding2));
  }

  /**
   * Geeft een string representatie terug van dit object
   * @return Een string representatie van dit object
   */
  public String toString() {
    return "Scholer: " + this.getNaam() + ". CPP: " + this.getOpleidingsNaam() + ". Modules: " + this.behaaldeModules + ".";
  }
}
