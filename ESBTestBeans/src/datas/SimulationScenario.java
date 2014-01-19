package datas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Describes a simulation scenario. Including agents configuration and simulation steps.
 */
public class SimulationScenario implements Serializable {

    private ArrayList<AgentConfiguration> agentsconfiguration;
    private ArrayList<SimulationStep> steps;
    private long endDate = 0;

    /**
     * Returns a simulation scenario instance.
     */
    public SimulationScenario() {
        agentsconfiguration = new ArrayList<AgentConfiguration>();
        steps = new ArrayList<SimulationStep>();
    }

    /**
     * Returns an arraylist of AgentConfiguration
     * @return
     */
    public ArrayList<AgentConfiguration> getAgentsconfiguration() {
        return agentsconfiguration;
    }

    /**
     * Sets the agents configuration.
     * @param agentsconfiguration
     */
    public void setAgentsconfiguration(ArrayList<AgentConfiguration> agentsconfiguration) {
        this.agentsconfiguration = agentsconfiguration;
    }

    /**
     * Returns a list of steps.
     * @return
     */
    public ArrayList<SimulationStep> getSteps() {
        return steps;
    }

    /**
     * Sets the list of steps for this scenario
     * @param steps
     */
    public void setSteps(ArrayList<SimulationStep> steps) {
        this.steps = steps;
        for (Iterator<SimulationStep> i = steps.iterator();i.hasNext();) {
            SimulationStep step = i.next();
            if (step.getBurstStopDate() > endDate) {
                 endDate = step.getBurstStopDate();
            }
        }
    }

    /**
     * Adds a step to the scenario
     * @param step The step to be added
     */
    public void addStep(SimulationStep step) {
        steps.add(step);
        if (step.getBurstStopDate() > endDate) {
            endDate = step.getBurstStopDate();
        }
    }

    /**
     * Get the scenario end date
     * @return The scenario end date.
     */
    public long getEndDate() {
        return endDate;
    }


    /**
     * Returns a String representation of this object.
     * @return
     */
    @Override
    public String toString() {
        String str = "\n";
        str = str + " - Agents configuration - \n";
        for (int i = 0; i < agentsconfiguration.size(); i++) {
            str = str + agentsconfiguration.get(i);
        }
        str = str + " - Simulation scenario - \n";
        for (int i = 0; i < steps.size(); i++) {
            str = str + steps.get(i);
        }
        return str;
    }
}
