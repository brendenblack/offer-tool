package com.kixeye.analytics.offertool.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
//@Table(name = "users")
@ToString
public class User
{
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userid;

//    @Column(name = "persona_id")
    private String personaId;

//    @Column(name = "player_id")
    private String playerId;

    private Long fbid;

    private String kxid;

//    @Column(name = "addtime")
    private Long addTime;

//    @Column(name = "remtime")
    private Long remTime;

//    @Column(name = "seentime")
    private Long seenTime;

    private Integer credits;

//    @Column(name = "first_name")
    private String firstName;

//    @Column(name = "last_name")
    private String lastName;

    private String pic;

//    @Column(name = "pic_square")
    private String picSquare;

//    @Column(name = "profile_url")
    private String profileUrl;

    private String email;

    private String proxymail;
}
