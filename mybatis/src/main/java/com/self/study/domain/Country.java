package com.self.study.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Country  implements Serializable {

    private int    id;
    private String countryname;
    private String countrycode;


}
