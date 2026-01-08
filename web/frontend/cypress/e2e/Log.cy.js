describe('Logs Test:', () => {
  it('Check if Log is Read Only', () => {
    cy.visit('http://localhost:8082/')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')
    //---------- Create
    cy.get(':nth-child(1) > select').select('Log (Read-Only)')

    //Daten Laden
    cy.get('.btn-load').click()

    //check if neuer Eintrag Button exists
    cy.get('.btn-save').should('not.exist')

    //check if any edit button exist
    cy.get('.btn-edit').should('not.exist')

    //check if any delete button exist
    cy.get('.btn-delete').should('not.exist')

    cy.get('body').then($body => {
      //check if there are entries
      const noData = $body.find('.status-text:contains("Keine Daten vorhanden.")').length > 0
      if (!noData) {
        //if there are entries, open the detailmodal
        cy.get(':nth-child(1) > .sticky-col > .action-buttons > .btn-view').click()

        // check the detailmodal is visible
        cy.get('#detail-modal').should('be.visible')

        //check that edit is not visible in the detailmodal
        cy.get('#btn-edit-from-detail').should('not.exist')

        // close the modal
        cy.get('.modal-actions > .btn').click()
      } else {
        cy.log('Keine Einträge vorhanden – Detail-Check übersprungen')
      }
    })
  })
})