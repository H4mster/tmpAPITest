import com.alibaba.fastjson.JSON;
import resense.ocrft.core.*;
import resense.ocrft.entity.FTResult;
//java 原生原生文件读取
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
public class Demo {
    static {
        //有opencv版本 -Djava.library.path=D:\work\c++\RSAPI\x64\Debug;D:\work\SDK\opencv-3.4.5-vc14_vc15\build\java\x64
        //无opencvb版本 -Djava.library.path=D:\work\c++\RSAPI\x64\Debug
        //真实测试版 -Djava.library.path=D:\workspace\IDEA_project\test_api
        //如果直接用-Djava.library.path***会导致找不到其他dll库所以建议将dll库给添加到环境变量中
        System.loadLibrary("RSAPI");
    }

    public static void main(String[] args) throws IOException {

        Long type;
        int engine_type = -1;       //-1表示cpu
        String modelDir = "D:\\work\\FTmodel"; //模型存放目录
        int devid = -1;
        System.out.println(RsApi.code());
        type  = RsApi.openEngine( modelDir, devid);
        if (null != type) {
            System.out.println("engine address:" + type);
//            String result = RsApi.recongize1(type, residr);
//            System.out.println("RSRecongize1 result:" + result);  //
//            JSONObject jsObj = JSON.parseObject(result);
//            System.out.println("RSRecongize1 jsObj"+jsObj);
//            FTResult ftResult = JSON.parseObject(result,new TypeReference<FTResult>(){});
            String imgpath ="D:\\testimg\\1.jpg";
            FTResult ftResult1 = RsApi.recongize1(type,imgpath);
            System.out.println(ftResult1.getLines().get(0).getChars().get(0));

            BufferedImage image = ImageIO.read(new FileInputStream(imgpath));
            FTResult ftResult2=RsApi.recognzie2(type,image);
            System.out.println("ftresult2:"+ftResult2);
            RsApi.closeEngine(type);
            String mergePdfSavePath="D:\\testimg\\pdfsave\\pdf.pdf";
            String[] pdfs = {"D:\\testimg\\pdf1\\pdf1.pdf","D:\\testimg\\pdf2\\pdf2.pdf"};
            boolean flag =RsApi.pdfMerge("C:\\Users\\dell\\Desktop\\1.jpg",pdfs);
            System.out.println("RSPDF_Merge:"+flag);
            boolean flag1 =RsApi.pdfSplit(mergePdfSavePath,"D:\\testimg\\splitpdf");
            System.out.println("pdfSplit:"+flag1);
            boolean flag2 =RsApi.pdfSave(mergePdfSavePath,"D:\\testimg\\1.jpg", JSON.toJSONString(ftResult1)
                    ,"宋体");
            System.out.println("pdfSave:"+flag2);

        } else {
            System.out.println("type is null");
        }
    }




}

