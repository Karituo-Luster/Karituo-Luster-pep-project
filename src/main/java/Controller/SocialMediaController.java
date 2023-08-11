package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
//import Model.Message;
import Service.AccountService;
//import Service.MessageService;



public class SocialMediaController {

    AccountService AS = new AccountService();
    //MessageService MS = new MessageService();

    public Javalin startAPI() {
        Javalin app = Javalin.create()
        .post("/register", this::register);
        app.get("/login", this::login);
        return app;
    }

    private void register(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(ctx.body(), Account.class);
        Account addAcc = AS.registerUser(acc);
        if(addAcc != null){
            ctx.json(mapper.writeValueAsString(addAcc));
        }else
            ctx.status(400);
    }
    private void login(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(ctx.body(), Account.class);
        Account checkCreds = AS.checkCreds(acc);
        if(checkCreds!=null){
            ctx.json(mapper.writeValueAsString(checkCreds));
        }else 
            ctx.status(401);
    }
}