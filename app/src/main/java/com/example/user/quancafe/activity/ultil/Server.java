package com.example.user.quancafe.activity.ultil;

/**
 * Created by User on 01/07/2018.
 */

public class Server {
   // http://172.20.10.2:8080/ConnectServerCafe/registation.php
    public static String localhost = "172.20.10.2:8080";
    public static String urlRegistation = "http://"+localhost+"/Cafe/ConnectServerCafe/registation.php";
    public static String urlLogIn = "http://"+localhost+"/Cafe/ConnectServerCafe/logIn.php";
    public static String Duongdanloaimon = "http://" + localhost + "/Cafe/ConnectServerCafe/getloaimon.php";
    public static String Duongdandanhsachmontheoloai = "http://" + localhost + "/Cafe/ConnectServerCafe/getmonantheoloai.php?page=";
}
