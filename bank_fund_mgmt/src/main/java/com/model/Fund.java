package com.model;

import jakarta.persistence.*;
import org.hibernate.id.IdentityGenerator;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class Fund {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column(name = "sum_amount")
    private BigDecimal sumAmount;

    @Column(name = "expense_ratio")
    private BigDecimal expenseRatio;

    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(BigDecimal sumAmount) {
        this.sumAmount = sumAmount;
    }

    public BigDecimal getExpenseRatio() {
        return expenseRatio;
    }

    public void setExpenseRatio(BigDecimal expenseRatio) {
        this.expenseRatio = expenseRatio;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "Fund{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sumAmount=" + sumAmount +
                ", expenseRatio=" + expenseRatio +
                ", createdAt=" + createdAt +
                ", manager=" + manager +
                '}';
    }
}
