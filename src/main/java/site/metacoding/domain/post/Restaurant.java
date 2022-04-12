package site.metacoding.domain.post;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(length = 300)
    private String thumUrl; // 썸네일
    @Column(length = 300, nullable = false)
    private String name; // 가게 이름
    @Column(length = 300, nullable = false)
    private String address; // 주소
    @Column(length = 30, nullable = false)
    private String telDisplay; // 전화번호
    @Column(length = 1000, nullable = true)
    private String bizhourInfo; // 영업시간
    @Column(length = 300, nullable = false)
    private String homePage; // 홈페이지
    @Column(length = 3000)
    private String menuInfo; // 메뉴
    @Column(length = 30, nullable = false)
    private String x; // 위도
    @Column(length = 30, nullable = false)
    private String y; // 경도
    @Column(length = 100, nullable = false)
    private String category; // 한식;전복요리;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateDate;
}
