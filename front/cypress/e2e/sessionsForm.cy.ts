describe('Page de création/édition de session', () => {
  it('Affiche le formulaire de création/édition de session et soumet les données', () => {

    // Interception for teacher API
    cy.intercept('GET', '/api/teacher', {
      body: [{
        id: 1,
        lastName: 'DELAHAYE',
        firstName: 'Margot',
        createdAt: '2024-11-23T11:47:46',
        updatedAt: '2024-11-23T11:47:46'
      }]
    }).as('getTeacher');
    cy.intercept('post', '/api/session', {
      statusCode: 200,  // Ensure a successful response
      body: {}          // Empty response body, adjust based on your API
    }).as('createdSession');

    // Connexion personnalisée
    cy.customSession();
    cy.customLogin();

    // Attendre que les données soient disponibles
    cy.wait(1000);

    cy.get('.mat-card-header > .mat-focus-indicator').click();

    cy.wait(1000);

    // Vérifier que le titre de la session est affiché correctement
    cy.contains('h1', 'Create session').should('be.visible');

    // Vérifier que les champs du formulaire sont présents
    cy.get('mat-form-field').should('have.length', 4);  // Vérifier qu'il y a 4 champs de formulaire

    // Vérifier si le champ "Name" est bien présent
    cy.get('input[formControlName="name"]').should('be.visible');

    // Vérifier si le champ "Date" est bien présent
    cy.get('input[type="date"]').should('be.visible');

    // Vérifier si la liste déroulante des enseignants est présente et contient des enseignants
    cy.get('mat-select[formControlName="teacher_id"]').should('be.visible').click();
    cy.get('mat-option').should('have.length', 1);  // Vérifier qu'il y a un enseignant dans la liste

    // Vérifier si le champ "Description" est présent
    cy.get('textarea[formControlName="description"]').should('be.visible');

    // Vérifier que le bouton "Save" est visible et désactivé si le formulaire est invalide
    cy.get('button[type="submit"]').should('be.visible').and('be.disabled');

    // Compléter le formulaire avec des données
    cy.get('input[formControlName="name"]').click({ force: true }).type('Nouvelle session de test');

    cy.get('input[type="date"]').click({ force: true }).type('2024-12-20');
    cy.get('mat-option').first().click(); // Sélectionner le premier enseignant
    cy.get('textarea[formControlName="description"]').type('Description de la nouvelle session');

    // Soumettre le formulaire
    cy.get('button[type="submit"]').click();

    // Vérifier que le formulaire a été soumis (affichage d'un message ou redirection)
    cy.contains('created').should('be.visible');


  });
});
