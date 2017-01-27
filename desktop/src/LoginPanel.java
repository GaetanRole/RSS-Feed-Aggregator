import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class LoginPanel extends AppPanel {

    private JButton loginButton = new JButton("Login");
    private JTextField loginTextField = new JTextField();
    private JPasswordField passwordTextField = new JPasswordField();

    public LoginPanel() {
        //this.setBackground(Color.red);

        this.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loginTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String username = loginTextField.getText();
                String password = passwordTextField.getText();

                SessionManager.getInstance().saveToken(username, password);
                HTTPRequest request = HTTPRequest.GET("/feeds", null);
                request.execute(new HTTPRequest.HTTPRequestTask() {
                    @Override
                    public void onPostExecute(HTTPResponse res) {
                        if (res.getError() != null) {
                            CacheManager.saveContent(CacheManager.CREDENTIALS_FILENAME, "");
                            JOptionPane.showMessageDialog(LoginPanel.this.window, "Cannot connect to server.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }


                        LoginPanel.this.window.changePanel(new DashboardPanel());
                    }
                });
            }
        });

        JPanel globalPane = new JPanel();
        GridLayout l = new GridLayout(0, 2);
        globalPane.setLayout(l);
        //globalPane.setBackground(Color.green);

        globalPane.add(new JLabel("Username: "));
        globalPane.add(this.loginTextField);

        globalPane.add(new JLabel("Password: "));
        globalPane.add(this.passwordTextField);

        globalPane.add(this.loginButton);


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
    }
}
