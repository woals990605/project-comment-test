package site.metacoding.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;

    @Column(unique = true, nullable = false, length = 20)
    private String id; // ssar

    @Column(length = 30)
    private String name; // 황재민

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false, length = 30)
    private String email;

    @CreatedDate
    private LocalDateTime createDate;

    @Transient
    private String remember;

}
