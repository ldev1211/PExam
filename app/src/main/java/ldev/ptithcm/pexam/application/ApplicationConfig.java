package ldev.ptithcm.pexam.application;

import android.app.Application;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class ApplicationConfig extends Application {
    public static int numAnswered = 0;
    public static String NAME_SQLITE = "PExamSQLite.sqlite";
    public static String baseURLPExamServer = "http://apildevmobile.com";
    public static Socket mSocket;
    static {
        try {
            mSocket = IO.socket(baseURLPExamServer);
        } catch (URISyntaxException ignored) {
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mSocket.connect();
    }
}
