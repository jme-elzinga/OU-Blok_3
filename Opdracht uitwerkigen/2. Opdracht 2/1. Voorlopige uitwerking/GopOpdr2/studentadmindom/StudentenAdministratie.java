/**
 * 
 */
package studentadmindom;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse die verantwoordelijk is voor studenten administratie (aanmaken en beheren van studenten en opleidingen, het beheren van de lijsten van studenten en opleidingen en het beheren en tonen van de resultataten van de studenten).
 * @author Johan Elzinga
 *
 */
public class StudentenAdministratie {

  private static ArrayList<Student> studenten = new ArrayList<Student>();
  private static ArrayList<Opleiding> opleidingen = new ArrayList<Opleiding>();
  private static final int MIN_NAAM_LENGTE=2;
  
  /**
   * Maakt een nieuwe StudentenAdministratie object aan en vult deze met opleidingen.
   */
  public StudentenAdministratie() {
    initOpleidingen();
  }
  
  /**
   * Roept de voegOpleidingen methode aan voor het toevoegen van opleidingen aan de arraylist opleidingen.
   */
  private static void initOpleidingen() {
    voegOpleidingToe("Studie", "Informatica", 200);
    voegOpleidingToe("Studie", "Wiskunde", 160);
    voegOpleidingToe("CPP", "CPP Java", 6);
    voegOpleidingToe("CPP", "CPP Softwarearchitect", 4);
    voegOpleidingToe("CPP", "CPP Systeemontwikkelaar", 3);
  }

  /**
   * voegt, afhankelijk van het type (Studie of CPP), een opleiding met de bijbehorende studiepunten of aantal modules toe aan de arraylist opleidingen.
   */
  private static void voegOpleidingToe(String type, String naam, int doel) {
    if(type.equals("Studie")) {
      opleidingen.add(new Studie(naam, doel));
    } else if (type.equals("CPP")) {
      opleidingen.add(new Cpp(naam, doel));
    }
  }

  /**
   * Voeg een reguliere student toe aan de arraylist met studenten
   * @param naam De achternaam van de student
   * @param studienaam De naam van de studie die de student volgt.
   * @return Een string met een foutmelding of leeg wanneer de student is aangemaakt.
   */
  public String voegRegulierToe(String naam, String studienaam) {
    String melding = controleerNaam(naam);
    if (melding.contentEquals("")) {
      Opleiding opleiding = zoekOpleiding(studienaam);
      if (opleiding == null) {
        melding = "De door u gegeven Studie bestaat niet (" + studienaam + ")";
      } else if (!(opleiding instanceof Studie)) {
        melding = "U hebt geen Studie opgegeven (" + studienaam + ")";
      } else {
        // Zowel de Student als de Studie zijn juist, maak een nieuw studenten object aan en voeg die toe aan de lijst.
        studenten.add(new Regulier(naam, opleiding));
      }
    }
    return melding;
  }

  /**
   * Voeg een scholer to aan de arraylist met studenten
   * @param naam De achternaam van de scholer
   * @param cppnaam De naam van het CPP traject welke de scholer volgt.
   * @return Een string met een foutmelding of leeg wanneer de scholer is aangemaakt.
   */
  public String voegScholerToe(String naam, String cppnaam) {
    String melding = controleerNaam(naam);
    if (melding.contentEquals("")) {
      Opleiding opleiding = zoekOpleiding(cppnaam);
      if (opleiding == null) {
        melding = "De door u gegeven CPP bestaat niet (" + cppnaam + ")";
      } else if (!(opleiding instanceof Cpp)) {
        melding = "U hebt geen CPP opgegeven (" + cppnaam + ")";
      } else {
        // Zowel de Scholer als de Cpp zijn juist, maak een nieuw Scholer object aan en voeg die toe aan de lijst.
        studenten.add(new Scholer(naam, opleiding));
      }
    }
    return melding;
  }

