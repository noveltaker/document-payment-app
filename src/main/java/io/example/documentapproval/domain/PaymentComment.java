package io.example.documentapproval.domain;

import io.example.documentapproval.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;

@Table
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentComment extends BaseTimeEntity implements Serializable {

    @Transient
    private final Long serialVersionUID = 1L;

    @Id
    @OneToOne
    @JoinColumn(name = "payment_user_id")
    private PaymentUser paymentUser;

    @Column(nullable = false)
    private Boolean signYn;

    @Lob
    private String comment;

    @PrePersist
    @PostLoad
    void onInsert() {
        if (ObjectUtils.isEmpty(this.signYn)) signYn = Boolean.FALSE;
    }

    @Builder
    public PaymentComment(PaymentUser paymentUser, Boolean signYn, String comment) {
        this.paymentUser = paymentUser;
        this.signYn = signYn;
        this.comment = comment;
    }

    public Boolean getSignYn() {
        if (ObjectUtils.isEmpty(this.signYn)) return Boolean.FALSE;
        return signYn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentComment) || Hibernate.getClass(o) != Hibernate.getClass(this)) return false;
        PaymentComment that = (PaymentComment) o;
        return that.getPaymentUser().getId().equals(this.getPaymentUser().getId());
    }

    @Override
    public int hashCode() {
        return paymentUser.hashCode();
    }

}
