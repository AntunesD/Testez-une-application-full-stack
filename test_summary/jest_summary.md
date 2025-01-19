# Récapitulatif des Tests de Fonctionnalités

## Tests Unitaires

| **Fonctionnalité**    | **Tests Réalisés**                                                               |
| --------------------- | -------------------------------------------------------------------------------- |
| **AppComponent**      | - Crée l'application                                                             |
|                       | - Appele `logOut` et navigue sur `logout()`                                      |
| **MeComponent**       | - Charge les données utilisateur à l'initialisation                              |
|                       | - `back()` doit naviguer en arrière                                              |
|                       | - `delete()` doit supprimer l'utilisateur et se déconnecter                      |
| **LoginComponent**    | - Le formulaire doit être invalide initialement                                  |
|                       | - Vérifie la validité ou non des champs                                          |
|                       | - Appele `authService.login` et `sessionService.logIn` sur une soumission valide |
|                       | - Défini `onError` en cas d'échec de connexion                                   |
| **RegisterComponent** | - Le formulaire doit être invalide initialement                                  |
|                       | - Vérifie la validité ou non des champs                                          |
|                       | - Appele `authService.register` et naviguer sur une soumission valide            |
|                       | - Défini `onError` en cas d'échec d'inscription                                  |
| **DetailComponent**   | - Vérifie l'appel de `ngOnInit` et l'initialisation des données                  |
| **SessionApiService** | - Récupere les sessions                                                          |
|                       | - Crée une nouvelle session                                                      |
|                       | - Supprime une session                                                           |
|                       | - Met à jour une session                                                         |
|                       | - Participe à une session                                                        |
|                       | - Se désinscrie à une session                                                    |
| **SessionService**    | - Vérifie la création du service                                                 |
|                       | - Vérifie l'état initial de `isLogged` et `sessionInformation`                   |
|                       | - Met à jour `isLogged` lors de la connexion                                     |
|                       | - Met à jour de `isLogged` lors de la déconnexion                                |
| **TeacherService**    | - Récupere tous les enseignants                                                  |
|                       | - Récupere les détails d'un enseignant                                           |
|                       | - Gère les erreurs 404 lors de la récupération des détails                       |
| **UserService**       | - Récupere un utilisateur par ID                                                 |
|                       | - Supprime un utilisateur par ID                                                 |
| **FormComponent**     | - Récupère les détails de la session lors de la mise à jour                      |
|                       | - Crée une nouvelle session                                                      |
|                       | - Met à jour une session existante                                               |
|                       | - Récupère tous les enseignants                                                  |

## Tests d'Intégration

| **Fonctionnalité**    | **Tests Réalisés**                                                       |
| --------------------- | ------------------------------------------------------------------------ |
| **DetailComponent**   | - Session sont récupérés lors de l'initialisation.                       |
|                       | - Suppression de la session et la navigation vers la liste des sessions. |
| **FormComponent**     | - Les détails de la session sont récupérés lors de la mise à jour.       |
|                       | - Création d'une nouvelle session.                                       |
|                       | - Mise à jour d'une session existante.                                   |
|                       | - Tous les enseignants sont récupérés.                                   |
| **LoginComponent**    | - Navige vers la liste des sessions après une connexion réussie.         |
|                       | - `onError` est défini  en cas d'échec de connexion.                     |
| **RegisterComponent** | - Navigation vers la page de connexion après une inscription réussie.    |
|                       | - `onError` est défini en cas d'échec d'inscription.                     |
