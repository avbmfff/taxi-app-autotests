### Project Structure
- BaseTest.java: A base test class that handles the setup and teardown of Playwright's browser and page instances.
- TaxiTest.java: Contains tests for specific pages and functionalities related to taxi ordering, user profile management, and more.
- TaxiAll.java: Includes tests for the complete user journey, combining all key interactions with the app.
- Playwright Configuration: Configures Playwright to run the tests in a Chromium browser with custom options like slow motion (setSlowMo(250)) for better observability.
