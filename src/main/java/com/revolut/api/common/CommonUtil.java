package com.revolut.api.common;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author : Vivek Mehta
 * Date: 26 Sep 2019
 * Details: Application Common utils to provide all common methods
 */
public class CommonUtil
{
    public static boolean isMapValueNotBlank(Map<?, ?> object, String key) throws Exception
    {
        return (object != null && object.get(key) != null && StringUtils.isNotBlank(getStringValue(object.get(key))));
    }

    public static boolean isMapValueNotNull(Map<?, ?> object, String key) throws Exception
    {
        return (object != null && object.get(key) != null);
    }

    public static String getStringValue(Object object) throws Exception
    {
        String result = "";

        if (object != null)
        {
            result = String.valueOf(object);
        }

        return result;
    }

    public static Integer getIntegerValue(Object object) throws Exception
    {
        int result = 0;

        if (object != null)
        {
            result = Integer.parseInt(String.valueOf(object));
        }

        return result;
    }
}
