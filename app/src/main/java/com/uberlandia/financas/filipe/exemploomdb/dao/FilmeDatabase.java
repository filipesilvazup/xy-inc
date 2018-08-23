package com.uberlandia.financas.filipe.exemploomdb.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.uberlandia.financas.filipe.exemploomdb.FilmeSelecionado;

@Database(entities = {FilmeSelecionado.class}, version = 1, exportSchema = false)
public abstract class FilmeDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();

    private static FilmeDatabase filmeDatabase;

    public static FilmeDatabase getInstance(Context context) {
        if (filmeDatabase == null) {
            filmeDatabase = Room.databaseBuilder(context.getApplicationContext(), FilmeDatabase.class, "db_filme")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return filmeDatabase;
    }

}

