package com.gz.suoyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

public class PinyinUtils {
	public static String hanzi2pinyin(String hanzi) {
		try{
			HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
			format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
			format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			char[] charArray = hanzi.trim().toCharArray();
			String str = "";
			for(int x=0;x<charArray.length;x++) {
				String[] hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(charArray[x],format);
				if(hanyuPinyinStringArray.length > 0) {
					str += hanyuPinyinStringArray[0];
				}
			}

			return str.charAt(0)+"";
			
			
		} catch(Exception e) {
			
		}
		
		return null;
	}

}
