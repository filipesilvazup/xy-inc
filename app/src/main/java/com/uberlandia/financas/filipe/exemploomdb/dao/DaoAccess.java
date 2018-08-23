package com.uberlandia.financas.filipe.exemploomdb.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.uberlandia.financas.filipe.exemploomdb.FilmeSelecionado;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DaoAccess {
    @Insert
    void insertFilme(FilmeSelecionado filme);

    @Insert
    void insertFilmes(ArrayList<FilmeSelecionado> moviesList);

    @Query("SELECT * FROM FilmeSelecionado WHERE imdbID =:imdbID")
    FilmeSelecionado findFilmeById(String imdbID);

    @Query("SELECT * FROM FilmeSelecionado")
    List<FilmeSelecionado> findAll();

    @Query("SELECT * FROM FilmeSelecionado WHERE Title LIKE :titulo")
    List<FilmeSelecionado> findFilmeByName(String titulo);

    @Update
    void updateFilme(FilmeSelecionado movies);

    @Delete
    void deleteFilme(FilmeSelecionado movies);
}
