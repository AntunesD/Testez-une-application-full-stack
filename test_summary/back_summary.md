# Récapitulatif des Tests Backend


## Tests Unitaires

| **Fonctionnalité** | **Tests Réalisés**                                    |
| ------------------ | ----------------------------------------------------- |
| **AuthController** | - Test de l'authentification réussie d'un utilisateur |
|                    | - Test de l'enregistrement d'un utilisateur           |
|                    | - Test de l'enregistrement avec un email déjà pris    |
| **SessionService** | - Test de création d'une session                      |
|                    | - Test de suppression d'une session                   |
|                    | - Test de récupération de toutes les sessions         |
|                    | - Test de récupération d'une session par ID           |
|                    | - Test de participation à une session                 |
|                    | - Test de mise à jour d'une session                   |
|                    | - Test de désinscription d'une session                |
| **TeacherService** | - Test de récupération de tous les enseignants        |
|                    | - Test de récupération d'un enseignant par ID         |
| **UserService**    | - Test de suppression d'un utilisateur                |
|                    | - Test de récupération d'un utilisateur par ID        |



## Tests de Mapper

| **Mapper**        | **Tests Réalisés**                                                         |
| ----------------- | -------------------------------------------------------------------------- |
| **SessionMapper** | - Test de conversion entre Session et SessionDto                           |
|                   | - Test de conversion entre une liste de Session et une liste de SessionDto |
| **TeacherMapper** | - Test de conversion entre Teacher et TeacherDto                           |
|                   | - Test de conversion entre une liste de Teacher et une liste de TeacherDto |
| **UserMapper**    | - Test de conversion entre User et UserDto                                 |
|                   | - Test de conversion entre une liste de User et une liste de UserDto       |

## Tests d'Exceptions

| **Exception**           | **Tests Réalisés**                               |
| ----------------------- | ------------------------------------------------ |
| **BadRequestException** | - Vérification du statut HTTP correct            |
|                         | - Vérification de l'héritage de RuntimeException |
| **NotFoundException**   | - Vérification du statut HTTP correct            |
|                         | - Vérification de l'héritage de RuntimeException |


## Tests d'Intégration

| **Fonctionnalité** | **Tests Réalisés**                                                 |
| ------------------ | ------------------------------------------------------------------ |
| **Auth**           | - Test de connexion d'un utilisateur avec des identifiants valides |
|                    | - Test d'enregistrement d'un nouvel utilisateur                    |
|                    | - Test de connexion avec un email déjà utilisé                     |
| **Session**        | - Test de création d'une session                                   |
|                    | - Test de récupération d'une session par ID                        |
|                    | - Test de mise à jour d'une session                                |
|                    | - Test de suppression d'une session                                |
|                    | - Test de participation à une session                              |
|                    | - Test de désinscription d'une session                             |
|                    | - Test de récupération de toutes les sessions                      |