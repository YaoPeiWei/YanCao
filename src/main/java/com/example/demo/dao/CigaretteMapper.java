package com.example.demo.dao;

import com.example.demo.pojo.Cigarette;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CigaretteMapper {
    @Select("select * from cigarette limit #{index},#{size}")
    public List<Cigarette> SelectCigarette(@Param("index")int index ,@Param("size") int size);
    @Select("select count(*) from cigarette")
    public int getCount();
    @Select("select * from Cigarette")
    List<Cigarette> findCigarette();
    @Insert("insert into cigarette (account,name,surplus,img,cumulative,price)  values (#{account},#{name},#{surplus},#{img},#{cumulative},#{price})")
    public int saveCigarette(Cigarette cigarette);
    @Update("update cigarette set name=#{name},surplus=#{surplus},img=#{img},cumulative=#{cumulative},price=#{price} where account=#{account}")
    public int modifyCigarette(Cigarette cigarette);
    @Select("select * from  cigarette where account= #{account} ")
    public Cigarette get(String account);
    @Delete("delete from cigarette where account=#{account}")
    public void deleteByAccount(String account);
    @Update("UPDATE yancao.`cigarette` SET surplus = (SELECT yancao.`cigarette`.`surplus` WHERE yancao.`cigarette`.`account`=#{account})-#{surplus}" +
            " WHERE yancao.`cigarette`.`account`=#{account}")
    public int UpdateCigaretteSurplus(@Param("surplus") int surplus,@Param("account") String account);
}
