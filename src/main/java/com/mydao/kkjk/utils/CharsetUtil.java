package com.mydao.kkjk.utils;

import java.io.UnsupportedEncodingException;

/**
 * @program: kkjk
 * @description: 字符编码处理
 * @author: Eyki
 * @create: 2019-02-22 17:17
 **/
public class CharsetUtil {
    //UTF-8->GB2312
    public static String utf8Togb2312(String str){

        StringBuffer sb = new StringBuffer();

        for ( int i=0; i<str.length(); i++) {

            char c = str.charAt(i);
            switch (c) {
                case '+' :
                    sb.append( ' ' );
                    break ;
                case '%' :
                    try {
                        sb.append(( char )Integer.parseInt (
                                str.substring(i+1,i+3),16));
                    }
                    catch (NumberFormatException e) {
                        throw new IllegalArgumentException();
                    }

                    i += 2;

                    break ;

                default :

                    sb.append(c);

                    break ;

            }

        }

        String result = sb.toString();

        String res= null ;

        try {

            byte [] inputBytes = result.getBytes( "utf-8" );

            res= new String(inputBytes, "GB2312" );

        }

        catch (Exception e){}

        return res;

    }

}
