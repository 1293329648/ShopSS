package com.cjf.web.convert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;

public class DataConverter extends StrutsTypeConverter{

    //支持转换的多种日期格式
    private final DateFormat[]dfs=
        {
            new SimpleDateFormat("yyyy-MM-dd"),
        };
    /*
     * 将指定格式字符串转换为日期类型
     */
    @Override
    public Object convertFromString(Map context, String[] values, Class toType) {
        //获取日期的字符串
        String value=(String)values[0];
        //遍历日期支持格式，进行转换
        for (int i = 0; i < dfs.length; i++) {
            try {
                return dfs[i].parse(value);
            } catch (Exception e) {
                continue;
            }
        }
        //如果遍历完毕后若没有转换成功，表明出现 转换异常
        throw new TypeConversionException();
    }

    /*
     *将日期转换为指定格式字符串
     */
    @Override
    public String convertToString(Map context, Object obj) {
        //输出的格式是yyy-MM-dd
        return new SimpleDateFormat("yyyy-MM-dd").format(obj);
    }
}