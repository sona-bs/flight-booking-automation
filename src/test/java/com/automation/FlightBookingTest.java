package com.automation.tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.tests.BaseSetup;

public class FlightBookingTest extends BaseSetup {

    // Test Case 1 - Verify Home Page
    @Test
    public void verifyHomePage() {

        String title = driver.getTitle();

        Assert.assertEquals(
                title,
                "BlazeDemo",
                "Home page title is incorrect"
        );
    }


    // Test Case 2 - Select Departure City
    @Test
    public void selectDepartureCity() {

        Select departureCity = new Select(
                driver.findElement(By.name("fromPort"))
        );

        departureCity.selectByVisibleText("Boston");

        String selectedCity =
                departureCity.getFirstSelectedOption().getText();

        Assert.assertEquals(
                selectedCity,
                "Boston",
                "Departure city was not selected"
        );
    }


    // Test Case 3 - Select Destination City
    @Test
    public void selectDestinationCity() {

        Select destinationCity = new Select(
                driver.findElement(By.name("toPort"))
        );

        destinationCity.selectByVisibleText("London");

        String selectedCity =
                destinationCity.getFirstSelectedOption().getText();

        Assert.assertEquals(
                selectedCity,
                "London",
                "Destination city was not selected"
        );
    }


    // Test Case 4 - Search Flights
    @Test
    public void searchFlights() {

        Select departureCity = new Select(
                driver.findElement(By.name("fromPort"))
        );

        departureCity.selectByVisibleText("Boston");

        Select destinationCity = new Select(
                driver.findElement(By.name("toPort"))
        );

        destinationCity.selectByVisibleText("London");

        driver.findElement(
                By.cssSelector("input[value='Find Flights']")
        ).click();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        String heading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.tagName("h3")
                )
        ).getText();

        Assert.assertEquals(
                heading,
                "Flights from Boston to London:",
                "Flight search failed"
        );
    }


    // Test Case 5 - Verify Flight Results
    @Test
    public void verifyFlightResults() {

        Select departureCity = new Select(
                driver.findElement(By.name("fromPort"))
        );

        departureCity.selectByVisibleText("Boston");

        Select destinationCity = new Select(
                driver.findElement(By.name("toPort"))
        );

        destinationCity.selectByVisibleText("London");

        driver.findElement(
                By.cssSelector("input[value='Find Flights']")
        ).click();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("table tbody tr")
                )
        );

        int numberOfFlights = driver.findElements(
                By.cssSelector("table tbody tr")
        ).size();

        Assert.assertTrue(
                numberOfFlights > 0,
                "No flights were displayed"
        );
    }


    // Test Case 6 - Complete Flight Booking
    @Test
    public void completeFlightBooking() {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));


        // Select departure city
        Select departureCity = new Select(
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.name("fromPort")
                        )
                )
        );

        departureCity.selectByVisibleText("Boston");


        // Select destination city
        Select destinationCity = new Select(
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.name("toPort")
                        )
                )
        );

        destinationCity.selectByVisibleText("London");


        // Click Find Flights
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("input[value='Find Flights']")
                )
        ).click();


        // Wait for flight results
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("table tbody tr")
                )
        );


        // Select first available flight
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(
                                "table tbody tr input[value='Choose This Flight']"
                        )
                )
        ).click();


        // Enter passenger name
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("inputName")
                )
        ).sendKeys("John Smith");


        // Enter address
        driver.findElement(
                By.id("address")
        ).sendKeys("123 Main Street");


        // Enter city
        driver.findElement(
                By.id("city")
        ).sendKeys("Bengaluru");


        // Enter state
        driver.findElement(
                By.id("state")
        ).sendKeys("Karnataka");


        // Enter ZIP code
        driver.findElement(
                By.id("zipCode")
        ).sendKeys("560001");


        // Select card type
        Select cardType = new Select(
                driver.findElement(By.id("cardType"))
        );

        cardType.selectByVisibleText("Visa");


        // Enter credit card number
        driver.findElement(
                By.id("creditCardNumber")
        ).sendKeys("4111111111111111");


        // Enter credit card month
        driver.findElement(
                By.id("creditCardMonth")
        ).clear();

        driver.findElement(
                By.id("creditCardMonth")
        ).sendKeys("12");


        // Enter credit card year
        driver.findElement(
                By.id("creditCardYear")
        ).clear();

        driver.findElement(
                By.id("creditCardYear")
        ).sendKeys("2030");


        // Enter name on card
        driver.findElement(
                By.id("nameOnCard")
        ).sendKeys("John Smith");


        // Click Purchase Flight
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("input[value='Purchase Flight']")
                )
        ).click();


        // Verify booking confirmation
        String confirmationMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.tagName("h1")
                )
        ).getText();


        Assert.assertEquals(
                confirmationMessage,
                "Thank you for your purchase today!",
                "Flight booking was not completed successfully"
        );
    }
}