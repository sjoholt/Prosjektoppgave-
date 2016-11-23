package no.ntnu.projectserver;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Data class holding information about article objects
 * @author Team Tungrocken
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "ARTICLE")

public class Article implements Serializable {
    
    @Id @GeneratedValue
    Long articleId;
    String title;
    String ingress;
    
    @Column(columnDefinition="clob")
    String content;
    String photoUrl;
    String youtubeUrl;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    Date datePosted = new Date();
    
    public Article() {
    }

    public Article(String title, String ingress, String content, String photoUrl, String youtubeUrl) {
        this.title = title;
        this.ingress = ingress;
        this.content = content;
        this.photoUrl = photoUrl;
        this.youtubeUrl = youtubeUrl;
    }
    
    // Following is a standard set of setter and getter methods

    public Long getArticleId() {
        return articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngress() {
        return ingress;
    }

    public void setIngress(String ingress) {
        this.ingress = ingress;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
    
    public Date getDatePosted() {
        return this.datePosted;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    } 

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }
}
