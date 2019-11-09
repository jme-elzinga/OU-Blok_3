package theatergui;

import java.awt.GridLayout;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import theater.Plaats;
import theater.Theater;
import theater.Voorstelling;

/**
 * Deze klasse representeert de zaalbezetting van de geselecteerde voorstelling.
 * <ul>
 * <li>Het zaalPanel vraagt bij de voorstelling één voor één de plaatsen op,</li>
 * <li>Het zaalPanel maakt voor elke plaats een plaatsPanel</li>
 * <li>Het zaalPanel voegt de muisluisteraar aan het plaatsPanel toe.</li>
 * </ul>
 */
public class ZaalPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  public static final int   HGAP             = 4;
  public static final int   VGAP             = 10;
  public static final int   AFMETING         = 350;

  public ZaalPanel(Voorstelling voorstelling, MouseListener muisLuisteraar) {
    setSize(AFMETING, AFMETING);
    setLayout(new GridLayout(Theater.AANTALRIJEN + 1, Theater.AANTALPERRIJ + 1,
        HGAP, VGAP));
    // Eerst een rij met stoelnummers
    this.add(new JLabel(""));
    for (int stoelnr = 1; stoelnr <= Theater.AANTALPERRIJ; stoelnr++) {
      this.add(new JLabel("" + stoelnr, SwingConstants.CENTER));
    }
    // Elke kolom begint ook met een rijnummer
    for (int rijnr = 1; rijnr <= Theater.AANTALRIJEN; rijnr++) {
      this.add(new JLabel(rijnr + ""));
      for (int stoelnr = 1; stoelnr <= Theater.AANTALPERRIJ; stoelnr++) {
        Plaats plaats = voorstelling.getPlaats(rijnr, stoelnr);
        PlaatsPanel plaatsPanel = new PlaatsPanel(plaats, muisLuisteraar);
        this.add(plaatsPanel);
      }
    }
  }

}