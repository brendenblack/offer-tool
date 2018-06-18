package com.kixeye.analytics.offertool.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "user_offers", schema = "offers")
public class UserOffer
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

//    @Column(name = "offer_id")
//    private Integer offerId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    private Integer status;

    @Column(name = "num_purchase")
    public Integer amountPurchased;

    @Column(name = "last_purchase_time")
    public Long lastPurchaseTime;

    @Column(name = "mod_time")
    public Long modifiedTime;

    @Column(name = "created_time")
    public Long createdTime;

    @Column(name = "end_time")
    public Long endTime;
}
