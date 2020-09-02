package hellocucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

class IsItFriday {
    static String isItFriday(String today) {
        return "Nope";
    }
}

public class StepDefinitions {

    private String today;
    private String actualAnswer;

    @Given("today is Sunday")
    public void today_is_Sunday(){
        this.today = "Sunday";
    }

    @When("I ask whether it's Friday yet")
    public void i_ask_whether_it_s_Friday_yet(){
        actualAnswer = IsItFriday.isItFriday(this.today);
    }

    @Then("I should be told {string}")
    public void i_should_be_told(String expectedAnswer){
        Assert.assertEquals(expectedAnswer, this.actualAnswer);
    }

}
