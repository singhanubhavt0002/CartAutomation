package com.springboot.dataProviders;

import org.testng.annotations.DataProvider;

public class CartDataProvider {

    @DataProvider(name = "flatxAmountOffTestData")
    public Object[][] flatxAmountOffTestData() {
        return new Object[][]{
                // Positive Test Cases (FLAT Amount Off)
                // String testCaseTitle, int userId, int cartValue, int restaurantId, double expectedCartValue, String offerType, int offerValue)
                {"Test Case for User 1 - FLAT Amount Off Offer: 20 off on Cart Value 100", 1, 100, 1, 80, "FLATX", 20},
                {"Test Case for User 2 - FLAT Amount Off Offer: 30 off on Cart Value 200", 2, 200, 1, 170, "FLATX", 30},

                // Negative Test Cases (FLAT Amount Off)
                {"Test Case for User 1 - FLAT Amount Off Offer: 20 off on Cart Value 10 (Cart Value cannot go below 0)", 1, 10, 1, 0, "FLATX", 20},
                {"Test Case for User 2 - FLAT Amount Off Offer: 50 off on Cart Value 30 (Cart Value cannot go below 0)", 2, 30, 1, 0, "FLATX", 50},

                // Corner Test Cases (FLAT Amount Off)
                {"Test Case for User 1 - FLAT Amount Off Offer: 100 off on Cart Value 100 (Cart Value becomes 0)", 1, 100, 1, 0, "FLATX", 100},
                {"Test Case for User 3 - FLAT Amount Off Offer: 200 off on Cart Value 200 (Cart Value becomes 0)", 3, 200, 1, 0, "FLATX", 200},

                // Additional Negative and Edge Test Cases
                {"Verify invalid offer_value (negative value) for FLATX", 1, 100, 1, 100, "FLATX", -20},
                {"Verify missing restaurant ID in the offer API request", 1, 100, 0, 100, "FLATX", 20},
                {"Verify invalid user (non-existent user ID) for applying FLATX offer", 999, 120, 1, 100, "FLATX", 20},
                {"Verify missing offer_value for FLATX offer type", 1, 100, 1, 100, "FLATX", 0},
                {"Verify applying offer when cart_value is 0", 1, 0, 1, 0, "FLATX", 40}
        };
    }

    // Data provider for FLATX% (Percentage Off) Test Cases
    @DataProvider(name = "flatxPercentageOffTestData")
    public Object[][] flatxPercentageOffTestData() {
        return new Object[][]{
                // Positive Test Cases (FLAT Percentage Off)
                // String testCaseTitle, int userId, int cartValue, int restaurantId, double expectedCartValue, String offerType, int offerValue)
                {"Test Case for User 1 - FLAT Percentage Off Offer: 10% off on Cart Value 200", 1, 200, 1, 180, "FLATX%", 10},
                {"Test Case for User 2 - FLAT Percentage Off Offer: 20% off on Cart Value 500", 2, 500, 1, 400, "FLATX%", 20},

                // Negative Test Cases (FLAT Percentage Off)
                {"Test Case for User 1 - FLAT Percentage Off Offer: 10% off on Cart Value 50", 1, 50, 1, 45, "FLATX%", 10},
                {"Test Case for User 2 - FLAT Percentage Off Offer: 50% off on Cart Value 30", 2, 30, 1, 15, "FLATX%", 50},

                // Corner Test Cases (FLAT Percentage Off)
                {"Test Case for User 3 - FLAT Percentage Off Offer: 100% off on Cart Value 100 (Cart Value becomes 0)", 3, 100, 1, 0, "FLATX%", 100},
                {"Test Case for User 2 - FLAT Percentage Off Offer: 90% off on Cart Value 1000", 2, 1000, 1, 100, "FLATX%", 90},

                // Additional Negative and Edge Test Cases
                {"Verify invalid offer_value (negative value) for FLATX offer", 1, 100, 1, 100, "FLATX", -20},
                {"Verify missing restaurant ID in the offer API request", 1, 100, 0, 100, "FLAT", 20},
                {"Verify invalid user (non-existent user ID) for applying FLATX offer", 999, 120, 1, 100, "FLATX", 20},
                {"Verify missing offer_value for FLATX% offer type", 1, 100, 1, 100, "FLATX", 0},
                {"Verify applying offer when cart_value is 0", 1, 0, 1, 0, "FLATX", 40}
        };
    }
}
