package com.karmanno.txexample;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class SomeEntity {

    @Id
    private String id;

    private LocalDateTime time;

}
