/*
 * Copyright (c) 2019 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.ei.b7a.smb.util;

import org.ballerinalang.jvm.BallerinaErrors;
import org.ballerinalang.jvm.BallerinaValues;
import org.ballerinalang.jvm.types.BPackage;
import org.ballerinalang.jvm.types.BType;
import org.ballerinalang.jvm.values.ErrorValue;
import org.ballerinalang.jvm.values.MapValue;
import org.ballerinalang.jvm.values.ObjectValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Utils class for SMB client operations.
 */
public class SMBUtil {

    private static final Logger log = LoggerFactory.getLogger(SMBUtil.class);
    private static final int MAX_PORT = 65535;

    private SMBUtil() {
        // private constructor
    }

    public static boolean notValidProtocol(String url) {

        return !url.startsWith("smb");
    }

    public static String createUrl(ObjectValue clientConnector, String filePath) throws BallerinaSMBException {

        String username = (String) clientConnector.getNativeData(SmbConstants.ENDPOINT_CONFIG_USERNAME);
        String password = (String) clientConnector.getNativeData(SmbConstants.ENDPOINT_CONFIG_PASS_KEY);
        String host = (String) clientConnector.getNativeData(SmbConstants.ENDPOINT_CONFIG_HOST);
        int port = (int) clientConnector.getNativeData(SmbConstants.ENDPOINT_CONFIG_PORT);
        String protocol = (String) clientConnector.getNativeData(SmbConstants.ENDPOINT_CONFIG_PROTOCOL);

        return createUrl(protocol, host, port, username, password, filePath);
    }

    public static String createUrl(MapValue config) throws BallerinaSMBException {

        final String filePath = config.getStringValue(SmbConstants.ENDPOINT_CONFIG_PATH);
        String protocol = config.getStringValue(SmbConstants.ENDPOINT_CONFIG_PROTOCOL);
        final String host = config.getStringValue(SmbConstants.ENDPOINT_CONFIG_HOST);
        int port = extractPortValue(config.getIntValue(SmbConstants.ENDPOINT_CONFIG_PORT));

        final MapValue secureSocket = config.getMapValue(SmbConstants.ENDPOINT_CONFIG_SECURE_SOCKET);
        String username = null;
        String password = null;
        if (secureSocket != null) {
            final MapValue basicAuth = secureSocket.getMapValue(SmbConstants.ENDPOINT_CONFIG_BASIC_AUTH);
            if (basicAuth != null) {
                username = basicAuth.getStringValue(SmbConstants.ENDPOINT_CONFIG_USERNAME);
                password = basicAuth.getStringValue(SmbConstants.ENDPOINT_CONFIG_PASS_KEY);
            }
        }
        return createUrl(protocol, host, port, username, password, filePath);
    }

    private static String createUrl(String protocol, String host, int port, String username, String password,
                                    String filePath) throws BallerinaSMBException {

        String userInfo = username + ":" + password;
        URI uri;
        try {
            uri = new URI(protocol, userInfo, host, port, filePath, null, null);
        } catch (URISyntaxException e) {
            throw new BallerinaSMBException("Error occurred while constructing a URI from host: " + host +
                    ", port: " + port + ", username: " + username + " and basePath: " + filePath + e.getMessage(), e);
        }
        return uri.toString();
    }

    public static Map<String, String> getAuthMap(MapValue config) {
        final MapValue secureSocket = config.getMapValue(SmbConstants.ENDPOINT_CONFIG_SECURE_SOCKET);
        String username = null;
        String password = null;
        if (secureSocket != null) {
            final MapValue basicAuth = secureSocket.getMapValue(SmbConstants.ENDPOINT_CONFIG_BASIC_AUTH);
            if (basicAuth != null) {
                username = basicAuth.getStringValue(SmbConstants.ENDPOINT_CONFIG_USERNAME);
                password = basicAuth.getStringValue(SmbConstants.ENDPOINT_CONFIG_PASS_KEY);
            }
        }
        Map<String, String> authMap = new HashMap<>();
        authMap.put(SmbConstants.ENDPOINT_CONFIG_USERNAME, username);
        authMap.put(SmbConstants.ENDPOINT_CONFIG_PASS_KEY, password);

        return authMap;
    }

    /**
     * Creates an error message.
     *
     * @param error   the cause for the error.
     * @param details the detailed message of the error.
     * @return an error which will be propagated to ballerina user.
     */
    public static ErrorValue createError(String error, String details) {

        return BallerinaErrors.createError(error, details);
    }

    /**
     * Gives the port value from a given config input
     *
     * @param longValue the input config value
     * @return the relevant int value from the config
     */
    public static int extractPortValue(long longValue) {

        if (longValue <= 0 || longValue > MAX_PORT) {
            log.error("Invalid port number given in configuration");
            return -1;
        }
        try {
            return Math.toIntExact(longValue);
        } catch (ArithmeticException e) {
            log.warn("The value set for port needs to be less than {}. The port value is set to {}",
                    Integer.MAX_VALUE, Integer.MAX_VALUE);
            return Integer.MAX_VALUE;
        }
    }

    /**
     * Gives record type object for FileInfo record
     *
     * @return FileInfo record type object
     */
    public static BType getFileInfoType() {

        MapValue<String, Object> fileInfoStruct = BallerinaValues.createRecordValue(
                new BPackage(SmbConstants.SMB_ORG_NAME, SmbConstants.SMB_MODULE_NAME, SmbConstants.SMB_MODULE_VERSION),
                SmbConstants.SMB_FILE_INFO);
        return fileInfoStruct.getType();
    }
}
