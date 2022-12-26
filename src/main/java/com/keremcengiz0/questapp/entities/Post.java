package com.keremcengiz0.questapp.entities;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "post")
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER) // LAZY = DB'den Post objesini çektiğimde user'i getirme.
    @JoinColumn(name= "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)  //Bir user silindiğinde tüm postları da silinir.
    private User user;

    private String title;

    @Lob
    @Column(columnDefinition = "text")
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
}
