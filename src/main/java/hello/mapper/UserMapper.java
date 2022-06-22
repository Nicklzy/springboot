package hello.mapper;

import hello.service.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("SELECT * FROM User WHERE id = #{id}")
    User findUserById(@Param("id") Integer id);
}
