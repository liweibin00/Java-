package com.zz.sql_plus;

import java.io.*;

public class test {
    public void replacTextContent(String path) throws IOException {

        String[] srcArr = {"logi", "date", "inte", "deci-10", "deci-0", "x\\(18\\)", "x\\(24\\)", "x\\(2\\)", "x\\(4\\)", "x\\(1\\)", "yes/no", "99/99/99",
                "x\\(3\\)", "x\\(8\\)", "x\\(12\\)", "X\\(8\\)", "X\\(18\\)", "X\\(12\\)", "X\\(2\\)"};
        String[] replaceArr = {"boolean", "datetime", "int", "float", "float", "varchar(18)", "varchar(24)",
                "varchar(2)", "varchar(4)", "varchar(1)", "boolean", "datetime", "varchar(3)", "varchar(8)", "varchar(12)", "varchar(8)", "varchar(18)",
                "varchar(12)", "varchar(2)"};

        for (int i = 0; i < srcArr.length; i++) {
            //原有的内容
            String srcStr = srcArr[i];
            //要替换的内容
            String replaceStr = replaceArr[i];
            // 读
            File file = new File(path);
            FileReader in = new FileReader(file);
            BufferedReader bufIn = new BufferedReader(in);
            // 内存流, 作为临时流
            CharArrayWriter tempStream = new CharArrayWriter();
            // 替换
            String line = null;
            while ((line = bufIn.readLine()) != null) {
                // 替换每行中, 符合条件的字符串
                line = line.replaceAll(srcStr, replaceStr);
                String[] split = new String[0];
                try {
                    split = line.split(" ");
                    System.out.println(split[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                // 将该行写入内存
                tempStream.write(line);
                // 添加换行符
                tempStream.append(System.getProperty("line.separator"));
            }
            // 关闭 输入流
            bufIn.close();
            // 将内存中的流 写入 文件
            FileWriter out = new FileWriter(file);
            //如果文件不存在，则自动生成
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            tempStream.writeTo(out);
            out.close();
            System.out.println("====path:" + path);

        }

    }


    //将df文件转换成txt文件
    public void change_txt() throws IOException {
        FileInputStream fis = new FileInputStream("d:\\pt.df");//从a.txt中读出
        FileOutputStream fos = new FileOutputStream("d:\\test.txt");//写到test.txt中去
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        BufferedWriter write = new BufferedWriter(new OutputStreamWriter(fos));
        String temp;
        while ((temp = reader.readLine()) != null) {//一次读一行
            write.write(temp);
        }
        reader.close();
        write.close();
    }


    //截取字符串拼接sql语句

    /**
     * 截取字符串str中指定字符 strStart、strEnd之间的字符串
     *
     * @return
     */
    public void subString() throws IOException {
        FileInputStream fis = new FileInputStream("d:\\test.txt");//从a.txt中读出
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
//        FileOutputStream fos = new FileOutputStream("d:\\sql.txt");//写到test.txt中去
//        BufferedWriter write = new BufferedWriter(new OutputStreamWriter(fos));
        String temp;
        FileWriter fw = null;
        File f = new File("d:\\sql.txt");
        String[] strStart = {"10"};
        String[] strEnd = {"90"};
        while ((temp = reader.readLine()) != null) {//一次读一行
            for (int i = 0; i < strStart.length; i++) {
                /* 找出指定的2个字符在 该字符串里面的 位置 */
                int strStartIndex = temp.indexOf(strStart[i]);
                int strEndIndex = temp.indexOf(strEnd[i]);


                /* index 为负数 即表示该字符串中 没有该字符 */
                if (strStartIndex < 0) {
                    System.out.println("字符串 :---->" + temp + "<---- 中不存在 " + strStart + ", 无法截取目标字符串");
                }
                if (strEndIndex < 0) {
                    System.out.println("字符串 :---->" + temp + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串");
                }
                /* 开始截取 */
                String result = temp.substring(strStartIndex, strEndIndex).substring(strStart.length);

                //去处多余的空格，用于分装字符串中间截取出来内容的容器
                StringBuffer sb = new StringBuffer();//用其他方法实现
                int flag;
                for (int i2 = 0; i2 < result.length(); i2++) {
                    flag = 0;
                    if (result.charAt(i2) != ' ') {
                        sb.append(result.charAt(i2));
                    } else {
                        flag = 1;
                    }
                    try {
                        if (result.charAt(i2) == ' ' && result.charAt(i2 + 1) != ' ') {
                            sb.append(" ");
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }

                String fieldBuild_use = sb.toString();
                //用于封装字段的build容器
                StringBuilder sb_field = new StringBuilder();
                int count = 2;


                //创建sql语句
                sb_field.append("CREATE TABLE cankun_data (\n" +
                        "id INT, -- 编号\n"
                );
                String[] split = sb.toString().split(" ");
                for (int i1 = 1; i1 < split.length; i1 += 4) {
                    System.out.println(split[i1]);
                    if (fieldBuild_use.charAt(i1) == ' ' && fieldBuild_use.charAt(i1 + 1) != ' ') {
                        sb_field.append(split[i1] + " " + split[count]).append(",\n");

                    }
                    count += 4;
                }
                sb_field.replace(sb_field.length() - 2, sb_field.length(), ");");
                String s = sb_field.toString();

                try {
                    if(!f.exists()){
                        f.createNewFile();
                    }
                    fw = new FileWriter(f);
                    BufferedWriter out = new BufferedWriter(fw);
                    out.write(s, 0, s.length()-1);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("end");
             /*   // FileInputStream sqlFs = new FileInputStream(s);//从sb_field中读出
                //BufferedReader sqlReader = new BufferedReader(new InputStreamReader(sqlFs));
                FileOutputStream fos = new FileOutputStream("d:\\sql.txt");//写到test.txt中去
                BufferedWriter write = new BufferedWriter(new OutputStreamWriter(fos));
                while ((sqlOut = ) != null) {//一次读一行
                    write.write(sqlOut);
                }
                if (sqlReader == null) {
                    reader.close();
                }
                if (write == null) {
                    write.close();*/
                }

            }
            if (reader == null) {
                reader.close();
            }


        }

       /*
        for (int i = 0; i < strStart.length; i++) {
            *//* 找出指定的2个字符在 该字符串里面的 位置 *//*
            int strStartIndex = fisString.indexOf(strStart[i]);
            int strEndIndex = fisString.indexOf(strEnd[i]);

            *//* index 为负数 即表示该字符串中 没有该字符 *//*
            if (strStartIndex < 0) {
                System.out.println("字符串 :---->" + fisString + "<---- 中不存在 " + strStart + ", 无法截取目标字符串");
            }
            if (strEndIndex < 0) {
                System.out.println("字符串 :---->" + fisString + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串");
            }
            *//* 开始截取 *//*
            String result = fisString.substring(strStartIndex, strEndIndex).substring(strStart.length);
            System.out.println(result);
        }*/



    //将txt文件转换成execl文件
/*    public void txtToExcel() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(
                "D:\\test.txt")));
        System.out.println("fangwendaotext");
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet("pldrxkxxmb");
        HSSFRow firstrow = null;
        HSSFCell[] firstcell = null;
        String str = null;
        int i = 0;
        while ((str = br.readLine()) != null) {
            firstrow = sheet.createRow(i);
            firstcell = new HSSFCell[1];
            firstcell[0] = firstrow.createCell(0);
            firstcell[0].setCellValue(str);
            i++;
        }
        OutputStream out = new FileOutputStream("D:\\pldrxkxxmb.xls");
        System.out.println("fangwendaotext");
        hwb.write(out);
        out.close();
        br.close();

    }*/


    public static void main(String[] args) throws IOException {
        test aa = new test();
        try {
            aa.replacTextContent("d:\\pt.df");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filePath = "d:\\pt.df";

//      "res/";
        readTxtFile(filePath);
        aa.change_txt();
        aa.subString();
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
            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    System.out.println(lineTxt);
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

    }
}
