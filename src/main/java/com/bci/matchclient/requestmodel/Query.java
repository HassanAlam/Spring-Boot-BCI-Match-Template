package com.bci.matchclient.requestmodel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Query {
    public String sourceCountry;
    public String dateOfBirth;
    public String firstName;
    public String familyName;
    public String streetAddress;
    public String city;
}

