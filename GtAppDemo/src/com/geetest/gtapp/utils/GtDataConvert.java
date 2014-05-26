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

			GtLogger.v("user_response before encode:" + response);

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
			GtLogger.expection(LoggerString.getFileLineMethod()
					+ e.getMessage());
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

			String encodeUserActions = encode(userActions);

			return encodeUserActions;
			// return
			// "s$$$o9%27A:?;:J::::::J::::JJ::::J:J:K:J:JI:::J:J9$$$%27!N(N*A*42+73+7060.89-.77!P(G06!U(777JoJ/!E(!r(Kn!Q)nJ!t6";
		} catch (Exception e) {
			GtLogger.expection(LoggerString.getFileLineMethod()
					+ e.getMessage());
		}
		return null;

	}

	/**
	 * 对行为数据进行压缩编码
	 * 
	 * @param userActions
	 * @return
	 */
	private static String encode(ArrayList<CaptchaUserAction> userActions) {

		try {
			// TODO 将所有的数据打印出来
			GtLogger.v("做差值处理之前");
			for (int i = 0; i < userActions.size(); i++) {
				userActions.get(i).v();
			}

			ArrayList<CaptchaUserAction> diffCaptchaUserActions = new ArrayList<CaptchaUserAction>();
			diffCaptchaUserActions = getOffsetData(userActions);

			GtLogger.v("做差值处理后");
			for (int i = 0; i < diffCaptchaUserActions.size(); i++) {
				diffCaptchaUserActions.get(i).v();
			}

			String rx = "", ry = "", rt = "";
			for (int i = 0; i < diffCaptchaUserActions.size(); i++) {
				String rxi = "", ryi = "", rti = "";

				int xi = (int) (diffCaptchaUserActions.get(i).getxPos());
				int yi = (int) (diffCaptchaUserActions.get(i).getyPos());
				int ti = (int) (diffCaptchaUserActions.get(i)
						.getTimeIncrement());

				if (yi == -2 && xi >= 1 && xi <= 4) {
					ryi = getChar(1 + xi);
				} else if (yi == -1 && xi >= -2 && xi <= 6) {
					ryi = getChar(8 + xi);
				} else if (yi == 0 && xi >= -5 && xi <= 10) {
					ryi = getChar(20 + xi);
				} else if (yi == 1 && xi >= -2 && xi <= 7) {
					ryi = getChar(33 + xi);
				} else {
					// 对yi进行编码
					if (yi >= -17 && yi <= 20) {
						ryi = getChar(58 + yi);
					} else if (yi < -17) {
						ryi = getChar(1) + to77(-18 - yi) + getChar(0);
					} else if (yi > 20) {
						ryi = getChar(0) + to77(yi - 21) + getChar(1);
					}

					// 对区间的x编码
					if (xi >= -21 && xi <= 55) {
						rxi = getChar(23 + xi);
					} else if (xi < -21) {
						rxi = getChar(1) + to77(-22 - xi) + getChar(0);
					} else if (xi > 55) {
						rxi = getChar(0) + to77(xi - 56) + getChar(1);
					}
				}

				// 对时间进行编码变换
				String tempTi = to77(ti);
				if (tempTi.length() <= 1) {
					rti = tempTi;
				} else if (tempTi.length() == 2) {
					rti = getChar(0) + tempTi;
				} else {
					rti = getChar(0) + getChar(0);
				}

				rx = rx + rxi;
				ry = ry + ryi;
				rt = rt + rti;

			}

			GtLogger.v("rx: " + rx);
			GtLogger.v("ry: " + ry);
			GtLogger.v("rt: " + rt);

			String finalString = rx + getChar(1) + getChar(1) + getChar(1) + ry
					+ getChar(1) + getChar(1) + getChar(1) + rt;
			return finalString;

			// return
			// "s$$$o9%27A:?;:J::::::J::::JJ::::J:J:K:J:JI:::J:J9$$$%27!N(N*A*42+73+7060.89-.77!P(G06!U(777JoJ/!E(!r(Kn!Q)nJ!t6";

			// return null;
		} catch (Exception e) {
			GtLogger.expection(LoggerString.getFileLineMethod()
					+ e.getMessage());
			return null;
		}

	}

	/**
	 * 获取输入数据的差分量
	 * 
	 * @param userActions
	 * @return
	 */
	@SuppressWarnings("unused")
	private static ArrayList<CaptchaUserAction> getOffsetData(
			ArrayList<CaptchaUserAction> userActions) {

		try {

			ArrayList<CaptchaUserAction> diffCaptchaUserActions = new ArrayList<CaptchaUserAction>();

			for (int i = 0; i < userActions.size() - 1; i++) {
				CaptchaUserAction diffAction = new CaptchaUserAction();

				// 相邻求差
				diffAction.setxPos(userActions.get(i + 1).getxPos()
						- userActions.get(i).getxPos());
				diffAction.setyPos(userActions.get(i + 1).getyPos()
						- userActions.get(i).getyPos());
				diffAction.setTimeIncrement(userActions.get(i + 1)
						.getTimeIncrement()
						- userActions.get(i).getTimeIncrement());

				// 剔除重复项 进行存储
				if ((diffAction.getxPos() != 0) || (diffAction.getyPos() != 0)
						|| (diffAction.getTimeIncrement() != 0)) {
					diffCaptchaUserActions.add(diffAction);
				}

			}

			return diffCaptchaUserActions;
		} catch (Exception e) {
			GtLogger.expection(LoggerString.getFileLineMethod()
					+ e.getMessage());
			return null;
		}

	}

	/**
	 * 根据位置获取编码串
	 * 
	 * @param num
	 * @return
	 */
	private static String getChar(int num) {
		String list = "!$'()*+,-./0123456789:;?@ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz~";
		return list.charAt(num) + "";
	}

	/**
	 * 使用78进制来压缩存储数据
	 * 
	 * @param e
	 * @return
	 */
	private static String to77(int tempara) {

		String result = "";
		int temp = tempara;
		// // TODO
		// if (temp > 300) {
		// temp = 260;
		// }
		// GtLogger.v("result: " + result + " temp: " + temp);

		try {

			while (temp != 0) {
				result = result.toString() + getChar(temp % 77 + 2);
				temp = (temp - temp % 77) / 77;
			}

			// 通过特殊保留符号位来区分压缩类别。
			if (result.equals("")) {
				return getChar(2) + "";
			} else {
				return result;
			}

		} catch (Exception e) {
			GtLogger.expection(LoggerString.getFileLineMethod()
					+ e.getMessage());
			return result;
		}

		// return result.equals(null) ? result : getChar(2);
		// return null;
	}

	// /**
	// * 使用78进制来压缩存储数据
	// *
	// * @param e
	// * @return
	// */
	// private static String to77(int temp) {
	// // TODO
	//
	// try {
	//
	// String result = "";
	//
	// while (temp != 0) {
	// result = result.toString() + getChar(temp % 77 + 2);
	// temp = (temp - temp % 77) / 77;
	// }
	//
	// // 通过特殊保留符号位来区分压缩类别。
	// if (result.equals("")) {
	// return getChar(2) + "";
	// } else {
	// return result;
	// }
	//
	// } catch (Exception e) {
	// GtLogger.v(LoggerString.getFileLineMethod() + e.getMessage());
	// }
	// return null;
	//
	// // return result.equals(null) ? result : getChar(2);
	// // return null;
	// }

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
