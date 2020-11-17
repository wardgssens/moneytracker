package person;

public class Everyone extends Person {

    public Everyone() {
        super.name = "Everyone";
    }

    public boolean canPay() {
        return false;
    }
}
