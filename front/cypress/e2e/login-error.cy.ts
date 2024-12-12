describe('Login spec', () => {
  it('Login failed with incorrect credentials', () => {
    cy.visit('/login');

    // Interception de la requête avec un statut 401 (mauvais identifiants)
    cy.intercept('POST', '/api/auth/login', {
      statusCode: 401,
      body: { message: 'Invalid credentials' },
    }).as('loginRequest');

    // Remplir les champs avec des identifiants incorrects
    cy.get('input[formControlName=email]').type("wrong@user.com");
    cy.get('input[formControlName=password]').type("wrongPassword123");

    // Soumettre le formulaire
    cy.get('form').submit(); // ou cy.get('button[type="submit"]').click();

    // Attendre l'interception de la requête
    cy.wait('@loginRequest', { timeout: 10000 });

    // Vérifier que le message d'erreur s'affiche
    cy.get('p.error').should('be.visible').and('contain', 'An error occurred');
  });

  it('Email field turns red when clicked without filling in', () => {
    cy.visit('/login'); // Accéder à la page de login

    // Cliquer sur le champ email sans le remplir
    cy.get('input[formcontrolname="email"]').click();

    // Vérifier que la classe "mat-form-field-invalid" est présente après avoir cliqué sur le champ
    cy.get('input[formcontrolname="email"]').should('have.class', 'ng-invalid');

    cy.get('input[formcontrolname="password"]').click();
    // Vérifier que le champ email est visuellement en erreur (avec la classe .mat-warn)
    cy.get('mat-form-field').should('have.class', 'mat-form-field-invalid');
    cy.get('input[formcontrolname="email"]').should('have.class', 'mat-input-element');

    // Vérifier que le caret est rouge, ce qui indique l'état invalide du champ
    cy.get('input[formcontrolname="email"]').should('have.css', 'caret-color', 'rgb(244, 67, 54)'); // Cela correspond à #f44336 en hexadécimal
  });
});
