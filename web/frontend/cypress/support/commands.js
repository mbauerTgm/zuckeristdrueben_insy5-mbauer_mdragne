// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add('login', (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })

Cypress.Commands.add('createSample', (data) => {

  cy.get(':nth-child(1) > select').select('Sample')
  cy.get('.btn-load').click()

  // Neuen Eintrag öffnen
  cy.get('.btn-save').click()

  // Formular ausfüllen
  cy.get('#field-s_id').type(data.s_id)
  cy.get('#field-s_stamp').type(data.s_stamp)
  cy.get('#field-name').type(data.name)
  cy.get('#field-weight_net').type(data.weight_net)
  cy.get('#field-weight_bru').type(data.weight_bru)
  cy.get('#field-weight_tar').type(data.weight_tar)
  cy.get('#field-quantity').type(data.quantity)
  cy.get('#field-distance').type(data.distance)
  cy.get('#field-date_crumbled').type(data.date_crumbled)
  cy.get('#field-s_flags').type(data.s_flags)
  cy.get('#field-lane').type(data.lane)
  cy.get('#field-comment').type(data.comment)

  // Speichern
  cy.get('.form-actions > .btn-save').click()
})

Cypress.Commands.add('deleteSample', (sample_identifier) => {
    cy.get(':nth-child(1) > select').select('Sample')
    cy.get('.btn-load').click()
    // search creatted item
    cy.get('.search-input').clear().type(sample_identifier)
    cy.get('.search-input-group > .btn').click()
    cy.get('body').then(($body) => {
    if ($body.find('.btn-delete').length > 0) {
      cy.get('.btn-delete').click()
      cy.get('.modal-actions > .btn-delete').click()
      //cy.get('.info-text').should('contain', 'Zeige 0 von 0 Einträgen')
      cy.log('Sample erfolgreich gelöscht')
    } else {
      cy.log('WARNUNG: Kein Sample zum Löschen gefunden.')
    }
  })
})

Cypress.Commands.add('createBox', (data) => {

    cy.get(':nth-child(1) > select').select('Box')
    cy.get('.btn-load').click()
  
    // new entry
    cy.get('.btn-save').click()

    // Formular ausfüllen
    cy.get('#field-b_id').type(data.b_id)
    cy.get('#field-name').type(data.name)
    cy.get('#field-num_max').clear().type(data.num_max)
    cy.get(':nth-child(4) > select').select(data.typ)
    cy.get('#field-comment').type(data.comment)

    // save
    cy.get('.form-actions > .btn-save').click()
})

Cypress.Commands.add('deleteBox', (searchTerm) => {
    cy.get(':nth-child(1) > select').select('Box')
    cy.get('.btn-load').click()

    // search
    cy.get('.search-input').clear().type(searchTerm)
    cy.get('.search-input-group > .btn').click()

    cy.get('body').then(($body) => {
    if ($body.find('.btn-delete').length > 0) {
      cy.get('.btn-delete').click()
      cy.get('.modal-actions > .btn-delete').click()
      //cy.get('.info-text').should('contain', 'Zeige 0 von 0 Einträgen')
      cy.log('Box erfolgreich gelöscht')
    } else {
      cy.log('WARNUNG: Keine Box zum Löschen gefunden. Überspringe Schritt.')
    }
  })
})
Cypress.Commands.add('login',(userid, password) => {

  cy.get('#username').type(userid)
  cy.get('#password').type(password)
  cy.get('.submit-btn').click()
  cy.wait(2000)
})