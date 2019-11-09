/**
 * 
 */
package test;

import studentadmindom.StudentAdminException;
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
    try {
      studentAdmin.voegRegulierToe("Johan", "CPP Java");
    }
    catch (StudentAdminException e) {
      System.out.println("koppelen student aan CPP      : " + e.getMessage());
    }
    // Test met een niet bestaande studie
    try {
      studentAdmin.voegRegulierToe("Johan", "Informatica123");
    }
    catch (StudentAdminException e) {
      System.out.println("Foute studienaam              : " + e.getMessage());
    }
    // Test met een foutieve student naam (te weinig tekens)
    try {
      studentAdmin.voegRegulierToe(" J ", "Informatica");
    }
    catch (StudentAdminException e) {
      System.out.println("Foute studentnaam (<2 tekens) : " + e.getMessage());
    }
    // Test met een foutieve student naam (Dubbele naam)
    try {
      // Correcte gegevens ingevoerd
      studentAdmin.voegRegulierToe("Johan", "Informatica");
      // dubbele naam ingevoerd
      studentAdmin.voegRegulierToe("Johan", "Informatica");
    }
    catch (StudentAdminException e) {
      System.out.println("Dubbele studentnaam           : " + e.getMessage());
    }

    System.out.println("-----------");

    //Testen van voegScholerToe(naam, cppnaam) buiten de Gui
    // Test koppelen van een scholer aan een Studie
    try {
      studentAdmin.voegScholerToe("Jan", "Informatica");
    }
    catch (StudentAdminException e) {
      System.out.println("koppelen scholer aan studie   : " + e.getMessage());
    }
    // Test met een niet bestaande CPP
    try {
      studentAdmin.voegScholerToe("Jan", "CPP Java123");
    }
    catch (StudentAdminException e) {
      System.out.println("Foute CPP                     : " + e.getMessage());
    }
    // Test met een foutieve student naam (te weinig tekens)
    try {
      studentAdmin.voegScholerToe("J", "CPP Java");
    }
    catch (StudentAdminException e) {
      System.out.println("Foute studentnaam (<2 tekens) : " + e.getMessage());
    }
    // Test met een foutieve student naam (Dubbele naam)
    try {
      // Correcte gegevens ingevoerd
      studentAdmin.voegScholerToe("Jan", "CPP Java ");
      // dubbele naam ingevoerd
      studentAdmin.voegScholerToe("Jan", "CPP Java");
    }
    catch (StudentAdminException e) {
      System.out.println("Dubbele studentnaam           : " + e.getMessage());
    }
  }

}
