package com.geetest.gtapp.utils;

import java.util.ArrayList;
import java.util.Arrays;

import com.geetest.gtapp.logger.GtLogger;
import com.geetest.gtappdemo.model.vo.CaptchaUserAction;
import com.geetest.gtappdemo.model.vo.DecodedChallenge;

/**
 * 数据类型转换
 * 
 * @author Zheng 2014年5月15日 上午11:24:07
 */
public class GtDataConvert {

	/**
	 * 将用户的拖动的位置进行编码 2014年5月16日 09:17:23
	 * 
	 * @param xPos
	 *            x偏移量
	 * @param randBase
	 *            随机码
	 * @return
	 */
	/**
	 * @param xPos
	 * @param randBase
	 * @return
	 */
	public static String EnCryptUserResponse(int xPos,
			DecodedChallenge decodedChallenge) {

		try {

			String randBase = decodedChallenge.getRandBase();
			String realChallenge = decodedChallenge.getRealChallenge();

			// TODO 参考ZCX的JS代码
			GtLogger.v("int xPos: " + xPos + " String randBase: " + randBase);

			int[] bValue = new int[randBase.length()];

			for (int i = 0; i < randBase.length(); i++) {
				int tempStr = (int) randBase.charAt(i);// 获取Ascii值
				bValue[i] = tempStr > 57 ? tempStr - 87 : tempStr - 48;
				GtLogger.v(tempStr + "");
			}

			int answerBase = bValue[0] * 36 + bValue[1];

			int response = xPos + answerBase;

			// 获取一张映射表---根据字母第一次出现的顺序对字符进行映射编码
			// 字符去重

			// String nonRepeatChallenge = realChallenge.replaceAll(
			// "(?s)(.)(?=.*\\1)", "");//
			// 正则表达式去重字符：http://peterpengyc.iteye.com/blog/1534558

			String nonRepeatChallenge = rmRepeated(realChallenge);

			GtLogger.v("realC" + "hallenge: " + realChallenge);
			GtLogger.v("nonRepeatChallenge: " + nonRepeatChallenge
					+ "  length: " + nonRepeatChallenge.length());

			// 按照序列进行编码--以5为周期进行排布
			ArrayList<ArrayList<Character>> charValueMap = getCharValueMap(nonRepeatChallenge);

			int num = response;
			int ran2, rest = 4;
			String finalResponse = "";

			@SuppressWarnings("unchecked")
			ArrayList<Integer> cash = new ArrayList(Arrays.asList(1, 2, 5, 10,
					50));// 用于字符替换的编码方式
			// int cash[] = { 1, 2, 5, 10, 50 };// 字符替换码

			while (num > 0) {
				if ((num - cash.get(rest)) >= 0) {
					ran2 = (int) (Math.random() * (charValueMap.get(rest)
							.size()));
					finalResponse = finalResponse
							+ charValueMap.get(rest).get(ran2);
					num = num - cash.get(rest);
				} else {
					charValueMap.remove(rest);
					// charValueMap.splice(rest, 1);//删除从rest开始后的1个元素
					// cash.splice(rest, 1);// 删除cash里面的rest开始后的1个元素
					cash.remove(rest);
					rest = rest - 1;
				}
			}
			return finalResponse;

			// return "aaaaaa";
		} catch (Exception e) {
			GtLogger.v(LoggerString.getFileLineMethod() + e.getMessage());
		}
		return null;
	}

	/**
	 * 以5为周期来排布字符串
	 * 
	 * @param nonRepeatChallenge
	 * @return
	 */
	private static ArrayList<ArrayList<Character>> getCharValueMap(
			String nonRepeatChallenge) {

		ArrayList<ArrayList<Character>> charValueMap = new ArrayList<ArrayList<Character>>();

		for (int i = 0; i < 5; i++) {
			ArrayList<Character> charValueGroup = new ArrayList<Character>();// 某一组的字符类型
			charValueMap.add(charValueGroup);// 声明一个长度为5的空架子
		}

		// 以5为周期进行排布
		for (int i = 0; i < nonRepeatChallenge.length(); i++) {
			charValueMap.get(i % 5).add(nonRepeatChallenge.charAt(i));
		}

		return charValueMap;
	}

	/**
	 * 清除字符串中重复字母算法
	 * 
	 * @param s
	 * @return
	 */
	private static String rmRepeated(String s) {
		int len = s.length();
		int k = 0;
		int count = 0;
		String str = "";
		char[] c = new char[len];
		for (int i = 0; i < len; i++) {
			c[i] = s.charAt(i);
		}
		for (int i = 0; i < len; i++) {
			k = i + 1;
			while (k < len - count) {
				if (c[i] == c[k]) {
					for (int j = k; j < len - 1; j++) {
						c[j] = c[j + 1];// 出现重复字母，从k位置开始将数组往前挪位
					}
					count++;// 重复字母出现的次数
					k--;
				}
				k++;
			}

		}
		for (int i = 0; i < len - count; i++) {
			str += String.valueOf(c[i]);
		}
		return str;

	}

	/**
	 * 对用户行为数据进行编码：主要是一些数组
	 * 
	 * @return
	 */
	public static String EncryptUserAction(
			ArrayList<CaptchaUserAction> userActions) {

		try {
			// TODO 参考ZCX的JS代码
			return "s$$$o9%27A:?;:J::::::J::::JJ::::J:J:K:J:JI:::J:J9$$$%27!N(N*A*42+73+7060.89-.77!P(G06!U(777JoJ/!E(!r(Kn!Q)nJ!t6";
		} catch (Exception e) {
			GtLogger.v(LoggerString.getFileLineMethod() + e.getMessage());
		}
		return null;

	}

	private ArrayList<CaptchaUserAction> getOffsetData() {
		return null;
	}

	/**
	 * 根据位置获取编码串
	 * 
	 * @param num
	 * @return
	 */
	private Character getChar(int num) {
		String list = "!$'()*+,-./0123456789:;?@ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz~";
		return list.charAt(num);
	}

	private Character to77(int e) {
		//TODO
		
		return null;
	}
	
	private Character encode(int e) {
		//TODO
		
		return null;
	}

	// /**
	// * 获取字符的16进制的unicode编码
	// *
	// * @param str
	// * @return
	// */
	// public static String toUnicode(String str) {
	// // 存放返回值
	// String restr = "";
	// System.out.print("字符串\"" + str + "\"的unicode码:");
	// for (char a : str.toCharArray()) {
	// // 十六进制显示
	// String ch = Integer.toHexString((int) a);
	// // 用0补齐四位
	// for (int i = ch.length(); i < 4; i++) {
	// ch = "0" + ch;
	// }
	// // 全部转换成大写 不转换也无所谓
	// ch = ch.toUpperCase();
	// restr += "\\u" + ch;
	// }
	// System.out.println(restr);
	// return restr;
	// }

}
