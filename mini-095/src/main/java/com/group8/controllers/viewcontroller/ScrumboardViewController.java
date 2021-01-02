package com.group8.controllers.viewcontroller;

import com.group8.controllers.ActivityController;
import com.group8.controllers.SprintController;
import com.group8.helper.UIHelper;
import com.group8.model.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ScrumboardViewController implements Initializable {

    @FXML
    private StackPane scrumboardView;
    @FXML
    private GridPane projectBreadcrumb;
    @FXML
    private ListView<ListCellItem> listToDo;
    @FXML
    private ListView<ListCellItem> listInProgress;
    @FXML
    private ListView<ListCellItem> listReview;
    @FXML
    private ListView<ListCellItem> listDone;
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
    private Button boardRefreshButton;
    @FXML
    private Button userDeleteButton;
    @FXML
    private TextField activitySearch;
    @FXML
    private TextArea dbFeedback;

    private static SprintController sprintController = new SprintController();
    private static ActivityController activityController = new ActivityController();
    private static UIHelper uiHelper = new UIHelper();
    public ObservableList<String> names = FXCollections.observableArrayList();
    private static ArrayList<Activity> activitiesList = new ArrayList<>();

    @FXML
    private void handleUserButtons(ActionEvent event) throws IOException {
        // clear all text field
        if (event.getSource() == userNewButton) {
            // Session.setWindowMode("new");
            // uiHelper.loadWindow("UserAddView", userNewButton, null);

        } else if (event.getSource() == userModifyButton) {
            // Modify user details

        } else if (event.getSource() == userAssignButton) {
            // List all projects window

        } else if (event.getSource() == boardRefreshButton) {
            loadListToDo();

        } else if (event.getSource() == userDeleteButton) {
            // Archive project window
        }
    }

    @FXML
    private void handleSelection(MouseEvent event) throws IOException {
        ArrayList<ListView> listViews = new ArrayList<>(Arrays.asList(listToDo, listInProgress, listReview, listDone));

        for (ListView list : listViews) {
            if (event.getSource() != list) {
                list.getSelectionModel().clearSelection();
            }
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

        ArrayList<ListCellItem> todoItems = new ArrayList<>();
        ArrayList<ListCellItem> inprogressItems = new ArrayList<>();
        ArrayList<ListCellItem> reviewItems = new ArrayList<>();
        ArrayList<ListCellItem> doneItems = new ArrayList<>();
        String activityName;
        String activityType;
        double activityPriority;

        for (Activity activity : activitiesList) {
            activityName = activity.getName();
            activityPriority = activity.getPriority();
            if (activity instanceof UserStory) {
                activityType = "User Story";
            } else if (activity instanceof Task) {
                activityType = "Task";
            } else {
                activityType = "Bug";
            }
            if (activity.getActivityStatus().equals("TODO")) {
                todoItems.add(new ListCellItem(activityName, activityType, activityPriority));
            } else if (activity.getActivityStatus().equals("INPROGRESS")) {
                inprogressItems.add(new ListCellItem(activityName, activityType, activityPriority));
            } else if (activity.getActivityStatus().equals("REVIEW")) {
                reviewItems.add(new ListCellItem(activityName, activityType, activityPriority));
            } else if (activity.getActivityStatus().equals("DONE")) {
                doneItems.add(new ListCellItem(activityName, activityType, activityPriority));
            }
        }

        inprogressItems.add(new ListCellItem("Test 1", "User Story", 3.0));
        reviewItems.add(new ListCellItem("Test 2", "Task", 3.0));
        doneItems.add(new ListCellItem("Test 3", "Bug", 3.0));

        Callback<ListView<ListCellItem>, ListCell<ListCellItem>> cellFactory = new Callback<ListView<ListCellItem>, ListCell<ListCellItem>>() {
            @Override
            public ListCell<ListCellItem> call(ListView<ListCellItem> param) {
                ListCell<ListCellItem> cell = new ListCell<ListCellItem>() {
                    @Override
                    protected void updateItem(ListCellItem item, boolean empty) {

                        super.updateItem(item, empty);
                        if (item != null && item.getType().equals("User Story")) {
                            setPrefHeight(45.0);
                            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.STACK_EXCHANGE, "12");
                            icon.setFill(Color.rgb(8,97,8));
                            setText(item.getName());
                            setGraphic(icon);
                            setTextFill(Color.rgb(8,97,8));
                        } else if (item != null && item.getType().equals("Task")){
                            setPrefHeight(45);
                            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CODE_FORK, "12");
                            icon.setFill(Color.rgb(0,5,221));
                            setText(item.getName());
                            setGraphic(icon);
                            setTextFill(Color.rgb(0,5,221));
                        } else if (item != null && item.getType().equals("Bug")){
                            setPrefHeight(45);
                            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.BUG, "12");
                            icon.setFill(Color.rgb(200,13,13));
                            setText(item.getName());
                            setGraphic(icon);
                            setTextFill(Color.rgb(200,13,13));
                        } else if (empty || item == null || item.getName().equals("")) {
                            setVisible(false);
                            setPrefHeight(0.0);
                        }
                    }
                };
                return cell;
            }
        };

        listToDo.getItems().clear();
        listToDo.setCellFactory(cellFactory);
        listToDo.getItems().addAll(todoItems);

        listInProgress.getItems().clear();
        listInProgress.setCellFactory(cellFactory);
        listInProgress.getItems().addAll(inprogressItems);

        listReview.getItems().clear();
        listReview.setCellFactory(cellFactory);
        listReview.getItems().addAll(reviewItems);

        listDone.getItems().clear();
        listDone.setCellFactory(cellFactory);
        listDone.getItems().addAll(doneItems);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            activitiesList.clear();
            activitiesList = activityController.getActivitiesList();

            loadListToDo();
            uiHelper.loadProjectBreadcrumbs(projectBreadcrumb);

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

    public static class ListCellItem {
        private final String name;
        private final String type;
        private final double priority;

        public ListCellItem(String name, String type, double priority) {
            this.name = name;
            this.type = type;
            this.priority = priority;
        }

        public String getName() {
            return name;
        }
        public String getType() {
            return type;
        }
        public double getPriority() {
            return priority;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}