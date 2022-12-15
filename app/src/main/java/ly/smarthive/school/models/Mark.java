package ly.smarthive.school.models;

public class Mark {
    int val_one, val_two;
    String name;

    public Mark(){}
    public Mark(int val_one, int val_two, String name) {
        this.val_one = val_one;
        this.val_two = val_two;
        this.name = name;
    }

    public int getVal_one() {
        return val_one;
    }

    public void setVal_one(int val_one) {
        this.val_one = val_one;
    }

    public int getVal_two() {
        return val_two;
    }

    public void setVal_two(int val_two) {
        this.val_two = val_two;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
