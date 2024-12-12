describe('Session Detail Page spec', () => {
  it('Displays session details and actions based on user role', () => {
    const sessions = [
      {
        id: 2,
        name: 'Yoga Session',
        date: '2023-03-15T00:00:00.000+00:00',
        teacher_id: 1,
        description: 'A relaxing yoga session to improve your flexibility and calm your mind.',
        users: [1, 2],
        createdAt: '2024-12-12T15:39:30',
        updatedAt: '2024-12-12T15:39:36'
      },
      {
        id: 3,
        name: 'session 2',
        date: '2013-02-01T00:00:00.000+00:00',
        teacher_id: 1,
        description: 'another description',
        users: [],
        createdAt: '2024-12-12T15:39:36',
        updatedAt: '2024-12-12T15:39:36',
      },
    ];


    // Mock session data and login as admin
    cy.customSession(sessions)
    cy.intercept('GET', '/api/session/2', { body: sessions[0] });
    cy.customLogin();

    cy.wait(1000);
    cy.get(':nth-child(1) > .mat-card-actions > :nth-child(1)').click();
    // Verify the session title is displayed
    cy.contains('h1', 'Yoga Session').should('be.visible');

    // Verify the session description is displayed
    cy.contains('Description:').should('be.visible');
    cy.contains(sessions[0].description).should('be.visible');

    // Verify the session date is displayed
    cy.contains('March 15, 2023').should('be.visible');

    // Verify the attendees count is displayed
    cy.contains('2 attendees').should('be.visible');

    // Verify the image is displayed
    cy.get('img.picture').should('be.visible');

    // Verify the created and updated dates are displayed
    cy.contains('Create at: December 12, 2024').should('be.visible');
    cy.contains('Last update: December 12, 2024').should('be.visible');

    // If the user is admin, verify the delete button is visible
    cy.get('button').contains('Delete').should('be.visible');
  });
});
