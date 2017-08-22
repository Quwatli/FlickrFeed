package li.quwat.flickrfeed;

/**
 * Created by a. z. quwatli on 8/22/2017.
 */

//Due to mbile device storage limits, this object actually stores the data used to get the photo/image rather the image itself, which would be counter intuitive
    //this class is used to store the information parsed from the downloaded feed, in this case via processing the json feed file

public class Photo {
    private String Title;
    private String Author;
    private String AuthorID;
    private String Link;
    private String Tags;
    private String Image;

    public Photo(String title, String author, String authorID, String link, String tags, String image) {
        Title = title;
        Author = author;
        AuthorID = authorID;
        Link = link;
        Tags = tags;
        Image = image;
    }

    void setTitle(String title) {
        Title = title;
    }

    void setAuthor(String author) {
        Author = author;
    }

    void setAuthorID(String authorID) {
        AuthorID = authorID;
    }

    void setLink(String link) {
        Link = link;
    }

    void setTags(String tags) {
        Tags = tags;
    }

    void setImage(String image) {
        Image = image;
    }

    String getTitle() {
        return Title;
    }

    String getAuthor() {
        return Author;
    }

    String getAuthorID() {
        return AuthorID;
    }

    String getLink() {
        return Link;
    }

    String getTags() {
        return Tags;
    }

    String getImage() {
        return Image;
    }

    @Override
    public String toString() {
        return "Photo " +
                "Title: " + Title + '\n' +
                ", Author: " + Author + '\n' +
                ", AuthorID: " + AuthorID + '\n' +
                ", Link: " + Link + '\n' +
                ", Tags: " + Tags + '\n' +
                ", Image: " + Image + '\n';
    }
}
