package com.springboot.test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.springboot.controller.ApplyOfferResponse;
import com.springboot.controller.OfferRequest;
import com.springboot.dataProviders.CartDataProvider;
import com.springboot.runner.CartRunner;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class CartOfferApplicationTests {

    private static ExtentReports extent;
    private static ExtentTest test;
    CartRunner cartRunner = new CartRunner();

    @BeforeClass
    public void setUp() {
        // Initialize the report
        ExtentSparkReporter spark = new ExtentSparkReporter("target/ExtentReports/extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        System.out.println("ExtentReporter setup successful");
    }

    @Test(dataProvider = "flatxAmountOffTestData", dataProviderClass = CartDataProvider.class)
    public void verifyFlatAmountDiscount(String testCaseTitle, int userId, int cartValue, int restaurantId, double expectedCartValue, String offerType, int offerValue) throws Exception {
        test = extent.createTest(testCaseTitle);
        try {
            String segment = cartRunner.getUserSegment(userId);
            List<String> segments = new ArrayList<>();
            segments.add(segment);
            OfferRequest offerRequest = new OfferRequest(restaurantId, offerType, offerValue, segments);
            boolean result = cartRunner.addOffer(offerRequest);
            Assert.assertTrue(result, "Offer addition failed for " + testCaseTitle);
            ApplyOfferResponse response = cartRunner.applyOffer(cartValue, userId, restaurantId);
            Assert.assertEquals(response.getCart_value(), expectedCartValue, 0.01, "Cart value mismatch for " + testCaseTitle);
            test.pass("Test Passed");
        } catch (AssertionError e) {
            test.fail("Test Failed: " + e.getMessage());
        } catch (Exception e) {
            test.fail("Test Failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(dataProvider = "flatxPercentageOffTestData", dataProviderClass = CartDataProvider.class)
    public void verifyFlatPercentageDiscount(String testCaseTitle, int userId, int cartValue, int restaurantId, double expectedCartValue, String offerType, int offerValue) throws Exception {
        // Create an entry for the test case in the report
        test = extent.createTest(testCaseTitle);
        try {
            String segment = cartRunner.getUserSegment(userId);
            List<String> segments = new ArrayList<>();
            segments.add(segment);
            OfferRequest offerRequest = new OfferRequest(restaurantId, offerType, offerValue, segments);
            boolean result = cartRunner.addOffer(offerRequest);
            Assert.assertTrue(result, "Offer addition failed for " + testCaseTitle);
            ApplyOfferResponse response = cartRunner.applyOffer(cartValue, userId, restaurantId);
            Assert.assertEquals(response.getCart_value(), expectedCartValue, 0.01, "Cart value mismatch for " + testCaseTitle);
            test.pass("Test Passed");
        } catch (AssertionError e) {
            test.fail("Test Failed: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            test.fail("Test Failed: " + e.getMessage());
            throw e;
        }
    }

    @AfterClass
    public void tearDown() {
        extent.flush();
    }
}
