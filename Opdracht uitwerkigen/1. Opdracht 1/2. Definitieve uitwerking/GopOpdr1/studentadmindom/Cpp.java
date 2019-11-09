/**
 * 
 */
package studentadmindom;

/**
 * Klasse voor het beheer van de CPP opleidings informatie.
 * @author Johan Elzinga
 *
 */
class Cpp extends Opleiding {

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
   * Geeft het aantal modules terug welke gehaald moeten worden om voor deze CPP te slagen.
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   * @return Het aantal modules.
   */
  int getSlagingsResultaat() {
    return modules;
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
    String naam2 = ((Cpp)obj).getNaam();
    int modules2 = ((Cpp)obj).getSlagingsResultaat();
    return (this.getNaam().equals(naam2) && modules == modules2);
  }

  /**
   * Geeft een string representatie terug van dit object
   * @return Een string representatie van dit object
   */
  public String toString() {
    return "CPP: " + this.getNaam() + ". Modules: " + this.getSlagingsResultaat() + ".";
  }
}
