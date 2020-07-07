package com.crazyorange.beauty.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.crazyorange.beauty.database.convert.TypeConvert;
import com.crazyorange.beauty.database.user.UserDao;
import com.crazyorange.beauty.database.user.UserEntity;

/**
 * @author guojinlong
 * 数据库的控制类
 */
@Database(entities = {UserEntity.class}, version = 1, exportSchema = false)
@TypeConverters(TypeConvert.class)
public abstract class BeautyDBManager extends RoomDatabase {
    private static final String DataBaseName = "beauty-weather.db";
    private static volatile BeautyDBManager instance;

    public static BeautyDBManager instance(Context application) {
        if (instance == null) {
            synchronized (BeautyDBManager.class) {
                if (instance == null) {
                    instance = create(application.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private static BeautyDBManager create(final Context context) {
        return Room.databaseBuilder(
                context,
                BeautyDBManager.class,
                DataBaseName)
                .build();
    }

    public abstract UserDao getUserDao();
}
