package com.example.popularmoviesapp.database;


import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteMovie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    //For Singleton instanciation
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "movies_db";
    private static AppDatabase sIntance;


    public static AppDatabase getInstance(Context context) {
        if (sIntance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new Database instance");

                sIntance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sIntance;
    }

    public abstract TaskDao taskDao();
}
