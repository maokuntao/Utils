package imageUtils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * 切割图像
 * 
 * @author TMK
 *
 */
public class CutImage {
	
	/**
	 * 图像切割(按指定起点坐标和宽高切割)
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param result
	 *            切片后的图像地址
	 * @param x
	 *            目标切片起点坐标X
	 * @param y
	 *            目标切片起点坐标Y
	 * @param width
	 *            目标切片宽度
	 * @param height
	 *            目标切片高度
	 */
	public final static void cutByPositionAndSize(String srcImageFile, String result, int x,
			int y, int width, int height) {
		
		try {
			
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			
			if (srcWidth > 0 && srcHeight > 0) {
				
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				
				// 四个参数分别为图像起点坐标和宽高
				ImageFilter cropFilter = new CropImageFilter(x, y, width,
						height);
				
				Image img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(image.getSource(), cropFilter));
				
				BufferedImage tag = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
				g.dispose();
				
				// 输出为文件
				ImageIO.write(tag, "JPEG", new File(result));
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * 图像切割（指定切片的行数和列数）
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param descDir
	 *            切片目标文件夹
	 * @param rows
	 *            目标切片行数。默认2，必须是范围 [1, 20] 之内
	 * @param cols
	 *            目标切片列数。默认2，必须是范围 [1, 20] 之内
	 */
	public final static void cutByColAndRow(String srcImageFile, String descDir,
			int rows, int cols) {
		
		try {
			
			if (rows <= 0 || rows > 20)
				rows = 2; // 切片行数
			
			if (cols <= 0 || cols > 20)
				cols = 2; // 切片列数
			
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			
			if (srcWidth > 0 && srcHeight > 0) {
				
				Image img;
				ImageFilter cropFilter;
				
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				
				int destWidth = srcWidth; // 每张切片的宽度
				int destHeight = srcHeight; // 每张切片的高度
				
				// 计算切片的宽度和高度
				if (srcWidth % cols == 0) {
					
					destWidth = srcWidth / cols;
					
				} else {
					destWidth = (int) Math.floor(srcWidth / cols) + 1;
					
				}
				
				if (srcHeight % rows == 0) {
					destHeight = srcHeight / rows;
					
				} else {
					destHeight = (int) Math.floor(srcWidth / rows) + 1;
				}
				
				// 循环建立切片
				// 改进的想法:是否可用多线程加快切割速度
				for (int i = 0; i < rows; i++) {
					
					for (int j = 0; j < cols; j++) {
						
						// 四个参数分别为图像起点坐标和宽高
						// 即: CropImageFilter(int x,int y,int width,int height)
						cropFilter = new CropImageFilter(j * destWidth, i
								* destHeight, destWidth, destHeight);
						
						img = Toolkit.getDefaultToolkit().createImage(
								new FilteredImageSource(image.getSource(),
										cropFilter));
						
						BufferedImage tag = new BufferedImage(destWidth,
								destHeight, BufferedImage.TYPE_INT_RGB);
						
						Graphics g = tag.getGraphics();
						g.drawImage(img, 0, 0, null); // 绘制缩小后的图
						g.dispose();
						
						// 输出为文件
						ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i
								+ "_c" + j + ".jpg"));
					}
				}
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * 图像切割（指定切片的宽度和高度）
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param descDir
	 *            切片目标文件夹
	 * @param destWidth
	 *            目标切片宽度。默认200
	 * @param destHeight
	 *            目标切片高度。默认150
	 */
	public final static void cutBySize(String srcImageFile, String descDir,
			int destWidth, int destHeight) {
		
		try {
			
			if (destWidth <= 0)
				destWidth = 200; // 切片宽度
			
			if (destHeight <= 0)
				destHeight = 150; // 切片高度
			
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			
			if (srcWidth > destWidth && srcHeight > destHeight) {
				
				Image img;
				ImageFilter cropFilter;
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				
				int cols = 0; // 切片横向数量
				int rows = 0; // 切片纵向数量
				
				// 计算切片的横向和纵向数量
				if (srcWidth % destWidth == 0) {
					cols = srcWidth / destWidth;
					
				} else {
					cols = (int) Math.floor(srcWidth / destWidth) + 1;
					
				}
				
				if (srcHeight % destHeight == 0) {
					rows = srcHeight / destHeight;
					
				} else {
					rows = (int) Math.floor(srcHeight / destHeight) + 1;
					
				}
				
				// 循环建立切片
				// 改进的想法:是否可用多线程加快切割速度
				for (int i = 0; i < rows; i++) {
					
					for (int j = 0; j < cols; j++) {
						
						// 四个参数分别为图像起点坐标和宽高
						// 即: CropImageFilter(int x,int y,int width,int height)
						cropFilter = new CropImageFilter(j * destWidth, i
								* destHeight, destWidth, destHeight);
						
						img = Toolkit.getDefaultToolkit().createImage(
								new FilteredImageSource(image.getSource(),
										cropFilter));
						
						BufferedImage tag = new BufferedImage(destWidth,
								destHeight, BufferedImage.TYPE_INT_RGB);
						
						Graphics g = tag.getGraphics();
						g.drawImage(img, 0, 0, null); // 绘制缩小后的图
						g.dispose();
						
						// 输出为文件
						ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i
								+ "_c" + j + ".jpg"));
					}
				}
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

}
