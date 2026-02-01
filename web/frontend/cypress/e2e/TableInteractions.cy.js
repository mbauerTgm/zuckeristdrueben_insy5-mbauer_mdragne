import { erstelleDatum } from '../support/utils';

describe('Table Interactions (Sort, Page, Limit) - Analysis:', () => {
  const baseComment = 'TableInt_' + Date.now()
  const totalItems = 12
  
  // 13-stellige ID Basis (Timestamp Sekunden + 000)
  const startIdStr = Math.floor(Date.now() / 1000).toString() + '000'
  const startId = parseInt(startIdStr)

  before(() => {
    cy.visit('http://localhost:8082/')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')

    const now = new Date().toISOString();
    
    // Daten erstellen (12 Samples + 12 Analyses)
    for (let i = 1; i <= totalItems; i++) {
        let idStr = (startId + i).toString()
        
        // Sample
        cy.request({
            method: 'POST',
            url: 'http://localhost:8082/api/samples',
            body: {
                s_id: idStr,
                s_stamp: now,
                name: `SortItem_${i.toString().padStart(2, '0')}`, 
                weight_net: 10 + i, weight_bru: 11, weight_tar: 1, quantity: 1, distance: 10,
                date_crumbled: now, s_flags: '-', lane: 1, 
                comment: baseComment
            }
        })

        // Analysis
        cy.request({
            method: 'POST',
            url: 'http://localhost:8082/api/analysis',
            body: {
                s_id: idStr,
                s_stamp: now,
                pol: 10 + i,
                date_in: now, a_flags: '-', 
                comment: baseComment
            }
        })
    }
    
    cy.get('[data-cy="log-out-btn"]').click()
  })

  after(() => {
    // Cleanup
    cy.request({ method: 'POST', url: 'http://localhost:8082/api/auth/logout', failOnStatusCode: false })
    cy.visit('http://localhost:8082/auth')
    cy.get('#username', { timeout: 10000 }).should('be.visible')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')
    
    const filterUrl = `http://localhost:8082/api/analysis/filter?page=0&size=100&sId.from=${startId}&sId.to=${startId + 100}`
    
    cy.request('GET', filterUrl).then((resp) => {
        const items = resp.body.content || []
        const myItems = items.filter(i => i.comment === baseComment)
        
        myItems.forEach(item => {
            cy.request({
                method: 'DELETE',
                url: `http://localhost:8082/api/analysis/${item.a_id}`,
                failOnStatusCode: false 
            })
            const stampEnc = encodeURIComponent(item.s_stamp)
            cy.request({
                method: 'DELETE',
                url: `http://localhost:8082/api/samples/${item.s_id}/${stampEnc}`,
                failOnStatusCode: false 
            })
        })
    })
  })

  beforeEach(() => {
    // Sauberes Login
    cy.request({ method: 'POST', url: 'http://localhost:8082/api/auth/logout', failOnStatusCode: false })
    cy.visit('http://localhost:8082/auth')
    cy.get('#username', { timeout: 10000 }).should('be.visible')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')
    
    // Analysis Tabelle wählen
    cy.get(':nth-child(1) > select').select('Analysis')
    cy.get('.btn-load').click()
    
    // Backend-Filter setzen -> Lädt NUR unsere 12 Einträge
    cy.contains('button', 'Filter').click()
    cy.get('.filter-dropdown').should('be.visible')
    
    cy.contains('.filter-group', 'Sample ID').within(() => {
        cy.get('input').eq(0).clear().type((startId + 1).toString())
        cy.get('input').eq(1).clear().type((startId + totalItems).toString())
    })
    
    cy.intercept('GET', '/api/analysis/filter*').as('applyFilter')
    cy.get('.filter-dropdown .btn-save').click()
    cy.wait('@applyFilter')
    
    cy.get('table', { timeout: 10000 }).should('exist')
  })

  it('Change Display Limit (Rows per Page)', () => {
    // Limit auf 10 setzen
    cy.contains('label', 'Limit:').parent().find('select').select('10')
    cy.intercept('GET', '/api/analysis/filter*').as('loadLimit')
    cy.wait('@loadLimit')
    
    // Check: Seite 1 = 10 Zeilen
    cy.get('tbody tr').should('have.length', 10)
    cy.get('.info-text').should('contain', 'Zeige 10 von')

    // Limit auf 25 setzen
    cy.contains('label', 'Limit:').parent().find('select').select('25')
    cy.wait('@loadLimit')

    // Check: Seite 1 = 12 Zeilen (alle)
    cy.get('tbody tr').should('have.length', totalItems)
  })

  it('Pagination Functionality (Next/Prev)', () => {
    cy.contains('label', 'Limit:').parent().find('select').select('10')
    cy.intercept('GET', '/api/analysis/filter*').as('loadPage')
    cy.wait('@loadPage')

    cy.get('.page-info').should('contain', 'Seite 1')
    cy.get('tbody tr').should('have.length', 10)

    // Nächste Seite
    cy.get('.pagination-controls button').contains('›').click()
    cy.wait('@loadPage')

    cy.get('.page-info').should('contain', 'Seite 2')
    cy.get('tbody tr').should('have.length', totalItems - 10)

    // Zurück
    cy.get('.pagination-controls button').contains('‹').click()
    cy.wait('@loadPage')
    cy.get('.page-info').should('contain', 'Seite 1')
  })

  it('Table Sorting (ASC/DESC)', () => {
    
    cy.get('tbody tr').should('have.length', totalItems)

    // Sortieren nach "S id" (ASC)
    cy.get('thead th').contains('S id').click()
    cy.wait(200) // Client-Side Sort Wait
    
    const firstID = (startId + 1).toString()
    const lastID = (startId + totalItems).toString()

    // ASC Check: Kleinste ID oben
    cy.get('tbody tr').first().should('contain', firstID)
    cy.get('tbody tr').last().should('contain', lastID)

    // Sortierung umkehren (DESC)
    cy.get('thead th').contains('S id').click()
    cy.wait(200)
    
    // DESC Check: Größte ID oben
    cy.get('tbody tr').first().should('contain', lastID)
    cy.get('tbody tr').last().should('contain', firstID)
  })
})