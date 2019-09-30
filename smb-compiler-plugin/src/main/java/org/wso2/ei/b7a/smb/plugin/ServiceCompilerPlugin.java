/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package org.wso2.ei.b7a.smb.plugin;

import org.ballerinalang.compiler.plugins.SupportedResourceParamTypes;
import org.ballerinalang.model.tree.AnnotationAttachmentNode;
import org.ballerinalang.model.tree.ServiceNode;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.util.diagnostic.Diagnostic;
import org.ballerinalang.util.diagnostic.DiagnosticLog;
import org.wso2.ballerinalang.compiler.semantics.model.types.BObjectType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BType;
import org.wso2.ballerinalang.compiler.tree.BLangFunction;
import org.wso2.ballerinalang.compiler.tree.BLangSimpleVariable;
import org.wso2.ballerinalang.util.AbstractTransportCompilerPlugin;
import org.wso2.ei.b7a.smb.util.SmbConstants;

import java.util.List;

/**
 * Abstract Compiler plugin for validating SMB Listener services
 */
@SupportedResourceParamTypes(expectedListenerType = @SupportedResourceParamTypes.Type(
        orgName = "wso2",
        packageName = "smb",
        name = "Listener"),
        paramTypes = {
                @SupportedResourceParamTypes.Type(orgName = "wso2", packageName = "smb", name = "WatchEvent")
        }
)
public class ServiceCompilerPlugin extends AbstractTransportCompilerPlugin {

    private DiagnosticLog dlog = null;

    @Override
    public void init(DiagnosticLog diagnosticLog) {

        dlog = diagnosticLog;
    }

    @Override
    public void process(ServiceNode serviceNode, List<AnnotationAttachmentNode> annotations) {

        List<BLangFunction> resources = (List<BLangFunction>) serviceNode.getResources();
        if (resources.isEmpty()) {
            dlog.logDiagnostic(Diagnostic.Kind.ERROR, serviceNode.getPosition(),
                    "No resources defined for the service: " + serviceNode.getName().getValue());
        } else if (resources.size() >= 2) {
            dlog.logDiagnostic(Diagnostic.Kind.ERROR, serviceNode.getPosition(),
                    "Only one resource is allowed for the service: " + serviceNode.getName().getValue());
        }
        if (resources.size() == 1) {
            final List<BLangSimpleVariable> parameters = resources.get(0).getParameters();

            if (parameters.size() != 1) {
                dlog.logDiagnostic(Diagnostic.Kind.ERROR, resources.get(0).getPosition(),
                        "Invalid resource signature. A single " + SmbConstants.SMB_SERVER_EVENT
                                + " parameter is allowed in the resource signature.");
            }
            final BType type = parameters.get(0).getTypeNode().type;
            if (type.getKind().equals(TypeKind.OBJECT) && type instanceof BObjectType) {
                BObjectType event = (BObjectType) type;
                if (!"smb".equals(event.tsymbol.pkgID.name.value) || !SmbConstants.SMB_SERVER_EVENT
                        .equals(event.tsymbol.name.value)) {
                    dlog.logDiagnostic(Diagnostic.Kind.ERROR, parameters.get(0).getPosition(),
                            "Parameter should be of type - smb:" + SmbConstants.SMB_SERVER_EVENT);
                }
            }
        }
    }
}
