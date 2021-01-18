package com.example.countriesapp.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface CountriesAPI {

    @GET("DevTides/countries/master/countriesV2.json")
    Single<List<CountryModel>> getCountries();

}
