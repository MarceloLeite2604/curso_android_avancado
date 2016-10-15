package br.com.targettrust.aula5exercicio2.model;

/**
 * Created by Jackson on 22/10/2015.
 */
public class Movie {
    private String budget;
    private String voteAverage;
    private String backdropPath;
    private Genres[] genres;
    private String status;
    private String runtime;
    private SpokenLanguages[] spokenLanguages;
    private String adult;
    private String homepage;
    private String id;
    private Production_countries[] productionCountries;
    private String title;
    private String original_language;
    private String overview;
    private ProductionCompanies[] productionCompanies;
    private boolean belongsToCollection;
    private String imdbId;
    private String releaseDate;
    private String originalTitle;
    private String voteCount;
    private String posterPath;
    private String video;
    private String tagline;
    private String revenue;
    private String popularity;

    public Movie(String id, String title, String posterPath) {
        this.title = title;
        this.posterPath = posterPath;
        this.id = id;
    }

    public String getBuget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Genres[] getGenres() {
        return genres;
    }

    public void setGenres(Genres[] genres) {
        this.genres = genres;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public SpokenLanguages[] getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(SpokenLanguages[] spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Production_countries[] getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(Production_countries[] productionCountries) {
        this.productionCountries = productionCountries;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public ProductionCompanies[] getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(ProductionCompanies[] productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public boolean getBelongsToCollection() {
        return belongsToCollection;
    }

    public void setBelongsToCollection(boolean belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    @Override
    public String toString() {
        return "ClassPojo [budget = " + budget + ", voteAverage = " + voteAverage + ", backdropPath = " + backdropPath + ", genres = " + genres + ", status = " + status + ", runtime = " + runtime + ", spokenLanguages = " + spokenLanguages + ", adult = " + adult + ", homepage = " + homepage + ", id = " + id + ", productionCountries = " + productionCountries + ", title = " + title + ", original_language = " + original_language + ", overview = " + overview + ", productionCompanies = " + productionCompanies + ", belongsToCollection = " + belongsToCollection + ", imdbId = " + imdbId + ", releaseDate = " + releaseDate + ", originalTitle = " + originalTitle + ", voteCount = " + voteCount + ", posterPath = " + posterPath + ", video = " + video + ", tagline = " + tagline + ", revenue = " + revenue + ", popularity = " + popularity + "]";
    }
}
