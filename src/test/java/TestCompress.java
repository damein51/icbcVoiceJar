import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @program: damein
 * @description:
 * @author: lidongmin
 * @create: 2021-02-01 16:35
 */
public class TestCompress {

    public static void main(String[] args) throws Exception {

        String methodName = "org.apache.hadoop.io.compress.BZip2Codec";
        compress("e:/hello.txt", methodName);
        String decompressPath = "e:/hello.txt.bz2";
        decompress(decompressPath);

    }

    private static void compress(String fileName, String method) throws Exception {

        // （1）获取输入流
        FileInputStream fis = new FileInputStream(fileName);

        Class codecClass = Class.forName(method);

        CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(codecClass, new Configuration());

        // （2）获取输出流
        FileOutputStream fos = new FileOutputStream(fileName + codec.getDefaultExtension());
        CompressionOutputStream cos = codec.createOutputStream(fos);

        // （3）流的对拷
        IOUtils.copyBytes(fis, cos, 1024 * 1024 * 5, false);

        // （4）关闭资源
        fis.close();
        cos.close();
        fos.close();


    }

    // 2、解压缩
    private static void decompress(String fileName) throws IOException {

        // （0）校验是否能解压缩
        CompressionCodecFactory factory = new CompressionCodecFactory(new Configuration());

        CompressionCodec codec = factory.getCodec(new Path(fileName));

        if (codec == null) {
            System.out.println("cannot find codec for file " + fileName);
            return;
        }

        // （1）获取输入流
        CompressionInputStream cis = codec.createInputStream(new FileInputStream(fileName));

        // （2）获取输出流
        FileOutputStream fos = new FileOutputStream(fileName + ".decoded");

        // （3）流的对拷
        IOUtils.copyBytes(cis, fos, 1024 * 1024 * 5, false);

        // （4）关闭资源
        cis.close();
        fos.close();
    }
}