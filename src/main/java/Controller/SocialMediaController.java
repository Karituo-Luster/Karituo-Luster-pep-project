package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

public class SocialMediaController {

    AccountService AS = new AccountService();
    MessageService MS = new MessageService();

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        
        return app
        //CREATE
            .post("/register", this::register)
            .post("/login", this::login)
            .post("/messages", this::createMessage)
        //UPDATE
            .patch("/messages/{message_id}", this::updateMessage)
        //READ
            .get("/messages", this::getAllMessages)
            .get("/messages/{message_id}", this::getMessageById)
            .get("/accounts/{account_id}/messages", this::getMessagesByAccId)
        //DELETE
            .delete("/messages/{message_id}", this::deleteMessage);
    }

//HANDLING METHODS

    //CREATE
    private void register(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account acc = om.readValue(ctx.body(), Account.class);
        Account addAcc = AS.registerUser(acc);
        if(addAcc != null)
            ctx.json(om.writeValueAsString(addAcc));
        else
            ctx.status(400);
    }
    private void login(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account acc = om.readValue(ctx.body(), Account.class);
        Account logAcc = AS.logIn(acc.getUsername(), acc.getPassword());
        if(logAcc != null)
            ctx.json(om.writeValueAsString(logAcc));
        else
            ctx.status(401);
    }
    private void createMessage(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message msg = om.readValue(ctx.body(), Message.class);
        Message newMsg = MS.createMessage(msg);
        if(newMsg != null)
            ctx.json(om.writeValueAsString(newMsg));
        else
            ctx.status(400);
    }
    //READ
    private void getAllMessages(Context ctx){
        ctx.json(MS.getAllMessages());
    }
    private void getMessageById(Context ctx){
        Message msg = MS.getMessageById(Integer.parseInt(ctx.pathParam("message_id")));
        if(msg != null)
            ctx.json(MS.getMessageById(Integer.parseInt(ctx.pathParam("message_id"))));
    }
    private void getMessagesByAccId(Context ctx){
        int accId = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(MS.getMessagesByAccId(accId));
    }
    //UPDATE
    private void updateMessage(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message msg = om.readValue(ctx.body(), Message.class);
        int msgId = Integer.parseInt(ctx.pathParam("message_id"));
        Message update = MS.updateMessage(msgId, msg);
        if(update != null)
            ctx.json(om.writeValueAsString(update));
        else
            ctx.status(400);            
    }
    //DELETE
    private void deleteMessage(Context ctx){
        Message msg = MS.getMessageById(Integer.parseInt(ctx.pathParam("message_id")));
        if(msg != null)
            ctx.json(msg);
    }
}