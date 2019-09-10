package com.zz.eupa_test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class liweibin_sql {
    private static int openPrint;
    private static int endPrint;
    private static String tableName;


    public void replacTextContent() throws IOException {

        String[] srcArr = {"logi", "date", "inte", "deci-10", "deci-0", "x\\(18\\)", "x\\(24\\)", "x\\(2\\)", "x\\(4\\)", "x\\(1\\)", "yes/no", "99/99/99",
                "x\\(3\\)", "x\\(8\\)", "x\\(12\\)", "X\\(8\\)", "X\\(18\\)", "X\\(12\\)", "X\\(2\\)"};
        String[] replaceArr = {"boolean", "datetime", "int", "float", "float", "varchar(18)", "varchar(24)",
                "varchar(2)", "varchar(4)", "varchar(1)", "boolean", "datetime", "varchar(3)", "varchar(8)", "varchar(12)", "varchar(8)", "varchar(18)",
                "varchar(12)", "varchar(2)"};

        liweibin_sql liweibin_sql = new liweibin_sql();
        List<sql> show = liweibin_sql.show("d:\\pt.df");

        //创建字符串拼接容器
        StringBuilder sb_field = new StringBuilder();
        sb_field.append("CREATE TABLE cankun_data (\n" +
                "id INT, -- 编号\n"
        );


        //遍历show对象，拼接字段名和数据类型
        //对不需要替换的字段名和数据类型添加到sb_field容器中
        for (int i = 0; i < show.size(); i++) {
            sql sql = show.get(i);
            if (!sql.getDataType().contains("deci-10") || !sql.getDataType().contains("deci-10")) {
                if (!sql.getDataType().contains("logi")) {
                    if (!sql.getDataType().contains("date")) {
                        if (!sql.getDataType().contains("inte")) {
                            if (!sql.getDataType().contains("deci-10")) {
                                if (!sql.getDataType().contains("deci-0")) {
                                    sb_field.append(sql.getFiledName() + " " + sql.getDataType() + ",");
                                }
                            }
                        }
                    }
                }
            }

            //对需要替换的字段名和数据类型进行替换，然后添加到sb_field容器中
            for (int i1 = 0; i1 < srcArr.length; i1++) {

                //原有的内容
                String srcStr = srcArr[i1];
                //要替换的内容
                String replaceStr = replaceArr[i1];
                if (sql.getDataType().contains(srcStr)) {
                    if (true) {
                        String replace_success = sql.getDataType().replace(srcStr, replaceStr);
                        sb_field.append(sql.getFiledName() + " " + replace_success + ",");

                    }
                }
            }
        }
        //拿到已经拼接玩的創建表sql语句
        sb_field.replace(sb_field.length() - 1, sb_field.length(), ");");


        //创建添加的sql语句
        StringBuilder sb_insert = new StringBuilder();
        sb_insert.append("INSERT INTO cankun_data(id) VALUES");


        //遍歷循環得到id
        for (int i = 0; i < show.size(); i++) {
            String id = show.get(i).getId();
            sb_insert.append("("+id+ "),");
        }
        //拿到已经拼接玩的添加sql语句
        sb_insert.replace(sb_insert.length() - 1, sb_insert.length(), ";");


/*        System.out.println("****************************");
        System.out.println(sb_field);*/
        System.out.println("****************************");
        System.out.println(sb_insert);


        //将sb_field对象输出成txt文本文件
        File outputFile = new File("d:\\pt_sql.txt");
        File insertFile = new File("d:\\pt_insert.txt");
        FileOutputStream outputFileStream = null;
        FileOutputStream insertOutputFileStream=null;
        // try to open file output.txt
        try {
            outputFileStream = new FileOutputStream(outputFile);
            insertOutputFileStream = new FileOutputStream(insertFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        byte[] bytes = sb_field.toString().getBytes("utf-8");
            outputFileStream.write(bytes);

        byte[] bytes1 = sb_insert.toString().getBytes("utf-8");
        insertOutputFileStream.write(bytes1);
    }





    public static void main(String[] args) throws IOException {
        liweibin_sql aa = new liweibin_sql();
        try {
            aa.replacTextContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filePath = "d:\\d.txt";

//      "res/";
        readTxtFile(filePath);
//        aa.change_txt();

    }

    /**
     * 功能：Java读取df文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     *
     * @param filePath
     */
    public static void readTxtFile(String filePath) {
        try {
            String encoding = "utf-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                OutputStreamWriter osw = new OutputStreamWriter( new FileOutputStream("d:\\sql.txt"));
                BufferedWriter write = new BufferedWriter(osw);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    write.write(lineTxt);
                }
                read.close();
                write.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }


    //获取所有数据
    private List<sql> show(String Path) {
        // TODO Auto-generated method stub
        /*
         * 获取表名
         * 1、获取filedName dataType √
         * 2、获取Format √
         * 3、获取Initial √
         * 4、获取Label  ColumnLabel √
         * 5、获取Description ×
         * 6、整合数据
         */
        List<String> list = new ArrayList<String>();
        File file = new File(Path);

        List<sql> msqls = new ArrayList<sql>();
        List<sql> msqls1 = new ArrayList<sql>();
        List<sql> msqls2 = new ArrayList<sql>();
        List<sql> msqls3 = new ArrayList<sql>();
        List<sql> msqls4 = new ArrayList<sql>();


        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String str = "";
            while ((str = br.readLine()) != null) {
                list.add(str);
            }

            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }

            int flag = getEndPrint(list, "Table:");
            String[] names = list.get(flag).split("[\\s]{1,}");
            tableName = names[2];
            //1、获取filedName dataType
            //寻找openPrint  endPrint
            openPrint = getOpenPrint(list, "Order");
            endPrint = getEndPrint(list, "Format");
            //遍历openPrint-endPrint 添加fieldName dataType
            for (int i = openPrint; i < endPrint; i++) {
                sql msql = new sql();
                String[] split = list.get(i).split("[\\s]{1,}");
                if (split.length > 3) {
                    msql.setId(split[1]);
                    msql.setFiledName(split[2]);
                    msql.setDataType(split[3]);
                    msqls1.add(msql);
                }

            }
            for (int i = 0; i < msqls1.size(); i++) {
                System.out.println(msqls1.get(i));
            }
            //2、获取Format
            //endPrint 变 openPrint
            openPrint = endPrint + 2;
            //寻找endPrint
            endPrint = getEndPrint(list, "Initial");
            //添加Format
            for (int i = openPrint; i < endPrint; i++) {
                sql msql = new sql();
                String[] split = list.get(i).split("[\\s]{1,}");
                if (split.length > 1) {
                    msql.setFormat(split[1]);
                    msqls2.add(msql);
                }

            }

            //3、获取Initial
            //endPrint 变 openPrint
            openPrint = endPrint + 2;
            //寻找endPrint
            endPrint = getEndPrint(list, "Column Label");
            //添加Initial
            for (int i = openPrint; i < endPrint; i++) {
                sql msql = new sql();
                String[] split = list.get(i).split("[\\s]{1,}");
                if (split.length > 1) {
                    msql.setInitial(split[1]);
                    msqls3.add(msql);
                } else {
                    msql.setInitial("");
                    msqls3.add(msql);
                }

            }

            //4、获取Label  ColumnLabel
            //endPrint 变 openPrint
            openPrint = endPrint + 2;
            int one = foundPrintOfLabel(list.get(endPrint))[0];
            int two = foundPrintOfLabel(list.get(endPrint))[1];
//			System.out.println(one + "  " + two);
            //寻找endPrint
            endPrint = getEndPrint(list, "INDEX SUMMARY");
            //添加Label  ColumnLabel
            for (int i = openPrint; i < endPrint - 2; i++) {
                sql msql = new sql();
                msql.setLabel(list.get(i).substring(one, two - 1).trim());
                msql.setColumnLabel(list.get(i).substring(two).trim());
                msqls4.add(msql);
            }

            //5、获取Description

            //6、整合数据
            for (int i = 0; i < msqls.size(); i++) {
                sql msql = new sql();
                msql.setFiledName(msqls1.get(i).getFiledName());
                msql.setDataType(msqls1.get(i).getDataType());
                msql.setFormat(msqls2.get(i).getFormat());
                msql.setInitial(msqls3.get(i).getInitial());
                msql.setLabel(msqls4.get(i).getLabel());
                msql.setColumnLabel(msqls4.get(i).getColumnLabel());
                msqls.add(msql);
            }
        } catch (IOException e) {
            // TODO: handle exception
        } finally {
            System.out.println(openPrint + " and " + endPrint);
            return msqls1;
        }

    }

    //openPrint found
    private static int getOpenPrint(List<String> list, String found) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).indexOf(found) != -1) {
                return i + 2;
            }
        }
        return 0;
    }

    //endPrint found
    private static int getEndPrint(List<String> list, String found) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).indexOf(found) != -1) {
                return i;
            }
        }
        return 0;
    }

    //寻找断点 第4步专用
    private static int[] foundPrintOfLabel(String str) {
        int one = str.indexOf("Label");
        int two = str.indexOf("Column Label");
        int[] ints = {one, two};
        return ints;
    }


}
