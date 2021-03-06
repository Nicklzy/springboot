package hello.controller;

import hello.entity.Result;
import hello.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BlogController {
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    private final BlogService blogService;

    @GetMapping("/blog")
    @ResponseBody
    public Result getBlogs(@RequestParam("page") Integer page, @RequestParam(value = "userId", required = false) Integer userId) {
        if (page == null || page < 0) {
            page = 1;
        }
        return blogService.getBlogs(page, 10, userId);
    }
}
