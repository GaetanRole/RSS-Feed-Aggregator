import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.ParseException;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DashboardPanel extends AppPanel {

    private JButton addFeedButton = new JButton("Add");
    private JButton manageFeedsButton = new JButton("Manage");
    private JButton refreshFeedButton = new JButton("Refresh");
    private JButton refreshTagsButton = new JButton("Refresh");
    private JScrollPane scrollPane;
    private JPanel feedPanel;
    private JPanel mainList;
    private JComboBox<String> tagsComboBox = new JComboBox<String>();
    private ArrayList<FeedItem> allItems = new ArrayList<FeedItem>();
    private ArrayList<Feed> allFeeds = new ArrayList<Feed>();
    private String selectedFilter = null;
    private Boolean showManageFeeds = false;
    private Boolean isGettingFeeds = false, isGettingFeedsList = false;

    public DashboardPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.addFeedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DashboardPanel.this.window.changePanel(new AddFeedPanel());
            }
        });

        this.refreshFeedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showManageFeeds) {
                    updateFeedsList();
                } else {
                    updateFeeds();
                }
            }
        });

        this.refreshTagsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTags();
            }
        });

        this.manageFeedsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showManageFeeds = !showManageFeeds;
                manageFeedsButton.setText(showManageFeeds ? "Feeds" : "Manage");
                if (showManageFeeds) {
                    updateFeedsList();
                } else {
                    updateFeeds();
                }

                renderList();
            }
        });

        this.tagsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String item = (String) tagsComboBox.getSelectedItem();
                selectedFilter = item;
                if (tagsComboBox.getSelectedIndex() == 0) {
                    selectedFilter = null;
                }

                renderList();
            }
        });

        JPanel toolBar = new JPanel(new GridLayout(0, 2));
        JPanel buttonsBar = new JPanel(new FlowLayout());
        buttonsBar.add(Box.createHorizontalGlue());
        buttonsBar.add(new JLabel("Feeds: "));
        buttonsBar.add(this.addFeedButton);
        buttonsBar.add(this.manageFeedsButton);
        buttonsBar.add(this.refreshFeedButton);
        //buttonsBar.setBackground(Color.red);

        JPanel searchBar = new JPanel(new FlowLayout());
        searchBar.add(new JLabel("Tags: "));
        this.tagsComboBox.setEnabled(false);
        searchBar.add(this.tagsComboBox);
        searchBar.add(this.refreshTagsButton);
        //searchBar.setBackground(Color.green);

        GridBagConstraints searchBarGbc = new GridBagConstraints();
        searchBarGbc.gridy = 1;
        searchBarGbc.weightx = 30;
        searchBarGbc.weighty = 30;


        GridBagConstraints buttonBarGbc = new GridBagConstraints();
        buttonBarGbc.gridx = buttonBarGbc.gridy = 0;
        buttonBarGbc.gridwidth = buttonBarGbc.gridheight = 1;
        buttonBarGbc.fill = GridBagConstraints.BOTH;
        buttonBarGbc.anchor = GridBagConstraints.NORTHWEST;
        buttonBarGbc.weightx = buttonBarGbc.weighty = 70;

        toolBar.add(searchBar, searchBarGbc);
        toolBar.add(buttonsBar, buttonBarGbc);
        this.add(toolBar);
        this.setFeedPanel();

        try {
            JsonArray items = Json.parse(CacheManager.loadContent(CacheManager.FEED_CONTENT_CACHE_FILENAME)).asArray();
            this.allItems.clear();
            for (int i = 0; i < items.size(); i++) {
                JsonObject obj = items.get(i).asObject();
                this.allItems.add(new FeedItem(obj));
            }

            this.renderList();
        } catch (ParseException e) {
            //System.out.println(e.getMessage());
        }

        try {
            JsonArray items = Json.parse(CacheManager.loadContent(CacheManager.TAGS_CACHE_FILENAME)).asArray();
            this.renderTags(items);
        } catch (ParseException e) {
            //System.out.println(e.getMessage());
        }


        this.updateFeeds();
        this.updateTags();
    }

    public void removeFeedsListItem(int index) {
        this.allFeeds.remove(index);
        this.renderList();
    }

    private void updateTags() {
        this.tagsComboBox.setEnabled(false);
        this.tagsComboBox.insertItemAt("Loading ...", 0);
        HTTPRequest request = HTTPRequest.GET("/tags", null);
        request.execute(new HTTPRequest.HTTPRequestTask() {
            @Override
            public void onPostExecute(HTTPResponse res) {
                if (res.getError() != null) {
                    tagsComboBox.removeItemAt(0);
                    tagsComboBox.setEnabled(true);
                    return;
                }

                JsonArray array = res.getBody().asArray();
                renderTags(array);

                CacheManager.saveContent(CacheManager.TAGS_CACHE_FILENAME, res.getBody().toString());
                tagsComboBox.setEnabled(true);
            }
        });
    }

    private void renderTags(JsonArray tags) {
        tagsComboBox.removeAllItems();
        tagsComboBox.addItem("All");
        for (int i = 0; i < tags.size(); i++) {
            String tag = tags.get(i).asString();
            tagsComboBox.addItem(tag);
        }
    }

    private void setFeedPanel() {
        JPanel feedPanel = new JPanel();
        feedPanel.setLayout(new BorderLayout());

        JPanel mainList = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        mainList.add(new JPanel(), gbc);

        feedPanel.add(new JScrollPane(mainList));

        this.add(feedPanel);
        this.feedPanel = feedPanel;
        this.mainList = mainList;
    }

    private void updateFeeds() {
        if (this.isGettingFeeds) return;
        this.isGettingFeeds = true;
        HTTPRequest.GET("/feeds/sync", null).execute(new HTTPRequest.HTTPRequestTask() {
            @Override
            public void onPostExecute(HTTPResponse res) {
                if (res.getError() != null) {
                    isGettingFeeds = false;
                    return;
                }

                JsonArray content = null;
                try {
                    content = Json.parse(CacheManager.loadContent(CacheManager.FEED_CONTENT_CACHE_FILENAME)).asArray();
                } catch (ParseException e) {

                }

                JsonArray array = res.getBody().asArray();
                JsonArray newCache = new JsonArray();

                allItems.clear();
                for (int i = 0; i < array.size(); i++) {
                    JsonObject obj = array.get(i).asObject();
                    allItems.add(new FeedItem(obj));
                    newCache.add(obj);
                }

                if (content != null) {
                    for (int i = 0; i < content.size(); i++) {
                        JsonObject obj = content.get(i).asObject();
                        allItems.add(new FeedItem(obj));
                        newCache.add(obj);
                    }
                }

                CacheManager.saveContent(CacheManager.FEED_CONTENT_CACHE_FILENAME, newCache.toString());
                renderList();
                isGettingFeeds = false;
            }
        });
    }

    private void updateFeedsList() {
        if (this.isGettingFeedsList) return;
        this.isGettingFeedsList = true;
        HTTPRequest.GET("/feeds", null).execute(new HTTPRequest.HTTPRequestTask() {
            @Override
            public void onPostExecute(HTTPResponse res) {
                if (res.getError() != null) {
                    isGettingFeedsList = false;
                    return;
                }

                JsonArray array = res.getBody().asArray();
                allFeeds.clear();
                for (int i = 0; i < array.size(); i++) {
                    JsonObject obj = array.get(i).asObject();
                    allFeeds.add(new Feed(obj));
                }

                CacheManager.saveContent(CacheManager.FEEDS_LIST_CACHE_FILENAME, res.getBody().toString());
                renderList();
                isGettingFeedsList = false;
            }
        });
    }

    private void renderList() {
        ArrayList<Object> items = new ArrayList<Object>(this.showManageFeeds ? this.allFeeds : this.allItems);
        if (this.showManageFeeds) {

        } else {
            if (this.selectedFilter != null) {
                items.removeIf(s -> {
                    FeedItem c = (FeedItem)s;
                    return !c.getFluxTag().equals(this.selectedFilter);
                });
            }

            items.sort( (f, s) -> {
                FeedItem v1 = (FeedItem)f, v2 = (FeedItem)s;
                return v1.getTimestamp() > v2.getTimestamp() ? 1 : -1;
            });
        }

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(Box.createVerticalGlue());
        mainList.removeAll();

        for (int i = 0; i < items.size(); i++) {
            JPanel panel = this.showManageFeeds ? new FeedPanel(i, (Feed)items.get(i), this) : new FeedItemPanel((FeedItem)items.get(i));
            GridBagConstraints gbc2 = new GridBagConstraints();
            gbc2.gridwidth = GridBagConstraints.REMAINDER;
            gbc2.weightx = 1;
            gbc2.fill = GridBagConstraints.HORIZONTAL;
            mainList.add(panel, gbc2, 0);
        }


        feedPanel.validate();
        feedPanel.repaint();
    }
}

