/**
 * 
 */
package studentadmindom;

/**
 * Eigen Exception klasse
 * @author Johan Elzinga
 *
 */
@SuppressWarnings("serial")
class StudentAdminException extends Exception {

  /**
   * Constuctor voor een nieuwe exception zonder bericht 
   */
  public StudentAdminException() {
    super();
  }
  
  /**
   * Constuctor voor een nieuwe exception met bericht
   * @param message Het bericht van de exception. Kan met de methode getMessage() opgevraagd worden.
   */
  public StudentAdminException(String message) {
    super(message);
  }

}
