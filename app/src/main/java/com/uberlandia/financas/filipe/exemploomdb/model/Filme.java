package com.uberlandia.financas.filipe.exemploomdb.model;

/**
 * Created by Filipe on 20/08/2018.
 */

public class Filme {


    private String Title;
    private String Year;
    private String imdbID;
    private String Type;
    private String Poster;

    public Filme() {}

    public Filme(String title, String year, String imdbID, String type, String poster) {
        Title = title;
        Year = year;
        this.imdbID = imdbID;
        Type = type;
        Poster = poster;

    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    @Override
    public String toString() {
        return  "Título: " + Title + "\n" +
                "Year: " + Year + "\n" +
                "imdbID: " + imdbID + "\n" +
                "Type: " + Type + "\n";
    }
}