class FeedItemPanel extends JPanel {
    public FeedItemPanel(FeedItem item) {
        this.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 0, 10), new MatteBorder(0, 0, 1, 0, Color.GRAY)));
        this.add(Box.createHorizontalGlue());
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBackground(Color.white);
        this.add(new JLabel("Title: " + item.getTitle()));
        this.add(new JLabel("Date: " + item.getDate()));
        this.add(new JLabel("Tags: " + item.getFluxTag()));
        this.add(new JLabel("URL: " + item.getLink()));
        JTextArea area = new JTextArea(item.getDescription());
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setAlignmentX(0);
        area.setFont(new Font("HelveticaNeue-Light", Font.PLAIN, 16));
        area.setBorder(new EmptyBorder(10, 0, 10, 0));
        this.add(area);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, super.getPreferredSize().height);
    }
}

class FeedItem {
    private JsonObject obj;

    public FeedItem(JsonObject obj) {
        this.obj = obj;
    }

    public String getTitle() { return this.obj.getString("title", "Unknown title"); }
    public String getDate() {
        Date date = new Date(this.obj.getLong("pub_date", 0));
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String reportDate = df.format(date);
        return reportDate;
    }
    public double getTimestamp() {
        return this.obj.getDouble("pub_date", 0);
    }
    public String getLink() { return this.obj.getString("link", "Unknown link"); }
    public String getDescription() { return this.obj.getString("description", "Unknown content"); }
    public String getFluxTag() { return this.obj.get("flux").asObject().getString("tag", ""); }
}

