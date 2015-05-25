package imageUtils;

import java.awt.Color;
import java.awt.Font;

/**
 * @author TMK
 *
 */
public class ImageUtils {

	// 几种常见的图片格式
	public static String IMAGE_TYPE_GIF = "gif";// 图形交换格式
	public static String IMAGE_TYPE_JPG = "jpg";// 联合照片专家组
	public static String IMAGE_TYPE_JPEG = "jpeg";// 联合照片专家组
	public static String IMAGE_TYPE_BMP = "bmp";// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
	public static String IMAGE_TYPE_PNG = "png";// 可移植网络图形
	public static String IMAGE_TYPE_PSD = "psd";// Photoshop的专用格式

	public static void main(String[] args) {

		// 1-缩放图像：
		// 方法一：按比例缩放
		float scale = 1366F/2204F;
		ZoomImage.zoomByScale("E:/Code/imageUtils/1.jpg", "E:/Code/imageUtils/zoomByScale.jpg", scale, true);// 测试OK
		// 方法二：按高度和宽度缩放
		ZoomImage.zoomBySize("E:/Code/imageUtils/1.jpg", "E:/Code/imageUtils/zoomBySize.jpg", 768, 768, true);// 测试OK
		System.out.println("缩放图像完成！");

		// 2-切割图像：
		// 方法一：按指定起点坐标和宽高切割
		CutImage.cutByPositionAndSize("E:/Code/imageUtils/1.jpg", "E:/Code/imageUtils/cutByPositionAndSize.jpg", 0, 0, 400, 400);// 测试OK
		// 方法二：指定切片的行数和列数
		CutImage.cutByColAndRow("E:/Code/imageUtils/1.jpg", "E:/Code/imageUtils/", 2, 2);// 测试OK
		// 方法三：指定切片的宽度和高度
		CutImage.cutBySize("E:/Code/imageUtils/1.jpg", "E:/Code/imageUtils/", 300, 300);// 测试OK
		System.out.println("切割图像完成！");

		// 3-图像类型转换：
		ConvertImageType.convert("E:/Code/imageUtils/1.jpg", "GIF", "E:/Code/imageUtils/convertType.GIF");// 测试OK
		System.out.println("图像类型转换完成！");

		// 4-彩色转黑白：
		EditImage.gray("E:/Code/imageUtils/1.jpg", "E:/Code/imageUtils/gray.jpg");// 测试OK
		System.out.println("彩色转黑白完成！");

		// 5-给图片添加文字水印：
		// 方法一：
		EditImage.watermarkByText("我是水印文字", "E:/Code/imageUtils/1.jpg", "E:/Code/imageUtils/watermarkByText_1.jpg",
				"宋体", Font.BOLD, Color.white, 80, 0, 0, 0.5f);// 测试OK
		// 方法二：
		EditImage.watermarkByText("我也是水印文字", "E:/Code/imageUtils/1.jpg", "E:/Code/imageUtils/watermarkByText_2.jpg",
				"黑体", 36, Color.white, 80, 0, 0, 0.5f);// 测试OK
		System.out.println("给图片添加文字水印完成！");

		// 6-给图片添加图片水印：
		EditImage.watermarkByImage("E:/Code/imageUtils/2.jpg", "E:/Code/imageUtils/1.jpg",
				"E:/Code/imageUtils/watermarkByImage.jpg", 0, 0, 0.5f);// 测试OK
		System.out.println("给图片添加图片水印！");

	}



}
