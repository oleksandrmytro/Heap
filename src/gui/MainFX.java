package gui;

import AgendaKraj.Agenda;
import abstrTable.Obec;
import enumTypy.ePorovnani;
import enumTypy.eTypProhl;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nacteniAulozeni.UlozeniAnacteni;

public class MainFX extends Application {

    private Agenda agenda;
    private ListView<Obec> obciListView;
    ObservableList<eTypProhl> list = FXCollections.observableArrayList(eTypProhl.values());
    ObservableList<ePorovnani> porovnani = FXCollections.observableArrayList(ePorovnani.values());
    ChoiceBox<ePorovnani> typPorovnaniCB = new ChoiceBox<>(porovnani);
    ChoiceBox<eTypProhl> typItrCB = new ChoiceBox<>(list);
    public static final String OBEC_FILE_NAME = "obce.txt";

    @Override
    public void start(Stage stage) throws Exception {
        agenda = new Agenda();
        stage.setTitle("Obec Data Entry");
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        Scene scene = new Scene(root, 1150, 400);
        createList();
        root.setCenter(obciListView);
        root.setRight(createButtonsRight());
        HBox bottomButtonBox = createButtonsBottom();
        root.setBottom(bottomButtonBox);
        stage.setScene(scene);
        stage.show();
    }

    private void createList() {
        ObservableList<Obec> listData = FXCollections.observableArrayList();
        obciListView = new ListView<>(listData);
        obciListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        obciListView.setPrefSize(600, 600);
    }

    private VBox createButtonsRight() {
        VBox buttonsRight = new VBox(20);
        buttonsRight.setPadding(new Insets(10, 0, 10, 10));
        buttonsRight.setAlignment(Pos.CENTER);

        Button addButton = new Button("Přidat obec");
        addButton.setPrefSize(130, 30);
        addButton.setAlignment(Pos.CENTER);
        addButton.setOnAction(e -> addObec());

        Button deleteMaxBtn = new Button("Odeber Max");
        deleteMaxBtn.setPrefSize(130, 30);
        deleteMaxBtn.setAlignment(Pos.CENTER);
        deleteMaxBtn.setOnAction(e -> {
            agenda.odeberMax();
            obnov();
        });

        Button selectMaxBtn = new Button("Zpristupni Max");
        selectMaxBtn.setPrefSize(130, 30);
        selectMaxBtn.setAlignment(Pos.CENTER);
        selectMaxBtn.setOnAction(e -> {
            obciListView.getSelectionModel().select(agenda.zpristupniMax());
        });

        Button zrusButton = new Button("Zrusit");
        zrusButton.setPrefSize(130, 30);
        zrusButton.setAlignment(Pos.CENTER);
        zrusButton.setOnAction(e -> {
            obciListView.getSelectionModel().clearSelection();
            agenda.zrus();
            obnov();
        });

        Button prebudujBtn = new Button("Prebuduj");
        prebudujBtn.setPrefSize(130, 30);
        typPorovnaniCB.setValue(ePorovnani.POCET_OBYVATELU);
        typPorovnaniCB.setPrefSize(130, 30);
        prebudujBtn.setOnAction(e -> {
            ePorovnani typPorovnani = typPorovnaniCB.getValue();
            switch (typPorovnani) {
                case NAZEV -> Obec.setAktualniPorovnani(ePorovnani.NAZEV);
                case POCET_OBYVATELU -> Obec.setAktualniPorovnani(ePorovnani.POCET_OBYVATELU);
            }
            agenda.reorganizace();
            obnov();
        });

        buttonsRight.getChildren().addAll(addButton, deleteMaxBtn, selectMaxBtn, zrusButton, typPorovnaniCB, prebudujBtn);
        return buttonsRight;
    }

