package fr.unice.polytech.biblio;

import fr.unice.polytech.user.Etudiant;
import io.cucumber.java.fr.*;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Ph. Collet
 */
public class EmpruntLivreStepdefs {
    Bibliotheque biblio = new Bibliotheque();
    Etudiant etudiant;


    @Etantdonné("une bibliothèque avec un etudiant de nom {string} et de noEtudiant {int}")
    public void uneBibliothequeAvecUnEtudiantDeNomEtDeNoEtudiant(String nom, int ident) {
        etudiant = new Etudiant();
        etudiant.setNom(nom);
        etudiant.setNoEtudiant(ident);
        biblio.addEtudiant(etudiant);
    }


    @Etantdonné("un etudiant de nom {string} et de noEtudiant {int}")
    public void etantDonneUnEtudiant(String nomEtudiant, Integer noEtudiant)  // besoin de refactorer int en Integer car utilisation de la généricité par Cucumber Java 8
    {
        etudiant = new Etudiant();
        etudiant.setNom(nomEtudiant);
        etudiant.setNoEtudiant(noEtudiant);
        biblio.addEtudiant(etudiant);
    }
    @Et("un livre de titre {string}")
    public void eUnLivre(String titreLivre) {
        Livre livre = new Livre(titreLivre);
        biblio.addLivre(livre);
    }

    @Et("un livre de titre {string} en deux exemplaires")
    public void unLivreDeTitreEnDeuxExemplaires(String name) {
        eUnLivre(name);
        eUnLivre(name);
    }



    @Quand("{string} emprunte le livre {string}")
    public void quandEmprunte(String nomEtudiant, String titreLivre)  {
        etudiant = biblio.getEtudiantByName(nomEtudiant);
        Optional<Livre> livreOptional = biblio.getLivreDisponibleByTitle(titreLivre);
        livreOptional.ifPresent(livre -> biblio.emprunte(etudiant, livre));
    }
    @Et("Il y a le livre {string} dans un emprunt de la liste d'emprunts")
    public void etLivreDejaEmprunte(String titreLivre) {
        assertTrue(etudiant.getEmprunts().stream().
                anyMatch(emp -> emp.getLivreEmprunte().getTitre().equals(titreLivre)));
    }
    @Et("Le livre {string} est indisponible")
    public void etLivreDispo(String titreLivre)  {
        assertFalse(biblio.getLivreDisponibleByTitle(titreLivre).isPresent());
    }



    @Quand("{string} rend le livre {string}")
    public void rendreLivre(String nomEtudiant, String titreLivre) {
        etudiant = biblio.getEtudiantByName(nomEtudiant);
        Livre livre = etudiant.getEmpruntFor(titreLivre).getLivreEmprunte();
        biblio.rend(livre);
    }

    @Alors("Le livre {string} est disponible")
    public void leLivreEstDisponible(String titreLivre) {
        assertTrue( biblio.getLivreDisponibleByTitle(titreLivre).isPresent());
    }


    @Alors("Il y a {int} dans son nombre d'emprunts")
    public void ilYADansSonNombreDEmprunts(int nombredEmprunts) {
        assertEquals(nombredEmprunts, etudiant.getNombreDEmprunts());
    }

    @Etantdonnéque("{string} a emprunté le livre {string}")
    public void aEmprunteLeLivre(String nomEtudiant, String nomLivre) {
        Etudiant e = biblio.getEtudiantByName(nomEtudiant);
        Livre l = biblio.getLivreDisponibleByTitle(nomLivre).get();
        biblio.emprunte(e,l);
    }
}
