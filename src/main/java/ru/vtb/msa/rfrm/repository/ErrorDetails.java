package ru.vtb.msa.rfrm.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ErrorDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code")
    private Integer code;

    @Column(name = "description")
    private String description;

    @OneToMany
    @JoinColumn(name = "error_details")
    private List<TaskStatusHistory> taskStatusHistories;

}
