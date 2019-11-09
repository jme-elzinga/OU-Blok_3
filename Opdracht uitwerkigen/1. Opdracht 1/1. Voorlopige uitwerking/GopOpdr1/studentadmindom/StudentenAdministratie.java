/**
 * 
 */
package studentadmindom;

import java.util.ArrayList;

/**
 * @author Johan Elzinga
 *
 */
public class StudentenAdministratie {

  private static ArrayList<Student> studenten = new ArrayList<Student>();
  private static ArrayList<Opleiding> opleidingen = new ArrayList<Opleiding>();
  private static enum Type {
    /**
     * Reguliere opleiding.
     */
    STUDIE,
    /**
     * CPP opleiding.
     */
    CPP};
  
  /**
   * Maakt een nieuwe StudentenAdministratie object aan en vult deze met opleidingen.
   */
  public StudentenAdministratie() {
    initOpleidingen();
  }
  
  /**
   * Roept de voegOpleidingen methode aan voor het toevoegen van opleidingen aan de arraylist opleidingen.
   */
  private void initOpleidingen() {
    voegOpleidingToe(Type.STUDIE, "Informatica", 200);
    voegOpleidingToe(Type.STUDIE, "Wiskunde", 160);
    voegOpleidingToe(Type.CPP, "CPP Java", 6);
    voegOpleidingToe(Type.CPP, "CPP Softwarearchitect", 4);
    voegOpleidingToe(Type.CPP, "CPP Systeemontwikkelaar", 3);
  }

  /**
   * voegt, afhankelijk van het type, een opleiding met de bijbehorende studiepunten of aantal modules toe aan de arraylist opleidingen.
   */
  private void voegOpleidingToe(Type type, String naam, int doel) {
    if(type.equals(Type.STUDIE)) {
      opleidingen.add(new Studie(naam, doel));
    } else if (type.equals(Type.CPP)) {
      opleidingen.add(new Cpp(naam, doel));
    }
  }

  /**
   * Voeg een reguliere student toe aan de arraylist met studenten
   * @param naam De achternaam van de student
   * @param studienaam De naam van de studie die de student volgt.
   */
  public static void voegRegulierToe(String naam, String studienaam) {
    studenten.add(new Regulier(naam, zoekOpleiding(studienaam)));
  }

  /**
   * Voeg een scholer to aan de arraulist met studenten
   * @param naam De achternaam van de scholer
   * @param cppnaam De naam van het CPP traject welke de scholer volgt.
   */
  public static void voegScholerToe(String naam, String cppnaam) {
    studenten.add(new Scholer(naam, zoekOpleiding(cppnaam)));
  }

  /**
   * Zoekt een opleiding met de gegeven naam en geeft een obkect naar de desbetreffende opleiding terug of null wanneer deze niet bestaat of wanneer de arraylist met opleidingen leeg is.
   */
  private static Opleiding zoekOpleiding(String naam) {
    if (opleidingen.size() > 0 ) {
      for (Opleiding o : opleidingen) {
        if (o.getNaam().contentEquals(naam)) {
          return o;
        }
      }
    }
    return null;
  }

  /**
   * Zoekt een student met de gegeven naam en geeft een Student object terug of null wanneer de student niet gevonden wordt of wanneer de student arraylist leeg is.
   */
  private static Student zoekStudent(String naam) {
    if (studenten.size() > 0 ) {
      for (Student s : studenten) {
        if (s.getNaam().contentEquals(naam)) {
          return s;
        }
      }
    }
    return null;
  }

  /**
   * Geeft een string met student informatie.
   * @param naam De naam van de student
   * @return De string met de gevraagde informatie of een lege sting wanneer de student niet gevonden wordt.
   */
  public static String getStudentInfo(String naam) {
    Student s = zoekStudent(naam);
    if (s != null) {
      return s.getNaam() + ", " + s.getOpleidingsNaam() + ", " + s.getStudieResultaat();
    }
    return "";
  }

  /**
   * Verhoogt de behaalde punten van een reguliere student met het gegeven aantal punten
   * @param naam De naam van de reguliere student.
   * @param punten Het aantal punten waarmee verhoogd moet worden. 
   */
  public static void verhoogRegulier(String naam, double punten) {
    Student s = zoekStudent(naam);
    if (s != null && s instanceof Regulier) {
      s.verhoogDoel(punten);
    }
  }

  /**
   * Verhoogt het aantal behaalde modules van een scholer met 1 module.
   * @param naam De naam van de student.
   */
  public static void verhoogScholer(String naam) {
    Student s = zoekStudent(naam);
    if (s != null && s instanceof Scholer) {
      s.verhoogDoel(1.0);
    }
  }

  /**
   * Geeft een string van alle studenten met hun studie info
   * @return een string met resultaten.
   */
  public static String toonStudenten() {
    String resultaat = "";
    for (Student s : studenten) {
      resultaat = resultaat + getStudentInfo(s.getNaam()) + "\n";
    }
    return resultaat;
  }

  /**
   * Geeft een lijst met de namen van alle reguliere opleidingen.
   * @return Een arraylist met namen
   */
  public static ArrayList<String> getStudieNaam() {
    ArrayList<String> studieNamen = new ArrayList<>();
    for(Opleiding o : opleidingen) {
      if(o instanceof Studie) {
        studieNamen.add(o.getNaam());
      }
    }
    return studieNamen;
  }

  /**
   * Geeft een lijst met de namen van alle CPP opleidingen.
   * @return Een arraylist met namen
   */
  public static ArrayList<String> getCppNaam() {
    ArrayList<String> cppNamen = new ArrayList<>();
    for(Opleiding o : opleidingen) {
      if(o instanceof Cpp) {
        cppNamen.add(o.getNaam());
      }
    }
    return cppNamen;
  }
}
