package com.example.countriesapp.model;

import com.example.countriesapp.di.DaggerApiComponent;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;
import io.reactivex.Single;

public class CountriesService {

    private static CountriesService instance;

    @Inject
    public CountriesAPI api;

    private CountriesService(){
        DaggerApiComponent.create().inject(this);
    }

    public static CountriesService getInstance(){
        if(instance==null){
            instance = new CountriesService();
        }
        return instance;
    }

    public Single<List<CountryModel>> getCountries(){
        return api.getCountries();
    }
}
