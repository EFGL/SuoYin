package com.gz.myapplication;

import java.io.UnsupportedEncodingException;


public class MysendMessage {
	/**
	 * 显示屏和说话功能
	 * @param msg  说话的内容
	 * @param type  1显示屏，2说话
	 * @return
	 */
	public static String sayandshowmessage(String msg,int type){
		String mymsg=setmessage(msg, type);
		return mymsg;
	}


	public  static String setmessage(String s,int type){
		try {
			String utf8 = new String(s.getBytes( "UTF-8"));  
			String unicode = new String(utf8.getBytes(),"UTF-8");   
			byte[] b = unicode.getBytes("GBK");
			String str = " ";
			for (int i = 0; i < b.length; i++) {
				Integer I = new Integer(b[i]);
				String strTmp = I.toHexString(b[i]);
				if (strTmp.length() > 2)
					strTmp = strTmp.substring(strTmp.length() - 2);
				str = str + strTmp+" ";
			}
			String parameter1=str.toUpperCase();
			String muxor=setxor(parameter1);
			String mystrlent=getstrlent(parameter1);
			String retrunst="";
			switch (type) {
			case 1:
				retrunst="AA 55 AA 66 01 "+mystrlent+" "+parameter1+muxor;
				break;
			case 2:
				retrunst="AA 55 AA 66 02 "+mystrlent+" "+parameter1+muxor;
				break;
			}
			return retrunst;
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取长度
	 * @param str
	 * @return
	 */
	public static String getstrlent(String str){
		String[] mystr=str.trim().split(" ");
		int strlent=mystr.length;
		String sexlent=Integer.toHexString(strlent).toUpperCase();
		if(sexlent.length()==1){
			sexlent="0"+sexlent;
		}
		return sexlent;
	}

	/**
	 * 异或方法，不需要修改
	 * @param str
	 */
	public static String setxor(String str){
		String[] mystr=str.trim().split(" ");
		int strlent=mystr.length;
		String sexlent=Integer.toHexString(strlent).toUpperCase();
		if(sexlent.length()==1){
			sexlent="0"+sexlent;
		}
		for(int c=0;c<mystr.length;c++){
			sexlent=tohexstring(sexlent, mystr[c]);
		}
		if(sexlent.toUpperCase().length()==1){
			return "0"+sexlent.toUpperCase();
		}
		return sexlent.toUpperCase();
	}
	/**
	 * 异或方法，不需要修改
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static String tohexstring(String c1,String c2){
		long si = Long.parseLong (c1,16);
		long ti = Long.parseLong (c2,16);
		long st = si ^ ti;
		String hex = Long.toHexString (st);
		return hex;
	}
}
