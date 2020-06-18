package com.cetc.web.controller;


import com.cetc.commons.entity.User;
//import com.cetc.security.app.social.AppSignInUtils;
import com.fasterxml.jackson.annotation.JsonView;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;
//    @Autowired
//    private AppSignInUtils signInUtils;

    @PostMapping("/regist")
    public void regit(User user, HttpServletRequest request){
       String userId = user.getUserName();
        System.out.println("regist START");
       providerSignInUtils.doPostSignUp(userId,new ServletWebRequest(request));
      //  signInUtils.doPostSignUp(new ServletWebRequest(request),userId);
       System.out.println("regist over");
    }


    //@RequestMapping(value = "/user",method = RequestMethod.GET)
    @GetMapping
    @JsonView(User.UserSimpleView.class)
//    @ApiOperation(value = "用户查询服务器")
    public List<User> query(@RequestParam(required = false) String username){
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }

    //{id:\\d+}正则，表示ID为数字
    //@RequestMapping(value = "/user/{id:\\d+}",method = RequestMethod.GET)
    @GetMapping("/{id}")
    @JsonView(User.UserDeatailView.class)
    public User getInfo(@PathVariable String id){
//        throw new UserNotExistException("user is no  exist！");
        System.out.println("1进入getInfo服务");
        User user = new User();
        user.setUserName("duyang");
        System.out.println("getInfo:"+user.toString()+":"+user.getUserName());
        return user;
    }

    /*
    @Valid 与 User中的 验证注释对应
    如果不添加BindingResult errors 怎前端访问api时，会返回http 400，不能进入方法体
    添加后带着错误信息进入的方法体，并正常返回数据
     */
    @PostMapping
    public User create(@Valid @RequestBody User user, BindingResult errors){
        if(errors.hasErrors()){
            errors.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
        }
        System.out.println(user.getUserName());
        System.out.println(user.getCreated().toString());
        user.setUserId("1");
        return user;
    }

    @PutMapping("/{id}")
    public  User update(@Valid @RequestBody User user, BindingResult errors){
        if(errors.hasErrors()){
            errors.getAllErrors().stream().forEach(error ->
                    {
                        //FieldError fieldError = (FieldError) error;
                        //String message = fieldError.getField() + error.getDefaultMessage();
                        System.out.println(error.getDefaultMessage());
                    }
            );
        }
        System.out.println(user.getUserName());
        System.out.println(user.getCreated().toString());
        System.out.println(user.getUserId());
        return user;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        System.out.println(id);
    }

    @PostMapping
    @RequestMapping("/me")
    public Object getCurrentUser(@AuthenticationPrincipal UserDetails user){
        return  user;
    }
}
