package theatergui;
import javax.swing.*;
import theater.Plaats;
import java.awt.Color;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * De grafische representatie van een plaats in een voorstelling.
 * De klasse implementeert Observer en wordt gekoppeld aan de
 * bijbehorende plaats. Als deze van status verandert, wordt
 * update aangeroepen.
 * Dit is feitelijk alleen van belang voor het plaatsen van een klant;
 * de andere wijzigingen zouden ook via de event handlers kunnen
 * (zie de binnenklasse MuisLuisteraar van klasse VoorstellingsPanel) 
 * 
 */
public class PlaatsPanel extends JPanel implements Observer { 

  private static final long serialVersionUID = 1L;
  private static final Color KLEURVRIJ = Color.GREEN;
  private static final Color KLEURBEZET = Color.RED;
  private static final Color KLEURGESELECTEERD = Color.ORANGE;

  // De gerepresenteerde plaats
  private Plaats plaats = null;

  /**
   * De constructor zet de afmeting van het plaatspanel en bepaalt de kleur
   */
  public PlaatsPanel(Plaats plaats, MouseListener muisLuisteraar) {
    this.plaats = plaats;
    // voeg de muisLuisteraar toe aan dit plaatsPanel
    this.addMouseListener(muisLuisteraar);
    
    // voeg dit plaatsPanel als Observer toe aan de bijbehorende plaats
    plaats.addObserver(this);
    toonStatus();
  }
  
  /**
   * Deze methode wordt aangeroepen als de status van een
   * plaats verandert. 
   */
  public void update(Observable obs, Object arg) {
    toonStatus();
  }
  
  /**
   * Bepaalt de kleur van het panel op grond van de status van de plaats.
   */
  public void toonStatus() {
    Plaats.Status status = plaats.getStatus();
    switch (status) {
      case VRIJ:
        setBackground(KLEURVRIJ);
        break;
      case GERESERVEERD:
        setBackground(KLEURGESELECTEERD);
        break;
      case BEZET:
        setBackground(KLEURBEZET);
        break;
      default:
        break;
    }
  }

}