describe('Logs Test:', () => {
  it('Check if Log is Read Only', () => {
    cy.visit('http://localhost:8082/')

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

    //open detail from first entry
    cy.get(':nth-child(1) > .sticky-col > .action-buttons > .btn-view').click()

    // Sicherstellen, dass das Modal offen ist
    cy.get('#detail-modal').should('be.visible')

    //check if edit button exists in detail view
    cy.get('#btn-edit-from-detail').should('not.exist')

    cy.get('.modal-actions > .btn').click()
  })
})