package org.mira.companion.Models;

/**
 * Created by @astonbraham on 24/05/2018.
 */

public class Downloadable {

    private int status;
    private int number_of_downloads;
    private String thumbnail;
    private String unique_id;
    private String name;
    private String description;
    private String file_size;
    private int category;
    private String version;
    private String author;
    private String download_link;
    private long release_timestamp;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNumber_of_downloads() {
        return number_of_downloads;
    }

    public void setNumber_of_downloads(int number_of_downloads) {
        this.number_of_downloads = number_of_downloads;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDownload_link() {
        return download_link;
    }

    public void setDownload_link(String download_link) {
        this.download_link = download_link;
    }

    public long getRelease_timestamp() {
        return release_timestamp;
    }

    public void setRelease_timestamp(long release_timestamp) {
        this.release_timestamp = release_timestamp;
    }

    @Override
    public String toString() {
        return "Downloadable{" +
                "status=" + status +
                ", number_of_downloads=" + number_of_downloads +
                ", thumbnail='" + thumbnail + '\'' +
                ", unique_id='" + unique_id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", file_size='" + file_size + '\'' +
                ", category=" + category +
                ", version='" + version + '\'' +
                ", author='" + author + '\'' +
                ", download_link='" + download_link + '\'' +
                ", release_timestamp=" + release_timestamp +
                '}';
    }
}