    private HBox createButtonsBottom() {
        Label typItrLbl = new Label("Typ iteratora");
        typItrCB.setValue(eTypProhl.SIRKA);
        typItrCB.setPrefSize(100, 30);
        typItrCB.setOnAction(e -> obnov());

        HBox buttonsBottom = new HBox(20);
        buttonsBottom.setPadding(new Insets(10, 0, 0, 0));
        Button saveButton = new Button("Ulozit");
        saveButton.setPrefSize(100, 30);
        saveButton.setAlignment(Pos.CENTER);
        saveButton.setOnAction(e -> {
            try {
                UlozeniAnacteni.uloz(OBEC_FILE_NAME, agenda, typItrCB.getValue());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Button loadButton = new Button("Nacist");
        loadButton.setPrefSize(100, 30);
        loadButton.setAlignment(Pos.CENTER);
        loadButton.setOnAction(e -> {
            try {
                UlozeniAnacteni.nacti(OBEC_FILE_NAME, agenda, typItrCB.getValue());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            obnov();
        });

        Button generateButton = new Button("Generovat");
        generateButton.setPrefSize(100, 30);
        generateButton.setAlignment(Pos.CENTER);
        generateButton.setOnAction(e -> {
            Obec obec = agenda.generuj();
            agenda.vloz(obec);
            obnov();
        });
        buttonsBottom.getChildren().addAll(typItrLbl, typItrCB, saveButton, loadButton, generateButton);
        return buttonsBottom;
    }

    private void addObec() {
        Dialog<Obec> dialog = new Dialog<>();
        dialog.setTitle("Add New Obec");
        dialog.setHeaderText("Enter Obec details");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField cisloKrajeField = new TextField();
        TextField pscField = new TextField();
        TextField nazevObceField = new TextField();
        TextField pocetMuzuField = new TextField();
        TextField pocetZenField = new TextField();

        grid.add(new Label("Číslo kraje:"), 0, 0);
        grid.add(cisloKrajeField, 1, 0);
        grid.add(new Label("PSČ:"), 0, 1);
        grid.add(pscField, 1, 1);
        grid.add(new Label("Název obce:"), 0, 2);
        grid.add(nazevObceField, 1, 2);
        grid.add(new Label("Počet mužů:"), 0, 3);
        grid.add(pocetMuzuField, 1, 3);
        grid.add(new Label("Počet žen:"), 0, 4);
        grid.add(pocetZenField, 1, 4);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(cisloKrajeField::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    int cisloKraje = Integer.parseInt(cisloKrajeField.getText());
                    String pscText = pscField.getText();
                    if (!pscText.matches("\\d{5}")) {
                        throw new IllegalArgumentException("PSČ musí být pěticiferné číslo.");
                    }
                    int psc = Integer.parseInt(pscText);
                    String nazevObce = nazevObceField.getText();
                    int pocetMuzu = Integer.parseInt(pocetMuzuField.getText());
                    int pocetZen = Integer.parseInt(pocetZenField.getText());

                    return new Obec(cisloKraje, psc, nazevObce, pocetMuzu, pocetZen);
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Chyba formátu");
                    alert.setContentText("Ujistěte se, že číselná pole 'Číslo kraje', 'Počet mužů' a 'Počet žen' jsou správně vyplněna.");
                    alert.showAndWait();
                } catch (IllegalArgumentException e) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Chyba formátu");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }

            }
            return null;
        });

        Optional<Obec> result = dialog.showAndWait();

        result.ifPresent(obec -> {
            agenda.vloz(obec);
            obnov();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void obnov() {
        eTypProhl typProhl = typItrCB.getValue();
        Iterator<Obec> itr = agenda.vytvorIterator(eTypProhl.HLOUBKA);
        obciListView.getItems().clear();
        if (typProhl == eTypProhl.SIRKA) {
            itr = agenda.vytvorIterator(eTypProhl.SIRKA);
        }
        switch (typPorovnaniCB.getValue()) {
            case NAZEV -> Obec.setAktualniPorovnani(ePorovnani.NAZEV);
            case POCET_OBYVATELU -> Obec.setAktualniPorovnani(ePorovnani.POCET_OBYVATELU);
        }
        agenda.reorganizace();
        while (itr.hasNext()) {
            obciListView.getItems().add(itr.next());
        }
    }
}
