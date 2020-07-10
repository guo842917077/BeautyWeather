package com.crazyorange.beauty.database.user;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


/**
 * @author guojinlong
 */
@Dao
public interface UserDao {
    /**
     * 查询当前用户是否存在
     *
     * @param username 用户名
     * @param password 密码
     * @return 数量
     */
    @Query("SELECT COUNT(*) FROM user WHERE username =:username and password =:password")
    int queryUser(String username, String password);

    @Query("SELECT COUNT(*) FROM user WHERE username =:username")
    int queryUserByName(String username);

    /**
     * 插入用户
     * INSERT INTO user (uid,username,password,date) VALUES (.....)
     *
     * @param entity
     */
    @Insert
    void insertUser(UserEntity entity);
}
