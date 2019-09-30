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

import ballerina/io;
import ballerinax/java;

public function initEndpoint(Client clientEndpoint, map<anydata> config) returns error? = @java:Method {
    name: "initClientEndpoint",
    class: "org.wso2.ei.b7a.smb.client.SMBClient"
} external;

public function get(Client clientEndpoint, handle path) returns io:ReadableByteChannel|error = @java:Method{
    name: "get",
    class: "org.wso2.ei.b7a.smb.client.SMBClient"
} external;

public function append(Client clientEndpoint, InputContent inputContent) returns error? = @java:Method{
    name: "append",
    class: "org.wso2.ei.b7a.smb.client.SMBClient"
} external;

public function put(Client clientEndpoint, InputContent inputContent) returns error? = @java:Method{
    name: "put",
    class: "org.wso2.ei.b7a.smb.client.SMBClient"
} external;

public function delete(Client clientEndpoint, handle path) returns error? = @java:Method{
    name: "delete",
    class: "org.wso2.ei.b7a.smb.client.SMBClient"
} external;

public function mkdir(Client clientEndpoint, handle path) returns error? = @java:Method{
    name: "mkdir",
    class: "org.wso2.ei.b7a.smb.client.SMBClient"
} external;

public function rmdir(Client clientEndpoint, handle path) returns error? = @java:Method{
    name: "rmdir",
    class: "org.wso2.ei.b7a.smb.client.SMBClient"
} external;

public function rename(Client clientEndpoint, handle origin, handle destination) returns error? = @java:Method{
    name: "rename",
    class: "org.wso2.ei.b7a.smb.client.SMBClient"
} external;

public function size(Client clientEndpoint, handle path) returns int|error = @java:Method{
    name: "size",
    class: "org.wso2.ei.b7a.smb.client.SMBClient"
} external;

public function list(Client clientEndpoint, handle path) returns FileInfo[]|error = @java:Method{
    name: "list",
    class: "org.wso2.ei.b7a.smb.client.SMBClient"
} external;

public function isDirectory(Client clientEndpoint, handle path) returns boolean|error = @java:Method{
    name: "isDirectory",
    class: "org.wso2.ei.b7a.smb.client.SMBClient"
} external;

public function poll(ListenerConfig config) returns error? = @java:Method{
    name: "poll",
    class: "org.wso2.ei.b7a.smb.server.SMBListenerHelper"
} external;

public function register(Listener listenerEndpoint, ListenerConfig config, service smbService, handle name)
    returns handle|error = @java:Method{
    name: "register",
    class: "org.wso2.ei.b7a.smb.server.SMBListenerHelper"
} external;

