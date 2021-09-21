/*
 * Copyright (c) 2021 Kelly Skye (ACT Health)
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

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeTypeEnum;
import net.fhirfactory.pegacorn.components.transaction.valuesets.exceptions.ResourceInvalidSearchException;
import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import net.fhirfactory.pegacorn.deployment.topology.model.nodes.ProcessingPlantTopologyNode;
import net.fhirfactory.pegacorn.itops.model.ITOpsMetricsSet;
import net.fhirfactory.pegacorn.itops.model.ITOpsPubSubReport;
import net.fhirfactory.pegacorn.petasos.endpoints.oam.itops.ITOpsDiscoveredNodesDM;

@ApplicationScoped
public class ITOpsSubscriptionsHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ITOpsSubscriptionsHandler.class);

    @Inject
    private ITOpsDiscoveredNodesDM nodeDM;

    protected ITOpsDiscoveredNodesDM getNodeDM(){
        return(this.nodeDM);
    }

    protected Logger getLogger(){
        return(LOG);
    }
    
    protected ITOpsPubSubReport getSubscriptions(@Header("nodeKey") String nodeKey) throws ResourceInvalidSearchException {
        getLogger().debug(".getSubscriptions(): Entry, nodeKey --> {}", nodeKey);
        TopologyNode node = getNodeDM().getNode(nodeKey);
        if (node.getComponentType().equals(TopologyNodeTypeEnum.PROCESSING_PLANT)) {
            ProcessingPlantTopologyNode subsystemNode = (ProcessingPlantTopologyNode) node;
            //TODO work out how to get subscriptions from topologyNode
            return new ITOpsPubSubReport();
        }
        return (null);
    }
}
