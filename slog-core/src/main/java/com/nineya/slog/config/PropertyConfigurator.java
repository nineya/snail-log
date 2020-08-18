package com.nineya.slog.config;

import com.nineya.slog.Level;
import com.nineya.slog.Logger;
import com.nineya.slog.LogManager;
import com.nineya.slog.appender.Appender;
import com.nineya.slog.appender.AppenderSkeleton;
import com.nineya.slog.appender.ConsoleAppender;
import com.nineya.slog.exception.ConfiguratorException;
import com.nineya.slog.exception.SnailFileException;
import com.nineya.slog.filter.Filter;
import com.nineya.slog.internal.Node;
import com.nineya.slog.internal.StatusLogger;
import com.nineya.slog.layout.Layout;
import com.nineya.slog.layout.PatternLayout;
import com.nineya.slog.LoggerRepository;
import com.nineya.slog.tool.FileTool;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author linsongwang
 * @date 2020/7/29 0:23
 * property配置加载器
 */
public class PropertyConfigurator implements Configurator<Properties> {
    private static final String LOGGER_OPEN = "slog.config.loggerOpen";
    private static final String LOGGER_PREFIX = "slog.";
    private static final String PARENT_SUFFIX = ".parent";
    private static final String CLASS_PREFIX = "slog.class.";
    private static final String Filter_PREFIX = "slog.filter.";
    private static final String APPENDER_SUFFIX = ".appender";
    private static final String LEVEL_SUFFIX = ".level";
    private static final String LAYOUT_SUFFIX = ".layout";
    private static final String FILTER_SUFFIX = ".filter.";
    private static final String APPENDER_ATTRIBUTE_SUFFIX = ".attribute.";
    private static final Logger STATUS_LOGGER = StatusLogger.getLogger();

    @Override
    public void doConfigure(String path, LoggerRepository repository) throws IOException {
        InputStream in = new BufferedInputStream(new FileInputStream(path));
        doConfigure(in, repository);
    }

    public void doConfigure(InputStream in, LoggerRepository repository) throws IOException{
        if (in == null){
            throw new SnailFileException("配置文件不存在");
        }
        Properties properties = new Properties();
        properties.load(in);
        in.close();
        doConfigure(properties, repository);
    }

    @Override
    public void doConfigure(Properties properties, LoggerRepository repository) {
        String[] loggerNames = properties.getProperty(LOGGER_OPEN, "").split(",");
        parseLogger(LogManager.ROOT_LOGGER_NAME, properties);
        for (String loggerName : loggerNames){
            parseLogger(loggerName, properties);
        }
        parseClassNode(properties);
        repository.setFilter(doFilter(Filter_PREFIX, properties));
    }

    private Logger parseLogger(String loggerName, Properties properties){
        Logger logger = LogManager.getLogger(loggerName);
        String loggerPrefix = LOGGER_PREFIX + loggerName;
        String level = properties.getProperty(loggerPrefix + LEVEL_SUFFIX);
        if (level != null) {
            logger.setLevel(Level.value(level));
        }
        // 添加日志继承关系
        if (!loggerName.equals(LogManager.ROOT_LOGGER_NAME)){
            String parentName = properties.getProperty(loggerPrefix + PARENT_SUFFIX, LogManager.ROOT_LOGGER_NAME);
            logger.setParent(LogManager.getLogger(parentName));
        }
        try {
            Appender appender = parseAppender(loggerPrefix + APPENDER_SUFFIX, properties);
            if (appender != null){
                logger.setAppender(appender);
            }
        } catch (Exception e) {
            STATUS_LOGGER.error("创建Appender失败", e);
        }
        logger.setFilter(doFilter(loggerPrefix + FILTER_SUFFIX, properties));
        return logger;
    }

    /**
     * 使用配置生成Appender对象
     * @param appenderPrefix Appender配置前缀
     * @param properties 配置信息
     * @return Appender对象
     */
    private Appender parseAppender(String appenderPrefix, Properties properties) {
        String appenderClass = properties.getProperty(appenderPrefix);
        if (appenderClass==null){
            return null;
        }
        try {
            Class clazz = Class.forName(appenderClass);
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            AppenderSkeleton appender = (AppenderSkeleton) constructor.newInstance();
            appender.setLayout(parseLayout(appenderPrefix + LAYOUT_SUFFIX, properties));
            appender.setFilter(doFilter(appenderPrefix + FILTER_SUFFIX, properties));
            doAttribute(appenderPrefix + APPENDER_ATTRIBUTE_SUFFIX, clazz, appender, properties);
            return appender;
        } catch (Exception e) {
            throw new ConfiguratorException(e, "创建 Appender 失败");
        }
    }

