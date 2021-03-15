package com.sudrives.sudrivespartner.networks;

import android.content.Context;
import android.util.Log;


import com.sudrives.sudrivespartner.utils.AppConstants;

import java.net.URISyntaxException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import okhttp3.OkHttpClient;

/**
 * Created by krishnapal on 9/19/17.
 */

public class SocketConnection {
    private static final SocketConnection ourInstance = new SocketConnection();
    private static Socket socketInstance;
    private static android.os.Handler handler = new android.os.Handler();
    private static SocketStatusCallBacks socketStatusCallBacks;

    private static Context context;


    public static SocketConnection getInstance(Context mContext, SocketStatusCallBacks socketCallBacks) {
        context = mContext;
        Socket socket;
        try {
            socket = IO.socket("http://172.31.43.67:3000");
            socket.connect();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("testq12",e.toString());
        }
        socketStatusCallBacks = (SocketStatusCallBacks) mContext;
        if (socketInstance == null) {
            try {

                HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                };
                TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {

                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {

                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[0];
                    }
                }};
                X509TrustManager trustManager = (X509TrustManager) trustAllCerts[0];

                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .hostnameVerifier(hostnameVerifier)
                        .sslSocketFactory(sslSocketFactory, trustManager)
                        .build();


                IO.Options opts = new IO.Options();

                opts.callFactory = okHttpClient;
                opts.webSocketFactory = okHttpClient;
                Log.e("socketurl",AppConstants.BASE_SOCKET_URL);
                socketInstance = IO.socket(AppConstants.BASE_SOCKET_URL, opts); //Change on 16-01-2018 because in Android version Orio or Above in Background socket is disconnect


                connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ourInstance;
    }

    public SocketConnection() {
    }

    public static void connect() {


        socketInstance.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.e("Connected", "Connected");
                socketStatusCallBacks.onSocketConnected();


                //socketInstance.emit(AppConstants.KEY_ON_CONNECTION_EMIT, AppConstants.USER_ID);


            }

        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.e("Disconnected", "Disconnected");
                socketStatusCallBacks.onSocketDisconnected();
            }

        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                socketStatusCallBacks.onSocketConnectionError();
                Log.e("Connection Error", "Error in Connection " + args[0]);
            }
        }).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                socketStatusCallBacks.onSocketConnectionTimeout();
                Log.e("Connection Timeout", "Connection Time Out");
            }
        }).on(Socket.EVENT_RECONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                socketStatusCallBacks.onSocketReconnected();
                Log.e("Connection Reconnect", "Reconnected");
            }
        });

        socketInstance.connect();
    }

    public static boolean isConnected() {
        return socketInstance.connected();
    }

    public static void disconnect() {

        if (socketInstance != null)
            if (socketInstance.connected()) {
                socketInstance.disconnect();
                socketInstance = null;
            }
    }

    public static Socket getSocket() {

        return socketInstance;
    }


    public static void attachListener(String nodeName, Emitter.Listener listener) {

        if (!isListenerEnabled(nodeName)) {
            socketInstance.on(nodeName, listener);
        }

       /* if(nodeName.equalsIgnoreCase(AppConstants.EVENT_STATUS)){
            socketInstance.on(nodeName, listener);
            return;
        }else{
            if (!isListenerEnabled(nodeName)) {
                socketInstance.on(nodeName, listener);
            }
        }*/


    }


    public static void detachListener(String nodeName, Emitter.Listener listener) {
        Log.e("Connection Status", "" + isConnected());
        if (isListenerEnabled(nodeName)) {
            socketInstance.off(nodeName, listener);
        }

    }

    public static void attachSingleEventListener(String nodeName, Emitter.Listener listener) {
        socketInstance.once(nodeName, listener);
    }

    public static void removeListener(String nodeName, Emitter.Listener listener) {
        if (isListenerEnabled(nodeName)) {
            socketInstance.off(nodeName, listener);
        }
    }

    public static boolean isListenerEnabled(String nodeName) {
        return socketInstance.hasListeners(nodeName);
    }


    public static void emitToServer(String event, Object object) {
        socketInstance.emit(event, object);
    }

    public static void emitToServer(String event, Object object, Object object2) {
        socketInstance.emit(event, object, object2);
    }

    public static void emitToServer(String event, Object object, Object object2, Object object3) {
        socketInstance.emit(event, object, object2, object3);
    }

    public static void emitToServer(String event, Object object, Object object2, Object object3, Object object4) {
        socketInstance.emit(event, object, object2, object3, object4);
    }


    public static void emitToServer(String event, Object object, Object object2, Object object3, Object object4, Object object5) {
        socketInstance.emit(event, object, object2, object3, object4, object5);
    }


    public static void emitToServer(String event, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        socketInstance.emit(event, object, object2, object3, object4, object5, object6);
    }


    public static void emitToServer(String event, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        socketInstance.emit(event, object, object2, object3, object4, object5, object6, object7, object8);
    }

    public static void emitToServer(String event, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12) {
        socketInstance.emit(event, object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12);
    }


    public interface SocketStatusCallBacks {
        public void onSocketConnected();

        public void onSocketDisconnected();

        public void onSocketReconnected();

        public void onSocketConnectionError();

        public void onSocketConnectionTimeout();
    }


}
