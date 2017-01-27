import javax.swing.*;
import java.lang.System;

public class Main {
    private JFrame mainFrame;

    public static void main(String[] args) {
        AppWindow window = AppWindow.getInstance();
        window.changePanel(SessionManager.isLoggedIn() ? new DashboardPanel() : new LoginPanel());
    }
}
