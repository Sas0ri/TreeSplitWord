package love.cq.library;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import love.cq.domain.Branch;
import love.cq.domain.Forest;
import love.cq.domain.WoodInterface;
import love.cq.util.IOUtil;

public class Library {
	public static Forest makeForest(String path) throws Exception {
		return makeForest(new FileInputStream(path));
	}

	public static Forest makeForest(InputStream inputStream) throws Exception {
		return makeForest(IOUtil.getReader(inputStream, "UTF-8"));
	}

	public static Forest makeForest(BufferedReader br) throws Exception {
		return makeLibrary(br, new Forest());
	}

	private static Forest makeLibrary(BufferedReader br, Forest forest) throws Exception {
		try {
			String temp = null;
			while ((temp = br.readLine()) != null) {
				insertWord(forest, temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			br.close();
		}
		return forest;
	}

	/**
	 * 插入一个词
	 * 
	 * @param forest
	 * @param temp
	 */
	public static void insertWord(Forest forest, String temp) {

		String[] param = temp.split("\t");

		temp = param[0];
		
		String[] resultParams = null ;

		WoodInterface branch = forest;
		char[] chars = temp.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars.length == i + 1) {
				resultParams = new String[param.length-1] ;
				for (int j = 1; j < param.length; j++) {
					resultParams[j-1] = param[j] ;
				}
				branch.add(new Branch(chars[i], 3, resultParams));
			} else {
				branch.add(new Branch(chars[i], 1, null));
			}
			branch = branch.get(chars[i]);
		}
	}

	/**
	 * 删除一个词
	 * 
	 * @param forest
	 * @param temp
	 */
	public static void removeWord(Forest forest, String word) {
		WoodInterface branch = forest;
		char[] chars = word.toCharArray();
		
		for (int i = 0; i < chars.length; i++) {
			if (chars.length == i + 1) {
				branch.add(new Branch(chars[i], -1, null));
			}
			branch = branch.get(chars[i]);
		}
	}
}