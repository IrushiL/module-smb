// Copyright (c) 2019 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
//
// WSO2 Inc. licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except
// in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

// SMB Client Endpoint

import ballerina/log;
import ballerina/io;
import ballerinax/java;

# Represents an SMB client that intracts with a Samba server.
public type Client client object {
    private ClientEndpointConfig config = {};

    # Gets invoked during object initialization.
    #
    # + clientConfig - Configurations for smb client endpoint
    public function __init(ClientEndpointConfig clientConfig) {
        self.config = clientConfig;
        map<anydata>|error configMap = map<anydata>.constructFrom(clientConfig);
        if(configMap is map<anydata>){
            error? response = initEndpoint(self, configMap);
        } else {
            log:printError("Invalid config provided");
        }
    }

    # The `get()` function can be used to retrieve file content from a remote resource.
    #
    # + path   - The resource path
    # + return - A ReadableByteChannel that represents the data source to the resource or
    # an `error` if failed to establish communication with the Samba server or read the resource
    public remote function get(string path) returns io:ReadableByteChannel|error {
        handle resourcePath = java:fromString(path);
        io:ReadableByteChannel|error response = get(self, resourcePath);
        return response;
    }

    # The `append()` function can be used to append content to an existing file in a Samba server.
    # A new file is created if the file does not exist.
    #
    # + path    - The resource path
    # + content - Content to be written to the file in server
    # + return  - An `error` if failed to establish communication with the Samba server
    public remote function append(string path, io:ReadableByteChannel|string|xml|json content) returns error? {
        return append(self, getInputContent(path, content));
    }

    # The `put()` function can be used to add a file to a Samba server.
    #
    # + path    - The resource path
    # + content - Content to be written to the file in server
    # + return  - An `error` if failed to establish communication with the Samba server
    public remote function put(string path, io:ReadableByteChannel|string|xml|json content) returns error? {
        return put(self, getInputContent(path, content));
    }

    # The `delete()` function can be used to delete a file from a Samba server.
    #
    # + path   - The resource path
    # + return -  An `error` if failed to establish communication with the Samba server
    public remote function delete(string path) returns error? {
        handle resourcePath = java:fromString(path);
        error? response = delete(self, resourcePath);
        return response;
    }

    # The `mkdir()` function can be used to create a new direcotry in a Samba server.
    #
    # + path   - The directory path
    # + return - An `error` if failed to establish communication with the Samba server
    public remote function mkdir(string path) returns error? {
        handle resourcePath = java:fromString(path);
        error? response = mkdir(self, resourcePath);
        return response;
    }

    # The `rmdir()` function can be used to delete an empty directory in a Samba server.
    #
    # + path   - The directory path
    # + return - An `error` if failed to establish communication with the Samba server
    public remote function rmdir(string path) returns error? {
        handle resourcePath = java:fromString(path);
        error? response = rmdir(self, resourcePath);
        return response;
    }

    # The `rename()` function can be used to rename a file or move to a new location within the same Samba server.
    #
    # + origin      - The source file location
    # + destination - The destination file location
    # + return      - An `error` if failed to establish communication with the Samba server
    public remote function rename(string origin, string destination) returns error? {
        handle originPath = java:fromString(origin);
        handle destinationPath = java:fromString(destination);
        error? response = rename(self, originPath, destinationPath);
        return response;
    }

    # The `size()` function can be used to get the size of a file resource.
    #
    # + path   - The resource path
    # + return - The file size in bytes or an `error` if failed to establish communication with the Samba server
    public remote function size(string path) returns int|error {
        handle resourcePath = java:fromString(path);
        int|error response = size(self, resourcePath);
        return response;
    }

    # The `list()` function can be used to get the file name list in a given folder.
    #
    # + path   - The direcotry path
    # + return - An array of file names or an `error` if failed to establish communication with the Samba server
    public remote function list(string path) returns FileInfo[]|error {
        handle resourcePath = java:fromString(path);
        FileInfo[]|error response = list(self, resourcePath);
        return response;
    }

    # The `isDirectory()` function can be used to check if a given resource is a direcotry.
    #
    # + path   - The resource path
    # + return - true if given resource is a direcotry or an `error` if failed to connect with the Samba server
    public remote function isDirectory(string path) returns boolean|error {
        handle resourcePath = java:fromString(path);
        boolean|error response = isDirectory(self, resourcePath);
        return response;
    }
};

# Configuration for smb client endpoint.
#
# + protocol     - Supported protocol
# + host         - Target service URL
# + port         - Port number of the remote service
# + secureSocket - Authenthication options
public type ClientEndpointConfig record {|
    Protocol protocol = SMB;
    string host = "127.0.0.1";
    int port = 445;
    SecureSocket? secureSocket = ();
|};

function getInputContent(string path, io:ReadableByteChannel|string|xml|json content) returns InputContent{
    InputContent inputContent = {
        filePath: path
    };

    if(content is io:ReadableByteChannel){
        inputContent.isFile = true;
        inputContent.fileContent = content;
    } else if(content is string){
        inputContent.textContent = content;
    } else if(content is json){
        inputContent.textContent = content.toJsonString();
    } else {
        inputContent.textContent = content.toString();
    }

    return inputContent;
}
