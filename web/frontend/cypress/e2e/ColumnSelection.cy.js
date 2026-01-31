describe('Column Selection Test:', () => {

  beforeEach(() => {
    cy.visit('http://localhost:8082/')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')
    
    // Request abfangen, damit wir warten können bis Daten da sind
    cy.intercept('GET', '/api/analysis/filter*').as('loadData')

    cy.get(':nth-child(1) > select').select('Analysis')
    cy.get('.btn-load').click()

    cy.wait('@loadData')
    cy.get('table').should('exist')
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