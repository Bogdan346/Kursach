package sample;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static javafx.collections.FXCollections.*;


public class Controller implements Initializable {


    @FXML
    public ResourceBundle resources;
    @FXML
    public URL location;
    @FXML
    public ResourceBundle resourcesBundle;


    //ComboBox
    @FXML
    public ComboBox<String> searchSystemBox;


    //ComboBox


    //Button
    @FXML
    public Button backButton;// публічні змінні типу Button, які
    @FXML
    public Button forwardButton;
    @FXML
    public Button exportButton;


    @FXML
    public Label checkLink;
    @FXML
    public Button editButton;
    @FXML
    public Button saveButton;
    @FXML
    public Button importXLToDBButton;
    @FXML
    public Button searchButton;


    @FXML
    private Button addButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Button deleteButton;
    @FXML
    public Button goButton;
//Button

    // TextField
    @FXML
    public TextField searchField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField linkField;

// TextField

    // TableView
    @FXML
    public TableColumn<Data, String> tableViewColumId;
    @FXML
    public TableColumn<Data, String> tableViewColumName;
    @FXML
    public TableColumn<Data, String> tableViewColumLink;
    @FXML
    public TableView<Data> tableView;
    @FXML
    public ObservableList<Data> data;// лист прослушки измененния объектов в клекции
    @FXML
    public DataBaseConnector dc;


    Data dta = new Data();
//TableView

    //Browser
    public String http = " http://";
    @FXML
    public TextField addresBar;
    @FXML
    public WebView webView;
    @FXML
    public WebEngine engine;
    @FXML
    public String adrsLink;
    //Browser


    /////////////////////////////////////////////*************************/////////////////////////////////////////////////
//                                                    methods
// Главный метод инициализации

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//Brawser


        engine = webView.getEngine();//получаем объект WebEngine из  WebView используя метод  getEngine()

//Brawser


        dc = (new DataBaseConnector());

//Tooltip
        addButton.setTooltip(toolTipe("This button add elements in table"));
        deleteButton.setTooltip(toolTipe("This button delete elements from table"));
        refreshButton.setTooltip(toolTipe("Press this button for load data in table"));
        goButton.setTooltip(toolTipe("Go to you address"));
        tableView.setTooltip(toolTipe("Here located youre data"));
        backButton.setTooltip(toolTipe("Back on previously page"));
        forwardButton.setTooltip(toolTipe("Back on next page"));
        addresBar.setTooltip(toolTipe("Input here you address"));
        tableView.setTooltip(toolTipe("Here located youre data"));
        addButton.setDisable(true);
        deleteButton.setDisable(true);
        exportButton.setDisable(true);
        saveButton.setDisable(true);

//Tooltip


//Listiners

        addButton.setOnAction(event -> {
            addAction();
            loadDataFromDatabaseToTableView();
        });
        deleteButton.setOnAction(event -> {
            deleteAction();

        });
        refreshButton.setOnAction(event -> {
            addButton.setDisable(false);
            deleteButton.setDisable(false);
            refreshButton.setDisable(true);
            exportButton.setDisable(false);
            loadDataFromDatabaseToTableView();


        });
        goButton.setOnAction(event -> {
            go();

        });

        backButton.setOnAction(event -> {
            goBack();

        });
        forwardButton.setOnAction(event -> {
            goForward();

        });
        tableView.setOnMouseClicked(event -> {
            clickOnAddres(event);
        });
        exportButton.setOnAction(event -> {
            try {
                exportToExcelFile();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        editButton.setOnAction(event -> {
            getChangingData();
            saveButton.setDisable(false);
        });
        saveButton.setOnAction(event -> {
            deleteAction();
            addAction();
            loadDataFromDatabaseToTableView();
            saveButton.setDisable(true);

        });

        searchField.setOnKeyReleased(event -> {
            searchName();
        });

        //Choose Search System
        searchSystemBox.setItems(FXCollections.<String>observableArrayList("Google", "Duckduckgo", "Yippy", "Disconnect Search"));
        String SSystems[] = new String[]{"google.com", "duckduckgo.com", "yippy.com", "search.disconnect.me"};
        searchSystemBox.setValue("Google");
        String startSearchSystemValue = "google.com";
        engine.load(http + startSearchSystemValue);
        searchSystemBox.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number odl_v, Number newVal) -> {

                    String comboBoxSearchSystemValue = SSystems[newVal.intValue()];
                    engine.load(http + comboBoxSearchSystemValue);
                }
        );
        //Choose Search System



    }


//Listiners


