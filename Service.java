package service;

import domain.Recipe;
import javafx.collections.ObservableList;
import repository.Repository;

import java.util.List;

public class Service {
    private Repository repo;

    public Service(Repository repo) {
        this.repo = repo;
    }

    public void add(Recipe r){repo.add(r);}

    public List<Recipe> getAll(){
        return repo.getDocuments();
    }

    public List<Recipe> showRecipes(){
        return repo.showRecipes();
    }


    public void populeaza(){
        Recipe r1=new Recipe("paste",20,"paste, carne");
        Recipe r2=new Recipe("pizza",90,"oua, carne");
        Recipe r3=new Recipe("piure",10,"cartofi, lapte");
        Recipe r4=new Recipe("ciorba",110,"apa, legume");
        Recipe r5=new Recipe("shaorma",2,"cartofi, iubire");


        repo.add(r1);
        repo.add(r2);
        repo.add(r3);
        repo.add(r4);
        repo.add(r5);
    }

    public void remove(Recipe r){repo.remove(r);}

    public ObservableList<Recipe> search(String searchThing){
        return repo.search(searchThing);
    }
}
