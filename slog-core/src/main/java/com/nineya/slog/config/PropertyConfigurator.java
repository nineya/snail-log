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
    private static final String CLASS_SUFFIX = ".class";
    private static final String APPENDER_SUFFIX = ".appender";
    private static final String LEVEL_SUFFIX = ".level";
    private static final String LAYOUT_SUFFIX = ".layout";
    private static final String FILTER_SUFFIX = ".filter";
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
            Appender appender = parseAppender(loggerPrefix + APPENDER_SUFFIX, properties);
            if (appender != null){
                logger.setAppender(appender);
            }
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
    public void doAttribute(String prefix, Class clazz, Object obj, Properties properties) throws IllegalAccessException, InvocationTargetException {
        while (clazz !=null){
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods){
                Object attribute = properties.get(prefix + method.getName().replaceFirst("set", ""));
                if (attribute!=null){
                    method.setAccessible(true);
                    method.invoke(obj, attribute);
                }
            }
            clazz = clazz.getSuperclass();
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