    public void deleteAction() {
        Data d = tableView.getSelectionModel().getSelectedItem();

        if (d == null) {
            warningDialogs("No object selected", "Choose object for deleting ");
        } else {
            try {
                deleteFromDb(d.getId());
                ///deletefromTable
                int selectR = tableView.getSelectionModel().getSelectedIndex();
                tableView.getItems().remove(selectR);
                addresBar.clear();
                ///deletefromTable

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteFromDb(String id) {
        try {

            String sql = "DELETE  FROM mydatabase WHERE idmydatabase = " + id + "";
            sqlExecute(sql);

        } catch (Exception e) {
        }
    }


    public void addAction() {


        if (nameField.getLength() != 0 && linkField.getLength() != 0) {
            DataBaseConnector dataBaseConnector = new DataBaseConnector();
            dataBaseConnector.addElementToDB(nameField.getText(), linkField.getText());
            nameField.clear();
            linkField.clear();
        } else {

            warningDialogs("Empty textfield", "Information in textfield is empty\nPlease, fill textfield");
        }
    }


    // Метод загрузки инфи в таблицу из БД
    public void loadDataFromDatabaseToTableView() {


        try {

            Connection conn = dc.getDbConnection();
            data = observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM mydatabase;");


            while (rs.next()) {
                data.add(new Data(rs.getString("Name"), rs.getString("Link"), rs.getString("idmydatabase")));
            }
//setCellValueFactory(...) определяет, какое поле внутри класса будут использоваться для конкретного столбца в таблице
            // PVF -Считывает геттер  и запис в колонку .позволяет в качестве строки обращатся к названию поля


            tableViewColumName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tableViewColumLink.setCellValueFactory(new PropertyValueFactory<>("link"));

            //tableViewColumId.setCellValueFactory(new PropertyValueFactory<>("id"));

            tableView.setItems(null);
            tableView.setItems(data);// использ набор данних из нашей коллекции
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /// Go Button
    public void go() {
        if (addresBar.getLength() != 0) {
            adrsLink = addresBar.getText().toString();
            engine.load(http + adrsLink);
        } else {
            warningDialogs("Empty Address Bar", "Input address");
        }
    }


    public void sqlExecute(String sql) {
        try {
            Connection connection = dc.getDbConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getAddres() {
        Data links = tableView.getSelectionModel().getSelectedItem();
        String lin = links.getLink();
        addresBar.setText(lin);
    }


    public void warningDialogs(String title, String description) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(description);
        alert.showAndWait();
    }


    public void goBack() {
        final WebHistory history = engine.getHistory();
        ObservableList<WebHistory.Entry> entryList = history.getEntries();
        int currentIndex = history.getCurrentIndex();
        Platform.runLater(() ->
        {
            history.go(entryList.size() > 1 && currentIndex > 0 ? -1 : 0);
        });
    }

    public void goForward() {
        final WebHistory history = engine.getHistory();
        ObservableList<WebHistory.Entry> entryList = history.getEntries();
        int currentIndex = history.getCurrentIndex();

        Platform.runLater(() ->
        {
            history.go(entryList.size() > 1 && currentIndex < entryList.size() - 1 ? 1 : 0);
        });
    }

    public Tooltip toolTipe(String text) {
        Tooltip tooltip = new Tooltip(text);
        return tooltip;
    }

    public void clickOnAddres(MouseEvent click) {
        if (click.getClickCount() == 2) {
            getAddres();
            go();
        } else {
            getAddres();
        }
    }

    public void exportToExcelFile() throws SQLException, ClassNotFoundException, IOException {

        String sql = "SELECT * FROM mydatabase";
        Connection connection = dc.getDbConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        XSSFWorkbook wd = new XSSFWorkbook();
        XSSFSheet sheet = wd.createSheet("User Data");
        XSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("Name:");
        header.createCell(1).setCellValue("Link:");

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.setColumnWidth(0, 256 * 25);
        sheet.setColumnWidth(1, 256 * 25);
        int index = 1;
        while (rs.next()) {
            XSSFRow row = sheet.createRow(index);
            row.createCell(0).setCellValue(rs.getString("Name"));
            row.createCell(1).setCellValue(rs.getString("Link"));
            index++;
        }


        FileOutputStream fileOutputStream = new FileOutputStream("UserData.xlsx");
        wd.write(fileOutputStream);
        fileOutputStream.close();
        warningDialogs("title ", "Exele was created");
        preparedStatement.close();
        rs.close();
    }


    public void getChangingData() {
        Data links = tableView.getSelectionModel().getSelectedItem();
        Data names = tableView.getSelectionModel().getSelectedItem();
        String lin = links.getLink();
        String nam = names.getName();
        nameField.setText(nam);
        linkField.setText(lin);
    }


    public void searchName() {
        if (searchField.getText().equals(" ")) {
            loadDataFromDatabaseToTableView();
        } else {
            data.clear();
            String sql = "SELECT * FROM mydatabase where Name LIKE '%" + searchField.getText() + "%'";
            try {
                Connection connection = dc.getDbConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    data.add(new Data(rs.getString("Name"), rs.getString("Link"), rs.getString("idmydatabase")));
                }
                tableView.setItems(data);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

}















