package hello.service;

import hello.dao.BlogDao;
import hello.entity.Blog;
import hello.entity.BlogResult;
import hello.entity.Result;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BlogService {
    private BlogDao blogDao;

    @Inject
    public BlogService(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    public Result getBlogs(Integer page, Integer pageSize, Integer userId) {
        try{
            List<Blog> list = blogDao.getBlogs(page, pageSize, userId);
            int count = blogDao.count(userId);

            int pageCount = count % pageSize == 0 ? count / pageSize : ((count / pageSize) + 1);
            return BlogResult.newResults(list, count, page, pageCount);
        } catch (Exception e){
            return BlogResult.failure("系统异常");
        }
    }

}
