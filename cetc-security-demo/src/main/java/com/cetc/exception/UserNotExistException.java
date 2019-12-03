package com.cetc.exception;

public class UserNotExistException extends RuntimeException {
    private static final long serialVersionUID = -6538298167019085802L;

    private String id ;

    public UserNotExistException(String id){
        super(id+":用户不存在");
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
