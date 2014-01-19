package interfaces;

/**
 * UserInputsListener
 */
public interface UserInputsListener {

    public void startSimulation(String resultsFilename);

    public void abortSimulation();

    public void loadScenario(String XMLfile);

    public void calculateKPI(String XMLfile);

    public void calculateKPI(String inXMLfile, String outXMLfile);
}
