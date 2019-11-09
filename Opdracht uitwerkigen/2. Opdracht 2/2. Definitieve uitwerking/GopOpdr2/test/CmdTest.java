/**
 * 
 */
package test;

import studentadmindom.StudentenAdministratie;

/**
 * Klasse om de situaties te testen welke niet in de Gui voor kunnen komen
 * @author Johan Elzinga
 *
 */
class CmdTest {

  public static void main(String[] args) {
    StudentenAdministratie studentAdmin = new StudentenAdministratie();

    //Testen van voegRegulierToe(naam, studienaam) buiten de Gui
    // Test koppelen van een student aan een CPP
    System.out.println("koppelen student aan CPP      : " + studentAdmin.voegRegulierToe("Johan", "CPP Java"));
    // Test met een niet bestaande studie
    System.out.println("Foute studienaam              : " + studentAdmin.voegRegulierToe("Johan", "Informatica123"));
    // Test met een foutieve student naam (te weinig tekens)
    System.out.println("Foute studentnaam (<2 tekens) : " + studentAdmin.voegRegulierToe(" J ", "Informatica"));
    // Test met een foutieve student naam (Dubbele naam)
    System.out.println("Correcte gegevens             : " + studentAdmin.voegRegulierToe("Johan", "Informatica"));
    System.out.println("Dubbele studentnaam           : " + studentAdmin.voegRegulierToe("Johan", "Informatica"));

    System.out.println("-----------");

    //Testen van voegScholerToe(naam, cppnaam) buiten de Gui
    // Test koppelen van een scholer aan een Studie
    System.out.println("koppelen scholer aan studie   : " + studentAdmin.voegScholerToe("Jan", "Informatica"));
    // Test met een niet bestaande CPP
    System.out.println("Foute CPP                     : " + studentAdmin.voegScholerToe("Jan", "CPP Java123"));
    // Test met een foutieve student naam (te weinig tekens)
    System.out.println("Foute studentnaam (<2 tekens) : " + studentAdmin.voegScholerToe("J", "CPP Java"));
    // Test met een foutieve student naam (Dubbele naam)
    System.out.println("Correcte gegevens             : " + studentAdmin.voegScholerToe("Jan", "CPP Java"));
    System.out.println("Dubbele studentnaam           : " + studentAdmin.voegScholerToe("Jan", "CPP Java"));
  }

}
