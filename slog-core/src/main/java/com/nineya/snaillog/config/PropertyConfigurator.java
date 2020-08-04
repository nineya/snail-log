package com.nineya.snaillog.config;

import com.nineya.snaillog.Level;
import com.nineya.snaillog.Logger;
import com.nineya.snaillog.LogManager;
import com.nineya.snaillog.appender.Appender;
import com.nineya.snaillog.appender.AppenderSkeleton;
import com.nineya.snaillog.appender.ConsoleAppender;
import com.nineya.snaillog.exception.SnailFileException;
import com.nineya.snaillog.filter.Filter;
import com.nineya.snaillog.internal.Node;
import com.nineya.snaillog.layout.Layout;
import com.nineya.snaillog.layout.PatternLayout;
import com.nineya.snaillog.LoggerRepository;
import com.nineya.snaillog.tool.FileTool;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
    private static final String CLASS_SUFFIX = ".class";
    private static final String APPENDER_SUFFIX = ".appender";
    private static final String LEVEL_SUFFIX = ".level";
    private static final String LAYOUT_SUFFIX = ".layout";
    private static final String FILTER_SUFFIX = ".filter";
    private static final String APPENDER_ATTRIBUTE_SUFFIX = ".attribute.";

    @Override
    public void doConfigure(String path, LoggerRepository repository) throws IOException {
        if (!FileTool.fileExists(path)){
            throw new SnailFileException(path, "配置文件不存在");
        }
        Properties properties = new Properties();
        InputStream in = new BufferedInputStream(new FileInputStream(path));
        properties.load(in);
        in.close();
        doConfigure(properties, repository);
    }

    @Override
    public void doConfigure(Properties properties, LoggerRepository repository) {
        String[] loggerNames = properties.getProperty(LOGGER_OPEN, "").split(",");
        parseRootLogger(properties);
        for (String loggerName : loggerNames){
            parseLogger(loggerName, properties);
        }
    }

    // 生成root日志
    public Logger parseRootLogger(Properties properties){
        Logger logger = parseLogger(LogManager.ROOT_LOGGER_NAME, properties);
        if (logger.getAppender() == null) {
            AppenderSkeleton appender = new ConsoleAppender();
            appender.setLayout(new PatternLayout());
            logger.setAppender(appender);
        }
        return logger;
    }

    public Logger parseLogger(String loggerName, Properties properties){
        Logger logger = LogManager.getLogger(loggerName);
        String loggerPrefix = LOGGER_PREFIX + loggerName;
        String level = properties.getProperty(loggerPrefix + LEVEL_SUFFIX);
        if (level != null) {
            logger.setLevel(Level.value(level));
        }
        // 添加日志继承关系
        if (!loggerName.equals(LogManager.ROOT_LOGGER_NAME)){
            String parentName = properties.getProperty(loggerPrefix + PARENT_SUFFIX, LogManager.ROOT_LOGGER_NAME);
            if (!parentName.equals(LogManager.ROOT_LOGGER_NAME)){
                logger.setParent(LogManager.getLogger(parentName));
            }
        }
        parseClassNode(loggerName, properties);
        try {
            logger.setAppender(parseAppender(loggerPrefix + APPENDER_SUFFIX, properties));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logger;
    }

    public Appender parseAppender(String appenderPrefix, Properties properties) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        String appenderClass = properties.getProperty(appenderPrefix);
        if (appenderClass==null){
            return null;
        }
        Class clazz = Class.forName(appenderClass);
        Constructor constructor = clazz.getDeclaredConstructor();
        AppenderSkeleton appender = (AppenderSkeleton) constructor.newInstance();
        constructor.setAccessible(true);
        appender.setLayout(parseLayout(appenderPrefix + LAYOUT_SUFFIX, properties));
        Filter filter = parseFilter(appenderPrefix + FILTER_SUFFIX, properties);
        if (filter!=null){
            appender.addFilter(filter);
        }
        doAttribute(appenderPrefix + APPENDER_ATTRIBUTE_SUFFIX, clazz, appender, properties);
        return appender;
    }

    public Layout parseLayout(String layoutPrefix, Properties properties) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        String layoutClass = properties.getProperty(layoutPrefix);
        if (layoutClass==null){
            return new PatternLayout();
        }
        Class clazz =  Class.forName(layoutClass);
        Constructor constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        Layout layout = (Layout) constructor.newInstance();
        doAttribute(layoutPrefix + ".", clazz, layout, properties);
        return layout;
    }

    public Filter parseFilter(String filterPrefix, Properties properties) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        String filterClass = properties.getProperty(filterPrefix);
        if (filterClass == null){
            return null;
        }
        Class clazz = Class.forName(filterClass);
        Constructor constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        Filter filter = (Filter) constructor.newInstance();
        doAttribute(filterPrefix + ".", clazz, filter, properties);
        return filter;
    }

    public void parseClassNode(String loggerName, Properties properties){
        Node rootNode = LogManager.getLoggerRepository().getRootNode();
        String prefix = LOGGER_PREFIX + loggerName + CLASS_SUFFIX;
        for (String key : properties.stringPropertyNames()){
            if (key.startsWith(prefix)){
                String packages = key.replace(prefix, "");
                if (packages.equals("")){
                    rootNode.addLevelFilters(loggerName, Level.value(properties.getProperty(key)));
                    continue;
                }
                String[] classs = packages.substring(1).split("\\.");
                Node node = rootNode;
                for (String clazz: classs){
                    node = node.getChild(clazz);
                }
                node.addLevelFilters(loggerName, Level.value(properties.getProperty(key)));
            }
        }
    }

    // 属性注入
    public void doAttribute(String prefix, Class clazz, Object obj, Properties properties) throws IllegalAccessException, NoSuchFieldException {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            Object attribute = properties.get(prefix + field.getName());
            if (attribute!=null){
                field.setAccessible(true);
                field.set(obj, attribute);
            }
        }
    }

    public static void configure(String path){
        try {
            new PropertyConfigurator().doConfigure(path, LogManager.getLoggerRepository());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void configure(Properties properties){
        new PropertyConfigurator().doConfigure(properties, LogManager.getLoggerRepository());
    }
}
