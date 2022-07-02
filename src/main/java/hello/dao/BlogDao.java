package hello.dao;

import hello.entity.Blog;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogDao {
    @Inject
    public BlogDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private final SqlSession sqlSession;

    public List<Blog> getBlogs(Integer page, Integer pageSize, Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("offset", (page - 1) * pageSize);
        params.put("limit", pageSize);
        return sqlSession.selectList("selectBlog", params);
    }

    public Integer count(Integer userId) {
        return sqlSession.selectOne("countBlog", userId);
    }
}