    /**
     * 解析配置信息生成Layout对象，如果配置中未对layout进行定义，则默认使用<b>PatternLayout<b/>
     * @param layoutPrefix layout配置前缀
     * @param properties 配置信息
     * @return 返回Layout对象
     */
    private Layout parseLayout(String layoutPrefix, Properties properties) {
        String layoutClass = properties.getProperty(layoutPrefix);
        if (layoutClass == null){
            return new PatternLayout();
        }
        try {
            Class clazz = Class.forName(layoutClass);
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            Layout layout = (Layout) constructor.newInstance();
            doAttribute(layoutPrefix + ".", clazz, layout, properties);
            return layout;
        } catch (Exception e) {
            throw new ConfiguratorException(e, "创建 Layout 出错");
        }
    }

    /**
     * 仔细创建过滤器操作，将单个过滤器组成过滤器链
     * @param filterPrefix 前缀
     * @param properties 配置信息
     * @return 过滤器链的首个过滤器
     */
    private Filter doFilter(String filterPrefix, Properties properties) {
        Filter headFilter = parseFilter(filterPrefix + "0", properties);
        if (headFilter == null){
            return null;
        }
        Filter filter = headFilter;
        int i = 1;
        while(true){
            filter.setNextFilter(parseFilter(filterPrefix + i++, properties));
            if (filter.getNextFilter() == null){
                return headFilter;
            }
            filter = filter.getNextFilter();
        }
    }

    /**
     * 解析配置信息生成过滤器对象
     * @param filterPrefix 过滤器前缀
     * @param properties 配置信息
     * @return 过滤器对象
     */
    private Filter parseFilter(String filterPrefix, Properties properties) {
        String filterClass = properties.getProperty(filterPrefix);
        if (filterClass == null){
            return null;
        }
        try {
            Class clazz = Class.forName(filterClass);
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            Filter filter = (Filter) constructor.newInstance();
            doAttribute(filterPrefix + ".", clazz, filter, properties);
            return filter;
        } catch (Exception e) {
            STATUS_LOGGER.error("创建 Filter 出错", e);
        }
        return null;
    }

    /**
     * 解析Properties中的配置，生成Node的level过滤信息
     * @param properties 配置信息
     */
    private void parseClassNode(Properties properties){
        Node rootNode = LogManager.getLoggerRepository().getRootNode();
        for (String key : properties.stringPropertyNames()){
            if (key.startsWith(CLASS_PREFIX)){
                String[] packages = key.replace(CLASS_PREFIX, "").split("\\.");
                if (packages.length == 1){
                    rootNode.addLevelFilters(packages[0], Level.value(properties.getProperty(key)));
                    continue;
                }
                Node node = rootNode;
                for (int i = 1; i < packages.length; i++){
                    node = node.getChild(packages[i]);
                }
                node.addLevelFilters(packages[0], Level.value(properties.getProperty(key)));
            }
        }
    }

    /**
     * 注入属性
     * @param prefix 配置信息前缀
     * @param clazz 将要被注入的对象的Class
     * @param obj 要被注入属性的对象
     * @param properties 配置信息
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void doAttribute(String prefix, Class clazz, Object obj, Properties properties) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods){
            Object attribute = properties.get(prefix + method.getName().replaceFirst("set", ""));
            if (attribute!=null){
                try {
                    method.invoke(obj, attribute);
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 通过path路径加载配置
     * @param path properties路径
     */
    public static void configure(String path){
        try {
            new PropertyConfigurator().doConfigure(path, LogManager.getLoggerRepository());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void configure(InputStream stream){
        try {
            new PropertyConfigurator().doConfigure(stream, LogManager.getLoggerRepository());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过Properties创建配置
     * @param properties Properties对象
     */
    public static void configure(Properties properties){
        new PropertyConfigurator().doConfigure(properties, LogManager.getLoggerRepository());
    }
}
