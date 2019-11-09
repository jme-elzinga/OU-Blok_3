package theatergui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import theater.*;

 /**
   * Deze klasse representeert een grafische inerface voor een bepaalde
   * voorstelling. De klasse regisseert ook de opbouw van die grafische interface.
   * Dat gaat als volgt:
   * <ul>
   * <li>Eerst wordt een nieuwe MouseListener gemaakt, die
   * <ul>
   * <li> ervoor zorgt dat een vrije plaats bij de eerste muisklik gereserveerd wordt
   * en bij de volgende klik weer vrij (enzovoort)
   * </li>
   * <li>de status informatie van de plaats toont bij het binnengaan
   * van de muis
   * </li>
   * </ul> </li>
   * <li>
   * Er wordt een nieuw ZaalPanel gemaakt; de voorstelling en de luisteraar gaan
   * mee als parameters. Het zaalPanel zorgt voor creatie van
   * de plaatsPanels en verbindt er de luisteraar mee.
   * </li></ul>
   */
public class VoorstellingsPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private Theater theater = null; 
  private ZaalPanel zaalPanel = null;
  private JLabel bezetLabel = null;
  private MuisLuisteraar muisLuisteraar = new MuisLuisteraar();

  /**
   * Constructor voor VoorstellingsPanel.
   * @param theater het theater
   */
  public VoorstellingsPanel(Theater theater) {
    this.theater = theater;
    this.setLayout(null);
    this.setSize(ZaalPanel.AFMETING, ZaalPanel.AFMETING+50);

    // maak het zaalPanel voor de huidige voorstelling
    Voorstelling voorstelling = theater.getHuidigeVoorstelling();
    zaalPanel = new ZaalPanel(voorstelling, muisLuisteraar);
    zaalPanel.setLocation(0, 0);
    this.add(zaalPanel);

    bezetLabel = new JLabel();
    bezetLabel.setSize(400, 20);
    bezetLabel.setLocation(0, ZaalPanel.AFMETING + 20);
    this.add(bezetLabel);
  }
  
  /**
   * Muisluisteraar voor de plaatsPanels.
   */
  public class MuisLuisteraar extends MouseAdapter {
    
    /**
     * Deze event handler voor een muisklik op een vrije of gereserveerde
     * plaats en (de-)reserveert deze plaats.
     */
    public void mousePressed(MouseEvent ee) {
      int[] rijstoel = bepaalPlaats(ee.getComponent());
      theater.veranderReservering(rijstoel[0], rijstoel[1]);
    }
    
    /**
     * Deze event handler toont klantinformatie wanneer de muis  
     * een plaats aanwijst.
     */
    public void mouseEntered(MouseEvent ee) {
      PlaatsPanel plaatsPanel = (PlaatsPanel)ee.getComponent();
      int[] rijstoel = bepaalPlaats(plaatsPanel);
      String info = theater.geefPlaatsInfo(rijstoel[0], rijstoel[1]);
      bezetLabel.setText(info);
    }

    /**
     * Als de muis een plaatspanel verlaat, wordt het bezetLabel weer leeg
     */
    public void mouseExited(MouseEvent ee) {
      bezetLabel.setText("");
    }
  }

  // private methode
  
  /**
   * Deze methode rekent rij- en stoelnummer van een aangeklikt plaatsPanel uit
   */
  private int[] bepaalPlaats(Component c){
    int[] rijstoel = new int[2];
    rijstoel[0] = c.getLocation().y / (c.getHeight() + ZaalPanel.VGAP);
    rijstoel[1] = c.getLocation().x / (c.getWidth() + ZaalPanel.HGAP);
    return rijstoel;
  }
  
}
