package gr.hubit.library;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Book implements Parcelable {
    private String code;
    private String title;
    private String author;
    private String publicationDate;
    private String placeOfPublication;
    private String publisher;
    private String ownershipDetails;
    private String description;
    private String illustrations;
    private String briefDescription;
    private String comment;
    private String bookDescription;
    private int image;
    private HashMap<Integer, ArrayList<Integer>> images;

    public Book(){

    }

    protected Book(Parcel in) {
        code = in.readString();
        title = in.readString();
        author = in.readString();
        publicationDate = in.readString();
        placeOfPublication = in.readString();
        publisher = in.readString();
        ownershipDetails = in.readString();
        description = in.readString();
        illustrations = in.readString();
        briefDescription = in.readString();
        comment = in.readString();
        bookDescription = in.readString();
        image = in.readInt();
        images = (HashMap<Integer, ArrayList<Integer>>) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(publicationDate);
        dest.writeString(placeOfPublication);
        dest.writeString(publisher);
        dest.writeString(ownershipDetails);
        dest.writeString(description);
        dest.writeString(illustrations);
        dest.writeString(briefDescription);
        dest.writeString(comment);
        dest.writeString(bookDescription);
        dest.writeInt(image);
        dest.writeSerializable(images);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getPlaceOfPublication() {
        return placeOfPublication;
    }

    public void setPlaceOfPublication(String placeOfPublication) {
        this.placeOfPublication = placeOfPublication;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getOwnershipDetails() {
        return ownershipDetails;
    }

    public void setOwnershipDetails(String ownershipDetails) {
        this.ownershipDetails = ownershipDetails;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIllustrations() {
        return illustrations;
    }

    public void setIllustrations(String illustrations) {
        this.illustrations = illustrations;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public HashMap<Integer, ArrayList<Integer>> getImages(){return images;}

    public void setimages(HashMap<Integer, ArrayList<Integer>> images){
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(getCode(), book.getCode()) &&
                Objects.equals(getTitle(), book.getTitle()) &&
                Objects.equals(getAuthor(), book.getAuthor()) &&
                Objects.equals(getPublicationDate(), book.getPublicationDate()) &&
                Objects.equals(getPlaceOfPublication(), book.getPlaceOfPublication()) &&
                Objects.equals(getPublisher(), book.getPublisher()) &&
                Objects.equals(getOwnershipDetails(), book.getOwnershipDetails()) &&
                Objects.equals(getDescription(), book.getDescription()) &&
                Objects.equals(getIllustrations(), book.getIllustrations()) &&
                Objects.equals(getBriefDescription(), book.getBriefDescription()) &&
                Objects.equals(getComment(), book.getComment()) &&
                Objects.equals(getBookDescription(), book.getBookDescription());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCode(), getTitle(), getAuthor(), getPublicationDate(), getPlaceOfPublication(), getPublisher(), getOwnershipDetails(), getDescription(), getIllustrations(), getBriefDescription(), getComment(), getBookDescription());
    }

    @Override
    public String toString() {
        return "Book{" +
                "code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
