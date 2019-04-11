package com.planit.qa.util;

import java.util.Random;

import com.planit.qa.base.TestBase;

public class StringUtils extends TestBase{
	
	public String randomEmailGenerator(int lengthOfData)
	{
		StringBuilder builder =null;
		String randomString= null;
		try {
			String charactersInData= "01234567889abcdefghijklmnopqrstuvwxyz";
			builder = new StringBuilder();
			Random random = new Random();

			for(int i=0;i<lengthOfData;i++)
			{
				builder.append(charactersInData.charAt(random.nextInt(charactersInData.length())));
			}
			System.out.println(builder);
			randomString = builder.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return randomString;
	}


	public String randomPasswordGenerator(int lengthOfData)
	{
		StringBuilder builder =null;
		String randomString= null;
		try {
			String charactersInData= "ABCDEFGHIJKLMNOPQRSTUVWXYZ01234567889abcdefghijklmnopqrstuvwxyz~!@#$%^&*_";
			builder = new StringBuilder();
			Random random = new Random();

			for(int i=0;i<lengthOfData;i++)
			{
				builder.append(charactersInData.charAt(random.nextInt(charactersInData.length())));
			}
			System.out.println(builder);
			randomString = builder.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return randomString;
	}

}
