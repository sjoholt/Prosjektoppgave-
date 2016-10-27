
package no.ntnu.projectserver;

import java.io.Serializable;
import java.util.Date;
<<<<<<< HEAD
import javax.persistence.Column;
=======
import javax.persistence.CascadeType;
>>>>>>> origin/master
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
    
    @XmlJavaTypeAdapter(User.UserAdapter.class)
    @ManyToOne(optional = false,cascade = CascadeType.PERSIST)
    User owner;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    Date datePosted = new Date();
    
    public Article() {
    }
    
    // Following is a basic set of setters and getters methods

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

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Date getDatePosted() {
        return datePosted;
    }  
}
