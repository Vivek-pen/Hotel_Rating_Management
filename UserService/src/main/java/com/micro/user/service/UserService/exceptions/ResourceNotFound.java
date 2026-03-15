package com.micro.user.service.UserService.exceptions;

public class ResourceNotFound extends RuntimeException{

    public ResourceNotFound(){
        super("Resource not found on server!!");
    }

    public ResourceNotFound(String message){
        super(message);
    }

}
