package com.group8.controllers.viewcontroller;

import com.group8.App;
import com.group8.controllers.UserController;
import com.group8.helper.UIHelper;
import com.group8.model.Session;
import com.group8.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;



public class ScrumboardViewController implements Initializable {

    @FXML
    private StackPane scrumboardView;
    @FXML
    private VBox listToDo;
    @FXML
    private VBox listInProgress;
    @FXML
    private ListView<String> listReview;
    @FXML
    private ListView<String> listDone;
    @FXML
    private Button btnActivityItemAssign;
    @FXML
    private Label activityName;

    @FXML
    private Button userNewButton;
    @FXML
    private Button userModifyButton;
    @FXML
    private Button userAssignButton;
    @FXML
    private Button userListButton;
    @FXML
    private Button userDeleteButton;
    @FXML
    private TextField activitySearch;
    @FXML
    private TextArea dbFeedback;

    private static UIHelper uiHelper = new UIHelper();
    public ObservableList<String> names = FXCollections.observableArrayList();

    @FXML
    private void handleUserButtons(ActionEvent event) throws IOException {
        // clear all text field
        if (event.getSource() == userNewButton) {
            // Session.setWindowMode("new");
            // uiHelper.loadWindow("UserAddView", userNewButton, null);

        } else if (event.getSource() == userModifyButton) {
            // Modify user details
            //User user = tblUsers.getSelectionModel().getSelectedItem();
            // Session.setSetOpenItem(user);
            /*if (user == null) {
                uiHelper.alertDialogGenerator(userView,"error", "Modify user", "No user exist or no user selected.\nPlease select an user and try again.");
            } else {
                Session.setWindowMode("edit");
                uiHelper.loadWindow("UserAddView", userModifyButton, user);
            }*/

        } else if (event.getSource() == userAssignButton) {
            // List all projects window

        } else if (event.getSource() == userListButton) {
            // List all projects window
            // userController.getUser();

        } else if (event.getSource() == userDeleteButton) {
            // Archive project window
        }
    }

    public void loadUserData() {

        //getting the full list of books from file
        // List<User> userList = userController.getUserList();
        // ObservableList<User> viewUsers = (ObservableList<User>) FXCollections.observableArrayList(userList);

        /*tblClmUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblClmUserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        tblClmUserFullname.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        tblClmUserRole.setCellValueFactory(new PropertyValueFactory<>("userRole"));
        tblClmUserEmailAddress.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));*/

        // tblUsers.setItems(viewUsers);
    }

    public void loadListToDo() {

        names.addAll(
                "Adam", "Alex", "Alfred", "Albert",
                "Brenda", "Connie", "Derek", "Donny",
                "Lynne", "Myrtle", "Rose", "Rudolph",
                "Tony", "Trudy", "Williams", "Zach"
        );
        names.addAll(
                "Adams", "Alexander", "Alfred", "Albert",
                "Brenda", "Connie", "Derek", "Donny",
                "Lynne", "Myrtle", "Rose", "Rudolph",
                "Tony", "Trudy", "Williams", "Zach"
        );
        names.addAll(
                "Adams", "Alexander", "Alfred", "Albert",
                "Brenda", "Connie", "Derek", "Donny",
                "Lynne", "Myrtle", "Rose", "Rudolph",
                "Tony", "Trudy", "Williams", "Zach"
        );

        // listToDo.setItems(names);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // TODO
            // initCol();
            // loadUserData();

            // loadListToDo();

            Node[] nodes = new Node[20];
            int i = 1;
            for (Node node : nodes) {
                try {

                    node = FXMLLoader.load(App.class.getResource("fxml/content/ActivityItem.fxml"));
                    Node finalNode = node;
                    Node finalNode3 = node;

                    //give the items some effect
                    Node finalNode1 = node;
                    node.setOnMouseEntered(event -> {
                        finalNode1.setStyle("-fx-background-color : #d9e2f6");
                    });
                    Node finalNode2 = node;
                    node.setOnMouseExited(event -> {
                        finalNode2.setStyle("-fx-background-color : #fff");
                    });
                    //getactivityName.setText("Activity ID: " + String.valueOf(i));
                    listToDo.getChildren().add(node);
                    i++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnActivityItemAssign(MouseEvent event) {
        System.out.println("Button pressed: " + event.getSource());
    }

    @FXML
    private void userSearchOnKeyReleased(KeyEvent event) {
        /*tblActivities.getItems().clear();
        activityController.activityName = activitiesSearch.getText();*/
    }

    @FXML
    private void btnRefreshOnAction(ActionEvent event) {
        loadUserData();
    }

    @FXML
    public void actionFeedback(String result) {
        //dbFeedback.setText(result);
        //System.out.println(result);
    }
}