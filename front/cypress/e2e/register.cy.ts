describe('Register spec', () => {
  it('Successfully registers a new user', () => {
    // Intercepter la requête POST pour l'enregistrement et répondre avec un succès
    cy.intercept('POST', '/api/auth/register', {
      statusCode: 201,  // Statut de création réussi
      body: {
        id: 1,
        firstName: 'John',
        lastName: 'Doe',
        email: 'john.doe@example.com',
      },
    }).as('registerRequest');

    // Visiter la page d'enregistrement
    cy.visit('/register');

    // Remplir le formulaire d'enregistrement avec des informations valides
    cy.get('input[formControlName="firstName"]').type('John');
    cy.get('input[formControlName="lastName"]').type('Doe');
    cy.get('input[formControlName="email"]').type('john.doe@example.com');
    cy.get('input[formControlName="password"]').type('Password123');

    // Soumettre le formulaire
    cy.get('button[type="submit"]').click();

    // Attendre la réponse de l'enregistrement
    cy.wait('@registerRequest');

    // Vérifier la réponse de l'API si nécessaire
    cy.get('span.error').should('not.exist'); // Pas d'erreur
  });

  it('Shows error when registration fails', () => {
    // Intercepter la requête POST pour l'enregistrement et simuler une erreur
    cy.intercept('POST', '/api/auth/register', {
      statusCode: 400,  // Statut de la requête échouée (exemple : 400 Bad Request)
      body: {
        error: 'Email already in use',
      },
    }).as('registerRequestFail');

    // Intercepter la requête GET pour récupérer la session (si nécessaire pour l'échec)
    cy.intercept('GET', '/api/session').as('sessionRequestFail');

    // Visiter la page d'enregistrement
    cy.visit('/register');

    // Remplir le formulaire d'enregistrement avec un email déjà existant
    cy.get('input[formControlName="firstName"]').type('John');
    cy.get('input[formControlName="lastName"]').type('Doe');
    cy.get('input[formControlName="email"]').type('existing.email@example.com');
    cy.get('input[formControlName="password"]').type('Password123');

    // Soumettre le formulaire
    cy.get('button[type="submit"]').click();

    // Attendre la réponse de l'enregistrement
    cy.wait('@registerRequestFail');

    // Vérifier que le message d'erreur est affiché
    cy.get('span.error').should('be.visible').and('contain', 'An error occurred');

  });
});
