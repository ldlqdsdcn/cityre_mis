package cn.cityre.mis.core.dao.db;

import com.jolbox.bonecp.BoneCPDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.*;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;

/**
 * 实现一个实现ApplicationContextAware和ApplicationListener接口的类DynamicDataSourceC3p0，
 * 实现ApplicationContextAware是为了得到ApplicationContext，
 * 实现了ApplicationListener是为了配置spring的加载事件。
 */
public class DynamicCreateDataSourceBean implements ApplicationContextAware, ApplicationListener {
    private String filename = "/datasource/city.properties";
    private static final String DATABASE_PROP_FILE = "database.properties";
    private ConfigurableApplicationContext app;

    private DynamicDataSource dynamicDataSource;

    public void setDynamicDataSource(DynamicDataSource dynamicDataSource) {
        this.dynamicDataSource = dynamicDataSource;
    }

    public void setApplicationContext(ApplicationContext app) throws BeansException {
        this.app = (ConfigurableApplicationContext) app;
    }


    public void onApplicationEvent(ApplicationEvent event) {
        // 如果是容器刷新事件OR Start Event
        if (event instanceof ContextRefreshedEvent) {
            try {
                regDynamicBean();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void regDynamicBean() throws IOException {
        // 解析属性文件，得到数据源Map
        Map<String, DataSourceInfo> mapCustom = parsePropertiesFile();
        // 把数据源bean注册到容器中
        addSourceBeanToApp(mapCustom);
    }

    /**
     * 功能说明：根据DataSource创建bean并注册到容器中
     *
     * @param
     * @param mapCustom
     */
    private void addSourceBeanToApp(Map<String, DataSourceInfo> mapCustom) {
        DefaultListableBeanFactory acf = (DefaultListableBeanFactory) app
                .getAutowireCapableBeanFactory();
        Iterator<String> iter = mapCustom.keySet().iterator();

        Map<Object, Object> targetDataSources = new LinkedHashMap<Object, Object>();
        BeanDefinitionBuilder bdb;


        // 根据数据源得到数据，动态创建数据源bean 并将bean注册到applicationContext中去
        while (iter.hasNext()) {

            //  bean ID
            String beanKey = iter.next();
            //  创建bean
            // bdb = BeanDefinitionBuilder.rootBeanDefinition(DATASOURCE_BEAN_CLASS);


            bdb = BeanDefinitionBuilder.rootBeanDefinition(BoneCPDataSource.class);
            bdb.getBeanDefinition().setAttribute("id", beanKey);
            bdb.addPropertyValue("driverClassName", "com.mysql.jdbc.Driver");
            bdb.addPropertyValue("url", mapCustom.get(beanKey).connUrl);
            bdb.addPropertyValue("username", mapCustom.get(beanKey).userName);
            bdb.addPropertyValue("password", mapCustom.get(beanKey).password);
            bdb.addPropertyValue("maxActive", 100);
            bdb.addPropertyValue("initialSize", 10);
            bdb.addPropertyValue("removeAbandonedTimeout", 60);
            bdb.addPropertyValue("minEvictableIdleTimeMillis", 30000);
            bdb.addPropertyValue("minIdle", 10);
            bdb.addPropertyValue("maxWait", 10000);
            bdb.addPropertyValue("removeAbandoned", true);
            bdb.addPropertyValue("validationInterval", 30000);
            bdb.addPropertyValue("timeBetweenEvictionRunsMillis", 30000);
            bdb.addPropertyValue("validationQuery", "SELECT 1");
            bdb.addPropertyValue("testOnBorrow", true);
            bdb.addPropertyValue("testOnReturn", true);
            bdb.addPropertyValue("testWhileIdle", true);

            //  注册bean
            acf.registerBeanDefinition(beanKey, bdb.getBeanDefinition());

            //  放入map中，注意一定是刚才创建bean对象
//            Object obj = app.getBean(beanKey);
            targetDataSources.put(beanKey, app.getBean(beanKey));

        }
        //  将创建的map对象set到 targetDataSources；
        dynamicDataSource.setTargetDataSources(targetDataSources);

        //  必须执行此操作，才会重新初始化AbstractRoutingDataSource 中的 resolvedDataSources，也只有这样，动态切换才会起效
        dynamicDataSource.afterPropertiesSet();

    }

    /**
     * 功能说明：GET ALL SM_STATIONS FROM DB1
     *
     * @return
     * @throws IOException
     */
    private Map<String, DataSourceInfo> parsePropertiesFile()
            throws IOException {


        Map<String, DataSourceInfo> mds = new HashMap<String, DataSourceInfo>();
//        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        InputStream in = this.getClass().getResourceAsStream(filename);
//        InputStream in  = new FileInputStream(path+filename);
        Properties p = new Properties();
        p.load(in);
        Iterator<Entry<Object, Object>> it = p.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Object, Object> entry = it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            String dbname = (String) key;
            String[] dbArray = dbname.split("\\.");
            dbname = "dataSource_" + dbArray[0];
            DataSourceInfo dsi = new DataSourceInfo();
            //1 url 2 username= 3 password=
            String[] sdiArray = value.toString().split("\\|");
            String url = sdiArray[0];
            String usernameExpression = sdiArray[1];
            String passwordExpression = sdiArray[2];
            dsi.userName = usernameExpression.split("=")[1];
            dsi.connUrl = url;
            dsi.password = passwordExpression.split("=")[1];
            mds.put(dbname, dsi);
        }
        Resource fileResource = new ClassPathResource(DATABASE_PROP_FILE);
        InputStream inputStream = fileResource.getInputStream();
        Properties properties = new Properties();
        properties.load(inputStream);
        inputStream.close();
//        center.url=jdbc:mysql://10.11.10.34:3307/cityre_center?user=liudalei&password=liudalei_2017&autoReconnect=true&characterEncoding=utf8
//        cityreaccount.url
        mds.put("dataSource_center", addDataSourceInfo("center", properties));
        mds.put("dataSource_account", addDataSourceInfo("useraccount_center", properties));
        mds.put("dataSource_cityreaccount", addDataSourceInfo("cityre_center", properties));
        return mds;
    }

    DataSourceInfo addDataSourceInfo(String dbCode, Properties prop) {
        String url = prop.getProperty("jdbc." + dbCode + ".connection.url");
        String username = prop.getProperty("jdbc." + dbCode+ ".connection.username");
        String password = prop.getProperty("jdbc." + dbCode + ".connection.password");
        DataSourceInfo dsi = new DataSourceInfo();
        dsi.connUrl = url;
        dsi.userName = username;
        dsi.password = password;
        return dsi;
    }

    //  自定义数据结构
    private class DataSourceInfo {

        public String connUrl;
        public String userName;
        public String password;

        public String toString() {
            return "(url:" + connUrl + ", username:" + userName + ", password:"
                    + password + ")";
        }
    }

}  