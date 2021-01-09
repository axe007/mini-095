package com.group8.helper;

import com.group8.App;
import com.group8.controllers.ProjectController;
import com.group8.model.Project;
import com.group8.model.Session;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;
import java.util.regex.Pattern;

public class UIHelper {
    public void loadWindow(String viewName, Button sourceButton, String stageTitle) throws IOException {
        Stage stage;
        Parent root;
        root = FXMLLoader.load(App.class.getResource("fxml/content/" + viewName + ".fxml"));
        stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(stageTitle);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.initOwner(sourceButton.getScene().getWindow());
        stage.show();
    }

    public void loadWindow(String viewName, Button sourceButton, Project project) throws IOException {
        Stage stage;
        Parent root;
        root = FXMLLoader.load(App.class.getResource("fxml/content/" + viewName + ".fxml"));
        stage = new Stage();
        stage.setScene(new Scene(root));

        if (Session.getWindowMode().equals("new")) {
            stage.setTitle("Add new project");
        } else if (Session.getWindowMode().equals("edit")) {
            stage.setTitle("Edit project details");
        }

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.initOwner(sourceButton.getScene().getWindow());
        stage.show();
    }

    public Optional<ButtonType> alertDialogGenerator(StackPane root, String alertType, String title, String content) {
        Alert alert = null;
        DialogPane dialogPane = null;

        Optional<ButtonType> result = null;

        if (alertType.equals("error")) {
            alert = new Alert(Alert.AlertType.ERROR);
            dialogPane = alert.getDialogPane();
            dialogPane.getStyleClass().add("dialog-pane-error");
        } else if (alertType.equals("success")) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            dialogPane = alert.getDialogPane();
            dialogPane.getStyleClass().add("dialog-pane-success");
        } else if (alertType.equals("confirm")) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            dialogPane = alert.getDialogPane();
            ((Button) dialogPane.lookupButton(ButtonType.OK)).setText("Yes");
            ((Button) dialogPane.lookupButton(ButtonType.CANCEL)).setText("No");
            dialogPane.getStyleClass().add("dialog-pane-success");

        }

        centerButtons(dialogPane);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.initOwner(root.getScene().getWindow());
        styleAlert(alert);
        result = alert.showAndWait();

        return result;
    }

    private void styleAlert(Alert alert) {
        // Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        // LibraryAssistantUtil.setStageIcon(stage);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(App.class.getResource("css/default.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert");
    }

    public void centerButtons(DialogPane dialogPane) {
        Region spacer = new Region();
        ButtonBar.setButtonData(spacer, ButtonBar.ButtonData.BIG_GAP);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        dialogPane.applyCss();
        HBox hboxDialogPane = (HBox) dialogPane.lookup(".container");
        hboxDialogPane.getChildren().add(spacer);
    }

    public void loadProjectBreadcrumbs(GridPane projectBreadcrumb) {
        ObjectId projectId = Session.getOpenProjectId();
        Label openProjectLabel = new Label();
        Label projectStartDateLabel = new Label();
        Label projectEndDateLabel = new Label();
        Label openProjectName = new Label();
        Label projectStartDate = new Label();
        Label projectEndDate = new Label();

        openProjectLabel.getStyleClass().add("project-info-header-label");
        projectStartDateLabel.getStyleClass().add("project-info-label");
        projectEndDateLabel.getStyleClass().add("project-info-label");
        openProjectName.getStyleClass().add("project-info-header");
        projectStartDate.getStyleClass().add("project-info-text");
        projectEndDate.getStyleClass().add("project-info-text");
        openProjectLabel.setText("Current project");
        projectStartDateLabel.setText("Start date");
        projectEndDateLabel.setText("End date");

        FontAwesomeIconView icon1 = new FontAwesomeIconView(FontAwesomeIcon.ANGLE_LEFT, "18");
        icon1.getStyleClass().add("project-breadcrumb-icon");
        FontAwesomeIconView icon2 = new FontAwesomeIconView(FontAwesomeIcon.ANGLE_LEFT, "18");
        icon2.getStyleClass().add("project-breadcrumb-icon");
        FontAwesomeIconView icon3 = new FontAwesomeIconView(FontAwesomeIcon.ANGLE_LEFT, "18");
        icon3.getStyleClass().add("project-breadcrumb-icon");

        projectBreadcrumb.getChildren().clear();

        if (projectId == null) {
            openProjectName.setText("No project is open yet");
            openProjectName.setStyle("-fx-text-fill: #eb7f6e");
        } else {
            String projectName = Session.getOpenProjectName();
            LocalDate startDate = Session.getProjectStartDate();
            LocalDate endDate = Session.getProjectEndDate();
            Session.setProjectStartDate(startDate);
            Session.setProjectEndDate(endDate);
            String startDateText = startDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
            String endDateText = endDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
            projectStartDate.setText(startDateText);
            projectEndDate.setText(endDateText);

            openProjectName.setText(projectName);
            projectBreadcrumb.add(icon2, 1, 1);
            projectBreadcrumb.add(icon3, 1, 2);

            projectBreadcrumb.add(projectStartDate, 0, 1);
            projectBreadcrumb.add(projectEndDate, 0, 2);
            projectBreadcrumb.add(projectStartDateLabel, 2, 1);
            projectBreadcrumb.add(projectEndDateLabel, 2, 2);
        }
        projectBreadcrumb.add(openProjectName, 0, 0);
        projectBreadcrumb.add(icon1, 1, 0);
        projectBreadcrumb.add(openProjectLabel, 2, 0);

    }

    public boolean uiValidator(String fieldName) {
        boolean result = false;

        return result;
    }

    public static boolean validateEmailAddress(String emailID) {
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(emailID).matches();
    }
}