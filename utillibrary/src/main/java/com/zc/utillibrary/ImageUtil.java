package com.zc.utillibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.ExifInterface;

import com.blankj.utilcode.util.ImageUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author wenchao
 * @date  2019/7/18
 * @version 1.0.1
 * @description 图片处理类
 */
public class ImageUtil {

    private static String PHOTO_FILE_NAME = "PMSManagerPhoto";

    /**
     * 获取图片的旋转角度
     *
     * @param filePath 路径
     * @return  int
     */
    public static int getRotateAngle(String filePath) {
        int rotate_angle = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate_angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate_angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate_angle = 270;
                    break;
                    default:
                        break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate_angle;
    }

    /**
     * 旋转图片角度
     *
     * @param angle 角度
     * @param bitmap 图片
     * @return Bitmap
     */
    public static Bitmap setRotateAngle(int angle, Bitmap bitmap) {

        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(angle);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;

    }

    /**
     * Create circle image bitmap.
     * 转换为圆形状的bitmap
     * @param source the source
     * @return the bitmap
     */
    public static Bitmap createCircleImage(Bitmap source) {
        int length = source.getWidth() < source.getHeight() ? source.getWidth() : source.getHeight();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(length / 2, length / 2, length / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }
    /**
     * 图片压缩-质量压缩
     *  覆盖原图片
     * @param filePath 源图片路径
     * @return 压缩后的路径
     */

    public static String compressImageOrrived(String filePath) {
        //原文件
        File oldFile = new File(filePath);
        String oldFileName = oldFile.getName();
        String oldPath = oldFile.getParent();
        //压缩文件路径 照片路径/
        String targetPath = oldFile.getPath();
        //压缩比例0-100
        int quality = 50;
        //获取一定尺寸的图片
        Bitmap bm = getSmallBitmap(filePath);
        //获取相片拍摄角度
        int degree = getRotateAngle(filePath);
        //旋转照片角度，防止头像横着显示
        if (degree != 0) {
            bm = setRotateAngle(degree,bm);
        }
        File outputFile = new File(targetPath);
        try {
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
            } else {
                outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            if (oldFileName.endsWith(".jpg")) {
                bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
            }else if (oldFileName.endsWith(".png")){
                bm.compress(Bitmap.CompressFormat.PNG, quality, out);
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return filePath;
        }
        return outputFile.getPath();
    }

    /**
     * 图片压缩-质量压缩
     *
     * @param filePath 源图片路径
     * @return 压缩后的路径
     */

    public static String compressImage(String filePath) {
        //原文件
        File oldFile = new File(filePath);
        String oldFileName = oldFile.getName();
        String oldPath = oldFile.getParent();
        //压缩文件路径 照片路径/
        String targetPath = oldFile.getPath();
        if (oldFileName.endsWith(".jpg")) {
            targetPath = oldPath + oldFileName.replace(".jpg","_1.jpg");
        }else if (oldFileName.endsWith(".png")){
            targetPath = oldPath + oldFileName.replace(".png","_1.png");
        }
        //压缩比例0-100
        int quality = 50;
        //获取一定尺寸的图片
        Bitmap bm = getSmallBitmap(filePath);
        //获取相片拍摄角度
        int degree = getRotateAngle(filePath);
        //旋转照片角度，防止头像横着显示
        if (degree != 0) {
            bm = setRotateAngle(degree,bm);
        }
        File outputFile = new File(targetPath);
        try {
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
            } else {
                outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            if (oldFileName.endsWith(".jpg")) {
                bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
            }else if (oldFileName.endsWith(".png")){
                bm.compress(Bitmap.CompressFormat.PNG, quality, out);
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return filePath;
        }
        return outputFile.getPath();
    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     *
     * @param filePath the file path 路径
     * @return the small bitmap
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //只解析图片边沿，获取宽高
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, 1080, 1920);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * Calculate in sample size int.
     * 计算缩放比
     * @param options   the options
     * @param reqWidth  the req width
     * @param reqHeight the req height
     * @return the int
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 调整图片大小
     * @param bm Bitmap
     * @param newWidth 新图片宽度
     * @param newHeight 新图片高度
     * @return Bitmap
     */
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        //获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        //计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        //取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        //得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }
}
