/**
 * 
 */
package restaurantsimulatie;

/**
 * Klasse die verantwoordelijk is voor het aanmaken van een maaltijd.
 * @author Johan Elzinga
 */
public class Maaltijd {
  private String omschrijving = "";
  private int tafelnummer = 0;

  /**
   * Maakt een maaltijd object met de gegeven omschrijving en het tafelnummer.
   * @param omschrijving De omschrijving van de maaltijd.
   * @param tafelnummer Het tafelnummer waar de maaltijd voor bestemd is.
   */
  public Maaltijd(String omschrijving, int tafelnummer) {
    this.omschrijving = omschrijving;
    this.tafelnummer = tafelnummer;
  }

  /**
   * Geeft het tafelnummer.
   * <br>Bijzonderheden:<br> - Deze methode kan alleen aangeroepen worden binnen het package restaurantsimulatie.<br>
   * @return Het tafelnummer van dit object.
   */
  int getTafelnummer() {
    return tafelnummer;
  }

  /**
   * Geeft een string met daarin de omschrijving van de maaltijd en het bijbehorende tafelnummer.
   */
  public String toString() {
    return "maaltijd '" + omschrijving + "' voor tafel " + tafelnummer;
  }
}
