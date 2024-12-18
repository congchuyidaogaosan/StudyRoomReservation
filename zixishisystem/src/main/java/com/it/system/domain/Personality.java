package com.it.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Personality {

    private int id;

    private String EnglishName;
    private String Name;
    private String photo;


}
