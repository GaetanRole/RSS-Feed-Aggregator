import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;

public class AppWindow extends JFrame {
    public static AppWindow getInstance() {
        if (null == instance) {
            instance = new AppWindow();
        }
        return instance;
    }

    private AppWindow() {
        this.setTitle("RSS Feed Aggregator");
        this.setSize(800, 600);
        this.setMinimumSize(new Dimension(800, 600));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        MenuItem item = new MenuItem("Test");
        menu.add(item);
        this.setMenuBar(menuBar);
    }

    public void changePanel(JPanel panel) {
        this.getContentPane().removeAll();
        this.getContentPane().add(panel, BorderLayout.CENTER);
        this.getContentPane().doLayout();
        this.update(this.getGraphics());
        this.getContentPane().revalidate();
    }

    /** L'instance statique */
    private static AppWindow instance;
}