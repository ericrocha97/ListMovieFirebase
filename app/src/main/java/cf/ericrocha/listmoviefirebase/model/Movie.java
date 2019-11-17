package cf.ericrocha.listmoviefirebase.model;

public class Movie {
    private String titleMovie;
    //private String genre;
    private String year;

    public Movie(){
    }

    public Movie(String titleMovie,  String year) {
        this.titleMovie = titleMovie;
        this.year = year;
    }

    public String getTitleMovie() {
        return titleMovie;
    }

    public void setTitleMovie(String titleMovie) {
        this.titleMovie = titleMovie;
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
