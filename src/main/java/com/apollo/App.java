package com.apollo;

import com.apple.eawt.Application;
import com.blade.Blade;
import com.blade.security.web.xss.XssMiddleware;

/**
 * Hello world!
 */
public class App {

    static String content = "empty";

    public static void main(String[] args) {
        System.out.println("Start Blade!");

        Blade.of()
                // 反射型
                .get("/reflect", ctx -> ctx.html(ctx.fromString("x")))
                // 存储型
                .get("/a", ctx -> ctx.render("a.html"))
                .get("/b", ctx -> {
                    System.out.println(content);
                    ctx.attribute("content", content);
                    ctx.render("b.html");
                })
                // DOM-Base型
                .get("/domxss", ctx -> ctx.render("domxss.html"))
                .post("/submit", ctx -> {
                    content = ctx.fromString("content");
                    ctx.redirect("/b");
                })
                // 防御
                .use(new XssMiddleware())
                .start(Application.class, args)
                ;

    }
}
