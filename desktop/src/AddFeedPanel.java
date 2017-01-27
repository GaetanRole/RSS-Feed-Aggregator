import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class AddFeedPanel extends AppPanel {
    private JButton validButton = new JButton("Add");
    private JButton cancelButton = new JButton("Cancel");
    private JTextField feedNameTextField = new JTextField();
    private JTextField feedTagTextField = new JTextField();
    private JTextField feedUrlTextField = new JTextField();

    public AddFeedPanel() {
        JPanel globalPane = new JPanel();
        globalPane.setLayout(new GridLayout(0, 2));
        //globalPane.setBackground(Color.green);

        globalPane.add(new JLabel("Name: "));
        globalPane.add(this.feedNameTextField);

        globalPane.add(new JLabel("Tag: "));
        globalPane.add(this.feedTagTextField);

        globalPane.add(new JLabel("URL: "));
        globalPane.add(this.feedUrlTextField);

        globalPane.add(this.cancelButton);
        globalPane.add(this.validButton);

        globalPane.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY), new EmptyBorder(10, 10, 10, 10)));

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 60;      //make this component tall
        gbc.ipadx = 200;
        gbc.weightx = 0.0;
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(globalPane, gbc);

        this.validButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (feedNameTextField.getText().isEmpty() || feedTagTextField.getText().isEmpty() || feedUrlTextField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                validButton.setEnabled(false);
                Map<String, String> m = new HashMap<String, String>();
                m.put("name", feedNameTextField.getText());
                m.put("url", feedUrlTextField.getText());
                m.put("tag", feedTagTextField.getText());
                HTTPRequest request = HTTPRequest.POST("/feeds", m);
                request.execute(new HTTPRequest.HTTPRequestTask() {
                    @Override
                    public void onPostExecute(HTTPResponse res) {
                        validButton.setEnabled(true);
                        cancelButton.setEnabled(true);

                        if (res.getError() != null) {
                            JOptionPane.showMessageDialog(null, "Cannot add your feed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        JOptionPane.showMessageDialog(null, "You feed has been added !", "Created", JOptionPane.INFORMATION_MESSAGE);
                        AddFeedPanel.this.window.changePanel(new DashboardPanel());
                    }
                });
            }
        });

        this.cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddFeedPanel.this.window.changePanel(new DashboardPanel());
            }
        });



        //this.add(this.feedNameTextField);
        //this.add(this.feedTagTextField);
        //this.add(this.feedUrlTextField);
        //this.add(this.validButton);
        //this.add(this.cancelButton);
    }
}
