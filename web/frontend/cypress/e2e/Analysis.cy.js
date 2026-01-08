import { erstelleDatum } from '../support/utils';

describe('Analysis Test:', () => {
  const sampleData = {
    s_id: 187187187187,
    s_stamp: erstelleDatum(0),
    name: 'Zuckeristdrüben',
    weight_net: 9.27,
    weight_bru: 10.0,
    weight_tar: 9.5,
    quantity: 3,
    distance: 2000,
    date_crumbled: erstelleDatum(2),
    s_flags: '----------',
    lane: 1,
    comment: 'Normal__//Sample_TEST_CASE_187_DONT_CHANGE_187_TIMESTAMP:' + erstelleDatum(0)
  };

  beforeEach(() => {
    cy.visit('http://localhost:8082/')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')
    cy.createSample(sampleData);
    cy.wait(500)
  });

  afterEach(() => {
    cy.deleteSample(sampleData.comment)
  });


  it('Create entry, check, edit and delete', () => {
    const sId = sampleData.s_id
    const pol = 1.87
    const nat = 18.78
    const kal = '83.15'
    const an = '0.18'
    const glu = 0
    const dry = 0
    const date_in = erstelleDatum(0)
    const date_out = erstelleDatum(1)
    const a_flags = 'MLHHL------'
    const lane = 1
    const comment = 'Normal__//Analysis_TEST_CASE_187_DONT_CHANGE_187_TIMESTAMP:' + erstelleDatum(0)

    //---------- Create

    cy.get(':nth-child(1) > select').select('Analysis')
    //Daten Laden
    cy.get('.btn-load').click()
    //neuer Eintrag
    cy.get('.btn-save').click()
    // S id
    cy.get('#field-s_id').type(sId)
    // Pol
    cy.get('#field-pol').type(pol)
    // Nat
    cy.get('#field-nat').type(nat)
    // Kal
    cy.get('#field-kal').type(kal)
    // An
    cy.get('#field-an').type(an)
    // Glu
    cy.get('#field-glu').type(glu)
    // Dry
    cy.get('#field-dry').type(dry)
    //Date In
    cy.get('#field-date_in').type(date_in)
    //Date Out 2023-07-27T10:37:06.000+00:00
    cy.get('#field-date_out').type(date_out)

    // A-Flags
    cy.get('#field-a_flags').type(a_flags)
    // Lane
    cy.get('#field-lane').type(lane)
    //Comment
    cy.get('#field-comment').type(comment)

    //Save btn
    cy.get('.form-actions > .btn-save').click()
    
    // search creatted item
    cy.get('.search-input').clear().type(comment)
    cy.get('.search-input-group > .btn').click()
    cy.contains('Date in').click()
    cy.contains('Date in').click()
    
    //open detail
    cy.get(':nth-child(1) > .sticky-col > .action-buttons > .btn-view').click()

    // Sicherstellen, dass das Modal offen ist
    cy.get('#detail-modal').should('be.visible')

    // S-ID
    cy.get('#detail-s_id').should('contain', sId)

    // Pol
    cy.get('#detail-pol').should('contain', pol)

    // Nat
    cy.get('#detail-nat').should('contain', nat)

    // Kal
    cy.get('#detail-kal').should('contain', kal)

    // An
    cy.get('#detail-an').should('contain', an)

    // Glu
    cy.get('#detail-glu').should('contain', glu)

    // Dry
    cy.get('#detail-dry').should('contain', dry)

    // A-Flags
    cy.get('#detail-a_flags').should('contain', a_flags)

    // Lane
    cy.get('#detail-lane').should('contain', lane)

    // Comment
    cy.get('#detail-comment').should('contain', comment)

    //Dates
    cy.get('#detail-date_in').should('not.be.empty')
    cy.get('#detail-date_out').should('not.be.empty')


    // -----------------------------------------------------
    // Edit
    // -----------------------------------------------------
    
    // click edit in deatail view
    cy.get('#btn-edit-from-detail').click()

    //check if we are in edit mode
    cy.get('#field-s_id').should('have.value', sId)
    
    const pol1 = 1.6

    const nat1 = 1.87

    const kal1 = 83.55

    const an1 = 0.78

    // Pol
    cy.get('#field-pol').clear().type(pol1)

    // Nat
    cy.get('#field-nat').clear().type(nat1)

    // Kal
    cy.get('#field-kal').clear().type(kal1)

    // An
    cy.get('#field-an').clear().type(an1)

    //Save btn
    cy.get('.form-actions > .btn-save').click()

    // search creatted item
    cy.get('.search-input').clear().type(comment)
    cy.get('.search-input-group > .btn').click()
    cy.contains('Date in').click()
    cy.contains('Date in').click()
    
    //open detail
    cy.get(':nth-child(1) > .sticky-col > .action-buttons > .btn-view').click()

    // Sicherstellen, dass das Modal offen ist
    cy.get('#detail-modal').should('be.visible')

    // S-ID
    cy.get('#detail-s_id').should('contain', sId)

    // Pol
    cy.get('#detail-pol').should('contain', pol1)

    // Nat
    cy.get('#detail-nat').should('contain', nat1)

    // Kal
    cy.get('#detail-kal').should('contain', kal1)

    // An
    cy.get('#detail-an').should('contain', an1)

    // Glu
    cy.get('#detail-glu').should('contain', glu)

    // Dry
    cy.get('#detail-dry').should('contain', dry)

    // A-Flags
    cy.get('#detail-a_flags').should('contain', a_flags)

    // Lane
    cy.get('#detail-lane').should('contain', lane)

    // Comment
    cy.get('#detail-comment').should('contain', comment)

    //Dates
    cy.get('#detail-date_in').should('not.be.empty')
    cy.get('#detail-date_out').should('not.be.empty')

    //close detail view
    cy.get('#btn-close-detail').click()

    //delete item
    cy.contains('Date in').click()
    cy.contains('Date in').click()
    cy.get('.btn-delete').click()
    cy.get('.modal-actions > .btn-delete').click()
    cy.get('body').should($body => {
      const infoText = $body.find('.info-text:contains("Zeige 0 von 0 Einträgen")').length > 0;
      const statusText = $body.find('.status-text:contains("Keine Daten vorhanden.")').length > 0;

      expect(infoText || statusText, 'Info- oder Status-Text vorhanden').to.be.true;
    });
  })

  it('Check create required error', () => {
    
    cy.get(':nth-child(1) > select').select('Analysis')
    //Daten Laden
    cy.get('.btn-load').click()
    //neuer Eintrag
    cy.get('.btn-save').click()
    //Save btn
    cy.get('.form-actions > .btn-save').click()
    cy.get('.error-text').should('contain', 'Alle Pflichtfelder müssen ausgefüllt sein')
    cy.get('.error-text').should('contain', 'Fehlende Felder: - S id')
  })
})