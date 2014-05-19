package com.geetest.gtappdemo.model.vo;

import com.geetest.gtapp.logger.GtLogger;
import com.geetest.gtapp.utils.LoggerString;

/**
 * 解码之后的challenge
 * 
 * @author Zheng 2014年5月19日 上午11:09:44
 */
public class DecodedChallenge {

	private String realChallenge = "0accdbb7cda7c8a11f182cd28f6c2c24";// 真正的32位的challenge
	private String randBase = "5v";// 最后的一位随机码

	/**
	 * 构造函数
	 * 
	 * @param encodedChallenge
	 *            解码之前的34位Challenge值
	 */
	public DecodedChallenge(String encodedChallenge) {
		try {
			if (encodedChallenge.length() == 34) {
				// 按照具体的编码 规范来进行解码
				this.realChallenge = encodedChallenge.substring(0, 32);
				this.randBase = encodedChallenge.substring(32);
			}else {
				GtLogger.v(LoggerString.getFileLineMethod() + "encodedChallenge：字符长度不符合规范");
			}

		} catch (Exception e) {
			GtLogger.v(LoggerString.getFileLineMethod() + e.getMessage());
		}
	}

	public String getRealChallenge() {
		return realChallenge;
	}

	public void setRealChallenge(String realChallenge) {
		this.realChallenge = realChallenge;
	}

	public String getRandBase() {
		return randBase;
	}

	public void setRandBase(String randBase) {
		this.randBase = randBase;
	}

}
