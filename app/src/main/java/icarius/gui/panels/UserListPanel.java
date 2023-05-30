package icarius.gui.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import icarius.App;
import icarius.entities.Campus;
import icarius.entities.User;
import icarius.gui.frames.MainFrame;
import icarius.http.ConnectionException;
import icarius.http.GetRequest;
import icarius.http.ServerResponse;

public class UserListPanel extends JPanel{
    private MainFrame frame;
    private GridBagConstraints c;

    public UserListPanel(MainFrame frame){
        this.frame = frame;

        c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;

        setLayout(new GridBagLayout());
        updateUserList();
        setBackground(Color.PINK); // TODO - remove after buttons span full page and is in JSCROLLPANE
    }

    public void updateUserList(){
        // Get User List
        List<User> userList = fetchUserList();

        // Reset list panel
        removeAll();

        // Add List of Users as buttons
        c.gridy=0;
        for (User user : userList){
            JButton button = new JButton();
            button.setText(user.getUsername());
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae){
                    ((UserInfoPanel) getParent().getComponent(1)).setUserInfoPage(user);
                }
            });
            add(button, c);
            c.gridy++;
        }
    }

    private List<User> fetchUserList() {
        List<User> userList = new ArrayList<>();
        
        GetRequest request = new GetRequest("/api/users/list", App.userClient);
        try {
            ServerResponse response = request.send();

            // Parse XML response
            Document document = DocumentHelper.parseText(response.getBody());
            Element root = document.getRootElement();
            
            // iterate through child elements of presentation with element name "slide"
            for (Iterator<Element> it = root.elementIterator("slide"); it.hasNext();) {
                Element slide = it.next();
                User user = new User(App.userClient, slide.attributeValue("title"));

                for (Iterator<Element> it2 = slide.elementIterator(); it2.hasNext();) {
                    Element node = it2.next();
                    // picks out the text from 'text' nodes
                    if (node.getName().equals("text")) {
                        String permissionsString = node.getData().toString();
                        switch (permissionsString) {
                            case "":
                                // If no permissions
                                break;
                            case "All":
                                // If admin user
                                user.setAdmin(true);
                                break;
                            default:
                                // Convert string of numbers into list of ints
                                String[] campusIdsAsStrings = permissionsString.split(",");
                                for (String id : campusIdsAsStrings) {
                                    // Get campus
                                    Long campusId = Long.parseLong(id);
                                    Campus campus = App.db.getCampusById(campusId);
                                    user.addPermission(campus);
                                }
                                break;
                        }
                    }
                }

                userList.add(user);
            }
        } catch (ConnectionException ce) {
            frame.setNotification(ce.getMessage(), Color.RED);
        } 
        catch (DocumentException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
