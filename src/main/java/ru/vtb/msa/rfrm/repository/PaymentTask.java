package ru.vtb.msa.rfrm.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PaymentTask {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "reward_id", nullable = false)
    private Long rewardId;

    private String paymentTask;
}
