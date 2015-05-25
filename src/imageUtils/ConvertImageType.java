package imageUtils;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * 转换图片格式
 * 
 * @author TMK
 *
 */
public class ConvertImageType {
	
	/**
	 * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param formatName
	 *            包含格式非正式名称的 String：如JPG、JPEG、GIF等
	 * @param destImageFile
	 *            目标图像地址
	 */
	public final static void convert(String srcImageFile, String formatName,
			String destImageFile) {
		
		try {
			
			File f = new File(srcImageFile);
			f.canRead();
			f.canWrite();
			
			BufferedImage src = ImageIO.read(f);
			ImageIO.write(src, formatName, new File(destImageFile));
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
