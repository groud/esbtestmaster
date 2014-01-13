package datas;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author gilles
 */
public class SimulationScenario implements Serializable{
    private ArrayList<AgentConfiguration> agentsconfiguration;
    private ArrayList<SimulationStep> steps;
    private long endDate = 0;

    public SimulationScenario () {
        agentsconfiguration = new ArrayList<AgentConfiguration>();
        steps = new ArrayList<SimulationStep>();
    }




    public ArrayList<AgentConfiguration> getAgentsconfiguration() {
        return agentsconfiguration;
    }

    public void setAgentsconfiguration(ArrayList<AgentConfiguration> agentsconfiguration) {
        this.agentsconfiguration = agentsconfiguration;
    }

    public ArrayList<SimulationStep> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<SimulationStep> steps) {
        this.steps = steps;
    }

    public void addStep(SimulationStep step) {
        steps.add(step);
        if(step.getBurstStopDate() > endDate) {
            endDate = step.getBurstStopDate();
        }
    }

    public long getEndDate() {
        return endDate;
    }

        @Override
    public String toString() {
        String str ="\n";
        str = str + " - Agents configuration - \n";
        for (int i=0;i<agentsconfiguration.size();i++) {
            str = str + agentsconfiguration.get(i);
        }
        str = str + " - Simulation scenario - \n";
        for (int i=0;i<steps.size();i++) {
            str = str + steps.get(i);
        }
        return str;
    }
}
