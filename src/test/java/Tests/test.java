package Tests;


import Basetest.Basetest;
import Pages.Webpage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

@Slf4j
public class test extends Basetest {

    @Test
    public void testingUIForms () {
        page.navigate ("http://85.192.34.140:8081/");
        page.getByText("Forms").click();

        page.getByText("Practice form").click();
        page.querySelector("#firstName").fill("name");
        page.getByPlaceholder("Last Name").fill("my last name");
        page.fill("[id =userNumber]", "123456789");
        page.fill("[id=dateOfBirthInput]", "24.02.2024");


        page.screenshot();

    }

    @Test
    public void testingUIElements () {
        page.navigate ("http://85.192.34.140:8081/");
        page.getByText("Elements").click();
        page.querySelector("//*[@id=\"item-0\"]/span").click();

        page.getByPlaceholder("Full Name").fill("My name");
        page.getByPlaceholder("name@example.com").fill("mmm@ukr.net");
        page.fill("[id=currentAddress]", "current address");
        page.fill("[id=permanentAddress]", "addres");

        page.click("[id=submit]");

        Assert.assertTrue(page.isVisible("[id=output]"));
        Assert.assertTrue(page.locator("[id=output]").textContent().contains("mmm@ukr.net"));

    }

    @Test
    public void slider () {
        page.navigate ("http://85.192.34.140:8081/");
        page.getByText("Widgets").click();
        page.querySelector("//*[@id=\"item-3\"]/span").click();

        Locator slider = page.locator(".range-slider");
        slider.dragTo(slider, new Locator.DragToOptions()
                .setTargetPosition(slider.boundingBox().x+20, 0));

    }

    @Test
    public void alerts () {
        page.navigate ("https://the-internet.herokuapp.com/javascript_alerts");
        Locator firstJsAlertButton = page.locator("//button[text()='Click for JS Alert']");

        firstJsAlertButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE).setTimeout(60000) // Optional: Set a timeout for waiting
        );


        // Set up an event listener for the first dialog (alert)
        page.onceDialog(alert -> {
            // Get the alert text
            String firstAlertText = alert.message();
            System.out.println("Actual alert Text (Alert)=" + firstAlertText);

            // Assert the alert text here
            assert firstAlertText.equals("I am a JS Alert") : "Alert text does not match the expected value.";
            // Accept the alert (click OK)
            alert.accept();
        });

        // Trigger the  first alert by clicking the button which opens the Alert
        firstJsAlertButton.click();
        System.out.println("01 Alert handled");

        // After handling, there is a text as You successfully clicked an alert
        Locator resultText = page.locator("//p[text()='You successfully clicked an alert']");
        System.out.println("Result text after clicking the alert =" + resultText.textContent());



        page.waitForTimeout(5000); // this will pause your test

    }

    @Test
    public void alerts2 () {
        page.navigate ("https://letcode.in/alert");
        Locator btn = page.locator("//button[text()='Simple Alert']");
        page.waitForTimeout(5000);
        page.onceDialog(alert -> {
            String SimpleAlertText = alert.message();
            System.out.println("Actual alert Text (Alert)=" + SimpleAlertText);

            assert SimpleAlertText.equals("Hey! Welcome to LetCode") ;
            alert.accept();
        });

        btn.click();
        System.out.println("simple Alert handled");

    }

    @Test
    public void alerts3 () {
        page.navigate("http://85.192.34.140:8081/");
        page.getByText("Alerts, Frame").first().click();
        page.locator("//*[@id='item-1']//following::span[text()='Alerts']").click();

        page.waitForTimeout(5000);

        Locator alertbtn = page.locator("//*[@id='alertButton']");

        page.onceDialog(alert -> {
            String SimpleAlertText = alert.message();
            System.out.println("Actual alert Text (Alert)=" + SimpleAlertText);

            assert SimpleAlertText.equals("You clicked a button");
            alert.accept();
        });

        alertbtn.click();
        System.out.println("simple Alert handled");

    }

    @Test
    public void searchtest () {
        page.navigate("http://85.192.34.140:8081/");
        page.getByText("Elements").click();
        page.getByText("Web Tables").click();

        Webpage webpage = new Webpage(page);
        String searchForName = "Kierra";
        webpage.search(searchForName);
        page.waitForTimeout(5000);

        List <String> namesInTable = webpage.getVisibleNames();
        System.out.println(namesInTable.size());


        //Assert.assertEquals(namesInTable.size(), 1);
        assertTrue(namesInTable.stream().anyMatch(x -> x.contains(searchForName)));
    }
}
