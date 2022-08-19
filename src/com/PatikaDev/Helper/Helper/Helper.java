package com.PatikaDev.Helper.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static void setLayout(){
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            if ("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

    }

    public static int screenCenterPoint(String eksen,Dimension size){
        int point = 0;
        switch (eksen){
            case "x":
                point = (Toolkit.getDefaultToolkit().getScreenSize().width-size.width)/2;
                break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height-size.height)/2;
                break;
            default:
                point = 0 ;
        }
        return point;
    }


    public static void showMsg(String str){
        String msg;
        switch (str){
            case "error":
                msg = "Bir Hata Olustu";
                break;
            case "done":
                msg = "İşlem Basarıyla gerceklestirildi";
                break;
            case "fill":
                msg = "Tüm Alanları Doldurunuz";
                break;
            default:
                msg = str;

        }
        JOptionPane.showMessageDialog(null,msg,"Hata",JOptionPane.INFORMATION_MESSAGE);

    }




}
