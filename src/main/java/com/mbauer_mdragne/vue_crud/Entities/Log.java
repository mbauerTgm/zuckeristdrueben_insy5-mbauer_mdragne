package com.mbauer_mdragne.vue_crud.Entities;

import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "log", schema = "venlab")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    private String level;
    
    private String info;

    @Column(name = "s_id")
    private String sId;

    @Column(name = "s_stamp")
    private Timestamp sStamp;

    @Column(name = "a_id")
    private Long aId;

    @Column(name = "date_exported")
    private Timestamp dateExported;
}