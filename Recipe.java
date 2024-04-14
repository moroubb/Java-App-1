package domain;

public class Recipe {

    private String name;
    private int cookingtime;
    private String incredients;

    public Recipe(String name, int cookingtime, String incredients) {
        this.name = name;
        this.cookingtime = cookingtime;
        this.incredients = incredients;
    }

    public String getName() {
        return name;
    }

    public int getCookingtime() {
        return cookingtime;
    }

    public String getIncredients() {
        return incredients;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCookingtime(int cookingtime) {
        this.cookingtime = cookingtime;
    }

    public void setIncredients(String incredients) {
        this.incredients = incredients;
    }

    @Override
    public String toString() {
        return  name + "  " + '\'' + " "+ cookingtime +" "+ incredients + '\'';
    }
}
