package gui;

import domain.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import service.Service;

import java.util.List;

public class Controller {
    private Service service;
    @FXML
    private ListView<Recipe> recListView;

    @FXML
    private Button addBtn;

    @FXML
    private Button removeBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchTF;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField cookTF;

    @FXML
    private TextField incredientsTF;

    public Controller(Service service) {
        this.service = service;
    }
    void populateList()
    {

        List<Recipe> recipeList= service.getAll();
        ObservableList<Recipe> reList = FXCollections.observableArrayList(recipeList);
        recListView.setItems(reList);
    }

    public void initialize()
    {
        populateList();

    }

    @FXML
    public void addBtnClicked(){

        System.out.println("ceva test");
        if(nameTF!=null){
            String name= nameTF.getText();
            int cookingtime=Integer.parseInt(cookTF.getText());
            String incredients= incredientsTF.getText();

            Recipe d= new Recipe(name,cookingtime,incredients);
            service.add(d);

            List<Recipe> recipeList= service.getAll();
            ObservableList<Recipe> recList = FXCollections.observableArrayList(recipeList);
            recListView.setItems(recList);

            nameTF.setText("");
            cookTF.setText("");
            incredientsTF.setText("");
        }
        else {recListView.getItems();}
    }
    @FXML
    public void removeBtnClicked(){
        Recipe selecedR= recListView.getSelectionModel().getSelectedItem();
        if(selecedR!=null)
        { service.remove(selecedR);
            populateList();}
        else{
            System.err.println("ceva");
        }

    }

    @FXML
    public void searchBtnClicked(){
        String searchText=searchTF.getText().trim();
        if(searchText!=null){
            ObservableList<Recipe> searchRes = service.search(searchText);
            if(searchRes!=null)
                recListView.setItems(searchRes);
            else {
                System.err.println("nimic gasit");
            }
        }
    }

    @FXML
    public void sortBtnClicked(){
        List<Recipe> recipeList= service.showRecipes();
        ObservableList<Recipe> reList = FXCollections.observableArrayList(recipeList);
        recListView.setItems(reList);
    }




}

