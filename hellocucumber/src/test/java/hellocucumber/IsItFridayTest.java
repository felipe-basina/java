package hellocucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class IsItFridayTest {

    private String today;
    private String actualAnswer;
    private IsItFriday isItFriday = new IsItFriday();

    @Given("today is {string}")
    public void today_is(String today){
        this.today = today;
    }

    @When("I ask whether it's Friday yet")
    public void i_ask_whether_it_s_Friday_yet(){
        actualAnswer = this.isItFriday.check(this.today);
    }

    @Then("I should be told {string}")
    public void i_should_be_told(String expectedAnswer){
        Assert.assertEquals(expectedAnswer, this.actualAnswer);
    }

}
