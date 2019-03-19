package sample;

import javafx.beans.property.SimpleStringProperty;

public class Data {
    private SimpleStringProperty name;
    private SimpleStringProperty link;
    private SimpleStringProperty id;

    // Конструктор
    public Data(String name, String link, String id) {
        this.name = new SimpleStringProperty(name);
        this.link = new SimpleStringProperty(link);
        this.id = new SimpleStringProperty(id);
    }

    // Конструктор
    public Data() {
    }


    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getLink() {
        return link.get();
    }

    public SimpleStringProperty linkProperty() {
        return link;
    }

    public void setLink(String link) {
        this.link.set(link);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

}