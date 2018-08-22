package com.uberlandia.financas.filipe.exemploomdb.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.uberlandia.financas.filipe.exemploomdb.FilmeSelecionado;

@Database(entities = {FilmeSelecionado.class}, version = 1, exportSchema = false)
public abstract class FilmeDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();
}

