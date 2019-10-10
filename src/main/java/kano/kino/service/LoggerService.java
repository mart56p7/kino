package kano.kino.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;

/**
 * Logger Service.
 * The class is made for collecting all output into a single place, so the output can be directed to where we want it. By default the output is directed to the System.out.println
 * */

@Service("LoggerService")
public class LoggerService {
    public final static int CONTROLLER = 1;
    public final static int CONTROLLER_MSG = 2;
    public final static int SERVICE = 3;
    public final static int SERVICE_MSG = 4;
    public final static int REPOSITORY = 5;
    public final static int REPOSITORY_MSG = 6;
    public final static int MODEL = 7;
    public final static int MODEL_MSG = 8;

    @Value("${kino.debug}")
    private boolean debug;

    private String[] indarray;

    private String classname = "";

    @Autowired
    public LoggerService(){
        indarray = new String[15];
        for(int i = 0; i < indarray.length; i++){
            String str = "";
            for(int j = 0; j < i; j++){
                str += " ";
            }
            indarray[i] = str;
        }
    }

    public void setClassName(String str){
        this.classname = str + " > ";
    }

    public void log(String method, String msg){
        log(method, msg, 0);
    }

    public void log(String method, Exception e){
        log(method, e, 0);
    }

    public void log(String method, Exception e, int indent){ log(method, e.getClass().toString(), e.getMessage(), indent); }

    public void log(String method, Exception e, String username){
        log(method, e, username, 0);
    }

    public void log(String method, Exception e, String username, int indent){ log(username + " > " + method, e.getClass().toString(), e.getMessage(), indent); }

    public void log(String method, String type, String msg){
        log(method, type, msg, 0);
    }

    public void log(String method, String type, String msg, int indent){
        log(method, type + ": " + msg, indent);
    }

    public void log(String method, String type, String msg, String username){
        log(method, type, msg, username, 0);
    }

    public void log(String method, String type, String msg, String username, int indent){ log(username + " > " + method, type, msg, indent); }

    public void log(String method, String msg, int indent) {
        log(method + " > " + msg, indent);
    }

    public void log(String s, int indent){
        log(indarray[indent] + this.classname + s);
    }

    public void log(String s){
        if(debug){
            System.out.println(Instant.now().getEpochSecond() + " " + s);
        }

    }
}
