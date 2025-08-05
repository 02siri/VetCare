// src/main/java/au/edu/rmit/sept/webapp/model/Home.java
package au.edu.rmit.sept.webapp.models;

public class Home {

    private String title;
    private String headerMessage;
    private String[] productImages;
    private String[] articleImages;

    // Constructor
    public Home(String title, String headerMessage, String[] productImages, String[] articleImages) {
        this.title = title;
        this.headerMessage = headerMessage;
        this.productImages = productImages;
        this.articleImages = articleImages;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeaderMessage() {
        return headerMessage;
    }

    public void setHeaderMessage(String headerMessage) {
        this.headerMessage = headerMessage;
    }

    public String[] getProductImages() {
        return productImages;
    }

    public void setProductImages(String[] productImages) {
        this.productImages = productImages;
    }

    public String[] getArticleImages() {
        return articleImages;
    }

    public void setArticleImages(String[] articleImages) {
        this.articleImages = articleImages;
    }
}
