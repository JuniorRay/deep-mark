package cn.witeye.iaac.mark.kit;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.Test;
/*	图片翻转以及旋转功能
 * @author Junior
 * @date Jun 8, 2018
 */
public class RotateImage {

	private static BufferedImage rotateByAngel(Image image, int angel) {
		int imageWidth = image.getWidth(null);
		int imageHeight = image.getHeight(null);
		// calculate the new image size
		Rectangle rectangle = CalcRotatedSize(new Rectangle(new Dimension(imageWidth, imageHeight)), angel);

		BufferedImage bufferedImage = null;
		bufferedImage = new BufferedImage(rectangle.width, rectangle.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bufferedImage.createGraphics();
		// transform
		g2.translate((rectangle.width - imageWidth) / 2, (rectangle.height - imageHeight) / 2);
		g2.rotate(Math.toRadians(angel), imageWidth / 2, imageHeight / 2);

		g2.drawImage(image, null, null);
		return bufferedImage;
	}

	// calculate the new image size
	private static Rectangle CalcRotatedSize(Rectangle rectangle, int angel) {
		// if angel is greater than 90 degree, we need to do some conversion
		if (angel >= 90) {
			if (angel / 90 % 2 == 1) {
				int temp = rectangle.height;
				rectangle.height = rectangle.width;
				rectangle.width = temp;
			}
			angel = angel % 90;
		}

		double r = Math.sqrt(rectangle.height * rectangle.height + rectangle.width * rectangle.width) / 2;
		double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
		double angelAlpha = (Math.PI - Math.toRadians(angel)) / 2;
		double angelDaltaWidth = Math.atan((double) rectangle.height / rectangle.width);
		double angelDaltaHeight = Math.atan((double) rectangle.width / rectangle.height);

		int lenDaltaWidth = (int) (len * Math.cos(Math.PI - angelAlpha - angelDaltaWidth));
		int lenDaltaHeight = (int) (len * Math.cos(Math.PI - angelAlpha - angelDaltaHeight));
		int desWidth = rectangle.width + lenDaltaWidth * 2;
		int desHeight = rectangle.height + lenDaltaHeight * 2;
		return new java.awt.Rectangle(new Dimension(desWidth, desHeight));
	}

	/*
	 * 镜像处理，输入image和方式，返回翻转的新image
	 *  type = 0 表示上下翻转，type = 1 表示左右翻转
	 */
	private static BufferedImage rollingOverByType(Image image, int type) {
		try {
			int imageWidth = image.getWidth(null);
			int imageHeight = image.getHeight(null);
			BufferedImage bufferedImage = new BufferedImage(imageWidth, imageHeight,
					BufferedImage.TYPE_INT_RGB);
			Graphics graphics = bufferedImage.createGraphics();
			graphics.drawImage(image, 0, 0, null); // 这里，大家可能会有疑问，似乎没有对bufferedimage干啥
			graphics.dispose(); // 但是是正确的，g调用drawImage就自动保存了

			int w = bufferedImage.getWidth();
			int h = bufferedImage.getHeight();

			int[][] datas = new int[w][h];
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					datas[j][i] = bufferedImage.getRGB(j, i);
				}
			}
			int[][] tmps = new int[w][h];
			if (type == 0) {
				for (int i = 0, a = h - 1; i < h; i++, a--) {
					for (int j = 0; j < w; j++) {
						tmps[j][a] = datas[j][i];
					}
				}
			} else if (type == 1) {
				for (int i = 0; i < h; i++) {
					for (int j = 0, b = w - 1; j < w; j++, b--) {
						tmps[b][i] = datas[j][i];
					}
				}
			}
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					bufferedImage.setRGB(j, i, tmps[j][i]);
				}
			}

			return bufferedImage;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	/**
	 * 图片翻转对外接口，可以直接使用
	 *（ type = 0 表示上下翻转，type = 1 表示左右翻转）
	 * */
	public static void rollingOver(File imageFile, String outputFolder, String fileName, int type) throws IOException {
		String outputImg = outputFolder + "/" + fileName;
    	
		BufferedImage src = ImageIO.read(imageFile);
		BufferedImage des = RotateImage.rollingOverByType(src, type);
		Assert.assertNotNull(des);
		Assert.assertTrue(ImageIO.write(des, "jpg", new File(outputImg)));
		System.out.println("outputImg=="+outputImg);
	}
	
	// 图片旋转对外接口，可以直接使用
	public static void rotate(File imageFile, String outputFolder, String fileName, int angle) throws IOException {
		String outputImg = outputFolder + "/" + fileName;
    	
		BufferedImage src = ImageIO.read(imageFile);
		BufferedImage des = RotateImage.rotateByAngel(src, angle);
		Assert.assertNotNull(des);
		Assert.assertTrue(ImageIO.write(des, "jpg", new File(outputImg)));
		System.out.println("outputImg=="+outputImg);
	}
	
//	@Test
	public void testRollingOver() throws IOException{
		File file=new File("/Users/juniorray/Documents/testImage/test.jpg" + 
				"");
		String outputFolder="/Users/juniorray/Documents/testImage/";
		int type=1;//（type = 0 表示上下翻转，type = 1 表示左右翻转）
		String typeStr="null";
		if(type==0) {
			typeStr="up";
		}else if(type==1) {
			typeStr="left";

		}
		String fileName="test"+"_"+typeStr+".jpg";
		
		rollingOver(file,outputFolder,fileName,type);
	}
	
	
	
	@Test
	public void testRotate() throws IOException{
		File file=new File("/Users/juniorray/Documents/testImage/test.jpg" + 
				"");
		String outputFolder="/Users/juniorray/Documents/testImage/";
		int angle=120;
		String fileName="test"+"_"+angle+".jpg";
		
		rotate(file,outputFolder,fileName,angle);
	}
	

//	@Test
	public void testRotateByAngel() throws IOException {

		BufferedImage src = ImageIO.read(new File("d:/dog.jpg"));
		BufferedImage des = RotateImage.rotateByAngel(src, 30);
		Assert.assertNotNull(des);
		Assert.assertTrue(ImageIO.write(des, "jpg", new File("d:/dog2.jpg")));

		// bigger angel
		des = RotateImage.rotateByAngel(src, 150);
		Assert.assertNotNull(des);
		Assert.assertTrue(ImageIO.write(des, "jpg", new File("d:/dog3.jpg")));

		// bigger angel
		des = RotateImage.rotateByAngel(src, 270);
		Assert.assertNotNull(des);
		Assert.assertTrue(ImageIO.write(des, "jpg", new File("d:/dog4.jpg")));
		
		//
	}
}