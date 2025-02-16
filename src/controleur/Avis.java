package controleur;

import java.util.Date;

public class Avis {
    private int idAvis, idLivre, idUser;
    private String nomLivre, commentaireAvis, noteAvis, dateAvis;

    public Avis(int idAvis, int idLivre, String nomLivre, int idUser, String commentaireAvis, String noteAvis, String dateAvis) {
        this.idAvis = idAvis;
        this.idLivre = idLivre;
        this.nomLivre = nomLivre;
        this.idUser = idUser;
        this.commentaireAvis = commentaireAvis;
        this.noteAvis = noteAvis;
        this.dateAvis = dateAvis;
    }

    public Avis(int idLivre, String nomLivre, int idUser, String commentaireAvis, String noteAvis, String dateAvis) {
        this.idAvis = 0;
        this.idLivre = idLivre;
        this.nomLivre = nomLivre;
        this.idUser = idUser;
        this.commentaireAvis = commentaireAvis;
        this.noteAvis = noteAvis;
        this.dateAvis = dateAvis;
    }

    public int getIdAvis() {
        return idAvis;
    }
    public void setIdAvis(int idAvis) {
        this.idAvis = idAvis;
    }

    public int getIdLivre() {
        return idLivre;
    }
    public void setIdLivre(int idLivre) {
        this.idLivre = idLivre;
    }

    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNomLivre() {
        return nomLivre;
    }
    public void setNomLivre(String nomLivre) {
        this.nomLivre = nomLivre;
    }

    public String getCommentaireAvis() {
        return commentaireAvis;
    }
    public void setCommentaireAvis(String commentaireAvis) {
        this.commentaireAvis = commentaireAvis;
    }

    public String getNoteAvis() {
        return noteAvis;
    }
    public void setNoteAvis(String noteAvis) {
        this.noteAvis = noteAvis;
    }

    public String getDateAvis() {
        return dateAvis;
    }
    public void setDateAvis(String dateAvis) {
        this.dateAvis = dateAvis;
    }
}
