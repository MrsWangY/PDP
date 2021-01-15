package com.pdp.manager.common.utils;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class StringHelpers {
	
	public static String get32UUID(){
		// 创建 GUID 对象
	      UUID uuid = UUID.randomUUID();
	      // 得到对象产生的ID
	      String a = uuid.toString();
	      // 替换 -
	      a = a.replaceAll("-", "");
	      return a;
	}
	
	public static Integer StringToInteger(Object obj){
		if(obj==null){
			return 0;
		}else{
			return Integer.valueOf(obj+"");
		}
	}
	
	/**
	 * 生成新的组合关键词
	 * @param paramStr
	 * @return
	 */
	public static String generateNewKeys(String paramStr) {
		String newKeys = "";	//新的组合分词
		String selectKeys = "";	//从原分词中提取三个字以上的分词
		String [] arr = paramStr.split("\\s+");
		for(int i=0; i<arr.length; i++){
			if(i<arr.length-1){
				if(arr[i]!=null&&arr[i].length()>1
						|| arr[i+1]!=null&&arr[i+1].length()>1){
					String key = arr[i] + arr[i+1];
					newKeys += key + " ";
				}
				
				if(arr[i]!=null&&arr[i].length()>=3){	//从原分词中提取三个字以上的分词
					selectKeys += arr[i] + " ";
				}
			} else {	//判断最后一个分词
				if(arr[i]!=null&&arr[i].length()>=3){
					selectKeys += arr[i] + " ";
				}
			}
		}
		
		return newKeys + selectKeys;
	}
	/**
	 * 
	 * @param str 2a,2,3a,2a
	 * @param regxix ,
	 * @return 2a,2,3a
	 */
	public static String StringArrayToSet(String str,String regxix){
		String [] ary  = str.split(regxix);
    	List ls = Arrays.asList(ary);
    	HashSet set = new HashSet(ls);
    	String temp = Arrays.toString(set.toArray());
    	return temp.substring(1, temp.length()-1);
	}
	
	/**
	 * 判断字符串是否是整数
	 * @param number
	 * @return
	 */
	public static boolean isInteger(String number) {
		boolean isNumber = false;
		if (StringHelpers.isNotEmpty(number)) {
			isNumber = number.matches("^([1-9]\\d*)|(0)$");
		}
		return isNumber;
	}
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		return !(str==null || str.trim().equals("")  || str.trim().equals("null"));
	}
	
	// 将GBK字符转化为ISO码	
	public static String parseGBK(String sIn) {		
		if (sIn == null || sIn.equals(""))			
			return sIn;		
		try {			
			return new String(sIn.getBytes("GBK"), "ISO-8859-1");		
		} catch (UnsupportedEncodingException usex) {			
			return sIn;		
		}	
	}
	/**
	 * 手机号验证
	 * 
	 * @param  str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) { 
		Pattern p = null;
		Matcher m = null;
		boolean b = false; 
		p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches(); 
		return b;
	}
	
	public static void printClassesOfClassLoader(ClassLoader loader) throws Exception{  
        try {  
            Field classesF = ClassLoader.class.getDeclaredField("classes");  
            classesF.setAccessible(true);  
            Vector<Class<?>> classes = (Vector<Class<?>>) classesF.get(loader);  
            
            PrintWriter pw = new PrintWriter( new FileWriter("E:/tttt1.txt") );
            for(Class c : classes) {  
                System.out.println(c);  
                pw.print(c);
            }  
            pw.close();
           
        } catch (NoSuchFieldException e) {  
            e.printStackTrace();  
        } catch (IllegalAccessException e) {  
            e.printStackTrace();  
        }  
    }  
	
	public static void printClassesOfClassLoader1() throws Exception{  
        try {  
            Field classesF = ClassLoader.class.getDeclaredField("classes");  
            classesF.setAccessible(true);  
//            Vector<Class<?>> classes = (Vector<Class<?>>) classesF.get(loader);  
            
            Vector<Class<?>> classes = (Vector<Class<?>>) classesF.get(ClassLoader.getSystemClassLoader());  
            
            PrintWriter pw = new PrintWriter( new FileWriter("E:/tttt1.txt") );
            for(Class c : classes) {  
                System.out.println(c);  
                pw.print(c);
            }  
            pw.close();
           
        } catch (NoSuchFieldException e) {  
            e.printStackTrace();  
        } catch (IllegalAccessException e) {  
            e.printStackTrace();  
        }  
    }  
	public static String strTrim(String tt){
		tt = tt.replaceAll("\\u00A0","") ;
		tt = tt.replaceAll("\\s*", ""); 
//		System.out.println("xxxxx="+tt.trim()+"===");  
		return tt.trim();
	}
	

	public static String FString(String string) {
		return string.replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5（）\\w-{}"+":："+"！!#&.——，|%￥@,。？?“”、]+","  ");
	}
}
