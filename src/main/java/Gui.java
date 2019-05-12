import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gui extends JFrame {

    final JPanel jpanel = new JPanel(new GridBagLayout());
    private final JLabel labelDate = new JLabel("Wprowadz dzien (D/M): ");
    private final JLabel labelTime = new JLabel("Wprowadz godzine (hh/mm): ");
    private final JTextField fieldDate = new JTextField(20);
    private final JTextField fieldTime = new JTextField(20);
    private final JButton acceptButton = new JButton("ok");
    Map<String, String> mapa = new HashMap<String, String>();

    Gui() {

        super("Odpisywacz");
        final GridBagConstraints constraints = new GridBagConstraints();

        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        jpanel.add(labelDate, constraints);

        constraints.gridx = 1;
        jpanel.add(fieldDate, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        jpanel.add(labelTime, constraints);

        constraints.gridx = 1;
        jpanel.add(fieldTime, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        acceptButton.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                int day = Integer.parseInt(fieldDate.getText().split("/")[0]);
                int month = Integer.parseInt(fieldDate.getText().split("/")[1]);
                int hour = Integer.parseInt(fieldTime.getText().split("/")[0]);
                int minute = Integer.parseInt(fieldTime.getText().split("/")[1]);
                Main.reply(day,month,hour,minute);
            }
        });
        jpanel.add(acceptButton, constraints);
        add(jpanel);

        pack();
        setVisible(true);

    }
}

