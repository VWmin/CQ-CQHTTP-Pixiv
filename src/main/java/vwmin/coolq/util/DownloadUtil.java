package vwmin.coolq.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.util.Assert;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

@Slf4j
public class DownloadUtil {

    public static boolean downloadPixivImage(String filePath, String fileName, String url) throws IOException {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        try {
            get.setHeader("Referer", "https://app-api.pixiv.net/");
            CloseableHttpResponse response = client.execute(get);
            HttpEntity responseEntity = response.getEntity();
            InputStream inputStream = responseEntity.getContent();

            saveImage(inputStream, filePath, fileName);

            response.close();
            return true;
        } catch (IOException e) {
            log.warn("图片下载或保存时出现错误");
            throw e;
        }

    }

    /**
     * 保存图片
     * @param in 输入流 非空
     * @param dirPath 文件夹路径 非空
     * @param fileName 指定文件名时 文件名不能为空或null
     */
    public static void saveImage(InputStream in, String dirPath, String fileName) throws IOException {
        Assert.notNull(in, "文件输入流不能为空");
        Assert.notNull(dirPath, "文件夹路径不能为空");

        File dir = new File(dirPath);
        if(!dir.exists()){
            if(!dir.mkdir()) {
                throw new IOException("文件夹创建失败: "+dirPath);
            }
        }

        File file = new File(dirPath.concat(fileName));
        doSave(in, file);
    }

    public static void doSave(InputStream in, File file) throws IOException{

        file.setReadable(true, false);
        file.setWritable(true, false);

        if(!file.exists()){
            if(file.createNewFile()){
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                FileChannel fileChannel = randomAccessFile.getChannel();

                try{
                    fileChannel.tryLock();
                }catch (OverlappingFileLockException e){
                    log.info("其他线程正在操作文件 >> " + file.getAbsolutePath());
                    fileChannel.close();
                    randomAccessFile.close();
                }

                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) != -1) {
                    randomAccessFile.write(buf, 0, len);
                }
                randomAccessFile.close();
                fileChannel.close();
            }else{
                throw new IOException("文件创建失败: " + file.getAbsolutePath());
            }
        }



    }

    public static boolean tryLocal(String dirPath, String fileName){
        File file = new File(dirPath.concat(fileName));
        return file.exists();
    }
}
