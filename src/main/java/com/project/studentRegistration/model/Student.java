package com.project.studentRegistration.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Students")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long studentNumber;

    private String studentName;
}
