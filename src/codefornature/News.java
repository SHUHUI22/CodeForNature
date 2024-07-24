/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codefornature;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ACER
 */
public class News implements Comparable<News> {
    String title;
    String link;
    LocalDate date;
    
    public News(){};
    
    public News(String title,String link,LocalDate date){
        this.title=title;
        this.link=link;
        this.date=date;
    }
    
    public void setTitle(String title){
        this.title=title;
    }
    
    public String getTitle(){
        return this.title;
    }
    
    public void setLink(String link){
        this.link=link;
    }
    
    public String getLink(){
        return this.link;
    }
    
    public void setDate(LocalDate date){
        this.date=date;
    }
    
    public LocalDate getDate(){
        return this.date;
    }
    
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy", java.util.Locale.ENGLISH);
        return this.getTitle()+"\n    "+this.getLink()+"\n    "+this.getDate().format(formatter)+"\n\n";
    }
  
    @Override
    public int compareTo(News o) {
       return this.getDate().compareTo(o.getDate());
    }
}
