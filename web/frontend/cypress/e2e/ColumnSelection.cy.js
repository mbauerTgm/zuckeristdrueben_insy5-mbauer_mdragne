import { erstelleDatum } from '../support/utils';

describe('Column Selection Test:', () => {
  const sId = 888888888888 // Test-ID für diesen Test
  const uniqueComment = 'ColTest_' + Date.now()

  before(() => {
    cy.visit('http://localhost:8082/')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')

    // Sample erstellen
    cy.createSample({
        s_id: sId,
        s_stamp: erstelleDatum(0),
        name: 'ColumnTestBase',
        weight_net: 10, weight_bru: 11, weight_tar: 1, quantity: 1, distance: 10,
        date_crumbled: erstelleDatum(0), s_flags: '-', lane: 1, 
        comment: uniqueComment
    })

    // Analyse erstellen
    cy.get(':nth-child(1) > select').select('Analysis')
    cy.get('.btn-load').click()
    cy.wait(500)
    cy.get('.btn-save').click()// Create Mode
    cy.wait(500) // UI Animation abwarten
    cy.get('#field-s_id').type(sId)
    cy.get('#field-pol').type(12.5) // Wert für "Pol" Spalte
    cy.get('#field-date_in').type(erstelleDatum(0))
    cy.get('.form-actions > .btn-save').click()
    
    // Logout um Session sauber für die Tests zu übergeben
    cy.get('[data-cy="log-out-btn"]').click();
    cy.wait(500);
    cy.url().should('include', '/auth');
  })

  beforeEach(() => {
    cy.visit('http://localhost:8082/')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')
    
    // Request abfangen, damit wir warten können bis Daten da sind
    cy.intercept('GET', '/api/analysis/filter*').as('loadData')

    cy.get(':nth-child(1) > select').select('Analysis')
    cy.get('.btn-load').click()

    cy.wait('@loadData')
    cy.get('table',).should('exist')
  })

  after(() => {
    cy.visit('http://localhost:8082/auth')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')
    
    // Analysis löschen
    cy.get(':nth-child(1) > select').select('Analysis')
    cy.get('.btn-load').click()
    cy.get('body').then($body => {
        if ($body.find('table').length > 0) {
             cy.get('.btn-delete').first().click()
             cy.get('.modal-actions > .btn-delete').click()
        }
    })
    
    // Sample löschen
    cy.deleteSample(uniqueComment)
  })

  afterEach(() => {
    cy.get('[data-cy="log-out-btn"]').click();
    cy.wait(500);
    cy.url().should('include', '/auth');
  })

  it('Toggle specific columns', () => {
    // Spaltenauswahl öffnen
    cy.contains('button', 'Spalten').click()
    cy.get('.column-dropdown').should('be.visible')

    // Prüfen, ob "Pol" initial sichtbar ist in der Tabelle
    cy.get('thead').contains('Pol').should('be.visible')

    // "Pol" abwählen
    cy.get('.column-dropdown').contains('label', 'Pol').find('input[type="checkbox"]').uncheck()

    // Prüfen, ob "Pol" verschwunden ist
    cy.get('thead').contains('Pol').should('not.exist')

    // "Pol" wieder anwählen
    cy.get('.column-dropdown').contains('label', 'Pol').find('input[type="checkbox"]').check()
    cy.get('thead').contains('Pol').should('be.visible')
  })

  it('Select All / Select None functionality', () => {
    cy.contains('button', 'Spalten').click()

    // "Alle" auswählen
    cy.get('.column-dropdown').contains('button', 'Alle').click()

    // Spalten sollten wieder da sein
    cy.get('thead').contains('S id').should('be.visible')
    cy.get('thead').contains('Nat').should('be.visible')
  })

  it('Close selector when clicking outside', () => {
    cy.contains('button', 'Spalten').click()
    cy.get('.column-dropdown').should('be.visible')

    // Klick auf den Header (außerhalb des Dropdowns)
    cy.get('.main-title').click()
    
    cy.get('.column-dropdown').should('not.exist')
  })
})