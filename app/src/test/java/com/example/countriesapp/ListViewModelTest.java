package com.example.countriesapp;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.countriesapp.model.CountriesService;
import com.example.countriesapp.model.CountryModel;
import com.example.countriesapp.viewmodel.ListViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public class ListViewModelTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    CountriesService countriesService;

    @InjectMocks
    ListViewModel listViewModel = new ListViewModel();

    private Single<List<CountryModel>> testSingle;

    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void  getCountriesSuccess(){
        CountryModel country = new CountryModel("countryName","capital","flag");
        ArrayList<CountryModel> countriesList = new ArrayList<>();
        countriesList.add(country);

        testSingle = Single.just(countriesList);

        Mockito.when(countriesService.getCountries()).thenReturn(testSingle);

        listViewModel.refresh();

        Assert.assertEquals(1,listViewModel.countries.getValue().size());
        Assert.assertEquals(false,listViewModel.countryLoadError.getValue());
        Assert.assertEquals(false,listViewModel.loading.getValue());

    }

    @Test
    public void getCountriesFail(){

        testSingle = Single.error(new Throwable());
        Mockito.when(countriesService.getCountries()).thenReturn(testSingle);
        Assert.assertEquals(true,listViewModel.countryLoadError.getValue());
        Assert.assertEquals(true,listViewModel.loading.getValue());


    }

    @Before
    public void setupRxSchedulers(){
        Scheduler immediate = new Scheduler() {
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(runnable ->{ runnable.run();},true);
            }
        };

        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedular -> immediate);
    }

}
