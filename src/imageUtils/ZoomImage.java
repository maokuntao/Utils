package imageUtils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 缩放图像
 * 
 * @author TMK
 *
 */
public class ZoomImage {
	/**
	 * 缩放图像（按比例缩放）
	 * 
	 * @param srcImageFileAddress
	 *            源图像文件地址
	 * @param desImageFileAddress
	 *            缩放后的图像地址
	 * @param scale
	 *            缩放比例
	 * @param flag
	 *            缩放选择:true 放大; false 缩小;
	 */
	public final static void zoomByScale(String srcImageFileAddress,
			String desImageFileAddress, float scale, boolean flag) {

		try {

			BufferedImage src = ImageIO.read(new File(srcImageFileAddress)); // 读入文件

			int width = src.getWidth(); // 得到源图宽
			int height = src.getHeight(); // 得到源图长

			float outputWidth , outputHeight;
			
			if (flag) {// 放大
				outputWidth = width * scale;
				outputHeight = height * scale;
			} else {// 缩小
				outputWidth = width / scale;
				outputHeight = height / scale;
			}

			Image image = src.getScaledInstance(Math.round(outputWidth), Math.round(outputHeight),
					Image.SCALE_DEFAULT);

			BufferedImage tag = new BufferedImage(Math.round(outputWidth), Math.round(outputHeight),
					BufferedImage.TYPE_INT_RGB);

			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();

			ImageIO.write(tag, "JPEG", new File(desImageFileAddress));// 输出到文件流

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 缩放图像（按指定高度和宽度缩放）
	 * 
	 * @param srcImageFile
	 *            源图像文件地址
	 * @param desImageFileAddress
	 *            缩放后的图像地址
	 * @param height
	 *            缩放后的高度
	 * @param width
	 *            缩放后的宽度
	 * @param blank
	 *            比例不对时是否需要补白：true为补白; false为不补白;
	 */
	public final static void zoomBySize(String srcImageFile,
			String desImageFileAddress, int height, int width, boolean blank) {

		try {

			double ratio = 0.0; // 缩放比例
			File f = new File(srcImageFile);
			BufferedImage bi = ImageIO.read(f);
			Image itemp = bi.getScaledInstance(width, height,
					BufferedImage.SCALE_SMOOTH);

			// 计算比例
			if ((bi.getHeight() > height) || (bi.getWidth() > width)) {

				if (bi.getHeight() > bi.getWidth()) {// 图片本身高度>宽度，缩放比例=指定高度/图片本身高度

					ratio = (new Integer(height)).doubleValue()
							/ bi.getHeight();
				} else {// 图片本身高度<=宽度，缩放比例=指定高度/图片本身高度

					ratio = (new Integer(width)).doubleValue() / bi.getWidth();
				}

				AffineTransformOp op = new AffineTransformOp(
						AffineTransform.getScaleInstance(ratio, ratio), null);
				itemp = op.filter(bi, null);

			}
			if (blank) {// 补白

				BufferedImage image = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);

				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);

				if (width == itemp.getWidth(null))

					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);

				else

					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);

				g.dispose();
				itemp = image;
			}

			ImageIO.write((BufferedImage) itemp, "JPEG", new File(
					desImageFileAddress));

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
