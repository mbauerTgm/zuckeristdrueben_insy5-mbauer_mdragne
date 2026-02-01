const { defineConfig } = require("cypress");

module.exports = defineConfig({
  reporter: 'cypress-mochawesome-reporter', 
  reporterOptions: {
    charts: true, 
    reportPageTitle: 'Testbericht',
    embeddedScreenshots: true,
    inlineAssets: true,
    saveAllAttempts: false,
  },
  e2e: {
    setupNodeEvents(on, config) {
      require('cypress-mochawesome-reporter/plugin')(on);
      require('@cypress/code-coverage/task')(on, config);
      return config;
    },
    
    video: true,
    screenshotOnRunFailure: true,
    trashAssetsBeforeRuns: true,
  },
});