/*
 * Copyright (c) 2018 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

import org.wso2.transport.remotefilesystem.Constants;

/**
 * Constants for SMB operations
 */
public class SmbConstants {

    private SmbConstants() {
        // private constructor
    }

    public static final String SUCCESSFULLY_FINISHED_THE_ACTION = "Successfully finished the action.";
    public static final String LAST_MODIFIED_TIMESTAMP = "lastModifiedTimestamp";

    public static final String PROPERTY_URI = Constants.URI;
    public static final String PROPERTY_DESTINATION = Constants.DESTINATION;
    public static final String SMB_PASSIVE_MODE = Constants.PASSIVE_MODE;
    public static final String USER_DIR_IS_ROOT = Constants.USER_DIR_IS_ROOT;
    public static final String AVOID_PERMISSION_CHECK = Constants.AVOID_PERMISSION_CHECK;
    public static final String PROPERTY_MAP = "map";
    public static final String SMB_ORG_NAME = "wso2";
    public static final String SMB_MODULE_NAME = "smb";
    public static final String SMB_MODULE_VERSION = "0.3.1";
    public static final String SMB_LISTENER = "Listener";
    public static final String SMB_SERVER_EVENT = "WatchEvent";
    public static final String SMB_FILE_INFO = "FileInfo";
    public static final String SMB_SERVER_CONNECTOR = "serverConnector";

    public static final String ENDPOINT_CONFIG_PROTOCOL = "protocol";
    public static final String ENDPOINT_CONFIG_HOST = "host";
    public static final String ENDPOINT_CONFIG_PORT = "port";
    public static final String ENDPOINT_CONFIG_USERNAME = "username";
    public static final String ENDPOINT_CONFIG_PASS_KEY = "password";
    public static final String ENDPOINT_CONFIG_PATH = "path";
    public static final String ENDPOINT_CONFIG_FILE_PATTERN = "fileNamePattern";
    public static final String ENDPOINT_CONFIG_SECURE_SOCKET = "secureSocket";
    static final String ENDPOINT_CONFIG_BASIC_AUTH = "basicAuth";
    public static final String ENDPOINT_CONFIG_PRIVATE_KEY = "privateKey";

    public static final String INPUT_CONTENT_FILE_PATH_KEY = "filePath";
    public static final String INPUT_CONTENT_IS_FILE_KEY = "isFile";
    public static final String INPUT_CONTENT_FILE_CONTENT_KEY = "fileContent";
    public static final String INPUT_CONTENT_TEXT_CONTENT_KEY = "textContent";

}