  /**
   * Controle of de naam de minimale lengte MIN_NAAM_LENGTE heeft en nog niet in het systeem voorkomt.
   * @param naam De te controleren naam
   * @return Een string met een foutmelding of leeg wanneer aan alle voorwaarden is voldaan.
   */
  private static String controleerNaam(String naam) {
    String melding="";
    Student student = zoekStudent(naam);
    if (naam.length()<MIN_NAAM_LENGTE) {
      // lengte naam korter dan MIX_NAAM_LENGTE
      melding = "De naam moet minimaal " + MIN_NAAM_LENGTE + " tekens lang zijn (" + naam + ")";
    } else if (student != null) {
      // Bestaande naam ingevoerd
      melding = "De naam die u ingevoerd heeft bestaat al in het systeem (" + student.getClass().getSimpleName() + ": "+ naam + ")";
    }
    return melding;
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
  public String getStudentInfo(String naam) {
    Student s = zoekStudent(naam);
    if (s != null) {
      return s.getNaam() + ", " + s.getOpleidingsNaam() + ", " + s.getStudieResultaat() + ", " + bepaalSlagingsResultaat(s);
    }
    return "";
  }

  /**
   * Controleert of de student geslaagd is en geeft dat als tekst terug
   * @param student Een student object
   * @return de tekst 'geslaagd' of 'niet geslaagd'
   */
  private static String bepaalSlagingsResultaat(Student student) {
    Opleiding opleiding=student.getOpleiding();
    opleiding.getSlagingsResultaat();
    if (student.getResultaat() >= (double)opleiding.getSlagingsResultaat()) {
      return "geslaagd";
    }
    return "niet geslaagd";
  }

  /**
   * Verhoogt de behaalde punten van een reguliere student met het gegeven aantal punten
   * @param naam De naam van de reguliere student.
   * @param punten Het aantal punten waarmee verhoogd moet worden. 
   */
  public String verhoogRegulier(String naam, double punten) {
    String bericht = "";
    Student s = zoekStudent(naam);
    if (s == null) {
      bericht = "De door u ingevoerde naam bestaat niet (" + naam + ")";
    } else if (!(s instanceof Regulier)) {
      bericht = "De door u ingevulde naam volgt geen reguliere studie (" + s.getClass().getSimpleName() + ": " + naam + ")";
    } else {
      ((Regulier)s).verhoogDoel(punten);
    }
    return bericht;
  }

  /**
   * Verhoogt het aantal behaalde modules van een scholer.
   * @param naam De naam van de student.
   */
  public String verhoogScholer(String naam) {
    String bericht = "";
    Student s = zoekStudent(naam);
    if (s == null) {
      bericht = "De door u ingevoerde naam bestaat niet (" + naam + ")";
    } else if (!(s instanceof Scholer)) {
      bericht = "De door u ingevulde naam volgt geen CPP (" + s.getClass().getSimpleName() + ": " + naam + ")";
    } else {
      ((Scholer)s).verhoogDoel();
    }
    return bericht;
  }

  /**
   * Geeft een string van alle studenten met hun studie info
   * @return een string met resultaten.
   */
  public String toonStudenten() {
    String resultaat = "";
    for (Student s : studenten) {
      resultaat = resultaat + getStudentInfo(s.getNaam()) + "\n";
    }
    return resultaat;
  }

  /**
   * Geeft een lijst met de namen van alle reguliere opleidingen.
   * @return Een lijst met namen
   */
  public List<String> getStudieNaam() {
    List<String> studieNamen = new ArrayList<String>();
    for(Opleiding o : opleidingen) {
      if(o instanceof Studie) {
        studieNamen.add(o.getNaam());
      }
    }
    return studieNamen;
  }

  /**
   * Geeft een lijst met de namen van alle CPP opleidingen.
   * @return Een lijst met namen
   */
  public List<String> getCppNaam() {
    List<String> cppNamen = new ArrayList<>();
    for(Opleiding o : opleidingen) {
      if(o instanceof Cpp) {
        cppNamen.add(o.getNaam());
      }
    }
    return cppNamen;
  }
}