class FeedPanel extends JPanel {
    private JButton deleteButton = new JButton("Delete feed");

    public FeedPanel(int index, Feed item, DashboardPanel dashboardPanel) {
        this.deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Do you really want to delete this feed ?", "Warning", JOptionPane.WARNING_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    HTTPRequest request = HTTPRequest.DELETE("/feeds/" + Integer.toString(item.getId()), null);
                    request.execute(new HTTPRequest.HTTPRequestTask() {
                        @Override
                        public void onPostExecute(HTTPResponse res) {

                        }
                    });

                    dashboardPanel.removeFeedsListItem(index);
                }
            }
        });

        this.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 0, 10), new MatteBorder(0, 0, 1, 0, Color.GRAY)));
        this.add(Box.createHorizontalGlue());
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBackground(Color.white);

        //this.deleteButton.setBackground(Color.red);
        //this.deleteButton.setForeground(Color.white);
        this.add(new JLabel(item.getName()));
        this.add(new JLabel(item.getTitle()));
        this.add(new JLabel(item.getTags()));
        this.add(new JLabel(item.getLink()));
        this.add(this.deleteButton);
    }
}

class Feed {
    private JsonObject obj;

    public Feed(JsonObject obj) {
        this.obj = obj;
    }

    public int getId() { return this.obj.getInt("id", -1); }
    public String getTitle() { return this.obj.getString("title", "Unknown title"); }
    public String getName() { return this.obj.getString("name", "Unknown name"); }
    public String getTags() { return this.obj.getString("tags", ""); }
    public String getLink() { return this.obj.getString("url", "Unknown link"); }
}
