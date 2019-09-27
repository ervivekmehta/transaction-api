package com.revolut.api.common;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * @author : Vivek Mehta
 * Date: 26 Sep 2019
 * Details: Configuration utils to provide all configuration from config YAML file.
 */
public class ConfigUtil
{
    private static final Logger _logger = LoggerFactory.getLogger(ConfigUtil.class);

    public static String getHostName()
    {

        InputStream fileInputStream = null;

        String host = "localhost";

        try
        {
            Yaml yaml = new Yaml();

            fileInputStream = new FileInputStream(new File(ConfigConstants.CURRENT_DIR + ConfigConstants.PATH_SEPARATOR + ConfigConstants.CONFIG_DIR + ConfigConstants.PATH_SEPARATOR + ConfigConstants.SERVER_CONFIG_YML));

            Map<String, Object> config = yaml.load(fileInputStream);

            host = CommonUtil.isMapValueNotBlank(config, ConfigConstants.HOST) ? CommonUtil.getStringValue(config.get(ConfigConstants.HOST)) : host;
        }
        catch (Exception exception)
        {
            _logger.error(exception);
        }
        finally
        {
            if (fileInputStream != null)
            {
                try
                {
                    fileInputStream.close();
                }
                catch (Exception ignored)
                {
                }
            }
        }

        return host;
    }

    public static int getPort()
    {
        InputStream fileInputStream = null;

        int host = 8080;

        try
        {
            Yaml yaml = new Yaml();

            fileInputStream = new FileInputStream(new File(ConfigConstants.CURRENT_DIR + ConfigConstants.PATH_SEPARATOR + ConfigConstants.CONFIG_DIR + ConfigConstants.PATH_SEPARATOR + ConfigConstants.SERVER_CONFIG_YML));

            Map<String, Object> config = yaml.load(fileInputStream);

            host = CommonUtil.isMapValueNotBlank(config, ConfigConstants.PORT) ? CommonUtil.getIntegerValue(config.get(ConfigConstants.PORT)) : host;
        }
        catch (Exception exception)
        {
            _logger.error(exception);
        }
        finally
        {
            if (fileInputStream != null)
            {
                try
                {
                    fileInputStream.close();
                }
                catch (Exception ignored)
                {
                }
            }
        }

        return host;
    }
}
