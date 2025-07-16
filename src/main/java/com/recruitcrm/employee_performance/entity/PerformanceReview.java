package com.recruitcrm.employee_performance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="performance_review")
public class PerformanceReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;

    @Column(name="review_date")
    private LocalDate reviewDate;

    @Column(name="score")
    private Long score;

    @Column(name="review_comments")
    private String reviewComments;

}
