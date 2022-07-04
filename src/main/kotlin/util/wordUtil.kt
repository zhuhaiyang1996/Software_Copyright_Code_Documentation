package util

import com.microsoft.schemas.vml.CTShape
import org.apache.poi.hwpf.extractor.WordExtractor
import org.apache.poi.ooxml.POIXMLDocument
import org.apache.poi.openxml4j.util.ZipSecureFile
import org.apache.poi.wp.usermodel.HeaderFooterType
import org.apache.poi.xwpf.extractor.XWPFWordExtractor
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFParagraph
import org.apache.poi.xwpf.usermodel.XWPFRun
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.namespace.QName


// https://github.com/tendyliu/Source2Docx/blob/master/src/main/java/SourceSixtyPagesWord.java
// https://github.com/DevMeteor/RuanZhuCode
// https://github.com/CharlieJiang/SourceCodeDocxGenerator
object wordUtil {

    /**
     * @param sourcePath 代码文件夹路径:
     * @return null
     * @author 
     * @description 生成软件著作权文档
     * @date 7/4/22 1:13 PM
     */
    @JvmStatic
    fun ruanzhu(sourcePath:String){

        val name = "效能监督"
        val version = "v1.0"

        // 输出文件夹
        var outputPath = ""

        // 模板文件夹
        var templatePath = ""

        if(SystemUtil.getOS(false)=="Windows"){
            outputPath = System.getProperty("user.home") + "\\Desktop"
            templatePath = ".\\template\\template.docx"
        }else if (SystemUtil.getOS(false)=="Linux"){
            outputPath = System.getProperty("user.home") + "/Desktop"
            templatePath = "./template/template.docx"
            println(outputPath)
        }

        var s = ""
        val files = FileUtil.getFileLists(sourcePath, arrayListOf(".kt",".kt1",".java",".dart"))

        // 遍历代码文件并保存到字符串 s 中
        for (file in files) {
            val scanner = Scanner(FileInputStream(file), "UTF-8")
            while (scanner.hasNext()) s += scanner.nextLine() + "\n"
            scanner.close()
        }

        //删除“//”注释
        s = s.replace("(?<!:)\\/\\/.*".toRegex(), "")

        //删除 “/**/”注释
        s = s.replace("\\/\\*[\\s\\S]*?\\*/".toRegex(), "")

        // 删除空行
        s = s.replace("(?m)^[ \t]*\r?\n".toRegex(), "")

        val doc = XWPFDocument(FileInputStream(templatePath))
        val runs: List<XWPFRun> = doc.headerList[1].paragraphs[1].runs
        runs[0].setText(name, 0)
        runs[1].setText(version, 0)

        // 统计代码行数
        var scanner = Scanner(s)
        var total = 0
        while (scanner.hasNext()) {
            total++
            scanner.nextLine()
        }
        println("总计：" + total + "行")
        scanner = Scanner(s)
        while (scanner.hasNext()) {
            val p1 = doc.createParagraph()
            val r1: XWPFRun = p1.createRun()
            r1.fontFamily = "等线 (西文正文)"
            r1.fontSize = 10
            r1.setText(scanner.nextLine())
        }
        scanner.close()
        doc.document.body.removeP(0)
        val out = FileOutputStream(("$outputPath/$name$version").toString() + "源代码.docx")

        // 设置文档属性
        doc.properties.coreProperties.creator = "软件著作权代码文档生成器"
        doc.properties.coreProperties.lastModifiedByUser = "软件著作权代码文档生成器"
        doc.properties.coreProperties.revision = "1"
        doc.properties.coreProperties.setModified(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()))
        doc.write(out)
        out.close()
    }

}