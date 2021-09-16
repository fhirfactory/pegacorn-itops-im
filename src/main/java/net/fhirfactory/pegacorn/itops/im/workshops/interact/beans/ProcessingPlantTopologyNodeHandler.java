/*
 * Copyright (c) 2021 Mark A. Hunter (ACT Health)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.fhirfactory.pegacorn.itops.im.workshops.interact.beans;

import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeTypeEnum;
import net.fhirfactory.pegacorn.components.transaction.valuesets.exceptions.ResourceInvalidSearchException;
import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import net.fhirfactory.pegacorn.deployment.topology.model.nodes.ProcessingPlantTopologyNode;
import net.fhirfactory.pegacorn.deployment.topology.model.nodes.SubsystemTopologyNode;
import net.fhirfactory.pegacorn.petasos.endpoints.oam.itops.ITOpsDiscoveredNodesDM;
import org.apache.camel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class ProcessingPlantTopologyNodeHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessingPlantTopologyNodeHandler.class);

    @Inject
    private ITOpsDiscoveredNodesDM nodeDM;

    protected ITOpsDiscoveredNodesDM getNodeDM(){
        return(this.nodeDM);
    }

    protected Logger getLogger(){
        return(LOG);
    }

    protected ProcessingPlantTopologyNode getProcessingPlantTopologyNode(@Header("componentId") String componentId) throws ResourceInvalidSearchException {
        getLogger().debug(".getProcessingPlantTopologyNode(): Entry, componentId --> {}", componentId);
        TopologyNode node = getNodeDM().getNode(componentId);
        if(node.getComponentType().equals(TopologyNodeTypeEnum.PROCESSING_PLANT)){
            ProcessingPlantTopologyNode subsystemNode = (ProcessingPlantTopologyNode) node;
            return(subsystemNode);
        }
        return(null);
    }

    protected List<ProcessingPlantTopologyNode> getProcessingPlantTopologyNodeList(@Header("sortBy") String sortBy,
                                                                                   @Header("sortOrder") String sortOrder,
                                                                                   @Header("pageSize") String pageSize,
                                                                                   @Header("page") String page){
        getLogger().info(".getProcessingPlantTopologyNodeList(): Entry");
        Set<TopologyNode> nodeList = getNodeDM().getTopologyNodeSet();
        List<ProcessingPlantTopologyNode> subsystemNodeList = new ArrayList<>();
        for(TopologyNode currentNode: nodeList){
            if(currentNode.getComponentType().equals(TopologyNodeTypeEnum.PROCESSING_PLANT)){
                ProcessingPlantTopologyNode currentSubsystemNode = (ProcessingPlantTopologyNode)currentNode;
                getLogger().info(".getProcessingPlantTopologyNodeList(): Adding Entry->{}", currentSubsystemNode.getComponentId());
                subsystemNodeList.add(currentSubsystemNode);
            }
        }
        return(subsystemNodeList);
    }
}
