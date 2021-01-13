package com.damein;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Test2 {


    private static final List<String> DATE_LIST = Arrays.asList(
            "201040003-9999-20200607-9999100004",
            "201040003-9999-20200607-9999100005",
            "201040003-9999-20200607-9999200002",
            "201040003-9999-20200607-9999900003",
            "201040003-9999-20200610-9999100004",
            "201040003-9999-20200610-9999100005",
            "201040003-9999-20200610-9999200002",
            "201040003-9999-20200610-9999900003",
            "201040003-9999-20200615-9999100004",
            "201040003-9999-20200615-9999100005",
            "201040003-9999-20200615-9999200002",
            "201040003-9999-20200615-9999900003"
    );


    public static void main(String[] args) throws IOException {
//
//        String inputCallRecFilePath = "/data/gz/call/";
//        String inputBillRecFilePath = "/data/gz/rec/";
//        String outFilePath = "/data/gz/";

        String publicPath = "D://data/gz/1000/";
        String acdpath = "D:\\data\\gz\\acd2/";
        String outFilePath = "D://data/gz/";

        List<List<String>> publicTmpList = queryFileList(publicPath);
        List<List<String>> acdTmpList = queryFileList(acdpath);


        List<String> publicList = publicTmpList.stream().map(n -> n.get(0)).collect(Collectors.toList());
        List<String> acdList = acdTmpList.stream().map(n -> n.get(0)).collect(Collectors.toList());

        System.out.println(publicList.size());
        System.out.println(acdList.size());


        String resultName1 = "1000线执行sql" + ".sql";
        String resultName2 = "acd线执行sql" + ".sql";

        //读出来Excel
        File file1 = new File(outFilePath + resultName1);
        FileWriter writer1 = new FileWriter(file1);
        writer1.write("\n");


        File file2 = new File(outFilePath + resultName2);
        FileWriter writer2 = new FileWriter(file2);
        writer2.write("\n");


        writer2.write("-- ACD线执行SQL ");
        writer2.write("\n");

        writer1.write("-- 1000线执行SQL ");
        writer1.write("\n");


        publicList.removeAll(DATE_LIST);
        List<List<String>> publiclists = averageAssign(publicList, 3);
        publiclists.forEach(pubsql -> {

            try {
                if (!DATE_LIST.contains(pubsql)) {
                    writer1.write("----------------------" + pubsql + "----------------------");
                    handlesql(writer1, pubsql);
                    writer1.write("\n");
                    writer1.write("\n");
                }
            } catch (Exception e) {
                log.error(" error = {0} ", e);
            }
        });

        acdList.removeAll(DATE_LIST);
        List<List<String>> acdlists = averageAssign(acdList, 10);
        acdlists.forEach(acdsql -> {

            try {
                writer2.write("----------------------" + acdsql + "----------------------");
                writer2.write("\n");
//                writer2.write("----------------------" + "需要在1000线执行" + "----------------------");
//                handlesql(writer2, acdsql);
                writer2.write("----------------------" + "需要在ACD线执行" + "----------------------");
                handlesql(writer2, acdsql);
                writer2.write("\n");
                writer2.write("\n");
            } catch (Exception e) {
                log.error(" error = {0} ", e);
            }
        });

        try {
            writer2.write("----------------------" + DATE_LIST + "----------------------");
            writer2.write("\n");
            writer2.write("----------------------" + "需要在1000线执行" + "----------------------");
            handlesql(writer2, DATE_LIST);
            writer2.write("----------------------" + "需要在ACD线执行" + "----------------------");
            handlesql(writer2, DATE_LIST);
            writer2.write("\n");
            writer2.write("\n");
        } catch (Exception e) {
            log.error(" error = {0} ", e);
        }

        writer2.flush();
        writer2.close();

        writer1.flush();
        writer1.close();
//        acdList.forEach(str -> {
//            try {
//
//                writer.write("mkdir -p " + outWavPath + batchName + "/" + tmpFileName);
//                writer.write("\n");
//            } catch (Exception e) {
//                log.error(" error = {0} ", e);
//            }
//        });
//        writer.write("\n");
//        writer.write("\n");
//        writer.flush();
//        writer.close();
    }

    private static void handlesql(FileWriter writer2, List<String> acdsql) throws IOException {
        writer2.write("\n");
//        writer2.write("-- 查询批次下案件数量");
//        writer2.write("\n");
//        writer2.write("select count(1) from rb_case_info_gzh where attr1 = '" + acdsql + "';");
//        writer2.write("\n");
        writer2.write("-- 查询批次下案件信息");
        writer2.write("\n");
        writer2.write("select attr1,attr2,borrower_name,borrower_phone,bill_code from rb_case_info_gzh where attr1 in (" + handleSqlList(acdsql) + "）;");
        writer2.write("\n");
//        writer2.write("-- 查询批次下呼叫记录数量");
//        writer2.write("\n");
//        writer2.write("select count(1) from rb_case_call_record_gzh  where batch_no = '" + acdsql + "';");
//        writer2.write("\n");
        writer2.write("-- 查询批次下呼叫记录信息");
        writer2.write("\n");
        writer2.write("select batch_no,bill_code,call_id,call_time,phone,url from rb_case_call_record_gzh  where batch_no  in (" + handleSqlList(acdsql) + "） and result = 1;");
        writer2.write("\n");
    }

    private static String handleSqlList(List<String> acdsql) {
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isNotEmpty(acdsql)) {
            for (int i = 0; i < acdsql.size(); i++) {
                if (i == acdsql.size() - 1) {
                    sb.append("'").append(acdsql.get(i)).append("'");
                } else {
                    sb.append("'").append(acdsql.get(i)).append("'").append(",");
                }
            }
            return sb.toString();
        }
        return "";
    }

    public static <T> List<List<T>> averageAssign(List<T> list, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        int remaider = list.size() % n;  //(先计算出余数)
        int number = list.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remaider > 0) {
                value = list.subList(i * number + offset, (i + 1) * number + offset + 1);
                remaider--;
                offset++;
            } else {
                value = list.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
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
