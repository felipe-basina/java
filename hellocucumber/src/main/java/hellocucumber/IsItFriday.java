package hellocucumber;

public class IsItFriday {

    public String check(String today) {
        return "Friday".equalsIgnoreCase(today) ? "TGIF" : "Nope";
    }

}
