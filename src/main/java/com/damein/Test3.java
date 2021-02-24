package com.damein;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Test3 {


    private static final String WAV_FILE_EXT1 = "http://10.30.1.153:8080";
    private static final String WAV_FILE_EXT2 = "http://10.30.1.153:8068/cincc-serv/media/download?";

    private static final String STR_LENGTH = "http://10.30.1.153:8068/cincc-serv/media/download?msServer=ms3&filePath=";

    private static final String START_WITH = "666666/servicerecord/";

    private static final String ACCESS_CODE1 = "000001666666006004";
    private static final List<String> DATE_LIST1 = Arrays.asList(
            "20200218", "20200227", "20200312", "20200329", "20200410", "20200423", "20200430", "20200512", "20200519", "20200526", "20200604", "20200611", "20200623", "20200709", "20200807", "20200929",
            "20200219", "20200302", "20200313", "20200330", "20200411", "20200424", "20200506", "20200513", "20200520", "20200527", "20200605", "20200612", "20200701", "20200710", "20200813",
            "20200221", "20200304", "20200314", "20200331", "20200413", "20200425", "20200507", "20200514", "20200521", "20200528", "20200606", "20200613", "20200702", "20200720", "20200814",
            "20200223", "20200306", "20200323", "20200401", "20200414", "20200426", "20200508", "20200515", "20200522", "20200529", "20200607", "20200614", "20200703", "20200727", "20200921",
            "20200224", "20200307", "20200324", "20200402", "20200415", "20200427", "20200509", "20200516", "20200523", "20200601", "20200608", "20200615", "20200704", "20200730", "20200925",
            "20200225", "20200309", "20200325", "20200408", "20200420", "20200428", "20200510", "20200517", "20200524", "20200602", "20200609", "20200616", "20200706", "20200731", "20200926",
            "20200226", "20200310", "20200326", "20200409", "20200422", "20200429", "20200511", "20200518", "20200525", "20200603", "20200610", "20200617", "20200707", "20200801", "20200927"

    );

    private static final String ACCESS_CODE2 = "000001666666005002";
    private static final List<String> DATE_LIST2 = Arrays.asList(
            "20191107", "20191127", "20191231", "20200208", "20200225", "20200312", "20200327", "20200411", "20200426", "20200511", "20200526", "20200610", "20200625", "20200710", "20200725", "20200809", "20200824",
            "20191108", "20191128", "20200103", "20200209", "20200226", "20200313", "20200328", "20200412", "20200427", "20200512", "20200527", "20200611", "20200626", "20200711", "20200726", "20200810", "20200825",
            "20191111", "20191129", "20200107", "20200211", "20200227", "20200314", "20200329", "20200413", "20200428", "20200513", "20200528", "20200612", "20200627", "20200712", "20200727", "20200811", "20200826",
            "20191112", "20191202", "20200108", "20200212", "20200228", "20200315", "20200330", "20200414", "20200429", "20200514", "20200529", "20200613", "20200628", "20200713", "20200728", "20200812", "20200827",
            "20191113", "20191203", "20200109", "20200213", "20200229", "20200316", "20200331", "20200415", "20200430", "20200515", "20200530", "20200614", "20200629", "20200714", "20200729", "20200813", "20200828",
            "20191114", "20191204", "20200110", "20200214", "20200302", "20200317", "20200401", "20200416", "20200501", "20200516", "20200531", "20200615", "20200630", "20200715", "20200730", "20200814", "20200829",
            "20191115", "20191205", "20200113", "20200215", "20200303", "20200318", "20200402", "20200417", "20200502", "20200517", "20200601", "20200616", "20200701", "20200716", "20200731", "20200815", "20200830",
            "20191116", "20191206", "20200114", "20200216", "20200304", "20200319", "20200403", "20200418", "20200503", "20200518", "20200602", "20200617", "20200702", "20200717", "20200801", "20200816", "20200831",
            "20191118", "20191209", "20200115", "20200217", "20200305", "20200320", "20200404", "20200419", "20200504", "20200519", "20200603", "20200618", "20200703", "20200718", "20200802", "20200817",
            "20191119", "20191212", "20200116", "20200218", "20200306", "20200321", "20200405", "20200420", "20200505", "20200520", "20200604", "20200619", "20200704", "20200719", "20200803", "20200818",
            "20191120", "20191213", "20200117", "20200219", "20200307", "20200322", "20200406", "20200421", "20200506", "20200521", "20200605", "20200620", "20200705", "20200720", "20200804", "20200819",
            "20191121", "20191216", "20200119", "20200220", "20200308", "20200323", "20200407", "20200422", "20200507", "20200522", "20200606", "20200621", "20200706", "20200721", "20200805", "20200820",
            "20191122", "20191217", "20200120", "20200221", "20200309", "20200324", "20200408", "20200423", "20200508", "20200523", "20200607", "20200622", "20200707", "20200722", "20200806", "20200821",
            "20191125", "20191226", "20200121", "20200223", "20200310", "20200325", "20200409", "20200424", "20200509", "20200524", "20200608", "20200623", "20200708", "20200723", "20200807", "20200822",
            "20191126", "20191230", "20200122", "20200224", "20200311", "20200326", "20200410", "20200425", "20200510", "20200525", "20200609", "20200624", "20200709", "20200724", "20200808", "20200823"
    );


    public static void main(String[] args) throws IOException {

        String inputCallRecFilePath = "/data/by/call/";
        String outFilePath = "/data/by/";

//        String inputCallRecFilePath = "D://data/by/call/";
//        String outFilePath = "D://data/by/";

        String outWavPath = "/data22/line1000record_by/";

        List<List<String>> callRecList = queryFileList(inputCallRecFilePath);


        List<ByCallRec> callRecs = callRecList.stream().map(n -> ByCallRec.builder()
                .id(GV.l(n.get(0)))
                .callTime(GV.l(n.get(1)))
                .callTimeStr(handleTimeStr(GV.l(n.get(1))))
                .url(n.get(2))
                .build()).collect(Collectors.toList());
        Map<String, List<ByCallRec>> callDateMap = callRecs.stream().collect(Collectors.groupingBy(ByCallRec::getCallTimeStr));


        String resultName = "BY_" + "_test_mkdir_shell_" + DateUtil.convertDateToString(DateUtil.DATE_TIME_PATTERN, DateUtil.getCurrentDate()) + ".sh";
        String resultName1 = "BY_" + "_test_71_shell_" + DateUtil.convertDateToString(DateUtil.DATE_TIME_PATTERN, DateUtil.getCurrentDate()) + ".sh";
        String resultName2 = "BY_" + "_test_225_shell_" + DateUtil.convertDateToString(DateUtil.DATE_TIME_PATTERN, DateUtil.getCurrentDate()) + ".sh";
        String resultName3 = "BY_" + "_test_600_shell_" + DateUtil.convertDateToString(DateUtil.DATE_TIME_PATTERN, DateUtil.getCurrentDate()) + ".sh";


        List<String> mkdirShell = new ArrayList<>();
        List<String> test71Shell = new ArrayList<>();
        List<String> test225Shell = new ArrayList<>();
        List<String> test1000Shell = new ArrayList<>();

        //读出来Excel
        File file = new File(outFilePath + resultName);
        FileWriter writer = new FileWriter(file);

        writer.write("#!/bin/bash");
        writer.write("\n");


        //读出来Excel
        File file1 = new File(outFilePath + resultName1);
        FileWriter writer1 = new FileWriter(file1);
        writer1.write("#!/bin/bash");
        writer1.write("\n");

        File file2 = new File(outFilePath + resultName2);
        FileWriter writer2 = new FileWriter(file2);
        writer2.write("#!/bin/bash");
        writer2.write("\n");

        File file3 = new File(outFilePath + resultName3);
        FileWriter writer3 = new FileWriter(file3);
        writer3.write("#!/bin/bash");
        writer3.write("\n");

        Set<String> strings = callDateMap.keySet();
        strings.forEach(date -> mkdirShell.add("mkdir -p " + outWavPath + date));


        callDateMap.forEach((date, val) -> {

            String tmpFileName = outWavPath + date + "/";
            val.forEach(rec -> {

                String originUrl = rec.getUrl();
                originUrl = originUrl.replace(WAV_FILE_EXT1, "");
                if (originUrl.startsWith(WAV_FILE_EXT2)) {
                    originUrl = originUrl.substring(STR_LENGTH.length());
                    if (originUrl.startsWith("=")) {
                        originUrl = originUrl.substring(1);
                    }
                }

                String recFileName = rec.getId() + "";
                if (originUrl.endsWith(".wav")) {
                    recFileName = recFileName + ".wav";
                } else {
                    recFileName = recFileName + ".mp3";
                }

                //判断文件在71还是225
                boolean isFlag71 = isFlag(originUrl);

                if (originUrl.startsWith("/home")) {
                    test1000Shell.add("sshpass -f /root/.225.txt scp -P 50022 " + originUrl
                            + " zlsduser@10.30.12.225:" + tmpFileName + recFileName);
                } else if (isFlag71) {
                    //71
                    if (!StringUtils.isEmpty(originUrl)) {
                        test71Shell.add("sshpass -f /root/.225.txt scp -P 50022 /home/media/media/" + originUrl
                                + " zlsduser@10.30.12.225:" + tmpFileName + recFileName);
                    }
                } else {
                    //225
                    if (originUrl.startsWith(START_WITH + ACCESS_CODE1)) {
                        test225Shell.add("cp /data23/xinfang_media/n1/home/media/media/" + originUrl + " " + tmpFileName + recFileName);
                    } else {
                        test225Shell.add("cp /data24/xinfang_media/n1/home/media/media/" + originUrl + " " + tmpFileName + recFileName);
                    }
                }
            });

        });


        //创建文件目录
        extracted(mkdirShell, writer);
        //创建71shell目录
        extracted(test71Shell, writer1);
        //创建225shell目录
        extracted(test225Shell, writer2);
        //创建1000shell目录
        extracted(test1000Shell, writer3);
        System.out.println("执行完毕");
    }

    private static void extracted(List<String> mkdirShell, FileWriter writer) throws IOException {
        for (int i = 0; i < mkdirShell.size(); i++) {
            String str = mkdirShell.get(i);
            String percent = countPercent(i, mkdirShell.size());
            writer.write(str);
            writer.write("\n");
            writer.write(" echo ' 已完成案件百分比：" + percent + "' ");
            writer.write("\n");
        }
        writer.write(" echo ' 已完成案件百分比：" + "100.00%" + "' ");
        writer.write("\n");
        writer.flush();
        writer.close();
    }

    private static String countPercent(int i, int billSize) {
        return new BigDecimal(i * 100).divide(new BigDecimal(billSize), 2, BigDecimal.ROUND_HALF_UP).toString() + "%";
    }

    private static String handleTimeStr(long time) {
        try {
            return DateUtil.convertDateToString(DateUtil.DATE, new Date(time));
        } catch (Exception e) {
            log.error(" error = {0} ", e);
        }
        return "";
    }

    private static boolean isFlag(String originUrl) {

        //有迁移录音逻辑
        for (String str1 : DATE_LIST1) {
            if (originUrl.startsWith(START_WITH + ACCESS_CODE1 + "/" + str1)) {
                return false;
            }
        }

        for (String str2 : DATE_LIST2) {
            if (originUrl.startsWith(START_WITH + ACCESS_CODE2 + "/" + str2)) {
                return false;
            }
        }
        return true;
    }

    private static List<List<String>> queryFileList(String filePath) {

        List<List<String>> result = new ArrayList<>();

        try {
            File inputFile = new File(filePath);
            File[] files = inputFile.listFiles();
            assert files != null;
            for (File file : files) {
                List<List<String>> tmpLst = readFile(file.getAbsolutePath(), file.getName());
                result.addAll(tmpLst);
            }
        } catch (Exception e) {
            log.error(" queryFileList error = {0} ", e);
        }
        return result;
    }

    private static List<List<String>> readFile(String filePath, String fileName) {


        if (fileName.endsWith(".xlsx") || fileName.endsWith(".xlx")) {
            return readExcelFile(filePath);
        } else if (fileName.endsWith(".csv")) {
            return readCsvFile(filePath);
        }
        return new ArrayList<>();
    }

    private static List<List<String>> readExcelFile(String filePath) {

        log.info("--------------解析 excel 文件-----------");
        System.out.println("--------------解析 excel 文件-----------");
        List<List<String>> dataList = new ArrayList<>();

        try {
            ExcelUtil ex = new ExcelUtil();
            dataList = ex.read(new File(filePath));
        } catch (Exception e) {
            log.error("解析 excel 文件异常", e);
        }
        System.out.println(" excel 表格中数据总行数：" + dataList.size());
        log.info(" excel 表格中数据总行数：{}", dataList.size());
        return dataList;
    }


    private static List<List<String>> readCsvFile(String filePath) {

        log.info("--------------解析csv文件-----------");
        System.out.println("--------------解析csv文件-----------");
        //存放csv每行数据
        List<String[]> dataList = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
            String[] nextLine;
            int index = 0;
            while ((nextLine = reader.readNext()) != null) {
                if (index > 0 && !StringUtils.isAllBlank(nextLine)) {
                    dataList.add(nextLine);
                }
                index++;
            }
            log.info("csv表格中数据总行数：{}", dataList.size());
        } catch (IOException e) {
            log.error("解析csv文件异常", e);
        }
        List<List<String>> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(dataList)) {
            dataList.forEach(str -> list.add(Arrays.asList(str)));
        }
        return list;
    }
}