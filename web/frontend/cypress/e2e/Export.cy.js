import { erstelleDatum } from '../support/utils';

describe('CSV Export Test:', () => {
  const uniqueComment = 'ExportTest_' + Date.now()
  const sId = '555555555555'

  // ARRANGE: Daten erstellen
  before(() => {
    cy.visit('http://localhost:8082/')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')

    // Sample erstellen
    cy.createSample({
        s_id: sId,
        s_stamp: erstelleDatum(0),
        name: 'ExportBase',
        weight_net: 10, weight_bru: 11, weight_tar: 1, quantity: 1, distance: 10,
        date_crumbled: erstelleDatum(0), s_flags: '-', lane: 1, 
        comment: uniqueComment
    })

    // Analysis erstellen
    cy.get(':nth-child(1) > select').select('Analysis')
    cy.get('.btn-load').click()
    cy.get('.btn-save').click().click()
    cy.wait(500)
    cy.get('#field-s_id').type(sId)
    cy.get('#field-pol').type(10.5)
    cy.get('#field-date_in').type(erstelleDatum(0))
    cy.get('.form-actions > .btn-save').click()
    
    cy.get('[data-cy="log-out-btn"]').click()
  })

  // CLEANUP
  after(() => {
    cy.visit('http://localhost:8082/auth')
    cy.get('#username', { timeout: 10000 }).should('be.visible')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')
    
    // Analysis löschen
    cy.get(':nth-child(1) > select').select('Analysis')
    cy.get('.btn-load').click()
    
    cy.get('.search-input').clear().type(uniqueComment)
    cy.get('.search-input-group > .btn').click()
    cy.wait(1000)

    cy.get('body').then($body => {
        if ($body.find('.btn-delete').length > 0) {
             cy.get('.btn-delete').first().click()
             cy.get('.modal-actions > .btn-delete').click()
             cy.wait(1000)
        }
    })
    
    cy.deleteSample(uniqueComment)
  })

  beforeEach(() => {
    cy.visit('http://localhost:8082/')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')
    cy.get(':nth-child(1) > select').select('Analysis')
    cy.get('.btn-load').click()
  })

  afterEach(() => {
    cy.get('[data-cy="log-out-btn"]').click();
    cy.wait(500);
    cy.url().should('include', '/auth');
  })

  it('Check CSV Export Functionality', () => {
    // Wir fangen den Request ab, um zu prüfen, ob er korrekt gesendet wird
    // und geben einen Dummy-Blob zurück, damit der Browser nicht wirklich speichert.
    cy.intercept('GET', '/api/analysis/export*', {
        statusCode: 200,
        body: 'Header1,Header2\nValue1,Value2',
        headers: { 'content-type': 'text/csv' }
    }).as('exportRequest')

    // Filtern, um sicherzustellen, dass Parameter übergeben werden
    cy.get('.search-input').type(sId)
    cy.get('.search-input-group > .btn').click()
    cy.wait(500)

    // Export Button klicken
    cy.get('button').contains('Export CSV').click()

    // Prüfen, ob Request rausging und ob Filter-Parameter dabei waren
    cy.wait('@exportRequest').then((interception) => {
        const url = interception.request.url
        expect(url).to.include('columns=') // Spalten sollten dabei sein
        // Da wir oben die Suche genutzt haben, wird diese im Frontend client-seitig gemacht,
        // oder wenn Filter genutzt werden, backend-seitig. 
        // Der Export nutzt `searchParams`.
    })

    // UI Feedback: Button sollte kurz laden (falls das Frontend schnell genug ist, sieht man es kaum)
    // Aber wir prüfen, ob kein Fehler angezeigt wird
    cy.get('.error-text').should('not.exist')
  })
})