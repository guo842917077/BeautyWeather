package com.crazyorange.beauty.database.convert;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * @author guojinlong
 * <p>
 * 类型转化器
 */
public class TypeConvert {
    @TypeConverter
    public Date convertDate(long value) {
        return new Date(value);
    }

    @TypeConverter
    public long convertLong(Date date) {
        return date.getTime();
    }
}
