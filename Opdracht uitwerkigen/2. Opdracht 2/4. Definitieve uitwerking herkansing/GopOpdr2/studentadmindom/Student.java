/**
 * 
 */
package studentadmindom;

/**
 * Abstracte klasse voor het beheer van de studenten informatie.
 * @author Johan Elzinga
 *
 */
abstract class Student implements Cloneable {

  private String naam = null;
  private Opleiding opleiding = null;
  private static final int MIN_NAAM_LENGTE=2;

  /**
   * Maakt een nieuw student object aan met gegeven naam en opleiding object
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   * @param naam De naam van de student.
   * @param opleiding Een opleidings object.
   * @throws StudentAdminException Wanneer geen opleidings object is mee gegeven of wanneer de naam korter is dan MIN_NAAM_LENGTE
   */
  Student(String naam, Opleiding opleiding) throws StudentAdminException {
    if (opleiding == null) {
      throw new StudentAdminException("Geen opleidings mee gegeven bij het aanmaken van de student");
    }
    if (naam.trim().length()<MIN_NAAM_LENGTE) {
      throw new StudentAdminException("De opgegeven naam is korter dan " + MIN_NAAM_LENGTE + " tekens");
    }
    this.naam = naam.trim();
    this.opleiding = opleiding;
  }

  /**
   * Geeft een string terug met daarin het studie resultaat van de student
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   * @return een string met het aantal behaalde punten of behaalde modules gevolgd door een tekst met het type resultaat (punten of modules)
   */
  abstract String getStudieResultaat();
  
  /**
   * Geeft de naam van de student.
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   * @return De naam van de student.
   */
  String getNaam() {
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

  /**
   * Geeft de naam van de gevolgde opleiding terug.
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package studentadmindom.<br>
   * @return De naam van de opleiding.
   */
  String getOpleidingsNaam() {
    return getOpleiding().getNaam();
  }

  /**
   * Controleert of de inhoud van dit object gelijk is aan het gegeven object
   * @param obj Het te controleren object
   * @return True wanneer de objecten inhoudelijk gelijk zijn anders false
   */
  public abstract boolean equals(Object obj);

  /**
   * Geeft een string representatie terug van dit object
   * @return Een string representatie van dit object
   */
  public abstract String toString();

  /**
   * Maakt een kopie van dit object.
   * @return Een nieuw object welke een exacte kopie is van het huidige object.
   */
  public Object clone() {
    Student student = null;
    try {
      student = (Student)super.clone();
      student.setOpleiding((Opleiding) this.getOpleiding().clone());
      return student;
    } catch (CloneNotSupportedException e) {
      System.out.println("CloneNotSupportedException gegenereerd vanuit : "+e.getMessage());
      return null;
    }
  }

  /**
   * Vult het attribuut opleiding met een opleidings object.
   * <br>Bijzonderheden:<br> - Deze methode is private en is bedoeld voor de clone methode.<br>
   * @param opleiding Het opleidings object wat gekoppeld moet worden aan de student.
   */
  private void setOpleiding(Opleiding opleiding) {
    if (opleiding != null) {
      this.opleiding = opleiding;
    }
  }
}
