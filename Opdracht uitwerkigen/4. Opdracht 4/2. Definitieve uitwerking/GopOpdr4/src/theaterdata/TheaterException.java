/**
 * 
 */
package theaterdata;


/**
 * Eigen Exception klasse
 * 
 * @author Johan Elzinga
 *
 */

@SuppressWarnings("serial")
public class TheaterException extends Exception {

  /**
   * Constuctor voor een nieuwe exception zonder bericht 
   * 
   */
  public TheaterException(){
    super();
  }

  /**
   * Constuctor voor een nieuwe exception met bericht
   * 
   * @param s Het bericht van de exception. Kan met de methode getMessage() opgevraagd worden.
   */
  public TheaterException(String s){
    super(s);
  }
}
