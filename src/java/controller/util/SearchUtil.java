/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class SearchUtil {

    public static Date convert(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.parse(date);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static java.sql.Date getSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static String format(Date date) {//"yyyy-MM-dd"
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(date);
    }

    public static java.util.Date getUtilDate(Date d) {
        return new java.util.Date(format(d));
    }

    public static String addConstraint(String beanAbrev, String attributName, String operator, Object value) {
        if (value != null) {
            return " AND " + beanAbrev + "." + attributName + " " + operator + "'" + value + "'";
        }
        return "";
    }

    public static String addConstraintMinMax(String beanAbrev, String attributName, Object valueMin, Object valueMax) {
        String requette = "";
        if (valueMin != null) {
            requette += " AND " + beanAbrev + "." + attributName + ">='" + valueMin + "'";
        }
        if (valueMax != null ) {
            requette += " AND " + beanAbrev + "." + attributName + "<='" + valueMax + "'";
        }
        return requette;
    }

    public static String addConstraintDate(String beanAbrev, String attributeName, String operator, Date value) {
        return addConstraint(beanAbrev, attributeName, operator, getSqlDate(value));
    }

    public static String addConstraintMinMaxDate(String beanAbrev, String attributName, Date valueMin, Date valueMax) {

        return addConstraintMinMax(beanAbrev, attributName, getSqlDate(valueMin), getSqlDate(valueMax));
    }
}
