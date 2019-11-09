/**
 * 
 */
package studentadmindom;

/**
 * Klasse voor het beheer van de reguliere opleidings informatie.
 * @author Johan Elzinga
 *
 */
class Studie extends Opleiding {

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
   * Geeft het aantal punten terug welke gehaald moeten worden om voor deze studie te slagen.
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   * @return Het aantal punten.
   */
  int getSlagingsResultaat() {
    return punten;
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
    String naam2 = ((Studie)obj).getNaam();
    int punten2 = ((Studie)obj).getSlagingsResultaat();
    return (this.getNaam().equals(naam2) && punten == punten2);
  }

  /**
   * Geeft een string representatie terug van dit object
   * @return Een string representatie van dit object
   */
  public String toString() {
    return "Studie: " + this.getNaam() + ". Punten: " + this.getSlagingsResultaat() + ".";
  }
}
