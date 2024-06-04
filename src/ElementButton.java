import javax.swing.*;
import java.awt.event.*;

public class ElementButton extends JPanel {

    private JButton button;

    public ElementButton(JTextArea area, String function)
    {
        button = new JButton(function);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                area.setText(area.getText() + function);
            }
        });
        this.add(button);
    }

}
