# Rapport D2

## Table des matières

- [Rapport D2](#rapport-d2)
  - [Table des matières](#table-des-matières)
  - [I) Organisation de l’équipe](#i-organisation-de-léquipe)
  - [II) Périmètre fonctionnel : Limites, Extensions, Points Forts et Points Faibles](#ii-périmètre-fonctionnel--limites-extensions-points-forts-et-points-faibles)
    - [2.1) Hypothèses de Travail (E.4)](#21-hypothèses-de-travail-e4)
    - [2.2) Points non implémentés relativement à la spécification et aux extensions requises](#22-points-non-implémentés-relativement-à-la-spécification-et-aux-extensions-requises)
    - [2.3) Fonctionnalités : points forts, points faibles](#23-fonctionnalités--points-forts-points-faibles)
  - [III) Conception](#iii-conception)
    - [3.1) Architecture](#31-architecture)
      - [Organisation du code](#organisation-du-code)
      - [Décomposition des services](#décomposition-des-services)
      - [Les entités et leurs communications](#les-entités-et-leurs-communications)
      - [Cheminement des requêtes](#cheminement-des-requêtes)
      - [Optimisations réalisées (front/back)](#optimisations-réalisées-frontback)
    - [3.2) Justification de l’Architecture](#32-justification-de-larchitecture)
      - [Points forts](#points-forts)
      - [Points faibles](#points-faibles)
  - [IV) Qualités des codes et gestion de projet](#iv-qualités-des-codes-et-gestion-de-projet)
  - [V) Rétrospective et auto-évaluation](#v-rétrospective-et-auto-évaluation)
    - [Nora Kayma-Kcilar (PO)](#nora-kayma-kcilar-po)
    - [Yohan Mazzi (SA)](#yohan-mazzi-sa)
    - [Romain Abbonato (Ops)](#romain-abbonato-ops)

---

## I) Organisation de l’équipe

- **Kayma-Kcilar Nora** : PO
- **Mazzi Yohan** : SA
- **Santos Reis Mathias** : Ops
- **Abbonato Romain** : Ops
- **Marill Matice** : QA

## II) Périmètre fonctionnel : Limites, Extensions, Points Forts et Points Faibles

### 2.1) Hypothèses de Travail (E.4)

- **Commande** : Une commande, qu’elle soit au sein d’un groupe ou non, est faite dans un seul restaurant.
- **Rejoindre une commande groupée** : Pour rejoindre un groupe, il est nécessaire de créer et de payer sa commande. Il est ensuite possible de passer une nouvelle commande qui remplacera la précédente. Tout utilisateur ayant passé commande peut valider le groupe et ainsi envoyer les commandes du groupe en préparation.
- **Livraison** : Les livraisons sont admises comme réalisables dans les temps donnés. La gestion des livreurs et de leurs disponibilités n’est pas gérée par l’application.

### 2.2) Points non implémentés relativement à la spécification et aux extensions requises

- Toutes les exigences MUST ont été complétées. Cependant :
  - Il manque dans le front la fonctionnalité de gestion des restaurants pour les managers (modifier les horaires ou les produits).
  - La fonctionnalité de compte et de connexion reste à implémenter.

### 2.3) Fonctionnalités : points forts, points faibles

- **Points forts** :
  - À détailler.

- **Points faibles** :
  - À détailler.

## III) Conception

### 3.1) Architecture

#### Organisation du code

Le code est composé de :
- **Un projet principal** contenu dans `core` :
  - Contient tous les objets compléts nécessaires au projet ainsi que les scénarios (gherkin) et les tests (unitaires et cucumber).
- **Cinq projets** contenus dans `services` :
  - Group, Order, Payment, Restaurant, User.
- **Partie front-end** écrite en React et contenue dans `front`.

#### Décomposition des services

- Chaque service possède :
  - Un serveur avec ses ports attribués.
  - Un handler interceptant des requêtes HTTP REST.
  - Une base de données (classe + fichier JSON) pour la persistance des données.
- Une gateway redirige les requêtes destinées à plusieurs services (ex. : dans le service Group).
- Chaque service est indépendant des autres et son architecture peut varier.

#### Les entités et leurs communications

- Chaque service utilise des objets :
  - Pris dans `core` si besoin et adaptés si nécessaire.
  - Les objets sont sérialisés et désérialisés pour interagir avec les fichiers JSON.

#### Cheminement des requêtes

1. **Récupération des restaurants** :
   - URI : `http://localhost:8080/api/restaurant`.

2. **Une étape de la prise de commande** :
   - À compléter.

#### Optimisations réalisées (front/back)

- Utilisation d’objets appropriés (ex. : `HashMap`).
- Records Java (ex. : `GroupCreationRequest`, `RestaurantRequest`) pour échanger des données de manière concise.

### 3.2) Justification de l’Architecture

#### Points forts

- Services organisés avec une nette séparation entre serveur, handlers et DB.
- Services indépendants avec un faible couplage.
- Architecture maintenable et évolutive.

#### Points faibles

- Difficulté à ajouter de nombreux nouveaux endpoints.
- Absence d’une API générale pour orchestrer les requêtes.
- Pas de système de cache pour optimiser les requêtes récurrentes.

## IV) Qualités des codes et gestion de projet

- À compléter.

## V) Rétrospective et auto-évaluation

### Nora Kayma-Kcilar (PO)

- **Fonctionnalités implémentées** :
  - Gestion des commandes de groupe.
  - Implémentation de stratégies de réduction (taille de groupe, nombre d’articles, etc.).
  - Validation des commandes avec prise en compte des délais.

- **Front-end** :
  - Intégration des interfaces de validation des commandes de groupe.
  - Visualisation des stratégies de réduction appliquées.

- **Réussites** :
  - Coordination efficace.
  - Modularité du code grâce à des design patterns.

- **Leçons apprises** :
  - Importance de la priorisation des tâches.

- **Améliorations possibles** :
  - Intégration plus rapide de la fonctionnalité de login.

### Yohan Mazzi (SA)

En tant que Software Architect, j’ai veillé à ce que l’architecture des services (APIs) soit claire et efficace en proposant des schémas papiers et me suis assuré que toute l’équipe suive le même fonctionnement de mise en place des services afin d’avoir une cohérence (serveur, handler, micro-services et db), et ce, malgré le fait que chaque service est indépendant. En effet, mon objectif a également été de faire en sorte que l’architecture soit facilement maintenable avec des services bien distincts permettant un développement sans dépendance. Enfin, j’ai dû logiquement m’assurer que les services fonctionnaient de manière fluide avec des données communes permettant de lier ces derniers et avec des réponses aux requêtes qui permettent leur utilisation par le front ou d’autres services efficacement. Ci-dessous est détaillé ma contribution, les réussites, les leçons apprises, ainsi que les erreurs et axes d’amélioration.

- **Fonctionnalités implémentées** :
  - Service de création et validation de groupes.
  - Implémentation de la passerelle Gateway pour le service Group.

- **Réussites** :
  - Architecture initiale robuste.
  - Utilisation de fichiers JSON pour la persistance des données.

- **Leçons apprises** :
  - Besoin d’une documentation claire pour prévenir les divergences entre services.

- **Améliorations possibles** :
  - Intégration de la notion d’utilisateur pour plus de fonctionnalités.
  - Optimisation de la gestion des fichiers JSON.

### Romain Abbonato (Ops)

En tant qu’Ops, mon rôle a été de garantir la fluidité et l’efficacité du développement tout au long du projet. Cela a impliqué de coordonner l’équipe sur nos différentes tâches. J’ai également travaillé à la résolution rapide des problèmes techniques pour maintenir un bon rythme de progression.

- **Fonctionnalités implémentées** :
  - Service Restaurant et interaction avec les commandes simples et groupées.
  - Filtrage des restaurants et articles selon plusieurs critères.

- **Front-end** :
  - Réalisation de la majorité des pages de l’application.

- **Réussites** :
  - Interface utilisateur intuitive pour identifier des bugs et améliorer l’application.
  - Conception back-end solide qui a permis d’avancer rapidement dans les dernières étapes.

- **Leçons apprises** :
  - Importance de la collaboration inter-équipe.
  - Valeur des tests utilisateurs pour collecter des retours précieux.

- **Améliorations possibles** :
  - Mise en place de tests end-to-end pour valider les cas d’utilisation.
  - Amélioration de la gestion des images associées aux restaurants et articles en enrichissant les métadonnées dans les fichiers JSON.

---

