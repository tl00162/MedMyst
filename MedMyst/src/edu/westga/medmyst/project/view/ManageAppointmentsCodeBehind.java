package edu.westga.medmyst.project.view;

import edu.westga.medmyst.project.model.Appointment;
import edu.westga.medmyst.project.viewmodel.MedMystViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ManageAppointmentsCodeBehind {

    @FXML
    private Button createAppointmentButton;

    @FXML
    private Button editAppointmentButton;

    @FXML
    private ListView<Appointment> appointmentsListView;

    private MedMystViewModel viewmodel;
    
    @FXML
    private void initialize() {
        this.refreshAppointmentList();
    }

    public void setViewModel(MedMystViewModel viewmodel) {
        this.viewmodel = viewmodel;
        this.refreshAppointmentList();
    }

    @FXML
    private void createAppointment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/westga/medmyst/project/view/AppointmentForm.fxml"));
            Pane appointmentFormPane = loader.load();

            Stage appointmentFormStage = new Stage();
            appointmentFormStage.setTitle("Create Appointment");
            appointmentFormStage.setScene(new Scene(appointmentFormPane));

            AppointmentFormCodeBehind appointmentFormController = loader.getController();
            appointmentFormController.setViewModel(this.viewmodel);
            appointmentFormController.setOnFormSubmit(() -> {
                this.refreshAppointmentList();
                appointmentFormStage.close();
            });

            appointmentFormStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void editAppointment() {
        Appointment selectedAppointment = this.appointmentsListView.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/westga/medmyst/project/view/AppointmentForm.fxml"));
                Pane appointmentFormPane = loader.load();

                Stage appointmentFormStage = new Stage();
                appointmentFormStage.setTitle("Edit Appointment");
                appointmentFormStage.setScene(new Scene(appointmentFormPane));

                AppointmentFormCodeBehind appointmentFormController = loader.getController();
                appointmentFormController.setViewModel(this.viewmodel);
                appointmentFormController.setCurrentAppointment(selectedAppointment);
                appointmentFormController.populateForm();
                appointmentFormController.setOnFormSubmit(() -> {
                    this.refreshAppointmentList();
                    appointmentFormStage.close();
                });

                appointmentFormStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void refreshAppointmentList() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList(this.viewmodel.getAllAppointments());
        this.appointmentsListView.setItems(appointmentList);
    }
}
