package datas;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.ArrayList;

/**
 *
 * @author gilles
 */
public class SimulationScenario {
    private ArrayList<AgentConfiguration> agentsconfiguration;
    private ArrayList<SimulationStep> steps;

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
