package wejump.server.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import wejump.server.domain.assignment.Submit;
import wejump.server.domain.course.Course;
import wejump.server.domain.course.EnrollCourse;
import wejump.server.domain.lesson.Attend;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
@Getter
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nickname", nullable = true, unique = true)
    private String nickname;

    @Column(name = "profile_image", nullable = true)
    private String image;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EnrollCourse> enrolledCourses = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Submit> submits = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attend> attends = new ArrayList<>();

    @OneToMany(mappedBy = "instructor")
    private List<Course> instructedCourses = new ArrayList<>();

    @Builder
    public Member(Long id, String name, String email, String nickname, String image, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.image = image;
        this.role = role;
    }

    public Member update(String name, String email) {
        this.name = name;
        this.email = email;
        return this;
    }
}