package com.group8.helper;

import com.group8.App;
import com.group8.controllers.ProjectController;
import com.group8.model.Project;
import com.group8.model.Session;
import com.group8.model.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
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
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        //LibraryAssistantUtil.setStageIcon(stage);

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
        Label openProjectLabel = new Label();
        Label projectStartDateLabel = new Label();
        Label projectEndDateLabel = new Label();

        Label openProjectName = new Label();
        Label projectStartDate = new Label();
        Label projectEndDate = new Label();

        openProjectLabel.getStyleClass().add("project-info-header-label");
        openProjectLabel.setPadding(new Insets(0, 0, 0, 10));
        openProjectLabel.setText("Current project");

        projectStartDateLabel.getStyleClass().add("project-info-label");
        projectStartDateLabel.setPadding(new Insets(0, 0, 0, 10));
        projectStartDateLabel.setText("Start date");

        projectEndDateLabel.getStyleClass().add("project-info-label");
        projectEndDateLabel.setPadding(new Insets(0, 0, 0, 10));
        projectEndDateLabel.setText("End date");

        openProjectName.setPadding(new Insets(0, 15, 0, 0));
        openProjectName.getStyleClass().add("project-info-header");

        projectStartDate.setPadding(new Insets(0, 15, 0, 0));
        projectStartDate.getStyleClass().add("project-info-text");
        projectEndDate.setPadding(new Insets(0, 15, 0, 0));
        projectEndDate.getStyleClass().add("project-info-text");

        FontAwesomeIconView icon1 = new FontAwesomeIconView(FontAwesomeIcon.ANGLE_LEFT, "18");
        icon1.getStyleClass().add("project-breadcrumb-icon");
        FontAwesomeIconView icon2 = new FontAwesomeIconView(FontAwesomeIcon.ANGLE_LEFT, "18");
        icon2.getStyleClass().add("project-breadcrumb-icon");
        FontAwesomeIconView icon3 = new FontAwesomeIconView(FontAwesomeIcon.ANGLE_LEFT, "18");
        icon3.getStyleClass().add("project-breadcrumb-icon");

        ObjectId projectId = Session.getOpenProjectId();
        ProjectController projectController = new ProjectController();

        projectBreadcrumb.getChildren().clear();

        if (projectId == null) {
            openProjectName.setText("No project is open yet");
            openProjectName.setStyle("-fx-text-fill: #eb7f6e");
        } else {
            String projectName = projectController.getProjectDetail(projectId, "projectName");
            LocalDate startDate = projectController.getProjectDate(projectId, "startDate");
            LocalDate endDate = projectController.getProjectDate(projectId, "endDate");
            String startDateText = startDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
            String endDateText = endDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
            projectStartDate.setText(startDateText);
            projectEndDate.setText(endDateText);

            openProjectName.setText(projectName);
            projectBreadcrumb.add(icon2, 1, 1);
            projectBreadcrumb.add(icon3, 1, 2);

            projectBreadcrumb.add(projectStartDateLabel, 2, 1);
            projectBreadcrumb.add(projectStartDate, 0, 1);
            projectBreadcrumb.add(projectEndDate, 0, 2);
            projectBreadcrumb.add(projectEndDateLabel, 2, 2);

        }
        projectBreadcrumb.add(openProjectName, 0, 0);
        projectBreadcrumb.add(icon1, 1, 0);
        projectBreadcrumb.add(openProjectLabel, 2, 0);

    }
}