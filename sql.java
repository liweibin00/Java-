package com.zz.eupa_test;

import com.sun.org.apache.bcel.internal.generic.RETURN;

public class sql {

    //id
    private String id;

    //字段名
    private String FiledName;

    //数据类型
    private String DataType;  //char float

    //规则
    private String Format; //18 9.9999

    private String Initial;

    private String Label;

    private String ColumnLabel;

    private String Description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFiledName() {
        return FiledName;
    }

    public void setFiledName(String filedName) {
        FiledName = filedName;
    }

    public String getDataType() {
        return DataType;
    }

    public void setDataType(String dataType) {
        DataType = dataType;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

    public String getInitial() {
        return Initial;
    }

    public void setInitial(String initial) {
        Initial = initial;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public String getColumnLabel() {
        return ColumnLabel;
    }

    public void setColumnLabel(String columnLabel) {
        ColumnLabel = columnLabel;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public sql() {
    }

    public sql(String id, String filedName, String dataType, String format, String initial, String label, String columnLabel, String description) {
        this.id = id;
        FiledName = filedName;
        DataType = dataType;
        Format = format;
        Initial = initial;
        Label = label;
        ColumnLabel = columnLabel;
        Description = description;
    }

    @Override
    public String toString() {
        return "sql{" +
                "id='" + id + '\'' +
                ", FiledName='" + FiledName + '\'' +
                ", DataType='" + DataType + '\'' +
                ", Format='" + Format + '\'' +
                ", Initial='" + Initial + '\'' +
                ", Label='" + Label + '\'' +
                ", ColumnLabel='" + ColumnLabel + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
