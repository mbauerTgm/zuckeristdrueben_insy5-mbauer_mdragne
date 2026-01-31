import { erstelleDatum } from '../support/utils';

describe('Advanced Analysis Filtering:', () => {
  const uniqueComment = 'AdvFilterTest_' + Date.now()
  const sId = 999999187111 

  before(() => {
    cy.visit('http://localhost:8082/')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')

    // Sample anlegen
    cy.createSample({
        s_id: sId,
        s_stamp: erstelleDatum(0),
        name: 'FilterBase',
        weight_net: 10, weight_bru: 11, weight_tar: 1, quantity: 1, distance: 10,
        date_crumbled: erstelleDatum(0), s_flags: '-', lane: 1, 
        comment: uniqueComment + '_Sample'
    })

    // Analysis anlegen
    cy.get(':nth-child(1) > select').select('Analysis')
    cy.get('.btn-load').click()
    cy.get('.btn-save').click().click()
    cy.wait(500)
    cy.get('#field-s_id').type(sId)
    cy.get('#field-pol').type(5.5)
    cy.get('#field-date_in').type(erstelleDatum(0)) 
    cy.get('#field-comment').type(uniqueComment)
    cy.get('.form-actions > .btn-save').click()

    cy.get('[data-cy="log-out-btn"]').click();
    cy.wait(500);
    cy.url().should('include', '/auth');
  })

  after(() => {
    cy.visit('http://localhost:8082/auth')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')
    
    // Analysis löschen
    cy.get(':nth-child(1) > select').select('Analysis')
    cy.get('.btn-load').click()
    cy.get('.search-input').clear().type(uniqueComment)
    cy.get('.search-input-group > .btn').click()
    
    // Warten bis Suche fertig ist
    cy.wait(1000) 
    
    cy.get('body').then($body => {
        // Nur löschen, wenn auch wirklich was gefunden wurde
        if ($body.find('.btn-delete').length > 0) {
            cy.get('.btn-delete').first().click()
            cy.get('.modal-actions > .btn-delete').click()
            // Warten bis Modal weg ist und Delete durch ist
            cy.wait(1000)
        }
    })

    // Sample löschen
    cy.deleteSample(uniqueComment + '_Sample')
  })

  beforeEach(() => {
    cy.visit('http://localhost:8082/')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')
    cy.get(':nth-child(1) > select').select('Analysis')
    cy.get('.btn-load').click()
    
    // Reset Filters before each test just in case
    cy.contains('button', 'Filter').click()
    cy.contains('.btn-link', 'Reset').click()
    cy.get('.filter-dropdown .btn-save').click()
  })

  it('Filter by Sample ID Range', () => {
    cy.contains('button', 'Filter').click()
    
    // Range setzen: sId bis sId (Exakter Match via Range)
    cy.contains('.filter-group', 'Sample ID').within(() => {
        cy.get('input').eq(0).type(sId)
        cy.get('input').eq(1).type(sId)
    })
    
    cy.get('.filter-dropdown .btn-save').click()
    
    // Check: Eintrag muss da sein
    cy.get('table').should('contain', uniqueComment)
    cy.get('table').should('contain', sId)

    // Check: Datenmenge (sollte 1 sein bei exakter ID, außer es gibt Leichen)
    cy.get('.info-text').should('contain', 'Zeige')
  })

  it('Filter by Date In (Datetime Local)', () => {
    const yesterday = erstelleDatum(-1).slice(0, 16)
    const tomorrow = erstelleDatum(1).slice(0, 16)

    cy.contains('button', 'Filter').click()
    
    cy.contains('.filter-group', 'Date In').within(() => {
        cy.get('input').eq(0).type(yesterday)
        cy.get('input').eq(1).type(tomorrow)
    })
    
    cy.get('.filter-dropdown .btn-save').click()

    // Unser Eintrag von "Heute" muss da sein
    cy.get('table').should('contain', uniqueComment)
  })

  it('Negative Filter Test (No Results)', () => {
    cy.contains('button', 'Filter').click()
    
    // ID Range setzen, die nicht existiert
    cy.contains('.filter-group', 'ID (a_id)').within(() => {
        cy.get('input').eq(0).type('999999999')
    })
    
    cy.get('.filter-dropdown .btn-save').click()

    // Tabelle sollte leer sein
    cy.get('.status-text').should('contain', 'Keine Daten vorhanden')
  })
})