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
    public static String urlMon = "http://" + localhost + "/Cafe/ConnectServerCafe/getmonan.php?page=";
    public static String Duongdandanhsachmontheoloai = "http://" + localhost + "/Cafe/ConnectServerCafe/getmonantheoloai.php?page=";
    public static String DuongdanBan = "http://" + localhost + "/Cafe/ConnectServerCafe/getban.php";
    public static String DuongdanCapNhatBan = "http://" + localhost + "/Cafe/ConnectServerCafe/updateban.php";
    public static String DuongdanChitietdonhang = "http://" + localhost + "/Cafe/ConnectServerCafe/chitietdonhang.php";
    public static String DuongdanThemHoaDon = "http://" + localhost + "/Cafe/ConnectServerCafe/themhoadon.php";
}
