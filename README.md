<h1 align="center">
  <br>
  <img src="core/doc/assets/logo.png">
  <br>
  Sophia Eats
  <br>
</h1>

<h5 align="center">Sophia Eats est un système en ligne de commande de repas pour le campus, 
permettant aux étudiants et au personnel de commander facilement auprès de différents restaurants 
et de se faire livrer à des points spécifiques en un temps précis.</h5>

<p align="center">
  <a href="#team">Team</a> •
  <a href="#structure-du-projet">Structure du projet</a> •
  <a href="#installation">Installation</a> 
</p>

---

## TEAM

<div align="center">
    <table style="border: none;">
        <tr>
            <td align="center" style="border: none;">
                <img src="core/doc/assets/teampics/Nora.png" width="auto" height="100" style="border-radius: 20px;">
                <p>Nora KAYMA-KCILAR</p>
                <span style="font-weight: bold;">PO</span>
            </td>
            <td align="center" style="border: none;">
                <img src="core/doc/assets/teampics/romain.jfif" width="100" height="auto" style="border-radius: 20px;">
                <p>Romain ABBONATO</p>
                <span style="font-weight: bold;">OPS</span>
            </td>
            <td align="center" style="border: none;">
                <img src="core/doc/assets/teampics/matice.png" width="auto" height="100" style="border-radius: 20px;">
                <p>Matice MARILL</p>
                <span style="font-weight: bold;">QA</span>
            </td>
            <td align="center" style="border: none;">
                <img src="core/doc/assets/teampics/yohan.png" width="100" height="auto" style="border-radius: 20px;">
                <p>Yohan MAZZI</p>
                <span style="font-weight: bold;">SA</span>
            </td>
            <td align="center" style="border: none;">
                <img src="core/doc/assets/teampics/mathias.png" width="100" height="auto" style="border-radius: 20px;">
                <p>Mathias SANTOS REIS</p>
                <span style="font-weight: bold;">OPS</span>
            </td>
        </tr>
    </table>
</div>

---

## Structure du projet

La structure du projet Sophia Eats est organisée pour séparer la logique métier centrale du système des services spécialisés. Voici un aperçu de l’organisation actuelle des dossiers principaux et de leurs rôles respectifs :

```plaintext
Sophia-Eats/
│
├── core/                     # Contient le noyau de l'application avec les classes de base et la logique métier.
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/         # Classes Java principales, modèle métier, logique métier.
│   │   └── test/
│   │       ├── java/         # Tests unitaires et d'intégration des classes de base.
│   │       └── resources/    # Regroupe toutes les features de tests écrites en Cucumber
│   ├── doc/
│   │   ├── README.md         # Documentation détaillée sur le module `core`.
│   └── pom.xml               # Fichier de configuration Maven pour le module `core`.
│
└── services/                 # Contient les différents services spécifiques de l'application.
    ├── Group/                 # Service de gestion des Groups.
    │   ├── src/
    │   └── pom.xml
    ├── Order/                # Service de gestion des Orders.
    │   ├── src/
    │   └── pom.xml
    ├── User/                 # Service de gestion des Utilisateurs.
    │   ├── src/
    │   └── pom.xml
    ├── Restaurant/           # Service de gestion des Restaurants.
    │   ├── src/
    │   └── pom.xml
    ├── Payment/              # Service pour la gestion des Payments.
    │   ├── src/
    │   └── pom.xml
    └── pom.xml               # Fichier de configuration Maven pour le module `services`.
```


