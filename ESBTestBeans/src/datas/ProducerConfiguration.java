package datas;

/**
 * Describes a producer configuration
 */
public class ProducerConfiguration extends AgentConfiguration {
    private String wsAddress;

    /**
     * Returns a ConsumerConfiguration, and set its agent type to consumer.
     */
    public ProducerConfiguration(){
        this.agentType = AgentType.PRODUCER;
    }

    /**
     * Get the producer web service address.
     * @return
     */
    public String getWsAddress() {
        return wsAddress;
    }

    /**
     * Set the producer web service.
     * @param wsAddress
     */
    public void setWsAddress(String wsAddress) {
        this.wsAddress = wsAddress;
    }

    /**
     * Returns a String representation of this object.
     * @return
     */
    @Override
    public String toString() {
        return agentType + " " + this.getAgentId() + " at "+ this.getWsAddress()+"\n";
    }
}
