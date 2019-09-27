package com.revolut.api.common;

/**
 * @author : Vivek Mehta
 * Date: 26 Sep 2019
 * Details: Application constants to hold all constants.
 */
public interface ConfigConstants
{
    //configuration constants
    String CURRENT_DIR = System.getProperty("user.dir");

    String PATH_SEPARATOR = System.getProperty("file.separator");

    String CONFIG_DIR = "config";

    String SERVER_CONFIG_YML = "server-config.yml";

    String WEB_ROOT_DIRECTORY = "webroot";

    // yml constants
    String HOST = "host";

    String PORT = "port";
}
