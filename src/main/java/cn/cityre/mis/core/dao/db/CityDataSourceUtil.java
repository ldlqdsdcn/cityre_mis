package cn.cityre.mis.core.dao.db;

/**
 * Created by 刘大磊 on 2017/8/23 18:05.
 */
public class CityDataSourceUtil {
    /**
     * 修改数据源名
     * @param cityCode
     * @return
     */
    public static String changeDB(String cityCode) {
        String dbname = "dataSource_" + cityCode;
        return dbname;
    }
}
