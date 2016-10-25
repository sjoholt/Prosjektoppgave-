/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.projectserver;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Demyx-Laptop
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
    String content;
    String photoUrl;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    Date datePosted = new Date();
    
    public Article() {
    }

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
