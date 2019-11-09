package studentadmingui;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import java.awt.Rectangle;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import studentadmindom.StudentenAdministratie;
import studentadmindom.StudentAdminException;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class StudentAdminFrame extends JFrame {

//----------------- Eigen attributen ---------------------------------------------
  private StudentenAdministratie studentAdmin = new StudentenAdministratie();
//----------------- Einde eigen attributen ---------------------------------------------

  private static final long serialVersionUID = 1L;
  private JPanel jContentPane = null;
  private JScrollPane jScrollPane = null;
  private JTextArea uitvoerGebied = null;
  private JButton toonAlleKnop = null;
  private JTabbedPane mijnTabbladenPanel = null;
  private JPanel voegStudenttoePanel = null;
  private JPanel voegScholertoePanel = null;
  private JPanel studentPanel = null;
  private JPanel alleStudentenPanel = null;
  private JLabel bestaandenaamLabel = null;
  private JTextField bestaandeNaamVeld = null;
  private JLabel infoLabel = null;
  private JTextField studentInfoVeld = null;
  private JLabel nieuwepuntenLabel = null;
  private JTextField puntenVeld = null;
  private JLabel uitlegLabel = null;
  private JButton moduleKnop = null;
  private JLabel opleidingLabel = null;
  private JLabel nstudentLabel = null;
  private JComboBox<String> opleidingComboBox = null;
  private JLabel studentLabel = null;
  private JTextField studentTextField = null;
  private JButton studentButton = null;
  private JComboBox<String> scholingComboBox = null;
  private JLabel scholerLabel = null;
  private JTextField scholerTextField = null;
  private JButton scholerButton = null;


//----------------- Eigen methoden ---------------------------------------------

  /**
   * Controleert of de te testen string inderdaad een double bevat (of int, omdat die impliciet gecast kan worden naar een double) en geeft dan true terug, in alle andere gevallen false.
   * @param test de te testen string.
   * @return true of false.
   */
  private boolean sanityDoubleTest(String test) {
    try {
      Double.parseDouble(test);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Maakt diverse velden leeg en vult het infoLabel met het gegeven bericht
   */
  private void meldingInfoLabel(String bericht) {
    bestaandeNaamVeld.setText("");
    puntenVeld.setText("");
    studentInfoVeld.setText("");
    studentTextField.setText("");
    scholerTextField.setText("");
    uitvoerGebied.setText("");
    infoLabel.setText(bericht);
  }

  /**
   * Maakt de velden in het studenteninfo tabblad leeg en vult het studentenInfoVeld met de gegeven melding
   */
  private void meldingStudentInfoVeld(String bericht) {
    //
    bestaandeNaamVeld.setText("");
    puntenVeld.setText("");
    studentInfoVeld.setText(bericht);
  }

  /**
   * Vult de ComboBox van Opleidingen met de juiste waarden en selecteert het eertse item.
   */
  private void setOpleidingComboBox() {
    for (String s: studentAdmin.getStudieNaam()) {
      opleidingComboBox.addItem(s);
    }
    opleidingComboBox.setSelectedIndex(0);
  }

  /**
   * Vult de ComboBox van Scholing met de juiste waarden en selecteert het eertse item.
   */
  private void setScholingComboBox() {
    for (String s: studentAdmin.getCppNaam()) {
      scholingComboBox.addItem(s);
    }
    scholingComboBox.setSelectedIndex(0);
  }

  /**
   * De 'Voeg student toe' knop in het 'nieuwe student' tabblad is ingedrukt.
   */
  private void studentButtonAction() {
    try {
      studentAdmin.voegRegulierToe(studentTextField.getText(), opleidingComboBox.getSelectedItem().toString());
    }
    catch (StudentAdminException e) {
      meldingInfoLabel(e.getMessage());
    }
    studentTextField.setText("");
  }

  /**
   * De 'Voeg scholer toe' knop in het 'nieuwe scholer' tabblad is ingedrukt.
   */
  private void scholerButtonAction() {
    try {
      studentAdmin.voegScholerToe(scholerTextField.getText(), scholingComboBox.getSelectedItem().toString());
    }
    catch (StudentAdminException e) {
      meldingInfoLabel(e.getMessage());
    }
    scholerTextField.setText("");
  }

  /**
   * Er is op Enter gedrukt in het bestaandeNaamVeld op het tabblad 'studentinfo'.
   */
  private void bestaandeNaamVeldAction() {
    infoLabel.setText("");
    infoLabel.requestFocus();
    String naam = bestaandeNaamVeld.getText();
    try {
      meldingStudentInfoVeld(studentAdmin.getStudentInfo(naam));
    }
    catch (StudentAdminException e) {
      meldingInfoLabel(e.getMessage());
    }
  }

  /**
   * Er is op Enter gedrukt in het puntenVeld op het tabblad 'studentinfo'.
   */
  private void puntenVeldAction() {
    if(sanityDoubleTest(puntenVeld.getText())) {
      // Gebruikersinvoer is een getal
      try {
        studentAdmin.verhoogRegulier(bestaandeNaamVeld.getText(),Double.parseDouble(puntenVeld.getText()));
        bestaandeNaamVeldAction();
      }
      catch (StudentAdminException e) {
        meldingInfoLabel(e.getMessage());
      }
    } else {
      // Gebruikersinvoer is geen getal
      meldingInfoLabel("Alleen getallen > 0 zijn toegestaan bij Punten behaald!");
    }
  }

  /**
   * Er is op de moduleKnop gedrukt op het tabblad 'studentinfo'.
   */
  private void  moduleKnopAction() {
    try {
      studentAdmin.verhoogScholer(bestaandeNaamVeld.getText());
      bestaandeNaamVeldAction();
    }
    catch (StudentAdminException e) {
      meldingInfoLabel(e.getMessage());
    }
  }

  /**
   * Er is op de 'Toon alle studenten' knop gedrukt op het tabblad 'alle studenten'.
   */
  private void toonAlleKnopAction() {
    try {
      uitvoerGebied.setText(studentAdmin.toonStudenten());
    }
    catch (StudentAdminException e) {
      meldingInfoLabel(e.getMessage());
    }
  }
//----------------- Einde eigen methoden ---------------------------------------------


  /**
   * This is the default constructor
   */
  public StudentAdminFrame() {
    super();
    initialize();
  }

  /**
   * This method initializes this
   * 
   * @return void
   */
  private void initialize() {
    this.setSize(466, 347);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(getJContentPane());
    this.setTitle("StudentAdministratie");
    // Hieronder aanroepen naar eigen methoden (Hiermee worden de comboBoxen gevuld).
    setOpleidingComboBox();
    setScholingComboBox();
  }

  /**
   * This method initializes jContentPane
   * 
   * @return javax.swing.JPanel
   */
  private JPanel getJContentPane() {
    if (jContentPane == null) {
      infoLabel = new JLabel();
      infoLabel.setBounds(new Rectangle(14, 278, 424, 33));
      infoLabel.setText("");
      jContentPane = new JPanel();
      jContentPane.setLayout(null);
      jContentPane.add(getMijnTabbladenPanel(), null);
      // Start toegevoegde code ivm het wisselen van de tabbladen
      ChangeListener changeListener = new ChangeListener() {
        public void stateChanged(ChangeEvent changeEvent) {
          meldingInfoLabel("");
        }
      };
      mijnTabbladenPanel.addChangeListener(changeListener);
      // einde eigen toegevoegde code
      jContentPane.add(infoLabel, null);
    }
    return jContentPane;
  }

  /**
   * This method initializes jScrollPane
   * 
   * @return javax.swing.JScrollPane
   */
  private JScrollPane getJScrollPane() {
    if (jScrollPane == null) {
      jScrollPane = new JScrollPane();
      jScrollPane.setBounds(new Rectangle(6, 65, 382, 121));
      jScrollPane.setViewportView(getUitvoerGebied());
    }
    return jScrollPane;
  }

  /**
   * This method initializes uitvoerGebied
   * 
   * @return javax.swing.JTextArea
   */
  private JTextArea getUitvoerGebied() {
    if (uitvoerGebied == null) {
      uitvoerGebied = new JTextArea();
      uitvoerGebied.setEditable(false);
    }
    return uitvoerGebied;
  }

  /**
   * This method initializes toonAlleKnop
   * 
   * @return javax.swing.JButton
   */
  private JButton getToonAlleKnop() {
    if (toonAlleKnop == null) {
      toonAlleKnop = new JButton();
      toonAlleKnop.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          toonAlleKnopAction();
        }
      });
      toonAlleKnop.setText("Toon alle studenten");
      toonAlleKnop.setBounds(new Rectangle(11, 8, 147, 20));
    }
    return toonAlleKnop;
  }

  /**
   * This method initializes mijnTabbladenPanel	
   * 	
   * @return javax.swing.JTabbedPane	
   */
  private JTabbedPane getMijnTabbladenPanel() {
    if (mijnTabbladenPanel == null) {
      mijnTabbladenPanel = new JTabbedPane();
      mijnTabbladenPanel.setBounds(new Rectangle(13, 19, 423, 224));
      mijnTabbladenPanel.addTab("nieuwe student", null,
          getVoegStudenttoePanel(), null);
      mijnTabbladenPanel.addTab("nieuwe scholer", null,
          getVoegScholertoePanel(), null);
      mijnTabbladenPanel.addTab("studentinfo", null, getStudentPanel(), null);
      mijnTabbladenPanel.addTab("alle studenten", null,
          getAlleStudentenPanel(), null);
    }
    return mijnTabbladenPanel;
  }

  /**
   * This method initializes voegStudenttoePanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getVoegStudenttoePanel() {
    if (voegStudenttoePanel == null) {
      studentLabel = new JLabel();
      studentLabel.setBounds(new Rectangle(15, 55, 91, 24));
      studentLabel.setText("Naam student");
      nstudentLabel = new JLabel();
      nstudentLabel.setBounds(new Rectangle(16, 16, 146, 24));
      nstudentLabel.setText("Selecteer een opleiding");
      voegStudenttoePanel = new JPanel();
      voegStudenttoePanel.setLayout(null);
      voegStudenttoePanel.add(nstudentLabel, null);
      voegStudenttoePanel.add(getOpleidingComboBox(), null);
      voegStudenttoePanel.add(studentLabel, null);
      voegStudenttoePanel.add(getStudentTextField(), null);
      voegStudenttoePanel.add(getStudentButton(), null);
    }
    return voegStudenttoePanel;
  }

  private JPanel getVoegScholertoePanel() {
    if (voegScholertoePanel == null) {
      scholerLabel = new JLabel();
      scholerLabel.setBounds(new Rectangle(16, 54, 116, 25));
      scholerLabel.setText("Naam scholer");
      opleidingLabel = new JLabel();
      opleidingLabel.setBounds(new Rectangle(16, 16, 173, 25));
      opleidingLabel.setText("Selecteer een CPP-Opleiding");
      voegScholertoePanel = new JPanel();
      voegScholertoePanel.setLayout(null);
      voegScholertoePanel.add(opleidingLabel, null);
      voegScholertoePanel.add(getScholingComboBox(), null);
      voegScholertoePanel.add(scholerLabel, null);
      voegScholertoePanel.add(getScholerTextField(), null);
      voegScholertoePanel.add(getScholerButton(), null);
    }
    return voegScholertoePanel;
  }

  /**
   * This method initializes studentPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getStudentPanel() {
    if (studentPanel == null) {
      uitlegLabel = new JLabel();
      uitlegLabel.setBounds(new Rectangle(16, 8, 334, 19));
      uitlegLabel.setText("Geef enter om invoer te bevestigen");
      nieuwepuntenLabel = new JLabel();
      nieuwepuntenLabel.setBounds(new Rectangle(14, 63, 256, 20));
      nieuwepuntenLabel.setText("Punten behaald (alleen reguliere opleiding) ");
      bestaandenaamLabel = new JLabel();
      bestaandenaamLabel.setBounds(new Rectangle(14, 35, 86, 20));
      bestaandenaamLabel.setText("Studentnaam");
      studentPanel = new JPanel();
      studentPanel.setLayout(null);
      studentPanel.add(bestaandenaamLabel, null);
      studentPanel.add(getBestaandeNaamVeld(), null);
      studentPanel.add(getStudentInfoVeld(), null);
      studentPanel.add(nieuwepuntenLabel, null);
      studentPanel.add(getPuntenVeld(), null);
      studentPanel.add(uitlegLabel, null);
      studentPanel.add(getModuleKnop(), null);
    }
    return studentPanel;
  }

  /**
   * This method initializes alleStudentenPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getAlleStudentenPanel() {
    if (alleStudentenPanel == null) {
      alleStudentenPanel = new JPanel();
      alleStudentenPanel.setLayout(null);
      alleStudentenPanel.add(getToonAlleKnop(), null);
      alleStudentenPanel.add(getJScrollPane(), null);
    }
    return alleStudentenPanel;
  }

  /**
   * This method initializes bestaandeNaamVeld	
   * 	
   * @return javax.swing.JTextField	
   */
  private JTextField getBestaandeNaamVeld() {
    if (bestaandeNaamVeld == null) {
      bestaandeNaamVeld = new JTextField();
      bestaandeNaamVeld.setBounds(new Rectangle(114, 36, 151, 20));
      bestaandeNaamVeld.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
              bestaandeNaamVeldAction();
            }
        }

      });
      bestaandeNaamVeld.addFocusListener(new FocusListener() {
        public void focusGained(FocusEvent e) {
          infoLabel.setText("");
        }
        @Override
        public void focusLost(FocusEvent e) {
          // Verplichte implementatie, wordt verder niet gebruikt.
        }
      });
    }
    return bestaandeNaamVeld;
  }

  /**
   * This method initializes studentInfoVeld	
   * 	
   * @return javax.swing.JTextField	
   */
  private JTextField getStudentInfoVeld() {
    if (studentInfoVeld == null) {
      studentInfoVeld = new JTextField();
      studentInfoVeld.setBounds(new Rectangle(10, 152, 392, 27));
      studentInfoVeld.setEditable(false);
    }
    return studentInfoVeld;
  }

  /**
   * This method initializes puntenVeld	
   * 	
   * @return javax.swing.JTextField	
   */
  private JTextField getPuntenVeld() {
    if (puntenVeld == null) {
      puntenVeld = new JTextField();
      puntenVeld.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          puntenVeldAction();
        }
      });
      puntenVeld.setBounds(new Rectangle(284, 63, 47, 20));
    }
    return puntenVeld;
  }

  /**
   * This method initializes moduleKnop	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getModuleKnop() {
    if (moduleKnop == null) {
      moduleKnop = new JButton();
      moduleKnop.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          moduleKnopAction();
        }
      });
      moduleKnop.setBounds(new Rectangle(14, 91, 328, 21));
      moduleKnop.setText("Module behaald (alleen CPP)");
    }
    return moduleKnop;
  }

  /**
   * This method initializes opleidingComboBox	
   * 	
   * @return javax.swing.JComboBox	
   */
  private JComboBox<String> getOpleidingComboBox() {
    if (opleidingComboBox == null) {
      opleidingComboBox = new JComboBox<String>();
      opleidingComboBox.setBounds(new Rectangle(195, 16, 200, 24));
    }
    return opleidingComboBox;
  }

  /**
   * This method initializes studentTextField	
   * 	
   * @return javax.swing.JTextField	
   */
  private JTextField getStudentTextField() {
    if (studentTextField == null) {
      studentTextField = new JTextField();
      studentTextField.setBounds(new Rectangle(195, 55, 197, 24));
      studentTextField.addFocusListener(new FocusListener() {
        public void focusGained(FocusEvent e) {
          infoLabel.setText("");
        }
        @Override
        public void focusLost(FocusEvent e) {
          // Verplichte implementatie, wordt verder niet gebruikt.
        }
      });
    }
    return studentTextField;
  }

  /**
   * This method initializes studentButton	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getStudentButton() {
    if (studentButton == null) {
      studentButton = new JButton();
      studentButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
          studentButtonAction();
        }
      });
      studentButton.setBounds(new Rectangle(15, 106, 153, 24));
      studentButton.setText("Voeg student toe");
    }
    return studentButton;
  }

  /**
   * This method initializes scholingComboBox	
   * 	
   * @return javax.swing.JComboBox	
   */
  private JComboBox<String> getScholingComboBox() {
    if (scholingComboBox == null) {
      scholingComboBox = new JComboBox<String>();
      scholingComboBox.setBounds(new Rectangle(210, 16, 167, 25));
    }
    return scholingComboBox;
  }

  /**
   * This method initializes scholerTextField	
   * 	
   * @return javax.swing.JTextField	
   */
  private JTextField getScholerTextField() {
    if (scholerTextField == null) {
      scholerTextField = new JTextField();
      scholerTextField.setBounds(new Rectangle(210, 54, 166, 25));
      scholerTextField.addFocusListener(new FocusListener() {
        public void focusGained(FocusEvent e) {
          infoLabel.setText("");
        }
        @Override
        public void focusLost(FocusEvent e) {
          // Verplichte implementatie, wordt verder niet gebruikt.
        }
      });
    }
    return scholerTextField;
  }

  /**
   * This method initializes scholerButton	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getScholerButton() {
    if (scholerButton == null) {
      scholerButton = new JButton();
      scholerButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
          scholerButtonAction();
        }
      });
      scholerButton.setBounds(new Rectangle(14, 103, 163, 25));
      scholerButton.setText("Voeg scholer toe");
    }
    return scholerButton;
  }

  public static void main(String[] args) {
    StudentAdminFrame fr = new StudentAdminFrame();
    fr.setVisible(true);
  }

} // @jve:decl-index=0:visual-constraint="10,10"
