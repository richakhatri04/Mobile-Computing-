package com.thealienobserver.nikhil.travon.models;

import java.util.Date;

public class NewsArticle {
    String title;
    String description;
    String imageUrl;
    String articleUrl;
    String content;
    Date publishedAt;

    public NewsArticle(String title, String description, String imageUrl, String articleUrl, String content, Date publishedAt) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.articleUrl = articleUrl;
        this.content = content;
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description == null? "": description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public String getContent() {
        return content;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }
}
