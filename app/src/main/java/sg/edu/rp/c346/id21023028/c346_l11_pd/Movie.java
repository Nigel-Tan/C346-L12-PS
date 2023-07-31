package sg.edu.rp.c346.id21023028.c346_l11_pd;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Movie implements Serializable {
    private int _id;
    private String title;
    private String genre;
    private int year;
    private String rating;


    public Movie(int _id, String title, String genre, int year, String rating) {
        this._id = _id;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


    public String urlRating() {
        Log.i("url", rating);
        if ("G".equals(rating)) { // G
            return "https://www.imda.gov.sg/-/media/imda/images/content/regulation-licensing-and-consultations/content-standards-and-classification/classification-rating/general-rating.webp";
        } else if ("PG".equals(rating)) { // PG
            return "https://www.imda.gov.sg/-/media/imda/images/content/regulation-licensing-and-consultations/content-standards-and-classification/classification-rating/pg-rating.webp";
        } else if ("PG13".equals(rating)) { // PG13
            return "https://www.imda.gov.sg/-/media/imda/images/content/regulation-licensing-and-consultations/content-standards-and-classification/classification-rating/pg13-rating.webp";
        } else if ("NC16".equals(rating)) { // NC16
            return "https://www.imda.gov.sg/-/media/imda/images/content/regulation-licensing-and-consultations/content-standards-and-classification/classification-rating/nc16-rating.webp";
        } else if ("M18".equals(rating)) { // M18
            return "https://www.imda.gov.sg/-/media/imda/images/content/regulation-licensing-and-consultations/content-standards-and-classification/classification-rating/m18-rating.webp";
        } else { // R21
            return "https://www.imda.gov.sg/-/media/imda/images/content/regulation-licensing-and-consultations/content-standards-and-classification/classification-rating/r21-rating.webp";
        }
    }




}
