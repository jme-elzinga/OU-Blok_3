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

  private List<Student> studenten = new ArrayList<Student>();
  private List<Opleiding> opleidingen = new ArrayList<Opleiding>();
  
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
    voegOpleidingToe("Studie", "Informatica", 200);
    voegOpleidingToe("Studie", "Wiskunde", 160);
    voegOpleidingToe("CPP", "CPP Java", 6);
    voegOpleidingToe("CPP", "CPP Softwarearchitect", 4);
    voegOpleidingToe("CPP", "CPP Systeemontwikkelaar", 3);
  }

  /**
   * voegt, afhankelijk van het type (Studie of CPP), een opleiding met de bijbehorende studiepunten of aantal modules toe aan de arraylist opleidingen.
   */
  private void voegOpleidingToe(String type, String naam, int doel) {
    if("Studie".equals(type)) {
      opleidingen.add(new Studie(naam, doel));
    } else if ("CPP".equals(type)) {
      opleidingen.add(new Cpp(naam, doel));
    }
  }

  /**
   * Voeg een reguliere student toe aan de arraylist met studenten
   * @param naam De achternaam van de  nieuwe student
   * @param studienaam De naam van de studie die de student volgt.
   * @throws StudentAdminException Wanneer geen of een niet bestaande studie wordt opgegeven of waneer een bestaande naam is opgegeven.
   */
  public void voegRegulierToe(String naam, String studienaam) throws StudentAdminException {
    Student student = zoekStudent(naam);
    if (student == null) {
      Opleiding opleiding = zoekOpleiding(studienaam);
      if (opleiding == null) {
        throw new StudentAdminException("De door u gegeven Studie bestaat niet (" + studienaam + ")");
      } else if (!(opleiding instanceof Studie)) {
        throw new StudentAdminException("U hebt geen Studie opgegeven (" + studienaam + ")");
      } else {
        // Studie is juist, probeer een een nieuw studenten object aan te maken en daarna toe te voegen aan de lijst
        studenten.add(new Regulier(naam, opleiding));
      }
    } else {
      throw new StudentAdminException("De ingevoerde naam bestaat al in het systeem (" + student.getClass().getSimpleName() + ": "+ naam + ")");
    }
  }

  /**
   * Voeg een scholer to aan de arraylist met studenten
   * @param naam De achternaam van de nieuwe scholer
   * @param cppnaam De naam van het CPP traject welke de scholer volgt.
   * @throws StudentAdminException Wanneer geen of een niet bestaande CCP wordt opgegeven of waneer een bestaande naam is opgegeven.
   */
  public void voegScholerToe(String naam, String cppnaam) throws StudentAdminException {
    Student student = zoekStudent(naam);
    if (student == null) {
      Opleiding opleiding = zoekOpleiding(cppnaam);
      if (opleiding == null) {
        throw new StudentAdminException("De door u gegeven CPP bestaat niet (" + cppnaam + ")");
      } else if (!(opleiding instanceof Cpp)) {
        throw new StudentAdminException("U hebt geen CPP opgegeven (" + cppnaam + ")");
      } else {
        // De CPP is juist, probeer een nieuw scholer object aan te maken en daarna toe te voegen aan de lijst.
        studenten.add(new Scholer(naam, opleiding));
      }
    } else {
      throw new StudentAdminException("De ingevoerde naam bestaat al in het systeem (" + student.getClass().getSimpleName() + ": "+ naam + ")");
    }
  }

  /**
   * Zoekt een opleiding met de gegeven naam en geeft een obkect naar de desbetreffende opleiding terug of null wanneer deze niet bestaat of wanneer de arraylist met opleidingen leeg is.
   */
  private Opleiding zoekOpleiding(String naam) {
    if (opleidingen.size() > 0 ) {
      for (Opleiding o : opleidingen) {
        if (o.getNaam().contentEquals(naam.trim())) {
          return o;
        }
      }
    }
    return null;
  }

  /**
   * Zoekt een student met de gegeven naam en geeft een Student object terug of null wanneer de student niet gevonden wordt of wanneer de student arraylist leeg is.
   */
  private Student zoekStudent(String naam) {
    if (studenten.size() > 0 ) {
      for (Student s : studenten) {
        if (s.getNaam().contentEquals(naam.trim())) {
          return s;
        }
      }
    }
    return null;
  }

  /**
   * Geeft een string met student informatie.
   * @param naam De naam van de student
   * @return De string met de gevraagde informatie of een exception wanneer de student niet gevonden wordt.
   * @throws StudentAdminException Wanneer de naam niet bestaat.
   */
  public String getStudentInfo(String naam) throws StudentAdminException {
    Student s = zoekStudent(naam);
    if (s == null) {
      throw new StudentAdminException("De door u ingevoerde naam is niet bekend");
    }
    return s.getNaam() + ", " + s.getOpleidingsNaam() + ", " + s.getStudieResultaat() + ", " + bepaalSlagingsResultaat(s);
  }

  /**
   * Controleert of de student geslaagd is en geeft dat als tekst terug
   * @param student Een student object
   * @return de tekst 'geslaagd' of 'niet geslaagd'
   */
  private String bepaalSlagingsResultaat(Student student) {
    String resultaat="";
    if (student != null) {
      Opleiding opleiding=student.getOpleiding();
      if (student instanceof Regulier) {
        //
        if (((Studie)opleiding).isGeslaagd(((Regulier)student).getBehaaldePunten())) {
          resultaat = "geslaagd";
        }
      }
      if (student instanceof Scholer) {
        //
        if (((Cpp)opleiding).isGeslaagd(((Scholer) student).getModules())) {
          resultaat = "geslaagd";
        }
      }
      resultaat = "niet geslaagd";
    }
    return resultaat;
  }

  /**
   * Verhoogt de behaalde punten van een reguliere student met het gegeven aantal punten
   * @param naam De naam van de reguliere student.
   * @param punten Het aantal punten (groter dan 0) waarmee verhoogd moet worden.
   * @throws StudentAdminException Waneer de naam niet bestaat, er geen reguliere student is ingevoerd of wanneer het aantal punten <= 0 is.
   */
  public void verhoogRegulier(String naam, double punten) throws StudentAdminException {
    Student s = zoekStudent(naam);
    if (s == null) {
      throw new StudentAdminException("De door u ingevoerde naam bestaat niet (" + naam + ")");
    } else if (!(s instanceof Regulier)) {
      throw new StudentAdminException("De door u ingevulde naam volgt geen reguliere studie (" + s.getClass().getSimpleName() + ": " + naam + ")");
    } else if (punten<=0) {
      throw new StudentAdminException("Alleen punten > 0 zijn toegestaan!");
    } else {
      ((Regulier)s).verhoogPunten(punten);
    }
  }

  /**
   * Verhoogt het aantal behaalde modules van een scholer met 1.
   * @param naam De naam van de student.
   * @throws StudentAdminException Wanneer de naam niet bestaat of wanneer er geen CCP'r is igevoerd.
   */
  public void verhoogScholer(String naam) throws StudentAdminException {
    Student s = zoekStudent(naam);
    if (s == null) {
      throw new StudentAdminException("De door u ingevoerde naam bestaat niet (" + naam + ")");
    } else if (!(s instanceof Scholer)) {
      throw new StudentAdminException("De door u ingevulde naam volgt geen CPP (" + s.getClass().getSimpleName() + ": " + naam + ")");
    } else {
      ((Scholer)s).verhoogModules();
    }
  }

  /**
   * Geeft een string van alle studenten met hun studie info
   * @return een string met resultaten.
   * @throws StudentAdminException Een eventuele exception vanuit getStudentInfo.
   */
  public String toonStudenten() throws StudentAdminException {
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
