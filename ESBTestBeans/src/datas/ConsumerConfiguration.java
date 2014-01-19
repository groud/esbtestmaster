package datas;

/**
 * Describes a consumer configuration
 */
public class ConsumerConfiguration extends AgentConfiguration {
    /**
     * Returns a ConsumerConfiguration, and set its agent type to consumer
     */
    public ConsumerConfiguration() {
        this.agentType = AgentType.CONSUMER;
    }
}
